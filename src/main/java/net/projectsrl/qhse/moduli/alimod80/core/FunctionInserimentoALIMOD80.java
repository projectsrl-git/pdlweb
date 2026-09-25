
package net.projectsrl.qhse.moduli.alimod80.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.qhse.moduli.alimod80.db.AliMod80DAO;
import net.projectsrl.qhse.moduli.core.FunctionInserimentoALIMOD_base;


public class FunctionInserimentoALIMOD80 extends FunctionInserimentoALIMOD_base<AliMod80DAO>  {

    public FunctionInserimentoALIMOD80(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        if (isAnInsert(req)) {
            templateData.put(AliMod80DAO.ID_MODULO, "");
            templateData.put(AliMod80DAO.ID_UTENTE_CON, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(AliMod80DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
            templateData.put(AliMod80DAO.STATO, StatiRichiesta.DRAFT.getCode());
        } else {
            PjNDAO_base rowToUpdate = new AliMod80DAO();
            String idRow = req.getField(AliMod80DAO.ID_MODULO);
            rowToUpdate.setAttribute(AliMod80DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), AliMod80DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }        
            
        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

}
