
package net.projectsrl.pdlweb.registri;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.wm.utils.Utils;

public class FunctionRegistroPermessiScaduti extends FunctionProjectWebApp_base {

    public FunctionRegistroPermessiScaduti(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        String anno=Utils.getAnnoOggi();
        templateData.put("ANNO", anno);

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }
}
