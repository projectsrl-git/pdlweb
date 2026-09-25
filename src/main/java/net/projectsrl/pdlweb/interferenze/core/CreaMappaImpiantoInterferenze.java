
package net.projectsrl.pdlweb.interferenze.core;

import java.util.HashMap;

import com.itextpdf.text.pdf.PdfContentByte;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.projectsrl.pdlweb.anagrafiche.db.AreaLavoroDAO;

public class CreaMappaImpiantoInterferenze extends CreaMappaImpianto_base {

	private static final String DATASET_INTERFERENZE = "DSInterferenzePDL";

	public CreaMappaImpiantoInterferenze(Integer idImpianto) {

		super(idImpianto);
	}

	@Override
	protected void riempiMappa(PdfContentByte cb, String turno) throws AppCrash {

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
		

		coloraArea(cb, rectX, rectY, rectW, rectH, idArea, idImpianto, testoX, testoY, aCapo, nrPDL, impresa, testo);

		//scriviTestoInArea(cb, rectX, rectY, testoX, testoY, rectW, rectH, idArea, idImpianto, aCapo, nrPDL, impresa, testo);

	}

	private void coloraArea(PdfContentByte cb, int rectX, int rectY, int rectW, int rectH, Integer idArea,
			String idImpianto, int testoX, int testoY, int aCapo, String nrPDL, String impresa, String testo) throws AppCrash {
		
		String colore = "";
		int yPosition = 0;
		

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
					yPosition = 0;

				} 
				
				
				if (dbRow.getField(AreaLavoroDAO.TESTO_X) != null && dbRow.getField(AreaLavoroDAO.TESTO_Y) != null
						&& dbRow.getField(AreaLavoroDAO.A_CAPO) != null) {

					testoX = (Integer) dbRow.getField(AreaLavoroDAO.TESTO_X);
					testoY = (Integer) dbRow.getField(AreaLavoroDAO.TESTO_Y);
					aCapo = (Integer) dbRow.getField(AreaLavoroDAO.A_CAPO);

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

	private void scriviTestoInArea(PdfContentByte cb, int rectX, int rectY, int testoX, int testoY, int rectW,
			int rectH, Integer idArea, String idImpianto, int aCapo, String nrPDL, String impresa, String testo)
			throws AppCrash {

		int yPosition = 0;
		
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

					if (dbRow.getField(AreaLavoroDAO.RECT_X) != null && dbRow.getField(AreaLavoroDAO.RECT_X) != null
							&& dbRow.getField(AreaLavoroDAO.RECT_W) != null
							&& dbRow.getField(AreaLavoroDAO.RECT_H) != null) {

						idArea = (Integer) dbRow.getField(AreaLavoroDAO.ID_AREA);
						yPosition = 0;
						rectX = (Integer) dbRow.getField(AreaLavoroDAO.RECT_X);

						rectY = (Integer) dbRow.getField(AreaLavoroDAO.RECT_Y);

						rectW = (Integer) dbRow.getField(AreaLavoroDAO.RECT_W);

						rectH = (Integer) dbRow.getField(AreaLavoroDAO.RECT_H);

						flTuttoImpianto = (boolean) dbRow.getField("FL_TUTTO_IMPIANTO");
					}
					yPosition = 0;
				}

				if (dbRow.getField(AreaLavoroDAO.TESTO_X) != null && dbRow.getField(AreaLavoroDAO.TESTO_Y) != null
						&& dbRow.getField(AreaLavoroDAO.A_CAPO) != null) {

					testoX = (Integer) dbRow.getField(AreaLavoroDAO.TESTO_X);
					testoY = (Integer) dbRow.getField(AreaLavoroDAO.TESTO_Y);
					aCapo = (Integer) dbRow.getField(AreaLavoroDAO.A_CAPO);

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

}
