package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;

import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;
import project.misc.Utils;

public class CreateLIMOD71Retro extends CreatePDF {

    public CreateLIMOD71Retro(String templateFile, String outDirectory, Map<String, Object> map) {

        super(templateFile, outDirectory, map);
    }

    @Override
    protected boolean printData(Map<String, Object> map) {

        float x;
        float y;

        x=30;
        y=49;
        printWrappedText(x, y, 40, (String) getMap().get("RAGSOC"));
        
        return true;
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String dtRegistro = Utils.getStringDataOggi();
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "LIMOD71-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
        nomeFileOutput = Normalizer.normalize(nomeFileOutput, Normalizer.Form.NFD);
        nomeFileOutput = nomeFileOutput.replaceAll("[^\\p{ASCII}]", "");
        nomeFileOutput = nomeFileOutput.replaceAll("[^a-zA-Z0-9.-]", "_");
        nomeFileOutput = getOutDirectory() + nomeFileOutput;
        return nomeFileOutput;
        
    }

    public static void main(String[] args) {

        Map<String, Object> data = new HashMap<String, Object>();

        // mi servono per il nome file
        data.put("RAGSOC", "Limito");
        
        CreatePDF pdf = new CreateLIMOD71Retro("D:/works/LIMOD71.pdf", "D:/works/", data);
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
