
package net.projectsrl.cataloghicloud.prodottigas.core;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.cataloghicloud.anagrafiche.db.ProdottiGasComposizioneDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoComponente extends FunctionAjaxForm_base<ProdottiGasComposizioneDAO> {

    public FunctionInserimentoComponente(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(ProdottiGasComposizioneDAO.ID_PRODOTTI_GAS_COMP));
    }

    @Override
    protected void insert(SsbServletRequest req, ProdottiGasComposizioneDAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        formDao.setAttributesFromRequest(req);
        formDao.insert();
    }    
}
