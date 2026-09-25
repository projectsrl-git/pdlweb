
package net.projectsrl.qhse.mail;

import java.util.HashMap;
import java.util.StringTokenizer;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Config;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.dafne.db.UtentiDAO;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.mail.DeferredMailSender;
import net.projectsrl.mail.SendSMTPMail;
import net.projectsrl.pdlweb.pdl.core.PdlWebUtils;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.webapp.security.WebAppUserSecurityInfo;

public class SendMail {

    private static final String DATASET_DISTRIBUTION_LIST = "DSDistributionList";
    private WebAppUserSecurityInfo<?> _userInfo;

    public SendMail(WebAppUserSecurityInfo<?>  userInfo) {

        _userInfo = userInfo;
    }
    
    
    public void sendMailAliMOD(PjNDAO_base dao, String workFlowAction, String statoIniziale, String statoFinale, String modulo) throws AppCrash {

        DataSet_itf dataSet = null;

        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_DISTRIBUTION_LIST);

            HashMap<String, String> params = new HashMap<String, String>();
            
            String whereCondition="";
            
            if (workFlowAction.equals(Constants_itf.APPROVE) && statoFinale.equals(StatiRichiesta.WAITNG_FOR_FIRST_APPROVAL.getCode())) {
                whereCondition+=" WHERE ";
                whereCondition+=" MODULO ='"+modulo+"' ";
                whereCondition+=" AND ";
                whereCondition+=" WORKFLOW_ACTION ='"+workFlowAction+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_INIZIALE ='"+statoIniziale+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_FINALE ='"+statoFinale+"' ";
                whereCondition+=" AND ";
                whereCondition+=" ID_AZIENDA ="+dao.getAttribute(AliModDAO_base.ID_AZIENDA)+" ";
            } else if (workFlowAction.equals(Constants_itf.APPROVE) && statoFinale.equals(StatiRichiesta.APPROVED.getCode())) {
                whereCondition+=" WHERE ";
                whereCondition+=" MODULO ='"+modulo+"' ";
                whereCondition+=" AND ";
                whereCondition+=" WORKFLOW_ACTION ='"+workFlowAction+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_INIZIALE ='"+statoIniziale+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_FINALE ='"+statoFinale+"' ";
            } else if (workFlowAction.equals(Constants_itf.ROLLBACK) && statoFinale.equals(StatiRichiesta.DRAFT.getCode())) {
                whereCondition+=" WHERE ";
                whereCondition+=" MODULO ='"+modulo+"' ";
                whereCondition+=" AND ";
                whereCondition+=" WORKFLOW_ACTION ='"+workFlowAction+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_INIZIALE ='"+statoIniziale+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_FINALE ='"+statoFinale+"' ";
            } else if (workFlowAction.equals(Constants_itf.NOTIFY) && statoFinale.equals(StatiRichiesta.APPROVED.getCode())) {
                whereCondition+=" WHERE ";
                whereCondition+=" MODULO ='"+modulo+"' ";
                whereCondition+=" AND ";
                whereCondition+=" WORKFLOW_ACTION ='"+workFlowAction+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_INIZIALE ='"+statoIniziale+"' ";
                whereCondition+=" AND ";
                whereCondition+=" STATO_FINALE ='"+statoFinale+"' ";
                whereCondition+=" AND ";
                whereCondition+=" ID_AZIENDA ="+dao.getAttribute(AliModDAO_base.ID_AZIENDA)+" ";
            }
            
            
            params.put(WebAppConstants_itf.WHERECONDITION, whereCondition);

            dataSet.setParam(params);
            dataSet.open();

            if (!dataSet.hasMoreElements()) {
                return;
            }
            
            Row_itf dbRow = (Row_itf) dataSet.nextElement();
            String subject = (String) dbRow.getField("OGGETTO");
            String body = (String) dbRow.getField("TESTO_MAIL");
            String sender=(String) dbRow.getField("MITTENTE");
            String recipients = (String) dbRow.getField("DESTINATARI_TO");
            String recipientsBCC=(String) dbRow.getField("DESTINATARI_BCC");
            String recipientsCC="";
            
