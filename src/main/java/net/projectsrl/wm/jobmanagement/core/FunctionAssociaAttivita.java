package net.projectsrl.wm.jobmanagement.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;

/**
 * FunctionAssociaDipendente
 * 
 */
public class FunctionAssociaAttivita extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "associa_attivita";
	

	public FunctionAssociaAttivita() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetCommessaXAttivita");
	}

	public FunctionAssociaAttivita(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetCommessaXAttivita");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_COMMESSA", req.getField("ID_COMMESSA"));
			return templateData;
		}
		
		if (option.equals(OPZIONE_MODIFICA)) {
			templateData.put("ID_COMMESSA", templateData.get("ID_COMMESSA"));
		}
		return templateData;

	}
}

