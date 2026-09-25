
package net.projectsrl.qhse.moduli.alimod67.core;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.qhse.moduli.alimod67.db.AliMod67DettagliDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoDettaglioALIMOD67 extends FunctionAjaxForm_base<AliMod67DettagliDAO> {

    public FunctionInserimentoDettaglioALIMOD67(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(AliMod67DettagliDAO.ID_DETTAGLIO));
    }

    @Override
    protected void insert(SsbServletRequest req, AliMod67DettagliDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        formDao.insert();
    }    
}
