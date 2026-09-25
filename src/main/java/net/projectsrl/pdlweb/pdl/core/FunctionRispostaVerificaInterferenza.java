package net.projectsrl.pdlweb.pdl.core;



import java.sql.Timestamp;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdlweb.pdl.db.PDLDAO;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

public class FunctionRispostaVerificaInterferenza extends FunctionAjaxForm_base<PDLDAO> {

	public FunctionRispostaVerificaInterferenza(ApplicationServices_itf applServices, String functionID,
			String functionName) {
		super(applServices, functionID, functionName);
	}

	@Override
	protected boolean isAnInsert(SsbServletRequest req) {
		return false;
	}

	@Override
	protected void update(SsbServletRequest req, PDLDAO formDao, UserSecurityInfo userInfo) throws AppCrash {
        

		String idPdl=req.getField("ID_PDL");
		String risposta=req.getField("RISPOSTA_VERIFICA");
		
		Timestamp now=new Timestamp(System.currentTimeMillis());
		Integer idUtente=(Integer) getSpecificUserInfo(userInfo).getIdUtente();
		
		formDao.setAttribute("ID_PDL", idPdl);
		
		formDao.setAttribute("RISPOSTA_VERIFICA", risposta);
		formDao.setAttribute("TS_VERIFICA", now);
		formDao.setAttribute("ID_UTENTE_VERIFICA", idUtente);
		
		
        formDao.update();
        
	}

}
