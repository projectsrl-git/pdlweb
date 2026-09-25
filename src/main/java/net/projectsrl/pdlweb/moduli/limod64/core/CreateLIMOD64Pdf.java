
package net.projectsrl.pdlweb.moduli.limod64.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.pdlweb.moduli.limod64.db.LiMod64DAO;
import net.projectsrl.qhse.moduli.core.CreateALIMODPdf_base;
import project.misc.Utils;

public class CreateLIMOD64Pdf extends CreateALIMODPdf_base {

    private static final String PDF_TEMPLATE_NAME = "LIMOD64.pdf";
    private static final int    MAX_ROWS_IN_PAGE  = 6;
    
    public CreateLIMOD64Pdf(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }
    
    @Override
    protected void setAcroField(Map<String, Object> map, String acroFieldName) {

        super.setAcroField(map, acroFieldName);
        
        if (acroFieldName.startsWith("FLG") && (Boolean) map.get(acroFieldName)==true) {
            setAcroFieldValue(acroFieldName, Constants_itf.ACROBAT_CHECKED);
        }
        
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        
        String nrModulo = (String) map.get("NR_MODULO");
        String nomeSito = (String) map.get("DESCR_SITO");
        String nomeImpianto = (String) map.get("DESCR_IMPIANTO");
        //nomeFileOutput = "alimod64-" + nrModulo + "-" + nomeSito.replace(" ", "_") + ".pdf";
        nomeFileOutput = "LI-MOD64-"+nrModulo+"-" + Utils.getStringDataOggi() + "-" + Utils.getStringOra()+"-"+ nomeSito.replace(" ", "_")+"-"+nomeImpianto.replace(" ", "_") + ".pdf";
        
        try {
        	LiMod64DAO limod64DAO = new LiMod64DAO();
        	limod64DAO.setAttribute(LiMod64DAO.ID_MODULO, map.get("ID_MODULO"));
			limod64DAO.setAttribute(LiMod64DAO.NOME_FILE, nomeFileOutput);
			limod64DAO.update();
        
        } catch (AppCrash e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
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
