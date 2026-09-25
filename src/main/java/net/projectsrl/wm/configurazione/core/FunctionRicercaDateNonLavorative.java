package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaDateNonLavorative
 * 
 */
public class FunctionRicercaDateNonLavorative extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_datenonlavorative";
    private static final String PAGE_RISULTATI = "browse_datenonlavorative";    
    

    public FunctionRicercaDateNonLavorative() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaDateNonLavorative(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String dataInizioDal		= req.getField("DATA_DAL");
        String dataInizioAl			= req.getField("DATA_AL");
        String descrizione			= req.getField("DESCRIZIONE"); 
        String ricorsiva    		= req.getField("RICORSIVA"); 
        

        String str_composed_where_cond 	= "";

        if (!descrizione.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DESCRIZIONE like '%"+descrizione.toUpperCase()+"'";
        }
        if (!ricorsiva.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND RICORSIVA like '%"+ricorsiva.toUpperCase()+"%'";
        }

        if (!dataInizioDal.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA >= '"+dataInizioDal.toUpperCase()+"'";
        }
        if (!dataInizioAl.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA <= '"+dataInizioAl.toUpperCase()+"'";
        }
       
            
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	if (str_composed_where_cond.startsWith("(")){
        		str_composed_where_cond=" ("+str_composed_where_cond.substring(4);
        	}else{
        		str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	}
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
