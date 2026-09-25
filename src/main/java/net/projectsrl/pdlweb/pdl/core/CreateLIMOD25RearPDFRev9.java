
package net.projectsrl.pdlweb.pdl.core;

import java.text.Normalizer;
import java.util.Map;

import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.pdf.CreateAcroFieldsPDF_base;

public class CreateLIMOD25RearPDFRev9 extends CreateAcroFieldsPDF_base {
	
	private static final String PDF_TEMPLATE_NAME = "pdla4retro_rev10.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 3;

    public CreateLIMOD25RearPDFRev9(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }
    
    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

    	String nomeFileOutput;
        String nrPDL = (String) map.get("NR_PDL");
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "pdl-" + nrPDL + "-" + nomeSito.replace(" ", "_") + ".pdf";
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
        	setAcroFieldValue(acroFieldName, "On");
        }
        
        if (acroFieldName.equals("V9_SEZ2_ALTRO_MISURE_SICUREZZA") && !map.get(acroFieldName).toString().trim().isEmpty()) {
        	setAcroFieldValue("FLG_V9_SEZ2_ALTRO_MISURE_SICUREZZA", "On");
        }      
        
        
    }
}
