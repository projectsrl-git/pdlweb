package net.projectsrl.wm.extcontractors.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionRicercaVisitatori
 * 
 */
public class FunctionRicercaVisitatori extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_visitatori";
    private static final String PAGE_RISULTATI = "browse_visitatori";    
    

    public FunctionRicercaVisitatori() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaVisitatori(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String nominativo			= req.getField("NOMINATIVO").trim(); 
        String gruppoAppartenenza	= req.getField("GRUPPO_APPARTENENZA"); 
        String azienda				= req.getField("AZIENDA"); 
        String dataIngresso			= Utils.ribaltaData(req.getField("DATA_INGRESSO")); 
        String dataUscita			= Utils.ribaltaData(req.getField("DATA_USCITA")); 
        String responsabile			= Utils.ribaltaData(req.getField("RESPONSABILE")); 
        String aziendaSessione 		= (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

        String str_composed_where_cond 	= "";              	

        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " AND AZIENDE_VISITATORI.CODICE_PARENT IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }
        
        if (!nominativo.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND NOMINATIVO like '%"+nominativo.toUpperCase()+"%'";
        }
        if (!gruppoAppartenenza.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND GRUPPO_APPARTENENZA = '"+gruppoAppartenenza.toUpperCase()+"'";
        }
        if (!dataIngresso.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_INGRESSO >= '"+dataIngresso+"'";
        }
        if (!dataUscita.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_USCITA <= '"+dataUscita+"'";
        }
        if (!responsabile.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND RESPONSABILE >= '"+responsabile.toUpperCase()+"'";
        }
         
        if (!azienda.trim().equalsIgnoreCase("") && !azienda.equals(aziendaSessione)) {	
        	str_composed_where_cond = str_composed_where_cond + " AND  AZIENDE_VISITATORI.CODICE_PARENT = '"+azienda+"'";
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
