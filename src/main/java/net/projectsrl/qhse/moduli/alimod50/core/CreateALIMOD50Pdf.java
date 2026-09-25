
package net.projectsrl.qhse.moduli.alimod50.core;

import java.util.Map;

import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.qhse.moduli.alimod50.db.AliMod50DettagliDAO;
import net.projectsrl.qhse.moduli.core.CreateALIMODPdf_base;

public class CreateALIMOD50Pdf extends CreateALIMODPdf_base {

    private static final String PDF_TEMPLATE_NAME = "ALI-MOD-050.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 7;

    public CreateALIMOD50Pdf(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String nrModulo = (String) map.get("NR_MODULO");
        String nomeSito = (String) map.get("DESCR_SITO");
        nomeFileOutput = "alimod50-" + nrModulo + "-" + nomeSito.replace(" ", "_") + ".pdf";

        return nomeFileOutput;
    }
    
    @Override
    public int getMaxRowsInPage() {

        return MAX_ROWS_IN_PAGE;
    }

    @Override
    protected String getTemplateName() {

        return PDF_TEMPLATE_NAME;
    }

    @Override
    protected void putIndexedFieldInMap(Map<String, Object> map, int i, String fieldName, Object fieldValue) {

        if (AliMod50DettagliDAO.FL_CONFORME.equals(fieldName)) {
            map.put(fieldValue + "." + i, Constants_itf.ACROBAT_CHECKED);
        } else {
            super.putIndexedFieldInMap(map, i, fieldName, fieldValue);
        }
    }
    
    
 
}
