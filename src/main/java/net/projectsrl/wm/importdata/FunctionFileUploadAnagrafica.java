
package net.projectsrl.wm.importdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.core.FunctionWebApp_base;
import net.projectsrl.wm.db.CurriculImportDAO;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.utils.RandomPasswordGenerator;
import net.projectsrl.wm.utils.Utils;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 * FunctionFileUpload
 * 
 */
public class FunctionFileUploadAnagrafica extends FunctionWebApp_base {

    private static final String PAGE        = "importanagrafiche";
    private static final String PAGE_CHIUDI = "importanagrafiche";
    private static final int    _sizeMax    = 100000000;
    int noOfCAPSAlpha = 1;
    int noOfDigits = 1;
    int noOfSplChars = 0;
    int minLen = 6;
    int maxLen = 7;


    public FunctionFileUploadAnagrafica() {

        super();
    }

    public FunctionFileUploadAnagrafica(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
        templateData.put(UploadedFiles.FILE_TYPE, req.getField(UploadedFiles.FILE_TYPE));
        templateData.put("MESSAGGIO_ATTESA", "Trasferimento file in corso...");
        _applicationSrv.displayPage(PAGE, templateData, res);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
        HashMap<String, Object> templateData = setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(templateData, req);
        creaCartella();
        cancellaFile();
        
        templateData.put("FILE1","");
        templateData.put("FILE2","");
        templateData.put("FILE3","");
        templateData.put("FILE_OK1","");
        templateData.put("FILE_OK2","");
        templateData.put("FILE_OK3","");
        req.getSession(false).setAttribute("FILE1", "");
        req.getSession(false).setAttribute("FILE2", "");
        req.getSession(false).setAttribute("FILE3", "");
        req.getSession(false).setAttribute("FILE_OK1", "");
        req.getSession(false).setAttribute("FILE_OK2", "");
        req.getSession(false).setAttribute("FILE_OK3", "");
        req.getSession(false).setAttribute("TITOLI", "");
        req.getSession(false).setAttribute("CARICATO", "");
        
        if (!req.getField("SALVA").equals("SI")){
        	uploadFiles(req);
	        
	        String nomeFileImportato1=(String) req.getSession(false).getAttribute("FILE_NAME_IMPORT_ANAGRAFICHE1");
	        String nomeFileImportato2=(String) req.getSession(false).getAttribute("FILE_NAME_IMPORT_ANAGRAFICHE2");
	        String nomeFileImportato3=(String) req.getSession(false).getAttribute("FILE_NAME_IMPORT_ANAGRAFICHE3");
	        templateData.put("CARICATO", "");
	        String idImport=Utils.getUnique();
	        
	        if (nomeFileImportato1.toLowerCase().endsWith(".txt") || nomeFileImportato2.toLowerCase().endsWith(".txt") || nomeFileImportato3.toLowerCase().endsWith(".txt")){
				creaScaricoDati(req,res,userInfo,idImport);
		        UploadedFiles.logUploadingStatus();
		        templateData.put("CARICATO", "SI");
		        templateData.put("FILE1",req.getSession(false).getAttribute("FILE1"));
		        templateData.put("FILE2",req.getSession(false).getAttribute("FILE2"));
		        templateData.put("FILE3",req.getSession(false).getAttribute("FILE3"));
		        templateData.put("FILE_OK1",req.getSession(false).getAttribute("FILE_OK1"));
		        templateData.put("FILE_OK2",req.getSession(false).getAttribute("FILE_OK2"));
		        templateData.put("FILE_OK3",req.getSession(false).getAttribute("FILE_OK3"));
		        if(req.getSession(false).getAttribute("CARICATO").equals("NO")){
		        	templateData.put("CARICATO", "NO");
		        }
	        }else{
	        	templateData.put("CARICATO", "NO");
	        }
	        if(templateData.get("CARICATO").equals("NO")){
	        	String sqlDelete ="DELETE FROM CURRICUL WHERE ID_IMPORT='"+idImport+"'";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete);
	        }
	        cancellaFile();
        }
        

        
        
