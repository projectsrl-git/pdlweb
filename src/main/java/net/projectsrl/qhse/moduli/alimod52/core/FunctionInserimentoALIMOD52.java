
package net.projectsrl.qhse.moduli.alimod52.core;

import java.io.PrintWriter;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.alibow.db.AttributiAziendaDAO;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.qhse.moduli.alimod52.db.AliMod52DAO;
import net.projectsrl.qhse.moduli.core.FunctionInserimentoALIMOD_base;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class FunctionInserimentoALIMOD52 extends FunctionInserimentoALIMOD_base<AliMod52DAO> {

    public FunctionInserimentoALIMOD52(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(AliMod52DAO.ID_MODULO, "");

            templateData.put(AliMod52DAO.ID_UTENTE_CON, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(AliMod52DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
            templateData.put(AliMod52DAO.STATO, StatiRichiesta.DRAFT.getCode());

        } else {
            PjNDAO_base rowToUpdate = new AliMod52DAO();
            String idRow = req.getField(AliMod52DAO.ID_MODULO);
            rowToUpdate.setAttribute(AliMod52DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), AliMod52DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }
    
    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message, AliMod52DAO formDao) {

        try {
            PrintWriter out = res.getWriter();

            Integer id = (Integer) formDao.getAttribute(AliModDAO_base.ID_MODULO);
            
            String sitoAifa = getFlagSitoAifa(formDao);

            String nr = (String) formDao.getAttribute(AliModDAO_base.NR_MODULO);
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"id\":" + id + ",\"nr\":'" + nr + "',\"aifa\":'" + sitoAifa + "'}";
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }

    private String getFlagSitoAifa(AliMod52DAO formDao) throws AppCrash {

        Integer idAzienda = (Integer) formDao.getAttribute(AliModDAO_base.ID_AZIENDA);
        AttributiAziendaDAO attrDAO = new AttributiAziendaDAO();
        attrDAO.setAttribute(AttributiAziendaDAO.ID_AZIENDA, idAzienda);
        attrDAO.setAttribute(AttributiAziendaDAO.CODICE_ATTRIBUTO,
                Constants_itf.CODICE_ATTRIBUTO_AZIENDA_SITO_AIFA);
        String sitoAifa = "N";
        try {
            attrDAO.retrieve();
            sitoAifa = (String) attrDAO.getAttribute(AttributiAziendaDAO.VALORE_ATTRIBUTO);
            if (sitoAifa==null) {
                sitoAifa="N";
            }
        } catch (Throwable e) {
//            AppCrash ac = new AppCrash(e);
//            ac.logContext(this.getClass().getName(), "error in retrieve AttributiAziendaDAO: ID_AZIENDA="
//                    + idAzienda + " - CODICE_ATTRIBUTO: SITO_AIFA");
        }
        return sitoAifa;
    }

    

}
