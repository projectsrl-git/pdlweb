
package net.projectsrl.pdlweb.anagrafiche.core;

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
import net.projectsrl.pdlweb.anagrafiche.db.AreaLavoroDAO;
import net.projectsrl.pdlweb.interferenze.core.CreaMappaImpiantoArea;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoAreaLavoro extends FunctionAjaxForm_base<AreaLavoroDAO> {

    public FunctionInserimentoAreaLavoro(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            
        } else {
            PjNDAO_base rowToUpdate = new AreaLavoroDAO();
            String idRow = req.getField(AreaLavoroDAO.ID_AREA);
            rowToUpdate.setAttribute(AreaLavoroDAO.ID_AREA, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), AreaLavoroDAO.ID_AREA + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

        }
        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }



    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
            AreaLavoroDAO formDao) {

        try {

            Integer idArea = (Integer) formDao.getAttribute(AreaLavoroDAO.ID_AREA);
            Integer idImpianto = (Integer) formDao.getAttribute(AreaLavoroDAO.ID_IMPIANTO);

            CreaMappaImpiantoArea creaMappa = new CreaMappaImpiantoArea(idImpianto,formDao);
            String fileNamePlanimetria = creaMappa.creaMappaArea(_applicationSrv.getRoot(),"");

            PrintWriter out = res.getWriter();
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"fileNamePlanimetria\":'" + fileNamePlanimetria + "',\'idRow\':"+idArea+"}";
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(AreaLavoroDAO.ID_AREA));
    }

}