        int contaMail = 0;
       
    	
        if (req.getField("SALVA").equals("SI")){
        	int lettoFile1 = 0;
         	int lettoFile2 = 0;
         	int lettoFile3 = 0;
         	templateData.put("FILE1",lettoFile1);
            templateData.put("FILE2",lettoFile2);
            templateData.put("FILE3",lettoFile3);
        	String stringaCompleta=req.getField("TO_SAVE");
        	String utenteCompleto="";
        	String taggancio="";
        	String mail="";
        	
          	int conta = 0;
          	
        	for( int i=0; i<stringaCompleta.length(); i++ ) {
        	    if( stringaCompleta.charAt(i) == '|' ) {
        	    	conta++;
        	    } 
        	}
        	
        	while (conta>0){
        		conta=conta-1;
        		int finale=stringaCompleta.indexOf('/');
        		utenteCompleto=stringaCompleta.substring(0,finale);
				stringaCompleta=stringaCompleta.substring(finale+1,stringaCompleta.length());
				
				taggancio=utenteCompleto.substring(utenteCompleto.indexOf("|"), utenteCompleto.indexOf("%")).replace("|", "").replace("%", "");
				mail=utenteCompleto.substring(utenteCompleto.indexOf("%")).replace("/", "").replace("%", "").toLowerCase();
				
				String updateDipendente ="UPDATE CURRICUL SET EMAIL='"+mail+"' WHERE TAGGANCIO = '"+taggancio+"'";
		        net.projectsrl.wm.utils.WMUtils.executeQuery(updateDipendente);
		        
		        String password = "";
                char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);
                password= new String(pswd);
                if (password.length()<7){
                	password=password+Utils.getUnique().substring(14, 18);
		    	}else{
		    		password=password+Utils.getUnique().substring(14, 17);
		    	}
            
		        String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");
		        String destinatari_mail =mail;
		        String nome=getDatiDipendente(taggancio)[0];
		        String cognome=getDatiDipendente(taggancio)[1];
		        String nominativo = cognome+" "+nome;
		        String aziendaDipendente=getDatiDipendente(taggancio)[2];
		        
		        if (!destinatari_mail.equals("")){
		        	contaMail=contaMail+1;
		        	String sqlInsertUtente ="INSERT INTO UTENTI (USERID,PASSWORD,RUOLO,ID_CODICE,NOME,COGNOME,EMAIL,DATA,DATA_SC,ATTIVO,AZIENDA,ID_DIPENDENTE,PWD_SCADUTA)" +
				    		" VALUES ('"+mail+"','"+password+"','D','"+Utils.getUnique()+"','"+nome+"','"+cognome+"','"+mail+"','"+Utils.getStringDataOggiRibaltata()+"','2999/12/31','S','"+aziendaDipendente+"','"+taggancio+"','S')";
					        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsertUtente);				        
					inviaMail(from, password, destinatari_mail, nominativo);
		        }
        	}
        }
        

        
        templateData.put("UTENTI_CREATI", contaMail);
        cancellaFile();
        
        _applicationSrv.displayPage(PAGE_CHIUDI, templateData, setPageDatasetParam(PAGE, req, templateData), res);

    }

    @SuppressWarnings("deprecation")
    private boolean uploadFiles(SsbServletRequest req) {

        String fileType = req.getField(UploadedFiles.FILE_TYPE);

        String fileName = "";
        int contatore=0;
        String estensione="";

        DiskFileUpload fu = new DiskFileUpload();
        // If file size exceeds, a FileUploadException will be thrown
        fu.setSizeMax(_sizeMax);
        try {
            List<FileItem> fileItems = fu.parseRequest(req);
            Iterator<FileItem> itr = fileItems.iterator();

            // ciclo per i file
            while (itr.hasNext()) {
                FileItem fi = (FileItem) itr.next();

                // Check if not form field so as to only handle the file inputs else condition handles the submit button input
                if (!fi.isFormField()) {
                    fileName = fi.getName();
                    contatore=contatore+1;

                    estensione=fileName.substring(fileName.lastIndexOf(".")).trim();
                    fileName = Integer.toString(contatore)+estensione;
                    req.getSession(false).setAttribute("FILE_NAME_IMPORT_ANAGRAFICHE"+contatore, fileName);
                    
                    File fNew = new File(_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.importcv"), fileName);
                    req.getSession(false).setAttribute("FILE_NAME_IMPORT_ANAGRAFICHE"+contatore, fileName);

                    fi.write(fNew);

                    UploadedFiles.setStatus(fileType, UploadedFiles.UPLOAD_COMPLETED, fileName, getSessionUser(req));
                }
            }

            return true;

        } catch (Throwable e) {
            new AppCrash(e);
            UploadedFiles.setStatus(fileType, UploadedFiles.UPLOAD_ERROR, fileName, getSessionUser(req));
            return false;
        }

    }
    
    
    
    
    
    private void creaScaricoDati(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo, String idImport) throws AppCrash{
   	
    	String inFile1 =  _applicationSrv.getRoot()+"import-cv/1.txt";
    	String inFile2 =  _applicationSrv.getRoot()+"import-cv/2.txt";
    	String inFile3 =  _applicationSrv.getRoot()+"import-cv/3.txt";
    	int lettoFile1 = 0;
    	int lettoFile2 = 0;
    	int lettoFile3 = 0;
    	int caricatoFile1 = 0;
    	int caricatoFile2 = 0;
    	int caricatoFile3 = 0;

    	
    	 
		File name1 = new File(inFile1);
		if (name1.isFile()) {
			try {
				BufferedReader input = new BufferedReader(new FileReader(name1));
	
				String stringa;
				String ditta="";
				String codice="";
				String nominativo="";
				String dataNascita="";
				String locNascita="";
				String codFiscale="";
				String indResidenza="";
				String civicoResidenza="";
				String catastoResidenza="";
				String localitaResidenza="";
				String capResidenza="";
				String indDomicil="";
				String civicoDomicil="";
				String catastoDomicil="";
				String catastoNascita="";
				String localitaDomic="";

				while(true) {
					stringa=input.readLine();
					if (stringa.startsWith("_____") || stringa.startsWith("PAGHE :") || stringa.startsWith("Richiesta") || stringa.contains("Assunzione Cessazione")  || stringa.contains("ELENCO DIPENDENTI")){
						continue;
					}
					if(stringa==null || stringa.startsWith("Numero dipendenti per ditta")){
						break;
					}
					if (stringa.trim().equals("")){
						continue;
					}
					
					if (stringa.startsWith("COD.DIP.") && stringa.length()<=63){
						req.getSession(false).setAttribute("TITOLI", stringa);
						continue;
					}
					
					if (stringa.startsWith("COD.DIP.") && stringa.length()>63){
						req.getSession(false).setAttribute("CARICATO", "NO");
						break;
					}
					
					// TXT1 - RIGA 0
					if (stringa.length()>22 && stringa.startsWith("Codice ditta")){
						ditta=stringa.substring(13,27).replace(" ","").trim().toUpperCase();
					}
					
					
					
					if (((String) req.getSession(false).getAttribute("TITOLI")).contains("VARIAZIONI")){
						// TXT1 - RIGA 1
						if (stringa.length()==75 && stringa.contains("D.Nascita")){
							codice=stringa.substring(0,12).replace(" ","");
							nominativo=stringa.substring(13,43).trim().toUpperCase();
							dataNascita=stringa.substring(64).trim().toUpperCase();
							lettoFile1=lettoFile1+1;
						}
						
						// TXT1 - RIGA 2
						if (stringa.length()>81 && stringa.length()<97 && stringa.contains("Com.Nasc.")){
							catastoNascita=stringa.substring(65,69).trim().toUpperCase();
							locNascita=stringa.substring(83).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 3
						if (stringa.length()==81 && stringa.contains("Cod.Fisc.")){
							codFiscale=stringa.substring(65).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 4
						if (stringa.length()>97 && stringa.length()<121 && stringa.contains("Res.ViaNr")){
							indResidenza=stringa.substring(71,112).trim().toUpperCase();
							civicoResidenza=stringa.substring(112).trim().toUpperCase();
						}

						// TXT1 - RIGA 5
						if (stringa.length()>81 && stringa.contains("Res.Com.")){
							catastoResidenza=stringa.substring(65,69).trim().toUpperCase();
							localitaResidenza=stringa.substring(70).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 6
						if (stringa.length()==70 && stringa.contains("Res.CAP")){
							capResidenza=stringa.substring(65).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 8
						if (stringa.length()>97 && stringa.length()<121 && stringa.contains("Cor.ViaNr")){
							indDomicil=stringa.substring(71,112).trim().toUpperCase();
							civicoDomicil=stringa.substring(112).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 10
						if (stringa.length()>81 && stringa.contains("Cor.Com.")){
							catastoDomicil=stringa.substring(65,69).trim().toUpperCase();
							localitaDomic=stringa.substring(70).trim().toUpperCase();
						}
					}else{
						// TXT1 - RIGA 1
						if (stringa.length()==64 && stringa.contains("D.Nascita")){
							codice=stringa.substring(0,12).replace(" ","");
							nominativo=stringa.substring(13,43).trim().toUpperCase();
							dataNascita=stringa.substring(53).trim().toUpperCase();
							lettoFile1=lettoFile1+1;
						}
						
						// TXT1 - RIGA 2
						if (stringa.length()>70 && stringa.length()<86 && stringa.contains("Com.Nasc.")){
							catastoNascita=stringa.substring(54,58).trim().toUpperCase();
							locNascita=stringa.substring(72).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 3
						if (stringa.length()==70 && stringa.contains("Cod.Fisc.")){
							codFiscale=stringa.substring(54).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 4
						if (stringa.length()>86 && stringa.length()<110 && stringa.contains("Res.ViaNr")){
							indResidenza=stringa.substring(60,101).trim().toUpperCase();
							civicoResidenza=stringa.substring(101).trim().toUpperCase();
						}

						// TXT1 - RIGA 5
						if (stringa.length()>70 && stringa.contains("Res.Com.")){
							catastoResidenza=stringa.substring(54,58).trim().toUpperCase();
							localitaResidenza=stringa.substring(59).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 6
						if (stringa.length()==59 && stringa.contains("Res.CAP")){
							capResidenza=stringa.substring(54).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 8
						if (stringa.length()>86 && stringa.length()<110 && stringa.contains("Cor.ViaNr")){
							indDomicil=stringa.substring(60,101).trim().toUpperCase();
							civicoDomicil=stringa.substring(101).trim().toUpperCase();
						}
						
						// TXT1 - RIGA 10
						if (stringa.length()>70 && stringa.contains("Cor.Com.")){
							catastoDomicil=stringa.substring(54,58).trim().toUpperCase();
							localitaDomic=stringa.substring(59).trim().toUpperCase();
						}
					}
					
					
					if (!codice.equals("")){
						
						DBTransaction dbTransaction = new DBTransaction();
						aggiornamento1(req, res, userInfo, dbTransaction, codice, ditta,nominativo, dataNascita, locNascita, codFiscale, indResidenza, civicoResidenza, catastoResidenza, localitaResidenza, capResidenza, indDomicil, civicoDomicil, catastoDomicil,catastoNascita,localitaDomic, caricatoFile1,idImport);
					}
			    }
				input.close();
				
			} catch (IOException ioException) {
			}
		}
		
		HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
		
			
			
			//FILE 2
			File name2 = new File(inFile2);
			if (name2.isFile()) {
				try {
					BufferedReader input = new BufferedReader(new FileReader(name2));
		
					String stringa;
					String ditta="";
					String codice="";
					String statoCivile="";
					String inail="";
					String titoloStudio="";
					String qualificaDipendente="";
					String capDomicil="";

					while(true) {
						stringa=input.readLine();
						if (stringa.startsWith("_____") || stringa.startsWith("PAGHE :") || stringa.startsWith("Richiesta") || stringa.contains("Assunzione Cessazione")  || stringa.contains("ELENCO DIPENDENTI")){
							continue;
						}
						if(stringa==null || stringa.startsWith("Numero dipendenti per ditta")){
							break;
						}
						if (stringa.trim().equals("")){
							continue;
						}
						
						if (stringa.startsWith("COD.DIP.") && stringa.length()<=63){
							req.getSession(false).setAttribute("TITOLI", stringa);
							continue;
						}
						
						if (stringa.startsWith("COD.DIP.") && stringa.length()>63){
							req.getSession(false).setAttribute("CARICATO", "NO");
							break;
						}
						
						// TXT2 - RIGA 0
						if (stringa.length()>22 && stringa.startsWith("Codice ditta")){
							ditta=stringa.substring(13,27).replace(" ","").trim().toUpperCase();
						}
						
						if (((String) req.getSession(false).getAttribute("TITOLI")).contains("VARIAZIONI")){
							// TXT2 - RIGA 1
							if (stringa.length()==70 && stringa.contains("Cor.CAP")){
								lettoFile2=lettoFile2+1;
								codice=stringa.substring(0,12).replace(" ","").toUpperCase();
								capDomicil=stringa.substring(65,70).trim().toUpperCase();
							}
							
							// TXT2 - RIGA 3
							if (stringa.length()>68 && stringa.contains("St.civile")){
								statoCivile= String.format("%03d",Integer.parseInt(stringa.substring(65,68).trim())).toUpperCase();
							}
							
							// TXT2 - RIGA 5
							if (stringa.length()==77 && stringa.contains("Nr. PAT")){
								inail=stringa.substring(65,77).trim().toUpperCase();
							}
							
							// TXT2 - RIGA 9
							if (stringa.length()>68 && stringa.contains("T.studio")){
								titoloStudio=String.format("%03d",Integer.parseInt(stringa.substring(65,68).trim())).toUpperCase();
							}
							
							// TXT2 - RIGA 10
							if (stringa.length()>68 && stringa.contains("Natura R.")){
								qualificaDipendente= String.format("%03d",Integer.parseInt(stringa.substring(65,68).trim())).toUpperCase();
							}
						}else{
							// TXT2 - RIGA 1
							if (stringa.length()==59 && stringa.contains("Cor.CAP")){
								lettoFile2=lettoFile2+1;
								codice=stringa.substring(0,12).replace(" ","").toUpperCase();
								capDomicil=stringa.substring(54,59).trim().toUpperCase();
							}
							
							// TXT2 - RIGA 3
							if (stringa.length()>57 && stringa.contains("St.civile")){
								statoCivile= String.format("%03d",Integer.parseInt(stringa.substring(54,57).trim())).toUpperCase();
							}
							
							// TXT2 - RIGA 5
							if (stringa.length()==66 && stringa.contains("Nr. PAT")){
								inail=stringa.substring(54,66).trim().toUpperCase();
							}
							
							// TXT2 - RIGA 9
							if (stringa.length()>57 && stringa.contains("T.studio")){
								titoloStudio=String.format("%03d",Integer.parseInt(stringa.substring(54,57).trim())).toUpperCase();
							}
							
							// TXT2 - RIGA 10
							if (stringa.length()>57 && stringa.contains("Natura R.")){
								qualificaDipendente= String.format("%03d",Integer.parseInt(stringa.substring(54,57).trim())).toUpperCase();
							}
						}
						
						
						if (!codice.equals("")){
							DBTransaction dbTransaction = new DBTransaction();
							aggiornamento2(req, res, userInfo, dbTransaction, codice, ditta,statoCivile, inail, titoloStudio, qualificaDipendente,capDomicil,caricatoFile2,idImport);
						}
						//System.out.println(stringa);
				    }
					input.close();
					
				} catch (IOException ioException) {
				}
			}
			
			
			
			//FILE 3
			File name3 = new File(inFile3);
			if (name3.isFile()) {
				try {
					BufferedReader input = new BufferedReader(new FileReader(name3));
		
					String stringa;
					String ditta="";
					String orarioLavoro="";
					String contratto="";
					String livello="";
					String dataAssunzione="";
					String qualifica="";
					String mansione="";
					String posInps="";
					String iban="";
					String codice="";
					String codiceCin="";
					String codiceChk="";
					String abi="";
					String cab="";
					String contoCorrente="";

					while(true) {
						stringa=input.readLine();
						if (stringa.startsWith("_____") || stringa.startsWith("PAGHE :") || stringa.startsWith("Richiesta") || stringa.startsWith("COD.DIP.") || stringa.contains("Assunzione Cessazione")  || stringa.contains("ELENCO DIPENDENTI")){
							continue;
						}
						if(stringa==null || stringa.startsWith("Numero dipendenti per ditta")){
							break;
						}
						if (stringa.trim().equals("")){
							continue;
						}
						
						// TXT3 - RIGA 0
						if (stringa.length()>22 && stringa.startsWith("Codice ditta")){
							ditta=stringa.substring(13,27).replace(" ","").trim().toUpperCase();
						}
						
						if (((String) req.getSession(false).getAttribute("TITOLI")).contains("VARIAZIONI")){
							// TXT3 - RIGA 1
							if (stringa.length()>69 && stringa.contains("Orario L.")){
								lettoFile3=lettoFile3+1;
								codice=stringa.substring(0,12).replace(" ","").toUpperCase();
								orarioLavoro=stringa.substring(65,68).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 2
							if (stringa.length()>70 && stringa.contains("Contratto")){
								contratto=stringa.substring(65,68).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 3
							if (stringa.length()>68 && stringa.contains("Livello")){
								livello=stringa.substring(65,77).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 4
							if (stringa.length()==75 && stringa.contains("D.Assunz.")){
								dataAssunzione=stringa.substring(65,75).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 5
							if (stringa.length()>67 && stringa.contains("Qualifica")){
								qualifica=stringa.substring(65,68).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 6
							if (stringa.length()>70 && stringa.contains("Mansione")){
								mansione=stringa.substring(65,68).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 7
							if (stringa.length()==78 && stringa.contains("Pos. INPS")){
								posInps=stringa.substring(65).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 8
							if (stringa.length()==92 && stringa.contains("IBAN")){
								iban=stringa.substring(65).trim().toUpperCase();
								codiceCin=stringa.substring(69,70).trim().toUpperCase();
								codiceChk=stringa.substring(67,69).trim().toUpperCase();
								abi=stringa.substring(70,75).trim().toUpperCase();
								cab=stringa.substring(75,80).trim().toUpperCase();
								contoCorrente=stringa.substring(80).trim().toUpperCase();
							}
						}else{
							// TXT3 - RIGA 1
							if (stringa.length()>58 && stringa.contains("Orario L.")){
								lettoFile3=lettoFile3+1;
								codice=stringa.substring(0,12).replace(" ","").toUpperCase();
								orarioLavoro=stringa.substring(54,57).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 2
							if (stringa.length()>59 && stringa.contains("Contratto")){
								contratto=stringa.substring(54,57).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 3
							if (stringa.length()>57 && stringa.contains("Livello")){
								livello=stringa.substring(54,66).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 4
							if (stringa.length()==64 && stringa.contains("D.Assunz.")){
								dataAssunzione=stringa.substring(54,64).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 5
							if (stringa.length()>56 && stringa.contains("Qualifica")){
								qualifica=stringa.substring(54,57).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 6
							if (stringa.length()>59 && stringa.contains("Mansione")){
								mansione=stringa.substring(54,57).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 7
							if (stringa.length()==67 && stringa.contains("Pos. INPS")){
								posInps=stringa.substring(54).trim().toUpperCase();
							}
							
							// TXT3 - RIGA 8
							if (stringa.length()==81 && stringa.contains("IBAN")){
								iban=stringa.substring(54).trim().toUpperCase();
								codiceCin=stringa.substring(58,59).trim().toUpperCase();
								codiceChk=stringa.substring(56,58).trim().toUpperCase();
								abi=stringa.substring(59,64).trim().toUpperCase();
								cab=stringa.substring(64,69).trim().toUpperCase();
								contoCorrente=stringa.substring(69).trim().toUpperCase();
							}
						}
						
						
						if (!codice.equals("")){
							DBTransaction dbTransaction = new DBTransaction();
							aggiornamento3(req, res, userInfo, dbTransaction, codice, ditta, orarioLavoro, contratto, livello, dataAssunzione, qualifica, mansione, posInps, iban,codiceCin,codiceChk,abi,cab,contoCorrente,caricatoFile3, idImport);
						}
				    }
					input.close();
					

					
				} catch (IOException ioException) {
				}
				
				
			}
			
			req.getSession(false).setAttribute("FILE1", lettoFile1);
			req.getSession(false).setAttribute("FILE2", lettoFile2);
			req.getSession(false).setAttribute("FILE3", lettoFile3);
		
 

    	
    	// cancello file cartelle non più utilizzate
        File cartellaUPLOAD = new File(_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.importcv"));
 		File[] filesUPLOAD = cartellaUPLOAD.listFiles();
 		for (File f : filesUPLOAD)
 		f.delete();
     	// fine cancellazione file cartelle non utilizzate
    }
    

    private void aggiornamento1(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo, DBTransaction dbTransaction, String codice, String ditta, String nominativo, String dataNascita, String locNascita, String codFiscale, String indResidenza, String civicoResidenza, String catastoResidenza, String localitaResidenza, String capResidenza, String indDomicil, String civicoDomicil, String catastoDomicil, String catastoNascita, String localitaDomic, int caricatoFile1, String idImport) throws AppCrash {

    	HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
    	templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
    	try {
	    	CurriculImportDAO cv = new CurriculImportDAO(dbTransaction);
	        cv.setField(CurriculImportDAO.CODICE, codice);
	        if (cv.retrieve()) {
	        	caricatoFile1=caricatoFile1+1;
	        	req.getSession(false).setAttribute("FILE_OK1", caricatoFile1);
		        cv.setField(CurriculImportDAO.AZIENDA_CV, ditta);
		        cv.setField(CurriculImportDAO.TAGGANCIO, Utils.getUnique());
		        cv.setField(CurriculImportDAO.CODICE, codice);
		        cv.setField(CurriculImportDAO.COGNOME, nominativo.substring(0,nominativo.lastIndexOf(" ")).trim());
		        cv.setField(CurriculImportDAO.NOME, nominativo.substring(nominativo.lastIndexOf(" ")).trim());
		        cv.setField(CurriculImportDAO.D_NASCITA, dataNascita);
		        cv.setField(CurriculImportDAO.CODFISCALE, codFiscale);
		        cv.setField(CurriculImportDAO.COD_CATASTO_RESID, catastoResidenza);
		        cv.setField(CurriculImportDAO.CAPRESIDENZ, capResidenza);
		        cv.setField(CurriculImportDAO.COD_CATASTO_DOMIC, catastoDomicil);
		        cv.setField(CurriculImportDAO.LOCNASCITA, locNascita);
		        cv.setField(CurriculImportDAO.LOCRESIDENZ, localitaResidenza);
		        cv.setField(CurriculImportDAO.CIVICO_RESID, civicoResidenza);
		        cv.setField(CurriculImportDAO.CIVICO_DOMIC, civicoDomicil);
		        cv.setField(CurriculImportDAO.INDRESIDENZ, indResidenza);
		        cv.setField(CurriculImportDAO.INDDOMICIL, indDomicil);
		        cv.setField(CurriculImportDAO.COD_CATASTO_NASCITA, catastoNascita);
		        cv.setField(CurriculImportDAO.LOCDOMICIL, localitaDomic);
		        cv.setField(CurriculImportDAO.D_DATAINS, Utils.getStringDataOggiRibaltata());
		        cv.setField(CurriculImportDAO.D_REGISTRAZ, Utils.getStringDataOggiRibaltata());
		        if (!codFiscale.equals("") && Integer.parseInt(codFiscale.substring(9,11))>40){
	            	cv.setField(CurriculImportDAO.SESSO, "F");
	            }else{
	            	cv.setField(CurriculImportDAO.SESSO, "M");
	            }
		        cv.setField(CurriculImportDAO.AZIENDA_INSERIMENTO, (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		        cv.update();
	        }else{
	        	caricatoFile1=caricatoFile1+1;
	        	req.getSession(false).setAttribute("FILE_OK1", caricatoFile1);
	        	cv.setField(CurriculImportDAO.AZIENDA_CV, ditta);
		        cv.setField(CurriculImportDAO.CODICE, codice);
		        cv.setField(CurriculImportDAO.COGNOME, nominativo.substring(0,nominativo.lastIndexOf(" ")).trim());
		        cv.setField(CurriculImportDAO.NOME, nominativo.substring(nominativo.lastIndexOf(" ")).trim());
		        cv.setField(CurriculImportDAO.D_NASCITA, dataNascita);
		        cv.setField(CurriculImportDAO.CODFISCALE, codFiscale);
		        cv.setField(CurriculImportDAO.COD_CATASTO_RESID, catastoResidenza);
		        cv.setField(CurriculImportDAO.CAPRESIDENZ, capResidenza);
		        cv.setField(CurriculImportDAO.COD_CATASTO_DOMIC, catastoDomicil);
		        cv.setField(CurriculImportDAO.LOCNASCITA, locNascita);
		        cv.setField(CurriculImportDAO.LOCRESIDENZ, localitaResidenza);
		        cv.setField(CurriculImportDAO.CIVICO_RESID, civicoResidenza);
		        cv.setField(CurriculImportDAO.CIVICO_DOMIC, civicoDomicil);
		        cv.setField(CurriculImportDAO.INDRESIDENZ, indResidenza);
		        cv.setField(CurriculImportDAO.INDDOMICIL, indDomicil);
		        cv.setField(CurriculImportDAO.COD_CATASTO_NASCITA, catastoNascita);
		        cv.setField(CurriculImportDAO.LOCDOMICIL, localitaDomic);
		        cv.setField(CurriculImportDAO.D_DATAMOD, Utils.getStringDataOggiRibaltata());
		        if (!codFiscale.equals("") && Integer.parseInt(codFiscale.substring(9,11))>40){
	            	cv.setField(CurriculImportDAO.SESSO, "F");
	            }else{
	            	cv.setField(CurriculImportDAO.SESSO, "M");
	            }
		        cv.setField(CurriculImportDAO.AZIENDA_INSERIMENTO, (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		        cv.setField(CurriculImportDAO.ID_IMPORT, idImport);
		        cv.insert();
	        }
	        
	        dbTransaction.commit();
    	 } catch (Throwable e) {
             if (dbTransaction != null) {
                 dbTransaction.rollBack();
             }
             new AppCrash(e).logContext("FileUploadAnagrafica:", "CARICAMENTO INTERROTTO - FILE 1 - riga codice " + codice);
         } finally {
             if (dbTransaction != null) {
                 dbTransaction.end();
             }
         }
    }
    
   
    
    private void aggiornamento2(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo, DBTransaction dbTransaction, String codice, String ditta, String statoCivile, String inail, String titoloStudio, String qualificaDipendente, String capDomicil, int caricatoFile2, String idImport) throws AppCrash {

    	HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
    	templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
    	try {
	    	CurriculImportDAO cv = new CurriculImportDAO(dbTransaction);
	        cv.setField(CurriculImportDAO.CODICE, codice);
	        if (cv.retrieve()) {
	        	caricatoFile2=caricatoFile2+1;
	        	req.getSession(false).setAttribute("FILE_OK1", caricatoFile2);
		        cv.setField(CurriculImportDAO.AZIENDA_CV, ditta);
		        cv.setField(CurriculImportDAO.TAGGANCIO, Utils.getUnique());
		        cv.setField(CurriculImportDAO.CODICE, codice);
		        cv.setField(CurriculImportDAO.IDSTATOCIV, statoCivile);
		        cv.setField(CurriculImportDAO.INAIL, inail);
		        cv.setField(CurriculImportDAO.TITOLO_STUDIO, titoloStudio);
		        cv.setField(CurriculImportDAO.TIPO_RAPPORTO, qualificaDipendente);
		        cv.setField(CurriculImportDAO.D_DATAINS, Utils.getStringDataOggiRibaltata());
		        cv.setField(CurriculImportDAO.CAPDOMICIL, capDomicil);
		        cv.setField(CurriculImportDAO.AZIENDA_INSERIMENTO, (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		        cv.setField(CurriculImportDAO.D_REGISTRAZ, Utils.getStringDataOggiRibaltata());
		        cv.update();
	        }else{
	        	caricatoFile2=caricatoFile2+1;
	        	req.getSession(false).setAttribute("FILE_OK1", caricatoFile2);
	        	cv.setField(CurriculImportDAO.AZIENDA_CV, ditta);
		        cv.setField(CurriculImportDAO.CODICE, codice);
		        cv.setField(CurriculImportDAO.IDSTATOCIV, statoCivile);
		        cv.setField(CurriculImportDAO.INAIL, inail);
		        cv.setField(CurriculImportDAO.TITOLO_STUDIO, titoloStudio);
		        cv.setField(CurriculImportDAO.TIPO_RAPPORTO, qualificaDipendente);
		        cv.setField(CurriculImportDAO.D_DATAMOD, Utils.getStringDataOggiRibaltata());
		        cv.setField(CurriculImportDAO.AZIENDA_INSERIMENTO, (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		        cv.setField(CurriculImportDAO.ID_IMPORT, idImport);
		        cv.insert();
	        }
	        
	        dbTransaction.commit();
    	 } catch (Throwable e) {
             if (dbTransaction != null) {
                 dbTransaction.rollBack();
             }
             new AppCrash(e).logContext("FileUploadAnagrafica:", "CARICAMENTO INTERROTTO - FILE 2 - riga codice " + codice);
         } finally {
             if (dbTransaction != null) {
                 dbTransaction.end();
             }
         }
    }
    
    
    
    
    private void aggiornamento3(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo, DBTransaction dbTransaction, String codice, String ditta, String orarioLavoro,String contratto,String livello,String dataAssunzione,String qualifica,String mansione,String posInps,String iban,String codiceCin,String codiceChk,String abi,String cab,String contoCorrente, int caricatoFile3, String idImport) throws AppCrash {

    	HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
    	templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
    	try {
	    	CurriculImportDAO cv = new CurriculImportDAO(dbTransaction);
	        cv.setField(CurriculImportDAO.CODICE, codice);
	        if (cv.retrieve()) {
	        	caricatoFile3=caricatoFile3+1;
	        	req.getSession(false).setAttribute("FILE_OK1", caricatoFile3);
		        cv.setField(CurriculImportDAO.AZIENDA_CV, ditta);
		        cv.setField(CurriculImportDAO.TAGGANCIO, Utils.getUnique());
		        cv.setField(CurriculImportDAO.CODICE, codice);
		        cv.setField(CurriculImportDAO.ORARIO_LAVORO, orarioLavoro);
		        cv.setField(CurriculImportDAO.LIVELLO, livello);
		        cv.setField(CurriculImportDAO.CCNL, contratto);
		        cv.setField(CurriculImportDAO.DATA_ASSUNZIONE, dataAssunzione);
		        cv.setField(CurriculImportDAO.QUALIFICA_DIPENDENTE, qualifica);
		        cv.setField(CurriculImportDAO.MANSIONE, mansione);
		        cv.setField(CurriculImportDAO.MATR_INPS, posInps);
		        cv.setField(CurriculImportDAO.CODICE_IBAN, iban);
		        cv.setField(CurriculImportDAO.CODICE_CIN, codiceCin);
		        cv.setField(CurriculImportDAO.CODICE_CHK, codiceChk);
		        cv.setField(CurriculImportDAO.CODICE_ABI, abi);
		        cv.setField(CurriculImportDAO.CODICE_CAB, cab);
		        cv.setField(CurriculImportDAO.CONTO_CORRENTE, contoCorrente);
		        cv.setField(CurriculImportDAO.AZIENDA_INSERIMENTO, (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		        cv.setField(CurriculImportDAO.D_REGISTRAZ, Utils.getStringDataOggiRibaltata());
		        cv.update();
	        }else{
	        	caricatoFile3=caricatoFile3+1;
	        	req.getSession(false).setAttribute("FILE_OK1", caricatoFile3);
	        	cv.setField(CurriculImportDAO.AZIENDA_CV, ditta);
		        cv.setField(CurriculImportDAO.CODICE, codice);
		        cv.setField(CurriculImportDAO.ORARIO_LAVORO, orarioLavoro);
		        cv.setField(CurriculImportDAO.LIVELLO, livello);
		        cv.setField(CurriculImportDAO.CCNL, contratto);
		        cv.setField(CurriculImportDAO.DATA_ASSUNZIONE, dataAssunzione);
		        cv.setField(CurriculImportDAO.QUALIFICA_DIPENDENTE, qualifica);
		        cv.setField(CurriculImportDAO.MANSIONE, mansione);
		        cv.setField(CurriculImportDAO.MATR_INPS, posInps);
		        cv.setField(CurriculImportDAO.CODICE_IBAN, iban);
		        cv.setField(CurriculImportDAO.CODICE_CIN, codiceCin);
		        cv.setField(CurriculImportDAO.CODICE_CHK, codiceChk);
		        cv.setField(CurriculImportDAO.CODICE_ABI, abi);
		        cv.setField(CurriculImportDAO.CODICE_CAB, cab);
		        cv.setField(CurriculImportDAO.CONTO_CORRENTE, contoCorrente);
		        cv.setField(CurriculImportDAO.AZIENDA_INSERIMENTO, (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		        cv.setField(CurriculImportDAO.ID_IMPORT, idImport);
		        cv.insert();
	        }
	        
	        dbTransaction.commit();
    	 } catch (Throwable e) {
             if (dbTransaction != null) {
                 dbTransaction.rollBack();
             }
             new AppCrash(e).logContext("FileUploadAnagrafica:", "CARICAMENTO INTERROTTO - FILE 3 - riga codice " + codice);
         } finally {
             if (dbTransaction != null) {
                 dbTransaction.end();
             }
         }
    }
    
    
    
    
    
    
    @SuppressWarnings({ "unchecked" })
    private HashMap inviaMail(String from, String new_pwd, String destinatari_mail, String nominativo) throws AppCrash {
	    String portale = Config.GetInstance().getProperty("indirizzo.portale");
        String elencoDestinatari = destinatari_mail;
        String oggetto = "";
        String corpo = "Gentile "+nominativo.toUpperCase()+",\n/nQuesta è una mail inviata automaticamente da DAFNE.\n/n";
        
    	oggetto="Nuovo utente DAFNE";
    	corpo += "E' stato creato il suo profilo utente per l'accesso a <a href='"+portale+"'>"+portale+"</a>, qui di seguito trova le credenziali per l'accesso al portale:\n" +
		"/n";
	    corpo += "<i>Userid</i>: " + destinatari_mail + "\n/n";
	    corpo += "<i>Password</i>: " + new_pwd + "\n/n";
	    corpo += "\n/nAcceda al sistema utilizzando la nuova password. Potrà modificarla con una nuova password utilizzando l'apposita pagina di Cambio Password.\n\n/n/nCordiali Saluti\n/n<i>Il Team DAFNE</i>";
	    System.out.println(nominativo.toUpperCase()+ " - " + elencoDestinatari);
	    System.out.println(elencoDestinatari);
        SendSMTPMail sendSMTPMail = new SendSMTPMail();
        sendSMTPMail.setFrom(from);
        sendSMTPMail.setSubject(oggetto);
        sendSMTPMail.setBody(corpo);
        sendSMTPMail.setTo(elencoDestinatari);
        sendSMTPMail.setServer(Config.GetInstance().getProperty("mail.SMTPHost"));

        try {
            MyAuthenticator auth = null;
            if (!Config.GetInstance().getProperty("mail.SMTPHost.user", "").equals("")) {
                auth = new MyAuthenticator();
            }
            sendSMTPMail.prepareMail(auth, false, "", "", "S");

            DeferredMailSender.getInstance().offer(sendSMTPMail);
            //templateData.put("EMAIL_INVIATA", "OK");
            //templateData.put("EMAIL_INVIATA_MESSAGE",
            //        Config.GetInstance().getProperty("Message.email_inviata_ok", NO_MESSAGE));

        } catch (Throwable e) {
            //templateData.put("EMAIL_INVIATA", "KO");
            //templateData.put("EMAIL_INVIATA_MESSAGE",
            //        Config.GetInstance().getProperty("Message.email_inviata_ko", NO_MESSAGE));
            new AppCrash(e);
        }
        
        return null;
    }
    
    
    
    
    private String[] getDatiDipendente(String taggancio) throws AppCrash {
        String[] dati = { "", "",""};
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DataSetDatiDipendente");
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("TAGGANCIO", taggancio);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
            	dati[0] = dbRow.getField("NOME").toString().trim();
            	dati[1] = dbRow.getField("COGNOME").toString().trim();
            	dati[2] = dbRow.getField("AZIENDA_CV").toString().trim();
            }
            dataSet.close();
        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
                }
            }
        }
        return dati;
    }
    
    
    private void cancellaFile() {
    	// cancello file cartelle non più utilizzate
        File cartellaUPLOAD = new File(_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.importcv"));
 		File[] filesUPLOAD = cartellaUPLOAD.listFiles();
 		for (File f : filesUPLOAD)
 		f.delete();
     	// fine cancellazione file cartelle non utilizzate
    }
    
    private void creaCartella() {
	    String cartella =_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.importcv");
	    new File(cartella).mkdir(); 
    }
}
