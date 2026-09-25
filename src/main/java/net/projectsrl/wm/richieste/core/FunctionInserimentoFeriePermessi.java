package net.projectsrl.wm.richieste.core;

import java.io.IOException;
import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.utils.Utils;
import net.projectsrl.wm.utils.WMUtils;

/**
 * FunctionInserimentoRimborsi
 * 
 */
public class FunctionInserimentoFeriePermessi extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_feriepermessi";
	private static final String DATASET_PARAAPPTUTTI = "DataSetParaAppTutti";
	private static final String DATASET_UTENTI = "DataSetUtenti";
	private static final String DATASET_FP = "DataSetGSTFeriePermessi";
	private static final String DATASET_TIPO_APPROVAZIONE = "DataSetTipoApprovazioneFP";
	private static final String DATASET_FP_DESCRI = "DataSetGSTFeriePermessiDescri";
	

	public FunctionInserimentoFeriePermessi() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetFeriePermessi");
	}

	public FunctionInserimentoFeriePermessi(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetFeriePermessi");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);  
		
		
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_FERIEPERMESSO", Utils.getUnique());
			templateData.put("ORE", getDatiPermesso(req.getField("TIPO_PERMESSO"))[0]);
			templateData.put("DOCUMENTAZIONE", getDatiPermesso(req.getField("TIPO_PERMESSO"))[1]);
			
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
		
		String dirAllegatiFp=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.upload.richieste");
        templateData.put("CARTELLA_FERIE_PERMESSI", dirAllegatiFp);
        String dirAllegatiFpBreve=Config.GetInstance().getProperty("cartella.upload.richieste");
    	templateData.put("CARTELLA_FERIE_PERMESSI_BREVE", dirAllegatiFpBreve);
		return templateData;

	}
	
	
	private String[] getDatiPermesso(String codPermesso) throws AppCrash {
        String[] dati = { "", ""};
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_FP);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("CODICE", codPermesso);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("CODICE").toString().trim();
                if (codPermesso.equals(idTrovato)){
                	String oreMinime=dbRow.getField("ORE_MINIME").toString().trim();
                	String documentazione=dbRow.getField("DOC").toString().trim();
                	dati[0] = oreMinime;
                	dati[1] = documentazione;
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
	
	
	
	private String[] getApprovatori(String idAziendaSessione, String idDipendente) throws AppCrash {
        String[] dati = { "", "", "", "", ""};
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_PARAAPPTUTTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("AZIENDA_DIP_APP", "'"+idAziendaSessione+"' AND ID_RICHIEDENTE='"+idDipendente+"'");
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
            	String idApprovatore1_1=dbRow.getField("A_1").toString().trim();
            	String idApprovatore1_2=dbRow.getField("A_2").toString().trim();
            	String idApprovatore1_3=dbRow.getField("A_3").toString().trim();
            	String idApprovatore1_4=dbRow.getField("A_4").toString().trim();
            	String idApprovatore1_5=dbRow.getField("A_5").toString().trim();
            	dati[0] = getMailApprovatori(idApprovatore1_1);
            	dati[1] = getMailApprovatori(idApprovatore1_2);
            	dati[2] = getMailApprovatori(idApprovatore1_3);
            	dati[3] = getMailApprovatori(idApprovatore1_4);
            	dati[4] = getMailApprovatori(idApprovatore1_5);
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
	
	
	private String getMailApprovatori(String idApprovatore) throws AppCrash {
        String dati = "";
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_UTENTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE", idApprovatore);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("ID_DIPENDENTE").toString().trim();
                if (idApprovatore.equals(idTrovato)){
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
	
	
	
	 @SuppressWarnings("unchecked")
		public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
	        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
	        saveVarStandard(templateData, req,res);
	        String dirAllegatiFp=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.upload.richieste");
	        templateData.put("CARTELLA_FERIE_PERMESSI", dirAllegatiFp);
	        String dirAllegatiFpBreve=Config.GetInstance().getProperty("cartella.upload.richieste");
    		templateData.put("CARTELLA_FERIE_PERMESSI_BREVE", dirAllegatiFpBreve);
	        templateData.put("RUOLO_SESSIONE", getSessionRole(req));
			templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
	        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
			templateData.put("SALVATO", "Salvataggio effettuato con successo");
			templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());

	        String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");
	        String nominativo = req.getSession(false).getAttribute("USER_NOME")+" "+req.getSession(false).getAttribute("USER_COGNOME");
	        
	        String idAziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
	        String idDipendente = req.getField("DIPENDENTE");
	        String mail1=getApprovatori(idAziendaSessione,idDipendente)[0]+";";
	        String mail2=getApprovatori(idAziendaSessione,idDipendente)[1]+";";
	        String mail3=getApprovatori(idAziendaSessione,idDipendente)[2]+";";
	        String mail4=getApprovatori(idAziendaSessione,idDipendente)[3]+";";
	        String mail5=getApprovatori(idAziendaSessione,idDipendente)[4];
	        
	        String destinatari_mail = "";
	        if(!mail1.equals(";")){
	        	destinatari_mail=destinatari_mail+mail1;
	        }
	        if(!mail2.equals(";")){
	        	destinatari_mail=destinatari_mail+mail2;
	        }
	        if(!mail3.equals(";")){
	        	destinatari_mail=destinatari_mail+mail3;
	        }
	        if(!mail4.equals(";")){
	        	destinatari_mail=destinatari_mail+mail4;
	        }
	        if(!mail5.equals(";")){
	        	destinatari_mail=destinatari_mail+mail5;
	        }
	        
	        String queryUpdateApprovatori="UPDATE FERIE_PERMESSI SET "
        			+ "LIV1_1='',LIV1_2='',LIV1_3='',LIV1_4='',LIV1_5='', "
        			+ "LIV2_1='',LIV2_2='',LIV2_3='',LIV2_4='',LIV2_5='', "
        			+ "LIV3_1='',LIV3_2='',LIV3_3='',LIV3_4='',LIV3_5='', "
        			+ "LIV4_1='',LIV4_2='',LIV4_3='',LIV4_4='',LIV4_5='', "
        			+ "LIV5_1='',LIV5_2='',LIV5_3='',LIV5_4='',LIV5_5='', "
        			+ " APPROVAZIONE = '', ADMIN = '' WHERE ID_FERIEPERMESSO='"+req.getField("ID_FERIEPERMESSO")+"'";
	        net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovatori);

	        
	        if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
	        	String queryUpdateApprovazione="UPDATE FERIE_PERMESSI SET "
	        			+ "LIV1_1='',LIV1_2='',LIV1_3='',LIV1_4='',LIV1_5='', "
	        			+ "LIV2_1='',LIV2_2='',LIV2_3='',LIV2_4='',LIV2_5='', "
	        			+ "LIV3_1='',LIV3_2='',LIV3_3='',LIV3_4='',LIV3_5='', "
	        			+ "LIV4_1='',LIV4_2='',LIV4_3='',LIV4_4='',LIV4_5='', "
	        			+ "LIV5_1='',LIV5_2='',LIV5_3='',LIV5_4='',LIV5_5='', "
	        			+ " APPROVAZIONE = '', ADMIN = '' WHERE ID_FERIEPERMESSO='"+req.getField("ID_FERIEPERMESSO")+"'";
				try {
					net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovazione);
				} catch (AppCrash e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        String tipoApprovazione=getTipoApprovazione(idDipendente); 
	        if (tipoApprovazione.equals("A")){
	        	String queryUpdateApprovatoriA="UPDATE FERIE_PERMESSI SET "
	        			+ "LIV1_1='S',LIV1_2='S',LIV1_3='S',LIV1_4='S',LIV1_5='S', "
	        			+ "LIV2_1='S',LIV2_2='S',LIV2_3='S',LIV2_4='S',LIV2_5='S', "
	        			+ "LIV3_1='S',LIV3_2='S',LIV3_3='S',LIV3_4='S',LIV3_5='S', "
	        			+ "LIV4_1='S',LIV4_2='S',LIV4_3='S',LIV4_4='S',LIV4_5='S', "
	        			+ "LIV5_1='S',LIV5_2='S',LIV5_3='S',LIV5_4='S',LIV5_5='S', "
	        			+ " APPROVAZIONE = 'S' WHERE ID_FERIEPERMESSO='"+req.getField("ID_FERIEPERMESSO")+"'";
		        net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovatoriA);
	        }
	        
	       	        
	        if (req.getField("REVOCATO").equals("S")){
	        	String queryDelete="delete from TIMESHT where idrisumana = '"+req.getField("DIPENDENTE")+"' and idcommessa='00000000000000000000' and data between '"+req.getField("DATA_DAL")+"' and '"+req.getField("DATA_AL")+"' and compcomm='"+req.getField("TIPO_PERMESSO")+"'";
				try {
					net.projectsrl.wm.utils.WMUtils.executeQuery(queryDelete);
				} catch (AppCrash e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        if(req.getField(OPZIONE_INSERIMENTO_MODIFICA).equals(OPZIONE_INSERIMENTO) || req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
	        	 templateData = inviaMail(req, templateData, from, destinatari_mail, nominativo);
	        }

	        
	       
	        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
		 }
	 
	 
		 private String getTipoApprovazione(String idDipendente) throws AppCrash {
		        String dati = "";
		        DataSet_itf dataSet = null;
		        try {
		            DataSetFactory dsFactory = DataSetFactory.getInstance();
		            dsFactory = DataSetFactory.getInstance();
		            dataSet = dsFactory.makeDataSet("", DATASET_TIPO_APPROVAZIONE);
		            HashMap<String, String> params = new HashMap<String, String>();
		            params.put("ID_DIPENDENTE", idDipendente);
		            dataSet.setParam(params);
		            dataSet.open();
		            while (dataSet.hasMoreElements()) {
		                Row_itf dbRow = (Row_itf) dataSet.nextElement();
		               	dati = dbRow.getField("APPROVAZIONE").toString().trim();
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
		 
		 
		 
		 private String getDescriFeriePermesso(String idFeriePermesso) throws AppCrash {
             String dati = "";
             DataSet_itf dataSet = null;
             try {
                 DataSetFactory dsFactory = DataSetFactory.getInstance();
                 dsFactory = DataSetFactory.getInstance();
                 dataSet = dsFactory.makeDataSet("", DATASET_FP_DESCRI);
                 HashMap<String, String> params = new HashMap<String, String>();
                 params.put("CODICE", idFeriePermesso);
                 dataSet.setParam(params);
                 dataSet.open();
                 while (dataSet.hasMoreElements()) {
                     Row_itf dbRow = (Row_itf) dataSet.nextElement();
                     dati = dbRow.getField("DESCRI").toString().trim();
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
		 
		 
		 @SuppressWarnings({ "unchecked" })
		    private HashMap inviaMail(SsbServletRequest req, HashMap templateData, String from, String destinatari_mail, String nominativo) throws AppCrash {
		        String elencoDestinatari = destinatari_mail;
			    
			    String oggetto="";
			    if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
                    oggetto = "Modifica richiesta ferie/permessi DAFNE";
                }else{
                    oggetto = "Nuova richiesta ferie/permessi DAFNE";
                }
		        
		        String corpo="";
                try {
                    corpo = leggiHtml();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
		        
                corpo=corpo.replace("#NOME#","utente");
                
                
		        if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
		            corpo=corpo.replace("#TESTO_1#","E' stata modificata una richiesta di ferie/permessi da "+nominativo);
                }else{
                    corpo=corpo.replace("#TESTO_1#","E' stata inserita una nuova richiesta di ferie/permessi da "+nominativo);
                }
		         
		        corpo=corpo.replace("#TESTO_2#","Tipo permesso/ferie: "+getDescriFeriePermesso(req.getField("TIPO_PERMESSO")));
		        corpo=corpo.replace("#TESTO_3#","Dal "+req.getField("DATA_DAL")+ " al "+req.getField("DATA_AL"));
		        
		        if (!req.getField("ORE_DAL").equals("") && !req.getField("ORE_AL").equals("")){
		            corpo=corpo.replace("#TESTO_4#","Orario: dalle "+req.getField("ORE_DAL")+ " alle "+req.getField("ORE_AL"));
		        }else{
		            corpo=corpo.replace("#TESTO_4#","");
		        }
		        
		        if (!req.getField("ORE").equals("")){
		            corpo=corpo.replace("#TESTO_5#", "Ore: "+req.getField("ORE"));
		        }else{
		            corpo=corpo.replace("#TESTO_5#", "");
		        }
		        
		        corpo=corpo.replace("#URL#", "Per poter visualizzare e gestire la richiesta <a href='"+Config.GetInstance().getProperty("indirizzo.portale")+"'> clicca qui </a>");
		        corpo=corpo.replace("#FIRMA#", "Dafne - Data Flow Network System");
		        corpo=corpo.replace("#FOOTER1#", "Dafne Data Flow Network System || Vers. 1.0.0 © Studio Nebbiolo 2014 || All Rights Reserved");
		        corpo=corpo.replace("#FOOTER2#", "");
		        
		        
		        
		        
		        
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

}

