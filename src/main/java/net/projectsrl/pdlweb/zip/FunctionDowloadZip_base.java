
package net.projectsrl.pdlweb.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;

import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.webapp.core.FunctionNoAuthentication;

public abstract class FunctionDowloadZip_base extends FunctionNoAuthentication {

    private static final String DOWNLOAD_DIR = Config.GetInstance().getProperty("Directory.download", "doc");

    public FunctionDowloadZip_base(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    protected abstract void createFolderToZip(File zipDirectory, SsbServletRequest req) throws AppCrash;

    @Override
    public boolean isAuthenticationRequired() {

        return false;
    }

    @Override
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        String zipDirectoryPath = generateZipDirectoryName();

        File zipDirectory = new File(zipDirectoryPath);
        if (!zipDirectory.exists()) {
            zipDirectory.mkdir();
        }

        createFolderToZip(zipDirectory, req);

        String fileOutZipFullPath = createZip(zipDirectory);

        if (allowDeleteDirectoryAfterZip()) {
            deleteDirectoryAfterZip(zipDirectory);
        }

        flushServletOutputStream(res, fileOutZipFullPath);

    }

    protected abstract boolean allowDeleteDirectoryAfterZip() ;
    
    protected void deleteDirectoryAfterZip(File zipDirectory) {

        for (String fileName : zipDirectory.list()) {
            File file = new File(zipDirectory.getAbsolutePath() + File.separator + fileName);
            file.delete();
        }
        zipDirectory.delete();
    }

    protected String generateZipDirectoryName() {

        String zipDirectoryPath = _applicationSrv.getRoot() + DOWNLOAD_DIR + File.separator
                + UUID.randomUUID().toString();

        return zipDirectoryPath;
    }

    /**
     * Questo metodo
     * 
     * @param csvList
     * @param fileOutZipFullPath
     */
    private String createZip(File zipDirectory) {

        // crea zip
        String fileOutZipFullPath = zipDirectory.getAbsolutePath() + ".zip";

        FileOutputStream zipFos = null;
        ZipOutputStream zos = null;

        try {
            zipFos = new FileOutputStream(fileOutZipFullPath);
            zos = new ZipOutputStream(zipFos);

            addFolderToZip(zipDirectory, zos);

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "error creating fileOutZipFullPath:" + fileOutZipFullPath);
        } finally {
            if (zos!=null) {
                try {
                    zos.close();
                } catch (Throwable e) {
                    AppCrash ac = new AppCrash(e);
                    ac.logContext(this.getClass().getName(), "error creating fileOutZipFullPath:" + fileOutZipFullPath);
                }                
            }
            if (zipFos!=null) {
                try {
                    zipFos.close();
                } catch (Throwable e) {
                    AppCrash ac = new AppCrash(e);
                    ac.logContext(this.getClass().getName(), "error creating fileOutZipFullPath:" + fileOutZipFullPath);
                }                
            }
        }

        return fileOutZipFullPath;
    }

    /**
     * Questo metodo
     * 
     * @param res
     * @param fileOutZipFullPath
     */
    private void flushServletOutputStream(SsbServletResponse res, String fileOutZipFullPath) {

        // crea output stream per il download
        FileInputStream fin = null;
        ServletOutputStream os = null;

        try {
            File fileOut = new File(fileOutZipFullPath);

            String contentType = getContentType(fileOutZipFullPath);
            res.setContentType(contentType);
            res.setHeader("Content-Disposition", "attachment; filename="+getDownloadedFilename());
            int length = (int) fileOut.length();

            if (length > Integer.MAX_VALUE) {
            }

            byte[] bytes = new byte[length];

            fin = new FileInputStream(fileOut);

            fin.read(bytes);

            os = res.getOutputStream();
            os.write(bytes);
            os.flush();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "error flushing fileOutZipFullPath:" + fileOutZipFullPath);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Throwable e) {
                    AppCrash ac = new AppCrash(e);
                    ac.logContext(this.getClass().getName(), "error closing ServletOutputStream");
                }
            }

            if (fin != null) {
                try {
                    fin.close();
                } catch (Throwable e) {
                    AppCrash ac = new AppCrash(e);
                    ac.logContext(this.getClass().getName(), "error closing FileInputStream");
                }
            }
        }
    }

    protected abstract String getDownloadedFilename(); 
    
    private String getContentType(String fileName) {

        String extension[] = { // File Extensions
        "txt", // 0 - plain text
                "htm", // 1 - hypertext
                "jpg", // 2 - JPEG image
                "png", // 2 - JPEG image
                "gif", // 3 - gif image
                "pdf", // 4 - adobe pdf
                "doc", // 5 - Microsoft Word
                "docx",// 5 - Microsoft Word
                "zip", // 6 - Zip
        }; // you can add more
        String mimeType[] = { // mime types
        "text/plain", // 0 - plain text
                "text/html", // 1 - hypertext
                "image/jpg", // 2 - image
                "image/jpg", // 2 - image
                "image/gif", // 3 - image
                "application/pdf", // 4 - Adobe pdf
                "application/msword", // 5 - Microsoft Word
                "application/msword", // 5 - Microsoft Word
                "application/zip", // 6 - zip
        }, // you can add more
        contentType = "text/html"; // default type
        // dot + file extension
        int dotPosition = fileName.lastIndexOf('.');
        // get file extension
        String fileExtension = fileName.substring(dotPosition + 1);
        // match mime type to extension
        for (int index = 0; index < mimeType.length; index++) {
            if (fileExtension.equalsIgnoreCase(extension[index])) {
                contentType = mimeType[index];
                break;
            }
        }
        return contentType;
    }

    private void addFileToZip(File file, ZipOutputStream zos) throws AppCrash {

        try {
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            zos.closeEntry();
            fis.close();
            // file.delete();
        } catch (Throwable e) {
            AppCrash ac = new AppCrash();
            ac.logContext(this.getClass().getName(), "Errore nella creazione del file zip");
        }
    }

    private void addFolderToZip(File folder, ZipOutputStream zos) throws Exception {

        /*
         * check the empty folder
         */
        if (folder.list().length == 0) {
            return;
        }

        /*
         * list the files in the folder
         */
        for (String fileName : folder.list()) {
            File file = new File(folder.getAbsolutePath() + File.separator + fileName);
            if (file.exists()) {
                addFileToZip(file, zos);
            }
        }
    }

}
