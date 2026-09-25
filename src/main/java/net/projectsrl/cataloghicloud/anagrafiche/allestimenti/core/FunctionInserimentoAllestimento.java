
package net.projectsrl.cataloghicloud.anagrafiche.allestimenti.core;

import java.io.PrintWriter;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.cataloghicloud.anagrafiche.allestimenti.db.AllestimentiDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoAllestimento extends FunctionAjaxForm_base<AllestimentiDAO> {

    public FunctionInserimentoAllestimento(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }
    

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        if (isAnInsert(req)) {
            templateData.put(AllestimentiDAO.ID_ALLESTIMENTO, "");

        } else {
            PjNDAO_base rowToUpdate = new AllestimentiDAO();
            String idRow = req.getField(AllestimentiDAO.ID_ALLESTIMENTO);
            rowToUpdate.setAttribute(AllestimentiDAO.ID_ALLESTIMENTO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), AllestimentiDAO.ID_ALLESTIMENTO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }        

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }    

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(AllestimentiDAO.ID_ALLESTIMENTO));
    }

    @Override
    protected void insert(SsbServletRequest req, AllestimentiDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        formDao.insert();
    }    
    
    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message, AllestimentiDAO formDao) {

        try {
            PrintWriter out = res.getWriter();
            
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message + "'}";
            
//            String codiceFamiglia=(String) formDao.getAttribute(AllestimentiDAO.CODICE_ALLESTIMENTO);
//            
//            if (codiceFamiglia != null) {
//                FamigliaProdottiGasDAO dao=new FamigliaProdottiGasDAO();
//                dao.setAttribute(FamigliaProdottiGasDAO.CODICE_FAMIGLIA, codiceFamiglia);
//                dao.retrieve();
//                Integer idRecord=(Integer) dao.getAttribute(FamigliaProdottiGasDAO.ID_FAMIGLIA);
//                resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message + "',\"idRecord\":" + idRecord + "}";
//            }

            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }
    
}
