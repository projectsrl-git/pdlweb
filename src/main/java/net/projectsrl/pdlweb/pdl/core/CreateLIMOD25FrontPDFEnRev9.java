
package net.projectsrl.pdlweb.pdl.core;

import java.text.Normalizer;
import java.util.Map;

import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.pdf.CreateAcroFieldsPDF_base;
import project.misc.Utils;

public class CreateLIMOD25FrontPDFEnRev9 extends CreateAcroFieldsPDF_base {
	
	private static final String PDF_TEMPLATE_NAME = "pdla4fronte_en_rev10.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 3;

    public CreateLIMOD25FrontPDFEnRev9(String outDirectory, Map<String, Object> map) {

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
        
        if (acroFieldName.equals("DESCRIZIONE_LAVORO")){
        	String equipment= (String) map.get("DESCR_EQUIPMENT");
        	String odl= (String) map.get("ODL");
        	String lavoro= (String) map.get("DESCRIZIONE_LAVORO");
        	
        	if (equipment!=null){
        		if(!equipment.isEmpty()){
        			equipment="EQUIPMENT: "+equipment+" \r";
        		}
        	}else{
        		equipment="";
        	}
        	
    		if (odl!=null){
    			if (!odl.isEmpty()){
    				odl= "ODL/Invest.: "+odl+" \r";
    			}
        	}else{
        		odl="";
        	}
    		
    		lavoro=equipment+odl+lavoro;
    		setAcroFieldValue("DESCRIZIONE_DEL_LAVORO_COMPLETA", lavoro);
        }

        if (acroFieldName.equals("DESCR_EQUIPMENT") && !map.get(acroFieldName).toString().trim().isEmpty()) {
            setAcroFieldValue(acroFieldName, "EQUIPMENT: "+(String) map.get(acroFieldName));
        }
        
        if (acroFieldName.equals("ODL") && !map.get(acroFieldName).toString().trim().isEmpty()) {
            setAcroFieldValue(acroFieldName, "ODL/Invest.: "+(String) map.get(acroFieldName));
        }
        
        if (map.get("DATA_OGGI").equals("S")){
        	setAcroFieldValue("DATA_OGGI", Utils.getStringDataOggi());
        }else{
        	setAcroFieldValue("DATA_OGGI", "");
        }
        
        
        //getNomeCognomeFromIdUtente
        
        
        
    }
}
