
package net.projectsrl.dafne.richieste.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.db.RendicontazioniDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

/**
 * FunctionInserimentoRendicontazioni
 */
public class FunctionInserimentoRendicontazioni extends FunctionAjaxForm_base<RendicontazioniDAO> {

    public FunctionInserimentoRendicontazioni(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(RendicontazioniDAO.ID_RENDICONTAZIONE, "");
        } else {
            PjNDAO_base rendicontazioni = new RendicontazioniDAO();
            String idRendicontazione = req.getField(RendicontazioniDAO.ID_RENDICONTAZIONE);
            rendicontazioni.setAttribute(RendicontazioniDAO.ID_RENDICONTAZIONE, idRendicontazione);
            ErrDetector.GetInstance().preCond(rendicontazioni.retrieve(),
                    RendicontazioniDAO.ID_RENDICONTAZIONE + " not found");
            rendicontazioni.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(RendicontazioniDAO.ID_RENDICONTAZIONE));
    }

}
