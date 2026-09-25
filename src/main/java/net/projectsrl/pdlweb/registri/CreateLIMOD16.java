package net.projectsrl.pdlweb.registri;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfStamper;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;
import net.projectsrl.wm.utils.Utils;

public class CreateLIMOD16 extends CreatePDF {


    public CreateLIMOD16(String templateFile, String outDirectory, Map<String, Object> map) {

        super(templateFile, outDirectory, map);

    }

    @Override
    protected boolean printData(Map<String, Object> map) {

        boolean printEnded = false;
        String pageNo=(String) map.get(PAGE_NUMBER);
        if (pageNo==null) {
            pageNo="0";
        }
        map.put(PAGE_NUMBER, ""+(Integer.parseInt(pageNo)+1));
        float y = 20;
        int x = 120;
        
        String nomeSito = (String) map.get("RAGSOC");
        
        if (nomeSito.equals("Priolo - SMR.IA")) {
            y = 19;
            x = 160;
            printText(x, y, "LI/RCSS/PR MOD 16");
            y = 38;
            printText(x, y, "LI/RCSS/PR");
        }
        
        y = 33;
        x = 160;
        printText(x, y, ""+(String) map.get(PAGE_NUMBER));
        
        y = 64;
        x = 35;
        printTextField(x, y, "DT_REGISTRO");

        List<String[]> report = (List<String[]>) map.get("report");

        setFontSize(10);
        printText(50, 266, ""+ map.get(DATASET_SIZE));

        setFontSize(8);

        y = 94;

        printEnded = true;
        boolean primoRecord=true;


        List<String[]> used = new ArrayList<String[]>();
        
        for (String[] row : report) {
            
            if (y >= 249 - 20) {
                printEnded = false;
                break;
            }

            if (primoRecord) {
                primoRecord=false;
            } else {
                printText(27, y,
                        "_______________________________________________________________________________________________________");
                y += 5;
            }

            printText(27, y, row[0]);

            float yRow1 = printWrappedText(40, y, 35, row[1]);

            float yRow2 = printWrappedText(95, y, 20, row[2]);

            float yRow3 = printWrappedText(95, yRow2 + 7, 12, row[3]);

            float yRow4 = printWrappedText(122, y, 13, row[4]);

            float yRow5 = printWrappedText(144, y, 30, row[5]);

            float yRow6 = printWrappedText(144, yRow5 + 7, 15, row[6]);

            float yRow7 = yRow6 + 5;

            printText(144, yRow7, "_________________");
            
            printText(175, y, row[7]);

            float yRow8 = printWrappedText(175, y + 7, 15,  (String) map.get("NOME_UTENTE")+" "+(String) map.get("COGNOME_UTENTE"));

            float yRow9 = yRow8 + 5;

            printText(175, yRow9, "__________");
            
            y = Math.max(y, yRow1);
            y = Math.max(y, yRow2);
            y = Math.max(y, yRow3);
            y = Math.max(y, yRow4);
            y = Math.max(y, yRow5);
            y = Math.max(y, yRow6);
            y = Math.max(y, yRow7);
            y = Math.max(y, yRow8);
            y = Math.max(y, yRow9);

            y += 3;

            used.add(row);
        }
        
        report.removeAll(used);        
        
        return printEnded;
    }


    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String dtRegistro = Utils.getStringDataOggi();
        map.put("DT_REGISTRO", dtRegistro);
        String nomeSito = (String) map.get("RAGSOC");
        nomeFileOutput = "LIMOD16-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
        nomeFileOutput = Normalizer.normalize(nomeFileOutput, Normalizer.Form.NFD);
        nomeFileOutput = nomeFileOutput.replaceAll("[^\\p{ASCII}]", "");
        nomeFileOutput = nomeFileOutput.replaceAll("[^a-zA-Z0-9.-]", "_");
        nomeFileOutput = getOutDirectory() + nomeFileOutput;
        return nomeFileOutput;
    }

    
    @Override
    public String createPDF() throws AppCrash {

        String nomeFileOutput = null;

        PdfReader reader = null;
        PdfStamper stamper = null;
        Document document = null;
        PdfCopy writer = null;
        ByteArrayOutputStream baos = null;
        PdfReader readerPageTemp = null;


        try {

            setBf( BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED));

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);

            nomeFileOutput = setPDFOutFileName(getMap());

            if (!nomeFileOutput.startsWith(getOutDirectory())) {
                nomeFileOutput = Normalizer.normalize(nomeFileOutput, Normalizer.Form.NFD);
                nomeFileOutput = nomeFileOutput.replaceAll("[^\\p{ASCII}]", "");
                nomeFileOutput = nomeFileOutput.replaceAll("[^a-zA-Z0-9.-]", "_");
                nomeFileOutput = getOutDirectory() + nomeFileOutput;
            }

            boolean printEnded = false;

            document = new Document();
            writer = new PdfSmartCopy(document, new FileOutputStream(nomeFileOutput));
            document.open();

            int pageNumber=0;
            while (!printEnded) {

                ++pageNumber;


                baos = new ByteArrayOutputStream();
                reader = new PdfReader(getOutDirectory()+"LIMOD16.pdf");
                stamper = new PdfStamper(reader, baos);
                stamper.setFormFlattening(true);
                setCb(stamper.getOverContent(reader.getNumberOfPages()));

                printEnded = printData(getMap());

                stamper.close();
                reader.close();

                readerPageTemp = new PdfReader(baos.toByteArray());
                PdfImportedPage pageTemp = writer.getImportedPage(readerPageTemp, 1);
                writer.addPage(pageTemp);
                readerPageTemp.close();
                baos.close();
            }

            document.close();
            writer.close();

            addAttachments(nomeFileOutput);

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {

            if (readerPageTemp != null) {
                try {
                    readerPageTemp.close();

                } catch (Throwable th) {
                    AppCrash ac = new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close readerPageTemp");
                }
            }

            if (stamper != null) {
                try {
                    stamper.close();

                } catch (Throwable th) {
                    AppCrash ac = new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close stamper");
                }
            }

            if (reader != null) {
                try {
                    reader.close();

                } catch (Throwable th) {
                    AppCrash ac = new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close reader");
                }
            }

            if (baos != null) {
                try {
                    baos.close();

                } catch (Throwable th) {
                    AppCrash ac = new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close reader");
                }
            }

            if (document != null) {
                try {
                    document.close();

                } catch (Throwable th) {
                    AppCrash ac = new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close document");
                }
            }

            if (writer != null) {
                try {
                    writer.close();

                } catch (Throwable th) {
                    AppCrash ac = new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close writer");
                }
            }

        }
        return nomeFileOutput;

    }

    public static void main(String[] args) {

        Map<String, Object> data = new HashMap<String, Object>();

        // mi servono per il nome file
        data.put("RAGSOC", "Priolo - SMR.IA");

        List<String[]> report = new ArrayList<String[]>();

        report.add(new String[] { "0", "DESCRIZIONE LAVORO",
                "come da mail precedente, sono stati individuati e risolti la maggior parte dei problemi", "DESCR_AREA",
                "aiaueue eueieie eieieeie eieie", "pippero priirrrorrrrr", "Pippo Franco Giulio Cesareeeeeeee",
                "DT_PDL" });
        report.add(new String[] { "0001-18",
                "come da mail precedente, sono stati individuati e risolti la maggior parte dei problemi causati principalmente da alcune incompatibilità di alcuni dati durante le importazioni delle tabelle PdL dal vecchio tool di gestione al nuovo portale.",
                "come da mail precedente, sono stati individuati e risolti la maggior parte dei problemi", "DESCR_AREA",
                "IMPRESA TESTO", "RESPONSABILE CENTRALE DELEGATO", "NOME COGNOME DELEGATO LAVORI AL", "DT_PDL" });
        report.add(new String[] { "0002-18",
                "come da mail precedente, sono stati individuati e risolti la maggior parte dei problemi causati principalmente da alcune incompatibilità di alcuni dati durante le importazioni delle tabelle PdL dal vecchio tool di gestione al nuovo portale.",
                "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO", "RESPONSABILE_CENTRALE_DELEGATO",
                "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "3", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "4", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "5", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "6", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "7", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "8", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "9", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });
        report.add(new String[] { "NR_PDL", "DESCRIZIONE_LAVORO", "DESCR_EQUIPMENT", "DESCR_AREA", "IMPRESA_TESTO",
                "RESPONSABILE_CENTRALE_DELEGATO", "NOME_COGNOME_DELEGATO_LAVORI_AL", "DT_PDL" });

        data.put("report", report);

        data.put("COGNOME_UTENTE", "Miduri");
        data.put("NOME_UTENTE", "Francesco");
        
        String directory = "D:/works/";

        CreatePDF pdf = new CreateLIMOD16(directory + "LIMOD16.pdf", directory, data);
        pdf.setTest(true);
        pdf.setFontSize(7);

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
