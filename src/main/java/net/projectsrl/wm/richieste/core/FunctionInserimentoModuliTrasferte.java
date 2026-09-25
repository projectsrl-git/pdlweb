package net.projectsrl.wm.richieste.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.utils.Utils;
import net.projectsrl.wm.utils.WMUtils;

/**
 * FunctionInserimentoTrasferte
 * 
 */
public class FunctionInserimentoModuliTrasferte extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_modulitrasferte";
	private static final String DATASET_UTENTI = "DataSetUtenti";
	private static final String DATASET_LOGO = "DataSetAllegatiLogo";
	
	public static final String PAGE_STAMPA_PDF = "stampa_modulitrasferte_pdf";
	public static final String XML_NAME_PDF = "modulitrasfertePDF.xml";
	public static final String XSL_NAME_PDF_COMPLETA = "modulitrasfertePDF.xsl";
	

	public FunctionInserimentoModuliTrasferte() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetModuliTrasferte");
	}

	public FunctionInserimentoModuliTrasferte(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetModuliTrasferte");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);        
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_MODTRASFERTA", Utils.getUnique());
			if (getSessionRole(req).equals("D")){
				templateData.put("DIPENDENTE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
				templateData.put("AZIENDA", WMUtils.getDatiDipendente((String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE")));
			}else{
				templateData.put("AZIENDA", WMUtils.getDatiDipendente(req.getField("ID_DIPENDENTE")));
			}
			if (templateData.get("AZIENDA_TENDINA")==null || templateData.get("AZIENDA_TENDINA").equals("")){
				templateData.put("AZIENDA_TENDINA", WMUtils.getDatiDipendente((String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE")));
			}
		}
		
		String dirModuliTrasferte=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.moduli.trasferte");
        templateData.put("CARTELLA_MODULI_TRASFERTE", dirModuliTrasferte);
        String dirModuliTrasferteBreve=Config.GetInstance().getProperty("cartella.moduli.trasferte");
        templateData.put("CARTELLA_MODULI_TRASFERTE_BREVE", dirModuliTrasferteBreve);
        
		return templateData;

	}

	
	
	 @SuppressWarnings("unchecked")
		public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
	        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
	        saveVarStandard(templateData, req,res);
	        templateData.put("RUOLO_SESSIONE", getSessionRole(req));
			templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
	        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
			templateData.put("SALVATO", "Salvataggio effettuato con successo");
			templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
			
			String dirModuliTrasferte=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.moduli.trasferte");
	        templateData.put("CARTELLA_MODULI_TRASFERTE", dirModuliTrasferte);
	        String dirModuliTrasferteBreve=Config.GetInstance().getProperty("cartella.moduli.trasferte");
        	templateData.put("CARTELLA_MODULI_TRASFERTE_BREVE", dirModuliTrasferteBreve);
			String dirAzienda =_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.moduli.trasferte")+req.getField("AZIENDA_TENDINA")+"/";
	        new File(dirAzienda).mkdir();
	        String idDipendente=templateData.get("DIPENDENTE").toString();
	        String dirDipendente =dirAzienda+ idDipendente+"/";
	        new File(dirDipendente).mkdir();
	        String dirModulo =dirDipendente+ req.getField("ID_MODTRASFERTA")+"/";
            new File(dirModulo).mkdir();
	        
	        String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");
	        String nominativo = req.getSession(false).getAttribute("USER_NOME")+" "+req.getSession(false).getAttribute("USER_COGNOME");
	        
	        String mail1=getMailDipendente(req.getField("DIPENDENTE"));
	        
	        String destinatari_mail = "";
	        if(!mail1.equals(";")){
	        	destinatari_mail=destinatari_mail+mail1;
	        }
	        
	        if(req.getField(OPZIONE_INSERIMENTO_MODIFICA).equals(OPZIONE_INSERIMENTO)){
	        	 templateData = inviaMail(req, templateData, from, destinatari_mail, nominativo);
	        }
	       
	        Date currentTime = new Date();
			SimpleDateFormat formatterTimestamp = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
			String strDataOra = formatterTimestamp.format(currentTime);
			
			
			templateData.put("aggiornato_a",strDataOra);
			String logoAzienda=getLogo(req.getField("AZIENDA_TENDINA"));
		    String logo = _applicationSrv.getRoot() + "areadocumenti\\"+req.getField("AZIENDA_TENDINA")+"\\"+logoAzienda;
		    if (logoAzienda.equals("")){
	        	logo=_applicationSrv.getRoot() + "img\\logo\\white_logo.jpg";
        	}
		    templateData.put("logo",logo);
			
			Map dataSourceParamPDF[]=setPageDatasetParam(PAGE_STAMPA_PDF, req, templateData);
			
			String fileName="Modulitrasferte_"+templateData.get("ID_MODTRASFERTA");		
			salvaFormatoPDFInDiversiModuli(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParamPDF);

	        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
		 }
	 
	 	protected void salvaFormatoPDFInDiversiModuli(String fileName, String page_stampa2, HashMap templateData, Map[] dataSourceParam) throws AppCrash {
			salvaFormatoPDF(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParam,XML_NAME_PDF,XSL_NAME_PDF_COMPLETA);
		}
	 	
	 	@SuppressWarnings("unchecked")
		protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {
		 String anno			= req.getField("ANNO_RIMBORSO");
	        String mese			= req.getField("MESE_RIMBORSO"); 
	        String data			= Utils.ribaltaData(req.getField("DATA_RIMBORSO"));  
	        String descrizione	= req.getField("DESCRIZIONE");
	        String note			= req.getField("NOTE");
	        String azienda		= req.getField("AZIENDA_TENDINA");
	        String dipendente	= req.getField("DIPENDENTE");
	        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

	        String str_composed_where_cond 	= "";
	        
	        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
	    		str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
	        }

	        if (!anno.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND SUBSTRING(DATA_RIMBORSO,1,4) = '"+anno+"'";
	        }
	        if (!mese.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND SUBSTRING(DATA_RIMBORSO,6,2) = '%"+mese+"'";
	        }
	        if (!data.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND DATA_RIMBORSO = '"+data+"'";
	        }
	        if (!descrizione.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND DESCRIZIONE like '%"+descrizione+"%'";
	        }
	        if (!note.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND NOTE like '%"+note+"%'";
	        }
	        if (!azienda.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND MOD_TRASFERTE.AZIENDA_TENDINA = '"+azienda+"'";
	        }
	        if (!dipendente.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND DIPENDENTE = '"+dipendente+"'";
	        }
  
			str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
	        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
	        	if (str_composed_where_cond.startsWith("(")){
	        		str_composed_where_cond=" ("+str_composed_where_cond.substring(4);
	        	}else{
	        		str_composed_where_cond=" "+str_composed_where_cond.substring(4);
	        	}
	        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
	        }

			queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
	        
	    	return queryParameter;
	        
	 }
		 
		 
		 @SuppressWarnings({ "unchecked" })
		    private HashMap inviaMail(SsbServletRequest req, HashMap templateData, String from, String destinatari_mail, String nominativo) throws AppCrash {
			    String portale = Config.GetInstance().getProperty("indirizzo.portale");
		        String elencoDestinatari = destinatari_mail;
		        
		       
		        String oggetto = "Nuovo ordine trasferta DAFNE";
		        
		        String corpo = "Gentile utente,\n/nQuesta è una mail inviata automaticamente da DAFNE.\n/n";

		        corpo += "E' stata inserito un nuovo ordine di trasferta da "+nominativo+".\n"+"/n";
	        	corpo += "Per poter visualizzare e gestire la richiesta cliccare <a href='"+portale+"'>qui</a>\n"+"/n";
			    corpo += "\n/nCordiali Saluti\n/n<i>Il Team DAFNE</i>";
		        
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
                    templateData.put("EMAIL_INVIATA", "OK");
                    templateData.put("EMAIL_INVIATA_MESSAGE",
                            Config.GetInstance().getProperty("Message.email_inviata_ok", NO_MESSAGE));

                } catch (Throwable e) {
                    templateData.put("EMAIL_INVIATA", "KO");
                    templateData.put("EMAIL_INVIATA_MESSAGE",
                            Config.GetInstance().getProperty("Message.email_inviata_ko", NO_MESSAGE));
                    new AppCrash(e);
                }
                
                return templateData;
		    }
		 
		 
		 
		 private String getMailDipendente(String idDipendente) throws AppCrash {
		        String dati = "";
		        String idTrovato="";
		        DataSet_itf dataSet = null;
		        try {
		            DataSetFactory dsFactory = DataSetFactory.getInstance();
		            dsFactory = DataSetFactory.getInstance();
		            dataSet = dsFactory.makeDataSet("", DATASET_UTENTI);
		            HashMap<String, String> params = new HashMap<String, String>();
		            params.put("ID_DIPENDENTE", idDipendente);
		            dataSet.setParam(params);
		            dataSet.open();
		            while (dataSet.hasMoreElements()) {
		                Row_itf dbRow = (Row_itf) dataSet.nextElement();
		                idTrovato = dbRow.getField("ID_DIPENDENTE").toString().trim();
		                if (idDipendente.equals(idTrovato)){
		                	dati = dbRow.getField("EMAIL").toString().trim();
		                }
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
		 
		
		 
		 private String getLogo(String aziendaLogo) throws AppCrash {
		        String dati = "";
		        String idTrovato="";
		        DataSet_itf dataSet = null;
		        try {
		            DataSetFactory dsFactory = DataSetFactory.getInstance();
		            dsFactory = DataSetFactory.getInstance();
		            dataSet = dsFactory.makeDataSet("", DATASET_LOGO);
		            HashMap<String, String> params = new HashMap<String, String>();
		            params.put("AZIENDA", aziendaLogo);
		            dataSet.setParam(params);
		            dataSet.open();
		            while (dataSet.hasMoreElements()) {
		                Row_itf dbRow = (Row_itf) dataSet.nextElement();
		                idTrovato = dbRow.getField("AZIENDA").toString().trim();
		                if (aziendaLogo.equals(idTrovato)){
		                	dati = dbRow.getField("FILENAME").toString().trim();
		                }
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


}

