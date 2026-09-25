package net.projectsrl.wm.creazioneguidata.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.db.ApprovazioniDAO;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoProfiloOrario
 * 
 */
public class FunctionInserimentoProfiloOrarioCG extends FunctionInserimento {

	private static final String PAGE = "inserimento_profiloorario_cg";
	

	public FunctionInserimentoProfiloOrarioCG() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetProfiloOrario");
	}

	public FunctionInserimentoProfiloOrarioCG(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetProfiloOrario");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_PROFILO", Utils.getUnique());
			templateData.put("AZIENDA",(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
			return templateData;
		}
		return templateData;

	}
	
	@SuppressWarnings("unchecked")
	protected HashMap saveVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		gestioneProfiloPredefinito(req);
		return templateData;
	}
	
	private void gestioneProfiloPredefinito( SsbServletRequest req) throws AppCrash {
		if (req.getField("PREDEFINITO").equals("S")){
			String queryUpdate="UPDATE PROFILO_ORARIO SET PREDEFINITO='' WHERE AZIENDA='"+req.getField("AZIENDA")+"'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
			String queryUpdate9="UPDATE PROFILO_ORARIO SET PREDEFINITO='S' WHERE AZIENDA='"+req.getField("AZIENDA")+"' AND ID_PROFILO='"+req.getField("ID_PROFILO")+"'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate9);
		}
	}
	
}

