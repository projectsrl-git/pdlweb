package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaCodificaRuoli
 * 
 */
public class FunctionRicercaCodificaRuoli extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_codificaruoli";
    private static final String PAGE_RISULTATI = "browse_codificaruoli";    
    

    public FunctionRicercaCodificaRuoli() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaCodificaRuoli(ApplicationServices_itf applServices, String functionID, String functionName) {
        super(applServices, functionID, functionName);
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    /**
     * Prepara la where condition da passare alla query per la visualizzazione della pagina di ricerca
     * 
     * @param SsbServletRequest+ req
     * @param HashMap queryParameter
     * 
     * @return HashMap
     */
    @SuppressWarnings("unchecked")
	protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {
    	
    	String prefissoParametro= "RUO";
        String codice 			= req.getField("CODICE").trim();
        String descrizione		= req.getField("DESCRIZIONE").trim();        

        String str_composed_where_cond 	= "";

            if (!codice.trim().equalsIgnoreCase("")) {	
            	str_composed_where_cond = str_composed_where_cond + " AND CODICE LIKE '"+prefissoParametro+codice.toUpperCase()+"%'";
            }                    	

            if (!descrizione.trim().equalsIgnoreCase("")) {	
            	str_composed_where_cond = str_composed_where_cond + " AND DESCRI LIKE '%"+descrizione.toUpperCase()+"%'";
            }            
            
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
		if (str_composed_where_cond.startsWith("AND ")) {
			str_composed_where_cond=" AND "+str_composed_where_cond.substring(4);
		}

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
