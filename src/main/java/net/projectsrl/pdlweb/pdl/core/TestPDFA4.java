
package net.projectsrl.pdlweb.pdl.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import net.project.errors.AppCrash;
import net.projectsrl.pdf.CreatePDF;

public class TestPDFA4 {

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
            String nomeFileCompletoFronte = nomeFileCompleto + "fronte.pdf";
            copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoFronte));
            new File(nomeFileCompleto).delete();

            CreatePDF pdf2 = new CreateLIMOD25RearPDF(directory, data);
            pdf2.setTest(true);
            pdf2.createPDF();
            String nomeFileCompletoRetro = nomeFileCompleto + "retro.pdf";
            copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoRetro));
            new File(nomeFileCompleto).delete();


            
            PdfReader readerPage1 = new PdfReader(nomeFileCompletoFronte);
            PdfReader readerPage2 = new PdfReader(nomeFileCompletoRetro);

            
            
            // accodare 2 pagine - presente anche su FunctionCreaPDFCedolino di Dafne
            Document document = new Document();
            
            
        	PdfCopy writer = new PdfCopy(document, new FileOutputStream(nomeFileCompleto));
            document.open();
            PdfImportedPage page1;
            PdfImportedPage page2;
        	
        	page1 = writer.getImportedPage(readerPage1, 1);
        	
        	page2 = writer.getImportedPage(readerPage2, 1);
        	

			writer.addPage(page1);
			writer.addPage(page2);
			
            document.close();
            writer.close();
            
            // accodare 2 pagine - fine

            
            

            new File(nomeFileCompletoFronte + "rotate.pdf").delete();
            new File(nomeFileCompletoRetro + "rotate.pdf").delete();
            

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
