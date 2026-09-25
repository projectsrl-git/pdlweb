
package net.projectsrl.dafne.richieste.core;

import java.io.PrintWriter;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.json.JsonDAOResult;
import net.projectsrl.dafne.json.JsonResult;
import net.projectsrl.dafne.richieste.db.TipoRichiesteAziendaDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

/**
 * FunctionInserimentoTipiRichiestaAzienda
 */
public class FunctionInserimentoTipiRichiestaAzienda extends FunctionAjaxForm_base<TipoRichiesteAziendaDAO> {

    public FunctionInserimentoTipiRichiestaAzienda(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(TipoRichiesteAziendaDAO.ID_TIPO_RICHIESTA_AZIENDA, "");
        } else {
            PjNDAO_base rowToUpdate = new TipoRichiesteAziendaDAO();
            String idRow = req.getField(TipoRichiesteAziendaDAO.ID_TIPO_RICHIESTA_AZIENDA);
            rowToUpdate.setAttribute(TipoRichiesteAziendaDAO.ID_TIPO_RICHIESTA_AZIENDA, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(),
                    TipoRichiesteAziendaDAO.ID_TIPO_RICHIESTA_AZIENDA + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(TipoRichiesteAziendaDAO.ID_TIPO_RICHIESTA_AZIENDA));
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
            TipoRichiesteAziendaDAO formDao) {

        try {
            PrintWriter out = res.getWriter();

            JsonResult jsonResult = new JsonDAOResult(result, title, message, formDao);

            String resultString = jsonResult.createJsonString();
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }
    
}
