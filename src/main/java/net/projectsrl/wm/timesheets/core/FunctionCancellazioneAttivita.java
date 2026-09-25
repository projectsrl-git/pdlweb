package net.projectsrl.wm.timesheets.core;

import java.sql.PreparedStatement;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.errors.Logger;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.core.FunctionWebApp_base;

/**
 * FunctionCancellazioneAttivita
 * 
 */
public class FunctionCancellazioneAttivita extends FunctionWebApp_base {

	public FunctionCancellazioneAttivita() {

		super();
	}

	public FunctionCancellazioneAttivita(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}

	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
	    
	    try {
	        cancellazioneAttivita(req);
            res.sendRedirect("astro?FUNCTIONID=Timesheet");
        } catch (Throwable th) {
            throw new AppCrash(th);
        }
	}

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        elabora(req, res, userInfo);
    }

    private void cancellazioneAttivita(SsbServletRequest req) throws AppCrash {
        String idCommessa = req.getField("IDCOMMESSA");
        String idRisumana = (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
        String compComm = req.getField("COMPCOMM");
        String anno = req.getField("ANNO");
        String mese = req.getField("MESE");
        
        PreparedStatement ps = null;
        DBTransaction dbtransaction = new DBTransaction();
        String delete = "";
        try {
            
            delete = "DELETE FROM TIMESHT WHERE IDCOMMESSA='" + idCommessa + "' AND IDRISUMANA='" + idRisumana
                    + "' AND COMPCOMM='" + compComm + "' AND ANNO='" + anno + "' AND MESE='" + mese + "'";
            ps = dbtransaction.prepareStatement(delete);
            ps.execute();
            dbtransaction.commit();
            Logger.GetInstance().log0("---- delete" + delete);


        } catch (Throwable e) {
            if (dbtransaction != null) {
                dbtransaction.rollBack();
            }
            AppCrash ac = new AppCrash(e);
            throw ac;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Throwable close) {
                AppCrash appCrashClose = new AppCrash(close);
                appCrashClose.logContext("FunctionCancellazioneAttivita.cancellazioneAttivita",
                        "Errore durante la chiusura del PreparedStatement");
                throw appCrashClose;
            }
        }
        
        
    }
    
    

}
