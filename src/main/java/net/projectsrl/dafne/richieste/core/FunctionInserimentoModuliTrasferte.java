
package net.projectsrl.dafne.richieste.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.db.ModuliTrasferteDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

/**
 * FunctionInserimentoModuliTrasferte
 */
public class FunctionInserimentoModuliTrasferte extends FunctionAjaxForm_base<ModuliTrasferteDAO> {

    public FunctionInserimentoModuliTrasferte(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(ModuliTrasferteDAO.ID_MODTRASFERTA, "");
        } else {
            PjNDAO_base modulitrasferte = new ModuliTrasferteDAO();
            String idModTrasferta = req.getField(ModuliTrasferteDAO.ID_MODTRASFERTA);
            modulitrasferte.setAttribute(ModuliTrasferteDAO.ID_MODTRASFERTA, idModTrasferta);
            ErrDetector.GetInstance().preCond(modulitrasferte.retrieve(),
                    ModuliTrasferteDAO.ID_MODTRASFERTA + " not found");
            modulitrasferte.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(ModuliTrasferteDAO.ID_MODTRASFERTA));
    }

}
