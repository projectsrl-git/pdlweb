
package net.projectsrl.bow.parameters;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.cataloghicloud.anagrafiche.db.FamigliaProdottiGasDAO;
import net.projectsrl.dafne.db.TableIdentity;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;


public class FunctionInserimentoLingueIso extends FunctionAjaxForm_base<FamigliaProdottiGasDAO> {

    public FunctionInserimentoLingueIso(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        if (isAnInsert(req)) {
            Integer nextFkRowId = new TableIdentity("PRODOTTI_GAS").getTableIdentity();
            templateData.put(FamigliaProdottiGasDAO.ID_FAMIGLIA, "");

            // altri campi nel form
            templateData.put("ID_PRODOTTI_GAS_NEXT", nextFkRowId);
        } else {
            PjNDAO_base rowToUpdate = new FamigliaProdottiGasDAO();
            String idRow = req.getField(FamigliaProdottiGasDAO.ID_FAMIGLIA);
            rowToUpdate.setAttribute(FamigliaProdottiGasDAO.ID_FAMIGLIA, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), FamigliaProdottiGasDAO.ID_FAMIGLIA + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

            // altri campi nel form
            templateData.put("ID_PRODOTTI_GAS_NEXT", idRow);

        }        

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(FamigliaProdottiGasDAO.ID_FAMIGLIA));
    }

    

}
