package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;

import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;
import project.misc.Utils;

public class CreateLIMOD64 extends CreatePDF {

    public CreateLIMOD64(String templateFile, String outDirectory, Map<String, Object> map) {

        super(templateFile, outDirectory, map);
    }

    @Override
    protected boolean printData(Map<String, Object> map) {

        float x;
        float y;

        x=35;
        y=-70;
        printWrappedText(x, y, 100, (String) getMap().get("RAGSOC"));
        
        x=120;
        y=-70;
        printWrappedText(x, y, 100, (String) getMap().get("AREA_LAVORO"));
        
        x=205;
        y=-70;
        printWrappedText(x, y, 100, (String) getMap().get("NUMERO"));
        
        x=245;
        y=-70;
        printWrappedText(x, y, 100, (String) getMap().get("DT_MODULO"));
        
        
        
        // DA RIPETERE PER 6 VOLTE - COORDINATE SUCCESSIVE -40, -32, -24, -16, -8
        x=20;
        y=-48;
        printWrappedText(x, y, 100, (String) getMap().get("DESCR_ATTIVITA"));
        
        x=135;
        y=-48;
        printWrappedText(x, y, 100, (String) getMap().get("IMPRESA"));
        
        x=172;
        y=-48;
        printWrappedText(x, y, 100, (String) getMap().get("PREPOSTO"));
        
        x=205;
        y=-48;
        printWrappedText(x, y, 100, (String) getMap().get("CONTRATTO"));
        
        x=230;
        y=-48;
        printWrappedText(x, y, 100, (String) getMap().get("NUM_PDL"));
       
        
        
        
        // AREA A
        // ripetere per 15 volte - y=y+5 quindi 17,22,27
        x=91;
        y=17;
        printWrappedText(x, y, 100, (String) getMap().get("A1_1"));
        
        x=99;
        y=17;
        printWrappedText(x, y, 100, (String) getMap().get("A1_2"));
        
        x=107;
        y=17;
        printWrappedText(x, y, 100, (String) getMap().get("A1_3"));
        
        x=114;
        y=17;
        printWrappedText(x, y, 100, (String) getMap().get("A1_4"));
        
        x=122;
        y=17;
        printWrappedText(x, y, 100, (String) getMap().get("A1_5"));
        
        x=130;
        y=17;
        printWrappedText(x, y, 100, (String) getMap().get("A1_6"));
        
        
        x=13;
        y=87;
        printWrappedText(x, y, 100, (String) getMap().get("ALTRO"));		// SECONDA RIGA y+5
        
        
        
        // AREA B
        // ripetere per 15 volte - y=y+5 quindi 17,22,27
        // ripetere per ogni checkbox da 1 a 6
        x=91;
        y=110;
        printWrappedText(x, y, 100, (String) getMap().get("B1_1"));
        
        
        
        // AREA C
        x=137;
        y=27;
        printWrappedText(x, y, 100, (String) getMap().get("C1_NOTA_COORD_TEMP"));
        
        x=180;
        y=68;
        printWrappedText(x, y, 100, (String) getMap().get("C2_DELIMITAZIONE_AREA"));
        
        x=180;
        y=77;
        printWrappedText(x, y, 100, (String) getMap().get("C2_RECINZIONE_AREA"));
        
        x=180;
        y=87;
        printWrappedText(x, y, 100, (String) getMap().get("C2_COPERTURE"));
        
        x=180;
        y=95;
        printWrappedText(x, y, 100, (String) getMap().get("C2_PARATIE"));
        
        x=170;
        y=148;
        printWrappedText(x, y, 100, (String) getMap().get("C3_USO_DI_DPI"));
        
        x=170;
        y=167;
        printWrappedText(x, y, 100, (String) getMap().get("C3_NOTA_OPERATIVA"));
        
        // ripetere per 5 volte - y=y+5 quindi 17,22,27
        // ripetere per ogni checkbox da 1 a 6 (x+8)
        x=232;
        y=17;
        printWrappedText(x, y, 100, (String) getMap().get("C1_1"));
        
        // ripetere per ogni checkbox da 1 a 6 (x+10)
        x=232;
        y=77;
        printWrappedText(x, y, 100, (String) getMap().get("C2_1"));
        
        // ripetere per ogni checkbox da 1 a 6 (x+15)
        x=232;
        y=120;
        printWrappedText(x, y, 100, (String) getMap().get("C3_1"));
        
        
        x=42;
        y=220;
        printWrappedText(x, y, 100, (String) getMap().get("REV_N"));
        
        x=42;
        y=226;
        printWrappedText(x, y, 100, (String) getMap().get("REV_DATA"));
        
        x=230;
        y=226;
        printWrappedText(x, y, 100, (String) getMap().get("COORD_GEST"));
        
        
        
        return true;
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String dtRegistro = Utils.getStringDataOggi();
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "LIMOD64-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
        nomeFileOutput = Normalizer.normalize(nomeFileOutput, Normalizer.Form.NFD);
        nomeFileOutput = nomeFileOutput.replaceAll("[^\\p{ASCII}]", "");
        nomeFileOutput = nomeFileOutput.replaceAll("[^a-zA-Z0-9.-]", "_");
        nomeFileOutput = getOutDirectory() + nomeFileOutput;
        return nomeFileOutput;
        
    }

    public static void main(String[] args) {

        Map<String, Object> data = new HashMap<String, Object>();

        // mi servono per il nome file
        data.put("RAGSOC", "GASCO2");
        data.put("AREA_LAVORO", "CABINA 9.4");
        data.put("NUMERO", "59");
        data.put("DT_MODULO", "18/05/2016");
        
        data.put("DESCR_ATTIVITA", "CAMPO1");
        data.put("IMPRESA", "CAMPO2");
        data.put("PREPOSTO", "CAMPO3");
        data.put("CONTRATTO", "CAMPO4");
        data.put("NUM_PDL", "CAMPO5");
        
        data.put("A1_1", "X");
        data.put("A1_2", "X");
        data.put("A1_3", "X");
        data.put("A1_4", "X");
        data.put("A1_5", "X");
        data.put("A1_6", "X");
        data.put("ALTRO", "TESTO");
        
        data.put("B1_1", "X");
        
        data.put("C1_NOTA_COORD_TEMP", "NOTA_COORD_TEMP");
        data.put("C2_DELIMITAZIONE_AREA", "C2_DELIMITAZIONE_AREA");
        data.put("C2_RECINZIONE_AREA", "C2_RECINZIONE_AREA");
        data.put("C2_COPERTURE", "C2_COPERTURE");
        data.put("C2_PARATIE", "C2_PARATIE");
        data.put("C3_USO_DI_DPI", "C3_USO_DI_DPI");
        data.put("C3_NOTA_OPERATIVA", "C3_NOTA_OPERATIVA");
        
        data.put("C1_1", "X");
        data.put("C2_1", "X");
        data.put("C3_1", "X");
        
        data.put("REV_N", "123");
        data.put("REV_DATA", "12/03/2018");
        data.put("COORD_GEST", "COORDINATORE");
       
        
        CreatePDF pdf = new CreateLIMOD64("D:/works/LIMOD64.pdf", "D:/works/", data);
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
