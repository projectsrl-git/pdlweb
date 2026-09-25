package net.projectsrl.wm.jobmanagement.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionAssociaDipendente
 * 
 */
public class FunctionAssociaDipendente extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "associa_dipendente";
	

	public FunctionAssociaDipendente() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetCommessaXDipendente");
	}

	public FunctionAssociaDipendente(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetCommessaXDipendente");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_COMMESSA", req.getField("ID_COMMESSA"));
			templateData.put("ASSOCIATA_DAL","01/01/"+Utils.getAnnoOggi());
            templateData.put("ASSOCIATA_AL","31/12/"+Utils.getAnnoOggi());
			return templateData;
		}
		
		if (option.equals(OPZIONE_MODIFICA)) {
			templateData.put("ID_COMMESSA", templateData.get("ID_COMMESSA"));
		}
		return templateData;

	}
}

