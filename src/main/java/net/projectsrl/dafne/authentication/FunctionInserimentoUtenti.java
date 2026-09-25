
package net.projectsrl.dafne.authentication;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Config;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.dafne.db.UtentiAziendeDAO;
import net.projectsrl.dafne.db.UtentiDAO;
import net.projectsrl.dafne.db.UtentiProfiliDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.mail.DeferredMailSender;
import net.projectsrl.mail.SendSMTPMail;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;

public class FunctionInserimentoUtenti extends FunctionAjaxForm_base<UtentiDAO> {



    public FunctionInserimentoUtenti(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(UtentiDAO.ID_UTENTE, "");
            templateData.put(UtentiDAO.USERNAME, "");
        } else {
            PjNDAO_base utente = new UtentiDAO();
            String idUtente = req.getField(UtentiDAO.ID_UTENTE);
            utente.setAttribute(UtentiDAO.ID_UTENTE, idUtente);
            ErrDetector.GetInstance().preCond(utente.retrieve(), UtentiDAO.ID_UTENTE + " not found");
            utente.setMapFromAttributes(templateData);

            templateData.put(DafneCostanti_itf.PROFILO_MULTIPLO, new UtentiProfiliDAO().getSelectedCodeList(idUtente));
            templateData.put(DafneCostanti_itf.AZIENDE_MULTIPLE, new UtentiAziendeDAO().getSelectedCodeList(idUtente));
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(UtentiDAO.ID_UTENTE));
    }

    @Override
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		super.elabora(req, res, userInfo);

		if (!req.getField("EMAIL").isEmpty() && isAnInsert(req)) {
			inviaMail(req, userInfo);
		}

	}

	@SuppressWarnings({ "unchecked" })
	private void inviaMail(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {

		String lang = "";
		try {
			lang = getSpecificUserInfo(userInfo).getField(WebAppConstants_itf.CURRENT_SELECTED_ISO_LANGUAGE);
		} catch (Throwable e) {
			lang = "it";
		}

		String portale = Config.GetInstance().getProperty("mail.portale");
		String indirizzoPortale = Config.GetInstance().getProperty("mail.indirizzo.portale");
		String subject = Config.GetInstance().getProperty("mail.subject.nuovoutente." + lang);
		String body = Config.GetInstance().getProperty("mail.body.nuovoutente." + lang);

		String sender = Config.GetInstance().getProperty("mail.from");
		String recipients = req.getField("EMAIL");
		String recipientsCC = "";
		String recipientsBCC = "";
		String username = req.getField("USERNAME");
		String password = Config.GetInstance().getProperty("Utenti.Password.Iniziale");

		subject = subject.replace("#PORTALE#", portale);

		body = body.replace("#PORTALE#", portale);
		body = body.replace("#INDIRIZZO_PORTALE#", indirizzoPortale);
		body = body.replace("#USERNAME#", username);
		body = body.replace("#PASSWORD#", password);

		send(sender, recipients, recipientsCC, recipientsBCC, subject, body, userInfo);
	}

	public void send(String sender, String recipients, String recipientsCC, String recipientsBCC, String subject,
			String body, UserSecurityInfo userInfo) throws AppCrash {

		SendSMTPMail sendSMTPMail = null;

		try {

			sendSMTPMail = new SendSMTPMail();
			sendSMTPMail.setFrom(sender);
			sendSMTPMail.setCc(recipientsCC);
			sendSMTPMail.setBcc(recipientsBCC);
			sendSMTPMail.setSubject(subject);
			sendSMTPMail.setBody(body);
			sendSMTPMail.setTo(recipients);
			sendSMTPMail.setServer(Config.GetInstance().getProperty("mail.SMTPHost"));
			sendSMTPMail.setUsername(userInfo.getUserId());

			sendSMTPMail.prepareMail();
			DeferredMailSender.getInstance().offer(sendSMTPMail);

		} catch (Throwable e) {
			AppCrash ac = new AppCrash(e);
			ac.logContext(this.getClass().getName(),
					"error sending mail sendMailApprover - sendSMTPMail: " + sendSMTPMail.toString());
		}
	}
}
