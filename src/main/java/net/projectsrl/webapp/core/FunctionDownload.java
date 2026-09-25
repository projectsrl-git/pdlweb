
package net.projectsrl.webapp.core;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;

public class FunctionDownload extends FunctionDownload_base {

    public FunctionDownload(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean checkSession(SsbServletRequest req) {

        return true;
    }

    @Override
    public boolean isAccessFree() {

        return true;
    }

    @Override
    public boolean isAuthenticationRequired() {

        return false;
    }

    @Override
    public void mostra(SsbServletRequest request, SsbServletResponse response, UserSecurityInfo userInfo)
            throws AppCrash {

        String fileName = request.getParameter("fileName");
        fileName = fileName.replace("Ã ", "à");
        String type = request.getParameter("type");

        downloadFile(response, fileName, type);
    }

}
