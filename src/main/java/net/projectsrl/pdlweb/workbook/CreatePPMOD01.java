
package net.projectsrl.pdlweb.workbook;

import java.io.FileOutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfStamper;

import net.project.errors.AppCrash;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.pdf.CreateAcroFieldsPDF_base;
import project.misc.Utils;

/**
 * PP-MOD01 "Allegati al Permesso di Lavoro" e' in realta' composto da 3 sezioni indipendenti,
 * ciascuna condizionata dal proprio flag sulla tabella PDL:
 * 
 * - FLG_V9_PDL_A_CALDO -> sezione A) Permesso di Lavoro a caldo
 * - FLG_IN_QUOTA -> sezione B) Permesso per lavori in quota
 * - FLG_SCAVO -> sezione C) Permesso di lavoro per scavi
 * 
 * In precedenza esisteva un unico template "PPMOD01.pdf" che conteneva SEMPRE tutte e 3 le sezioni,
 * a prescindere dai flag. Ora ogni sezione ha un proprio template PDF dedicato (stessi campi
 * AcroForm/stesso layout della relativa parte del vecchio template, semplicemente separati in 3
 * file), e viene creato un solo PDF che contiene solo le sezioni effettivamente richieste dai flag:
 * 
 * - se e' vero un solo flag -> il PDF finale contiene solo quella sezione;
 * - se sono veri piu' flag -> le sezioni vengono unite in un unico PDF, nell'ordine
 * caldo -> quota -> scavo.
 * 
 * IMPORTANTE: i 3 file di template sotto elencati (TEMPLATE_CALDO/TEMPLATE_QUOTA/TEMPLATE_SCAVO)
 * devono essere preparati come PDF con campi AcroForm (stessi nomi campo gia' in uso nel vecchio
 * PPMOD01.pdf per la relativa sezione) e collocati in webapp/templates/. Il vecchio PPMOD01.pdf
 * unico va dismesso/sostituito da questi 3 file.
 */
public class CreatePPMOD01 extends CreateAcroFieldsPDF_base {

    private static final String TEMPLATE_CALDO = "PPMOD01_CALDO.pdf";
    private static final String TEMPLATE_QUOTA = "PPMOD01_QUOTA.pdf";
    private static final String TEMPLATE_SCAVO = "PPMOD01_SCAVO.pdf";

    private static final int MAX_ROWS_IN_PAGE = 3;

    public CreatePPMOD01(String outDirectory, Map<String, Object> map) {

        super(outDirectory, map);
    }

    @Override
    protected String setPDFOutFileName(Map<String, Object> map) {

        String nomeFileOutput;
        String nomeSito = (String) map.get("RAGSOC");
        String dtRegistro = Utils.getStringDataOggi();
        nomeFileOutput = "PPMOD01-" + Utils.ribaltaData(dtRegistro) + "-" + nomeSito.replace(" ", "_") + ".pdf";
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

        // Usato dal costruttore della classe base solo per calcolare la directory dei template
        // (_templateDirectory). Il/i template realmente usati in stampa vengono scelti dinamicamente
        // sezione per sezione, in base ai flag: vedi getSezioniDaStampare()/createPDF().
        return TEMPLATE_CALDO;
    }

    @Override
    protected void setAcroField(Map<String, Object> map, String acroFieldName) {

        super.setAcroField(map, acroFieldName);

        if (acroFieldName.startsWith("FLG") && (Boolean) map.get(acroFieldName) == true) {
            setAcroFieldValue(acroFieldName, Constants_itf.ACROBAT_CHECKED);
        }
    }

    /**
     * Determina, in base ai flag della richiesta/PDL, quali sezioni (e quindi quali template) vanno
     * incluse nel PDF finale, gia' nell'ordine in cui devono comparire quando sono piu' di una.
     */
    private List<String> getSezioniDaStampare(Map<String, Object> map) {

        List<String> sezioni = new ArrayList<String>();

        if (isFlagTrue(map, "FLG_V9_PDL_A_CALDO")) {
            sezioni.add(TEMPLATE_CALDO);
        }
        if (isFlagTrue(map, "FLG_IN_QUOTA")) {
            sezioni.add(TEMPLATE_QUOTA);
        }
        if (isFlagTrue(map, "FLG_SCAVO")) {
            sezioni.add(TEMPLATE_SCAVO);
        }

        return sezioni;
    }

    private boolean isFlagTrue(Map<String, Object> map, String flagName) {

        Object value = map.get(flagName);
        return (value instanceof Boolean) && ((Boolean) value);
    }

    /**
     * Sovrascrive completamente createPDF() della classe base: quest'ultima gestisce un solo
     * template fisso, mentre qui serve compilare 1, 2 o 3 template diversi (uno per ciascuna
     * sezione attiva) ed eventualmente unirli in un solo documento finale.
     */
    @Override
    public String createPDF() throws AppCrash {

        List<String> sezioni = getSezioniDaStampare(getMap());

        if (sezioni.isEmpty()) {
            throw new AppCrash(new IllegalStateException(
                    "Nessuna sezione da stampare per PPMOD01: nessuno dei flag FLG_V9_PDL_A_CALDO, "
                            + "FLG_IN_QUOTA, FLG_SCAVO risulta valorizzato per questo PDL"));
        }

        String nomeFileOutput = setPDFOutFileName(getMap());

        Document document = null;
        PdfCopy writer = null;

        try {
            document = new Document();
            writer = new PdfSmartCopy(document, new FileOutputStream(nomeFileOutput));
            document.open();

            for (String templateName : sezioni) {
                aggiungiSezione(writer, templateName, getMap());
            }

            document.close();
            writer.close();

        } catch (Throwable t) {

            throw new AppCrash(t);

        } finally {

            if (document != null && document.isOpen()) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
        }

        return nomeFileOutput;
    }

    /**
     * Compila un singolo template di sezione (riempiendo i suoi campi AcroForm con i dati della
     * map, riusando la stessa logica di setAcroField/printData gia' presente per il caso a
     * template unico) e ne riversa tutte le pagine ottenute nel documento finale (writer).
     */
    private void aggiungiSezione(PdfCopy writer, String templateName, Map<String, Object> map) throws AppCrash {

        PdfReader reader = null;
        PdfStamper stamper = null;
        ByteArrayOutputStream baos = null;
        PdfReader readerSezioneCompilata = null;

        try {
            String templateFile = getTemplateDirectory() + templateName;

            baos = new ByteArrayOutputStream();
            reader = new PdfReader(templateFile);
            stamper = new PdfStamper(reader, baos);
            stamper.setFormFlattening(true);

            AcroFields acroFields = stamper.getAcroFields();
            setAcroFields(acroFields);

            printData(map);

            stamper.close();
            reader.close();

            readerSezioneCompilata = new PdfReader(baos.toByteArray());
            for (int i = 1; i <= readerSezioneCompilata.getNumberOfPages(); i++) {
                PdfImportedPage pagina = writer.getImportedPage(readerSezioneCompilata, i);
                writer.addPage(pagina);
            }

        } catch (Throwable t) {

            AppCrash ac = new AppCrash(t);
            ac.logContext(this.getClass().getName(), "Errore nella compilazione del template " + templateName);
            throw ac;

        } finally {

            if (readerSezioneCompilata != null) {
                readerSezioneCompilata.close();
            }
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (Throwable th) {
                    // gia' chiuso sopra nel percorso senza errori: ignorabile
                }
            }
            if (reader != null) {
                reader.close();
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (Throwable th) {
                    // ignorabile
                }
            }
        }
    }

}
