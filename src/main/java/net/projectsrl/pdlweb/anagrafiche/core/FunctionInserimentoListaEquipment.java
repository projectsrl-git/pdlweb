
package net.projectsrl.pdlweb.anagrafiche.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.pdlweb.anagrafiche.db.EquipmentDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoListaEquipment extends FunctionAjaxForm_base<EquipmentDAO> {

    public FunctionInserimentoListaEquipment(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        // indispensabile
        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
        } else {
            PjNDAO_base rowToUpdate = new EquipmentDAO();
            String idRow = req.getField(EquipmentDAO.ID_EQUIPMENT);
            rowToUpdate.setAttribute(EquipmentDAO.ID_EQUIPMENT, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), EquipmentDAO.ID_EQUIPMENT + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

        }

        // indispensabile
        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(EquipmentDAO.ID_EQUIPMENT));
    }

}
