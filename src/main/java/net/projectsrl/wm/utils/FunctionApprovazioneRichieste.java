package net.projectsrl.wm.utils;

import java.io.IOException;
import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.core.FunctionWebApp_base;
import net.projectsrl.wm.db.FeriePermessiDAO;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.richieste.core.FunctionRicercaFeriePermessi;

/**
 * FunctionCancellaTestataDettagli
 * 
 */
public class FunctionApprovazioneRichieste extends FunctionWebApp_base {

	private static final String DATASET_FP_DESCRI = "DataSetGSTFeriePermessiDescri";

	public FunctionApprovazioneRichieste() {

		super();
	}

	public FunctionApprovazioneRichieste(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}

	public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
		elabora(req, res, userInfo);
	}

	@SuppressWarnings("unchecked")
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		String page = req.getField("NEXT_PAGE");

		HashMap templateData = (HashMap) setCommonTags(req, userInfo);
		templateData = setTemplateDataFromRequest(templateData, req);

		if (req.getField("AGGIORNARE_DOC").equals("SI")) {
			DBTransaction dbTransaction = new DBTransaction();
			FeriePermessiDAO fp = new FeriePermessiDAO(dbTransaction);
			fp.setField(FeriePermessiDAO.ID_FERIEPERMESSO, req.getField("ID"));
			if (fp.retrieve()) {
				fp.setField(FeriePermessiDAO.DOCUMENTAZIONE, req.getField("ST_APPROVAZ"));
				fp.update();
			}
			dbTransaction.commit();
			dbTransaction.end();

		}

		if (req.getField("AGGIORNARE").equals("SI")) {
			String queryUpdate = "UPDATE FERIE_PERMESSI SET " + req.getField("CAMPO") + "='"
					+ (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE")
					+ req.getField("ST_APPROVAZ") + "' WHERE ID_FERIEPERMESSO='" + req.getField("ID") + "'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String queryUpdateAll = "UPDATE FERIE_PERMESSI SET "
					+ "LIV1_1 = CASE (SELECT distinct coalesce(APPROVATORI.A_1,'') AS LIV1_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV1_1 END, "
					+ "LIV1_2 = CASE (SELECT distinct coalesce(APPROVATORI.A_2,'') AS LIV1_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV1_2 END, "
					+ "LIV1_3 = CASE (SELECT distinct coalesce(APPROVATORI.A_3,'') AS LIV1_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV1_3 END, "
					+ "LIV1_4 = CASE (SELECT distinct coalesce(APPROVATORI.A_4,'') AS LIV1_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV1_4 END, "
					+ "LIV1_5 = CASE (SELECT distinct coalesce(APPROVATORI.A_5,'') AS LIV1_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_1 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV1_5 END, "
					+ "LIV2_1 = CASE (SELECT distinct coalesce(APPROVATORI.A_1,'') AS LIV2_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV2_1 END, "
					+ "LIV2_2 = CASE (SELECT distinct coalesce(APPROVATORI.A_2,'') AS LIV2_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV2_2 END, "
					+ "LIV2_3 = CASE (SELECT distinct coalesce(APPROVATORI.A_3,'') AS LIV2_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV2_3 END, "
					+ "LIV2_4 = CASE (SELECT distinct coalesce(APPROVATORI.A_4,'') AS LIV2_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV2_4 END, "
					+ "LIV2_5 = CASE (SELECT distinct coalesce(APPROVATORI.A_5,'') AS LIV2_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_2 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV2_5 END, "
					+ "LIV3_1 = CASE (SELECT distinct coalesce(APPROVATORI.A_1,'') AS LIV3_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV3_1 END, "
					+ "LIV3_2 = CASE (SELECT distinct coalesce(APPROVATORI.A_2,'') AS LIV3_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV3_2 END, "
					+ "LIV3_3 = CASE (SELECT distinct coalesce(APPROVATORI.A_3,'') AS LIV3_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV3_3 END, "
					+ "LIV3_4 = CASE (SELECT distinct coalesce(APPROVATORI.A_4,'') AS LIV3_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV3_4 END, "
					+ "LIV3_5 = CASE (SELECT distinct coalesce(APPROVATORI.A_5,'') AS LIV3_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_3 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV3_5 END, "
					+ "LIV4_1 = CASE (SELECT distinct coalesce(APPROVATORI.A_1,'') AS LIV4_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV4_1 END, "
					+ "LIV4_2 = CASE (SELECT distinct coalesce(APPROVATORI.A_2,'') AS LIV4_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV4_2 END, "
					+ "LIV4_3 = CASE (SELECT distinct coalesce(APPROVATORI.A_3,'') AS LIV4_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV4_3 END, "
					+ "LIV4_4 = CASE (SELECT distinct coalesce(APPROVATORI.A_4,'') AS LIV4_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV4_4 END, "
					+ "LIV4_5 = CASE (SELECT distinct coalesce(APPROVATORI.A_5,'') AS LIV4_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_4 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV4_5 END, "
					+ "LIV5_1 = CASE (SELECT distinct coalesce(APPROVATORI.A_1,'') AS LIV5_1 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV5_1 END, "
					+ "LIV5_2 = CASE (SELECT distinct coalesce(APPROVATORI.A_2,'') AS LIV5_2 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV5_2 END, "
					+ "LIV5_3 = CASE (SELECT distinct coalesce(APPROVATORI.A_3,'') AS LIV5_3 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV5_3 END, "
					+ "LIV5_4 = CASE (SELECT distinct coalesce(APPROVATORI.A_4,'') AS LIV5_4 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV5_4 END, "
					+ "LIV5_5 = CASE (SELECT distinct coalesce(APPROVATORI.A_5,'') AS LIV5_5 FROM APPROVAZIONI LEFT OUTER JOIN RICHIEDENTI_DETT ON APPROVAZIONI.ID_GRUPPO = RICHIEDENTI_DETT.ID_GRUPPO LEFT OUTER JOIN APPROVATORI ON APPROVATORI.ID_GRUPPO = LIVELLO_5 WHERE APPROVAZIONI.AZIENDA ='"
					+ req.getField("AZIENDA_TENDINA") + "' AND ID_RICHIEDENTE ='"
					+ req.getField("DIPENDENTE_AGGIORNARE") + "') WHEN '' THEN '" + req.getField("ST_APPROVAZ")
					+ "' ELSE LIV5_5 END " + " WHERE ID_FERIEPERMESSO='" + req.getField("ID") + "'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateAll);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String queryUpdateApprovazione = "UPDATE FERIE_PERMESSI SET APPROVAZIONE ="
					+ "CASE (SELECT CASE LEN(LIV1_1) WHEN 21 THEN SUBSTRING(LIV1_1,21,1) ELSE LIV1_1 END+CASE LEN(LIV1_2) WHEN 21 THEN SUBSTRING(LIV1_2,21,1) ELSE LIV1_2 END+CASE LEN(LIV1_3) WHEN 21 THEN SUBSTRING(LIV1_3,21,1) ELSE LIV1_3 END+CASE LEN(LIV1_4) WHEN 21 THEN SUBSTRING(LIV1_4,21,1) ELSE LIV1_4 END+CASE LEN(LIV1_5) WHEN 21 THEN SUBSTRING(LIV1_5,21,1) ELSE LIV1_5 END+CASE LEN(LIV2_1) WHEN 21 THEN SUBSTRING(LIV2_1,21,1) ELSE LIV2_1 END+CASE LEN(LIV2_2) WHEN 21 THEN SUBSTRING(LIV2_2,21,1) ELSE LIV2_2 END+CASE LEN(LIV2_3) WHEN 21 THEN SUBSTRING(LIV2_3,21,1) ELSE LIV2_3 END+CASE LEN(LIV2_4) WHEN 21 THEN SUBSTRING(LIV2_4,21,1) ELSE LIV2_4 END+CASE LEN(LIV2_5) WHEN 21 THEN SUBSTRING(LIV2_5,21,1) ELSE LIV2_5 END+CASE LEN(LIV3_1) WHEN 21 THEN SUBSTRING(LIV3_1,21,1) ELSE LIV3_1 END+CASE LEN(LIV3_2) WHEN 21 THEN SUBSTRING(LIV3_2,21,1) ELSE LIV3_2 END+CASE LEN(LIV3_3) WHEN 21 THEN SUBSTRING(LIV3_3,21,1) ELSE LIV3_3 END+CASE LEN(LIV3_4) WHEN 21 THEN SUBSTRING(LIV3_4,21,1) ELSE LIV3_4 END+CASE LEN(LIV3_5) WHEN 21 THEN SUBSTRING(LIV3_5,21,1) ELSE LIV3_5 END+CASE LEN(LIV4_1) WHEN 21 THEN SUBSTRING(LIV4_1,21,1) ELSE LIV4_1 END+CASE LEN(LIV4_2) WHEN 21 THEN SUBSTRING(LIV4_2,21,1) ELSE LIV4_2 END+CASE LEN(LIV4_3) WHEN 21 THEN SUBSTRING(LIV4_3,21,1) ELSE LIV4_3 END+CASE LEN(LIV4_4) WHEN 21 THEN SUBSTRING(LIV4_4,21,1) ELSE LIV4_4 END+CASE LEN(LIV4_5) WHEN 21 THEN SUBSTRING(LIV4_5,21,1) ELSE LIV4_5 END+CASE LEN(LIV5_1) WHEN 21 THEN SUBSTRING(LIV5_1,21,1) ELSE LIV5_1 END+CASE LEN(LIV5_2) WHEN 21 THEN SUBSTRING(LIV5_2,21,1) ELSE LIV5_2 END+CASE LEN(LIV5_3) WHEN 21 THEN SUBSTRING(LIV5_3,21,1) ELSE LIV5_3 END+CASE LEN(LIV5_4) WHEN 21 THEN SUBSTRING(LIV5_4,21,1) ELSE LIV5_4 END+CASE LEN(LIV5_5) WHEN 21 THEN SUBSTRING(LIV5_5,21,1) ELSE LIV5_5 END FROM FERIE_PERMESSI WHERE ID_FERIEPERMESSO='"
					+ req.getField("ID") + "')" + "WHEN 'SSSSSSSSSSSSSSSSSSSSSSSSS' THEN 'S' ELSE"
					+ "(CASE WHEN (SELECT CASE LEN(LIV1_1) WHEN 21 THEN SUBSTRING(LIV1_1,21,1) ELSE LIV1_1 END+CASE LEN(LIV1_2) WHEN 21 THEN SUBSTRING(LIV1_2,21,1) ELSE LIV1_2 END+CASE LEN(LIV1_3) WHEN 21 THEN SUBSTRING(LIV1_3,21,1) ELSE LIV1_3 END+CASE LEN(LIV1_4) WHEN 21 THEN SUBSTRING(LIV1_4,21,1) ELSE LIV1_4 END+CASE LEN(LIV1_5) WHEN 21 THEN SUBSTRING(LIV1_5,21,1) ELSE LIV1_5 END+CASE LEN(LIV2_1) WHEN 21 THEN SUBSTRING(LIV2_1,21,1) ELSE LIV2_1 END+CASE LEN(LIV2_2) WHEN 21 THEN SUBSTRING(LIV2_2,21,1) ELSE LIV2_2 END+CASE LEN(LIV2_3) WHEN 21 THEN SUBSTRING(LIV2_3,21,1) ELSE LIV2_3 END+CASE LEN(LIV2_4) WHEN 21 THEN SUBSTRING(LIV2_4,21,1) ELSE LIV2_4 END+CASE LEN(LIV2_5) WHEN 21 THEN SUBSTRING(LIV2_5,21,1) ELSE LIV2_5 END+CASE LEN(LIV3_1) WHEN 21 THEN SUBSTRING(LIV3_1,21,1) ELSE LIV3_1 END+CASE LEN(LIV3_2) WHEN 21 THEN SUBSTRING(LIV3_2,21,1) ELSE LIV3_2 END+CASE LEN(LIV3_3) WHEN 21 THEN SUBSTRING(LIV3_3,21,1) ELSE LIV3_3 END+CASE LEN(LIV3_4) WHEN 21 THEN SUBSTRING(LIV3_4,21,1) ELSE LIV3_4 END+CASE LEN(LIV3_5) WHEN 21 THEN SUBSTRING(LIV3_5,21,1) ELSE LIV3_5 END+CASE LEN(LIV4_1) WHEN 21 THEN SUBSTRING(LIV4_1,21,1) ELSE LIV4_1 END+CASE LEN(LIV4_2) WHEN 21 THEN SUBSTRING(LIV4_2,21,1) ELSE LIV4_2 END+CASE LEN(LIV4_3) WHEN 21 THEN SUBSTRING(LIV4_3,21,1) ELSE LIV4_3 END+CASE LEN(LIV4_4) WHEN 21 THEN SUBSTRING(LIV4_4,21,1) ELSE LIV4_4 END+CASE LEN(LIV4_5) WHEN 21 THEN SUBSTRING(LIV4_5,21,1) ELSE LIV4_5 END+CASE LEN(LIV5_1) WHEN 21 THEN SUBSTRING(LIV5_1,21,1) ELSE LIV5_1 END+CASE LEN(LIV5_2) WHEN 21 THEN SUBSTRING(LIV5_2,21,1) ELSE LIV5_2 END+CASE LEN(LIV5_3) WHEN 21 THEN SUBSTRING(LIV5_3,21,1) ELSE LIV5_3 END+CASE LEN(LIV5_4) WHEN 21 THEN SUBSTRING(LIV5_4,21,1) ELSE LIV5_4 END+CASE LEN(LIV5_5) WHEN 21 THEN SUBSTRING(LIV5_5,21,1) ELSE LIV5_5 END FROM FERIE_PERMESSI WHERE ID_FERIEPERMESSO='"
					+ req.getField("ID") + "') LIKE '%N%' THEN 'N'" + "ELSE 'A' END) END WHERE ID_FERIEPERMESSO='"
					+ req.getField("ID") + "'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdateApprovazione);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (getSessionRole(req).equals("A")) {
				DBTransaction dbTransaction;
				try {
					dbTransaction = new DBTransaction();
					FeriePermessiDAO fp = new FeriePermessiDAO(dbTransaction);
					fp.setField(FeriePermessiDAO.ID_FERIEPERMESSO, req.getField("ID"));
					if (fp.retrieve()) {
						fp.setField(FeriePermessiDAO.APPROVAZIONE, req.getField("ST_APPROVAZ"));
						fp.setField(FeriePermessiDAO.ADMIN, req.getField("X"));
						fp.update();
					}
					dbTransaction.commit();
					dbTransaction.end();
				} catch (AppCrash e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			try {
				inviaMail(req);
			} catch (AppCrash e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		HashMap<String, String> queryParameter = new HashMap<String, String>();
		FunctionRicercaFeriePermessi cls = new FunctionRicercaFeriePermessi();
		cls.prepareWhereCondition(req, queryParameter);

		_applicationSrv.displayPage(page, templateData, setPageDatasetParam(page, req, templateData), res);

	}

	private HashMap inviaMail(SsbServletRequest req) throws AppCrash {
		String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");

		String[] datiRichiedente = getApprovazioneFerie(req.getField("ID"));

		String elencoDestinatari = datiRichiedente[1];
		String esito = "";
		if (!datiRichiedente[0].equals("")) {
			if (datiRichiedente[0].equals("S")) {
				esito = "approvata";
			} else {
				esito = "rifiutata";
			}
		}

		String oggetto = "Esito richiesta ferie/permessi DAFNE";

		String corpo = "";
		try {
			corpo = leggiHtml();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		corpo = corpo.replace("#NOME#", "utente");

		corpo = corpo.replace("#TESTO_1#", "La richiesta di ferie/permessi è stata " + esito);

		corpo = corpo.replace("#TESTO_2#",
				"Tipo permesso/ferie: " + getDescriFeriePermesso(req.getField("TIPO_PERMESSO")));
		corpo = corpo.replace("#TESTO_3#", "Dal " + req.getField("DATA_DAL") + " al " + req.getField("DATA_AL"));

		if (!req.getField("ORE_DAL").equals("") && !req.getField("ORE_AL").equals("")) {
			corpo = corpo.replace("#TESTO_4#",
					"Orario: dalle " + req.getField("ORE_DAL") + " alle " + req.getField("ORE_AL"));
		} else {
			corpo = corpo.replace("#TESTO_4#", "");
		}

		if (!req.getField("ORE").equals("")) {
			corpo = corpo.replace("#TESTO_5#", "Ore: " + req.getField("ORE"));
		} else {
			corpo = corpo.replace("#TESTO_5#", "");
		}

		corpo = corpo.replace("#URL#", "Per poter visualizzare la richiesta <a href='"
				+ Config.GetInstance().getProperty("indirizzo.portale") + "'> clicca qui </a>");
		corpo = corpo.replace("#FIRMA#", "Dafne - Data Flow Network System");
		corpo = corpo.replace("#FOOTER1#",
				"Dafne Data Flow Network System || Vers. 1.0.0 © Studio Nebbiolo 2014 || All Rights Reserved");
		corpo = corpo.replace("#FOOTER2#", "");

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
			// templateData.put("EMAIL_INVIATA", "OK");
			// templateData.put("EMAIL_INVIATA_MESSAGE",
			// Config.GetInstance().getProperty("Message.email_inviata_ok",
			// NO_MESSAGE));

		} catch (Throwable e) {
			// templateData.put("EMAIL_INVIATA", "KO");
			// templateData.put("EMAIL_INVIATA_MESSAGE",
			// Config.GetInstance().getProperty("Message.email_inviata_ko",
			// NO_MESSAGE));
			new AppCrash(e);
		}

		// return templateData;
		return null;
	}

	private String getDescriFeriePermesso(String idFeriePermesso) throws AppCrash {
		String dati = "";
		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", DATASET_FP_DESCRI);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("CODICE", idFeriePermesso);
			dataSet.setParam(params);
			dataSet.open();
			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();
				dati = dbRow.getField("DESCRI").toString().trim();
			}
			dataSet.close();
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
		return dati;
	}

	private String[] getApprovazioneFerie(String idFeriePermesso) throws AppCrash {
		String[] dati = new String[2];
		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", "DataSetBrowseFeriePermessi");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("COMPOSED_WHERE_COND", "WHERE fp.ID_FERIEPERMESSO='" + idFeriePermesso + "'");
			dataSet.setParam(params);
			dataSet.open();
			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();
				dati[0] = dbRow.getField("APPROVAZIONE").toString().trim();
				dati[1] = dbRow.getField("EMAIL").toString().trim();
			}
			dataSet.close();
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
		return dati;
	}

}
