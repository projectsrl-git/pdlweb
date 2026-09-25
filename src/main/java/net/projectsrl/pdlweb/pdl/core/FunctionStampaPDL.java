
package net.projectsrl.pdlweb.pdl.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.pdf.CreatePDF;
import net.projectsrl.pdlweb.pdl.db.PDLDAO;
import net.projectsrl.pdlweb.workbook.CreateLIMOD120;
import net.projectsrl.pdlweb.workbook.CreateLIMOD26;
import net.projectsrl.pdlweb.workbook.CreateLIMOD32;
import net.projectsrl.pdlweb.workbook.CreateLIMOD47;
import net.projectsrl.pdlweb.workbook.CreateLIMOD52;
import net.projectsrl.pdlweb.workbook.CreateLIMOD71Fronte;
import net.projectsrl.pdlweb.workbook.CreateLIMOD71Retro;
import net.projectsrl.pdlweb.workbook.CreateLIMOD73;
import net.projectsrl.pdlweb.workbook.CreatePPMOD01;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;

public class FunctionStampaPDL extends FunctionProjectWebApp_base {

    private static final String DATASET = "DSPDLTemp";


    public FunctionStampaPDL(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);

    }

    @Override
    public boolean isAuthenticationRequired() {

        return false;
    }

    @Override
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        String directory = _applicationSrv.getRoot() + "/Output/";
        String filename = creaFilePdf(req, userInfo);

        File file = new File(filename);
        String contentType = getContentType(filename);
        System.out.println(contentType);
        res.setContentType(contentType);
        res.setHeader("Content-Disposition", "attachment; filename=" + filename.replace(directory, ""));
        int length = (int) file.length();

        if (length > Integer.MAX_VALUE) {
        }

        byte[] bytes = new byte[length];

        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);

            fin.read(bytes);

            ServletOutputStream os = res.getOutputStream();
            os.write(bytes);
            os.flush();
        } catch (Throwable ac) {
            new AppCrash(ac);
        }

    }

    protected String getContentType(String fileName) {

        String extension[] = { // File Extensions
                "txt", // 0 - plain text
                "htm", // 1 - hypertext
                "jpg", // 2 - JPEG image
                "png", // 2 - JPEG image
                "gif", // 3 - gif image
                "pdf", // 4 - adobe pdf
                "doc", // 5 - Microsoft Word
                "docx", }; // you can add more
        String mimeType[] = { // mime types
                "text/plain", // 0 - plain text
                "text/html", // 1 - hypertext
                "image/jpg", // 2 - image
                "image/jpg", // 2 - image
                "image/gif", // 3 - image
                "application/pdf", // 4 - Adobe pdf
                "application/msword", // 5 - Microsoft Word
                "application/msword", // 5 - Microsoft Word
        }, // you can add more
                contentType = "text/html"; // default type
        // dot + file extension
        int dotPosition = fileName.lastIndexOf('.');
        // get file extension
        String fileExtension = fileName.substring(dotPosition + 1);
        // match mime type to extension
        for (int index = 0; index < mimeType.length; index++) {
            if (fileExtension.equalsIgnoreCase(extension[index])) {
                contentType = mimeType[index];
                break;
            }
        }
        return contentType;
    }

    protected String creaFilePdf(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {

        String nomeFileCompleto = null;

        String formato = req.getField("FORMATO_STAMPA");
        String idPdl = req.getField("ID_PDL");
        String revisione = req.getField("REVISIONE");
        String modulo = req.getField("MODULO");
        String lingua = req.getField("LINGUA_STAMPA");
        String stampaDataOggi= req.getField("DATA_OGGI");
        
        Map<String, Object> data = setData(idPdl, userInfo);
        data.put("DATA_OGGI", stampaDataOggi);

        try {
            String directory = _applicationSrv.getRoot() + "/Output/";

            if (formato.equals("A3")) {

                if (lingua.equals("EN")){
                	if(revisione.equals("10")){
                		CreatePDF pdf = new CreateLIMOD25FrontPDFEnRev9(directory, data);
                		nomeFileCompleto = pdf.createPDF();
                	}else{
                		CreatePDF pdf = new CreateLIMOD25FrontPDFEn(directory, data);
                		nomeFileCompleto = pdf.createPDF();
                	}	
                }else{
                	if(revisione.equals("10")){
                		CreatePDF pdf = new CreateLIMOD25FrontPDFRev9(directory, data);
                		nomeFileCompleto = pdf.createPDF();
                	}else{
                		CreatePDF pdf = new CreateLIMOD25FrontPDF(directory, data);
                		nomeFileCompleto = pdf.createPDF();
                	}
                	
                }
                

                
                String nomeFileCompletoTemporaneo = nomeFileCompleto + "_TEMP.pdf";
                String nomeFileCompletoFronte = nomeFileCompleto + "fronte.pdf";
                copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoFronte));
                new File(nomeFileCompleto).delete();

                if (lingua.equals("EN")){
                	if(revisione.equals("10")){
                		CreatePDF pdf2 = new CreateLIMOD25RearPDFEnRev9(directory, data);
                        pdf2.createPDF();
                	}else{
                		CreatePDF pdf2 = new CreateLIMOD25RearPDFEn(directory, data);
                        pdf2.createPDF();
                	}
                	
                }else{
                	if(revisione.equals("10")){
                		CreatePDF pdf2 = new CreateLIMOD25RearPDFRev9(directory, data);
                        pdf2.createPDF();
                	}else{
                		CreatePDF pdf2 = new CreateLIMOD25RearPDF(directory, data);
                        pdf2.createPDF();
                	}
                	
                }
                
                String nomeFileCompletoRetro = nomeFileCompleto + "retro.pdf";
                copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoRetro));
                new File(nomeFileCompleto).delete();

                PdfReader readerPage1 = new PdfReader(nomeFileCompletoFronte);
                PdfReader readerPage2 = new PdfReader(nomeFileCompletoRetro);
                PdfStamper stamper = new PdfStamper(readerPage2, new FileOutputStream(nomeFileCompleto + "_TEMP.pdf"));

                stamper.insertPage(1, readerPage1.getPageSizeWithRotation(1)); // dice di scrivere su nuova pagina

                PdfContentByte page1 = stamper.getOverContent(1); // queste 3 righe aggiungono una nuova pagina
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
                    } else {
                        canvas.addTemplate(pageNew, a4_width, 0);
                        document.newPage();
                    }
                }
                // step 5
                document.close();
                reader.close();
                new File(nomeFileCompletoTemporaneo).delete();

            } else {
                
                if (modulo.equals("")) {
                	
                	if (lingua.equals("EN")){
                		if(revisione.equals("10")){
                			CreatePDF pdf = new CreateLIMOD25FrontPDFEnRev9(directory, data);
                            nomeFileCompleto = pdf.createPDF();
                		}else{
                			CreatePDF pdf = new CreateLIMOD25FrontPDFEn(directory, data);
                            nomeFileCompleto = pdf.createPDF();
                		}
                		
                	}else{
                		if(revisione.equals("10")){
                			CreatePDF pdf = new CreateLIMOD25FrontPDFRev9(directory, data);
                            nomeFileCompleto = pdf.createPDF();
                		}else{
                			CreatePDF pdf = new CreateLIMOD25FrontPDF(directory, data);
                            nomeFileCompleto = pdf.createPDF();
                		}
                		
                	}
                	
                    String nomeFileCompletoFronte = nomeFileCompleto + "fronte.pdf";
                    copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoFronte));

                    if (lingua.equals("EN")){
                    	if(revisione.equals("10")){
                    		CreatePDF pdf2 = new CreateLIMOD25RearPDFEnRev9(directory, data);
                            pdf2.createPDF();
                    	}else{
                    		CreatePDF pdf2 = new CreateLIMOD25RearPDFEn(directory, data);
                            pdf2.createPDF();
                		}
                    	
                    }else{
                    	if(revisione.equals("10")){
                    		CreatePDF pdf2 = new CreateLIMOD25RearPDFRev9(directory, data);
                            pdf2.createPDF();
                    	}else{
                    		CreatePDF pdf2 = new CreateLIMOD25RearPDF(directory, data);
                            pdf2.createPDF();
                		}
                    	
                    }
                    
                    String nomeFileCompletoRetro = nomeFileCompleto + "retro.pdf";
                    copyFile(new File(nomeFileCompleto), new File(nomeFileCompletoRetro));

                    PdfReader cover = new PdfReader(nomeFileCompletoFronte);
                    PdfReader reader = new PdfReader(nomeFileCompletoRetro);
                    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(nomeFileCompleto));
                    stamper.insertPage(1, cover.getPageSizeWithRotation(1));
                    PdfContentByte page1 = stamper.getOverContent(1);
                    PdfImportedPage page = stamper.getImportedPage(cover, 1);
                    page1.addTemplate(page, 0, 0);
                    stamper.close();
                    cover.close();
                    reader.close();
                } else {
					CreatePDF pdf;
					switch (modulo) {
					case "LIMOD26":
						pdf = new CreateLIMOD26(directory, data);
						nomeFileCompleto = pdf.createPDF();
						break;
					case "LIMOD32":
						pdf = new CreateLIMOD32(directory + modulo + ".pdf", directory, data);
						nomeFileCompleto = pdf.createPDF();
						break;
					case "LIMOD47":
						pdf = new CreateLIMOD47(directory + modulo + ".pdf", directory, data);
						nomeFileCompleto = pdf.createPDF();
						break;
					case "LIMOD52":
						pdf = new CreateLIMOD52(directory, data);
						nomeFileCompleto = pdf.createPDF();
						break;
					case "LIMOD71":
						pdf = new CreateLIMOD71Fronte(directory + "LIMOD71Fronte.pdf", directory, data);

						nomeFileCompleto = pdf.createPDF();
						String nomeFileCompletoFronte = nomeFileCompleto + "fronte.pdf";
						new File(nomeFileCompleto).renameTo(new File(nomeFileCompletoFronte));

						CreatePDF pdf2 = new CreateLIMOD71Retro(directory + "LIMOD71Retro.pdf", directory, data);
						pdf2.createPDF();
						String nomeFileCompletoRetro = nomeFileCompleto + "retro.pdf";
						new File(nomeFileCompleto).renameTo(new File(nomeFileCompletoRetro));

						PdfReader cover = new PdfReader(nomeFileCompletoFronte);
						PdfReader reader = new PdfReader(nomeFileCompletoRetro);
						PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(nomeFileCompleto));
						stamper.insertPage(1, cover.getPageSizeWithRotation(1));
						PdfContentByte page1 = stamper.getOverContent(1);
						PdfImportedPage page = stamper.getImportedPage(cover, 1);
						page1.addTemplate(page, 0, 0);
						stamper.close();
						cover.close();
						reader.close();
						break;
					case "LIMOD73":
						pdf = new CreateLIMOD73(directory + modulo + ".pdf", directory, data);
						nomeFileCompleto = pdf.createPDF();
						break;
					case "LIMOD120":
						pdf = new CreateLIMOD120(directory, data);
						nomeFileCompleto = pdf.createPDF();
						break;
					case "PPMOD01":
						pdf = new CreatePPMOD01(directory, data);
						nomeFileCompleto = pdf.createPDF();
						break;
					default:
						throw new IllegalArgumentException("Modulo non valido: " + modulo);
					}

				}

			}
		} catch (Throwable e) {
			throw new AppCrash(e);
		}
                

        return nomeFileCompleto;

    }

    protected Map<String, Object> setData(String idPdl, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> data = new HashMap<String, Object>();

        DataSet_itf dataSet = null;

        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET);
            HashMap<String, String> params = new HashMap<String, String>();
            String whereCondition = " WHERE ID_PDL=" + idPdl + " ";
            params.put("WHERECONDITION", whereCondition);
            params.put(DafneCostanti_itf.WHERECONDITION_AZIENDE,
                    getSpecificUserInfo(userInfo).getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));
            dataSet.setParam(params);
            dataSet.open();

            if (!dataSet.hasMoreElements()) {
                return null;
            }

            Row_itf dbRow = (Row_itf) dataSet.nextElement();

            fillMapFromRow(data, dbRow);

            dataSet.close();

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

        }
        return data;
    }

    private void fillMapFromRow(Map<String, Object> data, Row_itf dbRow) throws AppCrash {

        PDLDAO dao = new PDLDAO();
        dao.putFieldInMapFromRow(data, dbRow, "RAGSOC");
        dao.putFieldInMapFromRow(data, dbRow, "DESCR_AREA");
        dao.putFieldInMapFromRow(data, dbRow, "DESCR_EQUIPMENT");
        dao.putFieldInMapFromRow(data, dbRow, "MODULO_INTERFERENZE");
        
        dao.putFieldInMapFromRow(data, dbRow, "NR_PDL_VERIFICATI_PREVENTIVI");
        
        dao.putFieldInMapFromRow(data, dbRow, "NC_DL_AL_1");
        dao.putFieldInMapFromRow(data, dbRow, "NC_DL_AL_2");
        dao.putFieldInMapFromRow(data, dbRow, "NC_DL_AL_P1");
        dao.putFieldInMapFromRow(data, dbRow, "NC_DL_AL_P2");
        
        dao.putFieldInMapFromRow(data, dbRow, "NOMINATIVO_RICHIEDENTE");
        dao.putFieldInMapFromRow(data, dbRow, "SEZ2_RISCHI_SPECIFICI_NOMINATIVO");
        
        dao.setMapFromRow(data, dbRow);
    }

    protected void copyFile(File sourceFile, File destFile) throws IOException {

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

