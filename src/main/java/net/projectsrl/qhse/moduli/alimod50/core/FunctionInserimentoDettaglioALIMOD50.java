
package net.projectsrl.qhse.moduli.alimod50.core;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.qhse.moduli.alimod50.db.AliMod50DettagliDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoDettaglioALIMOD50 extends FunctionAjaxForm_base<AliMod50DettagliDAO> {

    public FunctionInserimentoDettaglioALIMOD50(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(AliMod50DettagliDAO.ID_DETTAGLIO));
    }

    @Override
    protected void insert(SsbServletRequest req, AliMod50DettagliDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        formDao.insert();
    }    
}
