
package it.project.webapp.accesscontrol;

import it.project.webapp.core.SessionCounter;

import javax.servlet.http.HttpSession;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Config;
import net.project.misc.hash;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.AuthenticationProvider;
import net.project.servlet.security.UserSecurityInfo;
import project.mail.InvioMail;
import project.mail.MyAuthenticator;

/**
 * Authentication provider per l'applicazione.
 */
public class BasicAuthenticationProvider extends AuthenticationProvider {

    private String _configName = "";

    /**
     * Costruttore vuoto.
     */
    public BasicAuthenticationProvider() {

        super();
    }

    /**
     * Costruttore.
     * 
     * @param configName java.lang.String nome della configurazione.
     */
    public BasicAuthenticationProvider(String configName) {

        _configName = configName;
    }

    /**
     * Metodo di autenticazione.
     * 
     * @param user net.ssb.servlet.security.UserSecurityInfo Oggetto contenente le informazioni di sicurezza
     *            dell'utente.
     * @param identity java.lang.Object Oggetto che identifica l'utente.
     * 
     * @exception net.ssb.errors.AppCrash.
     */
    @Override
    public void authenticate(UserSecurityInfo user, Object identity) throws AppCrash {

        checkAuthenticationParameters(user, identity);

        SsbServletRequest req = (SsbServletRequest) identity;

        HttpSession session = req.getSession(false);

        if (user.getUserId() != null && !(user.getUserId().equals(""))) {
            return;
        }
        String userid = req.getField("USERID");
        String password = req.getField("PASSWORD");
        String reset_pwd = req.getField("RESET_PWD");

        try {
            if (userid.equals("")) {
                user = null;
                return;
            }

            UtentiDAOLogin utentiDAO = new UtentiDAOLogin();
            utentiDAO.setField(UtentiDAOLogin.USERID, userid);
            if (utentiDAO.retrieve()) {

                String dbPassword = utentiDAO.getField(UtentiDAOLogin.PASSWORD).trim();

                // per compatibilità devo accettare anche la password in chiaro sul db
                // ma se la trovo salvata in chiaro, aggiorno il db con la password cifrata
                //
                if (!password.equals("Reset")) {
                    if (hash.doSHA1(password).equals(dbPassword) || password.equals(dbPassword)) {

                        String ruolo = utentiDAO.getField(UtentiDAOLogin.RUOLO).trim();
                        user.setUserId(userid);
                        user.setRoleId(ruolo);
                        user.setIPAddress(req.getRemoteAddr());
                        user.setApplicationId(Config.GetInstance(_configName).getProperty("Servlet.ApplicationName"));

                        // questo serve solo per la compatibilità con eventuali vecchie funzioni di ASTRO
                        session.setAttribute("USER", userid);

                        // se la password su db è in chiaro la aggiorno con la password cifrata
                        if (password.equals(dbPassword)) {
                            // la password è in chiaro
                            UtentiDAO utente = new UtentiDAO();
                            utente.setField(UtentiDAO.USERID, userid);
                            if (utente.retrieve()) {
                                utente.setField(UtentiDAO.PASSWORD, hash.doSHA1(password));
                                utente.update();
                            }
                        }
                    }
                }

                if (password.equals("Reset")) {
                    password = "";
                    String ruolo = utentiDAO.getField(UtentiDAOLogin.RUOLO).trim();
                    user.setUserId(userid);
                    user.setRoleId(ruolo);
                    user.setIPAddress(req.getRemoteAddr());
                    user.setApplicationId(Config.GetInstance(_configName).getProperty("Servlet.ApplicationName"));

                    // questo serve solo per la compatibilità con eventuali vecchie funzioni di ASTRO
                    session.setAttribute("USER", userid);

                    // se la password su db è in chiaro la aggiorno con la password cifrata
                    if (password.equals("")) {
                        // la password è in chiaro
                        UtentiDAO utente = new UtentiDAO();
                        utente.setField(UtentiDAO.USERID, userid);
                        if (utente.retrieve()) {
                            String new_pwd = "";
                            String mail = "";
                            int RANGE = 6 - 1 + 1;
                            int numero = (int) (RANGE * Math.random()) + 1;

                            // new_pwd =
                            // Config.GetInstance().getProperty("mail.pwd"+numero)+Utils.getUnique().substring(16,20);
                            new_pwd = "benvenuto";
                            mail = utente.getField(UtentiDAO.EMAIL) + ";assistenza@chevroletiride.com";

                            utente.setField(UtentiDAO.PASSWORD, new_pwd);
                            utente.setField(UtentiDAO.DATA, "1800/01/01");
                            utente.update();

                            // inizio mail

                            String from = Config.GetInstance().getProperty("mail.resetfrom",
                                    "assistenza@chevroletiride.com");
                            InvioMail invioMail = new InvioMail();

                            invioMail.setFrom(from);

                            String oggetto = "Reset password www.chevroletiride.com";
                            invioMail.setSubject(oggetto);

                            String corpo = "Gentile Utente,\nLa sua password è stata resettata come richiesto, qui di seguito le nuove credenziali per l'accesso a Iride:\n";

                            corpo += "<i>Userid</i>:                        " + userid + "\n";
                            corpo += "<i>Password temporanea</i>: " + new_pwd + "\n";

                            corpo += "\nAcceda al sistema utilizzando la password temporanea ed automaticamente le sarà richiesto di modificarla con una nuova password conosciuta solo da Lei.\n\nGrazie\n Chevrolet Iride - Servizio Assistenza Tecnico\n";
                            invioMail.setBody(corpo);

                            invioMail.setTo(mail);
                            invioMail.setServer(Config.GetInstance().getProperty("mail.SMTPHost"));

                            try {
                                MyAuthenticator auth = null;
                                if (!Config.GetInstance().getProperty("mail.SMTPHost.user", "").equals("")) {
                                    auth = new MyAuthenticator();
                                }

                                invioMail.invioMail(auth, false);
                            } catch (Throwable e) {
                                new AppCrash(e);
                            }

                        }

                        // fine mail

                    }
                }
            }

            session.setAttribute("LOGIN", user);
            SessionCounter.setSessionProperties(session);
            
            session.setAttribute("AZIENDA_SESSIONE", "");

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            StringBuffer userData = new StringBuffer();
            userData.append("USERID");
            userData.append(": -");
            userData.append(userid);
            userData.append("-");
            ac.logContext("BasicAuthenticationProvider", userData.toString());
            throw ac;
        }
    }

    protected void checkAuthenticationParameters(UserSecurityInfo user, Object identity) throws AppCrash {

        ErrDetector.GetInstance().preCond(user != null,
                "Invocato authenticate(UserSecurityInfo, Object) col primo parametro null!");
        ErrDetector.GetInstance().preCond(identity != null,
                "Invocato authenticate(UserSecurityInfo, Object) col secondo parametro null!");
        ErrDetector.GetInstance().preCond(
                identity instanceof SsbServletRequest,
                "Invocato authenticate(UserSecurityInfo, Object) col secondo parametro di classe"
                        + " diversa da net.ssb.servlet.frame.SsbServletRequest!");
    }
}
