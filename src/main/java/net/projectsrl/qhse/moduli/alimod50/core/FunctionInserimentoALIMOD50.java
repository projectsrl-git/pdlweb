
package net.projectsrl.qhse.moduli.alimod50.core;

import java.io.File;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.qhse.moduli.alimod50.db.AliMod50DAO;
import net.projectsrl.qhse.moduli.core.FunctionInserimentoALIMOD_base;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class FunctionInserimentoALIMOD50 extends FunctionInserimentoALIMOD_base<AliMod50DAO> {

    public FunctionInserimentoALIMOD50(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(AliMod50DAO.ID_MODULO, "");

            templateData.put(AliMod50DAO.ID_UTENTE_CON, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(AliMod50DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
            templateData.put(AliMod50DAO.STATO, StatiRichiesta.DRAFT.getCode());

        } else {
            PjNDAO_base rowToUpdate = new AliMod50DAO();
            String idRow = req.getField(AliMod50DAO.ID_MODULO);

            String fileFirma1 = "Output/alimod50_firma1_" + idRow + ".png";
            File firma1 = new File(_applicationSrv.getRoot() + "/" + fileFirma1);
            if (firma1.exists()) {
                templateData.put("FILE_FIRMA1", fileFirma1);
            }

            rowToUpdate.setAttribute(AliMod50DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), AliMod50DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }
    
    @Override
    protected void update(SsbServletRequest req, AliMod50DAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        super.update(req, formDao, userInfo);
        String idModulo = req.getField(AliModDAO_base.ID_MODULO);
        creaFirma("alimod50_firma1_" + idModulo + ".png", req.getField("FIRMA1"));

    }

}
