package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoDateNonLavorative
 * 
 */
public class FunctionInserimentoDateNonLavorative extends FunctionInserimento {

	private static final String PAGE = "inserimento_datenonlavorative";
	
	public FunctionInserimentoDateNonLavorative() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetDateNonLavorative");
	}

	public FunctionInserimentoDateNonLavorative(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetDateNonLavorative");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);  

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_RIGA", Utils.getUnique());
		}	
		return templateData;
		
	}
	
}

