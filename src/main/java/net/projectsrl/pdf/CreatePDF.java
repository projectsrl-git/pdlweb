
package net.projectsrl.pdf;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfStamper;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.webapp.core.WebAppConstants_itf;

//TODO rinominare CreatePDF_base
//TODO getTemplateName abstract
//TODO void printData
public abstract class CreatePDF {

    Map<String, Object>        _map;
    private String             _templateFile;
    private String             _coverTemplate;
    private String             _backCoverTemplate;
    private String             _internalPagesTemplate;
    private String[]           _fixedPagesTemplateArray;

    private String             _outDirectory;
    private String             _templateDirectory;
    private PdfContentByte     _cb;
    private BaseFont           _bf;
    private float              _fontSize           = 8;
    private boolean            _test;
    private AcroFields         _acroFields;
    private int                _lastRecordPrinted  = -1;
    private int                _pageNumber         = 0;
    private int                _maxRowsInPage      = 0;
    private int                _totalPages         = 0;
    private int                _fixedNumberOfPages = 0;

    public static final String PAGE_NUMBER         = "_PAGE_NUMBER";
    public static final String PAGE_OF_TOTAL       = "_PAGE_OF_TOTAL";
    public static final String TOTAL_PAGES         = "_TOTAL_PAGES";
    public static final String DATASET_SIZE        = "_DATASET_SIZE";
    public static final String LIST_OF_ROWS        = "_rows";

    public CreatePDF(String templateFile, String outDirectory, Map<String, Object> map) {

        _outDirectory = outDirectory;
        _templateDirectory = outDirectory.replace(WebAppConstants_itf.OUTPUT_PATH, WebAppConstants_itf.TEMPLATES_PATH);
        _templateFile = templateFile;
        _map = map;
    }

    public CreatePDF(String outDirectory, Map<String, Object> map) {

        _outDirectory = outDirectory;
        _templateDirectory = outDirectory.replace(WebAppConstants_itf.OUTPUT_PATH, WebAppConstants_itf.TEMPLATES_PATH);
        _map = map;

        if (getTemplateName() != null) {
            _templateFile = _templateDirectory + getTemplateName();
        }

    }

    protected String getTemplateFile(int pageNumber, int totalPages) {

        if (getFixedNumberOfPages() > 0) {
            return _templateDirectory + _fixedPagesTemplateArray[pageNumber - 1];
        } else if (pageNumber == 1 && _coverTemplate != null) {
            return _templateDirectory + _coverTemplate;
        } else if (pageNumber == totalPages && _backCoverTemplate != null) {
            return _templateDirectory + _backCoverTemplate;
        } else if (pageNumber > 1 && _internalPagesTemplate != null) {
            return _templateDirectory + _internalPagesTemplate;
        } else {
            return _templateFile;
        }

    }

    protected String getTemplateName() {

        return null;
    }