            if (workFlowAction.equals(Constants_itf.APPROVE) && statoFinale.equals(StatiRichiesta.WAITNG_FOR_FIRST_APPROVAL.getCode())) {
                UtentiDAO utente= new UtentiDAO();
                utente.setAttribute(UtentiDAO.ID_UTENTE, dao.getAttribute(AliModDAO_base.ID_UTENTE_INS));
                ErrDetector.GetInstance().preCond(utente.retrieve(),"user does not exists");
                recipientsCC= (String) utente.getAttribute(UtentiDAO.EMAIL);
               
            } else if (workFlowAction.equals(Constants_itf.APPROVE) && statoFinale.equals(StatiRichiesta.APPROVED.getCode())) {
                UtentiDAO utente= new UtentiDAO();
                utente.setAttribute(UtentiDAO.ID_UTENTE, dao.getAttribute(AliModDAO_base.ID_UTENTE_INS));
                ErrDetector.GetInstance().preCond(utente.retrieve(),"user does not exists");
                recipients= (String) utente.getAttribute(UtentiDAO.EMAIL);
                
                utente= new UtentiDAO();
                utente.setAttribute(UtentiDAO.ID_UTENTE, _userInfo.getIdUtente());
                ErrDetector.GetInstance().preCond(utente.retrieve(),"user does not exists");
                recipientsCC= (String) utente.getAttribute(UtentiDAO.EMAIL);
            } else if (workFlowAction.equals(Constants_itf.ROLLBACK) && statoFinale.equals(StatiRichiesta.DRAFT.getCode())) {
                UtentiDAO utente= new UtentiDAO();
                utente.setAttribute(UtentiDAO.ID_UTENTE, dao.getAttribute(AliModDAO_base.ID_UTENTE_INS));
                ErrDetector.GetInstance().preCond(utente.retrieve(),"user does not exists");
                recipients= (String) utente.getAttribute(UtentiDAO.EMAIL);
                
                utente= new UtentiDAO();
                utente.setAttribute(UtentiDAO.ID_UTENTE, _userInfo.getIdUtente());
                ErrDetector.GetInstance().preCond(utente.retrieve(),"user does not exists");
                recipientsCC= (String) utente.getAttribute(UtentiDAO.EMAIL);
            }
            
            
            
            subject = subject.replace("#NR_MODULO#", (String) dao.getAttribute(AliModDAO_base.NR_MODULO));
            subject = subject.replace("#DT_MODULO#", (String)dao.getAttribute(AliModDAO_base.DT_MODULO));

            body = body.replace("#ID_MODULO#",dao.getAttributeAsString(AliModDAO_base.ID_MODULO));

            send( sender,  recipients,  recipientsCC,  recipientsBCC,  subject,  body);
            
            
        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
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
    
    
    
    
    public void sendMailRejectPDL(PjNDAO_base dao, String action, String statoIniziale, String statoFinale, String modulo, String idModulo) throws AppCrash {

   	 DataSet_itf dataSet = null;
        dataSet = DataSetFactory.getInstance().makeDataSet("", "DSPDLWorkbookStampaMultipla");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("WHERECONDITION", " WHERE ID_PDL="+idModulo);
        dataSet.setParam(params);
        dataSet.open();

        if (dataSet.hasMoreElements()) {
       	 Row_itf dbRow = (Row_itf) dataSet.nextElement();
       	 
       	    String subject = Config.GetInstance().getProperty("testomail.rifiuto-oggetto");
            String body = Config.GetInstance().getProperty("testomail.rifiuto-corpo");
            
            subject=subject.replace("[NR_PDL]", dbRow.getField("NR_PDL").toString());
            
            body=body.replace("[INDIRIZZO_WEB]", "www.pdlweb.net");
            
            body=body.replace("[NR_PDL]", dbRow.getField("NR_PDL").toString());
            body=body.replace("[SITO]", dbRow.getField("RAGSOC").toString());            
            body=body.replace("[IMPIANTO]", dbRow.getField("DESCR_IMPIANTO").toString());
            body=body.replace("[AREA]", dbRow.getField("DESCR_AREA").toString());

            if (dbRow.getField("DESCR_EQUIPMENT")==null){
         	   body=body.replace("[EQUIPMENT]","");
            }else{
         	   body=body.replace("[EQUIPMENT]",dbRow.getField("DESCR_EQUIPMENT").toString());
            }
            
            body=body.replace("[ODL]", dbRow.getField("ODL").toString());
            body=body.replace("[V9_ATTREZZATURA_INSTALLAZIONE_OGGETTO]", dbRow.getField("V9_ATTREZZATURA_INSTALLAZIONE_OGGETTO").toString());
            body=body.replace("[V9_ATTREZZATURA_INSTALLAZIONE_SICUREZZA]", dbRow.getField("V9_ATTREZZATURA_INSTALLAZIONE_SICUREZZA").toString());
            body=body.replace("[DESCRIZIONE_LAVORO]", dbRow.getField("DESCRIZIONE_LAVORO").toString());
            body=body.replace("[MOTIVAZIONE]", dbRow.getField("MOTIVAZIONE").toString());
             
            String sender=Config.GetInstance().getProperty("mail.from.pdlweb");
            String recipients = PdlWebUtils.getMailFromIdUtente(dbRow.getField("ID_UTENTE_INS").toString());
            String recipientsBCC="";
            String recipientsCC=PdlWebUtils.getMailFromNominativoUtente(dbRow.getField("RESPONSABILE_CENTRALE_DELEGATO").toString());
            
            send( sender,  recipients.replaceAll(",", ";"),  recipientsCC,  recipientsBCC,  subject,  body);
        }
      
   }
    
    
    
