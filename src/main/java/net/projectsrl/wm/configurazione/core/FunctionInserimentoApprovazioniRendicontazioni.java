package net.projectsrl.wm.configurazione.core;


import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimentoMenu;
import net.projectsrl.wm.db.ApprovazioniRendicontazioniDAO;

/**
 * FunctionInserimentoApprovazioniRendicontazioni
 * 
 */
public class FunctionInserimentoApprovazioniRendicontazioni extends FunctionInserimentoMenu {

	private static final String PAGE = "inserimento_approvazionirendicontazioni";

	public FunctionInserimentoApprovazioniRendicontazioni() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetApprovazioniRendicontazioni");
	}

	public FunctionInserimentoApprovazioniRendicontazioni(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetApprovazioniRendicontazioni");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
		
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			return templateData;
		}
		return templateData;
	}
	
	
	@SuppressWarnings("unchecked")
	protected HashMap saveVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		gestioneApprovazioni(req);
		return templateData;
	}

	/**
     * Inserisce/Modifica le abilitazioni delle funzionalità per i vari ruoli
     * 
     * @param req
     * @throws AppCrash
     */
	private void gestioneApprovazioni( SsbServletRequest req) throws AppCrash {
			DataSet_itf dataSet = null;
			DataSetFactory dsFactory = DataSetFactory.getInstance();
	
			String dsName="DataSetApprovazioniRendicontazioni";

			try {
				dataSet = dsFactory.makeDataSet("", dsName);
				dataSet.open();
				
				// leggo tutte le righe del dataset ossia tutte le possibili competenze
				while (dataSet.hasMoreElements()) {
					Row_itf dbRow = (Row_itf) dataSet.nextElement();

					if (dbRow != null) {
						// leggo dalla riga della query codice completo di ogni singola competenza
						//
						String dbCODICE = (String) dbRow.getField("ID_GRUPPO");
						String azienda = req.getField("AZIENDA");

						// verifico se sono state fatte delle modifiche alla pagina rispetto 
						// alla situazione precedente letta da DB e creo un flag che sarà TRUE solo se
						// sarà stata fatta almeno una modifica
						
						String strApprovatore1Livello = req.getField(dbCODICE + "_FLAG_1");
						String dbApprovatore1Livello = (String) dbRow.getField(ApprovazioniRendicontazioniDAO.LIVELLO_1);
						if (!strApprovatore1Livello.equals(dbApprovatore1Livello)) {
							String sqlUpdate ="UPDATE APPROVAZIONI_RENDICONTAZIONI SET LIVELLO_1='"+strApprovatore1Livello+"' WHERE ID_GRUPPO ='"+dbCODICE+"' AND AZIENDA ='"+azienda+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						
						String strApprovatore2Livello = req.getField(dbCODICE + "_FLAG_2");
						String dbApprovatore2Livello = (String) dbRow.getField(ApprovazioniRendicontazioniDAO.LIVELLO_2);
						if (!strApprovatore2Livello.equals(dbApprovatore2Livello)) {
							String sqlUpdate ="UPDATE APPROVAZIONI_RENDICONTAZIONI SET LIVELLO_2='"+strApprovatore2Livello+"' WHERE ID_GRUPPO ='"+dbCODICE+"' AND AZIENDA ='"+azienda+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						
						String strApprovatore3Livello = req.getField(dbCODICE + "_FLAG_3");
						String dbApprovatore3Livello = (String) dbRow.getField(ApprovazioniRendicontazioniDAO.LIVELLO_3);
						if (!strApprovatore3Livello.equals(dbApprovatore3Livello)) {
							String sqlUpdate ="UPDATE APPROVAZIONI_RENDICONTAZIONI SET LIVELLO_3='"+strApprovatore3Livello+"' WHERE ID_GRUPPO ='"+dbCODICE+"' AND AZIENDA ='"+azienda+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						
						String strApprovatore4Livello = req.getField(dbCODICE + "_FLAG_4");
						String dbApprovatore4Livello = (String) dbRow.getField(ApprovazioniRendicontazioniDAO.LIVELLO_4);
						if (!strApprovatore4Livello.equals(dbApprovatore4Livello)) {
							String sqlUpdate ="UPDATE APPROVAZIONI_RENDICONTAZIONI SET LIVELLO_4='"+strApprovatore4Livello+"' WHERE ID_GRUPPO ='"+dbCODICE+"' AND AZIENDA ='"+azienda+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}
						
						String strApprovatore5Livello = req.getField(dbCODICE + "_FLAG_5");
						String dbApprovatore5Livello = (String) dbRow.getField(ApprovazioniRendicontazioniDAO.LIVELLO_5);
						if (!strApprovatore5Livello.equals(dbApprovatore5Livello)) {
							String sqlUpdate ="UPDATE APPROVAZIONI_RENDICONTAZIONI SET LIVELLO_5='"+strApprovatore5Livello+"' WHERE ID_GRUPPO ='"+dbCODICE+"' AND AZIENDA ='"+azienda+"'";
							net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
						}

					}
				}
			} catch (Throwable th) {
				AppCrash ac=new AppCrash(th);
				ac.logContext("FunctionInserimentoApprovazioniRendicontazioni", "Errore nella ricerca campi del dataset " + dsName);
				throw ac;
			} finally {
				// chiude il dataset per il conteggio degli elementi trovati
				if (dataSet != null) {
					try {
						dataSet.close();
					} catch (Throwable t) {
						AppCrash ac = new AppCrash(t);
						ac.logContext("FunctionInserimentoApprovazioniRendicontazioni", "Errore nella close del dataset " + dsName);
					}
				}
			}
		}
}

