package net.projectsrl.wm.documenti.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaCedolini
 * 
 */
public class FunctionRicercaCedolini extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_cedolini";
    private static final String PAGE_RISULTATI = "browse_cedolini";    
    

    public FunctionRicercaCedolini() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaCedolini(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
    	String azienda			="";
        String nominativo 		= req.getField("NOMECOGNOME").trim();
        azienda					= req.getField("AZIENDA");
        String anno				= req.getField("ANNO_CEDOLINO");
        String aziendaSessione 	= (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        String codCurricul 	= "";
        
        String ruolo		 	= getSessionRole(req);
        if ("D".equals(ruolo)){
            azienda		 		= (String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI");
            codCurricul 	= (String) req.getSession(false).getAttribute("COD_CURRICUL_SESSIONE");
        }
        
        String str_composed_where_cond = "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " and ELENCO_FILE.AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND ELENCO_FILE.AZIENDA = '"+azienda+"'";
        }
        
        if (!nominativo.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " AND NOMINATIVO LIKE '%"+nominativo+"%'";
        }
        
        if (!anno.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND ELENCO_FILE.ANNO = '"+anno+"'";
        }
        
        if (!codCurricul.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND ELENCO_FILE.DIPENDENTE = '"+codCurricul+"'";
        }
		
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
