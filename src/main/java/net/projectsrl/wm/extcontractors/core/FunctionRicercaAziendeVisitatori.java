package net.projectsrl.wm.extcontractors.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaAziendaliVisitatori
 * 
 */
public class FunctionRicercaAziendeVisitatori extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_aziendevisitatori";
    private static final String PAGE_RISULTATI = "browse_aziendevisitatori";    
    

    public FunctionRicercaAziendeVisitatori() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaAziendeVisitatori(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String codice 			= req.getField("CODICE");
        String ragSociale		= req.getField("RAGSOC").trim();        
        String ragRidotta 		= req.getField("RAGRID").trim();
        String settore 			= req.getField("SETTORE");
        String codiceParent		= req.getField("CODICE_PARENT");
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        
        String str_composed_where_cond = "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
        		str_composed_where_cond = str_composed_where_cond + " and AZIENDE_VISITATORI.CODICE_PARENT IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!codice.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE_VISITATORI.CODICE like '%"+codice+"'";
        }
        
        if (!ragSociale.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE_VISITATORI.RAGSOC like '%"+ragSociale.toUpperCase()+"%'";
        }
        
        if (!ragRidotta.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE_VISITATORI.RAGRID like '%"+ragRidotta.toUpperCase()+"%'";
        }
        
        if (!codiceParent.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE_VISITATORI.CODICE_PARENT like '%"+codiceParent+"'";
        }
        
        if (!settore.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE_VISITATORI.SETTORE = '"+settore.toUpperCase()+"'";
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
