
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
import net.projectsrl.dafne.richieste.db.RichiesteDAO;
import net.projectsrl.dafne.richieste.db.TipiRichiestaDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

/**
 * FunctionInserimentoStraordinari
 */
public class FunctionInserimentoStraordinari extends FunctionAjaxForm_base<RichiesteDAO> {

    public FunctionInserimentoStraordinari(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
        	templateData.put(RichiesteDAO.ID_RICHIESTA, "");
        } else {
        	PjNDAO_base rowToUpdate = new RichiesteDAO();
            String idRow = req.getField(RichiesteDAO.ID_RICHIESTA);
            rowToUpdate.setAttribute(RichiesteDAO.ID_RICHIESTA, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), RichiesteDAO.ID_RICHIESTA + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

    	return Util.IsEmpty(req.getField(RichiesteDAO.ID_RICHIESTA));
    }
    
    
    @Override
    protected void insert(SsbServletRequest req, RichiesteDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        TipiRichiestaDAO tipoRichiesta = new TipiRichiestaDAO();
        tipoRichiesta.setAttribute(TipiRichiestaDAO.CODICE, TipiRichiesta.STA.toString());
        ErrDetector.GetInstance().preCond(tipoRichiesta.retrieve(),
                "not found " + TipiRichiestaDAO.CODICE + ":" + tipoRichiesta.getAttribute(TipiRichiestaDAO.CODICE));
        formDao.setAttribute(RichiesteDAO.ID_TIPO_RICHIESTA,
                tipoRichiesta.getAttribute(TipiRichiestaDAO.ID_TIPO_RICHIESTA));
        formDao.setAttribute(RichiesteDAO.ID_UTENTE_INS, getSpecificUserInfo(userInfo).getIdUtente());
        formDao.insert();

    }

    @Override
    protected void update(SsbServletRequest req, RichiesteDAO formDao, UserSecurityInfo userInfo) throws AppCrash {
        
        formDao.setAttributesFromRequest(req);

        formDao.setAttribute(RichiesteDAO.ID_UTENTE_MOD, getSpecificUserInfo(userInfo).getIdUtente());

        super.update(req, formDao, userInfo);
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
            RichiesteDAO formDao) {

        try {
            PrintWriter out = res.getWriter();
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"idRichiesta\":" + formDao.getAttribute(RichiesteDAO.ID_RICHIESTA) + ",\"statoRichiesta\":'" + formDao.getAttribute(RichiesteDAO.STATO_RICHIESTA) + "'}";
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }

    }

}
