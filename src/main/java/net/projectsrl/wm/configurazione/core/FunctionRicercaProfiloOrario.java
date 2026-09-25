package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaProfiloOrario
 * 
 */
public class FunctionRicercaProfiloOrario extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_profiloorario";
    private static final String PAGE_RISULTATI = "browse_profiloorario";    
    

    public FunctionRicercaProfiloOrario() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaProfiloOrario(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String nomeProfilo		= req.getField("NOME_PROFILO").trim();
        String indennita		= req.getField("INDENNITA_PAUSA");  
        String maggiorazione	= req.getField("MAGG_TURNO_NOTTURNO"); 
        String timbLibere		= req.getField("TIMB_LIBERE"); 
        String timbrature		= req.getField("TIMBRATURE"); 
        String azienda			= req.getField("AZIENDA");
        String aziendaSessione 	= (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

        String str_composed_where_cond 	= "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!nomeProfilo.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND NOME_PROFILO like '%"+nomeProfilo.toUpperCase()+"%'";
        }
        
        if (!indennita.trim().equalsIgnoreCase("")) {
        	if (indennita.trim().equals("S")) {	
        		str_composed_where_cond = str_composed_where_cond + " AND INDENNITA_PAUSA = '"+indennita.toUpperCase()+"'";
        	}
        	if (indennita.trim().equals("N")) {
        		str_composed_where_cond = str_composed_where_cond + " AND INDENNITA_PAUSA = ''";	
        	}
        }
        
        if (!maggiorazione.trim().equalsIgnoreCase("")) {	
        	if (maggiorazione.trim().equals("S")) {	
        		str_composed_where_cond = str_composed_where_cond + " AND MAGG_TURNO_NOTTURNO = '"+maggiorazione.toUpperCase()+"'";
        	}
        	if (maggiorazione.trim().equals("N")) {	
        		str_composed_where_cond = str_composed_where_cond + " AND MAGG_TURNO_NOTTURNO = ''";
        	}
        }
        
        if (!timbLibere.trim().equalsIgnoreCase("")) {
        	if (timbLibere.trim().equals("S")) {	
        		str_composed_where_cond = str_composed_where_cond + " AND TIMB_LIBERE = '"+timbLibere.toUpperCase()+"'";
        	}
        	if (timbLibere.trim().equals("N")) {
        		str_composed_where_cond = str_composed_where_cond + " AND TIMB_LIBERE = ''";	
        	}
        }
        
        if (!timbrature.trim().equalsIgnoreCase("")) {	
        		str_composed_where_cond = str_composed_where_cond + " AND TIMBRATURE = '"+timbrature+"'";
        }
        
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDA = '"+azienda.toUpperCase()+"'";
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
