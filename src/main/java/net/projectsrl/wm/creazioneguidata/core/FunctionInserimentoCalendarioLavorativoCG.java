package net.projectsrl.wm.creazioneguidata.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoCalendarioLavorativo
 * 
 */
public class FunctionInserimentoCalendarioLavorativoCG extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_calendariolavorativo_cg";
	private static final String DATASET_CALENDARIO_LAVORATIVO = "DataSetCalendarioLavorativo";

	public FunctionInserimentoCalendarioLavorativoCG() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetCalendarioLavorativo");
	}

	public FunctionInserimentoCalendarioLavorativoCG(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetCalendarioLavorativo");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			if (templateData.get("ANNO")==null){
				templateData.put("ANNO", "");
			}
			
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_RIGA", Utils.getUnique());
			return templateData;
		}
		return templateData;
	}
	
	
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
       
		String idAzienda=req.getField("AZIENDA");
		String anno = req.getField("ANNO");
		String annoReplica = req.getField("ANNO_2");
		if (annoReplica.equals("")){
			annoReplica=anno;
		}
		
		while (Integer.parseInt(annoReplica)>= Integer.parseInt(anno)){
				
			
			String data = "";
			String mese = "";
			String giorno = "";
			int giorni=0;
			int giorniAnno=0;
			float divisione=Integer.parseInt(anno)%4;
			if (divisione==0){
				giorniAnno=366;
			}else{
				giorniAnno=365;
			}
			
			String esiste=getCalendario(idAzienda,anno);
			if (!esiste.equals("S")){
				int y=1;
				for (int i=1;i<=giorniAnno;i++){
					giorno=Integer.toString(i);
					giorni=i;
					
				
					if (giorniAnno==365){
						if (giorni<32){
							mese="01";
							if (giorni==1){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
						}
						if (giorni>31 && giorni<60){
							if (giorni==32){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="02";
						}
						if (giorni>59 && giorni<91){
							if (giorni==60){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="03";
						}
						if (giorni>90 && giorni<121){
							if (giorni==91){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="04";
						}
						if (giorni>120 && giorni<152){
							if (giorni==121){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="05";
						}
						if (giorni>151 && giorni<182){
							if (giorni==152){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="06";
						}
						if (giorni>181 && giorni<213){
							if (giorni==182){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="07";
						}
						if (giorni>212 && giorni<244){
							if (giorni==213){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="08";
						}
						if (giorni>243 && giorni<274){
							if (giorni==244){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="09";
						}
						if (giorni>273 && giorni<305){
							if (giorni==274){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="10";
						}
						if (giorni>304 && giorni<335){
							if (giorni==305){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="11";
						}
						if (giorni>334){
							if (giorni==335){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="12";
						}
					}
					if (giorniAnno==366){
						if (giorni<32){
							giorno=Integer.toString(y);
							mese="01";
							if (giorni==1){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
						}
						if (giorni>31 && giorni<61){
							if (giorni==32){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="02";
						}
						if (giorni>60 && giorni<92){
							if (giorni==61){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="03";
						}
						if (giorni>91 && giorni<122){
							if (giorni==92){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="04";
						}
						if (giorni>121 && giorni<153){
							if (giorni==122){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="05";
						}
						if (giorni>152 && giorni<183){
							if (giorni==153){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="06";
						}
						if (giorni>182 && giorni<214){
							if (giorni==183){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="07";
						}
						if (giorni>213 && giorni<245){
							if (giorni==214){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="08";
						}
						if (giorni>244 && giorni<275){
							if (giorni==245){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="09";
						}
						if (giorni>274 && giorni<306){
							if (giorni==275){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="10";
						}
						if (giorni>305 && giorni<336){
							if (giorni==306){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="11";
						}
						if (giorni>335){
							if (giorni==336){
								y=1;
							}
							giorno=Integer.toString(y);
							y++;
							mese="12";
						}
					}
					data = anno+"/"+mese+"/"+String.format("%02d", Integer.parseInt(giorno));
					
					String giornoDateString=String.format("%02d", Integer.parseInt(giorno));
					String dateString = String.format("%d-%d-%d", Integer.parseInt(anno), Integer.parseInt(mese), Integer.parseInt(giornoDateString));
					Date date = null;
					try {
						date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					giorno = new SimpleDateFormat("EEEE", Locale.ITALY).format(date);
					giorno=giorno.replace("ì", "i");
						
					// millisecondi per il GANTT
					String millisecondi="";
					//long millisec = date.getTime()-86400000;
					long millisec = date.getTime();
					millisecondi= String.valueOf(millisec);
					
					String sqlInsertData = "INSERT INTO CALENDARIO_LAVORATIVO (ID_RIGA,AZIENDA,ANNO,DATA,GIORNO,MILLISECONDI) VALUES (" +
				 		"'"+anno+data.replace("/", "")+idAzienda+"','"+idAzienda+"','"+anno+"','"+data+"','"+giorno+"','"+millisecondi+"')";
					net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsertData);
					
					
					// update data non lavorativa pre-impostata non ricorsiva
					String sqlUpdateData =" UPDATE CALENDARIO_LAVORATIVO SET DESCRIZIONE = (SELECT DNL.DESCRIZIONE FROM DATE_NON_LAVORATIVE AS DNL "
							+ "LEFT OUTER JOIN CALENDARIO_LAVORATIVO AS CA ON CA.DATA=DNL.DATA "
							+ "WHERE '"+data+"'=CA.DATA AND AZIENDA = '"+idAzienda+"') WHERE DATA IN (SELECT DATA FROM DATE_NON_LAVORATIVE) and CALENDARIO_LAVORATIVO.data = '"+data+"' AND AZIENDA = '"+idAzienda+"'";
					net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateData);
					
					// update data non lavorativa pre-impostata ricorsiva
					sqlUpdateData =" UPDATE CALENDARIO_LAVORATIVO SET DESCRIZIONE = (SELECT DNL.DESCRIZIONE FROM DATE_NON_LAVORATIVE AS DNL "
							+ "LEFT OUTER JOIN CALENDARIO_LAVORATIVO AS CA ON substring(CA.DATA,6,len(CA.data))=substring(DNL.DATA,6,len(DNL.data)) "
							+ "WHERE '"+data.substring(5)+"'=substring(CA.DATA,6,len(CA.data)) AND AZIENDA = '"+idAzienda+"') WHERE substring(DATA,6,len(data)) IN (SELECT substring(DATA,6,len(data)) FROM DATE_NON_LAVORATIVE) and CALENDARIO_LAVORATIVO.data = '"+data.substring(5)+"' AND AZIENDA = '"+idAzienda+"'";
					net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateData);
				}
			}
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_MODIFICA);
			req.setField(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_MODIFICA);
			
			
			if (req.getField("LUNEDI").equals("S")){
				String sqlUpdateLunedi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='S' WHERE GIORNO='lunedi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateLunedi);
			}else{
				String sqlUpdateLunedi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='' WHERE GIORNO='lunedi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateLunedi);
			}
			
			if (req.getField("MARTEDI").equals("S")){
				String sqlUpdateMartedi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='S' WHERE GIORNO='martedi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateMartedi);
			}else{
				String sqlUpdateMartedi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='' WHERE GIORNO='martedi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateMartedi);
			}
			
			if (req.getField("MERCOLEDI").equals("S")){
				String sqlUpdateMercoledi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='S' WHERE GIORNO='mercoledi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateMercoledi);
			}else{
				String sqlUpdateMercoledi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='' WHERE GIORNO='mercoledi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateMercoledi);
			}
			
			if (req.getField("GIOVEDI").equals("S")){
				String sqlUpdateGiovedi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='S' WHERE GIORNO='giovedi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateGiovedi);
			}else{
				String sqlUpdateGiovedi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='' WHERE GIORNO='giovedi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateGiovedi);
			}
			
			if (req.getField("VENERDI").equals("S")){
				String sqlUpdateVenerdi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='S' WHERE GIORNO='venerdi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateVenerdi);
			}else{
				String sqlUpdateVenerdi = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='' WHERE GIORNO='venerdi' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateVenerdi);
			}
			
			if (req.getField("SABATO").equals("S")){
				String sqlUpdateSabato = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='S' WHERE GIORNO='sabato' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateSabato);
			}else{
				String sqlUpdateSabato = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='' WHERE GIORNO='sabato' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateSabato);
			}
			
			if (req.getField("DOMENICA").equals("S")){
				String sqlUpdateDomenica = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='S' WHERE GIORNO='domenica' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateDomenica);
			}else{
				String sqlUpdateDomenica = "UPDATE CALENDARIO_LAVORATIVO SET NON_LAVORATIVO='' WHERE GIORNO='domenica' AND ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' AND DESCRIZIONE=''";
				net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateDomenica);
			}
			
			
			
			
			// range date inizio
			
				if(req.getField("DATA_DAL").equals(req.getField("DATA_AL"))){
					
					String updateGiorno = "UPDATE CALENDARIO_LAVORATIVO SET DESCRIZIONE = '"+req.getField("DESCRIZIONE")+"', FESTIVO = '"+req.getField("FESTIVO")+"', NON_LAVORATIVO='"+req.getField("NON_LAVORATIVO")+"', CHIUSURA_AZIENDALE='"+req.getField("CHIUSURA_AZIENDALE")+"', FESTA_PATRONALE='"+req.getField("FESTA_PATRONALE")+"' where  ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' and data = '"+Utils.ribaltaData(req.getField("DATA_DAL"))+"'";
		     	    try {
						net.projectsrl.wm.utils.WMUtils.executeQuery(updateGiorno);
					} catch (AppCrash e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		     	    if (req.getField("FESTA_PATRONALE").equals("S")){
			     	    String updateGiorno2 = "UPDATE CALENDARIO_LAVORATIVO SET FESTIVO = 'S', NON_LAVORATIVO='S', FESTA_PATRONALE='"+req.getField("FESTA_PATRONALE")+"' where  ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' and data = '"+Utils.ribaltaData(req.getField("DATA_DAL"))+"'";
			     	    try {
							net.projectsrl.wm.utils.WMUtils.executeQuery(updateGiorno2);
						} catch (AppCrash e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		     	    }
	
				}else{
					String dataDalCompleta = req.getField("DATA_DAL");
			    	String dataAlCompleta = req.getField("DATA_AL");
			    	int mesi=0;
			    	int i =0;
			    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
			    	Date d1 = null;
			    	Date d2 = null;
			    	try {
			    	    d1 = format.parse(dataDalCompleta);
			    	    d2 = format.parse(dataAlCompleta);
			    	} catch (ParseException e) {
			    	    e.printStackTrace();
			    	}    
			    	long diff = d2.getTime() - d1.getTime();
			    	long diffDays = diff / (60 * 60 * 1000 * 24);    
			    	int fine = (int) diffDays+1;
		
			    	
			    	
			    	Calendar startCalendar = new GregorianCalendar();
			    	startCalendar.setTime(d1);
			    	Calendar endCalendar = new GregorianCalendar();
			    	endCalendar.setTime(d2);
		
			    	int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			    	mesi = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		
			    	
			    	String dataDaScrivere=dataDalCompleta;
			    	while (i<fine){
			    		
			    		String updateGiorno = "UPDATE CALENDARIO_LAVORATIVO SET DESCRIZIONE = '"+req.getField("DESCRIZIONE")+"', FESTIVO = '"+req.getField("FESTIVO")+"', NON_LAVORATIVO='"+req.getField("NON_LAVORATIVO")+"', CHIUSURA_AZIENDALE='"+req.getField("CHIUSURA_AZIENDALE")+"', FESTA_PATRONALE='"+req.getField("FESTA_PATRONALE")+"' where  AZIENDA='"+idAzienda+"' and data = '"+Utils.ribaltaData(dataDaScrivere)+"'"; 
		         		try {
							net.projectsrl.wm.utils.WMUtils.executeQuery(updateGiorno);
						} catch (AppCrash e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		         		if (req.getField("FESTA_PATRONALE").equals("S")){
				     	    String updateGiorno2 = "UPDATE CALENDARIO_LAVORATIVO SET FESTIVO = 'S', NON_LAVORATIVO='S', FESTA_PATRONALE='"+req.getField("FESTA_PATRONALE")+"' where  ANNO ='"+anno+"' AND AZIENDA='"+idAzienda+"' and data = '"+Utils.ribaltaData(req.getField("DATA_DAL"))+"'";
				     	    try {
								net.projectsrl.wm.utils.WMUtils.executeQuery(updateGiorno2);
							} catch (AppCrash e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			     	    }
		         	    
		         	    String prossimoGiorno="";
		         	    String prossimoMese=dataDaScrivere.substring(3,5);
		         	    String prossimoAnno=dataDaScrivere.substring(6);
		         	    
		         	    
		         	    if (mesi>=1){
			         	    if ((Integer.parseInt(dataDaScrivere.substring(0,2))+1)>Integer.parseInt(Utils.getUltimoGiornoMese(dataDaScrivere.substring(3,5), dataDaScrivere.substring(6)))){
			         	    	prossimoGiorno="01";
			         	    	if ((Integer.parseInt(dataDaScrivere.substring(3,5))+1)>12){
			         	    		prossimoMese="01";
			         	    		prossimoAnno=Integer.toString(Integer.parseInt(prossimoAnno)+1);
			         	    	}else{
			         	    		prossimoMese=Integer.toString(Integer.parseInt(prossimoMese)+1);
			         	    	}
			         	    }else{
			         	    	prossimoGiorno=Integer.toString(Integer.parseInt(dataDaScrivere.substring(0,2))+1);
			         	    }
		         	    }else{        	    
		         	    	prossimoGiorno=Integer.toString(Integer.parseInt(dataDaScrivere.substring(0,2))+1);
		         	    }
		         	    
		         	    
		
		         	    if (prossimoGiorno.equals("1") || prossimoGiorno.equals("2") || prossimoGiorno.equals("3") || prossimoGiorno.equals("4") || prossimoGiorno.equals("5") || prossimoGiorno.equals("6") || prossimoGiorno.equals("7") || prossimoGiorno.equals("8") || prossimoGiorno.equals("9")){
		         	    	prossimoGiorno="0"+prossimoGiorno;
		         	    }
		         	    
		         	    if (prossimoMese.equals("1") || prossimoMese.equals("2") || prossimoMese.equals("3") || prossimoMese.equals("4") || prossimoMese.equals("5") || prossimoMese.equals("6") || prossimoMese.equals("7") || prossimoMese.equals("8") || prossimoMese.equals("9")){
		         	    	prossimoMese="0"+prossimoMese;
		         	    }
		         	    
		         	    if (prossimoAnno.equals("1") || prossimoAnno.equals("2") || prossimoAnno.equals("3") || prossimoAnno.equals("4") || prossimoAnno.equals("5") || prossimoAnno.equals("6") || prossimoAnno.equals("7") || prossimoAnno.equals("8") || prossimoAnno.equals("9")){
		         	    	prossimoAnno="0"+prossimoAnno;
		         	    }
		
		
		         	    dataDaScrivere=prossimoGiorno+"/"+prossimoMese+"/"+prossimoAnno;
			    		++i;
			    	}
			    	
			    
				}
			
			// range date fine
				
				
			// update per aggiornare date non lavorative-festive standard per un solo anno
			String update1st= "UPDATE CALENDARIO_LAVORATIVO SET FESTIVO='S', NON_LAVORATIVO='S' WHERE DATA IN (SELECT DATA FROM DATE_NON_LAVORATIVE)";
			net.projectsrl.wm.utils.WMUtils.executeQuery(update1st);
			update1st= "UPDATE CALENDARIO_LAVORATIVO SET FESTIVO='S', NON_LAVORATIVO='S' WHERE SUBSTRING (DATA,6,LEN(DATA)) IN (SELECT SUBSTRING (DATA,6,LEN(DATA)) FROM DATE_NON_LAVORATIVE WHERE RICORSIVA='S')";
			net.projectsrl.wm.utils.WMUtils.executeQuery(update1st);
			
			
			anno=String.valueOf(Integer.parseInt(anno)+1).trim();
		}		
		// commentato luca 25-02-2014
		//saveVarStandard(templateData, req,res);
		templateData.put("SALVATO", "Salvataggio effettuato con successo");
		templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
		templateData.put("DATA_DAL","");
		templateData.put("DATA_AL","");
		templateData.put("ID_RIGA","");
		templateData.put("DESCRIZIONE","");
		templateData.put("FESTIVO","");
		templateData.put("NON_LAVORATIVO","");
		templateData.put("FESTIVO","");
		templateData.put("CHIUSURA_AZIENDALE","");
		templateData.put("FESTA_PATRONALE","");
		
		// per upload in configurazione guidata
		if (req.getField("AVANTI").equals("NO")){
			templateData.put("OPZIONE_INSERIMENTO_MODIFICA", OPZIONE_INSERIMENTO);
		}else{
			templateData.put("OPZIONE_INSERIMENTO_MODIFICA", OPZIONE_MODIFICA);
		}
				
        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
	}
	
	

	private String getCalendario(String idAzienda,String anno) throws AppCrash {
        String esiste = "";
        String idTrovato="";
        String annoTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_CALENDARIO_LAVORATIVO);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("AZIENDA", idAzienda);
            params.put("ANNO", anno);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("AZIENDA").toString().trim();
                annoTrovato = dbRow.getField("ANNO").toString().trim();
                if (idAzienda.equals(idTrovato) && anno.equals(annoTrovato)){
                	esiste = "S";
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
        return esiste;
    }
	
	
}

