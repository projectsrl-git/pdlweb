
package net.projectsrl.pdlweb.pdl.core;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdlweb.pdl.db.PDLDAO;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.wm.utils.Utils;

public class FunctionDuplicaPDL extends FunctionProjectWebApp_base {

    public FunctionDuplicaPDL(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
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

        PDLDAO formDao = new PDLDAO();
        try {

            formDao.setAttribute(PDLDAO.ID_PDL, req.getField(PDLDAO.ID_PDL));
            formDao.retrieve();

            Map<String, Object> map = new HashMap<String, Object>();
            formDao.setMapForDuplicateDAO(map);

            PDLDAO formDaoNew = new PDLDAO();
            map.remove(PDLDAO.ID_PDL);
            map.remove(PDLDAO.NR_PDL);
//            map.remove(PDLDAO.RESPONSABILE_CENTRALE_DELEGATO);
//            map.remove(PDLDAO.NOME_COGNOME_PREPOSTO_IMPRESA);
//            map.remove(PDLDAO.NOME_COGNOME_DELEGATO_LAVORI_AL);
            map.remove(PDLDAO.DT_CHIUSURA);
            map.remove(PDLDAO.DT_ATTIVAZIONE);
            map.remove(PDLDAO.DT_SOSPENSIONE);
            map.remove(PDLDAO.ID_UTENTE_ATTIVAZIONE);
            map.remove(PDLDAO.TS_ATTIVAZIONE);
            map.remove(PDLDAO.TS_FINE_TURNO_ATTIVAZIONE);
            map.remove(PDLDAO.CAPO_TURNO);
            map.remove(PDLDAO.CODICE_TURNO_ATTIVAZIONE);
            map.remove(PDLDAO.FLG_TA);
            map.remove(PDLDAO.ODL);
            map.remove(PDLDAO.DESCRIZIONE_DELLA_SOSPENSIONE);
            map.remove(PDLDAO.MOTIVO_SCADENZA);
            
            map.remove(PDLDAO.DT_PRIMA_ATTIVAZIONE);
            map.remove(PDLDAO.ID_UTENTE_CHIUSURA);
            
            map.remove(PDLDAO.ID_UTENTE_ATTIVAZIONE);
            map.remove(PDLDAO.ID_UTENTE_PRIMA_ATTIVAZIONE);
            map.remove(PDLDAO.DATA_CHIUSURA);
            map.remove(PDLDAO.ORA_CHIUSURA);
            map.remove(PDLDAO.TS_VERIFICA);
            map.remove(PDLDAO.ID_UTENTE_VERIFICA);
            map.remove(PDLDAO.DT_APERTURA);
            
            
            
            
            formDaoNew.setAttributesFromMap(map);
            formDaoNew.setAttribute(PDLDAO.STATO, "OPE");
            formDaoNew.setAttribute(PDLDAO.REVISIONE, PdlWebUtils.getUltimaRevisione(req.getField(PDLDAO.ID_PDL)).toString());
            formDaoNew.setAttribute(PDLDAO.DT_PDL, Utils.getStringDataOggiRibaltata());
            formDaoNew.setAttribute(PDLDAO.ID_UTENTE_INS, getSpecificUserInfo(userInfo).getIdUtente());
            Timestamp tsIns = new Timestamp(System.currentTimeMillis());
            formDaoNew.setAttribute(PDLDAO.TS_INS, tsIns);
            
            
            formDaoNew.insert();

            result = true;

            title = Config.GetInstance().getProperty(lang + ".SaveFormMessageTitle.Success.INSERT",
                    "SaveFormMessage - no message title");
            message = "Duplicazione PdL terminata con successo";

        } catch (Throwable th) {

            result = false;
            title = Config.GetInstance().getProperty(lang + ".SaveFormMessageTitle.Error",
                    "SaveFormMessage - no message title");
            message = Config.GetInstance().getProperty(lang + ".SaveFormMessageText.Error",
                    "SaveFormMessage - no message text");

            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(), "errore salvataggio dati");

        } finally {

            sendResponseJSON(res, result, title, message, formDao);
        }

    }

    private void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
            PDLDAO formDao) {

        try {
            PrintWriter out = res.getWriter();

            Integer id = (Integer) formDao.getAttribute(PDLDAO.ID_PDL);
            String nrPdl = (String) formDao.getAttribute(PDLDAO.NR_PDL);
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"id\":" + id + ",\"nrPdl\":'" + nrPdl + "'}";

            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }


}
