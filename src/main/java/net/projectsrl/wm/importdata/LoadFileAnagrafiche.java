
package net.projectsrl.wm.importdata;

import java.util.HashMap;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.db.CurriculImportDAO;
import net.projectsrl.wm.db.IstrFormDAO;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.utils.RandomPasswordGenerator;
import net.projectsrl.wm.utils.Utils;

public class LoadFileAnagrafiche extends LoadAsciiFile_base {

    boolean        _cancellarePrecedenteCaricamento = false;
    
    int noOfCAPSAlpha = 1;
    int noOfDigits = 1;
    int noOfSplChars = 0;
    int minLen = 6;
    int maxLen = 7;

    @Override
    protected String getSeparator() {

        return "|";
    }

    @Override
    protected void store(DBTransaction dbtransaction, String user, SsbServletRequest req) throws AppCrash {

        if (getFieldValues().size() < 1) {
            return;
        }

        String idUnivoco=Utils.getUnique();
        CurriculImportDAO cv = new CurriculImportDAO(dbtransaction);
        String codice=getStringValue(2);
        while (codice.startsWith("0") && !codice.equals("0")){
        	codice=codice.substring(codice.length()+1);
        }
        if (codice.equals("")){
        	codice="0";
        }
        cv.setField(CurriculImportDAO.CODICE, codice);
        
        cv.setField(CurriculImportDAO.AZIENDA_INSERIMENTO, (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
        if (cv.retrieve()) {
        	String statoCiv=getStringValue(13);
        	if (statoCiv.length()==1){
        		// sto caricando il secondo excel
        		if (!statoCiv.equals("")){
            		int statoCivInt=Integer.parseInt(statoCiv);
            		statoCiv = String.format("%03d", statoCivInt);
            	}
            	cv.setField(CurriculImportDAO.IDSTATOCIV, statoCiv);
            	cv.update();
            	
            	IstrFormDAO istr = new IstrFormDAO(dbtransaction);
            	istr.setField(IstrFormDAO.DAGGANCIO, cv.getField("TAGGANCIO"));
            	istr.setField(IstrFormDAO.ID_ISTRUZIONE, Utils.getUnique());
            	String livelloIstr=getStringValue(22);
            	if (!livelloIstr.equals("")){
            		int livelloIstrInt=Integer.parseInt(livelloIstr);
            		livelloIstr = String.format("%03d", livelloIstrInt);
            	}
            	istr.setField(IstrFormDAO.LIVELLO_ISTR, livelloIstr);
            	istr.insert();
        	}else{
        		// sto caricando il terzo excel
        		String qualifica=getStringValue(17);
        		if (!qualifica.equals("")){
        			int qualificaInt=Integer.parseInt(qualifica);
        			qualifica = String.format("%03d", qualificaInt);
        		}
        		cv.setField(CurriculImportDAO.QUALIFICA_DIPENDENTE, qualifica);
        		cv.setField(CurriculImportDAO.MATR_INPS, getStringValue(22));
        		cv.setField(CurriculImportDAO.CODICE_IBAN, getStringValue(23));
        		
            	cv.update();
            	
            	
        	}
        	
        	
 
        	
        }else{
        	cv.setField(CurriculImportDAO.TAGGANCIO, idUnivoco);
            cv.setField(CurriculImportDAO.AZIENDA_CV, getStringValue(0));
            String matricolaCV=getStringValue(5);
            while (matricolaCV.startsWith("0") && !matricolaCV.equals("0")){
            	matricolaCV=matricolaCV.substring(matricolaCV.length()+1);
            }
            if (matricolaCV.equals("")){
            	matricolaCV="0";
            }
            cv.setField(CurriculImportDAO.CODICE, getStringValue(2));
            cv.setField(CurriculImportDAO.COGNOME, getStringValue(3).replace("'","''").toUpperCase().trim());
            cv.setField(CurriculImportDAO.NOME, getStringValue(4).replace("'","''").toUpperCase().trim());
            cv.setField(CurriculImportDAO.MATRICOLA, matricolaCV);
            cv.setField(CurriculImportDAO.D_REGISTRAZ, Utils.getStringDataOggiRibaltata());
            String codiceFiscale = getStringValue(6);
            cv.setField(CurriculImportDAO.CODFISCALE, codiceFiscale);
            if (codiceFiscale.substring(9,11).startsWith("4")){
            	cv.setField(CurriculImportDAO.SESSO, "F");
            }else{
            	cv.setField(CurriculImportDAO.SESSO, "M");
            }
            if (getStringValue(7).equals("")){
            	cv.setField(CurriculImportDAO.DATA_ASSUNZIONE, "");
            }else{
            	cv.setField(CurriculImportDAO.DATA_ASSUNZIONE, getStringValue(7).substring(6)+"/"+getStringValue(7).substring(3,5)+"/"+getStringValue(7).substring(0,2));
            }
            
            if (getStringValue(8).equals("")){
            	cv.setField(CurriculImportDAO.DATA_CESSAZIONE, "");
            }else{
            	cv.setField(CurriculImportDAO.DATA_CESSAZIONE, getStringValue(8).substring(6)+"/"+getStringValue(8).substring(3,5)+"/"+getStringValue(8).substring(0,2));
            }
            
            if (getStringValue(10).equals("")){
            	cv.setField(CurriculImportDAO.D_NASCITA, "");
            }else{
            	cv.setField(CurriculImportDAO.D_NASCITA, getStringValue(10).substring(6)+"/"+getStringValue(10).substring(3,5)+"/"+getStringValue(10).substring(0,2));
            }
            
            
            cv.setField(CurriculImportDAO.LOCNASCITA, getStringValue(12).replace("'","''").toUpperCase().trim());
            String indirizzoR = getStringValue(14).substring(5).trim();
            
            int indirizzoFineR=0;
            int indirizzoFineR0 = indirizzoR.indexOf(" 0");
            int indirizzoFineR1 = indirizzoR.indexOf(" 1");
            int indirizzoFineR2 = indirizzoR.indexOf(" 2");
            int indirizzoFineR3 = indirizzoR.indexOf(" 3");
            int indirizzoFineR4 = indirizzoR.indexOf(" 4");
            int indirizzoFineR5 = indirizzoR.indexOf(" 5");
            int indirizzoFineR6 = indirizzoR.indexOf(" 6");
            int indirizzoFineR7 = indirizzoR.indexOf(" 7");
            int indirizzoFineR8 = indirizzoR.indexOf(" 8");
            int indirizzoFineR9 = indirizzoR.indexOf(" 9");
            int indirizzoFineRSNC = indirizzoR.indexOf(" SNC");
            
            if (indirizzoFineR0!=-1){
            	indirizzoFineR=indirizzoFineR0;
            }
            if (indirizzoFineR1!=-1){
            	indirizzoFineR=indirizzoFineR1;
            }
            if (indirizzoFineR2!=-1){
            	indirizzoFineR=indirizzoFineR2;
            }
            if (indirizzoFineR3!=-1){
            	indirizzoFineR=indirizzoFineR3;
            }
            if (indirizzoFineR4!=-1){
            	indirizzoFineR=indirizzoFineR4;
            }
            if (indirizzoFineR5!=-1){
            	indirizzoFineR=indirizzoFineR5;
            }
            if (indirizzoFineR6!=-1){
            	indirizzoFineR=indirizzoFineR6;
            }
            if (indirizzoFineR7!=-1){
            	indirizzoFineR=indirizzoFineR7;
            }
            if (indirizzoFineR8!=-1){
            	indirizzoFineR=indirizzoFineR8;
            }
            if (indirizzoFineR9!=-1){
            	indirizzoFineR=indirizzoFineR9;
            }
            if (indirizzoFineRSNC!=-1){
            	indirizzoFineR=indirizzoFineRSNC;
            }
            
            cv.setField(CurriculImportDAO.INDRESIDENZ, indirizzoR.substring(0,indirizzoFineR).trim().replace("'","''").toUpperCase());
            cv.setField(CurriculImportDAO.CIVICO_RESID, indirizzoR.substring(indirizzoFineR).trim());
            cv.setField(CurriculImportDAO.COD_CATASTO_RESID, getStringValue(15));
            cv.setField(CurriculImportDAO.LOCRESIDENZ, getStringValue(16).replace("'","''").toUpperCase().trim());
            cv.setField(CurriculImportDAO.CAPRESIDENZ, getStringValue(17));
            cv.setField(CurriculImportDAO.PROVRESIDENZ, getStringValue(18));
            String indirizzoD = getStringValue(20).substring(5).trim();
            
            int indirizzoFineD=0;
            int indirizzoFineD0 = indirizzoD.indexOf(" 0");
            int indirizzoFineD1 = indirizzoD.indexOf(" 1");
            int indirizzoFineD2 = indirizzoD.indexOf(" 2");
            int indirizzoFineD3 = indirizzoD.indexOf(" 3");
            int indirizzoFineD4 = indirizzoD.indexOf(" 4");
            int indirizzoFineD5 = indirizzoD.indexOf(" 5");
            int indirizzoFineD6 = indirizzoD.indexOf(" 6");
            int indirizzoFineD7 = indirizzoD.indexOf(" 7");
            int indirizzoFineD8 = indirizzoD.indexOf(" 8");
            int indirizzoFineD9 = indirizzoD.indexOf(" 9");
            int indirizzoFineDSNC = indirizzoD.indexOf(" SNC");
            
            if (indirizzoFineD0!=-1){
            	indirizzoFineD=indirizzoFineD0;
            }
            if (indirizzoFineD1!=-1){
            	indirizzoFineD=indirizzoFineD1;
            }
            if (indirizzoFineD2!=-1){
            	indirizzoFineD=indirizzoFineD2;
            }
            if (indirizzoFineD3!=-1){
            	indirizzoFineD=indirizzoFineD3;
            }
            if (indirizzoFineD4!=-1){
            	indirizzoFineD=indirizzoFineD4;
            }
            if (indirizzoFineD5!=-1){
            	indirizzoFineD=indirizzoFineD5;
            }
            if (indirizzoFineD6!=-1){
            	indirizzoFineD=indirizzoFineD6;
            }
            if (indirizzoFineD7!=-1){
            	indirizzoFineD=indirizzoFineD7;
            }
            if (indirizzoFineD8!=-1){
            	indirizzoFineD=indirizzoFineD8;
            }
            if (indirizzoFineD9!=-1){
            	indirizzoFineD=indirizzoFineD9;
            }
            if (indirizzoFineDSNC!=-1){
            	indirizzoFineD=indirizzoFineDSNC;
            }
            
            cv.setField(CurriculImportDAO.INDDOMICIL, indirizzoD.substring(0,indirizzoFineD).trim().replace("'","''").toUpperCase());
            cv.setField(CurriculImportDAO.CIVICO_DOMIC, indirizzoD.substring(indirizzoFineD).trim());
            if (getStringValue(21).trim().equals("")){
            	cv.setField(CurriculImportDAO.COD_CATASTO_DOMIC, getStringValue(21));
            }else{
            	cv.setField(CurriculImportDAO.COD_CATASTO_DOMIC, getStringValue(21).substring(0,4));
            }
            
            cv.setField(CurriculImportDAO.LOCDOMICIL, getStringValue(22).replace("'","''").toUpperCase().trim());
            cv.setField(CurriculImportDAO.EMAIL, getStringValue(24));
            String new_pwd = "";
            
                char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);
                new_pwd= new String(pswd);
                if (new_pwd.length()<7){
		    		new_pwd=new_pwd+Utils.getUnique().substring(14, 18);
		    	}else{
		    		new_pwd=new_pwd+Utils.getUnique().substring(14, 17);
		    	}
            
				
		        String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");
		        String destinatari_mail = getStringValue(24);
		        String nominativo = getStringValue(4)+" "+getStringValue(3);
		        
	            String sqlInsertUtente ="INSERT INTO UTENTI (USERID,PASSWORD,RUOLO,ID_CODICE,NOME,COGNOME,EMAIL,DATA,DATA_SC,ATTIVO,AZIENDA,ID_DIPENDENTE,PWD_SCADUTA)" +
			    		" VALUES ('"+getStringValue(24)+"','"+new_pwd+"','D','"+Utils.getUnique()+"','"+getStringValue(4).replace("'", "''")+"','"+getStringValue(3).replace("'", "''")+"','"+getStringValue(24)+"','"+Utils.getStringDataOggiRibaltata()+"','2999/12/31','S','"+getStringValue(0)+"','"+idUnivoco+"','S')";
				        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsertUtente);

				        
        	inviaMail(from, new_pwd, destinatari_mail, nominativo);
            
            cv.insert();
            
            
        }
        
    }

