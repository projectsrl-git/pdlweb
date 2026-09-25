package net.projectsrl.wm.timesheets.core;

import java.util.HashMap;

import freemarker.template.SimpleNumber;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionRicerca;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionControlloTimbrature
 * 
 */
public class FunctionControlloTimbrature extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "controllotimbrature";
    private static final String PAGE_RISULTATI = "browse_controllotimbrature";    
    

    public FunctionControlloTimbrature() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionControlloTimbrature(ApplicationServices_itf applServices, String functionID, String functionName) {
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
        String nominativo		= req.getField("NOMINATIVO");
        String anno				= req.getField("ANNO");
        String mese				= req.getField("MESE").trim(); 
        String aziendaSessione 	= (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
 

        String str_composed_where_cond 	 = "";
        String str_composed_where_cond_1 = "";
        String str_composed_where_cond_2 = "";
        String str_composed_where_cond_3 = "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
    		str_composed_where_cond_1 = str_composed_where_cond_1 + " and ID_DIPENDENTE IN (SELECT ID_DIPENDENTE FROM DIPENDENTI WHERE AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%'))";
    		str_composed_where_cond_2 = str_composed_where_cond_2 + " and TIM.ID_DIPENDENTE IN (SELECT ID_DIPENDENTE FROM DIPENDENTI WHERE AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%'))";
    		str_composed_where_cond_3 = str_composed_where_cond_3 + " and ST.AZIENDA IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDA = '"+azienda.toUpperCase()+"'";
        	str_composed_where_cond_1 = str_composed_where_cond_1 + " and ID_DIPENDENTE IN (SELECT ID_DIPENDENTE FROM DIPENDENTI WHERE AZIENDA = '"+azienda.toUpperCase()+"')";
        	str_composed_where_cond_2 = str_composed_where_cond_2 + " and TIM.ID_DIPENDENTE IN (SELECT ID_DIPENDENTE FROM DIPENDENTI WHERE AZIENDA = '"+azienda.toUpperCase()+"')";
        	str_composed_where_cond_3 = str_composed_where_cond_3 + " AND ST.AZIENDA = '"+azienda.toUpperCase()+"'";
        }
        if (!nominativo.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND NOMINATIVO like '%"+nominativo.toUpperCase()+"%'";
        	str_composed_where_cond_1 = str_composed_where_cond_1 + " and ID_DIPENDENTE IN (SELECT ID_DIPENDENTE FROM DIPENDENTI WHERE NOMINATIVO like '%"+nominativo.toUpperCase()+"%')";
        	str_composed_where_cond_2 = str_composed_where_cond_2 + " and TIM.ID_DIPENDENTE IN (SELECT ID_DIPENDENTE FROM DIPENDENTI WHERE NOMINATIVO like '%"+nominativo.toUpperCase()+"%')";
        	str_composed_where_cond_3 = str_composed_where_cond_3 + " AND ST.NOMINATIVO like '%"+nominativo.toUpperCase()+"%'";
        }
        if (!anno.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND SUBSTRING(DATA,1,4) = '"+anno.toUpperCase()+"'";
        	str_composed_where_cond_1 = str_composed_where_cond_1 + " AND SUBSTRING(DATA_TIMBRATURA,1,4) = '"+anno.toUpperCase()+"'";
        	str_composed_where_cond_2 = str_composed_where_cond_2 + " AND SUBSTRING(TIM.DATA_TIMBRATURA,1,4) = '"+anno.toUpperCase()+"'";
        	str_composed_where_cond_3 = str_composed_where_cond_3 + " AND SUBSTRING(ST.DATA,1,4) = '"+anno.toUpperCase()+"'";
        }
        if (!mese.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND SUBSTRING(DATA,6,2)= '"+mese.toUpperCase()+"'";
        	str_composed_where_cond_1 = str_composed_where_cond_1 + " AND SUBSTRING(DATA_TIMBRATURA,6,2) = '"+mese.toUpperCase()+"'";
        	str_composed_where_cond_2 = str_composed_where_cond_2 + " AND SUBSTRING(TIM.DATA_TIMBRATURA,6,2) = '"+mese.toUpperCase()+"'";
        	str_composed_where_cond_3 = str_composed_where_cond_3 + " AND SUBSTRING(ST.DATA,6,2) = '"+mese.toUpperCase()+"'";
        }
            
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }
		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
		
		str_composed_where_cond_1=str_composed_where_cond_1.trim().toUpperCase();
		str_composed_where_cond_2=str_composed_where_cond_2.trim().toUpperCase();
		str_composed_where_cond_3=str_composed_where_cond_3.trim().toUpperCase();
		
    	String chiudi = req.getField("CHIUDI");
    	if (chiudi.equals("SI")){
    		
    		String queryUpdateTimbrature = Config.GetInstance().getProperty("chiusura.anomalie.update_timbrature");
    		queryUpdateTimbrature = queryUpdateTimbrature + str_composed_where_cond_1;
    		try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateTimbrature);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		String queryInsertTimbrature_1 = Config.GetInstance().getProperty("chiusura.anomalie.insert_timbrature_1");
    		String queryInsertTimbrature_2 = Config.GetInstance().getProperty("chiusura.anomalie.insert_timbrature_2");
    		String queryInsertTimbrature;
    		queryInsertTimbrature = queryInsertTimbrature_1 + str_composed_where_cond_2 + queryInsertTimbrature_2;
    		try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryInsertTimbrature);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		String queryInsertTimesht_1 = Config.GetInstance().getProperty("chiusura.anomalie.insert_timesht_1");
    		String queryInsertTimesht_2 = Config.GetInstance().getProperty("chiusura.anomalie.insert_timesht_2");
    		String queryInsertTimesht;
    		queryInsertTimesht = queryInsertTimesht_1 + str_composed_where_cond_3 + queryInsertTimesht_2;
    		try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryInsertTimesht);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        
    	return queryParameter;
    }
    
    @SuppressWarnings("unchecked")
	public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

    	HashMap templateData=(HashMap) setCommonTags(req,userInfo);
    	templateData.put("SUBMENU", getFunctionID());
    	templateData.put("SUBMENU_BREVE", getFunctionID().substring(0, 2));
    	templateData=setTemplateDataFromRequest(templateData,req);
    	templateData.put("ESITORICERCA",new SimpleNumber(0));
    	templateData.put("RUOLO_SESSIONE", getSessionRole(req));
    	templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
        
        String anno = req.getField("ANNO");
        if (anno == null || anno.equals("")) {
            anno = Utils.getAnnoOggi();
        }
        String mese = req.getField("MESE");
        if (mese == null || mese.equals("")) {
            mese = Utils.getMeseOggi();
        }
        String nominativo = req.getField("NOMINATIVO");
        String azienda = req.getField("AZIENDA");

        templateData.put("NOMINATIVO", nominativo);
        templateData.put("ANNO", anno);
        templateData.put("MESE", mese);
        templateData.put("AZIENDA", azienda);
        
        _applicationSrv.displayPage(PAGE_IMPOSTA, templateData, setPageDatasetParam(PAGE_IMPOSTA, req,templateData), res);
        
    }

}
