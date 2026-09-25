
package net.projectsrl.pdlweb.interferenze.core;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.pdlweb.moduli.limod70.db.LiMod70DAO;
import net.projectsrl.pdlweb.moduli.limod70.db.LiMod70DettagliDAO;
import net.projectsrl.webapp.authentication.MenuItem;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;

public class FunctionProcessaTuttoImpianto extends FunctionProjectWebApp_base {

	private static final String DS_PROGRESSIVO_MODULO = "DSProgressivoLIMOD";
	private static final String PARAM_TABLE_NAME = "TABLE_NAME";
	private static final String PARAM_FIELD_NAME = "FIELD_NAME";
	private static final String PARAM_CONDITION = "PARAM_CONDITION";
	private static final String PAGE_NAME = "ricercalimod70";

	public FunctionProcessaTuttoImpianto(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}
	
	

	@Override
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		Map<String, Object> templateData = createMapFromRequest(req, userInfo);

		String idImpianto = (String) templateData.get("ID_IMPIANTO");
		String turnoSelezionato = "";
		if (req.getSession(false).getAttribute("CODICE_TURNO_SELEZIONATO") != null) {
			turnoSelezionato = (String) req.getSession(false).getAttribute("CODICE_TURNO_SELEZIONATO");
		}
		String idArea = "";
		int idModulo = 0;

		// su id impianto vedere tutte aree in interferenza e fare insert,
		// suddivise per area con un dataset

		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();

			dataSet = dsFactory.makeDataSet("", "DSInterferenzePDLElenco");

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("ID_IMPIANTO", idImpianto+" AND ID_AREA IN (SELECT ID_AREA FROM V_INTERFERENZE_PDL_FINAL WHERE fl_tutto_impianto=TRUE) ");
			dataSet.setParam(params);
			dataSet.open();

			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();
				String idPDL = dbRow.getField("ID_PDL_CORRETTO").toString();
				String valutazione= (String) templateData.get("VALUTAZIONE");
				String distanzaRispetto= (String) templateData.get("DISTANZA_RISPETTO");

