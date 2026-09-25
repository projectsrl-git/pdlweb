
package net.projectsrl.dafne.richieste.core;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.db.TipiRichiestaDAO;

/**
 * FunctionApprovazioneFeriePermessi
 */
public class FunctionApprovazioneFeriePermessi extends FunctionInserimentoTipiRichiesta {

    public FunctionApprovazioneFeriePermessi(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected void update(SsbServletRequest req, TipiRichiestaDAO formDao, UserSecurityInfo userInfo) throws AppCrash {
        
        formDao.setAttributesFromRequest(req);
        
        
        formDao.update();
    }


}
