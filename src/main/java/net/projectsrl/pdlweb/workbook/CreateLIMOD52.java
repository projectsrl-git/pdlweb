package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;
import java.util.Map;

import net.projectsrl.pdf.CreateAcroFieldsPDF_base;
import project.misc.Utils;

public class CreateLIMOD52 extends CreateAcroFieldsPDF_base {
	
	private static final String PDF_TEMPLATE_NAME = "LIMOD52.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 3;

    public CreateLIMOD52(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String nomeSito = (String) map.get("RAGSOC");
        String dtRegistro = Utils.getStringDataOggi();
        nomeFileOutput = "LIMOD52-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
        nomeFileOutput = Normalizer.normalize(nomeFileOutput, Normalizer.Form.NFD);
        nomeFileOutput = nomeFileOutput.replaceAll("[^\\p{ASCII}]", "");
        nomeFileOutput = nomeFileOutput.replaceAll("[^a-zA-Z0-9.-]", "_");
        nomeFileOutput = getOutDirectory() + nomeFileOutput;
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
    
    
   /* printWrappedText(x, y, 50 , (String) getMap().get("RESPONSABILE_CENTRALE_DELEGATO"));
    if ((Boolean) getMap().get("FLG_IN_SPAZI_CONFINATI")){
    	printWrappedText(x, y, 80 , (String) getMap().get("DESCR_EQUIPMENT"));
    }*/

}
