package net.projectsrl.wm.creazioneguidata.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoDocAziendaCG
 * 
 */
public class FunctionInserimentoDocAziendaCG extends FunctionInserimento {

	private static final String PAGE = "inserimento_docazienda_cg";
	

	public FunctionInserimentoDocAziendaCG() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetDocAzienda");
	}

	public FunctionInserimentoDocAziendaCG(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetDocAzienda");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);  
		
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_DOCAZIENDA", Utils.getUnique());
			putIdInSession(req, templateData);
			return templateData;
			}
		
		putIdInSession(req, templateData);
		return templateData;

	}
	
	
	
	@SuppressWarnings("unchecked")
    private void putIdInSession(SsbServletRequest req, HashMap templateData) {

        String id = (String) templateData.get("ID_DOCAZIENDA");
        if (id == null || id.equals("")) {
        	id=req.getField("ID_DOCAZIENDA");  
            if (id == null || id.equals("")) {
                return;
            }            
        }
        
        String azienda = req.getField("AZIENDA"); 
        if (azienda == null || azienda.equals("")) {
        	azienda=(String) templateData.get("AZIENDA"); 
            if (azienda == null || azienda.equals("")) {
                return;
            }            
        }
        
        req.getSession(false).setAttribute(Costanti_itf.ID_DOCAZIENDA_SESSIONE, id);
        req.getSession(false).setAttribute(Costanti_itf.DOCAZIENDA_SESSIONE, azienda);
        templateData.put("AZIENDA_UPLOAD",azienda);
		templateData.put("MATRICOLA_UPLOAD", (String) req.getSession(false).getAttribute("MATRICOLA"));
    }
	
}

