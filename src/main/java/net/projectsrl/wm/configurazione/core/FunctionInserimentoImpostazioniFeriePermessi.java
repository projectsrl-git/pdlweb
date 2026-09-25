package net.projectsrl.wm.configurazione.core;


import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimentoMenu;
import net.projectsrl.wm.db.ImpostazioniFeriePermessiDAO;

/**
 * FunctionInserimentoImpostazioniFeriePermessi
 * 
 */
public class FunctionInserimentoImpostazioniFeriePermessi extends FunctionInserimentoMenu {

	private static final String PAGE = "inserimento_impostazioniferiepermessi";

	public FunctionInserimentoImpostazioniFeriePermessi() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetImpostazioniFeriePermessi");
	}

	public FunctionInserimentoImpostazioniFeriePermessi(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetImpostazioniFeriePermessi");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
		String aziendaInserimento=req.getField("AZIENDA");
		if (aziendaInserimento.equals("")){
			aziendaInserimento=(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
		}
		String sqlInsert ="INSERT INTO IMPOSTAZIONI_FERIEPERMESSI (ID_UNIVOCO,AZIENDA,ID_DIPENDENTE,APPROVAZIONE) SELECT ID_DIPENDENTE,AZIENDA,ID_DIPENDENTE,'C' FROM DIPENDENTI WHERE AZIENDA = '"+aziendaInserimento+"' AND ID_DIPENDENTE NOT IN (SELECT ID_DIPENDENTE FROM IMPOSTAZIONI_FERIEPERMESSI)";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsert);
		
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
	
			String dsName="DataSetImpostazioniFeriePermessi";
			
			
			

			try {
				dataSet = dsFactory.makeDataSet("", dsName);
				dataSet.open();
				
				// leggo tutte le righe del dataset ossia tutte le possibili competenze
				while (dataSet.hasMoreElements()) {
					Row_itf dbRow = (Row_itf) dataSet.nextElement();

					if (dbRow != null) {
						// leggo dalla riga della query codice completo di ogni singola competenza
						//
						String dbCODICE = (String) dbRow.getField("ID_UNIVOCO");
						String dbORDINE = (String) dbRow.getField("APPROVAZIONE");
						
						// istanzio un nuovo ruoliDAO per modificare o inserire le competenze
						// di questo cv
						ImpostazioniFeriePermessiDAO impostazioniferiepermessiDAO = new ImpostazioniFeriePermessiDAO();
						
						// verifico se sono state fatte delle modifiche alla pagina rispetto 
						// alla situazione precedente letta da DB e creo un flag che sarà TRUE solo se
						// sarà stata fatta almeno una modifica
						
						boolean flagIsModified=false;
						
						String strAPPROVAZIONE = req.getField(dbCODICE);
						String dbAPPROVAZIONE = (String) dbRow.getField(ImpostazioniFeriePermessiDAO.APPROVAZIONE);
						if (!strAPPROVAZIONE.equals(dbAPPROVAZIONE)) {
							flagIsModified=true;
							impostazioniferiepermessiDAO.setField(impostazioniferiepermessiDAO.APPROVAZIONE, strAPPROVAZIONE);
						}

						// se ho fatto qualche modifica rispetto alla precedente situazione allora 
						// o modifico la riga
						if (flagIsModified) {
							impostazioniferiepermessiDAO.setField(ImpostazioniFeriePermessiDAO.ID_UNIVOCO, dbCODICE);
							impostazioniferiepermessiDAO.update();
						}

					}
				}
			} catch (Throwable th) {
				AppCrash ac=new AppCrash(th);
				ac.logContext("FunctionInserimentoImpostazioniFeriePermessi", "Errore nella ricerca campi del dataset " + dsName);
				throw ac;
			} finally {
				// chiude il dataset per il conteggio degli elementi trovati
				if (dataSet != null) {
					try {
						dataSet.close();
					} catch (Throwable t) {
						AppCrash ac = new AppCrash(t);
						ac.logContext("FunctionInserimentoImpostazioniFeriePermessivv", "Errore nella close del dataset " + dsName);
					}
				}
			}
		}
}

