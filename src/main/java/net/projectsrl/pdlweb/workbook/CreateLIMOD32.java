package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;

import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;
import project.misc.Utils;

public class CreateLIMOD32 extends CreatePDF {

    public CreateLIMOD32(String templateFile, String outDirectory, Map<String, Object> map) {

        super(templateFile, outDirectory, map);
    }

    @Override
    protected boolean printData(Map<String, Object> map) {

        float x;
        float y;

        
        //Testata
        x=38;
        y=38;
        printWrappedText(x, y, 25, (String) getMap().get("RAGSOC"));
        
        x=175;
        printWrappedText(x, y, 25, (String) getMap().get("NR_PDL"));
                
        
        //Riquadro A
        x=75;
        y=53;
        //printWrappedText(x, y, 40, (String) getMap().get("RESPONSABILE_CENTRALE_DELEGATO"));
        
        x=130;
        printWrappedText(x, y, 60, (String) getMap().get("DESCR_EQUIPMENT"));        

        
        //Riquadro B
        x=72;
        y=100;
        printWrappedText(x, y, 40, (String) getMap().get("RESPONSABILE_CENTRALE_DELEGATO"));

        x=160;
        printWrappedText(x, y, 60, (String) getMap().get("DESCR_EQUIPMENT"));        
        

        //Riquadro C
        x=50;
        y=142;
        printWrappedText(x, y, 40, (String) getMap().get("NOME_COGNOME_DELEGATO_LAVORI_AL"));
        
        x=160;
        printWrappedText(x, y, 60, (String) getMap().get("DESCR_EQUIPMENT"));        

        
        //Riquadro D
        x=73;
        y=179;
        printWrappedText(x, y, 40, (String) getMap().get("RESPONSABILE_CENTRALE_DELEGATO"));

        x=145;
        printWrappedText(x, y, 60, (String) getMap().get("DESCR_EQUIPMENT"));        

        
        //Riquadro E
        x=73;
        y=216;
        printWrappedText(x, y, 75, (String) getMap().get("DESCR_EQUIPMENT"));

        x=135;
        y=237;
        printWrappedText(x, y, 50, (String) getMap().get("NOME_COGNOME_DELEGATO_LAVORI_AL"));
        
        x=115;
        y=259;
        //printWrappedText(x, y, 50, (String) getMap().get("RESPONSABILE_CENTRALE_DELEGATO"));

        
        
        
        return true;
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String dtRegistro = Utils.getStringDataOggi();
        map.put("DT_REGISTRO", dtRegistro);
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "LIMOD32-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
        nomeFileOutput = Normalizer.normalize(nomeFileOutput, Normalizer.Form.NFD);
        nomeFileOutput = nomeFileOutput.replaceAll("[^\\p{ASCII}]", "");
        nomeFileOutput = nomeFileOutput.replaceAll("[^a-zA-Z0-9.-]", "_");
        nomeFileOutput = getOutDirectory() + nomeFileOutput;
        return nomeFileOutput;
        
    }

    public static void main(String[] args) {

        Map<String, Object> data = new HashMap<String, Object>();

        // mi servono per il nome file
        data.put("RAGSOC", "Sito di Limito");
        data.put("DESCR_EQUIPMENT", "PT90110B- PRES. METANO A LIMITE DI BATTE");
        data.put("NR_PDL", "0001-18");
        data.put("DT_PDL", "10/01/1979");
        data.put("RESPONSABILE_CENTRALE_DELEGATO", "Babbo Natale");
        data.put("NOME_COGNOME_DELEGATO_LAVORI_AL", "La Befana vien di notte");
        
        CreatePDF pdf = new CreateLIMOD32("D:/works/LIMOD32.pdf", "D:/works/", data);
        pdf.setTest(true);

        String filename = null;

        try {
            filename = pdf.createPDF();
        } catch (AppCrash e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("terminato---------------------------------" + filename);
    }

}
