package net.projectsrl.scheduled;

import java.util.HashMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.dafne.db.UtentiDAO;
import net.projectsrl.mail.DeferredMailSender;
import net.projectsrl.mail.SendSMTPMail;

public class JobMailDisattivazioneUtenti implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

        String sender = Config.GetInstance().getProperty("mail.from");

        String object = Config.GetInstance().getProperty("mail.subject.utenti.disattivati");
        String body=Config.GetInstance().getProperty("mail.body.utenti.disattivati");
        String recipients=Config.GetInstance().getProperty("mail.assistenza");
        String recipientsCC="";
        String recipientsBCC="";
        
        String portale=Config.GetInstance().getProperty("mail.portale");
        String tempo=Config.GetInstance().getProperty("scheduler.disattivazione.utenti.tempo");
        String username="";
        String nome="";
        String cognome="";
        String mail="";
        String ultimoAccesso="";
        String idUtente="";
        Boolean inviaMail=false;
        
        String tabella="<table><font face='Arial' size='2'><tr>" +
        		"<td style='text-align:center' bgcolor='#e7771e'><font color='white'><b>Username</b></font></td>" +
        		"<td style='text-align:center' bgcolor='#e7771e'><font color='white'><b>Cognome</b></font></td>" +
        		"<td style='text-align:center' bgcolor='#e7771e'><font color='white'><b>Nome</b></font></td>" +
        		"<td style='text-align:center' bgcolor='#e7771e'><font color='white'><b>Mail</b></font></td>" +
         		"<td style='text-align:center' bgcolor='#e7771e'><font color='white'><b>Data ultimo accesso</b></font></td></tr>";
        

        DataSet_itf dataSet = null;
        
        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DSUtentiDisattivati");
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("WHERECONDITION"," '"+tempo+"'" );
            dataSet.setParam(params);
            dataSet.open();
            
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                
                	inviaMail=true;
                	idUtente = dbRow.getField("ID_UTENTE").toString().trim();
            		username = dbRow.getField("USERNAME").toString().trim();
                    cognome = dbRow.getField("COGNOME").toString().trim();
                    nome = dbRow.getField("NOME").toString().trim();
                    mail = dbRow.getField("EMAIL").toString().trim();
                    ultimoAccesso = dbRow.getField("ULTIMO_ACCESSO").toString().trim();

                    object = object.replace("#PORTALE#", portale);
                    body = body.replace("#PORTALE#", portale);
                    body = body.replace("#TEMPO#", tempo.replace("days", "giorni").replace("months", "mesi").replace("years", "anni"));
                    
                    tabella+="<tr>"+
                			"<td><font color='black'> "+username+" </font></td>" +
                			"<td><font color='black'> "+cognome+" </font></td>" +
                			"<td><font color='black'> "+nome+" </font></td>" +
                			"<td><font color='black'> "+mail+" </font></td>" +
                    		"<td style='text-align:right'><font color='black'> "+ultimoAccesso+" </font></td></tr>";
                   

                    UtentiDAO utenti = new UtentiDAO();
                    utenti.setAttribute(UtentiDAO.ID_UTENTE, idUtente);
                    utenti.retrieve();
                    utenti.setAttribute(UtentiDAO.FL_DISATTIVO, true);
                    utenti.update();
            }
            
            tabella+="</table>";
            body=body.replace("#TABELLA#", tabella);
            
            if (inviaMail){
            	send( sender, recipients, recipientsCC, recipientsBCC, object, body);
            }
           	

            dataSet.close();

	        } catch (Throwable t) {
	            AppCrash ac = new AppCrash(t);
	            ac.logContext(this.getClass().getName(), "errore nell'esecuzione del Job");
	        } finally {
	            if (dataSet != null) {
	                try {
	                    dataSet.close();
	                } catch (AppCrash ac) {
	                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
	                }
	            }
	        }

	}
	
	
	
	 public void send(String sender, String recipients, String recipientsCC, String recipientsBCC, String subject, String body) throws AppCrash {

	        SendSMTPMail sendSMTPMail=null;
	        
	        try {

	            sendSMTPMail = new SendSMTPMail();
	            sendSMTPMail.setFrom(sender);
	            sendSMTPMail.setCc(recipientsCC);
	            sendSMTPMail.setBcc(recipientsBCC);
	            sendSMTPMail.setSubject(subject);
	            sendSMTPMail.setBody(body);
	            sendSMTPMail.setTo(recipients);
	            sendSMTPMail.setServer(Config.GetInstance().getProperty("mail.SMTPHost"));
	            sendSMTPMail.setUsername(Config.GetInstance().getProperty("mail.from.service"));

	            sendSMTPMail.prepareMail();
	            DeferredMailSender.getInstance().offer(sendSMTPMail);

	        } catch (Throwable e) {
	            AppCrash ac = new AppCrash(e);
	            ac.logContext(this.getClass().getName(),
	                    "error sending mail sendMailApprover - sendSMTPMail: " + sendSMTPMail.toString());
	        }
	    }
	


}

