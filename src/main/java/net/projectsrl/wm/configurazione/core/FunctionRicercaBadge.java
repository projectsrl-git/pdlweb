package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionRicercaBadge
 * 
 */
public class FunctionRicercaBadge extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_badge";
    private static final String PAGE_RISULTATI = "browse_badge";    
    

    public FunctionRicercaBadge() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaBadge(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    	
        
        String codice			= req.getField("CODICE_BADGE");
        String codiceMeccanog	= req.getField("CODICE_MECCANOG"); 
        String aziendaDipendenti= req.getField("AZIENDA_D"); 
        String aziendaVisitatori= req.getField("AZIENDA_V"); 
        String dipendente		= req.getField("DIPENDENTE");
        String visitatore		= req.getField("VISITATORE");
        String tipologia		= req.getField("TIPOLOGIA");
        String sospeso			= req.getField("SOSPESO");
        String dataInizioDal	= Utils.ribaltaData(req.getField("DATA_INIZIO_DAL")); 
        String dataInizioAl		= Utils.ribaltaData(req.getField("DATA_INIZIO_AL")); 
        String dataFineDal		= Utils.ribaltaData(req.getField("DATA_FINE_DAL")); 
        String dataFineAl		= Utils.ribaltaData(req.getField("DATA_FINE_AL"));
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

        String str_composed_where_cond 	= "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and (BADGE.AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')"
    				+ " OR AZIENDE_VISITATORI.CODICE_PARENT IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%'))";
        }

        if (!codice.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.CODICE like '%"+codice.toUpperCase()+"'";
        }
        if (!codiceMeccanog.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.CODICE_MECCANOG like '%"+codiceMeccanog.toUpperCase()+"%'";
        }
        if (!tipologia.trim().equalsIgnoreCase("") && tipologia.equals("D")) {
	        if (!aziendaDipendenti.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.AZIENDA = '"+aziendaDipendenti.toUpperCase()+"'";
	        }
	        if (!dipendente.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.ID_SOGGETTO = '"+dipendente.toUpperCase()+"'";
	        }
        }
        if (!tipologia.trim().equalsIgnoreCase("") && tipologia.equals("V")) {
	        if (!aziendaVisitatori.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.AZIENDA = '"+aziendaVisitatori.toUpperCase()+"'";
	        }
	        if (!visitatore.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.ID_SOGGETTO = '"+visitatore.toUpperCase()+"'";
	        }
        }
        
        if(tipologia.equals("")){
        	if (!aziendaDipendenti.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.AZIENDA = '"+aziendaDipendenti.toUpperCase()+"'";
	        }
	        if (!dipendente.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.ID_SOGGETTO = '"+dipendente.toUpperCase()+"'";
	        }
        	if (!aziendaVisitatori.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.AZIENDA = '"+aziendaVisitatori.toUpperCase()+"'";
	        }
	        if (!visitatore.trim().equalsIgnoreCase("")) {	
	        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.ID_SOGGETTO = '"+visitatore.toUpperCase()+"'";
	        }
        }
        
        if (!dataInizioDal.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.DATA_INIZIO >= '"+dataInizioDal.toUpperCase()+"'";
        }
        if (!dataInizioAl.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.DATA_INIZIO <= '"+dataInizioAl.toUpperCase()+"'";
        }
        if (!dataFineDal.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.DATA_FINE >= '"+dataFineDal.toUpperCase()+"'";
        }
        if (!dataFineAl.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.DATA_FINE <= '"+dataFineAl.toUpperCase()+"'";
        }
        
        if (!tipologia.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond = str_composed_where_cond + " AND BADGE.TIPOLOGIA = '"+tipologia.toUpperCase()+"'";
        }
        
        if (!sospeso.trim().equalsIgnoreCase("")) {
        	if (sospeso.trim().equals("S")) {
        		str_composed_where_cond = str_composed_where_cond + " AND BADGE.SOSPESO = 'S'";
        	} else {
        		str_composed_where_cond = str_composed_where_cond + " AND BADGE.SOSPESO = ''";
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
