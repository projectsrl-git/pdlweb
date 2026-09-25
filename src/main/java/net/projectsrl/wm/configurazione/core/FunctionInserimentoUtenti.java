
package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoUtenti
 * 
 */
public class FunctionInserimentoUtenti extends FunctionInserimento {

    private static final String PAGE   = "inserimento_utenti";
    
    public FunctionInserimentoUtenti() {

        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetUtenti");
    }

    public FunctionInserimentoUtenti(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetUtenti");
    }

    @SuppressWarnings("unchecked")
    protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

        String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

        if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
            templateData.put("DATA_SC", "31/12/2999");
            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
            templateData.put("DATA", Utils.getStringDataOggi());
            templateData.put("ID_CODICE",Utils.getUnique());
            templateData.put("ATTIVO","S");
            templateData.put("AZIENDA",(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
            templateData.put("AZIENDA_TENDINA", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
            return templateData;
        }
        return templateData;

    }
    
    
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

	    HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
	    saveVarStandard(templateData, req,res);
	    templateData.put("SALVATO", "Salvataggio effettuato con successo");
	    templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
	    String idUtente=req.getField("ID_CODICE");
	    String idDipendente=req.getField("ID_DIPENDENTE");
	    String sqlUpdateIdUtenteInDipendenti ="";
	    if (!idDipendente.equals("")){
	    	sqlUpdateIdUtenteInDipendenti ="update dipendenti set id_utente = '"+idUtente+"' where id_dipendente='"+idDipendente+"'";
	    }else{
	    	sqlUpdateIdUtenteInDipendenti ="update dipendenti set id_utente = '' where id_utente='"+idUtente+"'";
	    }
	    net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateIdUtenteInDipendenti);

	    _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
    }

}
