
package net.projectsrl.qhse.moduli.alimod20.core;

import java.io.PrintWriter;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.qhse.moduli.alimod20.db.AliMod20DAO;
import net.projectsrl.qhse.moduli.core.FunctionInserimentoALIMOD_base;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class FunctionInserimentoALIMOD20 extends FunctionInserimentoALIMOD_base<AliMod20DAO> {

    public FunctionInserimentoALIMOD20(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(AliMod20DAO.ID_MODULO, "");

            templateData.put(AliMod20DAO.ID_UTENTE_CON, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(AliMod20DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
            templateData.put(AliMod20DAO.STATO, StatiRichiesta.DRAFT.getCode());

        } else {
            PjNDAO_base rowToUpdate = new AliMod20DAO();
            String idRow = req.getField(AliMod20DAO.ID_MODULO);
            rowToUpdate.setAttribute(AliMod20DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), AliMod20DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
            AliMod20DAO formDao) {

        try {
            PrintWriter out = res.getWriter();

            Integer id = (Integer) formDao.getAttribute(AliModDAO_base.ID_MODULO);

            
            Boolean flgAifa = (Boolean) formDao.getAttribute(AliMod20DAO.FLG_AIFA);

            String nr = (String) formDao.getAttribute(AliModDAO_base.NR_MODULO);
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"id\":" + id + ",\"nr\":'" + nr + "',\"aifa\":false}";
            
            if (flgAifa!=null) {
                resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                        + "',\"id\":" + id + ",\"nr\":'" + nr + "',\"aifa\":" + flgAifa.toString() + "}";
            }
            
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }


}
