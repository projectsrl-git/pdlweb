
package net.projectsrl.cataloghicloud.prodottigas.core;

import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.cataloghicloud.anagrafiche.allestimenti.db.SpecificheAllestimentoDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoSpecificheAllestimento extends FunctionAjaxForm_base<SpecificheAllestimentoDAO> {

    public FunctionInserimentoSpecificheAllestimento(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(SpecificheAllestimentoDAO.ID_ALLESTIMENTO));
    }


}
