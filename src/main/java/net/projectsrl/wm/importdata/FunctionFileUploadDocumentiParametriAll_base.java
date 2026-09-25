
package net.projectsrl.wm.importdata;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.project.errors.AppCrash;
import net.project.misc.Config;
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
public abstract class FunctionFileUploadDocumentiParametriAll_base extends FunctionWebApp_base {

    private static final String PAGE        = "file_upload";
    private static final String PAGE_CHIUDI = "file_upload_chiudi";
    private static final int    _sizeMax    = 100000000;

    public FunctionFileUploadDocumentiParametriAll_base() {

        super();
    }

    public FunctionFileUploadDocumentiParametriAll_base(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);

        templateData.put("MESSAGGIO_ATTESA", "Trasferimento file in corso...");

        _applicationSrv.displayPage(PAGE, templateData, res);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(templateData, req);

        String absolutePathFileName = uploadFiles(req);

        loadData(getSessionUser(req), absolutePathFileName, req);
        UploadedFiles.logUploadingStatus();		
        _applicationSrv.displayPage(getPaginaChiusuraPopup(), templateData, res);

    }

    protected String getPaginaChiusuraPopup() {

        return PAGE_CHIUDI;
    }

    protected abstract void loadData(String sessionUser, String absolutePathFileName, SsbServletRequest req)
            throws AppCrash;

    @SuppressWarnings("deprecation")
    private String uploadFiles(SsbServletRequest req) {

    	String fileType = req.getField(UploadedFiles.FILE_TYPE);
        String fileName = "";
        String uploadedFileName = "";
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

                    //CREARE CARTELLA AZIENDA SE NON ESISTE
                    //CREARE CARTELLA DIPENDENTE SE NON ESISTE
                    String dirAZIENDA =_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.documenti")+(String) req.getSession(false).getAttribute("DOCAZIENDA_SESSIONE")+"/";
                    new File(dirAZIENDA).mkdir();
                    String dirDocumentiAzienda=(String) req.getSession(false).getAttribute("DOCAZIENDA_SESSIONE");
                    
                    String directory = _applicationSrv.getRoot() + "areadocumenti";
                    File fNew = new File(directory, fileName);
                    UploadedFiles.setStatus(fileType, UploadedFiles.UPLOADING, fileName, getSessionUser(req));
                    fi.write(fNew);
                    uploadedFileName = fNew.getAbsolutePath();
                    UploadedFiles.setStatus(fileType, UploadedFiles.UPLOAD_COMPLETED, fileName, getSessionUser(req));

                }
            }

            req.getSession(false).setAttribute("FILE_UPLOADED", uploadedFileName);

            return uploadedFileName;
        } catch (Throwable e) {
            new AppCrash(e);
            return null;
        }

    }
}
