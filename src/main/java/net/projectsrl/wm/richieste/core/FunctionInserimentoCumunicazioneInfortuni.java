package net.projectsrl.wm.richieste.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import net.projectsrl.db.DbUtils;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoRendicontazioni
 * 
 */
public class FunctionInserimentoCumunicazioneInfortuni extends FunctionInserimento {

	private static final String PAGE = "inserimento_comunicazioneinfortuni";
	private static final String DATASET_DIPENDENTI = "DataSetDipendenti";
	private String _datasetTestata="";
	
	public static final String PAGE_STAMPA_PDF = "stampa_comunicazioneinfortuni_pdf";
	public static final String XML_NAME_PDF = "comunicazioneinfortuniPDF.xml";
	public static final String XSL_NAME_PDF_COMPLETA = "comunicazioneinfortuniPDF.xsl";	
	private static final String DATASET_LOGO = "DataSetAllegatiLogo";
	

	public FunctionInserimentoCumunicazioneInfortuni() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetRendicontazioni");
	}

	public FunctionInserimentoCumunicazioneInfortuni(ApplicationServices_itf applServices, String functionID, String functionName) {

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
			if(getSessionRole(req).equals("D")){
				templateData.put("AZIENDA_TENDINA",(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
				templateData.put("DIPENDENTE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
				putIdInSession(req, templateData);
				return templateData;
			}
		}
		putIdInSession(req, templateData);
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

		templateData.put("MATRICOLA_DIPENDENTE",getMatricolaDipendente(req.getField("DIPENDENTE")));
        req.setField("MATRICOLA_DIPENDENTE", getMatricolaDipendente(req.getField("DIPENDENTE")));
        
		templateData = saveVarStandard(templateData, req, res);
		
		templateData=nessunaOpzioniDettagli(templateData);
		templateData.put("RUOLO_SESSIONE", getSessionRole(req));
		templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		templateData.put("SALVATO", "Salvataggio effettuato con successo");
		templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
		
		
		//pdf
		
		Date currentTime = new Date();
		SimpleDateFormat formatterTimestamp = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
		String strDataOra = formatterTimestamp.format(currentTime);
		
		HttpSession session = req.getSession(true);
		templateData.put("aggiornato_a",strDataOra);
	    String logoAzienda=getLogo(req.getField("AZIENDA_TENDINA"));
	    String logo = _applicationSrv.getRoot() + "areadocumenti\\"+req.getField("AZIENDA_TENDINA")+"\\"+logoAzienda;
	    if (logoAzienda.equals("")){
	        	logo=_applicationSrv.getRoot() + "img\\logo\\white_logo.jpg";
        	}
	    templateData.put("logo",logo);
		
		Map dataSourceParamPDF[]=setPageDatasetParam(PAGE_STAMPA_PDF, req, templateData);
		
		String fileName="Comunicazioneinfortuni_"+templateData.get("ID_RENDICONTAZIONE");
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
	
	
	private String getMatricolaDipendente(String idDipendente) throws AppCrash {
        String dati = "";
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_DIPENDENTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE", idDipendente);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("ID_DIPENDENTE").toString().trim();
                if (idDipendente.equals(idTrovato)){
                	dati = dbRow.getField("MATRICOLA").toString().trim();
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

