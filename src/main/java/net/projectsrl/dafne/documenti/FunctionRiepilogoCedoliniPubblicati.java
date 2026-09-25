
package net.projectsrl.dafne.documenti;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;

public class FunctionRiepilogoCedoliniPubblicati extends FunctionProjectWebApp_base  {
	 
	 private static final String PAGE = "riepilogocedolinipubblicati";

    public FunctionRiepilogoCedoliniPubblicati() {

        super();
    }

    public FunctionRiepilogoCedoliniPubblicati(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }


    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

    	Map<String, Object> templateData = createMapFromRequest(req, userInfo);
		//HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
		//templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
	
		_applicationSrv.displayPage(PAGE, templateData, res);
	}

}

