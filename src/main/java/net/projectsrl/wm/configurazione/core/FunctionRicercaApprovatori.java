package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaApprovatori
 * 
 */
public class FunctionRicercaApprovatori extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_approvatori";
    private static final String PAGE_RISULTATI = "browse_approvatori";    
    

    public FunctionRicercaApprovatori() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaApprovatori(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
    
        String azienda			= req.getField("AZIENDA");
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        String nomeGruppo		= req.getField("NOME_GRUPPO");


        String str_composed_where_cond 	= "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }
        
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND APPROVATORI.AZIENDA = '"+azienda+"'";
        }
        
        if (!nomeGruppo.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND NOME_GRUPPO = '"+nomeGruppo+"'";
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
