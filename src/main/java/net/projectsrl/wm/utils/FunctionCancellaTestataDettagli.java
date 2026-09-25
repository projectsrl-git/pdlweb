package net.projectsrl.wm.utils;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.db.DbUtils;
import net.projectsrl.db.PjDAO_base;
import net.projectsrl.wm.core.FunctionWebApp_base;

/**
 * FunctionCancellaTestataDettagli
 * 
 */
public class FunctionCancellaTestataDettagli extends FunctionWebApp_base {

	public FunctionCancellaTestataDettagli() {

		super();
	}

	public FunctionCancellaTestataDettagli(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}

	public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
		elabora(req, res, userInfo);
	}

	@SuppressWarnings("unchecked")
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		HashMap templateData = (HashMap) setCommonTags(req, userInfo);

		templateData = setTemplateDataFromRequest(templateData, req);

		String page = req.getField("NEXT_PAGE");
		String dsNameTestata = req.getField("DS_TESTATA");
		String idUniqueTestata = req.getField("DS_TESTATA_ID_UNIQUE");
		templateData.put("ID_DIPENDENTE",req.getField("ID_NECESSARIO"));
		templateData.put("ID_COMMESSA",req.getField("ID_NECESSARIO"));

		templateData.put("PREFISSO_PARAMETRO", req.getField("PR_PARAMETRO"));
		templateData.put("DESCRIZIONE_PARAMETRO", req.getField("DES_PARAMETRO"));
		
		try {
			if (page.equals("ricerca_curriculum")){
				String sqlDeleteUtente="DELETE FROM UTENTI WHERE ID_DIPENDENTE='"+idUniqueTestata+"'";
			    net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteUtente);
			}
			cancellaTestata(idUniqueTestata, dsNameTestata);
			
			templateData.put(TAG_MESSAGE, Config.GetInstance().getProperty("DS." + dsNameTestata + ".Message.cancellazioneOK", NO_MESSAGE));
		} catch (AppCrash e) {
			templateData.put(TAG_MESSAGE, Config.GetInstance().getProperty("DS." + dsNameTestata + ".Message.cancellazioneKO", NO_MESSAGE));
		} finally {
			templateData.put("RUOLO_SESSIONE", getSessionRole(req));
			templateData.put("DIPENDENTE_ASSOCIAZIONI",req.getField("ID_NECESSARIO"));
			//_applicationSrv.displayPage(page, templateData, res);
			_applicationSrv.displayPage(page, templateData, setPageDatasetParam(page, req, templateData), res);
			
		}

	}

	private void cancellaTestata(String codice, String dsNameTestata) throws AppCrash {

		PjDAO_base testataDAO = DbUtils.makeDAOFromDsName(dsNameTestata);
		testataDAO.setField(testataDAO.getUniqueIdentifier(), codice);
		testataDAO.retrieve();
		
		if (ciSonoDettagli(dsNameTestata)){
			//String taggancio = testataDAO.getField("ID_UTENTE");
			String taggancio = testataDAO.getField("TAGGANCIO");
			testataDAO.delete();
			cancellaTabelleDettaglio(taggancio, dsNameTestata);		
		} else {
			testataDAO.delete();
		}

		
	}

	private void cancellaTabelleDettaglio(String taggancio, String dsNameTestata) throws AppCrash {
	
		String elencoDataSet=Config.GetInstance().getProperty("DS." + dsNameTestata + ".ElencoDSDettagli");
		
		String[] arr = elencoDataSet.split("\\,");

		for (int i = 0; i < arr.length; i++) {
			String dsName=arr[i];
			cancellaDettagli(dsName,taggancio);
		}

	}
	private void cancellaDettagli(String dsName, String taggancio) throws AppCrash {
		DataSet_itf dataSet = null;
		DataSetFactory dsFactory = DataSetFactory.getInstance();

		try {
			dataSet = dsFactory.makeDataSet("", dsName);
			HashMap<String, String> queryParameter = new HashMap<String, String>();
			
				queryParameter.put("ID_CODICE", taggancio);
				queryParameter.put("DAGGANCIO", taggancio);
			
			dataSet.setParam(queryParameter);
			dataSet.open();

			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				if (dbRow != null) {

					PjDAO_base dettagliDAO=DbUtils.makeDAOFromDsName(dsName);
					//String uniqueIdentifierValue = (String) dbRow.getField(dettagliDAO.getUniqueIdentifier());
					dettagliDAO.setField(dettagliDAO.getUniqueIdentifier(), taggancio);
					dettagliDAO.retrieve();
					dettagliDAO.delete();

				}
			}
		} catch (AppCrash ac) {
			ac.logContext("FunctionCancellaTestataDettagli", "Errore nella cancellazione: dataset " + dsName);
			throw ac;
		} finally {

			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (Throwable t) {
					AppCrash ac = new AppCrash(t);
					ac.logContext("FunctionCancellaTestataDettagli", "Errore nella close del dataset " + dsName);
				}
			}
		}

	}
	
	protected boolean ciSonoDettagli(String datasetTestata) {
		String elencoDsDettagli=Config.GetInstance().getProperty("DS."+datasetTestata+".ElencoDSDettagli");
		if (elencoDsDettagli!=null && !elencoDsDettagli.equals("")) {
			return true;
		} else {
			return false;
		}

	}
		
}
