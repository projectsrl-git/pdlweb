package net.projectsrl.wm.richieste.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionRicercaStraordinari
 * 
 */
public class FunctionRicercaStraordinari extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_straordinari";
    private static final String PAGE_RISULTATI = "browse_straordinari";    
    

    public FunctionRicercaStraordinari() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaStraordinari(ApplicationServices_itf applServices, String functionID, String functionName) {
        super(applServices, functionID, functionName);
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    /**
     * Prepara la where condition da passare alla query per la visualizzazione della pagina di ricerca
     * 
     * @param SsbServletRequest+ req
     * @param HashMap queryParameter
     * 
     * @return HashMap
     */
    @SuppressWarnings("unchecked")
	protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {

    	if (req.getField("AGGIORNARE").equals("SI")){
	    	String queryUpdate="UPDATE STRAORDINARI SET "+req.getField("CAMPO")+"='"+(String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE")+req.getField("ST_APPROVAZ")+"' WHERE ID_STRAORDINARIO='"+req.getField("ID")+"'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String queryUpdateAll="UPDATE STRAORDINARI SET "
					+ "LIV1_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV1_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_1 END, "
					+ "LIV1_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV1_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_2 END, "
					+ "LIV1_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV1_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_3 END, "
					+ "LIV1_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV1_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_4 END, "
					+ "LIV1_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV1_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_5 END, "
					+ "LIV2_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV2_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_1 END, "
					+ "LIV2_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV2_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_2 END, "
					+ "LIV2_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV2_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_3 END, "
					+ "LIV2_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV2_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_4 END, "
					+ "LIV2_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV2_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_5 END, "
					+ "LIV3_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV3_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_1 END, "
					+ "LIV3_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV3_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_2 END, "
					+ "LIV3_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV3_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_3 END, "
					+ "LIV3_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV3_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_4 END, "
					+ "LIV3_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV3_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_5 END, "
					+ "LIV4_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV4_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_1 END, "
					+ "LIV4_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV4_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_2 END, "
					+ "LIV4_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV4_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_3 END, "
					+ "LIV4_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV4_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_4 END, "
					+ "LIV4_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV4_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_5 END, "
					+ "LIV5_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV5_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_1 END, "
					+ "LIV5_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV5_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_2 END, "
					+ "LIV5_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV5_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_3 END, "
					+ "LIV5_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV5_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_4 END, "
					+ "LIV5_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV5_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_5 END "
					+ " WHERE ID_STRAORDINARIO='"+req.getField("ID")+"'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateAll);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String queryUpdateApprovazione="UPDATE STRAORDINARI SET APPROVAZIONE ="
					+ "CASE (SELECT CASE LEN(LIV1_1) WHEN 21 THEN SUBSTRING(LIV1_1,21,1) ELSE LIV1_1 END+CASE LEN(LIV1_2) WHEN 21 THEN SUBSTRING(LIV1_2,21,1) ELSE LIV1_2 END+CASE LEN(LIV1_3) WHEN 21 THEN SUBSTRING(LIV1_3,21,1) ELSE LIV1_3 END+CASE LEN(LIV1_4) WHEN 21 THEN SUBSTRING(LIV1_4,21,1) ELSE LIV1_4 END+CASE LEN(LIV1_5) WHEN 21 THEN SUBSTRING(LIV1_5,21,1) ELSE LIV1_5 END+CASE LEN(LIV2_1) WHEN 21 THEN SUBSTRING(LIV2_1,21,1) ELSE LIV2_1 END+CASE LEN(LIV2_2) WHEN 21 THEN SUBSTRING(LIV2_2,21,1) ELSE LIV2_2 END+CASE LEN(LIV2_3) WHEN 21 THEN SUBSTRING(LIV2_3,21,1) ELSE LIV2_3 END+CASE LEN(LIV2_4) WHEN 21 THEN SUBSTRING(LIV2_4,21,1) ELSE LIV2_4 END+CASE LEN(LIV2_5) WHEN 21 THEN SUBSTRING(LIV2_5,21,1) ELSE LIV2_5 END+CASE LEN(LIV3_1) WHEN 21 THEN SUBSTRING(LIV3_1,21,1) ELSE LIV3_1 END+CASE LEN(LIV3_2) WHEN 21 THEN SUBSTRING(LIV3_2,21,1) ELSE LIV3_2 END+CASE LEN(LIV3_3) WHEN 21 THEN SUBSTRING(LIV3_3,21,1) ELSE LIV3_3 END+CASE LEN(LIV3_4) WHEN 21 THEN SUBSTRING(LIV3_4,21,1) ELSE LIV3_4 END+CASE LEN(LIV3_5) WHEN 21 THEN SUBSTRING(LIV3_5,21,1) ELSE LIV3_5 END+CASE LEN(LIV4_1) WHEN 21 THEN SUBSTRING(LIV4_1,21,1) ELSE LIV4_1 END+CASE LEN(LIV4_2) WHEN 21 THEN SUBSTRING(LIV4_2,21,1) ELSE LIV4_2 END+CASE LEN(LIV4_3) WHEN 21 THEN SUBSTRING(LIV4_3,21,1) ELSE LIV4_3 END+CASE LEN(LIV4_4) WHEN 21 THEN SUBSTRING(LIV4_4,21,1) ELSE LIV4_4 END+CASE LEN(LIV4_5) WHEN 21 THEN SUBSTRING(LIV4_5,21,1) ELSE LIV4_5 END+CASE LEN(LIV5_1) WHEN 21 THEN SUBSTRING(LIV5_1,21,1) ELSE LIV5_1 END+CASE LEN(LIV5_2) WHEN 21 THEN SUBSTRING(LIV5_2,21,1) ELSE LIV5_2 END+CASE LEN(LIV5_3) WHEN 21 THEN SUBSTRING(LIV5_3,21,1) ELSE LIV5_3 END+CASE LEN(LIV5_4) WHEN 21 THEN SUBSTRING(LIV5_4,21,1) ELSE LIV5_4 END+CASE LEN(LIV5_5) WHEN 21 THEN SUBSTRING(LIV5_5,21,1) ELSE LIV5_5 END FROM STRAORDINARI WHERE ID_STRAORDINARIO='"+req.getField("ID")+"')"
					+ "WHEN 'SSSSSSSSSSSSSSSSSSSSSSSSS' THEN 'S' ELSE"
					+ "(CASE WHEN (SELECT CASE LEN(LIV1_1) WHEN 21 THEN SUBSTRING(LIV1_1,21,1) ELSE LIV1_1 END+CASE LEN(LIV1_2) WHEN 21 THEN SUBSTRING(LIV1_2,21,1) ELSE LIV1_2 END+CASE LEN(LIV1_3) WHEN 21 THEN SUBSTRING(LIV1_3,21,1) ELSE LIV1_3 END+CASE LEN(LIV1_4) WHEN 21 THEN SUBSTRING(LIV1_4,21,1) ELSE LIV1_4 END+CASE LEN(LIV1_5) WHEN 21 THEN SUBSTRING(LIV1_5,21,1) ELSE LIV1_5 END+CASE LEN(LIV2_1) WHEN 21 THEN SUBSTRING(LIV2_1,21,1) ELSE LIV2_1 END+CASE LEN(LIV2_2) WHEN 21 THEN SUBSTRING(LIV2_2,21,1) ELSE LIV2_2 END+CASE LEN(LIV2_3) WHEN 21 THEN SUBSTRING(LIV2_3,21,1) ELSE LIV2_3 END+CASE LEN(LIV2_4) WHEN 21 THEN SUBSTRING(LIV2_4,21,1) ELSE LIV2_4 END+CASE LEN(LIV2_5) WHEN 21 THEN SUBSTRING(LIV2_5,21,1) ELSE LIV2_5 END+CASE LEN(LIV3_1) WHEN 21 THEN SUBSTRING(LIV3_1,21,1) ELSE LIV3_1 END+CASE LEN(LIV3_2) WHEN 21 THEN SUBSTRING(LIV3_2,21,1) ELSE LIV3_2 END+CASE LEN(LIV3_3) WHEN 21 THEN SUBSTRING(LIV3_3,21,1) ELSE LIV3_3 END+CASE LEN(LIV3_4) WHEN 21 THEN SUBSTRING(LIV3_4,21,1) ELSE LIV3_4 END+CASE LEN(LIV3_5) WHEN 21 THEN SUBSTRING(LIV3_5,21,1) ELSE LIV3_5 END+CASE LEN(LIV4_1) WHEN 21 THEN SUBSTRING(LIV4_1,21,1) ELSE LIV4_1 END+CASE LEN(LIV4_2) WHEN 21 THEN SUBSTRING(LIV4_2,21,1) ELSE LIV4_2 END+CASE LEN(LIV4_3) WHEN 21 THEN SUBSTRING(LIV4_3,21,1) ELSE LIV4_3 END+CASE LEN(LIV4_4) WHEN 21 THEN SUBSTRING(LIV4_4,21,1) ELSE LIV4_4 END+CASE LEN(LIV4_5) WHEN 21 THEN SUBSTRING(LIV4_5,21,1) ELSE LIV4_5 END+CASE LEN(LIV5_1) WHEN 21 THEN SUBSTRING(LIV5_1,21,1) ELSE LIV5_1 END+CASE LEN(LIV5_2) WHEN 21 THEN SUBSTRING(LIV5_2,21,1) ELSE LIV5_2 END+CASE LEN(LIV5_3) WHEN 21 THEN SUBSTRING(LIV5_3,21,1) ELSE LIV5_3 END+CASE LEN(LIV5_4) WHEN 21 THEN SUBSTRING(LIV5_4,21,1) ELSE LIV5_4 END+CASE LEN(LIV5_5) WHEN 21 THEN SUBSTRING(LIV5_5,21,1) ELSE LIV5_5 END FROM STRAORDINARI WHERE ID_STRAORDINARIO='"+req.getField("ID")+"') LIKE '%N%' THEN 'N'"
					+ "ELSE 'A' END) END WHERE ID_STRAORDINARIO='"+req.getField("ID")+"'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovazione);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (getSessionRole(req).equals("A")){
				String queryUpdateApprovazioneS="UPDATE STRAORDINARI SET APPROVAZIONE ='"+req.getField("ST_APPROVAZ")+"', ADMIN = 'X' WHERE ID_STRAORDINARIO='"+req.getField("ID")+"'";
				try {
					net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovazioneS);
				} catch (AppCrash e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			if(req.getField("DATA_DAL").equals(req.getField("DATA_AL"))){
				String queryInsertTimesheet="INSERT INTO TIMESHT (IDTIMESHT, IDRISUMANA,IDCOMMESSA,IDCOMPXCOM,DATA,MINUTI,ORE,ANNO,MESE,CODRIS,COMMESSA,COMPCOMM) SELECT"
						+ "(CASE WHEN APPROVAZIONE='S' THEN '"+Utils.getUnique()+"' END) AS IDTIMESHT,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN DIPENDENTE END) AS IDRISUMANA,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN '00000000000000000000' END) AS IDCOMMESSA,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN '' END) AS IDCOMPXCOM,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN DATA_DAL END) AS DATA,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN '0' END) AS MINUTI,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN ORE END) AS ORE,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN SUBSTRING(DATA_DAL,1,4) END) AS ANNO,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN SUBSTRING(DATA_DAL,6,2) END) AS MESE,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN DIPENDENTE END) AS CODRIS,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN '00000000000000000000' END) AS COMMESSA,"
						+ "(CASE WHEN APPROVAZIONE='S' THEN TIPO_PERMESSO END) AS COMPCOMM "
						+ "FROM STRAORDINARI WHERE ID_STRAORDINARIO='"+req.getField("ID")+"'";
				try {
					net.projectsrl.wm.utils.WMUtils.executeQuery(queryInsertTimesheet);
				} catch (AppCrash e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				String dataDalCompleta = req.getField("DATA_DAL");
		    	String dataAlCompleta = req.getField("DATA_AL");
		    	String dataDalAnno = dataDalCompleta.substring(6);
		    	String dataDalMese = dataDalCompleta.substring(3,5);
		    	int mesi=0;
		    	int i =0;
		    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		    	Date d1 = null;
		    	Date d2 = null;
		    	try {
		    	    d1 = format.parse(dataDalCompleta);
		    	    d2 = format.parse(dataAlCompleta);
		    	} catch (ParseException e) {
		    	    e.printStackTrace();
		    	}    
		    	long diff = d2.getTime() - d1.getTime();
		    	long diffDays = diff / (60 * 60 * 1000 * 24);    
		    	int fine = (int) diffDays+1;

		    	
		    	
		    	Calendar startCalendar = new GregorianCalendar();
		    	startCalendar.setTime(d1);
		    	Calendar endCalendar = new GregorianCalendar();
		    	endCalendar.setTime(d2);

		    	int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		    	mesi = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		    	
		    	String dataDaScrivere=dataDalCompleta;
		    	while (i<fine){
	         	    
		         	   String queryInsertTimesheet="INSERT INTO TIMESHT (IDTIMESHT, IDRISUMANA,IDCOMMESSA,IDCOMPXCOM,DATA,MINUTI,ORE,ANNO,MESE,CODRIS,COMMESSA,COMPCOMM) SELECT"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '"+Utils.getUnique()+"' END) AS IDTIMESHT,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN DIPENDENTE END) AS IDRISUMANA,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '00000000000000000000' END) AS IDCOMMESSA,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '' END) AS IDCOMPXCOM,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '"+Utils.ribaltaData(dataDaScrivere)+"' END) AS DATA,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '0' END) AS MINUTI,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN ORE END) AS ORE,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '"+dataDalAnno+"' END) AS ANNO,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '"+dataDalMese+"' END) AS MESE,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN DIPENDENTE END) AS CODRIS,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN '00000000000000000000' END) AS COMMESSA,"
								+ "(CASE WHEN APPROVAZIONE='S' THEN TIPO_PERMESSO END) AS COMPCOMM "
								+ "FROM STRAORDINARI WHERE ID_STRAORDINARIO='"+req.getField("ID")+"'";
						try {
							net.projectsrl.wm.utils.WMUtils.executeQuery(queryInsertTimesheet);
						} catch (AppCrash e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	         	    
	         	    String prossimoGiorno="";
	         	    String prossimoMese=dataDaScrivere.substring(3,5);
	         	    String prossimoAnno=dataDaScrivere.substring(6);
	         	    
	         	    
	         	    if (mesi>=1){
		         	    if ((Integer.parseInt(dataDaScrivere.substring(0,2))+1)>Integer.parseInt(Utils.getUltimoGiornoMese(dataDaScrivere.substring(3,5), dataDaScrivere.substring(6)))){
		         	    	prossimoGiorno="01";
		         	    	if ((Integer.parseInt(dataDaScrivere.substring(3,5))+1)>12){
		         	    		prossimoMese="01";
		         	    		prossimoAnno=Integer.toString(Integer.parseInt(prossimoAnno)+1);
		         	    	}else{
		         	    		prossimoMese=Integer.toString(Integer.parseInt(prossimoMese)+1);
		         	    	}
		         	    }else{
		         	    	prossimoGiorno=Integer.toString(Integer.parseInt(dataDaScrivere.substring(0,2))+1);
		         	    }
	         	    }else{        	    
	         	    	prossimoGiorno=Integer.toString(Integer.parseInt(dataDaScrivere.substring(0,2))+1);
	         	    }
	         	    
	         	    

	         	    if (prossimoGiorno.equals("1") || prossimoGiorno.equals("2") || prossimoGiorno.equals("3") || prossimoGiorno.equals("4") || prossimoGiorno.equals("5") || prossimoGiorno.equals("6") || prossimoGiorno.equals("7") || prossimoGiorno.equals("8") || prossimoGiorno.equals("9")){
	         	    	prossimoGiorno="0"+prossimoGiorno;
	         	    }
	         	    
	         	    if (prossimoMese.equals("1") || prossimoMese.equals("2") || prossimoMese.equals("3") || prossimoMese.equals("4") || prossimoMese.equals("5") || prossimoMese.equals("6") || prossimoMese.equals("7") || prossimoMese.equals("8") || prossimoMese.equals("9")){
	         	    	prossimoMese="0"+prossimoMese;
	         	    }
	         	    
	         	    if (prossimoAnno.equals("1") || prossimoAnno.equals("2") || prossimoAnno.equals("3") || prossimoAnno.equals("4") || prossimoAnno.equals("5") || prossimoAnno.equals("6") || prossimoAnno.equals("7") || prossimoAnno.equals("8") || prossimoAnno.equals("9")){
	         	    	prossimoAnno="0"+prossimoAnno;
	         	    }


	         	    dataDaScrivere=prossimoGiorno+"/"+prossimoMese+"/"+prossimoAnno;
		    		++i;
		    	}
		    	
		    
			}
    	}
    	
    	
        
       
        String stato		= req.getField("STATO_APPROVAZIONE"); 
        String azienda		= req.getField("AZIENDA_TENDINA");
        String dipendente	= req.getField("DIPENDENTE");
        String dataDal		= Utils.ribaltaData(req.getField("DATA_DAL_FP"));
        String dataAl		= Utils.ribaltaData(req.getField("DATA_AL_FP"));
        
        
        if (getSessionRole(req).equals("D")){
            dipendente= (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
        }
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        
        if(getSessionRole(req).equals("D")){
        	azienda=aziendaSessione;
        }

        String str_composed_where_cond 	= "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!dataDal.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_DAL >= '"+dataDal+"'";
        }
        if (!dataAl.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_AL <= '"+dataAl+"'";
        }
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND STRAORDINARI.AZIENDA_TENDINA = '"+azienda+"'";
        }
        if (!dipendente.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DIPENDENTE = '"+dipendente+"'";
        }
        
        if (!stato.trim().equalsIgnoreCase("")) {
        	if (stato.trim().equals("S")) {
        		str_composed_where_cond = str_composed_where_cond + " AND STATO_APPROVAZIONE = 'S'";
        	} 
        	if (stato.trim().equals("N")) {
        		str_composed_where_cond = str_composed_where_cond + " AND STATO_APPROVAZIONE = 'N'";
        	} 
        	if (stato.trim().equals("A")) {
        		str_composed_where_cond = str_composed_where_cond + " AND (APPROVAZIONE = 'A' OR APPROVAZIONE='')";
        	} 
        }
            
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	if (str_composed_where_cond.startsWith("(")){
        		str_composed_where_cond=" ("+str_composed_where_cond.substring(4);
        	}else{
        		str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	}
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
        
    	return queryParameter;
    }    
}
