
package net.projectsrl.pdlweb.interferenze.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.pdf.PdfContentByte;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.projectsrl.pdlweb.anagrafiche.db.AreaLavoroDAO;
import net.projectsrl.wm.utils.Utils;

public class CreaPDFImpiantoInterferenze extends CreaMappaImpiantoInterferenze {

	public static final String PRINT_SUFFIX = "-stampa";
	private static final String DATASET_INTERFERENZE = "DSInterferenzePDL";

	public CreaPDFImpiantoInterferenze(Integer idImpianto) {

		super(idImpianto);
	}

	@Override
	protected String getSourceFilenameNoExtension() throws AppCrash {

		return super.getSourceFilenameNoExtension().concat(PRINT_SUFFIX);
	}

	@Override
	protected String extractImageFromPDF(String applicationRoot, String fileNamePlanimetria, String pdfTempFullPath)
			throws AppCrash {

		return pdfTempFullPath;
	}

	@Override
	protected void riempiMappa(PdfContentByte cb, String turno) throws AppCrash {

		try {

			int rectX = 0;
			int rectY = 0;
			int rectW = 0;
			int rectH = 0;

			int testoX = 0;
			int testoY = 0;
			int aCapo = 0;

			String nrPDL = "";
			String impresa = "";
			String testo = "";
			Integer idArea = null;
			String idImpianto = getIdImpianto().toString();
			if (!turno.isEmpty()){
				turno=turno.replace("00", "");
			}
			
			String dataOggi= Utils.getStringDataOggi();

			//coloraArea(cb, rectX, rectY, rectW, rectH, idArea, idImpianto);		nella stampa non serve colorare le aree

			scriviTestoInArea(cb, rectX, rectY, testoX, testoY, rectW, rectH, idArea, idImpianto, aCapo, nrPDL, impresa,
					testo);
			
			int yPosition = 0;
			if (idImpianto.equals("106")){
				writePdfTextTurno(cb, yPosition, 1095, 90, aCapo, dataOggi, 10);
				writePdfTextTurno(cb, yPosition, 1105, 70, aCapo, turno, 10);
			}
			if (idImpianto.equals("107")){
				writePdfTextTurno(cb, yPosition, 770, 46, aCapo, dataOggi, 6);
				writePdfTextTurno(cb, yPosition, 770, 36, aCapo, turno, 6);
			}
			
			

		} catch (Throwable th) {
			AppCrash ac = new AppCrash(th);
			throw ac;
		}

	}

