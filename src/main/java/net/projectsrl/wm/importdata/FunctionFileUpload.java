
package net.projectsrl.wm.importdata;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.core.FunctionWebApp_base;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 * FunctionFileUpload
 * 
 */
public class FunctionFileUpload extends FunctionWebApp_base {

    private static final String PAGE        = "file_upload";
    private static final String PAGE_CHIUDI = "file_upload_chiudi";
    private static final int    _sizeMax    = 100000000;

    public FunctionFileUpload() {

        super();
    }

    public FunctionFileUpload(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);

        templateData.put(UploadedFiles.FILE_TYPE, req.getField(UploadedFiles.FILE_TYPE));
        templateData.put("MESSAGGIO_ATTESA", "Trasferimento file in corso...");

        _applicationSrv.displayPage(PAGE, templateData, res);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(templateData, req);

        uploadFiles(req);

        UploadedFiles.logUploadingStatus();
        _applicationSrv.displayPage(PAGE_CHIUDI, templateData, res);

    }

    @SuppressWarnings("deprecation")
    private boolean uploadFiles(SsbServletRequest req) {

        String fileType = req.getField(UploadedFiles.FILE_TYPE);

        String fileName = "";
        DiskFileUpload fu = new DiskFileUpload();
        // If file size exceeds, a FileUploadException will be thrown
        fu.setSizeMax(_sizeMax);
        try {
            List<FileItem> fileItems = fu.parseRequest(req);
            Iterator<FileItem> itr = fileItems.iterator();

            // ciclo per i file
            while (itr.hasNext()) {
                FileItem fi = (FileItem) itr.next();

                // Check if not form field so as to only handle the file inputs
                // else condition handles the submit button input
                if (!fi.isFormField()) {
                    fileName = fi.getName();

                    int positionOfLastSlash = fileName.lastIndexOf("\\");
                    fileName = fileName.substring(positionOfLastSlash + 1);

                    File fNew = new File(UploadedFiles.getDirectory(), fileName);
                    UploadedFiles.setStatus(fileType, UploadedFiles.UPLOADING, fileName, getSessionUser(req));

                    fi.write(fNew);

                    UploadedFiles.setStatus(fileType, UploadedFiles.UPLOAD_COMPLETED, fileName, getSessionUser(req));
                }
            }

            return true;

        } catch (Throwable e) {
            new AppCrash(e);
            UploadedFiles.setStatus(fileType, UploadedFiles.UPLOAD_ERROR, fileName, getSessionUser(req));
            return false;
        }

    }
}
