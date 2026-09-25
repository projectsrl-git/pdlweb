
package net.projectsrl.cataloghicloud.prodottigas.core;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.cataloghicloud.anagrafiche.db.ProdottiGasDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoProdottiGas extends FunctionAjaxForm_base<ProdottiGasDAO> {

    public FunctionInserimentoProdottiGas(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(ProdottiGasDAO.ID_PRODOTTI_GAS));
    }

    @Override
    protected void insert(SsbServletRequest req, ProdottiGasDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        formDao.insert();
    }    
}