    @Override
    protected String getStringValue(int index) {

        String value = super.getStringValue(index);
        return value;
    }

    @Override
    public void setDefaultData(HashMap<String, String> defaultData) throws AppCrash {

    }

    @Override
    public void loadData(String user, String absolutePathFileName, SsbServletRequest req) throws AppCrash {

        _cancellarePrecedenteCaricamento = true;
        super.loadData(user, absolutePathFileName, req);
    }

    @Override
    protected void setFieldValues(String text) {

        if (text.contains(";;")) {
            text = text.replace(";;", "; ;");
            text = text.replace(";;", "; ;");
            text = text.replace(";;", "; ;");
            text = text.replace(";;", "; ;");
            text = text.replace(";;", "; ;");
        }

        text = text.replaceAll("\"", "");

        super.setFieldValues(text);
    }
    
    
    
    
    @SuppressWarnings({ "unchecked" })
    private HashMap inviaMail(String from, String new_pwd, String destinatari_mail, String nominativo) throws AppCrash {
	    String portale = Config.GetInstance().getProperty("indirizzo.portale");
        String elencoDestinatari = destinatari_mail;
        String oggetto = "";
        String corpo = "Gentile "+nominativo.toUpperCase()+",\n/nQuesta è una mail inviata automaticamente da DAFNE.\n/n";
    	oggetto="Nuovo utente DAFNE";
    	corpo += "E' stato creato il suo profilo utente per l'accesso a <a href='"+portale+"'>"+portale+"</a>, qui di seguito trova le credenziali per l'accesso al portale:\n" +
		"/n";
	    corpo += "<i>Userid</i>: " + destinatari_mail + "\n/n";
	    corpo += "<i>Password</i>: " + new_pwd + "\n/n";
	    corpo += "\n/nAcceda al sistema utilizzando la nuova password. Potrà modificarla con una nuova password utilizzando l'apposita pagina di Cambio Password.\n\n/n/nCordiali Saluti\n/n<i>Il Team DAFNE</i>";
	    System.out.println(nominativo.toUpperCase()+ " - " + elencoDestinatari);
	    System.out.println(elencoDestinatari);
        SendSMTPMail sendSMTPMail = new SendSMTPMail();
        sendSMTPMail.setFrom(from);
        sendSMTPMail.setSubject(oggetto);
        sendSMTPMail.setBody(corpo);
        sendSMTPMail.setTo(elencoDestinatari);
        sendSMTPMail.setServer(Config.GetInstance().getProperty("mail.SMTPHost"));

        try {
            MyAuthenticator auth = null;
            if (!Config.GetInstance().getProperty("mail.SMTPHost.user", "").equals("")) {
                auth = new MyAuthenticator();
            }
            sendSMTPMail.prepareMail(auth, false, "", "", "S");

            DeferredMailSender.getInstance().offer(sendSMTPMail);
            //templateData.put("EMAIL_INVIATA", "OK");
            //templateData.put("EMAIL_INVIATA_MESSAGE",
            //        Config.GetInstance().getProperty("Message.email_inviata_ok", NO_MESSAGE));

        } catch (Throwable e) {
            //templateData.put("EMAIL_INVIATA", "KO");
            //templateData.put("EMAIL_INVIATA_MESSAGE",
            //        Config.GetInstance().getProperty("Message.email_inviata_ko", NO_MESSAGE));
            new AppCrash(e);
        }
        
        return null;
    }

	

}
