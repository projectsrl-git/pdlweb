package net.projectsrl.wm.timesheets.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.core.FunctionWebApp_base;

/**
 * FunctionRicercaAttivita
 * 
 */
public class FunctionRicercaAttivita extends FunctionWebApp_base {

	public static final String PAGE = "ricerca_attivita";

	public FunctionRicercaAttivita() {

		super();
	}

	public FunctionRicercaAttivita(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}

	public void mostra(SsbServletRequest req, SsbServletResponse res,UserSecurityInfo userInfo) throws AppCrash {
        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
   	 	templateData.put("ID_COMMESSA",req.getField("COMMESSA_ATTIVITA_ID"));
   	 	templateData.put("ANNO",req.getField("ANNO_T"));
   	 	templateData.put("MESE",req.getField("MESE_T"));
        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData),
                res);
	}

}
