package net.projectsrl.pdlweb.workbook;



import java.text.Normalizer;

import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;
import project.misc.Utils;

public class CreateLIMOD70 extends CreatePDF {

    public CreateLIMOD70(String templateFile, String outDirectory, Map<String, Object> map) {

        super(templateFile, outDirectory, map);
    }

    @Override
    protected boolean printData(Map<String, Object> map) {

        float x;
        float y;

        x=60;
        y=120;
        printWrappedText(x, y, 100, (String) getMap().get("RAGSOC"));
        
        x=165;
        y=120;
        printWrappedText(x, y, 100, (String) getMap().get("DT_MODULO"));
        
        x=33;
        y=127;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_11"));

        x=33;
        y=133;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_12"));
        
        
        x=110;
        y=127;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_21"));

        x=110;
        y=133;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_22"));
        
        
        x=187;
        y=127;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_31"));

        x=186;
        y=133;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_32"));
        
        
        
        
        
        x=32;
        y=186;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_11"));

        x=54;
        y=186;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_12"));
        
        
        x=109;
        y=186;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_21"));

        x=131;
        y=186;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_22"));
        
        
        x=186;
        y=186;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_31"));

        x=208;
        y=186;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_32"));
        
        
        
        
        x=37;
        y=191;
        printWrappedText(x, y, 100, (String) getMap().get("ORA_11"));
        
        x=114;
        y=191;
        printWrappedText(x, y, 100, (String) getMap().get("ORA_21"));
        
        x=191;
        y=191;
        printWrappedText(x, y, 100, (String) getMap().get("ORA_31"));

        
        x=63;
        y=191;
        printWrappedText(x, y, 100, (String) getMap().get("FIRMA_11"));
        
        x=140;
        y=191;
        printWrappedText(x, y, 100, (String) getMap().get("FIRMA_21"));
        
        x=217;
        y=191;
        printWrappedText(x, y, 100, (String) getMap().get("FIRMA_31"));

        
        
        x=33;
        y=147;
        printWrappedText(x, y, 100, (String) getMap().get("AREA_11"));

        x=110;
        y=147;
        printWrappedText(x, y, 100, (String) getMap().get("AREA_21"));

        x=187;
        y=147;
        printWrappedText(x, y, 100, (String) getMap().get("AREA_31"));
        
        
        // DA RIPETERE SU 5 RIGHE E 6 QUADRATI SE FOSSE NECESSARIO- INIZIO
        x=30;
        y=151;
        printWrappedText(x, y, 100, (String) getMap().get("AZIENDA_11"));
        
        x=55;
        y=151;
        printWrappedText(x, y, 100, (String) getMap().get("PDL_11"));
        
        x=30;
        y=157;
        printWrappedText(x, y, 100, (String) getMap().get("AZIENDA_21"));
        
        x=55;
        y=157;
        printWrappedText(x, y, 100, (String) getMap().get("PDL_21"));
        
        // DA RIPETERE- FINE

        
        
        
        x=33;
        y=198;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_211"));

        x=33;
        y=204;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_212"));
        
        
        x=110;
        y=198;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_221"));

        x=110;
        y=204;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_222"));
        
        
        x=187;
        y=198;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_231"));

        x=186;
        y=204;
        printWrappedText(x, y, 100, (String) getMap().get("PRIMA_VALUTAZIONE_232"));
        
        
        
        
        
        x=32;
        y=254;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_211"));

        x=54;
        y=254;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_212"));
        
        
        x=109;
        y=254;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_221"));

        x=131;
        y=254;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_222"));
        
        
        x=186;
        y=254;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_231"));

        x=208;
        y=254;
        printWrappedText(x, y, 100, (String) getMap().get("RISPETTO_232"));
        
        
        
        x=33;
        y=215;
        printWrappedText(x, y, 100, (String) getMap().get("AREA_211"));

        x=110;
        y=215;
        printWrappedText(x, y, 100, (String) getMap().get("AREA_221"));

        x=187;
        y=215;
        printWrappedText(x, y, 100, (String) getMap().get("AREA_231"));
        
        
        x=38;
        y=259;
        printWrappedText(x, y, 100, (String) getMap().get("ORA_211"));

        x=115;
        y=259;
        printWrappedText(x, y, 100, (String) getMap().get("ORA_221"));

        x=192;
        y=259;
        printWrappedText(x, y, 100, (String) getMap().get("ORA_231"));
        
        
        x=64;
        y=259;
        printWrappedText(x, y, 100, (String) getMap().get("FIRMA_211"));

        x=141;
        y=259;
        printWrappedText(x, y, 100, (String) getMap().get("FIRMA_221"));

        x=218;
        y=259;
        printWrappedText(x, y, 100, (String) getMap().get("FIRMA_231"));

        
        
        
        return true;
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String dtRegistro = Utils.getStringDataOggi();
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "LIMOD70-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
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
        data.put("DT_MODULO", "18/05/2016");
        data.put("PRIMA_VALUTAZIONE_11", "X");
        data.put("PRIMA_VALUTAZIONE_12", "X");
        data.put("PRIMA_VALUTAZIONE_21", "X");
        data.put("PRIMA_VALUTAZIONE_22", "X");
        data.put("PRIMA_VALUTAZIONE_31", "X");
        data.put("PRIMA_VALUTAZIONE_32", "X");
        
        data.put("PRIMA_VALUTAZIONE_211", "X");
        data.put("PRIMA_VALUTAZIONE_212", "X");
        data.put("PRIMA_VALUTAZIONE_221", "X");
        data.put("PRIMA_VALUTAZIONE_222", "X");
        data.put("PRIMA_VALUTAZIONE_231", "X");
        data.put("PRIMA_VALUTAZIONE_232", "X");
        
        data.put("RISPETTO_11", "X");
        data.put("RISPETTO_12", "X");
        data.put("RISPETTO_21", "X");
        data.put("RISPETTO_22", "X");
        data.put("RISPETTO_31", "X");
        data.put("RISPETTO_32", "X");
        
        data.put("RISPETTO_211", "X");
        data.put("RISPETTO_212", "X");
        data.put("RISPETTO_221", "X");
        data.put("RISPETTO_222", "X");
        data.put("RISPETTO_231", "X");
        data.put("RISPETTO_232", "X");
        
        data.put("AREA_11", "CHEMICALS");
        data.put("AREA_21", "CHEMICALS");
        data.put("AREA_31", "CHEMICALS");
        
        data.put("AREA_211", "CHEMICALS");
        data.put("AREA_221", "CHEMICALS");
        data.put("AREA_231", "CHEMICALS");
        
        data.put("ORA_11", "16:43:09");
        data.put("ORA_21", "16:43:09");
        data.put("ORA_31", "16:43:09");

        data.put("ORA_211", "16:43:09");
        data.put("ORA_221", "16:43:09");
        data.put("ORA_231", "16:43:09");
        
        data.put("FIRMA_11", "RICCARDO SALA");
        data.put("FIRMA_21", "RICCARDO SALA");
        data.put("FIRMA_31", "RICCARDO SALA");

        data.put("FIRMA_211", "RICCARDO SALA");
        data.put("FIRMA_221", "RICCARDO SALA");
        data.put("FIRMA_231", "RICCARDO SALA");
        
        data.put("AZIENDA_11", "COEMI");
        data.put("AZIENDA_21", "STEA");
        
        data.put("PDL_11", "1005-2018");
        data.put("PDL_21", "1006-2018");
        
        CreatePDF pdf = new CreateLIMOD70("D:/works/LIMOD70.pdf", "D:/works/", data);
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
