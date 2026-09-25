package net.projectsrl.wm.richieste.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionRicercaRimborsi
 * 
 */
public class FunctionRicercaModuliTrasferte extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_modulitrasferte";
    private static final String PAGE_RISULTATI = "browse_modulitrasferte";    
    

    public FunctionRicercaModuliTrasferte() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaModuliTrasferte(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
         
    	String cliente		= req.getField("CLIENTE");
    	String dataDal		= Utils.ribaltaData(req.getField("DATA_DAL_FP"));
        String dataAl		= Utils.ribaltaData(req.getField("DATA_AL_FP"));
        String azienda		= req.getField("AZIENDA_TENDINA");
        String dipendente	= req.getField("DIPENDENTE");
        if (getSessionRole(req).equals("D")){
            dipendente= (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
        }
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

        String str_composed_where_cond 	= "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!dataDal.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_DAL >= '"+dataDal+"'";
        }
        if (!dataAl.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_AL <= '"+dataAl+"'";
        }
        
        if (!cliente.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND MOD_TRASFERTE.CLIENTE like '%"+cliente+"%'";
        }
       
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND MOD_TRASFERTE.AZIENDA_TENDINA = '"+azienda+"'";
        }
        if (!dipendente.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DIPENDENTE = '"+dipendente+"'";
        }
        
       
            
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	if (str_composed_where_cond.startsWith("(")){
        		str_composed_where_cond=" ("+str_composed_where_cond.substring(4);
        	}else{
        		str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	}
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
