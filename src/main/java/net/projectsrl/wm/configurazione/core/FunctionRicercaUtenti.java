package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaUtenti
 * 
 */

public class FunctionRicercaUtenti extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_utenti";
    private static final String PAGE_RISULTATI = "browse_utenti";    
    

    public FunctionRicercaUtenti() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaUtenti(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String userid 			= req.getField("USERID").trim();
        String nomeCognome		= req.getField("NOMECOGNOME").trim();        
        String ruolo	 		= req.getField("RUOLO1").trim();
        String azienda	 		= req.getField("AZIENDA").trim();
        String attivo	 		= req.getField("ATTIVO").trim();
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

        String str_composed_where_cond = "1=1";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
        		str_composed_where_cond = str_composed_where_cond + " and UTENTI.AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }
        
        if (!userid.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " and utenti.userid like '%"+userid.toUpperCase()+"%'";
        }

        if (!nomeCognome.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " and utenti.NOME+' '+utenti.COGNOME like'%"+nomeCognome.toUpperCase()+"%'";
        }
        
        if (!ruolo.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " and utenti.ruolo like '%"+ruolo.toUpperCase()+"%'";
        }
        
        if (!azienda.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " and utenti.azienda = '"+azienda.toUpperCase()+"'";
        }
        
        if (!attivo.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " and utenti.attivo = '"+attivo.toUpperCase()+"'";
        }
                   
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
		if (str_composed_where_cond.startsWith("AND ")) {
			str_composed_where_cond=" AND "+str_composed_where_cond.substring(4);
		}

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
