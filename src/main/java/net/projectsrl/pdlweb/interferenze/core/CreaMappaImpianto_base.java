
package net.projectsrl.pdlweb.interferenze.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.projectsrl.pdlweb.anagrafiche.db.AreaLavoroDAO;
import net.projectsrl.pdlweb.pdl.db.ImpiantiDAO;

public abstract class CreaMappaImpianto_base {

    private static final String PDF_TEMP_SUFFIX = "_temp";
    public static final String  IMG_PATH        = "/img/";
    private Integer             _idImpianto;

    public CreaMappaImpianto_base(Integer idImpianto) {

        _idImpianto = idImpianto;
    }

    public String creaMappaArea(String applicationRoot, String turno) throws AppCrash {

        String fileNamePlanimetria = "";
        String outputFileName = "";

        try {
            fileNamePlanimetria = getSourceFilenameNoExtension();

            String pdfTempFullPath = createPDF(applicationRoot, fileNamePlanimetria, turno);

            outputFileName = extractImageFromPDF(applicationRoot, fileNamePlanimetria, pdfTempFullPath);

        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(),
                    "_idImpianto:" + _idImpianto + " - fileNamePlanimetria:" + fileNamePlanimetria);
            throw ac;
        }

        return outputFileName;

    }

    protected String getSourceFilenameNoExtension() throws AppCrash {

        String fileNamePlanimetria;
        ImpiantiDAO impianto = new ImpiantiDAO();
        impianto.setAttribute(ImpiantiDAO.ID_IMPIANTO, _idImpianto);
        impianto.retrieve();

        fileNamePlanimetria = (String) impianto.getAttributeAsString(ImpiantiDAO.FILENAME_PLANIMETRIA);
        return fileNamePlanimetria;
    }

    protected String extractImageFromPDF(String applicationRoot, String fileNamePlanimetria, String pdfTempFullPath)
            throws AppCrash {

        String outputFileName = null;

        File outputfile = null;

        try {

            PDDocument documentOut = PDDocument.load(pdfTempFullPath);

            List<PDPage> list = documentOut.getDocumentCatalog().getAllPages();

            ImageIO.scanForPlugins();
            for (PDPage page : list) {
                BufferedImage image = page.convertToImage();
                outputfile = new File(applicationRoot + IMG_PATH + fileNamePlanimetria + ".jpg");
                ImageIO.write(image, "jpg", outputfile);
            }

            documentOut.close();

            File file = new File(pdfTempFullPath);
            Files.deleteIfExists(file.toPath());

            outputFileName = IMG_PATH.substring(1) + outputfile.getName();

        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(),
                    "_idImpianto:" + _idImpianto + " - fileNamePlanimetria:" + fileNamePlanimetria);
            throw ac;
        }

        return outputFileName;
    }

    private String createPDF(String applicationRoot, String fileNamePlanimetria, String turno) throws AppCrash {

        String pdfTempFullPath = null;

        try {

            String sourcePdfFileFullPath = applicationRoot + IMG_PATH + fileNamePlanimetria + ".pdf";
            pdfTempFullPath = applicationRoot + IMG_PATH + fileNamePlanimetria + PDF_TEMP_SUFFIX + ".pdf";

            File sourcePdfFile = new File(sourcePdfFileFullPath);

            if (!sourcePdfFile.exists()) {
                return null;
            }

            // modifico il pdf colorando l'area interessata

            FileOutputStream out = new FileOutputStream(pdfTempFullPath);
            PdfReader reader = new PdfReader(sourcePdfFileFullPath);
            PdfStamper stamper = new PdfStamper(reader, out);

            PdfContentByte cb = stamper.getOverContent(reader.getNumberOfPages());

            riempiMappa(cb, turno);

            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();

        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(),
                    "_idImpianto:" + _idImpianto + " - fileNamePlanimetria:" + fileNamePlanimetria);
            throw ac;
        }

        return pdfTempFullPath;
    }

    protected abstract void riempiMappa(PdfContentByte cb, String turno) throws AppCrash;

    protected void writePdfText(PdfContentByte cb, int yPosition, int xTextPosition, int yTextPosition, int lineSpacing,
            String text) throws AppCrash {

        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            cb.saveState();
            cb.setColorStroke(BaseColor.BLACK);
            cb.setColorFill(BaseColor.BLACK);
            cb.beginText();
            cb.setFontAndSize(bf, 6);
            cb.moveText(xTextPosition, yTextPosition);
            cb.moveTextWithLeading(1, (float) (yPosition * -lineSpacing));
            cb.showText(text);
            cb.endText();
            cb.fill();
            cb.restoreState();

        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            throw ac;
        }

    }
    
    
    protected void writePdfTextTurno(PdfContentByte cb, int yPosition, int xTextPosition, int yTextPosition, int lineSpacing,
            String text, int dimensioneFont) throws AppCrash {

    	
    	
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            cb.saveState();
            cb.setColorStroke(BaseColor.BLACK);
            cb.setColorFill(BaseColor.BLACK);
            cb.beginText();
            cb.setFontAndSize(bf, dimensioneFont);
            cb.moveText(xTextPosition, yTextPosition);
            cb.moveTextWithLeading(1, (float) (yPosition * -lineSpacing));
            cb.showText(text);
            cb.endText();
            cb.fill();
            cb.restoreState();

        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            throw ac;
        }

    }

    protected void coloraArea(PdfContentByte cb, int rectX, int rectY, int rectW, int rectH, String colore) throws AppCrash {

    	BaseColor myColor = WebColors.getRGBColor(colore);
    	
    	//BaseColor myColor = WebColors.getRGBColor("#ff3f3f");
    	//ff3f3f - rosso
    	//ff853f - arancio - preso da viste 
    	
        cb.saveState();
        //cb.setColorStroke(BaseColor.RED);
        //cb.setColorFill(BaseColor.RED);
        cb.setColorStroke(myColor);
        cb.setColorFill(myColor);
        cb.rectangle(rectX, rectY, rectW, rectH);
        cb.fill();
        cb.restoreState();

    }
    
    
    protected void coloraTutteLeAree(PdfContentByte cb, int rectX, int rectY, int rectW, int rectH, String colore, String idImpianto) throws AppCrash {

    	//BaseColor myColor = WebColors.getRGBColor("#ff3f3f");
    	BaseColor myColor = WebColors.getRGBColor(colore);
    	
    	//BaseColor myColor = WebColors.getRGBColor("#ff3f3f");
    	//ff3f3f - rosso
    	//ff853f - arancio - preso da viste 
    	
    	
    	DataSet_itf dataSet = null;
        dataSet = DataSetFactory.getInstance().makeDataSet("", "DSAreaLavoro");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("WHERECONDITION", "WHERE ID_IMPIANTO="+idImpianto);
        dataSet.setParam(params);
        dataSet.open();

        while (dataSet.hasMoreElements()) {
            Row_itf dbRow = (Row_itf) dataSet.nextElement();

            if (dbRow.getField(AreaLavoroDAO.RECT_X) != null && dbRow.getField(AreaLavoroDAO.RECT_X) != null
                    && dbRow.getField(AreaLavoroDAO.RECT_W) != null
                    && dbRow.getField(AreaLavoroDAO.RECT_H) != null) {

                rectX = (Integer) dbRow.getField(AreaLavoroDAO.RECT_X);

                rectY = (Integer) dbRow.getField(AreaLavoroDAO.RECT_Y);

                rectW = (Integer) dbRow.getField(AreaLavoroDAO.RECT_W);

                rectH = (Integer) dbRow.getField(AreaLavoroDAO.RECT_H);
                
                cb.saveState();
                cb.setColorStroke(myColor);
                cb.setColorFill(myColor);
                cb.rectangle(rectX, rectY, rectW, rectH);
                cb.fill();
                cb.restoreState();
            }

        }

    }


    public Integer getIdImpianto() {

        return _idImpianto;
    }

}
