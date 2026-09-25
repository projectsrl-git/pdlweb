
package net.projectsrl.webapp.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.errors.Logger;
import net.project.errors.ParamCrash;
import net.project.misc.Config;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletResponse;

public abstract class FunctionDownload_base extends FunctionProjectWebApp_base {

    public FunctionDownload_base(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    protected void downloadFile(SsbServletResponse response, String fileName, String type) throws ParamCrash, AppCrash {

        ErrDetector.GetInstance().param(Util.IsNotEmpty(fileName), "File Name can't be null or empty");

        String dirPath = Config.GetInstance().getProperty("directory.external_files", _applicationSrv.getRoot());

        String fullPath = fileName;
        if (!fileName.contains(dirPath)) {
            fullPath = dirPath + File.separator + fileName;
        }

        try {
            File file = new File(fullPath);
            ErrDetector.GetInstance().param(file.exists(), "File doesn't exists on server.");

            Logger.GetInstance().log0("FunctionDownload - File location on server::" + file.getAbsolutePath());

            InputStream fis = new FileInputStream(file);
            String mimeType = getContentType(fileName);
            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            if (type != null) {
                if (type.equals("preview")) {
                    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                }
            }

            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "Error downloading file fileName:" + fileName);
            throw ac;
        }
    }

    private String getContentType(String fileName) {

        String extension[] = { // File Extensions
                "txt", // 0 - plain text
                "htm", // 1 - hypertext
                "jpg", // 2 - JPEG image
                "jpeg", // 2 - JPEG image
                "png", // 2 - JPEG image
                "gif", // 3 - gif image
                "pdf", // 4 - adobe pdf
                "doc", // 5 - Microsoft Word
                "docx", // 5 - Microsoft Word
                "zip", // 6 - Zip
        }; // you can add more
        String mimeType[] = { // mime types
                "text/plain", // 0 - plain text
                "text/html", // 1 - hypertext
                "image/jpeg", // 2 - image
                "image/jpeg", // 2 - image
                "image/png", // 2 - image
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

}
