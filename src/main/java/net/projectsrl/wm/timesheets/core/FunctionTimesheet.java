
package net.projectsrl.wm.timesheets.core;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import it.project.iride.core.FunctionMostraPaginaConDataset;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.utils.Utils;

public class FunctionTimesheet extends FunctionMostraPaginaConDataset {

    public FunctionTimesheet() {

        super();
    }

    public FunctionTimesheet(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

    	HttpSession session = req.getSession(true);
        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);

        String anno = req.getField("ANNO");
        if (anno == null || anno.equals("")) {
            anno = Utils.getAnnoOggi();
        }
        String mese = req.getField("MESE");
        if (mese == null || mese.equals("")) {
            mese = Utils.getMeseOggi();
        }

        String tipoOO = req.getField("TIPOGST_OO");
        String tipoOS = req.getField("TIPOGST_OS");
        String tipoAI = req.getField("TIPOGST_AI");
        String tipoFP = req.getField("TIPOGST_FP");
        String tipologiaGST="";
        
        if (tipoOO.equals("") && tipoOS.equals("") && tipoAI.equals("") && tipoFP.equals("")){
        	templateData.put("TIPOLOGIA_GST", "");
        }else{
        	if (!tipoOO.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoOO+"'";
            }	 
        	if (!tipoOS.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoOS+"'";
            }	 
        	if (!tipoAI.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoAI+"'";
            }	 
        	if (!tipoFP.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoFP+"'";
            }	 
        	if(tipologiaGST.startsWith(" and ")){
        		tipologiaGST=" "+tipologiaGST.substring(4);
        	}
        	tipologiaGST=tipologiaGST.replace("and", "or");
        	templateData.put("TIPOLOGIA_GST", "AND ("+tipologiaGST+") ");
        }
        templateData.put("TIPOGST_OO", tipoOO);
        templateData.put("TIPOGST_OS", tipoOS);
        templateData.put("TIPOGST_AI", tipoAI);
        templateData.put("TIPOGST_FP", tipoFP);
        
        
        templateData.put("ANNO", anno);
        templateData.put("MESE", mese);
        templateData.put("AZIENDA", session.getAttribute("AZIENDA_SESSIONE"));
        String codRis = (String) session.getAttribute("ID_DIPENDENTE_SESSIONE");
        String risorsaSelezionata = req.getField("ID_DIPENDENTE_SESSIONE");
        if (!risorsaSelezionata.equals("")){
            templateData.put("ID_DIPENDENTE_SESSIONE", risorsaSelezionata);
        }else{
        	templateData.put("ID_DIPENDENTE_SESSIONE", codRis);
        }
        
        templateData.put("SALVATO",(String) req.getAttribute("SALVATO"));
        templateData.put("SALVATO_REMINDER",(String) req.getAttribute("SALVATO_REMINDER"));
        
        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
        super.elabora(req, res, userInfo);
    }

}
