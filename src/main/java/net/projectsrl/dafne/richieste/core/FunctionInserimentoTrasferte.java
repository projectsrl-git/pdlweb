
package net.projectsrl.dafne.richieste.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.db.TrasferteDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

/**
 * FunctionInserimentoTrasferte
 */
public class FunctionInserimentoTrasferte extends FunctionAjaxForm_base<TrasferteDAO> {

    public FunctionInserimentoTrasferte(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(TrasferteDAO.ID_TRASFERTA, "");
        } else {
            PjNDAO_base trasferte = new TrasferteDAO();
            String idTrasferta = req.getField(TrasferteDAO.ID_TRASFERTA);
            trasferte.setAttribute(TrasferteDAO.ID_TRASFERTA, idTrasferta);
            ErrDetector.GetInstance().preCond(trasferte.retrieve(), TrasferteDAO.ID_TRASFERTA + " not found");
            trasferte.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(TrasferteDAO.ID_TRASFERTA));
    }

}
