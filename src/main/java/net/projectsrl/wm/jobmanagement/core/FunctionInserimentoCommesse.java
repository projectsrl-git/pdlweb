
package net.projectsrl.wm.jobmanagement.core;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoCommesse
 * 
 */
public class FunctionInserimentoCommesse extends FunctionInserimento {

    private static final String PAGE   = "inserimento_commesse";

    public FunctionInserimentoCommesse() {

        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetCommesse");
    }

    public FunctionInserimentoCommesse(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetCommesse");
    }

    @SuppressWarnings("unchecked")
    protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

        String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

        if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
            templateData.put("ID_COMMESSA",Utils.getUnique());
            HttpSession session = req.getSession(true); 
            templateData.put("ID_AZIENDA",session.getAttribute("AZIENDA_SESSIONE"));
            templateData.put("ID_DIPENDENTE",session.getAttribute("ID_DIPENDENTE_SESSIONE"));
            templateData.put("VALIDITA_DAL","01/01/"+Utils.getAnnoOggi());
            templateData.put("VALIDITA_AL","31/12/"+Utils.getAnnoOggi());
            return templateData;
        }
        return templateData;

    }   

}
