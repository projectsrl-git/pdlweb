package net.projectsrl.wm.timesheets.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaUtenti
 * 
 */

public class FunctionRicercaTimesheet extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_utenti";
    private static final String PAGE_RISULTATI = "browse_utenti";    
    

    public FunctionRicercaTimesheet() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaTimesheet(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String userid 			= req.getField("USERID");
        String nomeCognome		= req.getField("NOMECOGNOME").trim();        
        String ruolo	 		= req.getField("RUOLO1");

        String str_composed_where_cond 	= "";

            if (!userid.trim().equalsIgnoreCase("")) {	
            	str_composed_where_cond = str_composed_where_cond + " and userid like '%"+userid.toUpperCase()+"%'";
            }                    	

            if (!nomeCognome.trim().equalsIgnoreCase("")) {	
            	str_composed_where_cond = str_composed_where_cond + " and NOME+' '+COGNOME like'%"+nomeCognome.toUpperCase()+"%'";
            }            
            
            if (!ruolo.trim().equalsIgnoreCase("")) {
            	str_composed_where_cond = str_composed_where_cond + " and ruolo like '"+ruolo.toUpperCase()+"%'";
            }	            
        	
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
		if (str_composed_where_cond.startsWith("AND ")) {
			str_composed_where_cond=" AND "+str_composed_where_cond.substring(4);
		}

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
