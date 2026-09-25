package net.projectsrl.wm.richieste.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionRicercaRendicontazioni
 * 
 */
public class FunctionRicercaRendicontazioni extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_rendicontazioni";
    private static final String PAGE_RISULTATI = "browse_rendicontazioni";    
    

    public FunctionRicercaRendicontazioni() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaRendicontazioni(ApplicationServices_itf applServices, String functionID, String functionName) {
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
    		
    		try {
				gestioneRendicontazioni(req);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
	    	String queryUpdate="UPDATE RENDICONTAZIONI SET "+req.getField("CAMPO")+"='"+(String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE")+req.getField("ST_APPROVAZ")+"' WHERE ID_RENDICONTAZIONE='"+req.getField("ID")+"'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String queryUpdateAll="UPDATE RENDICONTAZIONI SET "
					+ "LIV1_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV1_1 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_1 END, "
					+ "LIV1_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV1_2 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_2 END, "
					+ "LIV1_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV1_3 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_3 END, "
					+ "LIV1_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV1_4 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_4 END, "
					+ "LIV1_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV1_5 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV1_5 END, "
					+ "LIV2_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV2_1 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_1 END, "
					+ "LIV2_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV2_2 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_2 END, "
					+ "LIV2_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV2_3 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_3 END, "
					+ "LIV2_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV2_4 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_4 END, "
					+ "LIV2_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV2_5 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV2_5 END, "
					+ "LIV3_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV3_1 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_1 END, "
					+ "LIV3_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV3_2 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_2 END, "
					+ "LIV3_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV3_3 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_3 END, "
					+ "LIV3_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV3_4 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_4 END, "
					+ "LIV3_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV3_5 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV3_5 END, "
					+ "LIV4_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV4_1 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_1 END, "
					+ "LIV4_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV4_2 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_2 END, "
					+ "LIV4_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV4_3 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_3 END, "
					+ "LIV4_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV4_4 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_4 END, "
					+ "LIV4_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV4_5 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV4_5 END, "
					+ "LIV5_1 = CASE (SELECT coalesce(APPROVATORI.A_1,'') AS LIV5_1 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_1 END, "
					+ "LIV5_2 = CASE (SELECT coalesce(APPROVATORI.A_2,'') AS LIV5_2 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_2 END, "
					+ "LIV5_3 = CASE (SELECT coalesce(APPROVATORI.A_3,'') AS LIV5_3 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_3 END, "
					+ "LIV5_4 = CASE (SELECT coalesce(APPROVATORI.A_4,'') AS LIV5_4 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_4 END, "
					+ "LIV5_5 = CASE (SELECT coalesce(APPROVATORI.A_5,'') AS LIV5_5 FROM APPROVAZIONI_RENDICONTAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI_RENDICONTAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI_RENDICONTAZIONI.AZIENDA ='"+req.getField("AZIENDA_TENDINA")+"' AND ID_RICHIEDENTE ='"+req.getField("DIPENDENTE_AGGIORNARE")+"') WHEN '' THEN '"+req.getField("ST_APPROVAZ")+"' ELSE LIV5_5 END "
					+ " WHERE ID_RENDICONTAZIONE='"+req.getField("ID")+"'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateAll);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String queryUpdateApprovazione="UPDATE RENDICONTAZIONI SET APPROVAZIONE ="
					+ "CASE (SELECT CASE LEN(LIV1_1) WHEN 21 THEN SUBSTRING(LIV1_1,21,1) ELSE LIV1_1 END+CASE LEN(LIV1_2) WHEN 21 THEN SUBSTRING(LIV1_2,21,1) ELSE LIV1_2 END+CASE LEN(LIV1_3) WHEN 21 THEN SUBSTRING(LIV1_3,21,1) ELSE LIV1_3 END+CASE LEN(LIV1_4) WHEN 21 THEN SUBSTRING(LIV1_4,21,1) ELSE LIV1_4 END+CASE LEN(LIV1_5) WHEN 21 THEN SUBSTRING(LIV1_5,21,1) ELSE LIV1_5 END+CASE LEN(LIV2_1) WHEN 21 THEN SUBSTRING(LIV2_1,21,1) ELSE LIV2_1 END+CASE LEN(LIV2_2) WHEN 21 THEN SUBSTRING(LIV2_2,21,1) ELSE LIV2_2 END+CASE LEN(LIV2_3) WHEN 21 THEN SUBSTRING(LIV2_3,21,1) ELSE LIV2_3 END+CASE LEN(LIV2_4) WHEN 21 THEN SUBSTRING(LIV2_4,21,1) ELSE LIV2_4 END+CASE LEN(LIV2_5) WHEN 21 THEN SUBSTRING(LIV2_5,21,1) ELSE LIV2_5 END+CASE LEN(LIV3_1) WHEN 21 THEN SUBSTRING(LIV3_1,21,1) ELSE LIV3_1 END+CASE LEN(LIV3_2) WHEN 21 THEN SUBSTRING(LIV3_2,21,1) ELSE LIV3_2 END+CASE LEN(LIV3_3) WHEN 21 THEN SUBSTRING(LIV3_3,21,1) ELSE LIV3_3 END+CASE LEN(LIV3_4) WHEN 21 THEN SUBSTRING(LIV3_4,21,1) ELSE LIV3_4 END+CASE LEN(LIV3_5) WHEN 21 THEN SUBSTRING(LIV3_5,21,1) ELSE LIV3_5 END+CASE LEN(LIV4_1) WHEN 21 THEN SUBSTRING(LIV4_1,21,1) ELSE LIV4_1 END+CASE LEN(LIV4_2) WHEN 21 THEN SUBSTRING(LIV4_2,21,1) ELSE LIV4_2 END+CASE LEN(LIV4_3) WHEN 21 THEN SUBSTRING(LIV4_3,21,1) ELSE LIV4_3 END+CASE LEN(LIV4_4) WHEN 21 THEN SUBSTRING(LIV4_4,21,1) ELSE LIV4_4 END+CASE LEN(LIV4_5) WHEN 21 THEN SUBSTRING(LIV4_5,21,1) ELSE LIV4_5 END+CASE LEN(LIV5_1) WHEN 21 THEN SUBSTRING(LIV5_1,21,1) ELSE LIV5_1 END+CASE LEN(LIV5_2) WHEN 21 THEN SUBSTRING(LIV5_2,21,1) ELSE LIV5_2 END+CASE LEN(LIV5_3) WHEN 21 THEN SUBSTRING(LIV5_3,21,1) ELSE LIV5_3 END+CASE LEN(LIV5_4) WHEN 21 THEN SUBSTRING(LIV5_4,21,1) ELSE LIV5_4 END+CASE LEN(LIV5_5) WHEN 21 THEN SUBSTRING(LIV5_5,21,1) ELSE LIV5_5 END FROM RENDICONTAZIONI WHERE ID_RENDICONTAZIONE='"+req.getField("ID")+"')"
					+ "WHEN 'SSSSSSSSSSSSSSSSSSSSSSSSS' THEN 'S' ELSE"
					+ "(CASE WHEN (SELECT CASE LEN(LIV1_1) WHEN 21 THEN SUBSTRING(LIV1_1,21,1) ELSE LIV1_1 END+CASE LEN(LIV1_2) WHEN 21 THEN SUBSTRING(LIV1_2,21,1) ELSE LIV1_2 END+CASE LEN(LIV1_3) WHEN 21 THEN SUBSTRING(LIV1_3,21,1) ELSE LIV1_3 END+CASE LEN(LIV1_4) WHEN 21 THEN SUBSTRING(LIV1_4,21,1) ELSE LIV1_4 END+CASE LEN(LIV1_5) WHEN 21 THEN SUBSTRING(LIV1_5,21,1) ELSE LIV1_5 END+CASE LEN(LIV2_1) WHEN 21 THEN SUBSTRING(LIV2_1,21,1) ELSE LIV2_1 END+CASE LEN(LIV2_2) WHEN 21 THEN SUBSTRING(LIV2_2,21,1) ELSE LIV2_2 END+CASE LEN(LIV2_3) WHEN 21 THEN SUBSTRING(LIV2_3,21,1) ELSE LIV2_3 END+CASE LEN(LIV2_4) WHEN 21 THEN SUBSTRING(LIV2_4,21,1) ELSE LIV2_4 END+CASE LEN(LIV2_5) WHEN 21 THEN SUBSTRING(LIV2_5,21,1) ELSE LIV2_5 END+CASE LEN(LIV3_1) WHEN 21 THEN SUBSTRING(LIV3_1,21,1) ELSE LIV3_1 END+CASE LEN(LIV3_2) WHEN 21 THEN SUBSTRING(LIV3_2,21,1) ELSE LIV3_2 END+CASE LEN(LIV3_3) WHEN 21 THEN SUBSTRING(LIV3_3,21,1) ELSE LIV3_3 END+CASE LEN(LIV3_4) WHEN 21 THEN SUBSTRING(LIV3_4,21,1) ELSE LIV3_4 END+CASE LEN(LIV3_5) WHEN 21 THEN SUBSTRING(LIV3_5,21,1) ELSE LIV3_5 END+CASE LEN(LIV4_1) WHEN 21 THEN SUBSTRING(LIV4_1,21,1) ELSE LIV4_1 END+CASE LEN(LIV4_2) WHEN 21 THEN SUBSTRING(LIV4_2,21,1) ELSE LIV4_2 END+CASE LEN(LIV4_3) WHEN 21 THEN SUBSTRING(LIV4_3,21,1) ELSE LIV4_3 END+CASE LEN(LIV4_4) WHEN 21 THEN SUBSTRING(LIV4_4,21,1) ELSE LIV4_4 END+CASE LEN(LIV4_5) WHEN 21 THEN SUBSTRING(LIV4_5,21,1) ELSE LIV4_5 END+CASE LEN(LIV5_1) WHEN 21 THEN SUBSTRING(LIV5_1,21,1) ELSE LIV5_1 END+CASE LEN(LIV5_2) WHEN 21 THEN SUBSTRING(LIV5_2,21,1) ELSE LIV5_2 END+CASE LEN(LIV5_3) WHEN 21 THEN SUBSTRING(LIV5_3,21,1) ELSE LIV5_3 END+CASE LEN(LIV5_4) WHEN 21 THEN SUBSTRING(LIV5_4,21,1) ELSE LIV5_4 END+CASE LEN(LIV5_5) WHEN 21 THEN SUBSTRING(LIV5_5,21,1) ELSE LIV5_5 END FROM RENDICONTAZIONI WHERE ID_RENDICONTAZIONE='"+req.getField("ID")+"') LIKE '%N%' THEN 'N'"
					+ "ELSE 'A' END) END WHERE ID_RENDICONTAZIONE='"+req.getField("ID")+"'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovazione);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (getSessionRole(req).equals("A")){
				String queryUpdateApprovazioneS="UPDATE RENDICONTAZIONI SET APPROVAZIONE ='"+req.getField("ST_APPROVAZ")+"', ADMIN = 'X' WHERE ID_RENDICONTAZIONE='"+req.getField("ID")+"'";
				try {
					net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovazioneS);
				} catch (AppCrash e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
    	}
    	
    	
       
    	String cliente		= req.getField("CLIENTE");
    	String stato		= req.getField("APPROVAZIONE"); 
    	String dataDal		= Utils.ribaltaData(req.getField("DATA_DAL_FP"));
        String dataAl		= Utils.ribaltaData(req.getField("DATA_AL_FP"));
    	String azienda		= req.getField("AZIENDA_TENDINA");
        String dipendente	= req.getField("DIPENDENTE");
        
        
        if (getSessionRole(req).equals("D")){
            dipendente= (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
        }
        String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");

        String str_composed_where_cond 	= "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    		str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        
        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND RENDICONTAZIONI.AZIENDA_TENDINA = '"+azienda+"'";
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
        
        if (!dataDal.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_DAL >= '"+dataDal+"'";
        }
        if (!dataAl.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DATA_AL <= '"+dataAl+"'";
        }
        
        if (!cliente.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND RENDICONTAZIONI.CLIENTE like '%"+cliente+"%'";
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
    
    
    
    private void gestioneRendicontazioni( SsbServletRequest req) throws AppCrash {
		DataSet_itf dataSet = null;
		DataSetFactory dsFactory = DataSetFactory.getInstance();

		String dsName="DataSetRendicontazioni";

		try {
			dataSet = dsFactory.makeDataSet("", dsName);
			dataSet.open();
			
			// leggo tutte le righe del dataset ossia tutte le possibili competenze
			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				if (dbRow != null) {
					// leggo dalla riga della query codice completo di ogni singola competenza
					//
					String dbCODICE = (String) dbRow.getField("ID_RENDICONTAZIONE");
					String azienda = req.getField("AZIENDA");

					// verifico se sono state fatte delle modifiche alla pagina rispetto alla situazione precedente letta da DB e creo un flag che sarà TRUE solo se sarà stata fatta almeno una modifica
					
					String id = req.getField("ID");
					String dbId = dbRow.getField("ID_RENDICONTAZIONE").toString().trim();
					String strSpeseViaggio = req.getField("VALORE_SPESE_VIAGGIO_IMPORTO");
					String dbSpeseViaggio = dbRow.getField("SPESE_VIAGGIO_IMPORTO_A").toString().trim();
					String strSpeseTrasporto = req.getField("VALORE_SPESE_TRASPORTO_IMPORTO");
					String dbSpeseTrasporto = dbRow.getField("SPESE_TRASPORTO_IMPORTO_A").toString().trim();
					String strSpeseParcheggio = req.getField("VALORE_SPESE_PARCHEGGIO_IMPORTO");
					String dbSpeseParcheggio = dbRow.getField("SPESE_PARCHEGGIO_IMPORTO_A").toString().trim();
					String strSpeseVitto = req.getField("VALORE_SPESE_VITTO_IMPORTO");
					String dbSpeseVitto = dbRow.getField("SPESE_VITTO_IMPORTO_A").toString().trim();
					String strSpeseAlloggio = req.getField("VALORE_SPESE_ALLOGGIO_IMPORTO");
					String dbSpeseAlloggio = dbRow.getField("SPESE_ALLOGGIO_IMPORTO_A").toString().trim();
					String strSpesePedaggi = req.getField("VALORE_SPESE_PEDAGGI_IMPORTO");
					String dbSpesePedaggi = dbRow.getField("SPESE_PEDAGGI_IMPORTO_A").toString().trim();
					String strSpeseAltro = req.getField("VALORE_SPESE_ALTRO_IMPORTO");
					String dbSpeseAltro = dbRow.getField("SPESE_ALTRO_IMPORTO_A").toString().trim();



					
					if (dbId.equals(id)){
						if (!strSpeseViaggio.equals(dbSpeseViaggio) && !strSpeseViaggio.equals("")) {
							String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_VIAGGIO_IMPORTO_A="+strSpeseViaggio+" WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						if (!strSpeseTrasporto.equals(dbSpeseTrasporto) && !strSpeseTrasporto.equals("")) {
							String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_TRASPORTO_IMPORTO_A="+strSpeseTrasporto+" WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						if (!strSpeseParcheggio.equals(dbSpeseParcheggio) && !strSpeseParcheggio.equals("")) {
							String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_PARCHEGGIO_IMPORTO_A="+strSpeseParcheggio+" WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						if (!strSpeseVitto.equals(dbSpeseVitto) && !strSpeseVitto.equals("")) {
							String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_VITTO_IMPORTO_A="+strSpeseVitto+" WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						if (!strSpeseAlloggio.equals(dbSpeseAlloggio) && !strSpeseAlloggio.equals("")) {
							String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_ALLOGGIO_IMPORTO_A="+strSpeseAlloggio+" WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						if (!strSpesePedaggi.equals(dbSpesePedaggi) && !strSpesePedaggi.equals("")) {
							String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_PEDAGGI_IMPORTO_A="+strSpesePedaggi+" WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						if (!strSpeseAltro.equals(dbSpeseAltro) && !strSpeseAltro.equals("")) {
							String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_ALTRO_IMPORTO_A="+strSpeseAltro+" WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						String sqlUpdate ="UPDATE RENDICONTAZIONI SET SPESE_TOTALE_IMPORTO_A=(SPESE_ALLOGGIO_IMPORTO_A+SPESE_ALTRO_IMPORTO_A+SPESE_PARCHEGGIO_IMPORTO_A+SPESE_PEDAGGI_IMPORTO_A+SPESE_TRASPORTO_IMPORTO_A+SPESE_VIAGGIO_IMPORTO_A+SPESE_VITTO_IMPORTO_A) WHERE ID_RENDICONTAZIONE ='"+dbCODICE+"'";
						net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						
						if ((!strSpeseViaggio.equals(dbSpeseViaggio) && !strSpeseViaggio.equals("")) ||
							(!strSpeseTrasporto.equals(dbSpeseTrasporto) && !strSpeseTrasporto.equals("")) ||
							(!strSpeseParcheggio.equals(dbSpeseParcheggio) && !strSpeseParcheggio.equals("")) ||
							(!strSpeseVitto.equals(dbSpeseVitto) && !strSpeseVitto.equals("")) ||
							(!strSpeseAlloggio.equals(dbSpeseAlloggio) && !strSpeseAlloggio.equals("")) ||
							(!strSpesePedaggi.equals(dbSpesePedaggi) && !strSpesePedaggi.equals("")) ||
							(!strSpeseAltro.equals(dbSpeseAltro) && !strSpeseAltro.equals("")))
						{
							String sqlUpdate2 = "";
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV1_1='' WHERE LEN(LIV1_1)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV1_2='' WHERE LEN(LIV1_2)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV1_3='' WHERE LEN(LIV1_3)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV1_4='' WHERE LEN(LIV1_4)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV1_5='' WHERE LEN(LIV1_5)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV2_1='' WHERE LEN(LIV2_1)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV2_2='' WHERE LEN(LIV2_2)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV2_3='' WHERE LEN(LIV2_3)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV2_4='' WHERE LEN(LIV2_4)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV2_5='' WHERE LEN(LIV2_5)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV3_1='' WHERE LEN(LIV3_1)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV3_2='' WHERE LEN(LIV3_2)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV3_3='' WHERE LEN(LIV3_3)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV3_4='' WHERE LEN(LIV3_4)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV3_5='' WHERE LEN(LIV3_5)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV4_1='' WHERE LEN(LIV4_1)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV4_2='' WHERE LEN(LIV4_2)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV4_3='' WHERE LEN(LIV4_3)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV4_4='' WHERE LEN(LIV4_4)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV4_5='' WHERE LEN(LIV4_5)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV5_1='' WHERE LEN(LIV5_1)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV5_2='' WHERE LEN(LIV5_2)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV5_3='' WHERE LEN(LIV5_3)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV5_4='' WHERE LEN(LIV5_4)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET LIV5_5='' WHERE LEN(LIV5_5)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
							sqlUpdate2="UPDATE RENDICONTAZIONI SET APPROVAZIONE='' WHERE LEN(APPROVAZIONE)>0 AND ID_RENDICONTAZIONE ='"+dbCODICE+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
						}
					}
					
					
					

				}
			}
		} catch (Throwable th) {
			AppCrash ac=new AppCrash(th);
			ac.logContext("FunctionRicercaRendicontazioni", "Errore nella ricerca campi del dataset " + dsName);
			throw ac;
		} finally {
			// chiude il dataset per il conteggio degli elementi trovati
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (Throwable t) {
					AppCrash ac = new AppCrash(t);
					ac.logContext("FunctionRicercaRendicontazioni", "Errore nella close del dataset " + dsName);
				}
			}
		}
	}
    
}
