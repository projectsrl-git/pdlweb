package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;

import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;
import project.misc.Utils;

public class CreateLIMOD73 extends CreatePDF {

    public CreateLIMOD73(String templateFile, String outDirectory, Map<String, Object> map) {

        super(templateFile, outDirectory, map);
    }

    @Override
    protected boolean printData(Map<String, Object> map) {

        float x;
        float y;

        x=80;
        y=83;
        String text=(String) getMap().get("RAGSOC");
        printTextField(x, y, text.replace("Sito di ", ""));

        
        
        
        return true;
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String dtRegistro = Utils.getStringDataOggi();
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "LIMOD73-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
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
        
        CreatePDF pdf = new CreateLIMOD73("D:/works/LIMOD73.pdf", "D:/works/", data);
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
