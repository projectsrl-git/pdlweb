
package net.projectsrl.qhse.moduli.alimod67.core;

import java.util.Map;

import net.projectsrl.qhse.moduli.core.CreateALIMODPdf_base;

public class CreateALIMOD67Pdf extends CreateALIMODPdf_base {

    private static final String PDF_TEMPLATE_NAME = "ALI-MOD-067.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 3;

    public CreateALIMOD67Pdf(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String nrModulo = (String) map.get("NR_MODULO");
        String nomeSito = (String) map.get("DESCR_SITO");
        nomeFileOutput = "alimod67-" + nrModulo + "-" + nomeSito.replace(" ", "_") + ".pdf";
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

}
