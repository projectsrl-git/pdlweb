package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionCalendarioLavorativo
 * 
 */
public class FunctionCalendarioLavorativo extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_calendariolavorativo";
    private static final String PAGE_RISULTATI = "browse_calendariolavorativo";    
    

    public FunctionCalendarioLavorativo() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionCalendarioLavorativo(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        String anno		= req.getField("ANNO_CALENDARIO_LAVORATIVO");
        String azienda	= req.getField("AZIENDA");
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        String str_composed_where_cond 	= "";              	

        if (!anno.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND ANNO = '"+anno.toUpperCase()+"'";
        }else{
        	str_composed_where_cond = str_composed_where_cond + " AND ANNO = ''";
        }
        
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDA = '"+azienda.toUpperCase()+"'";
        }else{
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDA = '"+aziendaSessione+"'";
        }
        
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

        if (str_composed_where_cond.equals("")){
        	queryParameter.put("COMPOSED_WHERE_COND", "");
        }else{
        	queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond + " AND ");
        }
        
    	return queryParameter;
    }    
}
