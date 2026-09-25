package net.projectsrl.wm.extcontractors.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaContratti
 * 
 */
public class FunctionRicercaContratti extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_contratti";
    private static final String PAGE_RISULTATI = " browse_contratti";    
    

    public FunctionRicercaContratti() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaContratti(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String settore				= req.getField("SETTORE").trim(); 
        String contratti			= req.getField("CONTRATTI"); 

        String str_composed_where_cond 	= "";              	

        if (!settore.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " AND AZIENDE_VISITATORI.CODICE_PARENT IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+settore+"%')";
        }
        
        if (!contratti.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND NOMINATIVO like '%"+contratti.toUpperCase()+"%'";
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
