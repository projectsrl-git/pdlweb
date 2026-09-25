package net.projectsrl.wm.richieste.core;

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
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.utils.Utils;
import net.projectsrl.wm.utils.WMUtils;

/**
 * FunctionInserimentoStraordinari
 * 
 */
public class FunctionInserimentoStraordinari extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_straordinari";
	private static final String DATASET_PARAAPPTUTTI = "DataSetParaAppTutti";
	private static final String DATASET_UTENTI = "DataSetUtenti";
	private static final String DATASET_STRAORDINARI= "DataSetGSTStraordinari";
	private static final String DATASET_TIPO_APPROVAZIONE = "DataSetTipoApprovazione";
	

	public FunctionInserimentoStraordinari() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetStraordinari");
	}

	public FunctionInserimentoStraordinari(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetStraordinari");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);  
		
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_STRAORDINARIO", Utils.getUnique());
			templateData.put("ORE", getDatiStraordinario(req.getField("TIPO_PERMESSO"))[0]);
			templateData.put("DOCUMENTAZIONE", getDatiStraordinario(req.getField("TIPO_PERMESSO"))[1]);
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
		return templateData;

	}
	
	
	
	
	private String[] getDatiStraordinario(String codStraordinario) throws AppCrash {
        String[] dati = { "", ""};
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_STRAORDINARI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("CODICE", codStraordinario);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("CODICE").toString().trim();
                if (codStraordinario.equals(idTrovato)){
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
	
	
	
	 @SuppressWarnings("unchecked")
		public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
	        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
	        saveVarStandard(templateData, req,res);
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
	        
	        
	        String queryUpdateApprovatori="UPDATE STRAORDINARI SET "
        			+ "LIV1_1='',LIV1_2='',LIV1_3='',LIV1_4='',LIV1_5='', "
        			+ "LIV2_1='',LIV2_2='',LIV2_3='',LIV2_4='',LIV2_5='', "
        			+ "LIV3_1='',LIV3_2='',LIV3_3='',LIV3_4='',LIV3_5='', "
        			+ "LIV4_1='',LIV4_2='',LIV4_3='',LIV4_4='',LIV4_5='', "
        			+ "LIV5_1='',LIV5_2='',LIV5_3='',LIV5_4='',LIV5_5='', "
        			+ " APPROVAZIONE = '', ADMIN = '' WHERE ID_STRAORDINARIO='"+req.getField("ID_STRAORDINARIO")+"'";
	        net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovatori);

	        
	        if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
	        	String queryUpdateApprovazione="UPDATE STRAORDINARI SET "
        			+ "LIV1_1='',LIV1_2='',LIV1_3='',LIV1_4='',LIV1_5='', "
        			+ "LIV2_1='',LIV2_2='',LIV2_3='',LIV2_4='',LIV2_5='', "
        			+ "LIV3_1='',LIV3_2='',LIV3_3='',LIV3_4='',LIV3_5='', "
        			+ "LIV4_1='',LIV4_2='',LIV4_3='',LIV4_4='',LIV4_5='', "
        			+ "LIV5_1='',LIV5_2='',LIV5_3='',LIV5_4='',LIV5_5='', "
        			+ " APPROVAZIONE = '', ADMIN = '' WHERE ID_STRAORDINARIO='"+req.getField("ID_STRAORDINARIO")+"'";
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovazione);
	        }
	        
	        String tipoApprovazione=getTipoApprovazione(idDipendente); 
	        if (tipoApprovazione.equals("A")){
	        	String queryUpdateApprovatoriA="UPDATE STRAORDINARI SET "
	        			+ "LIV1_1='S',LIV1_2='S',LIV1_3='S',LIV1_4='S',LIV1_5='S', "
	        			+ "LIV2_1='S',LIV2_2='S',LIV2_3='S',LIV2_4='S',LIV2_5='S', "
	        			+ "LIV3_1='S',LIV3_2='S',LIV3_3='S',LIV3_4='S',LIV3_5='S', "
	        			+ "LIV4_1='S',LIV4_2='S',LIV4_3='S',LIV4_4='S',LIV4_5='S', "
	        			+ "LIV5_1='S',LIV5_2='S',LIV5_3='S',LIV5_4='S',LIV5_5='S', "
	        			+ " APPROVAZIONE = 'S' WHERE ID_STRAORDINARIO='"+req.getField("ID_STRAORDINARIO")+"'";
		        net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovatoriA);
	        }
	        
	        if(req.getField(OPZIONE_INSERIMENTO_MODIFICA).equals(OPZIONE_INSERIMENTO) || req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
	        	 templateData = inviaMail(req, templateData, from, destinatari_mail, nominativo);
	        }

	       
	        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
		 }
		 
		 
		 @SuppressWarnings({ "unchecked" })
		    private HashMap inviaMail(SsbServletRequest req, HashMap templateData, String from, String destinatari_mail, String nominativo) throws AppCrash {
			    String portale = Config.GetInstance().getProperty("indirizzo.portale");
		        String elencoDestinatari = destinatari_mail;
		        
		        String oggetto="";
		        if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
		        	oggetto = "Modifica richiesta straordinari DAFNE";
		        }else{
		        	oggetto = "Nuova richiesta straordinari DAFNE";
		        }
		        
		        
		        String corpo = "Gentile utente,\n/nQuesta è una mail inviata automaticamente da DAFNE.\n/n";
		        if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
		        	corpo += "E' stata modificata una richiesta di straordinari da "+nominativo+".\n"+"/n";
		        }else{
		        	corpo += "E' stata inserita una nuova richiesta di straordinari da "+nominativo+".\n"+"/n";
		        }
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

}