    public String createPDF() throws AppCrash {

        String nomeFileOutput = null;

        PdfReader reader = null;
        PdfStamper stamper = null;
        Document document = null;
        PdfCopy writer = null;
        ByteArrayOutputStream baos = null;
        PdfReader readerPageTemp = null;
        _pageNumber = 0;

        setTotalPages();

        try {

            _bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);

            nomeFileOutput = setPDFOutFileName(_map);

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

            while (!printEnded) {

                ++_pageNumber;

                _map.put(PAGE_NUMBER, "" + _pageNumber);

                if (getTotalPages() != 0) {
                    _map.put(PAGE_OF_TOTAL, _pageNumber + "/" + getTotalPages());
                }

                baos = new ByteArrayOutputStream();
                reader = new PdfReader(getTemplateFile(_pageNumber, getTotalPages()));
                stamper = new PdfStamper(reader, baos);
                stamper.setFormFlattening(true);
                _cb = stamper.getOverContent(reader.getNumberOfPages());
                _acroFields = stamper.getAcroFields();

                printEnded = isPrintEnded(_map);

                printData(_map);
                
                handleAcroFields(_acroFields,_map);

                stamper.close();
                reader.close();

                readerPageTemp = new PdfReader(baos.toByteArray());
                /*PdfImportedPage pageTemp = writer.getImportedPage(readerPageTemp, 1);
                writer.addPage(pageTemp)*/;
                
             // Invece di aggiungere solo la pagina 1, devi ciclare su tutte le pagine del readerPageTemp
                for (int i = 1; i <= readerPageTemp.getNumberOfPages(); i++) {
                    PdfImportedPage pageTemp = writer.getImportedPage(readerPageTemp, i);
                    writer.addPage(pageTemp);
                }
                readerPageTemp.close();
                baos.close();
            }

            document.close();
            writer.close();

            addAttachments(nomeFileOutput);
            addDiagram(nomeFileOutput);
            addSignature(nomeFileOutput);

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

    protected void handleAcroFields(AcroFields acroFields, Map<String, Object> map) throws AppCrash {

        // TODO Auto-generated method stub
        
    }

    @SuppressWarnings("unchecked")
    protected boolean isPrintEnded(Map<String, Object> map) {

        boolean printEnded;

        List<Map<String, Object>> report = (List<Map<String, Object>>) map.get(CreatePDF.LIST_OF_ROWS);

        if (report != null) {
            printEnded = createIndexedFieldsFromRows(map, report);
        } else {
            if (getFixedNumberOfPages() > 0) {
                if (getPageNumber() == getTotalPages()) {
                    printEnded = true;
                } else {
                    printEnded = false;
                }
            } else {
                printEnded = true;
            }

        }
        return printEnded;
    }

    public int getFixedNumberOfPages() {

        return _fixedNumberOfPages;
    }

    public int getTotalPages() {

        return _totalPages;
    }

    protected void addAttachments(String nomeFileOutput) throws AppCrash {

    }
    
    protected void addDiagram(String png) throws AppCrash {

    }
    
    protected void addSignature(String png) throws AppCrash {

    }

    protected void removePreviousPageFieldsFromMap(Map<String, Object> map) {

        List<String> previousPageIndexedFields = new ArrayList<String>();

        for (String keyName : map.keySet()) {
            if (keyName.indexOf(".") != -1) {
                for (int i = 1; i <= getMaxRowsInPage(); i++) {
                    if (keyName.endsWith("." + i)) {
                        previousPageIndexedFields.add(keyName);
                        break;
                    }
                }
            }
        }

        for (String key : previousPageIndexedFields) {
            map.remove(key);
        }
    }

    protected boolean createIndexedFieldsFromRows(Map<String, Object> map, List<Map<String, Object>> report) {

        removePreviousPageFieldsFromMap(map);

        boolean endOfReport = true;

        List<Map<String, Object>> used = new ArrayList<Map<String, Object>>();

        int i = 0;
        for (Map<String, Object> row : report) {

            ++i;

            if (isPageBreak(i)) {
                endOfReport = false;
                break;
            }

            for (String fieldName : row.keySet()) {

                Object fieldValue = row.get(fieldName);

                putIndexedFieldInMap(map, i, fieldName, fieldValue);
            }

            used.add(row);
        }

        report.removeAll(used);

        return endOfReport;
    }

    protected void putIndexedFieldInMap(Map<String, Object> map, int i, String fieldName, Object fieldValue) {

        map.put(fieldName + "." + i, fieldValue);
    }

    private void setTotalPages() {

        if (_map.get(LIST_OF_ROWS) != null) {
            List<?> rows = (List<?>) _map.get(LIST_OF_ROWS);
            int totalRows = rows.size();

            if (totalRows > 0 && getMaxRowsInPage() > 0) {
                _totalPages = (int) Math.ceil((double) totalRows / getMaxRowsInPage());
            }

        } else if (getFixedNumberOfPages() > 0) {
            _totalPages = getFixedNumberOfPages();
        } else {
            _totalPages = 1;
        }

        _map.put(TOTAL_PAGES, "" + getTotalPages());

    }

    protected void setAcroFieldValue(String acroFieldName, String acroFieldValue) {

        try {

            _acroFields.setField(acroFieldName, acroFieldValue);

        } catch (Throwable th) {

            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(),
                    "acroFieldName:" + acroFieldName + " - acroFieldValue:" + acroFieldValue);
        }
    }

