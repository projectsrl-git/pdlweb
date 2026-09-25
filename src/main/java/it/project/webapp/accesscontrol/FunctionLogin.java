
package it.project.webapp.accesscontrol;

import it.project.webapp.core.FunctionWebApp_base;
import it.project.webapp.core.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.AuthenticationProvider_itf;
import net.project.servlet.security.UserSecurityInfo;
import project.misc.Utils;

/**
 * FunctionLogin
 * 
 * Login standard per applicazione web
 * 
 */
public class FunctionLogin extends FunctionWebApp_base {

    private static final String PAGE                   = "login";
    private static final String DATASET_MENU_NAME      = "DataSetMenu";
    private static final String DATASET_MENU_DETT_NAME = "DataSetMenuDett";
    private static final String DATASET_UTENTI         = "DataSetUtenteSingolo";

    public FunctionLogin() {

        super();
    }

    public FunctionLogin(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        String user = getSessionUser(req);
        String ruolo = getSessionRole(req);

        if (user != null && ruolo != null && !user.equals("") && !ruolo.equals("")) {
            redirectToHomePage(res);
        } else {
            _applicationSrv.displayPage(PAGE, setCommonTags(req, userInfo), res);
        }
    }

    @Override
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        try {

            setSessionProperties(req);
            authenticate(userInfo, req);

            if (!isLoggedUser(userInfo)) {
                redirectToThisPageWithMessage(res, "1");
                return;
            }

            Utils.logOperation(getSessionUser(req),
                    getSessionRole(req) + " - " + req.getSession(false).getAttribute("USER_COGNOME") + " "
                            + req.getSession(false).getAttribute("USER_NOME"), getFunctionID() + " " + getName(),
                    Utils.getRequestParameters(req));

            redirectToHomePage(res);

        } catch (Throwable e) {
            throw new AppCrash(e);
        }
    }

    private boolean isLoggedUser(UserSecurityInfo userInfo) {

        if (userInfo == null || userInfo.getUserId() == null || userInfo.getUserId().equals("")) {
            return false;
        }
        return true;
    }

    protected void setSessionProperties(SsbServletRequest req) throws AppCrash {

        String societa = Utils.leggeStringaElencoCampi("SOCIETA", req.getField("SOCIETA"));
        String dbUrl = Utils.leggeStringaElencoCampi("DBURL", req.getField("SOCIETA"));

        String nomeDbHostName = readDBHostName();
        dbUrl = dbUrl.replaceAll("#DB_HOST_NAME#", nomeDbHostName);
        dbUrl = dbUrl.replaceAll("#DB_HOST_NAME2#", readSecondaryDBHostName());
        String logo = Utils.leggeStringaElencoCampi("LOGO", req.getField("SOCIETA"));
        String logo_in_elenchi = Utils.leggeStringaElencoCampi("LOGO_IN_ELENCHI", req.getField("SOCIETA"));

        HttpSession session = req.getSession(true);
        session.setAttribute("NOME_SOCIETA", societa);
        session.setAttribute("DBURL", dbUrl);
        session.setAttribute("LOGO", logo);
        session.setAttribute("LOGO_IN_ELENCHI", logo_in_elenchi);
        session.setAttribute("PAGE_TITLE", Config.GetInstance().getProperty("Servlet.ApplicationTitle", ""));

        Config.GetInstance().setProperty("DB.ConnectionURL", dbUrl);
        Config.GetInstance().setProperty("DBEntity.NomeDB", nomeDbHostName.substring(nomeDbHostName.indexOf("/") + 1));

    }

    private void redirectToHomePage(SsbServletResponse res) throws AppCrash {

        try {
            res.sendRedirect("astro?FUNCTIONID=Home");
        } catch (IOException e) {
            throw new AppCrash(e);
        }

    }

    protected void authenticate(UserSecurityInfo userInfo, SsbServletRequest req) throws AppCrash {

        AuthenticationProvider_itf authenticationProvider = null;

        // Recupero dal file di configurazione il tipo di implementazione da instanziare.
        String className = Config.GetInstance().getProperty(
                new String("Servlet." + getName() + ".authentication.class"));
        if (className == null) {
            // Se la proprieta' servlet.authentication.class non e' trovata viene istanziata la classe
            // BasicAuthenticationProvider.
            authenticationProvider = new BasicAuthenticationProvider();
        } else {
            try {
                authenticationProvider = (AuthenticationProvider_itf) (Class.forName(className)).newInstance();
            } catch (Throwable ex) {
                AppCrash err = new AppCrash(ex);
                err.logContext("AuthenticationProvider", "Errore durante la creazione dell'oggetto " + className
                        + " - " + ex.getMessage());
                throw (err);
            }
        }

        authenticationProvider.authenticate(userInfo, req);

        if (userInfo == null || userInfo.getUserId() == null || userInfo.getRoleId() == null) {
            return;
        }

        saveMenuInSession(req, req.getSession(false));
        saveCognomeNomeUserInSession(req, req.getSession(false));

    }

    @Override
    protected boolean checkSession(SsbServletRequest req) {

        return true;
    }

    @Override
    protected void loadDataFromSession(HttpSession session, HashMap<String, Object> templateData) {

        return;

    }

    protected void saveMenuInSession(SsbServletRequest req, HttpSession session) throws AppCrash {

        DataSet_itf dataSet = null;

        String user = getSessionUser(session);
        String ruolo = getSessionRole(session);

        try {
            if (user.equals("")) {
                user = null;
                return;
            }

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("RUOLO", ruolo);

            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_MENU_NAME);
            dataSet.setParam(params);
            dataSet.open();

            ArrayList<MenuItem> menu = new ArrayList<MenuItem>();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                String menuId = dbRow.getField("CODICE").toString().trim();
                String itemLevel = dbRow.getField("ORDINE").toString().trim();
                String caption = dbRow.getField("DESCRIZIONE").toString().trim();
                String url = dbRow.getField("LINK").toString().trim();

                MenuItem menuItem = new MenuItem();
                menuItem.setMenuId(menuId);
                menuItem.setItemLevel(itemLevel);
                menuItem.setCaption(caption);
                menuItem.setURL(url);

                menu.add(menuItem);
            }

            dataSet.close();

            session.setAttribute("voci_menu", menu);

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_MENU_DETT_NAME);
            dataSet.setParam(params);
            dataSet.open();

            ArrayList<MenuItem> menuDett = new ArrayList<MenuItem>();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                String menuId = dbRow.getField("CODICE").toString().trim();
                String itemLevel = dbRow.getField("ORDINE").toString().trim();
                String caption = dbRow.getField("DESCRIZIONE").toString().trim();
                String url = dbRow.getField("LINK").toString().trim();

                MenuItem menuItem = new MenuItem();
                menuItem.setMenuId(menuId);
                menuItem.setItemLevel(itemLevel);
                menuItem.setCaption(caption);
                menuItem.setURL(url);

                menuDett.add(menuItem);
            }

            dataSet.close();

            session.setAttribute("dett_voci_menu", menuDett);

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext("FunctionWebApp_base", "Errore nella close del dataset");
                }
            }
        }
    }

    protected void saveCognomeNomeUserInSession(SsbServletRequest req, HttpSession session) throws AppCrash {

        DataSet_itf dataSet = null;

        String user = getSessionUser(session);
        String ruolo = getSessionRole(session);

        try {
            if (user == null || user.equals("")) {
                user = null;
                return;
            }

            if (ruolo == null || ruolo.equals("")) {
                ruolo = null;
                return;
            }

            DataSetFactory dsFactory = DataSetFactory.getInstance();

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_UTENTI);

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("USERID", user);

            dataSet.setParam(params);
            dataSet.open();

            String cognome = "";
            String nome = "";
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                cognome = dbRow.getField("COGNOME").toString().trim();
                nome = dbRow.getField("NOME").toString().trim();
            }

            if (cognome == null) {
                cognome = "";
            }

            if (nome == null) {
                nome = "";
            }

            session.setAttribute("USER_COGNOME", cognome);
            session.setAttribute("USER_NOME", nome);

            dataSet.close();

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext("FunctionWebApp_base", "Errore nella close del dataset");
                }
            }
        }
    }
}
