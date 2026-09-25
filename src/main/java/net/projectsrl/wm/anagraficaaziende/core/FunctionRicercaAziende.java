package net.projectsrl.wm.anagraficaaziende.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaSediAziendali
 * 
 */
public class FunctionRicercaAziende extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_aziende";
    private static final String PAGE_RISULTATI = "browse_aziende";    
    

    public FunctionRicercaAziende() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaAziende(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String codice 			= req.getField("CODICE").trim();
        String ragSociale		= req.getField("RAGSOC").trim();        
        String ragRidotta 		= req.getField("RAGRID").trim();
        String settore 			= req.getField("SETTORE").trim();
        String codiceParent		= req.getField("CODICE_PARENT");
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        
        String str_composed_where_cond = "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!codice.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE.CODICE like '%"+codice+"'";
        }
        
        if (!ragSociale.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE.RAGSOC like '%"+ragSociale.toUpperCase()+"%'";
        }
        
        if (!ragRidotta.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE.RAGRID like '%"+ragRidotta.toUpperCase()+"%'";
        }    
        
        if (!codiceParent.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE.CODICE_PARENT = '"+codiceParent+"'";  	
        }
        
        if (!settore.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE.SETTORE = '"+settore.toUpperCase()+"'";
        }
		
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
