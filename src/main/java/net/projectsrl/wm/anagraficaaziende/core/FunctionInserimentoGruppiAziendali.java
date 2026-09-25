package net.projectsrl.wm.anagraficaaziende.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.wm.db.AziendeGruppiDAO;

/**
 * FunctionInserimentoUtenti
 * 
 */
public class FunctionInserimentoGruppiAziendali extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_gruppi_aziendali";

	public FunctionInserimentoGruppiAziendali() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetAziendeGruppi");
	}

	public FunctionInserimentoGruppiAziendali(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetAziendeGruppi");
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
		gestioneGruppiAziendali(req);
		return templateData;
	}

	/**
     * Inserisce/Modifica le abilitazioni delle funzionalità per i vari ruoli
     * 
     * @param req
     * @throws AppCrash
     */
	private void gestioneGruppiAziendali( SsbServletRequest req) throws AppCrash {
			DataSet_itf dataSet = null;
			DataSetFactory dsFactory = DataSetFactory.getInstance();
	
			String dsName="DataSetAziendeGruppi";

			try {
				dataSet = dsFactory.makeDataSet("", dsName);
				dataSet.open();
				
				// leggo tutte le righe del dataset ossia tutte le possibili competenze
				while (dataSet.hasMoreElements()) {
					Row_itf dbRow = (Row_itf) dataSet.nextElement();

					if (dbRow != null) {
						// leggo dalla riga della query codice completo di ogni singola competenza
						//
						String dbCODICE = (String) dbRow.getField("CODICE");
						
						// istanzio un nuovo aziendeDAO per modificare o inserire le competenze
						// di questo cv
						AziendeGruppiDAO aziendeGruppiDAO = new AziendeGruppiDAO();
						
						// verifico se sono state fatte delle modifiche alla pagina rispetto 
						// alla situazione precedente letta da DB e creo un flag che sarà TRUE solo se
						// sarà stata fatta almeno una modifica
						
						boolean flagIsModified=false;
						
						String strGRUPPI = req.getField(dbCODICE + "_FLAG_");
						String dbGRUPPI = (String) dbRow.getField(AziendeGruppiDAO.GRUPPI);
						if (!strGRUPPI.equals(dbGRUPPI)) {
							flagIsModified=true;
							aziendeGruppiDAO.setField(AziendeGruppiDAO.GRUPPI, strGRUPPI);
						}

						// se ho fatto qualche modifica rispetto alla precedente situazione allora 
						// o modifico la riga
						if (flagIsModified) {
							aziendeGruppiDAO.setField(AziendeGruppiDAO.CODICE, dbCODICE);
							aziendeGruppiDAO.update();
						}

					}
				}
			} catch (Throwable th) {
				AppCrash ac=new AppCrash(th);
				ac.logContext("FunctionInserimentoGruppiAziendali", "Errore nella ricerca campi del dataset " + dsName);
				throw ac;
			} finally {
				// chiude il dataset per il conteggio degli elementi trovati
				if (dataSet != null) {
					try {
						dataSet.close();
					} catch (Throwable t) {
						AppCrash ac = new AppCrash(t);
						ac.logContext("FunctionInserimentoGruppiAziendali", "Errore nella close del dataset " + dsName);
					}
				}
			}
		}
}

