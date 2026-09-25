
package net.projectsrl.wm.timesheets.core;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoTimbrature
 * 
 */
public class FunctionInserimentoTimbrature extends FunctionInserimentoSenzaControlloPreEsistenza {

    private static final String PAGE   = "inserimento_timbrature";

    public FunctionInserimentoTimbrature() {

        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetTimbrature");
    }

    public FunctionInserimentoTimbrature(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetTimbrature");
    }

    @SuppressWarnings("unchecked")
    protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

        String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

        if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
            templateData.put("ID_TIMBRATURA",Utils.getUnique());
            templateData.put("SCARTATA","N");
            HttpSession session = req.getSession(true); 
            templateData.put("ID_AZIENDA",session.getAttribute("AZIENDA_SESSIONE"));
            return templateData;
        }
        return templateData;

    }
    
    
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

    HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
    saveVarStandard(templateData, req,res);
    templateData.put("SALVATO", "Salvataggio effettuato con successo");
    
    _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
    }

}
