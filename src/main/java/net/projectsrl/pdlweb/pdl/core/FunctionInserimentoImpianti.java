
package net.projectsrl.pdlweb.pdl.core;

import java.io.PrintWriter;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.pdlweb.pdl.db.ImpiantiDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;


public class FunctionInserimentoImpianti extends FunctionAjaxForm_base<ImpiantiDAO> {

    public FunctionInserimentoImpianti(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        if (isAnInsert(req)) {
            templateData.put(ImpiantiDAO.ID_IMPIANTO, "");
            
        } else {
            PjNDAO_base rowToUpdate = new ImpiantiDAO();
            String idRow = req.getField(ImpiantiDAO.ID_IMPIANTO);
            rowToUpdate.setAttribute(ImpiantiDAO.ID_IMPIANTO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), ImpiantiDAO.ID_IMPIANTO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }        
            
        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(ImpiantiDAO.ID_IMPIANTO));
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message, ImpiantiDAO formDao) {

        try {
            PrintWriter out = res.getWriter();
            
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message + "'}";

            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }
    
    

}
