package net.projectsrl.wm.richieste.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.wm.utils.Utils;
import net.projectsrl.wm.utils.WMUtils;

/**
 * FunctionInserimentoTrasferte
 * 
 */
public class FunctionInserimentoTrasferte extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_trasferte";
	private static final String DATASET_MODTRASFERTA = "DataSetModuliTrasferteCerca";

	public FunctionInserimentoTrasferte() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetTrasferte");
	}

	public FunctionInserimentoTrasferte(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetTrasferte");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);        
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_TRASFERTA", Utils.getUnique());
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
	
		
		HashMap templateData = (HashMap) setCommonTags(req, userInfo);
		templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
        String idModTrasferta=req.getField("ID_TRASFERTA_ORIGINE");
        

        
        if (req.getField("OPZIONE_INSERIMENTO_MODIFICA").equals(OPZIONE_MODIFICA)){	
        	String tipologia = req.getField("TIPOLOGIA");
        	templateData = loadVarStandard(templateData, req);
        	templateData.put("TIPOLOGIA",tipologia);
        }else{
        	templateData = loadVarStandard(templateData, req);
        }
        
        if(!idModTrasferta.equals("")){
	        templateData.put("ID_TRASFERTA_ORIGINE",idModTrasferta);
	        templateData.put("DATA_DAL",getDatiTrasferta(idModTrasferta)[0]);
	        templateData.put("DATA_AL",getDatiTrasferta(idModTrasferta)[1]);
	        templateData.put("ORE_DAL",getDatiTrasferta(idModTrasferta)[2]);
	        templateData.put("ORE_AL",getDatiTrasferta(idModTrasferta)[3]);
	        templateData.put("ORE",getDatiTrasferta(idModTrasferta)[4]);
	        templateData.put("CLIENTE",getDatiTrasferta(idModTrasferta)[5]);
	        templateData.put("COMMESSA",getDatiTrasferta(idModTrasferta)[6]);
	        templateData.put("LUOGO",getDatiTrasferta(idModTrasferta)[7]);
        }
        
		templateData.put("RUOLO_SESSIONE", getSessionRole(req));
		templateData.put("SALVATO", "");
		templateData.put("SALVATO_REMINDER", "");
		_applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req,templateData), res);
	}
	
		
	private String[] getDatiTrasferta(String idModTrasferta) throws AppCrash {
        String[] dati = { "", "", "", "", "", "", "", ""};
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_MODTRASFERTA);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_MODTRASFERTA", idModTrasferta);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("ID_MODTRASFERTA").toString().trim();
                if (idModTrasferta.equals(idTrovato)){
                	String dataDal=dbRow.getField("DATA_DAL").toString().trim();
                	String dataAl=dbRow.getField("DATA_AL").toString().trim();
                	String oreDal=dbRow.getField("ORE_DAL").toString().trim();
                	String oreAl=dbRow.getField("ORE_AL").toString().trim();
                	String ore=dbRow.getField("ORE").toString().trim();
                	String cliente=dbRow.getField("CLIENTE").toString().trim();
                	String commessa=dbRow.getField("COMMESSA").toString().trim();
                	String luogo=dbRow.getField("LUOGO").toString().trim();
                	dati[0] = dataDal;
                	dati[1] = dataAl;
                	dati[2] = oreDal;
                	dati[3] = oreAl;
                	dati[4] = ore;
                	dati[5] = cliente;
                	dati[6] = commessa;
                	dati[7] = luogo;
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
	        templateData.put("RUOLO_SESSIONE", getSessionRole(req));
			templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
	        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
			templateData.put("SALVATO", "Salvataggio effettuato con successo");
			templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
	       
	        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
		 }
}

