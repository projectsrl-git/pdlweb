
package net.projectsrl.dafne.risorse;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.db.RisorseDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

/**
 * FunctionInserimentoRisorse
 */
public class FunctionInserimentoRisorse extends FunctionAjaxForm_base<RisorseDAO> {

    public FunctionInserimentoRisorse(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(RisorseDAO.ID_RISORSA, "");
        } else {
            PjNDAO_base rowToUpdate = new RisorseDAO();
            String idRow = req.getField(RisorseDAO.ID_RISORSA);
            rowToUpdate.setAttribute(RisorseDAO.ID_RISORSA, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), RisorseDAO.ID_RISORSA + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(RisorseDAO.ID_RISORSA));
    }

}