				if (idArea.equals(dbRow.getField("ID_AREA").toString()) && !idArea.isEmpty()) {

					LiMod70DAO limod70 = new LiMod70DAO();

					String nrDettaglio = getNextSequentialNumber("LIMOD70_DETTAGLI", "NR_DETTAGLIO");
					datiDettaglio(idImpianto, userInfo, idModulo, nrDettaglio, idArea, idPDL);

				} else {

					LiMod70DAO limod70 = new LiMod70DAO();

					String idAzienda = dbRow.getField("ID_AZIENDA").toString();
					idArea = dbRow.getField("ID_AREA").toString();
					String nrModulo = getNextSequentialNumber("LIMOD70", "NR_MODULO");

					limod70.setAttribute(LiMod70DAO.ID_MODULO, "");
					limod70.setAttribute(LiMod70DAO.NR_MODULO, nrModulo);
					limod70.setAttribute(LiMod70DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
					limod70.setAttribute(LiMod70DAO.STATO, StatiRichiesta.APPROVED.getCode());
					limod70.setAttribute(LiMod70DAO.ID_IMPIANTO, idImpianto);
					limod70.setAttribute(LiMod70DAO.ID_AZIENDA, idAzienda);
					limod70.setAttribute(LiMod70DAO.ID_AREA, idArea);
					limod70.setAttribute(LiMod70DAO.VALUTAZIONE, valutazione);
					limod70.setAttribute(LiMod70DAO.DISTANZA_RISPETTO, distanzaRispetto);
					limod70.setAttribute(LiMod70DAO.CODICE_TURNO, turnoSelezionato);
					limod70.setAttribute(LiMod70DAO.TS_INS, new Timestamp(System.currentTimeMillis()));
					limod70.setAttribute(LiMod70DAO.ORA, getOrario());
					limod70.setAttribute(LiMod70DAO.ID_UTENTE_INS, getSpecificUserInfo(userInfo).getIdUtente());

					limod70.insert();

					idModulo = (int) limod70.getAttribute("ID_MODULO");
					String nrDettaglio = getNextSequentialNumber("LIMOD70_DETTAGLI", "NR_DETTAGLIO");
					datiDettaglio(idImpianto, userInfo, idModulo, nrDettaglio, idArea, idPDL);
				}

			}
		} catch (AppCrash ac) {
			ac.logContext(this.getClass().getName(), "");
			throw ac;

		} finally {
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (AppCrash ac) {
					ac.logContext(this.getClass().getName(), "");
					throw ac;
				}
			}
		}

		String aaa="";
		_applicationSrv.displayPage(PAGE_NAME, templateData, setPageDatasetParam(PAGE_NAME, req, templateData),
				res);

	}

	protected boolean isAnInsert(SsbServletRequest req) {

		return Util.IsEmpty(req.getField(LiMod70DAO.ID_MODULO));
	}

	private String getNextSequentialNumber(String table, String field) throws AppCrash {

		String dsName = DS_PROGRESSIVO_MODULO;

		DataSet_itf dataSet = null;

		DataSetFactory dsFactory = DataSetFactory.getInstance();
		Integer lastSequentialNumeber = new Integer(0);

		try {
			dataSet = dsFactory.makeDataSet("", dsName);

			HashMap<String, String> param = new HashMap<String, String>();
			param.put(PARAM_TABLE_NAME, table);
			param.put(PARAM_FIELD_NAME, field);
			if (table.contains("_DETTAGLI")) {
				param.put(PARAM_CONDITION, "");
			} else {
				param.put(PARAM_CONDITION, " WHERE STATO='APP'");
			}
			dataSet.setParam(param);
			dataSet.open();

			if (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				if (dbRow != null) {
					String strLast = (String) dbRow.getField("ultimo");
					if (strLast != null && !strLast.trim().equals("")) {
						lastSequentialNumeber = Integer.valueOf(strLast);
						lastSequentialNumeber += 1;
					}
				}
			}
		} catch (AppCrash ac) {
			ac.logContext(this.getClass().getName(),
					"Errore nella ricerca dell'ultimo progressivo del dataset " + dsName);
			throw ac;
		} finally {
			// chiude il dataset per il conteggio degli elementi trovati
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (Throwable t) {
					AppCrash ac = new AppCrash(t);
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset " + dsName);
				}
			}
		}

		if (lastSequentialNumeber == null || lastSequentialNumeber.intValue() == 0) {
			lastSequentialNumeber = new Integer(1);
		}

		return "" + lastSequentialNumeber;

	}

	private static String getOrario() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", java.util.Locale.ITALY);
		return sdf.format(new Date());

	}

	private void datiDettaglio(String idImpianto, UserSecurityInfo userInfo, int idModulo, String nrDettaglio,
			String idArea, String idPDL) throws AppCrash {

		LiMod70DettagliDAO limod70dettagli = new LiMod70DettagliDAO();
		limod70dettagli.setAttribute(LiMod70DettagliDAO.ID_MODULO, idModulo);
		limod70dettagli.setAttribute(LiMod70DettagliDAO.NR_DETTAGLIO, nrDettaglio);
		limod70dettagli.setAttribute(LiMod70DettagliDAO.ID_PDL, idPDL);
		limod70dettagli.insert();

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

	            String menuId = "187";
	            String menuIdSup = "185";
	            String languageISO = "it";
	            String label = "Ricerca";
	            String function = "RicercaLIMOD70";
	            String link = "astro?FUNCTIONID=RicercaLIMOD70";
	            int itemLevel = 2;
	            String path = "1520";
	            String linkChain = "astro?FUNCTIONID=Home;#;astro?FUNCTIONID=RicercaLIMOD70";
	            boolean readOnly = false;
	            int nrOfChildren = 0;
	            String icon = "";

	            pathDescri = "Home / LIMOD70 - Valutazione preliminare rischi interferenziali / Visualizza - Modifica";

	            MenuItem selectedMenuItem = new MenuItem(menuId, menuIdSup, languageISO, label, function, link, itemLevel,
	                    path, pathDescri, linkChain, readOnly, nrOfChildren, icon);

	            map.put(WebAppConstants_itf.SELECTED_MENU_ITEM, selectedMenuItem);

	            map.put(WebAppConstants_itf.INCLUDED_MENU, "include/included_menu.include");
	            map.put(WebAppConstants_itf.SELECTED_PATH, path);

	        }

	        return pathDescri;

	    }

}
