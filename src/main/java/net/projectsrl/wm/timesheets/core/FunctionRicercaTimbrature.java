package net.projectsrl.wm.timesheets.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaTimbrature
 * 
 */

public class FunctionRicercaTimbrature extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_timbrature";
    private static final String PAGE_RISULTATI = "browse_timbrature";    
    

    public FunctionRicercaTimbrature() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaTimbrature(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String dipendente		= req.getField("DIPENDENTE").trim();        
        String idAzienda 		= req.getField("AZIENDA");
        String dataDal			= req.getField("DATA_DAL");
        String dataAl			= req.getField("DATA_AL");

        String str_composed_where_cond 	= "";

 
            if (!dipendente.trim().equalsIgnoreCase("")) {	
            	str_composed_where_cond = str_composed_where_cond + " and TIMBRATURE.ID_DIPENDENTE ='"+dipendente.toUpperCase()+"'";
            }            
            
            if (!idAzienda.trim().equalsIgnoreCase("")) {
            	str_composed_where_cond = str_composed_where_cond + " and TIMBRATURE.ID_AZIENDA = '"+idAzienda.toUpperCase()+"'";
            }	            
        	
            if (!dataDal.trim().equalsIgnoreCase("")) {	
            	str_composed_where_cond = str_composed_where_cond + " and TIMBRATURE.DATA_TIMBRATURA = '"+dataDal.toUpperCase()+"'";
            }                    	
            if (!dataAl.trim().equalsIgnoreCase("")) {	
            	str_composed_where_cond = str_composed_where_cond + " and TIMBRATURE.DATA_TIMBRATURA = '"+dataAl.toUpperCase()+"'";
            }                    	

            str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
            if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
            	str_composed_where_cond=" "+str_composed_where_cond.substring(4);
            	str_composed_where_cond=" WHERE "+str_composed_where_cond;
            }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
