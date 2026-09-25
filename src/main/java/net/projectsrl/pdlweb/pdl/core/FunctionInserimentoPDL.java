
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
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.pdlweb.pdl.db.DipendenzeDAO;
import net.projectsrl.pdlweb.pdl.db.PDLDAO;
import net.projectsrl.webapp.authentication.MenuItem;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;

public class FunctionInserimentoPDL extends FunctionAjaxForm_base<PDLDAO> {

    public FunctionInserimentoPDL(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(PDLDAO.ID_PDL, "");
            // templateData.put(PDLDAO.ID_UTENTE, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(PDLDAO.DT_PDL, project.misc.Utils.getStringDataOggi());
            templateData.put(PDLDAO.STATO, StatiPDL.APERTO.getCode());

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
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(PDLDAO.ID_PDL));
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
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

    @Override
    protected String getPathDescription(Map<String, Object> map) {

        String pathDescri = "";

        try {
            MenuItem selectedMenuItem = (MenuItem) map.get(WebAppConstants_itf.SELECTED_MENU_ITEM);
            if (selectedMenuItem != null) {
                if (selectedMenuItem.getPathDescri() != null) {
                    pathDescri = selectedMenuItem.getPathDescri();
                }
            }

        } catch (ClassCastException e) {

            String menuId = "32";
            String menuIdSup = "3";
            String languageISO = "it";
            String label = "Ricerca";
            String function = "RicercaPDL";
            String link = "astro?FUNCTIONID=RicercaPDL";
            int itemLevel = 2;
            String path = "1520";
            String linkChain = "astro?FUNCTIONID=Home;#;astro?FUNCTIONID=RicercaPDL";
            boolean readOnly = false;
            int nrOfChildren = 0;
            String icon = "";

            pathDescri = "Home / Permessi di Lavoro / Visualizza-Modifica";

            MenuItem selectedMenuItem = new MenuItem(menuId, menuIdSup, languageISO, label, function, link, itemLevel,
                    path, pathDescri, linkChain, readOnly, nrOfChildren, icon);

            map.put(WebAppConstants_itf.SELECTED_MENU_ITEM, selectedMenuItem);

            map.put(WebAppConstants_itf.INCLUDED_MENU, "include/included_menu.include");
            map.put(WebAppConstants_itf.SELECTED_PATH, path);

        }

        return pathDescri;

    }

}
