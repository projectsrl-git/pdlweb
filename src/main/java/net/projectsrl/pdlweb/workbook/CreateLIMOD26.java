package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;
import java.util.Map;

import net.projectsrl.pdf.CreateAcroFieldsPDF_base;
import project.misc.Utils;

public class CreateLIMOD26 extends CreateAcroFieldsPDF_base {
	
	private static final String PDF_TEMPLATE_NAME = "LIMOD26_rev2026.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 3;

	public CreateLIMOD26(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }
	
	
	@Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String nomeSito = (String) map.get("RAGSOC");
        String dtRegistro = Utils.getStringDataOggi();
        nomeFileOutput = "LIMOD26-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
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
    
    
    
    

    /*printWrappedText(x, y, 25, (String) getMap().get("RAGSOC"));
    printWrappedText(x, y, 75, (String) getMap().get("DESCR_EQUIPMENT"));
    printWrappedText(x, y, 25, (String) getMap().get("NR_PDL"));
    printWrappedText(x, y, 25, (String) getMap().get("DT_PDL"));*/
      
}
