package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;

/**
 * FunctionInserimentoParaCodificaRuoli
 * 
 */
public class FunctionInserimentoParaCodificaRuoli extends FunctionInserimento {

	private static final String PAGE = "inserimento_paracodificaruoli";

	public FunctionInserimentoParaCodificaRuoli() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetParaCodificaRuoli");
	}

	public FunctionInserimentoParaCodificaRuoli(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetParaCodificaRuoli");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			return templateData;
		}
		return templateData;
	}

	
	/*
	 * Prepara la where condition per le query di pagina Per agganciare i
	 * dettagli, l'unica condizione di JOIN che serve è TAGGANGIO=DAGGANCIO
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {
		String prefissoParametro = req.getField(PREFISSO_PARAMETRO);
		queryParameter.put(PREFISSO_PARAMETRO, prefissoParametro);
		
		return queryParameter;
	}
}