	private void coloraArea(PdfContentByte cb, int rectX, int rectY, int rectW, int rectH, Integer idArea,
			String idImpianto) throws AppCrash {
		String colore = "";

		List<String> impreseArea = new ArrayList<String>();

		DataSet_itf dataSet = null;
		try {
			dataSet = DataSetFactory.getInstance().makeDataSet("", DATASET_INTERFERENZE);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("ID_IMPIANTO", idImpianto);
			dataSet.setParam(params);
			dataSet.open();

			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				if (dbRow.getField(AreaLavoroDAO.ID_AREA) != null
						&& !((Integer) dbRow.getField(AreaLavoroDAO.ID_AREA)).equals(idArea)) {

					if (idArea == null) {
						impreseArea.add((String) dbRow.getField("NOME_IMPRESA"));
					} else {
						impreseArea.clear();
						impreseArea.add((String) dbRow.getField("NOME_IMPRESA"));
					}

					if (dbRow.getField(AreaLavoroDAO.RECT_X) != null && dbRow.getField(AreaLavoroDAO.RECT_X) != null
							&& dbRow.getField(AreaLavoroDAO.RECT_W) != null
							&& dbRow.getField(AreaLavoroDAO.RECT_H) != null) {

						idArea = (Integer) dbRow.getField(AreaLavoroDAO.ID_AREA);

						rectX = (Integer) dbRow.getField(AreaLavoroDAO.RECT_X);

						rectY = (Integer) dbRow.getField(AreaLavoroDAO.RECT_Y);

						rectW = (Integer) dbRow.getField(AreaLavoroDAO.RECT_W);

						rectH = (Integer) dbRow.getField(AreaLavoroDAO.RECT_H);

						colore = (String) dbRow.getField("COLORE");

						if (colore != null) {
							if (!colore.isEmpty()) {
								coloraArea(cb, rectX, rectY, rectW, rectH, colore);
							}
						}

					}

				} else {
					// aggiunta luca 13-02-2019
					colore = (String) dbRow.getField("COLORE");

					impreseArea.add((String) dbRow.getField("NOME_IMPRESA"));

					if (colore != null) {
						if (!colore.isEmpty()) {
							coloraArea(cb, rectX, rectY, rectW, rectH, colore);
						}
					}
				}

			}
		} catch (AppCrash ac) {
			ac.logContext(this.getClass().getName(),
					"Errore nella ricerca dell'ultimo progressivo del dataset " + "DSInterferenzePDL");
			try {
				throw ac;
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			// chiude il dataset per il conteggio degli elementi trovati
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (Throwable t) {
					AppCrash ac = new AppCrash(t);
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset " + "DSInterferenzePDL");
				}
			}
		}
	}

	private void scriviTestoInArea(PdfContentByte cb, int rectX, int rectY, int testoX, int testoY, int rectW,
			int rectH, Integer idArea, String idImpianto, int aCapo, String nrPDL, String impresa, String testo)
			throws AppCrash {
		int yPosition = 0;
		String colore;
		boolean flTuttoImpianto;
		DataSet_itf dataSet = null;
		try {
			dataSet = DataSetFactory.getInstance().makeDataSet("", DATASET_INTERFERENZE);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("ID_IMPIANTO", idImpianto);
			dataSet.setParam(params);
			dataSet.open();

			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				if (dbRow.getField(AreaLavoroDAO.ID_AREA) != null
						&& !((Integer) dbRow.getField(AreaLavoroDAO.ID_AREA)).equals(idArea)) {

					if (dbRow.getField(AreaLavoroDAO.RECT_X_STAMPA) != null
							&& dbRow.getField(AreaLavoroDAO.RECT_X_STAMPA) != null
							&& dbRow.getField(AreaLavoroDAO.RECT_W_STAMPA) != null
							&& dbRow.getField(AreaLavoroDAO.RECT_H_STAMPA) != null) {

						idArea = (Integer) dbRow.getField(AreaLavoroDAO.ID_AREA);

						yPosition = 0;
						rectX = (Integer) dbRow.getField(AreaLavoroDAO.RECT_X_STAMPA);

						rectY = (Integer) dbRow.getField(AreaLavoroDAO.RECT_Y_STAMPA);

						rectW = (Integer) dbRow.getField(AreaLavoroDAO.RECT_W_STAMPA);

						rectH = (Integer) dbRow.getField(AreaLavoroDAO.RECT_H_STAMPA);

						flTuttoImpianto = (boolean) dbRow.getField("FL_TUTTO_IMPIANTO");
					}
					yPosition = 0;
				}

				if (dbRow.getField(AreaLavoroDAO.TESTO_X_STAMPA) != null
						&& dbRow.getField(AreaLavoroDAO.TESTO_Y_STAMPA) != null
						&& dbRow.getField(AreaLavoroDAO.A_CAPO_STAMPA) != null) {

					testoX = (Integer) dbRow.getField(AreaLavoroDAO.TESTO_X_STAMPA);
					testoY = (Integer) dbRow.getField(AreaLavoroDAO.TESTO_Y_STAMPA);
					aCapo = (Integer) dbRow.getField(AreaLavoroDAO.A_CAPO_STAMPA);

					if (testoX == 0) {
						testoX = rectX + 5;
					}

					if (testoY == 0) {
						testoY = rectY + rectH - 10;
					}

					if (aCapo == 0) {
						aCapo = 8;
					}

					nrPDL = dbRow.getField("NR_PDL").toString();
					impresa = dbRow.getField("NOME_IMPRESA").toString();
					testo = nrPDL + " " + impresa;

					writePdfText(cb, yPosition, testoX, testoY, aCapo, testo);
					yPosition = yPosition + 1;

				}

			}
		} catch (AppCrash ac) {
			ac.logContext(this.getClass().getName(),
					"Errore nella ricerca dell'ultimo progressivo del dataset " + "DSInterferenzePDL");
			try {
				throw ac;
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			// chiude il dataset per il conteggio degli elementi trovati
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (Throwable t) {
					AppCrash ac = new AppCrash(t);
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset " + "DSInterferenzePDL");
				}
			}
		}
	}

	@Override
	protected void coloraArea(PdfContentByte cb, int rectX, int rectY, int rectW, int rectH, String colore)
			throws AppCrash {

		// BaseColor myColor = WebColors.getRGBColor("#ffffff");

		// cb.saveState();
		// cb.setColorStroke(myColor);
		// cb.setColorFill(myColor);
		// cb.rectangle(rectX, rectY, rectW, rectH);
		// cb.fill();
		// cb.restoreState();

		return;

	}

}
