
package net.projectsrl.pdlweb.moduli.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import net.project.errors.AppCrash;
import net.project.misc.Base64;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdlweb.moduli.db.LiModDAO_base;
import net.projectsrl.pdlweb.moduli.limod70.db.LiMod70DAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public abstract class FunctionInserimentoLIMOD_base<D extends LiModDAO_base> extends FunctionAjaxForm_base<D> {

    public FunctionInserimentoLIMOD_base(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(LiMod70DAO.ID_MODULO));
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message, D formDao) {

        try {
            PrintWriter out = res.getWriter();

            Integer id = (Integer) formDao.getAttribute(LiModDAO_base.ID_MODULO);
            
            String nr = "";
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"id\":" + id + ",\"nr\":'" + nr + "'}";
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }



    @Override
    protected void update(SsbServletRequest req, D formDao, UserSecurityInfo userInfo) throws AppCrash {

        String idModulo = req.getField(LiModDAO_base.ID_MODULO);

        formDao.setAttribute(LiModDAO_base.ID_MODULO, idModulo);

        formDao.retrieve();

        formDao.setAttributesFromRequest(req);

        formDao.update();
    }

    

    @Override
    protected void onSuccess(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo, D formDao) throws AppCrash {
        //SendMail mail=new SendMail(getSpecificUserInfo(userInfo));
        //mail.send("noreply@alirep.net", "fabiano.moda@gmail.com", "noreply@alirep.net", "fabiano.moda.projectsrl@gmail.com", "oggetto prova", "body prova");
    }

    protected String creaFirma(String fileName, String signFromPage) throws AppCrash {
    
        String imageCode = signFromPage;
    
        String imageString = imageCode.split(",")[0];
        // tokenize the data
    
        // create a buffered image
        BufferedImage image = new BufferedImage(350, 200, BufferedImage.TYPE_BYTE_GRAY);
        byte[] imageByte = null;
    
        File outputfile = null;
    
        String signImageFileName = null;
    
        try {
            imageByte = Base64.decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
    
            // write the image to a file
            outputfile = new File(_applicationSrv.getRoot() + "/Output/" + fileName);
            ImageIO.write(image, "png", outputfile);
            signImageFileName = outputfile.toString();
        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "error creating digital sign - signImageFileName:"+signImageFileName+ " - imageCode:"+imageCode);
        }
    
        return signImageFileName;
    
    }
    
}