
package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;

/**
 * FunctionInserimentoVisitatori
 * 
 */
public class FunctionInserimentoImpostazioniBase extends FunctionInserimento {

    private static final String PAGE   = "inserimento_impostazionibase";
    
    public FunctionInserimentoImpostazioniBase() {

        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetImpostazioniBase");
    }

    public FunctionInserimentoImpostazioniBase(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetImpostazioniBase");
    }

    @SuppressWarnings("unchecked")
    protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

        String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

        if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
            return templateData;
        }
        return templateData;

    } 
}
