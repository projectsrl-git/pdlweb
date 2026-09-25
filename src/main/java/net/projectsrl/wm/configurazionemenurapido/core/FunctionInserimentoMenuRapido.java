package net.projectsrl.wm.configurazionemenurapido.core;


import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimentoMenu;
import net.projectsrl.wm.db.RuoliDAO;

/**
 * FunctionInserimentoMenuRapido
 * 
 */
public class FunctionInserimentoMenuRapido extends FunctionInserimentoMenu {

	private static final String PAGE = "inserimento_menurapido";

	public FunctionInserimentoMenuRapido() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetAbilitazioni");
	}

	public FunctionInserimentoMenuRapido(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetAbilitazioni");
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
		gestioneAbilitazioni(req);
		return templateData;
	}

	/**
     * Inserisce/Modifica le abilitazioni delle funzionalità per i vari ruoli
     * 
     * @param req
     * @throws AppCrash
     */
	private void gestioneAbilitazioni( SsbServletRequest req) throws AppCrash {
			DataSet_itf dataSet = null;
			DataSetFactory dsFactory = DataSetFactory.getInstance();
	
			String dsName="DataSetAbilitazioni";

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
						String dbORDINE = (String) dbRow.getField("ORDINE");
						
						// istanzio un nuovo ruoliDAO per modificare o inserire le competenze
						// di questo cv
						RuoliDAO ruoliDAO = new RuoliDAO();
						
						// verifico se sono state fatte delle modifiche alla pagina rispetto 
						// alla situazione precedente letta da DB e creo un flag che sarà TRUE solo se
						// sarà stata fatta almeno una modifica
						
						boolean flagIsModified=false;
						
						String strUTENTI = req.getField(dbCODICE + "_FLAG_OK_" + dbORDINE);
						if(!strUTENTI.equals("")){
							strUTENTI=strUTENTI+";";
						}
						String dbUTENTI = (String) dbRow.getField(RuoliDAO.UTENTE);
						if (!strUTENTI.equals(dbUTENTI)) {
							flagIsModified=true;
							ruoliDAO.setField(RuoliDAO.UTENTE, strUTENTI);
						}

						// se ho fatto qualche modifica rispetto alla precedente situazione allora 
						// o modifico la riga
						if (flagIsModified) {
							ruoliDAO.setField(RuoliDAO.CODICE, dbCODICE);
						    ruoliDAO.update();
						}

					}
				}
			} catch (Throwable th) {
				AppCrash ac=new AppCrash(th);
				ac.logContext("FunctionInserimentoMenuRapido", "Errore nella ricerca campi del dataset " + dsName);
				throw ac;
			} finally {
				// chiude il dataset per il conteggio degli elementi trovati
				if (dataSet != null) {
					try {
						dataSet.close();
					} catch (Throwable t) {
						AppCrash ac = new AppCrash(t);
						ac.logContext("FunctionInserimentoMenuRapido", "Errore nella close del dataset " + dsName);
					}
				}
			}
		}
}