    /**
     * Consente a una sottoclasse che ridefinisce completamente createPDF() (ad es. per comporre piu'
     * template diversi in un solo documento finale, come CreatePPMOD01) di impostare l'AcroFields
     * "corrente" prima di riusare la logica di riempimento campi gia' presente in questa classe
     * (setAcroField/printData), invece di doverla riscrivere da zero.
     */
    protected void setAcroFields(AcroFields acroFields) {

        _acroFields = acroFields;
    }

    protected void setAcroField(Map<String, Object> map, String acroFieldName) {

        Object acroFieldValue = null;
        try {
            acroFieldValue = map.get(acroFieldName);

            if (acroFieldValue instanceof String) {
                _acroFields.setField(acroFieldName, (String) acroFieldValue);

                acroFieldValue = ((String) acroFieldValue).trim();

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2081)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2081)), "\u2081");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2082)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2082)), "\u2082");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2083)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2083)), "\u2083");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2084)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2084)), "\u2084");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2085)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2085)), "\u2085");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2086)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2086)), "\u2086");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2087)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2087)), "\u2087");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2088)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2088)), "\u2088");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2089)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2089)), "\u2089");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2264)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2264)), "\u2264");
                }

                if (((String) acroFieldValue).contains(Character.toString(ExtendedAscii.getAscii(0x2265)))) {
                    ((String) acroFieldValue).replace(Character.toString(ExtendedAscii.getAscii(0x2265)), "\u2265");
                }

                if (acroFieldValue.equals("SI")) {
                    _acroFields.setField(acroFieldName + "_SI", Constants_itf.ACROBAT_CHECKED);
                } else if (acroFieldValue.equals("NO")) {
                    _acroFields.setField(acroFieldName + "_NO", Constants_itf.ACROBAT_CHECKED);
                } else if (acroFieldValue.equals("NA")) {
                    _acroFields.setField(acroFieldName + "_NA", Constants_itf.ACROBAT_CHECKED);
                } else if (acroFieldValue.equals("S")) {
                    _acroFields.setField(acroFieldName + "_SI", Constants_itf.ACROBAT_CHECKED);
                } else if (acroFieldValue.equals("N")) {
                    _acroFields.setField(acroFieldName + "_NO", Constants_itf.ACROBAT_CHECKED);
                }

            } else if (acroFieldValue instanceof Integer) {
                _acroFields.setField(acroFieldName, ((Integer) acroFieldValue).toString());
            }

        } catch (Throwable th) {

            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(),
                    "acroFieldName:" + acroFieldName + " - acroFieldValue:" + acroFieldValue);
        }
    }

    protected abstract boolean printData(Map<String, Object> map);

    protected abstract String setPDFOutFileName(Map<String, Object> map);

    protected void printBoolean(float x, float y, String fieldName) {

        Boolean flagObj = (Boolean) _map.get(fieldName);

        if (Util.IsEmpty(flagObj)) {
            if (_test) {
                flagObj = Boolean.TRUE;
            } else {
                return;

            }
        }

        boolean flag = flagObj;
        if (flag) {

            String text = getFlagChar();

            printText(x, y, text);
        }
    }

    protected void printText(float x, float y, String text) {

        if (Util.IsEmpty(text)) {
            return;
        }

        _cb.beginText();
        _cb.moveText(Utilities.millimetersToPoints(x), 842 - Utilities.millimetersToPoints(y));

        _cb.setFontAndSize(_bf, _fontSize);
        _cb.showText(text);
        _cb.endText();
    }

    protected String getFlagChar() {

        return "x";
    }

    protected void printTextField(float x, float y, String fieldName) {

        String text = (String) _map.get(fieldName);

        if (Util.IsEmpty(text)) {
            if (_test) {
                text = fieldName;
            } else {
                return;

            }
        }

        printText(x, y, text);
    }

    protected void printNumericField(int x, int y, String fieldName) {

        String numberText = (String) _map.get(fieldName);

        if (Util.IsEmpty(numberText)) {
            if (_test) {
                numberText = fieldName;
            } else {
                return;

            }
        }

        printNumber(x, y, numberText);
    }

    protected void printNumber(int x, int y, String numberText) {

        _cb.beginText();
        _cb.moveText(Utilities.millimetersToPoints(x), 842 - Utilities.millimetersToPoints(y));
        _cb.setFontAndSize(_bf, _fontSize);
        _cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                NumberFormat.getCurrencyInstance(Locale.GERMANY).format(Float.parseFloat(numberText)), x, y, 0);
        _cb.endText();

    }

    public void setTest(boolean test) {

        _test = test;
    }

    protected String getOutDirectory() {

        return _outDirectory;
    }

    public void setFontSize(float fontSize) {

        _fontSize = fontSize;
    }

    public float getFontSize() {

        return _fontSize;
    }

    public PdfContentByte getCb() {

        return _cb;
    }

    public void setCb(PdfContentByte _cb) {

        this._cb = _cb;
    }

    public BaseFont getBf() {

        return _bf;
    }

    public void setBf(BaseFont _bf) {

        this._bf = _bf;
    }

    public Map<String, Object> getMap() {

        return _map;
    }

    protected float printWrappedText(float x, float y, int columnLength, String phrase) {

        float yWrap = y;

        if (phrase == null) {
            return y;
        }

        String[] words = phrase.split(" ");

        String text = "";
        for (int i = 0; i < words.length; i++) {
            if (text.length() + 1 + words[i].length() <= columnLength) {
                if (text.length() > 0) {
                    text = text.concat(" ");
                }
                text = text.concat(words[i]);
            } else {
                printText(x, yWrap, text);
                text = words[i];
                yWrap += 3;
            }
        }
        if (text.length() > 0) {
            printText(x, yWrap, text);
        }
        return yWrap;
    }

    public int getLastRecordPrinted() {

        return _lastRecordPrinted;
    }

    public int getPageNumber() {

        return _pageNumber;
    }

    public void setLastRecordPrinted(int lastRecordPrinted) {

        _lastRecordPrinted = lastRecordPrinted;
    }

    protected boolean isPageBreak(int rowIndex) {

        return false;
    }

    public void setTemplateFile(String templateFile) {

        _templateFile = templateFile;
    }

    public int getMaxRowsInPage() {

        return _maxRowsInPage;
    }

    public void setMaxRowsInPage(int maxRowsInPage) {

        _maxRowsInPage = maxRowsInPage;
    }

    public void setCoverTemplate(String coverTemplate) {

        _coverTemplate = coverTemplate;
    }

    public void setBackCoverTemplate(String backCoverTemplate) {

        _backCoverTemplate = backCoverTemplate;
    }

    public void setInternalPagesTemplate(String internalPagesTemplate) {

        _internalPagesTemplate = internalPagesTemplate;
    }

    public void setFixedNumberOfPages(int fixedNumberOfPages) {

        _fixedNumberOfPages = fixedNumberOfPages;
    }

    public void setFixedPagesTemplateArray(String[] fixedPagesTemplateArray) {

        _fixedPagesTemplateArray = fixedPagesTemplateArray;
    }

    public String getTemplateDirectory() {

        return _templateDirectory;
    }

}
