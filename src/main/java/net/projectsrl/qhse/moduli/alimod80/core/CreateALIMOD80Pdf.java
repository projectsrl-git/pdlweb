
package net.projectsrl.qhse.moduli.alimod80.core;

import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.qhse.moduli.alimod80.db.AliMod80DAO;
import net.projectsrl.qhse.moduli.core.CreateALIMODPdf_base;

public class CreateALIMOD80Pdf extends CreateALIMODPdf_base {

    public CreateALIMOD80Pdf(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);

        setFixedNumberOfPages(2);
        setFixedPagesTemplateArray(new String[] { "ALI-MOD-080-pag1.pdf", "ALI-MOD-080-pag2.pdf" });

    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String nrModulo = (String) map.get("NR_MODULO");
        String nomeSito = (String) map.get("DESCR_SITO");
        nomeFileOutput = "alimod80-" + nrModulo + "-" + nomeSito.replace(" ", "_") + ".pdf";
        return nomeFileOutput;
    }

    @Override
    protected void setAcroField(Map<String, Object> map, String acroFieldName) {

        if (AliMod80DAO.TIPO_COMUNICAZIONE.equals(acroFieldName)) {
            String acroFieldValue = (String) map.get(acroFieldName);

            String values[] = ((String) acroFieldValue).split(";");
            for (int i = 0; i < values.length; i++) {
                setAcroFieldValue(values[i], Constants_itf.ACROBAT_CHECKED);
            }

        } else if (AliMod80DAO.AZIONI_EVENTO.equals(acroFieldName)) {
            String acroFieldValue = (String) map.get(acroFieldName);

            String values[] = ((String) acroFieldValue).split(";");
            for (int i = 0; i < values.length; i++) {
                try {
                    setAcroFieldValue(acroFieldName + "." + i, getStringaAzione(values[i]));
                } catch (AppCrash e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            super.setAcroField(map, acroFieldName);
        }

    }

    private String getStringaAzione(String codice) throws AppCrash {

        String dati = "";
        DataSet_itf dataSet = null;
        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DSDescrizioneParametro");
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("CODICE", codice);
            dataSet.setParam(params);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                dati = dbRow.getField("DESCRIZIONE").toString().trim();
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

}
