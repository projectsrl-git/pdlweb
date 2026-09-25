package net.projectsrl.wm.jobmanagement.core;

import java.util.HashMap;

import it.project.iride.core.FunctionMostraPaginaConDataset;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;

/**
 * FunctionPianificazioneTurni
 * 
 */
public class FunctionPianificazioneTurni extends FunctionMostraPaginaConDataset {
 
    

    public FunctionPianificazioneTurni() {
        super();
    }

    public FunctionPianificazioneTurni(ApplicationServices_itf applServices, String functionID, String functionName) {
        super(applServices, functionID, functionName);

    }



    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);


        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        mostra(req, res, userInfo);

    }
   
}
