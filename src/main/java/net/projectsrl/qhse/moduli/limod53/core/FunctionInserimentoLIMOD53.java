
package net.projectsrl.qhse.moduli.limod53.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.qhse.moduli.core.FunctionInserimentoALIMOD_base;
import net.projectsrl.qhse.moduli.limod53.db.LiMod53DAO;

public class FunctionInserimentoLIMOD53 extends FunctionInserimentoALIMOD_base<LiMod53DAO> {

    public FunctionInserimentoLIMOD53(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(LiMod53DAO.ID_MODULO, "");

            templateData.put(LiMod53DAO.ID_UTENTE_CON, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(LiMod53DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
            templateData.put(LiMod53DAO.STATO, StatiRichiesta.DRAFT.getCode());

        } else {
            PjNDAO_base rowToUpdate = new LiMod53DAO();
            String idRow = req.getField(LiMod53DAO.ID_MODULO);
            rowToUpdate.setAttribute(LiMod53DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), LiMod53DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

}
