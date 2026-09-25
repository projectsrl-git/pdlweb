
package net.projectsrl.pdlweb.pdl.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.utils.Utils;

public class FunctionStampaPDLMultipli extends FunctionStampaPDL {

    public FunctionStampaPDLMultipli(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);

    }

    @Override
    public boolean isAuthenticationRequired() {

        return false;
    }

    @Override
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        String directory = _applicationSrv.getRoot() + "/Output/";

        req.setField("DATA_OGGI", req.getField("DATA_OGGI_ZIP")); 
        
        String elencoPdl = req.getField("ID_MODULO_STAMPA");
        String zipName = _applicationSrv.getRoot() + "/Output/Raccolta_Pdl_"+Utils.getStringDataOggiTrattino()+".zip";
        
        String filename =null;
		String[] strArray = elencoPdl.split(",");
		ZipOutputStream out;
		FileInputStream in = null;
		String nomeFile=null;
		
		try {
			out = new ZipOutputStream(new FileOutputStream(zipName));
		
		
		for (String idSingoloPdl : strArray) {
			req.setField("ID_PDL", idSingoloPdl);
			filename = creaFilePdf(req, userInfo);
			nomeFile = filename.substring(filename.lastIndexOf("pdl"));
			
			in = new FileInputStream(filename);
			out.putNextEntry(new ZipEntry(nomeFile));
			byte[] b = new byte[1024];

			int count;

			while ((count = in.read(b)) > 0) {
				System.out.println();

				out.write(b, 0, count);
			}
		}
		
		out.close();
		in.close();
		
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
        

        File file = new File(zipName);
        String contentType = getContentType(zipName);
        System.out.println(contentType);
        res.setContentType(contentType);
        res.setHeader("Content-Disposition", "attachment; filename=" + zipName.replace(directory, ""));
        int length = (int) file.length();

        if (length > Integer.MAX_VALUE) {
        }

        byte[] bytes = new byte[length];

        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);

            fin.read(bytes);

            ServletOutputStream os = res.getOutputStream();
            os.write(bytes);
            os.flush();
        } catch (Throwable ac) {
            new AppCrash(ac);
        }

    }

   

}