    public void sendMailRequestPDL(PjNDAO_base dao, String action, String statoIniziale, String statoFinale, String modulo, String idModulo) throws AppCrash {

      	 DataSet_itf dataSet = null;
           dataSet = DataSetFactory.getInstance().makeDataSet("", "DSPDLWorkbookStampaMultipla");
           HashMap<String, String> params = new HashMap<String, String>();
           params.put("WHERECONDITION", " WHERE ID_PDL="+idModulo);
           dataSet.setParam(params);
           dataSet.open();

           if (dataSet.hasMoreElements()) {
          	 Row_itf dbRow = (Row_itf) dataSet.nextElement();
          	 
          	   String subject = Config.GetInstance().getProperty("testomail.nuova_richiesta-oggetto");
               String body = Config.GetInstance().getProperty("testomail.nuova_richiesta-corpo");
               
               subject=subject.replace("[NR_PDL]", dbRow.getField("NR_PDL").toString());
                             
               body=body.replace("[INDIRIZZO_WEB]", "www.pdlweb.net");
               
               body=body.replace("[RICHIEDENTE]", PdlWebUtils.getNomeCognomeFromIdUtente(dbRow.getField("ID_UTENTE_INS").toString()));
               body=body.replace("[NR_PDL]", dbRow.getField("NR_PDL").toString());
               body=body.replace("[SITO]", dbRow.getField("RAGSOC").toString());            
               body=body.replace("[IMPIANTO]", dbRow.getField("DESCR_IMPIANTO").toString());
               body=body.replace("[AREA]", dbRow.getField("DESCR_AREA").toString());
               
               if (dbRow.getField("DESCR_EQUIPMENT")==null){
            	   body=body.replace("[EQUIPMENT]","");
               }else{
            	   body=body.replace("[EQUIPMENT]",dbRow.getField("DESCR_EQUIPMENT").toString());
               }
               
               body=body.replace("[ODL]", dbRow.getField("ODL").toString());
               body=body.replace("[V9_ATTREZZATURA_INSTALLAZIONE_OGGETTO]", dbRow.getField("V9_ATTREZZATURA_INSTALLAZIONE_OGGETTO").toString());
               body=body.replace("[V9_ATTREZZATURA_INSTALLAZIONE_SICUREZZA]", dbRow.getField("V9_ATTREZZATURA_INSTALLAZIONE_SICUREZZA").toString());
               body=body.replace("[DESCRIZIONE_LAVORO]", dbRow.getField("DESCRIZIONE_LAVORO").toString());
               body=body.replace("[MOTIVAZIONE]", dbRow.getField("MOTIVAZIONE").toString());
                
               String sender=Config.GetInstance().getProperty("mail.from.pdlweb");
               String recipients = PdlWebUtils.getMailFromNominativoUtente(dbRow.getField("RESPONSABILE_CENTRALE_DELEGATO").toString());
               String recipientsBCC="";
               String recipientsCC= PdlWebUtils.getMailFromIdUtente(dbRow.getField("ID_UTENTE_INS").toString());
               
               send( sender,  recipients.replaceAll(",", ";"),  recipientsCC,  recipientsBCC,  subject,  body);
           }
         
      }
    
    
    
    
    public void sendMailInterferenza(String idPdl, String descrImpianto, String descrArea) throws AppCrash {

    	 DataSet_itf dataSet = null;
         dataSet = DataSetFactory.getInstance().makeDataSet("", "DSInterferenzePDLMail");
         HashMap<String, String> params = new HashMap<String, String>();
         params.put("ID_PDL",  idPdl);
         dataSet.setParam(params);
         dataSet.open();

         if (dataSet.hasMoreElements()) {
        	 Row_itf dbRow = (Row_itf) dataSet.nextElement();
        	 
        	 
        	 /*String delims = ",";
     		 String splitString =  dbRow.getField("LISTA_NR_PDL").toString();
      
     		 System.out.println("StringTokenizer Example: \n");
     		 StringTokenizer st = new StringTokenizer(splitString, delims);
     		 while (st.hasMoreElements()) {
     			System.out.println("StringTokenizer Output: " + st.nextElement());
     		 }*/
     		
        	 
             String subject = Config.GetInstance().getProperty("testomail.interferenza-oggetto");
             String body = Config.GetInstance().getProperty("testomail.interferenza-corpo");
             body=body.replace("[INDIRIZZO_WEB]", "www.pdlweb.net");
             body=body.replace("[IMPIANTO]", descrImpianto);
             body=body.replace("[AREA]", descrArea);
             
            /* body+="<table><font face='Arial' size='2'><tr>" +
                     "<td bgcolor='#e7771e'><font color='white'><b>Nr. PdL</b></font></td>" +
                     "<td bgcolor='#e7771e'><font color='white'><b>Descrizione lavoro</b></font></td>" +
                     "<td bgcolor='#e7771e'><font color='white'><b>Impresa</b></font></td></tr>";*/
             
                 
             String delims = ",";
             
             String nrPdl="";
     		 String splitString =  dbRow.getField("LISTA_NR_PDL").toString();
     		 StringTokenizer st = new StringTokenizer(splitString, delims);
     		 while (st.hasMoreElements()) {
     			nrPdl=nrPdl+st.nextElement()+"\n";
     		 }
     		 
     		 
     		 String descriPdl="";
    		 String splitStringLav =  dbRow.getField("LISTA_DESCRIZIONE_LAVORO").toString();
    		 StringTokenizer stLav = new StringTokenizer(splitStringLav, delims);
    		 while (stLav.hasMoreElements()) {
    			 descriPdl=descriPdl+stLav.nextElement()+"\n";
    		 }
     		 
     		 String impresaPdl="";
	   		 String splitStringImp =  dbRow.getField("LISTA_IMPRESE").toString();
	   		 StringTokenizer stImp = new StringTokenizer(splitStringImp, delims);
	   		 while (stImp.hasMoreElements()) {
	   			impresaPdl=impresaPdl+"Soc. "+stImp.nextElement()+" - \n";
	   		 }
    		 
     		 
     		 
     		String tabella="<table><tr><td colspan='3'><font face='Arial' size='2' color='black'>PdL nr.:</font></td></tr>" +
     				"<tr><td><font face='Arial' size='2' color='black'> "+nrPdl+" </font>    </td>" +
                    "<td><font face='Arial' size='2' color='black'> "+impresaPdl+" </font>    </td>" +
                    "<td><font face='Arial' size='2' color='black'> "+descriPdl+" </font></td></tr></table>";
            
            body=body.replace("[TABELLA]",tabella);
            
            
     		 
             String sender=Config.GetInstance().getProperty("mail.from.pdlweb");
             String recipients = (String) dbRow.getField("LISTA_EMAIL");
             String recipientsBCC="";
             String recipientsCC="";
             
             send( sender,  recipients.replaceAll(",", ";"),  recipientsCC,  recipientsBCC,  subject,  body);
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
            sendSMTPMail.setUsername(_userInfo.getUserId());

            sendSMTPMail.prepareMail();
            DeferredMailSender.getInstance().offer(sendSMTPMail);

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(),
                    "error sending mail sendMailApprover - sendSMTPMail: " + sendSMTPMail.toString());
        }
    }

}
