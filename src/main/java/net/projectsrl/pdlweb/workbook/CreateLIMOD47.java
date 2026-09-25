package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;
import project.misc.Utils;

public class CreateLIMOD47 extends CreatePDF {

    public CreateLIMOD47(String templateFile, String outDirectory, Map<String, Object> map) {

        super(templateFile, outDirectory, map);
    }

    @Override
    protected boolean printData(Map<String, Object> map) {

        float x;
        float y;

        // Testata
        y=71;
        
        x=27;
        printWrappedText(x, y, 25, (String) getMap().get("RAGSOC"));        
        
        x=165;
        //printWrappedText(x, y, 25, (String) getMap().get("NR_PDL"));

        y=70;        
        
        x=71;
        printWrappedText(x, y, 25, (String) getMap().get("DESCR_AREA"));
        
        x=108;
        printWrappedText(x, y, 25,(String) getMap().get("DESCR_EQUIPMENT"));
        
        
        
        // Riquadro A
        y=143;        
        x=57;
        printWrappedText(x, y, 100, (String) getMap().get("NOME_COGNOME_PREPOSTO_IMPRESA"));
        
        y=149;
        x=95;
        printWrappedText(x, y, 75, (String) getMap().get("IMPRESA_TESTO"));

        
        
        // Riquadro B
        y=161;       
        x=33;
        printWrappedText(x, y, 25, (String) getMap().get("NR_PDL"));

        x=56;
        printWrappedText(x, y, 25, (String) getMap().get("DT_PDL"));        
        
        
        // Firma
        y=191;       
        x=85;
        printWrappedText(x, y, 75, (String) getMap().get("NOME_COGNOME_DELEGATO_LAVORI_AL"));

        
        
        return true;
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String dtRegistro = Utils.getStringDataOggi();
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "LIMOD47-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
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
        data.put("DESCR_AREA", "Area stoccaggio");        
        data.put("DESCR_EQUIPMENT", "PT90110B- PRES. METANO A LIMITE DI BATTE");
        data.put("NR_PDL", "0001-18");
        data.put("DT_PDL", "10/01/1979");
        data.put("NOME_COGNOME_PREPOSTO_IMPRESA", "Preposto Name");
        data.put("IMPRESA_TESTO", "IdeaIdraulica");
        data.put("NOME_COGNOME_DELEGATO_LAVORI_AL", "Delegato Lavori Name");
        
        CreatePDF pdf = new CreateLIMOD47("D:/works/LIMOD47.pdf", "D:/works/", data);
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
