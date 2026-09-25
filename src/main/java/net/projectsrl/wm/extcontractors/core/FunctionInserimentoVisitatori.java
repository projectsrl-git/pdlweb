
package net.projectsrl.wm.extcontractors.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoVisitatori
 * 
 */
public class FunctionInserimentoVisitatori extends FunctionInserimento {

    private static final String PAGE   = "inserimento_visitatori";
    
    public FunctionInserimentoVisitatori() {

        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetVisitatori");
    }

    public FunctionInserimentoVisitatori(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetVisitatori");
    }

    @SuppressWarnings("unchecked")
    protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

        String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

        if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
            templateData.put("ID_VISITATORE",Utils.getUnique());
            return templateData;
        }
        return templateData;

    } 
}
