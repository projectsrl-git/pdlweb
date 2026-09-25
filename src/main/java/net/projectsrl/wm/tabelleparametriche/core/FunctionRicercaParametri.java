package net.projectsrl.wm.tabelleparametriche.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaParametri
 * 
 */
public class FunctionRicercaParametri extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_parametri";
    private static final String PAGE_RISULTATI = "browse_parametri";    
    

    public FunctionRicercaParametri() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaParametri(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
    	String prefissoParametro= req.getField("PREFISSO_PARAMETRO");
        String codice 			= req.getField("CODICE").trim();
        String descrizione		= req.getField("DESCRI").trim();
        String note		= req.getField("NOTE").trim(); 
        String azienda			= req.getField("AZIENDA");
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        String tabella = "PARA";
        if (prefissoParametro.equals("CAT")){
        	tabella = "PARA_CATASTO";
        }

        String str_composed_where_cond 	= "";

        if (!prefissoParametro.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND PARA.CODICE LIKE '"+prefissoParametro+codice.toUpperCase()+"%'";
        }                    	

        if (!descrizione.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DESCRI LIKE '%"+descrizione.toUpperCase()+"%'";
        } 
        
        if (!note.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND LIBERA LIKE '%"+note.toUpperCase()+"%'";
        } 
        
        if (prefissoParametro.equals("INP")){
	        if (!azienda.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND  AZIENDA = '"+azienda+"'";
	        }else{
	        	str_composed_where_cond = str_composed_where_cond + " AND  AZIENDA = '"+aziendaSessione+"' or CODICE_PARENT ='"+aziendaSessione+"' or codice_parent in (select codice from aziende where codice_parent = '"+aziendaSessione+"' or codice = '"+aziendaSessione+"')";
	        }
        }
        
        str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
		
		queryParameter.put("TABELLA", tabella);
        
    	return queryParameter;
    }    
}
