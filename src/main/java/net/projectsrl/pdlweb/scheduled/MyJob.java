package net.projectsrl.pdlweb.scheduled;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.pdlweb.interferenze.core.CreaPDFImpiantoInterferenze;
import net.projectsrl.pdlweb.interferenze.db.StoricoInterferenzeDAO;
import net.projectsrl.webapp.core.WebAppConstants_itf;

public class MyJob implements Job {

	public static final String OUTPUT_PATH = WebAppConstants_itf.OUTPUT_PATH;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("My Logic");

		Integer idImpianto;
		String codImpianto;
		Integer idAzienda;
		Integer idUtente;
		String codiceTurno;
		String oraFineTurno;
		String dataFineTurno;
		String ora = getStringOra();
		String oraFineTurnoQuery = "";

		String cartellaPDF = Config.GetInstance().getProperty("cartella.storicointerferenze.pdf",
				"cartella.storicointerferenze.pdf");

		if (ora.equals("13-58")) {
			oraFineTurnoQuery = "14:00";
		}
		if (ora.equals("21-58")) {
			oraFineTurnoQuery = "22:00";
		}
		if (ora.equals("05-58")) {
			oraFineTurnoQuery = "06:00";
		}
		if (ora.equals("17-58")) {
			oraFineTurnoQuery = "18:00";
		}

		if (!oraFineTurnoQuery.isEmpty()) {

			DataSet_itf dataSet = null;
			try {

				DataSetFactory dsFactory = DataSetFactory.getInstance();

				dsFactory = DataSetFactory.getInstance();
				dataSet = dsFactory.makeDataSet("", "DataSetStoricoInterferenze");

				HashMap<String, String> params = new HashMap<String, String>();
				params.put("ORA_FINE_TURNO", oraFineTurnoQuery);
				dataSet.setParam(params);
				dataSet.open();

				while (dataSet.hasMoreElements()) {
					Row_itf dbRow = (Row_itf) dataSet.nextElement();

					idImpianto = (Integer) dbRow.getField("ID_IMPIANTO");
					codImpianto = (String) dbRow.getField("CODICE_IMPIANTO");
					idAzienda = (Integer) dbRow.getField("ID_AZIENDA");
					codiceTurno = (String) dbRow.getField("CODICE_TURNO");
					oraFineTurno = (String) dbRow.getField("ORA_FINE_TURNO");
					dataFineTurno = (String) dbRow.getField("DATA_FINE_TURNO");
					idUtente = (Integer) dbRow.getField("ID_UTENTE");

					// stampa - inizio
					String outputFileName = null;
					try {

						String turnoSelezionato = codiceTurno;

						CreaPDFImpiantoInterferenze creaMappa = new CreaPDFImpiantoInterferenze(
								new Integer(idImpianto));
						// String fileNamePlanimetria =
						// creaMappa.creaMappaArea(_applicationSrv.getRoot());
						String fileNamePlanimetria = creaMappa.creaMappaArea(cartellaPDF,turnoSelezionato);

						ora = getStringOra();

						outputFileName = fileNamePlanimetria.replace(CreaPDFImpiantoInterferenze.IMG_PATH, OUTPUT_PATH)
								.replace("_temp", "")
								.replace(CreaPDFImpiantoInterferenze.PRINT_SUFFIX, "_" + getStringDataOggiTrattino()
										+ "_" + ora + "_" + turnoSelezionato + "_" + codImpianto);

						File tempPDFFile = new File(fileNamePlanimetria);
						tempPDFFile.renameTo(new File(outputFileName));
						Files.deleteIfExists(tempPDFFile.toPath());

					} catch (Throwable e) {
						throw new AppCrash(e);
					}

					// stampa - fine

					StoricoInterferenzeDAO si = new StoricoInterferenzeDAO();
					si.setAttribute(StoricoInterferenzeDAO.ID_IMPIANTO, idImpianto);
					si.setAttribute(StoricoInterferenzeDAO.ID_AZIENDA, idAzienda);
					si.setAttribute(StoricoInterferenzeDAO.CODICE_TURNO, codiceTurno);
					si.setAttribute(StoricoInterferenzeDAO.NOME_FILE_COMPLETO, outputFileName);
					si.setAttribute(StoricoInterferenzeDAO.NOME_FILE,outputFileName.substring(outputFileName.lastIndexOf("/") + 1));
					si.setAttribute(StoricoInterferenzeDAO.ORA_STAMPA, ora.replace("-", ":"));
					si.setAttribute(StoricoInterferenzeDAO.DATA_STAMPA, dataFineTurno);
					si.setAttribute(StoricoInterferenzeDAO.ID_UTENTE, idUtente);
					si.insert();

				}

				dataSet.close();

			} catch (Throwable t) {
				AppCrash ac = new AppCrash(t);
				ac.logContext(this.getClass().getName(), "errore nell'esecuzione del Job");
			} finally {
				if (dataSet != null) {
					try {
						dataSet.close();
					} catch (AppCrash ac) {
						ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
					}
				}
			}
		}
	}

	private static String getStringDataOggiTrattino() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
		return sdf.format(new Date());
	}

	private static String getStringOra() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH-mm", Locale.ITALY);
		return sdf.format(new Date());
	}

}
