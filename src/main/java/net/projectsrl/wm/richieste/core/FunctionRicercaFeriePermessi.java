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
public class FunctionRicercaFeriePermessi extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_feriepermessi";
    private static final String PAGE_RISULTATI = "browse_feriepermessi";   
    

    public FunctionRicercaFeriePermessi() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaFeriePermessi(ApplicationServices_itf applServices, String functionID, String functionName) {
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
	public HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {

    	String tipoPermesso	= req.getField("TIPO_PERMESSO");
    	String stato		= req.getField("APPROVAZIONE"); 
    	String dataDal		= Utils.ribaltaData(req.getField("DATA_DAL_FP"));
        String dataAl		= Utils.ribaltaData(req.getField("DATA_AL_FP"));
    	String azienda		= req.getField("AZIENDA_TENDINA");
        String dipendente	= req.getField("DIPENDENTE");
        
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        
        if (getSessionRole(req).equals("D")){
            dipendente= (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
        }
        
        String str_composed_where_cond 	= "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!tipoPermesso.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND TIPO_PERMESSO = '"+tipoPermesso+"'";
        }
        if (!dataDal.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_DAL >= '"+dataDal+"'";
        }
        if (!dataAl.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_AL <= '"+dataAl+"'";
        }
        
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND FP.AZIENDA_TENDINA = '"+azienda+"'";
        }
        if (!dipendente.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND fp.DIPENDENTE = '"+dipendente+"'";
        }
        
        if (!stato.trim().equalsIgnoreCase("")) {
        	if (stato.trim().equals("S")) {
        		str_composed_where_cond = str_composed_where_cond + " AND APPROVAZIONE = 'S'";
        	} 
        	if (stato.trim().equals("N")) {
        		str_composed_where_cond = str_composed_where_cond + " AND APPROVAZIONE = 'N'";
        	} 
        	if (stato.trim().equals("A")) {
        		str_composed_where_cond = str_composed_where_cond + " AND (APPROVAZIONE = 'A' OR APPROVAZIONE='')";
        	} 
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
