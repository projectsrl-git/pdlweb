
package net.projectsrl.qhse.moduli.alimod20.core;

import java.sql.Timestamp;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.qhse.moduli.alimod20.db.AliMod20DettagliDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoDettaglioALIMOD20 extends FunctionAjaxForm_base<AliMod20DettagliDAO> {

    public FunctionInserimentoDettaglioALIMOD20(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(AliMod20DettagliDAO.ID_DETTAGLIO));
    }


    @Override
    protected void update(SsbServletRequest req, AliMod20DettagliDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        
        Integer idUtente=(Integer) getSpecificUserInfo(userInfo).getIdUtente();
        
        formDao.setAttribute(AliMod20DettagliDAO.ID_UTENTE_MOD, idUtente);
        formDao.setAttribute(AliMod20DettagliDAO.TS_MOD, new Timestamp(System.currentTimeMillis()));
        
        
        formDao.update();
    }
    
}
