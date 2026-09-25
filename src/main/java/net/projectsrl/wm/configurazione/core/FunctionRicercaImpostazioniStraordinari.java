package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaImpostazioniStraordinari
 * 
 */
public class FunctionRicercaImpostazioniStraordinari extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_impostazionistraordinari";
    private static final String PAGE_RISULTATI = "browse_impostazionistraordinari";    
    

    public FunctionRicercaImpostazioniStraordinari() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaImpostazioniStraordinari(ApplicationServices_itf applServices, String functionID, String functionName) {
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

        String azienda				= req.getField("AZIENDA"); 
        String aziendaSessione 		= (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

        String str_composed_where_cond 	= "";              	

        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " AND IMPOSTAZIONI_STRAORDINARI.AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

         
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND  IMPOSTAZIONI_STRAORDINARI.AZIENDA = '"+azienda+"'";
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
