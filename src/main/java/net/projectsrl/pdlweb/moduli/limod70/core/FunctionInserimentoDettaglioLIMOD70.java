
package net.projectsrl.pdlweb.moduli.limod70.core;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdlweb.moduli.limod70.db.LiMod70DettagliDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoDettaglioLIMOD70 extends FunctionAjaxForm_base<LiMod70DettagliDAO> {

    public FunctionInserimentoDettaglioLIMOD70(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(LiMod70DettagliDAO.ID_DETTAGLIO));
    }

    @Override
    protected void insert(SsbServletRequest req, LiMod70DettagliDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        formDao.insert();
    }    
}
