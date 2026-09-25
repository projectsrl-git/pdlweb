package net.projectsrl.wm.extcontractors.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;

/**
 * FunctionAssociaAziendeVisitatori
 * 
 */
public class FunctionAssociaAziendeVisitatori extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "associa_aziendevisitatori";
	

	public FunctionAssociaAziendeVisitatori() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetAziendeVisitatori");
	}

	public FunctionAssociaAziendeVisitatori(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetAziendeVisitatori");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("CODICE", req.getField("CODICE"));
			return templateData;
		}
		
		if (option.equals(OPZIONE_MODIFICA)) {
			templateData.put("CODICE", templateData.get("CODICE"));
		}
		return templateData;

	}
}

