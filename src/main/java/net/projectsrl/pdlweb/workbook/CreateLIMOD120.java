package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;
import java.util.Map;

import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.pdf.CreateAcroFieldsPDF_base;
import project.misc.Utils;

public class CreateLIMOD120 extends CreateAcroFieldsPDF_base {
	
	private static final String PDF_TEMPLATE_NAME = "LIMOD120.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 3;

	public CreateLIMOD120(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }
	
	
	@Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String nomeSito = (String) map.get("RAGSOC");
        String dtRegistro = Utils.getStringDataOggi();
        nomeFileOutput = "LIMOD120-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
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
    
    @Override
    protected void setAcroField(Map<String, Object> map, String acroFieldName) {

        super.setAcroField(map, acroFieldName);
        
        if (acroFieldName.startsWith("FLG") && (Boolean) map.get(acroFieldName)==true) {
            setAcroFieldValue(acroFieldName, Constants_itf.ACROBAT_CHECKED);
        }
    }
    
    
}
