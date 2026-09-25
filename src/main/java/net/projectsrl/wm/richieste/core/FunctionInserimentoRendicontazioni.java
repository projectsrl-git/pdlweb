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
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.db.DbUtils;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.utils.Utils;
import net.projectsrl.wm.utils.WMUtils;

/**
 * FunctionInserimentoRendicontazioni
 * 
 */
public class FunctionInserimentoRendicontazioni extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_rendicontazioni";
	private String _datasetTestata="";
	
	public static final String PAGE_STAMPA_PDF = "stampa_rendicontazione_pdf";
	public static final String XML_NAME_PDF = "rendicontazionePDF.xml";
	public static final String XSL_NAME_PDF_COMPLETA = "rendicontazionePDF.xsl";	
	private static final String DATASET_TIPO_APPROVAZIONE = "DataSetTipoApprovazioneR";
	private static final String DATASET_PARAAPPTUTTI = "DataSetParaAppTuttiRendicontazioni";
	private static final String DATASET_UTENTI = "DataSetUtenti";
	private static final String DATASET_LOGO = "DataSetAllegatiLogo";
	

	public FunctionInserimentoRendicontazioni() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetRendicontazioni");
	}

	public FunctionInserimentoRendicontazioni(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetRendicontazioni");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);  
		
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			
			templateData.put("NOMINATIVO", (String) req.getSession(false).getAttribute("NOMINATIVO_SESSIONE"));
			templateData.put("DATA_NASCITA", (String) req.getSession(false).getAttribute("DATA_NASCITA_SESSIONE"));
			templateData.put("CITTA_NASCITA", (String) req.getSession(false).getAttribute("CITTA_NASCITA_SESSIONE"));
			templateData.put("PROV_NASCITA", (String) req.getSession(false).getAttribute("PROV_NASCITA_SESSIONE"));
			templateData.put("INDIR_RESIDENZA", (String) req.getSession(false).getAttribute("INDIR_RESIDENZA_SESSIONE"));
			templateData.put("CITTA_RESIDENZA", (String) req.getSession(false).getAttribute("CITTA_RESIDENZA_SESSIONE"));
			templateData.put("CAP_RESIDENZA", (String) req.getSession(false).getAttribute("CAP_RESIDENZA_SESSIONE"));
			templateData.put("COD_FISCALE", (String) req.getSession(false).getAttribute("COD_FISCALE_SESSIONE"));
			templateData.put("ID_RENDICONTAZIONE", Utils.getUnique());
			
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
		putIdInSession(req, templateData);
		
		String dirModuliRendicontazioni=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.moduli.rendicontazioni");
        templateData.put("CARTELLA_MODULI_RENDICONTAZIONI", dirModuliRendicontazioni);
        String dirModuliRendicontazioniBreve=Config.GetInstance().getProperty("cartella.moduli.rendicontazioni");
        templateData.put("CARTELLA_MODULI_RENDICONTAZIONI_BREVE", dirModuliRendicontazioniBreve);
        
        String dirAllegatiRendicontazioni=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.upload.rendicontazioni");
        templateData.put("CARTELLA_RENDICONTAZIONI", dirAllegatiRendicontazioni);
         String dirRendicontazioniBreve=Config.GetInstance().getProperty("cartella.upload.rendicontazioni");
        templateData.put("CARTELLA_RENDICONTAZIONI_BREVE", dirRendicontazioniBreve);
        
		return templateData;

	}
	
	
	@SuppressWarnings("unchecked")
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		HashMap templateData = (HashMap) setCommonTags(req, userInfo);

		if (refresh(PAGE,req, templateData, res)) {
			return;
		}
		
	    // pulisce template dettagli
		//
		templateData = pulisceTuttiTemplateDettagli(templateData);
        
		templateData = saveVarStandard(templateData, req, res);
		
		templateData=nessunaOpzioniDettagli(templateData);
		templateData.put("RUOLO_SESSIONE", getSessionRole(req));
		templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		templateData.put("SALVATO", "Salvataggio effettuato con successo");
		templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
		
		String dirModuliRendicontazioni=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.moduli.rendicontazioni");
        templateData.put("CARTELLA_MODULI_RENDICONTAZIONI", dirModuliRendicontazioni);
        String dirModuliRendicontazioniBreve=Config.GetInstance().getProperty("cartella.moduli.rendicontazioni");
        templateData.put("CARTELLA_MODULI_RENDICONTAZIONI_BREVE", dirModuliRendicontazioniBreve);
        String dirAzienda =_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.moduli.rendicontazioni")+req.getField("AZIENDA_TENDINA")+"/";
        new File(dirAzienda).mkdir();
        String idDipendente=templateData.get("DIPENDENTE").toString();
        String dirDipendente =dirAzienda+ idDipendente+"/";
        new File(dirDipendente).mkdir();
        String dirModulo =dirDipendente+ req.getField("ID_RENDICONTAZIONE")+"/";
        new File(dirModulo).mkdir();
        
        String dirAllegatiRendicontazioni=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.upload.rendicontazioni");
        templateData.put("CARTELLA_RENDICONTAZIONI", dirAllegatiRendicontazioni);
         String dirRendicontazioniBreve=Config.GetInstance().getProperty("cartella.upload.rendicontazioni");
        templateData.put("CARTELLA_RENDICONTAZIONI_BREVE", dirRendicontazioniBreve);
        new File(dirAllegatiRendicontazioni).mkdir();
        String dirDipendente2 =dirAllegatiRendicontazioni+ idDipendente+"/";
        new File(dirDipendente2).mkdir();
        String dirModulo2 =dirDipendente2+ req.getField("ID_RENDICONTAZIONE")+"/";
        new File(dirModulo2).mkdir();
        
        
		
		String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");
        String nominativo = req.getSession(false).getAttribute("USER_NOME")+" "+req.getSession(false).getAttribute("USER_COGNOME");
        
        String idAziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
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
        
        String queryUpdateApprovatori="UPDATE RENDICONTAZIONI SET "
    			+ "LIV1_1='',LIV1_2='',LIV1_3='',LIV1_4='',LIV1_5='', "
    			+ "LIV2_1='',LIV2_2='',LIV2_3='',LIV2_4='',LIV2_5='', "
    			+ "LIV3_1='',LIV3_2='',LIV3_3='',LIV3_4='',LIV3_5='', "
    			+ "LIV4_1='',LIV4_2='',LIV4_3='',LIV4_4='',LIV4_5='', "
    			+ "LIV5_1='',LIV5_2='',LIV5_3='',LIV5_4='',LIV5_5='', "
    			+ "SPESE_VIAGGIO_IMPORTO_A=0.00, "
    			+ "SPESE_TRASPORTO_IMPORTO_A=0.00, "
    			+ "SPESE_PARCHEGGIO_IMPORTO_A=0.00, "
    			+ "SPESE_VITTO_IMPORTO_A=0.00, "
    			+ "SPESE_ALLOGGIO_IMPORTO_A=0.00, "
    			+ "SPESE_PEDAGGI_IMPORTO_A=0.00, "
    			+ "SPESE_ALTRO_IMPORTO_A=0.00, "
    			+ "SPESE_TOTALE_IMPORTO_A=0.00, "
    			+ " APPROVAZIONE = '', ADMIN = '' WHERE ID_RENDICONTAZIONE='"+req.getField("ID_RENDICONTAZIONE")+"'";
        net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovatori);
        
		
		String tipoApprovazione=getTipoApprovazione(idDipendente); 
        if (tipoApprovazione.equals("A")){
        	String queryUpdateApprovatoriA="UPDATE RENDICONTAZIONI SET "
        			+ "LIV1_1='S',LIV1_2='S',LIV1_3='S',LIV1_4='S',LIV1_5='S', "
        			+ "LIV2_1='S',LIV2_2='S',LIV2_3='S',LIV2_4='S',LIV2_5='S', "
        			+ "LIV3_1='S',LIV3_2='S',LIV3_3='S',LIV3_4='S',LIV3_5='S', "
        			+ "LIV4_1='S',LIV4_2='S',LIV4_3='S',LIV4_4='S',LIV4_5='S', "
        			+ "LIV5_1='S',LIV5_2='S',LIV5_3='S',LIV5_4='S',LIV5_5='S', "
        			+ " APPROVAZIONE = 'S' WHERE ID_RENDICONTAZIONE='"+req.getField("ID_RENDICONTAZIONE")+"'";
	        net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovatoriA);
	        String strSpeseViaggio = req.getField("SPESE_VIAGGIO_IMPORTO");
	        String strSpeseTrasporto = req.getField("SPESE_TRASPORTO_IMPORTO");
	        String strSpeseParcheggio = req.getField("SPESE_PARCHEGGIO_IMPORTO");
	        String strSpeseVitto = req.getField("SPESE_VITTO_IMPORTO");
	        String strSpeseAlloggio = req.getField("SPESE_ALLOGGIO_IMPORTO");
	        String strSpesePedaggi = req.getField("SPESE_PEDAGGI_IMPORTO");
	        String strSpeseAltro = req.getField("SPESE_ALTRO_IMPORTO");
	        String rendicontazione = req.getField("ID_RENDICONTAZIONE");
	        String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_VIAGGIO_IMPORTO_A="+strSpeseViaggio+", SPESE_TRASPORTO_IMPORTO_A="+strSpeseTrasporto+", SPESE_PARCHEGGIO_IMPORTO_A="+strSpeseParcheggio+", SPESE_VITTO_IMPORTO_A="+strSpeseVitto+", SPESE_ALLOGGIO_IMPORTO_A="+strSpeseAlloggio+", SPESE_PEDAGGI_IMPORTO_A="+strSpesePedaggi+", SPESE_ALTRO_IMPORTO_A="+strSpeseAltro+" WHERE ID_RENDICONTAZIONE ='"+rendicontazione+"'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
			String sqlUpdate2 ="UPDATE RENDICONTAZIONI SET SPESE_TOTALE_IMPORTO_A=(SPESE_ALLOGGIO_IMPORTO_A+SPESE_ALTRO_IMPORTO_A+SPESE_PARCHEGGIO_IMPORTO_A+SPESE_PEDAGGI_IMPORTO_A+SPESE_TRASPORTO_IMPORTO_A+SPESE_VIAGGIO_IMPORTO_A+SPESE_VITTO_IMPORTO_A) WHERE ID_RENDICONTAZIONE ='"+rendicontazione+"'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
        }
        
        if(req.getField(OPZIONE_INSERIMENTO_MODIFICA).equals(OPZIONE_INSERIMENTO) || req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
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
		
		String fileName="Rendicontazione_"+templateData.get("ID_RENDICONTAZIONE");
		salvaFormatoPDFInDiversiModuli(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParamPDF);

		_applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req,templateData), res);

		
	}
	
	
	protected @SuppressWarnings("unchecked") void salvaFormatoPDFInDiversiModuli(String fileName, String page_stampa2, HashMap templateData, Map[] dataSourceParam) throws AppCrash {
		salvaFormatoPDF(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParam,XML_NAME_PDF,XSL_NAME_PDF_COMPLETA);
	}
	
	 @SuppressWarnings("unchecked")
		protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {
		 String anno			= req.getField("ANNO_RIMBORSO");
	        String mese			= req.getField("MESE_RIMBORSO"); 
	        String data			= Utils.ribaltaData(req.getField("DATA_RIMBORSO"));  
	        String stato		= req.getField("STATO_APPROVAZIONE"); 
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
	        	str_composed_where_cond = str_composed_where_cond + " AND RENDICONTAZIONI.AZIENDA_TENDINA = '"+azienda+"'";
	        }
	        if (!dipendente.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND DIPENDENTE = '"+dipendente+"'";
	        }
	        
	        if (!stato.trim().equalsIgnoreCase("")) {
	        	if (stato.trim().equals("S")) {
	        		str_composed_where_cond = str_composed_where_cond + " AND STATO_APPROVAZIONE = 'S'";
	        	} 
	        	if (stato.trim().equals("N")) {
	        		str_composed_where_cond = str_composed_where_cond + " AND STATO_APPROVAZIONE = 'N'";
	        	} 
	        	if (stato.trim().equals("A")) {
	        		str_composed_where_cond = str_composed_where_cond + " AND STATO_APPROVAZIONE = 'A'";
	        	} 
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
	
	
	@SuppressWarnings("unchecked")
	private HashMap pulisceTuttiTemplateDettagli(HashMap templateData) throws AppCrash {
		
		if (!ciSonoDettagli()) {
			return templateData;
		}		
		
		String[] arr = getArrayDsDettagli();

		for (int i = 0; i < arr.length; i++) {
			String dsName=arr[i];
			
			clearTemplateFieldFromDAO(templateData,DbUtils.makeDAOFromDsName(dsName));
			
		}
		
		return templateData;
	}

	
	
	@SuppressWarnings("unchecked")
	protected HashMap elaboraTuttiDettagli(SsbServletRequest req, HashMap templateData) throws AppCrash {
		
		String[] arr = getArrayDsDettagli();

		for (int i = 0; i < arr.length; i++) {
			String dsName=arr[i];
			
			templateData=elaboraSingoloDettaglio(i,dsName,templateData,req);
			
		}
		
		return templateData;
		
	}	
	
	private String[] getArrayDsDettagli() {
		String elencoDataSet=Config.GetInstance().getProperty("DS." + _datasetTestata + ".ElencoDSDettagli");
		String[] arr = elencoDataSet.split("\\,");
		return arr;
	}

	
	@SuppressWarnings("unchecked")
    private void putIdInSession(SsbServletRequest req, HashMap templateData) {

        String idRendicontazione = (String) templateData.get("ID_RENDICONTAZIONE");
        if (idRendicontazione == null || idRendicontazione.equals("")) {
        	idRendicontazione=req.getField("ID_RENDICONTAZIONE");  
            if (idRendicontazione == null || idRendicontazione.equals("")) {
                return;
            }            
        }
        
        req.getSession(false).setAttribute(Costanti_itf.ID_RENDICONTAZIONE_SESSIONE, idRendicontazione);   
        templateData.put("AZIENDA_UPLOAD",(String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI"));
		templateData.put("MATRICOLA_UPLOAD", (String) req.getSession(false).getAttribute("MATRICOLA"));
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
	
	@SuppressWarnings({ "unchecked" })
    private HashMap inviaMail(SsbServletRequest req, HashMap templateData, String from, String destinatari_mail, String nominativo) throws AppCrash {
	    String portale = Config.GetInstance().getProperty("indirizzo.portale");
        String elencoDestinatari = destinatari_mail;
        
        
        String oggetto="";
        if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
        	oggetto = "Modifica rendicontazione DAFNE";
        }else{
        	oggetto = "Nuova rendicontazione DAFNE";
        }
        
        
        String corpo = "Gentile utente,\n/nQuesta è una mail inviata automaticamente da DAFNE.\n/n";
        if (req.getField("RIPARTE_ITER_APPROVAZIONE").equals("SI")){
        	corpo += "E' stata modificata una rendicontazione da "+nominativo+".\n"+"/n";
        }else{
        	corpo += "E' stata inserita una rendicontazione da "+nominativo+".\n"+"/n";
        }
    	corpo += "Per poter visualizzare e gestire la rendicontazione cliccare <a href='"+portale+"'>qui</a>\n"+"/n";
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

