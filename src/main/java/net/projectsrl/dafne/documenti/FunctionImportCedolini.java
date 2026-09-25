
package net.projectsrl.dafne.documenti;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.wm.importdata.UploadedFiles;

/**
 * FunctionImportCedolini
 * 
 */
public class FunctionImportCedolini extends FunctionProjectWebApp_base {


    private static final String _page        = "importcedolini";

    boolean        _cancellarePrecedenteCaricamento = false;
    
    public FunctionImportCedolini() {

        super();
    }

    public FunctionImportCedolini(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
    	
    	Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        //templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);


        //UploadedFiles.setTemplateData(templateData, UploadedFiles.PDFCEDOLINI);
        
        if (UploadedFiles.getStatus(UploadedFiles.PDFCEDOLINI).equals(UploadedFiles.UPDATE_COMPLETED)) {
            templateData.put("ESITO_KO", "");
            templateData.put("ESITO_OK", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFCEDOLINI)));
            UploadedFiles.setStatus(UploadedFiles.PDFCEDOLINI, UploadedFiles.UPDATE_CED_COMPLETED);
        } else {
            templateData.put("ESITO_KO", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFCEDOLINI)));
            templateData.put("ESITO_OK", "");
            UploadedFiles.setStatus(UploadedFiles.PDFCEDOLINI, UploadedFiles.UPDATE_CED_COMPLETED);
        }
        
        templateData.put("MESSAGGIO_ATTESA", "Elaborazione in corso...");

        _applicationSrv.displayPage(_page, templateData, res);

    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

    	Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        //templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
       
        if (UploadedFiles.getStatus(UploadedFiles.PDFCEDOLINI).equals(UploadedFiles.UPDATE_CED_COMPLETED)) {
            templateData.put("ESITO_KO", "");
            templateData.put("ESITO_OK", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFCEDOLINI)));
            UploadedFiles.setStatus(UploadedFiles.PDFCEDOLINI, UploadedFiles.UPDATE_CED_COMPLETED);
        } else {
            templateData.put("ESITO_KO", UploadedFiles.getStatusMessage(UploadedFiles
                    .getStatus(UploadedFiles.PDFCEDOLINI)));
            templateData.put("ESITO_OK", "");
            UploadedFiles.setStatus(UploadedFiles.PDFCEDOLINI, UploadedFiles.UPDATE_CED_COMPLETED);
        }
    
        //UploadedFiles.setTemplateData(templateData, UploadedFiles.PDFCEDOLINI);
        

        _applicationSrv.displayPage(_page, templateData, res);
    }

}
