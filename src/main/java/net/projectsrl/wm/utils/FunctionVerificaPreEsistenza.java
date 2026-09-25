package net.projectsrl.wm.utils;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.core.FunctionWebApp_base;

/**
 * FunctionVerificaPreEsistenza
 * 
 */
public class FunctionVerificaPreEsistenza extends FunctionWebApp_base {

	public FunctionVerificaPreEsistenza() {

		super();
	}

	public FunctionVerificaPreEsistenza(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}

	public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
		elabora(req, res, userInfo);
	}

	@SuppressWarnings("unchecked")
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		HashMap templateData = (HashMap) setCommonTags(req, userInfo);
		templateData = setTemplateDataFromRequest(templateData, req);
		String page =  req.getField("PAGE");
		String nomeCampo = req.getField("NOME_CAMPO_DA_CONTROLLARE");
		String valoreCampo = req.getField("VALORE_CAMPO_DA_CONTROLLARE");
		String dataset = req.getField("DATASET");
		String esiste = getEsistenza(nomeCampo,valoreCampo,dataset);

		templateData.put("ESISTE", esiste);
		templateData.put("RUOLO_SESSIONE", getSessionRole(req));
		_applicationSrv.displayPage(page, templateData, res);
		
	}

	
	
	private String getEsistenza(String nomeCampo, String valoreCampo, String dataset) throws AppCrash {

        String esiste = "NO";
        DataSet_itf dataSet = null;

        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", dataset);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(nomeCampo, valoreCampo);
            dataSet.setParam(params);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                esiste = "SI";
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
