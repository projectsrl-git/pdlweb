
package net.projectsrl.pdlweb.pdl.core;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.pdf.FunctionPDF;
import net.projectsrl.webapp.security.WebAppUserSecurityInfo;

public class ModuloPDLPDFRetro extends FunctionPDF {

    private static final String DATASET = "DSPDL";

    private final String        _idPdl;

    public ModuloPDLPDFRetro(String _idPdl) {
        super();
        this._idPdl = _idPdl;
    }

    public String createPDF(String nomeFileInput, String directory, SsbServletRequest req,
            WebAppUserSecurityInfo<?> userInfo) throws AppCrash {

        DataSet_itf dataSet = null;
        
        String nomeFileOutput=null;
        
        PdfReader reader =null;
        PdfStamper stamper =null;

        try {


            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET);
            HashMap<String, String> params = new HashMap<String, String>();
            String whereCondition = " WHERE ID_PDL="+_idPdl+" ";
            params.put("WHERECONDITION", whereCondition);
            params.put(DafneCostanti_itf.WHERECONDITION_AZIENDE, userInfo.getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));
            dataSet.setParam(params);
            dataSet.open();

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
            DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);

            if (!dataSet.hasMoreElements()) {
                return null;
            }
            
            
            Row_itf dbRow = (Row_itf) dataSet.nextElement();
            String nrPDL = (String) dbRow.getField("NR_PDL");
            String nomeSito=(String) dbRow.getField("RAGSOC");
            
            nomeFileOutput ="pdl-"+nrPDL+"-"+nomeSito.replace(" ", "_")+".pdf";
            nomeFileOutput = Normalizer.normalize(nomeFileOutput, Normalizer.Form.NFD);
            nomeFileOutput = nomeFileOutput.replaceAll("[^\\p{ASCII}]", "");
            nomeFileOutput = nomeFileOutput.replaceAll("[^a-zA-Z0-9.-]", "_");      
            nomeFileOutput = directory+nomeFileOutput;
            
            FileOutputStream out = new FileOutputStream(nomeFileOutput);
            reader = new PdfReader(nomeFileInput);
            stamper = new PdfStamper(reader, out);
            
            PdfContentByte cb = stamper.getOverContent(reader.getNumberOfPages());
            //printText(bf, cb, 110, 28,nomeSito );

            dataSet.close();

            stamper.setFormFlattening(true);

            stamper.close();
            reader.close();

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                    
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "Errore nella close dataset");
                }
            }
            
            if (stamper != null) {
                try {
                    stamper.close();
                    
                } catch (Throwable th) {
                    AppCrash ac=new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close stamper");
                }
            }
            
            if (reader != null) {
                try {
                    reader.close();
                    
                } catch (Throwable th) {
                    AppCrash ac=new AppCrash();
                    ac.logContext(this.getClass().getName(), "Errore nella close reader");
                }
            }


        }
        return nomeFileOutput;

    }

    protected void printText(BaseFont bf, PdfContentByte cb, int x, int y, String text) {

        cb.beginText();
        cb.moveText(Utilities.millimetersToPoints(x), 842 - Utilities.millimetersToPoints(y));
        cb.setFontAndSize(bf, 9);
        cb.showText(text);
        cb.endText();
    }

    protected void printNumber(BaseFont bf, PdfContentByte cb, int x, int y, String numerText) {

        cb.beginText();
        cb.moveText(Utilities.millimetersToPoints(x), 842 - Utilities.millimetersToPoints(y));
        cb.setFontAndSize(bf, 9);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                NumberFormat.getCurrencyInstance(Locale.GERMANY).format(Float.parseFloat(numerText)), x, y, 0);
        cb.endText();
    }

}
