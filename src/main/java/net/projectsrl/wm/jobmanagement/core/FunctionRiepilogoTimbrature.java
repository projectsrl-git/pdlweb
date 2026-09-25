package net.projectsrl.wm.jobmanagement.core;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import it.project.iride.core.FunctionMostraPaginaConDataset;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.db.TimbratureDAO;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionRiepilogoTimbrature
 * 
 */
public class FunctionRiepilogoTimbrature extends FunctionMostraPaginaConDataset {
 
    

    public FunctionRiepilogoTimbrature() {
        super();
    }

    public FunctionRiepilogoTimbrature(ApplicationServices_itf applServices, String functionID, String functionName) {
        super(applServices, functionID, functionName);

    }


    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);

        String dateNow=Utils.getStringDataOggi();
        templateData.put("DATE_NOW", dateNow);
        String timeNow=Utils.getStringOra();
        timeNow=timeNow.substring(0,5);
        templateData.put("TIME_NOW", timeNow);
        
        
        HttpSession session = req.getSession(true);
        
        String azienda =  (String) session.getAttribute("AZIENDA_SESSIONE");
        String risorsa = (String) session.getAttribute("ID_DIPENDENTE_SESSIONE");        
        
        templateData.put("AZIENDA_SESSIONE", azienda);
        templateData.put("ID_DIPENDENTE_SESSIONE", risorsa);
    
        templateData.put("COMPOSED_WHERE_COND", " WHERE TIMBRATURE.ID_DIPENDENTE='"+risorsa+"'");
                
        TimbratureDAO timbratura=new TimbratureDAO();
        
        timbratura.setField(TimbratureDAO.DATA_TIMBRATURA, Utils.ribaltaData(dateNow));
        timbratura.setField(TimbratureDAO.ORA_TIMBRATURA, timeNow);
        timbratura.setField(TimbratureDAO.ID_AZIENDA, azienda);
        timbratura.setField(TimbratureDAO.ID_DIPENDENTE, risorsa);
        timbratura.setField(TimbratureDAO.ID_TIMBRATURA, Utils.getUnique());
        timbratura.insert();
        
        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        mostra(req, res, userInfo);

    }
   
}
