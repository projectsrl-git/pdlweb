package net.projectsrl.pdlweb.pdl.core;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.pdlweb.pdl.db.DipendenzeDAO;
import net.projectsrl.pdlweb.pdl.db.PDLDAO;
import net.projectsrl.qhse.mail.SendMail;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionInserimentoPDLv9 extends FunctionAjaxForm_base<PDLDAO> {

    public FunctionInserimentoPDLv9(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        String profiloUtente=getSpecificUserInfo(userInfo).getRoleId();

        if (isAnInsert(req)) {
            templateData.put(PDLDAO.ID_PDL, "");
            // templateData.put(PDLDAO.ID_UTENTE, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(PDLDAO.DT_PDL, project.misc.Utils.getStringDataOggi());
            templateData.put(PDLDAO.STATO, StatiPDL.APERTO.getCode());
            
            if(profiloUtente.contains("PRE")){
            	templateData.put(PDLDAO.STATO, StatiPDL.IN_ATTESA.getCode());
            	
            	Integer idUtente=(Integer) getSpecificUserInfo(userInfo).getIdUtente();
                templateData.put(PDLDAO.ID_V9_NOMINATIVO_RICHIEDENTE, idUtente);
            }
            

        } else {
            PjNDAO_base rowToUpdate = new PDLDAO();
            String idRow = req.getField(PDLDAO.ID_PDL);
            rowToUpdate.setAttribute(PDLDAO.ID_PDL, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), PDLDAO.ID_PDL + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
            templateData.put(DafneCostanti_itf.PDL_MULTIPLO, new DipendenzeDAO().getSelectedCodeList(idRow));
            
            Integer idUtente=(Integer) getSpecificUserInfo(userInfo).getIdUtente();
            templateData.put(PDLDAO.ID_UTENTE_MOD, idUtente);
            templateData.put(PDLDAO.TS_MOD, new Timestamp(System.currentTimeMillis()));
            if(!profiloUtente.contains("PRE")){
            	if(templateData.get("STATO").toString().equals(StatiPDL.IN_ATTESA.getCode())){
            		templateData.put(PDLDAO.STATO, StatiPDL.APERTO.getCode());
            	}
            	
            }
        }
        
        templateData.put(PDLDAO.REVISIONE, "10");

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    
    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(PDLDAO.ID_PDL));
    }

    
	@Override
	protected void onSuccess(SsbServletRequest req, SsbServletResponse res, 
	                         UserSecurityInfo userInfo, PDLDAO formDao) throws AppCrash {

	    String profiloUtente = getSpecificUserInfo(userInfo).getRoleId();

	    if (profiloUtente != null && profiloUtente.contains("PRE")) {

	        Integer idPDL = (Integer) formDao.getAttribute(PDLDAO.ID_PDL);

	        if (idPDL != null) {

	            SendMail mail = new SendMail(getSpecificUserInfo(userInfo));
	            mail.sendMailRequestPDL(formDao, Constants_itf.APPROVE, StatiPDL.IN_ATTESA.getCode(), StatiPDL.APERTO.getCode(), "PDL", String.valueOf(idPDL));
	        }
	    }
	}
	
	
	
	
	@Override
	protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
	        PDLDAO formDao) {

	    try {
	        PrintWriter out = res.getWriter();

	        Integer id = (formDao != null) ? (Integer) formDao.getAttribute(PDLDAO.ID_PDL) : null;
	        String nrPdl = (formDao != null) ? (String) formDao.getAttribute(PDLDAO.NR_PDL) : null;

	        String resultString = "{\"result\":" + result + 
	                              ",\"title\":\"" + title + "\"" + 
	                              ",\"message\":\"" + message + "\"" + 
	                              ",\"id\":" + id + 
	                              ",\"nrPdl\":\"" + (nrPdl != null ? nrPdl : "") + "\"}";

	        out.println(resultString);
	        out.close();

	    } catch (Throwable e) {
	        AppCrash ac = new AppCrash(e);
	        ac.logContext(this.getClass().getName(), "errore writing succesful response");
	    }
	}
	  

}
