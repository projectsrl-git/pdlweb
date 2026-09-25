
package net.projectsrl.pdlweb.pdl.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;

public class TestPDF {

    public static void main(String[] args) {

        Map<String, Object> data = new HashMap<String, Object>();
        // mi servono per il nome file
        data.put("NR_PDL", "0001-18");
        data.put("RAGSOC", "Sito di Limito");
        String directory = "D:/works/";

        try {

            CreatePDF pdf = new CreateLIMOD25FrontPDF(directory, data);
            pdf.setTest(true);

            String nomeFileCompleto = pdf.createPDF();
            String nomeFileCompletoTemporaneo=nomeFileCompleto+"_TEMP.pdf";
            String nomeFileCompletoFronte = nomeFileCompleto + "fronte.pdf";
            copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoFronte));
            new File(nomeFileCompleto).delete();

            CreatePDF pdf2 = new CreateLIMOD25RearPDF(directory, data);
            pdf2.setTest(true);
            pdf2.createPDF();
            String nomeFileCompletoRetro = nomeFileCompleto + "retro.pdf";
            copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoRetro));
            new File(nomeFileCompleto).delete();

            //rotate(nomeFileCompletoFronte);
            //new File(nomeFileCompletoFronte).delete();
            //rotate(nomeFileCompletoRetro);
            //new File(nomeFileCompletoRetro).delete();

            //PdfReader readerPage1 = new PdfReader(nomeFileCompletoFronte + "rotate.pdf");
            //PdfReader readerPage2 = new PdfReader(nomeFileCompletoRetro + "rotate.pdf");
            
            PdfReader readerPage1 = new PdfReader(nomeFileCompletoFronte);
            PdfReader readerPage2 = new PdfReader(nomeFileCompletoRetro);
            PdfStamper stamper = new PdfStamper(readerPage2, new FileOutputStream(nomeFileCompletoTemporaneo));

            
            stamper.insertPage(1, readerPage1.getPageSizeWithRotation(1)); // dice di scrivere su nuova pagina
            
            PdfContentByte page1 = stamper.getOverContent(1);				// queste 3 righe aggiungono una nuova pagina
            PdfImportedPage page = stamper.getImportedPage(readerPage1, 1);
            page1.addTemplate(page, 0, 0);
            
            

            stamper.close();
            readerPage1.close();
            readerPage2.close();

            new File(nomeFileCompletoFronte + "rotate.pdf").delete();
            new File(nomeFileCompletoRetro + "rotate.pdf").delete();
            
            
            
            PdfReader reader = new PdfReader(nomeFileCompletoTemporaneo);
    	    // step 1
    	    Document document = new Document(PageSize.A3.rotate());
    	    // step 2
    	    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeFileCompleto));
    	    // step 3
    	    document.open();
    	    // step 4
    	    PdfContentByte canvas = writer.getDirectContent();
    	    float a4_width = PageSize.A4.getWidth();
    	    int n = reader.getNumberOfPages();
    	    int p = 0;
    	    PdfImportedPage pageNew;
    	    while (p++ < n) {
    	    	pageNew = writer.getImportedPage(reader, p);
    	        if (p % 2 == 1) {
    	            canvas.addTemplate(pageNew, 0, 0);
    	        }
    	        else {
    	            canvas.addTemplate(pageNew, a4_width, 0);
    	            document.newPage();
    	        }
    	    }
    	    // step 5
    	    document.close();
    	    reader.close();
    	    new File(nomeFileCompletoTemporaneo).delete();
    	    

            System.out.println("terminato---------------------------------");
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void rotate(String src) throws AppCrash {

        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(src);
            int n = reader.getNumberOfPages();
            PdfDictionary page;
            PdfNumber rotate;
            for (int p = 1; p <= n; p++) {
                page = reader.getPageN(p);
                rotate = page.getAsNumber(PdfName.ROTATE);
                if (rotate == null) {
                    page.put(PdfName.ROTATE, new PdfNumber(90));
                } else {
                    page.put(PdfName.ROTATE, new PdfNumber((rotate.intValue() + 90) % 360));
                }
            }

            // Once this is done, we use a PdfStamper to persist the change:

            stamper = new PdfStamper(reader, new FileOutputStream(src + "rotate.pdf"));
            stamper.close();
            reader.close();
        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();

                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    throw ac;
                }
            }

            if (reader != null) {
                try {
                    reader.close();

                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    throw ac;
                }
            }

        }

    }

    private static void copyFile(File sourceFile, File destFile) throws IOException {

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

}
