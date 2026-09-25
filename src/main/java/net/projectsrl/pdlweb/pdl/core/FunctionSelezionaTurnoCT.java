
package net.projectsrl.pdlweb.pdl.core;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdlweb.pdl.db.UtenteTurnoDAO;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.webapp.security.WebAppUserSecurityInfo;
import net.projectsrl.wm.utils.Utils;

public class FunctionSelezionaTurnoCT extends FunctionProjectWebApp_base {

    public FunctionSelezionaTurnoCT(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);

    }

    @Override
    public boolean isAuthenticationRequired() {

        return false;
    }

    @Override
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        boolean result = false;
        String title = "";
        String message = "";
        String lang = "";

        try {
            lang = getSpecificUserInfo(userInfo).getField(WebAppConstants_itf.CURRENT_SELECTED_ISO_LANGUAGE);
        } catch (Throwable e) {
            lang = "it";
        }

        String operationType = WebAppConstants_itf.OPERATION_TYPE_UPDATE;
        
        try {

            String codiceTurno = req.getField(PDLConstants_itf.CODICE_TURNO);

            String dataFineTurno = "";
            String oraFineTurno = "";

            if (codiceTurno.equals(PDLConstants_itf.TURNO_1)) {
                dataFineTurno = Utils.getStringDataOggiRibaltata();
                oraFineTurno = PDLConstants_itf.FINE_TURNO_1;
            } else if (codiceTurno.equals(PDLConstants_itf.TURNO_2)) {
                dataFineTurno = Utils.getStringDataOggiRibaltata();
                oraFineTurno = PDLConstants_itf.FINE_TURNO_2;
            } else if (codiceTurno.equals(PDLConstants_itf.TURNO_3)) {
                dataFineTurno = getTomorrowDateString();
                oraFineTurno = PDLConstants_itf.FINE_TURNO_3;
            } else if (codiceTurno.equals(PDLConstants_itf.TURNO_4)) {
                dataFineTurno = Utils.getStringDataOggiRibaltata();
                oraFineTurno = PDLConstants_itf.FINE_TURNO_4;
            } else if (codiceTurno.equals(PDLConstants_itf.TURNO_5)) {
                dataFineTurno = getTomorrowDateString();
                oraFineTurno = PDLConstants_itf.FINE_TURNO_5;
            }

            getSpecificUserInfo(userInfo).setField(PDLConstants_itf.CODICE_TURNO,  codiceTurno);
            getSpecificUserInfo(userInfo).setField(PDLConstants_itf.DATA_FINE_TURNO, dataFineTurno);
            getSpecificUserInfo(userInfo).setField(PDLConstants_itf.ORA_FINE_TURNO, oraFineTurno);
            
            req.getSession(false).setAttribute("CODICE_TURNO_SELEZIONATO", codiceTurno);
            
            Integer idUtente= (Integer) ((WebAppUserSecurityInfo<?>) userInfo).getIdUtente();
            UtenteTurnoDAO utenteturno = new UtenteTurnoDAO();
            utenteturno.setAttribute(UtenteTurnoDAO.ID_UTENTE, idUtente);
            utenteturno.setAttribute(UtenteTurnoDAO.CODICE_TURNO, codiceTurno);
            utenteturno.setAttribute(UtenteTurnoDAO.DATA_FINE_TURNO, dataFineTurno);
            utenteturno.setAttribute(UtenteTurnoDAO.ORA_FINE_TURNO, oraFineTurno);
            utenteturno.setAttribute(UtenteTurnoDAO.TS_LOGIN, new Timestamp(System.currentTimeMillis()));
            //utenteturno.setField(UtenteTurnoDAO.TS_LOGOUT, "");
   			utenteturno.insert();
   			

   			
            result = true;

            title = Config.GetInstance().getProperty(lang + ".SaveFormMessageTitle.Success." + operationType,
                    "SaveFormMessage - no message title");
            message = Config.GetInstance().getProperty(lang + ".SaveFormMessageText.Success." + operationType,
                    "SaveFormMessage - no message text");


        } catch (Throwable th) {

            result = false;
            title = Config.GetInstance().getProperty(lang + ".SaveFormMessageTitle.Error",
                    "SaveFormMessage - no message title");
            message = Config.GetInstance().getProperty(lang + ".SaveFormMessageText.Error",
                    "SaveFormMessage - no message text");
            

            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(), "errore salvataggio dati");

        } finally {

            sendResponseJSON(res, result, title, message);
        }

    }

    /**
     * Questo metodo
     * 
     * @param res
     * @param formDao
     */
    private void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message) {

        try {
            PrintWriter out = res.getWriter();
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message + "'}";
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }

    private String getTomorrowDateString() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);

        return dateFormat.format(cal.getTime());
    }

}
