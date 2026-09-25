
package net.projectsrl.wm.importdata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.core.FunctionWebApp_base;
import net.projectsrl.wm.db.FeriePermessiAtchDAO;
import net.projectsrl.wm.utils.Utils;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 * FunctionFileUpload
 * 
 */
public class FunctionFileUploadAllegatiRichieste extends FunctionWebApp_base {

    private static final String PAGE                     = "inserimento_feriepermessi";
    private static final int    _sizeMax                 = 100000000;

    public FunctionFileUploadAllegatiRichieste() {

        super();
    }

    public FunctionFileUploadAllegatiRichieste(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = (HashMap<String, Object>) setCommonTags(req, userInfo);
        templateData=setTemplateDataFromRequest(templateData,req);
        templateData.put("SUBMENU", getFunctionID());
        templateData.put("SUBMENU_BREVE", getFunctionID().substring(0, 2));
        templateData.put("RUOLO_SESSIONE", getSessionRole(req));
        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
        

        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req,templateData), res);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = setCommonTags(req, userInfo);
        templateData = setTemplateDataFromRequest(templateData, req);

        String idFeriePermesso=templateData.get("ID_FERIEPERMESSO").toString();
        String idDipendente=templateData.get("DIPENDENTE").toString();
        String percorso=_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.upload.richieste");
        uploadFiles(templateData, req, idDipendente, percorso, idFeriePermesso);
       
        String nomeFileImportato = (String) req.getSession(false).getAttribute("FILE_NAME_CEDOLINO");
        
        scriveTabellaAllegati(templateData, nomeFileImportato, idDipendente, idFeriePermesso);

        UploadedFiles.logUploadingStatus();

        try {
            res.sendRedirect("astro?FUNCTIONID=InserimentoFeriePermessi&ID_FERIEPERMESSO="+idFeriePermesso+"&OPZIONE_INSERIMENTO_MODIFICA=MODIFICA");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void scriveTabellaAllegati(HashMap<String, Object> templateData, String nomeFileImportato, String idDipendente, String idFeriePermesso) throws AppCrash {

        DBTransaction dbTransaction;
        dbTransaction = new DBTransaction();
        FeriePermessiAtchDAO fp = new FeriePermessiAtchDAO(dbTransaction);
        fp.setField(FeriePermessiAtchDAO.ID_FERIEPERMESSO, idFeriePermesso);
        fp.setField(FeriePermessiAtchDAO.ID_FERIEPERMESSO_ATCH, Utils.getUnique());
        fp.setField(FeriePermessiAtchDAO.FILENAME, nomeFileImportato);
        fp.setField(FeriePermessiAtchDAO.DATA_CARICAMENTO, Utils.getStringDataOggiRibaltata());
        fp.setField(FeriePermessiAtchDAO.ORA_CARICAMENTO, Utils.getOrario());
        fp.setField(FeriePermessiAtchDAO.DIPENDENTE, idDipendente);
        fp.insert();
        dbTransaction.commit();
        dbTransaction.end();
    }

    @SuppressWarnings("deprecation")
    private boolean uploadFiles(HashMap<String, Object> templateData, SsbServletRequest req, String idDipendente, String percorso, String idFeriePermesso) {

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
                    req.getSession(false).setAttribute("FILE_NAME_CEDOLINO", fileName);

                    String dirAllegati=percorso+idDipendente+"/";
                    new File(dirAllegati).mkdir();
                    dirAllegati=percorso+idDipendente+"/"+idFeriePermesso+"/";
                    new File(dirAllegati).mkdir();
                    
                    File fNew = new File(percorso+idDipendente+"/"+idFeriePermesso+"/", fileName);
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
