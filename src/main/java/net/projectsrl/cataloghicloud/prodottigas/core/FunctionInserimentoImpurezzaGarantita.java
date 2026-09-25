
package net.projectsrl.cataloghicloud.prodottigas.core;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.cataloghicloud.anagrafiche.db.ImpurezzeGarantiteDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoImpurezzaGarantita extends FunctionAjaxForm_base<ImpurezzeGarantiteDAO> {

    public FunctionInserimentoImpurezzaGarantita(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(ImpurezzeGarantiteDAO.ID_IMPUREZZE_GARANTITE));
    }

    @Override
    protected void insert(SsbServletRequest req, ImpurezzeGarantiteDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        formDao.insert();
    }    
}
