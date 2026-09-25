package net.projectsrl.wm.tabelleparametriche.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoParametri
 * 
 */
public class FunctionInserimentoParametri extends FunctionInserimento {

	private static final String PAGE = "inserimento_parametri";

	public FunctionInserimentoParametri() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetParametri");
	}

	public FunctionInserimentoParametri(ApplicationServices_itf applServices,
			String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetParametri");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req)
			throws AppCrash {

		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
		String prefissoParametro = req.getField("PREFISSO_PARAMETRO");
		String descrizioneParametro = req.getField("DESCRIZIONE_PARAMETRO");

		if (option == null || option.equals("")
				|| option.equals(OPZIONE_INSERIMENTO)) {
			if (prefissoParametro.equals("GST")) {
				String progressivo = prossimoProgressivo();
				templateData.put("ORDINE", progressivo);
			} else {
				templateData.put("ORDINE", "");
			}
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			putIdParametroInSession(req, templateData);
			return templateData;
		} else {
			templateData.put("SUFFISSO_PARAMETRO", req.getField("CODICE")
					.substring(3));
			putIdParametroInSession(req, templateData);
		}
		putIdParametroInSession(req, templateData);

		templateData.put(PREFISSO_PARAMETRO, prefissoParametro);
		templateData.put(DESCRIZIONE_PARAMETRO, descrizioneParametro);

		return templateData;
	}

	@SuppressWarnings("unchecked")
	private void putIdParametroInSession(SsbServletRequest req,
			HashMap templateData) {

		String idParametro = (String) templateData.get("AZIENDA");
		if (idParametro == null || idParametro.equals("")) {
			idParametro = req.getField("AZIENDA");
			if (idParametro == null || idParametro.equals("")) {
				return;
			}
		}

		req.getSession(false).setAttribute(Costanti_itf.ID_PARAMETRO_SESSIONE,
				idParametro);
		templateData.put("AZIENDA_UPLOAD", (String) req.getSession(false)
				.getAttribute("AZIENDA_CEDOLINI"));
		templateData.put("MATRICOLA_UPLOAD", (String) req.getSession(false)
				.getAttribute("MATRICOLA"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void mostra(SsbServletRequest req, SsbServletResponse res,
			UserSecurityInfo userInfo) throws AppCrash {

		HashMap templateData = (HashMap) setCommonTags(req, userInfo);
		templateData.put(
				"ID_DIPENDENTE_SESSIONE",
				(String) req.getSession(false).getAttribute(
						"ID_DIPENDENTE_SESSIONE"));
		templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false)
				.getAttribute("AZIENDA_SESSIONE"));

		// inizio inserimento di tutti i gruppi
		if (req.getField("CODICE").startsWith("APP")
				|| req.getField("PREFISSO_PARAMETRO").equals("APP")) {
			String azienda = req.getField("AZIENDA");
			if (azienda.equals("") && (!req.getField("CODICE").equals(""))) {
				azienda = req.getField("CODICE").substring(3).toUpperCase();
			}
			if (azienda.equals("")) {
				azienda = (String) req.getSession(false).getAttribute(
						"AZIENDA_SESSIONE");
			}

			DataSet_itf dataSet = null;
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			String dsName = "DataSetGruppiAziendali";
			try {
				dataSet = dsFactory.makeDataSet("", dsName);
				dataSet.open();
				while (dataSet.hasMoreElements()) {
					Row_itf dbRow = (Row_itf) dataSet.nextElement();
					if (dbRow != null) {
						String esiste = getEsisteRiga(azienda,
								(String) dbRow.getField("CODICE"));
						if (!esiste.equals("S")) {
							String sqlInsertGruppi = "INSERT INTO PARA (CODICE,DESCRI,AZIENDA,GR_AUTORIZZAZ) VALUES ('APP"
									+ Utils.getUnique()
									+ "','Approvatore','"
									+ azienda
									+ "','"
									+ (String) dbRow.getField("CODICE") + "')";
							net.projectsrl.wm.utils.WMUtils
									.executeQuery(sqlInsertGruppi);
						}

					}
				}
			} catch (Throwable th) {
				AppCrash ac = new AppCrash(th);
				ac.logContext("FunctionInserimentoParametri",
						"Errore nella ricerca campi del dataset " + dsName);
				throw ac;
			} finally {
				if (dataSet != null) {
					try {
						dataSet.close();
					} catch (Throwable t) {
						AppCrash ac = new AppCrash(t);
						ac.logContext("FunctionInserimentoParametri",
								"Errore nella close del dataset " + dsName);
					}
				}
			}
		}
		// fine inserimento di tutti i gruppi

		if (req.getField("OPZIONE_INSERIMENTO_MODIFICA").equals(
				OPZIONE_MODIFICA)) {
			String tipologia = req.getField("TIPOLOGIA");
			templateData = loadVarStandard(templateData, req);
			templateData.put("TIPOLOGIA", tipologia);
		} else {
			templateData = loadVarStandard(templateData, req);
		}
		templateData.put("RUOLO_SESSIONE", getSessionRole(req));
		templateData.put("SALVATO", "");
		templateData.put("SALVATO_REMINDER", "");
		_applicationSrv.displayPage(PAGE, templateData,
				setPageDatasetParam(PAGE, req, templateData), res);
	}

	private String getEsisteRiga(String azienda, String codice) throws AppCrash {
		String dati = "";
		String idTrovato = "";
		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", "DataSetParaApp");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("AZIENDA_TENDINA", azienda + "' AND GR_AUTORIZZAZ='"
					+ codice);
			dataSet.setParam(params);
			dataSet.open();
			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();
				idTrovato = dbRow.getField("AZIENDA").toString().trim();
				if (azienda.equals(idTrovato)) {
					dati = "S";
				} else {
					dati = "";
				}
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
					ac.logContext(this.getClass().getName(),
							"Errore nella close del dataset");
				}
			}
		}
		return dati;
	}

	private String prossimoProgressivo() throws AppCrash {

		String progressivo = "001";

		DataSet_itf dataSet = null;

		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", "DataSetUltimoOrdinePara");
			dataSet.open();

			if (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();
				String ultimoProgressivo = (String) dbRow.getField("ULTIMO");
				int ultimoProgressivoInt = 0;
				if (ultimoProgressivo != null && !ultimoProgressivo.equals("")) {
					ultimoProgressivoInt = Integer.parseInt(ultimoProgressivo) + 1;
					progressivo = String.format("%03d", ultimoProgressivoInt);
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
		return progressivo;
	}
}
