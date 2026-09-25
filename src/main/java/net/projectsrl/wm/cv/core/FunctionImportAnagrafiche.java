
package net.projectsrl.wm.cv.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.core.FunctionWebApp_base;
import net.projectsrl.wm.importdata.UploadedFiles;

/**
 * FunctionImportAnagrafiche
 * 
 */
public class FunctionImportAnagrafiche extends FunctionWebApp_base {


    private static final String _page        = "importanagrafiche";

    boolean        _cancellarePrecedenteCaricamento = false;
    
    public FunctionImportAnagrafiche() {

        super();
    }

    public FunctionImportAnagrafiche(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
    	
        HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);


        UploadedFiles.setTemplateData(templateData, UploadedFiles.PDFANAGRAFICHE);
        
        if (UploadedFiles.getStatus(UploadedFiles.PDFANAGRAFICHE).equals(UploadedFiles.UPDATE_COMPLETED)) {
            templateData.put("ESITO_KO", "");
            templateData.put("ESITO_OK", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFANAGRAFICHE)));
            UploadedFiles.setStatus(UploadedFiles.PDFANAGRAFICHE, UploadedFiles.UPDATE_COMPLETED_CV);
        } else {
            templateData.put("ESITO_KO", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFANAGRAFICHE)));
            templateData.put("ESITO_OK", "");
            UploadedFiles.setStatus(UploadedFiles.PDFANAGRAFICHE, UploadedFiles.UPDATE_COMPLETED_CV);
        }
        
        templateData.put("MESSAGGIO_ATTESA", "Elaborazione in corso...");

        _applicationSrv.displayPage(_page, templateData, res);

    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
       
        if (UploadedFiles.getStatus(UploadedFiles.PDFANAGRAFICHE).equals(UploadedFiles.UPDATE_COMPLETED_CV)) {
            templateData.put("ESITO_KO", "");
            templateData.put("ESITO_OK", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFANAGRAFICHE)));
            UploadedFiles.setStatus(UploadedFiles.PDFANAGRAFICHE, UploadedFiles.UPDATE_COMPLETED_CV);
        } else {
            templateData.put("ESITO_KO", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFANAGRAFICHE)));
            templateData.put("ESITO_OK", "");
            UploadedFiles.setStatus(UploadedFiles.PDFANAGRAFICHE, UploadedFiles.UPDATE_COMPLETED_CV);
        }
    
        UploadedFiles.setTemplateData(templateData, UploadedFiles.PDFANAGRAFICHE);

        _applicationSrv.displayPage(_page, templateData, res);
    }

}
