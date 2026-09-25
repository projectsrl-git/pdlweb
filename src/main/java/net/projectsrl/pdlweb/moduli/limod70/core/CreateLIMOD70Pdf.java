
package net.projectsrl.pdlweb.moduli.limod70.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.pdlweb.moduli.limod70.db.LiMod70DAO;
import net.projectsrl.qhse.moduli.core.CreateALIMODPdf_base;
import project.misc.Utils;

public class CreateLIMOD70Pdf extends CreateALIMODPdf_base {

	private static final String PDF_TEMPLATE_NAME = "LIMOD70.pdf";
	private static final int MAX_ROWS_IN_PAGE = 30;

	public CreateLIMOD70Pdf(String outDirectory, Map<String, Object> map) {

		super(outDirectory, map);
	}

	@Override
	protected String setPDFOutFileName(Map<String, Object> map) {

		String nomeFileOutput;

		String nrModulo = (String) map.get("NR_MODULO");
		String nomeSito = (String) map.get("DESCR_SITO");
		String nomeImpianto = (String) map.get("DESCR_IMPIANTO");
		// nomeFileOutput = "alimod70-" + nrModulo + "-" + nomeSito.replace(" ",
		// "_") + ".pdf";
		nomeFileOutput = "LI-MOD70-" + nrModulo + "-" + Utils.getStringDataOggi() + "-" + Utils.getStringOra() + "-"
				+ nomeSito.replace(" ", "_") + "-" + nomeImpianto.replace(" ", "_") + ".pdf";

		try {
			LiMod70DAO limod70DAO = new LiMod70DAO();
			limod70DAO.setAttribute(LiMod70DAO.ID_MODULO, map.get("ID_MODULO"));
			limod70DAO.setAttribute(LiMod70DAO.NOME_FILE, nomeFileOutput);
			limod70DAO.update();

		} catch (AppCrash e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nomeFileOutput;
	}


	@Override
	protected void setAcroField(Map<String, Object> map, String acroFieldName) {

 		if (map.containsKey("DT_MODULO") || map.containsKey("DESCR_SITO")) {
			setAcroFieldValue("DT_MODULO",map.get("DT_MODULO").toString());
			setAcroFieldValue("DESCR_SITO",map.get("DESCR_SITO").toString());
		}

		if (LiMod70DAO.ID_AREA.equals(acroFieldName)) {
			try {
				getStringaDatiArea(map);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getStringaDatiArea(Map<String, Object> map) throws AppCrash {

		String dati = "";
		String idModulo = "";
		String idModuloSingolo = "";
		String ora="";
		String firma="";
		String valutazione="";
		String distanzaRispetto="";

		for (Entry<String, Object> e : map.entrySet()) {
			if (e.getKey().startsWith("ID_MODULO")) {
				// add to my result list
				idModulo = idModulo + "," + e.getValue().toString();
			}
		}

		idModulo = idModulo.replaceFirst(",", "");

		DataSet_itf dataSet = null;
		DataSet_itf dataSetDettaglio = null;
		try {

			DataSetFactory dsFactory = DataSetFactory.getInstance();
			DataSetFactory dsFactoryDettaglio = DataSetFactory.getInstance();

			dsFactory = DataSetFactory.getInstance();
			dsFactoryDettaglio = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", "DSLIMOD70");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("WHERECONDITION", "WHERE ID_MODULO IN (" + idModulo + ")");
			params.put("WHERECONDITION_AZIENDE", " IS NOT NULL ");
			dataSet.setParam(params);
			dataSet.open();
			int i = 0;

			while (dataSet.hasMoreElements()) {
				i++;
				Row_itf dbRow = (Row_itf) dataSet.nextElement();
				dati = dbRow.getField("DESCR_AREA").toString().trim();
				idModuloSingolo = dbRow.getField("ID_MODULO").toString().trim();
				ora = dbRow.getField("ORA").toString().trim();
				firma = dbRow.getField("FIRMA").toString().trim();
				
				setAcroFieldValue("DESCR_AREA" + "." + i, dati);
				setAcroFieldValue("ORA" + "." + i, ora);
				setAcroFieldValue("FIRMA" + "." + i, firma);
				
				if (dbRow.getField("VALUTAZIONE")!=null){
					valutazione = dbRow.getField("VALUTAZIONE").toString().trim();
					if (valutazione.equals("PRI")){
						setAcroFieldValue("PRI" + "." + i, Constants_itf.ACROBAT_CHECKED);
					}
					if (valutazione.equals("NUO")){
						setAcroFieldValue("NUO" + "." + i, Constants_itf.ACROBAT_CHECKED);
					}
				}
				if (dbRow.getField("DISTANZA_RISPETTO")!=null){
					distanzaRispetto = dbRow.getField("DISTANZA_RISPETTO").toString().trim();
					if (distanzaRispetto.equals("S")){
						setAcroFieldValue("S" + "." + i, Constants_itf.ACROBAT_CHECKED);
					}
					if (distanzaRispetto.equals("N")){
						setAcroFieldValue("N" + "." + i, Constants_itf.ACROBAT_CHECKED);
					}
				}
				
				


				dataSetDettaglio = dsFactoryDettaglio.makeDataSet("", "DSLIMOD70Dettaglio");
				params.put("WHERECONDITION", "WHERE ID_MODULO=" + idModuloSingolo);
				dataSetDettaglio.setParam(params);
				dataSetDettaglio.open();
				int y = 0;
				if (i > 1) {
					y = 2 * (i - 2) + (2 * i);
				}
				y = i + y - 1;
				
				String impresaTesto = "";
				String preposto="";
				String nrPDL = "";
				while (dataSetDettaglio.hasMoreElements()) {
					y++;
					dbRow = (Row_itf) dataSetDettaglio.nextElement();
					impresaTesto = dbRow.getField("IMPRESA_TESTO").toString().trim();
					preposto = dbRow.getField("NOME_COGNOME_PREPOSTO_IMPRESA").toString().trim();
					nrPDL = dbRow.getField("NR_PDL").toString().trim();
					
					if(impresaTesto.equals("PERSONALE INTERNO")){
						preposto = dbRow.getField("NOME_COGNOME_DELEGATO_LAVORI_AL").toString().trim();
					}
					
					setAcroFieldValue("IMPRESA_TESTO" + "." + y, impresaTesto);
					setAcroFieldValue("NR_PDL" + "." + y, nrPDL);
					setAcroFieldValue("NOME_COGNOME_PREPOSTO_IMPRESA" + "." + y, preposto);
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
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
				}
			}
		}

		return dati;
	}

	@Override
	public int getMaxRowsInPage() {

		return MAX_ROWS_IN_PAGE;
	}

	@Override
	protected String getTemplateName() {

		return PDF_TEMPLATE_NAME;
	}
}
