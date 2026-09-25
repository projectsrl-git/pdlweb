
package net.projectsrl.wm.timesheets.core;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.core.FunctionWebApp_base;
import net.projectsrl.wm.db.TimesheetDAO;
import net.projectsrl.wm.utils.Utils;


/**
 * FunctionInserimentoAttivita
 * 
 */
public class FunctionInserimentoAttivita extends FunctionWebApp_base {

    public FunctionInserimentoAttivita() {

        super();
    }

    public FunctionInserimentoAttivita(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

       try {
            inserimentoAttivita(req);
            res.sendRedirect("astro?FUNCTIONID=RicercaAttivita");
        } catch (Throwable th) {
            throw new AppCrash(th);
        }
    }
    
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        return;
    }

    private void inserimentoAttivita(SsbServletRequest req) throws AppCrash {

        String idCommessa = req.getField("ID_COMMESSA");
        String commessa = req.getField("COMMESSA");
        String idRisumana = (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
        String cognome = (String) req.getSession(false).getAttribute("USER_COGNOME");
        String nome = (String) req.getSession(false).getAttribute("USER_NOME");
        String compComm = req.getField("COMPCOMM");
        String dsCompComm = req.getField("DSCOMPCOMM");
        String anno = req.getField("ANNO");
        String mese = req.getField("MESE");

        TimesheetDAO timesheetDAO = new TimesheetDAO();
        timesheetDAO.setField(TimesheetDAO.ANNO, anno);
        timesheetDAO.setField(TimesheetDAO.MESE, mese);
        timesheetDAO.setField(TimesheetDAO.IDCOMMESSA, idCommessa);
        timesheetDAO.setField(TimesheetDAO.COMMESSA, commessa);
        timesheetDAO.setField(TimesheetDAO.CODRIS, "");
        timesheetDAO.setField(TimesheetDAO.DESRIS, cognome + " " + nome);
        timesheetDAO.setField(TimesheetDAO.IDRISUMANA, idRisumana);
        timesheetDAO.setField(TimesheetDAO.COMPCOMM, compComm);
        timesheetDAO.setField(TimesheetDAO.DSCOMPCOMM, dsCompComm);
        timesheetDAO.setField(TimesheetDAO.DATA, anno + "/" + mese + "/01");
        timesheetDAO.setField(TimesheetDAO.IDTIMESHT, Utils.getUnique());
        timesheetDAO.insert();
    }

}
