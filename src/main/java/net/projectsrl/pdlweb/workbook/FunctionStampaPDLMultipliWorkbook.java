
package net.projectsrl.pdlweb.workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdf.CreatePDF;
import net.projectsrl.pdlweb.pdl.core.FunctionStampaPDL;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.wm.utils.Utils;

public class FunctionStampaPDLMultipliWorkbook extends FunctionStampaPDL {

    public FunctionStampaPDLMultipliWorkbook(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);

    }

    @Override
    public boolean isAuthenticationRequired() {

        return false;
    }

    @Override
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        String directory = _applicationSrv.getRoot() + "/Output/";

        String elencoPdl = req.getField("ID_MODULO_STAMPA");
        String zipName = _applicationSrv.getRoot() + "/Output/Raccolta_Workbook_Pdl_"+Utils.getStringDataOggiTrattino()+".zip";
        
        String filename =null;
		String[] strArray = elencoPdl.split(",");
		ZipOutputStream out;
		FileInputStream in = null;
		String nomeFile=null;
		String nomeFileSingolo=null;
		
		
		try {
			out = new ZipOutputStream(new FileOutputStream(zipName));
		
		
		for (String idSingoloPdl : strArray) {
			req.setField("ID_PDL", idSingoloPdl);
			filename = creaFilePdf(req, userInfo);
			
			int countPDF = StringUtils.countMatches(filename, ",")+1;
			
			String[] ary = filename.split(",");
			for (int i=0;i<countPDF;i++){
				
				nomeFileSingolo=ary[i].replace("[", "").replace("]", "").trim();
				// Nome del file dentro lo zip: prima si assumeva che ogni nome file contenesse
				// sempre la sottostringa "LIMOD" (usata per togliere il percorso), ma questo non e'
				// vero per i moduli con nome diverso (es. PPMOD01), causando un
				// StringIndexOutOfBoundsException. Si usa invece semplicemente il nome del file,
				// senza il percorso, valido per qualunque modulo.
				nomeFile = new File(nomeFileSingolo).getName();
				
//				nomeFile = nomeFileSingolo.substring(nomeFileSingolo.lastIndexOf("LIMOD"));
				
				in = new FileInputStream(nomeFileSingolo);
				out.putNextEntry(new ZipEntry(nomeFile));
				byte[] b = new byte[1024];

				int count;

				while ((count = in.read(b)) > 0) {
					System.out.println();

					out.write(b, 0, count);
				}
			}
			
		}
		
		out.close();
		in.close();
		
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
        

        File file = new File(zipName);
        
        String contentType = getContentType(zipName);
        System.out.println(contentType);
        res.setContentType(contentType);
        res.setHeader("Content-Disposition", "attachment; filename=" + zipName.replace(directory, ""));
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
            os.close();
            fin.close();
           
           
        } catch (Throwable ac) {
            new AppCrash(ac);
        }
        
        if(file.exists()) { 
        	file.delete();
        }
        
        
    }
    
    
    
    @Override
    protected String creaFilePdf(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {

        String nomeFileCompleto = null;

        String formato = req.getField("FORMATO_STAMPA");
        String idPdl = req.getField("ID_PDL");
        String modulo = req.getField("MODULO");
        String lingua = req.getField("LINGUA_STAMPA");
        
        
        List<String> arrayNomeFileCompleto = new ArrayList<String>();
        
        
        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();

        try {
            dataSet = dsFactory.makeDataSet("", "DSPDLWorkbookStampaMultipla");

            HashMap<String, String> param = new HashMap<String, String>();
            param.put(WebAppConstants_itf.WHERECONDITION, "WHERE ID_PDL=" + idPdl);
            dataSet.setParam(param);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if ((Boolean) dbRow.getField("FLG_MESSA_IN_SICUREZZA_ELETTRICA")){
                	modulo="LIMOD26";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                if ((Boolean) dbRow.getField("FLG_RIMOZIONE_TEMPORANEA_EIS")){
                	modulo="LIMOD32";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                if ((Boolean) dbRow.getField("FLG_CONSEGNA_VERIFICA_ACCETTAZIONE")){
                	modulo="LIMOD47";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                if ((Boolean) dbRow.getField("FLG_PERMESSO_DI_ACCESSO")){
                	modulo="LIMOD52";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                if ((Boolean) dbRow.getField("FLG_CIECATURA")){
                	modulo="LIMOD71";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                if ((Boolean) dbRow.getField("FLG_LOTO")){
                	modulo="LIMOD73";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                if ((Boolean) dbRow.getField("FLG_PERMESSO_ELETTRICO")){
                	modulo="LIMOD120";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                if (Boolean.TRUE.equals(dbRow.getField("FLG_IN_QUOTA"))
                		|| Boolean.TRUE.equals(dbRow.getField("FLG_SCAVO"))
                		|| Boolean.TRUE.equals(dbRow.getField("FLG_V9_PDL_A_CALDO"))){
                	// PPMOD01 e' un unico modulo che raggruppa 3 sezioni (quota/scavo/caldo): viene
                	// generato un solo PDF (con le sole sezioni richieste dai flag, unite se sono
                	// piu' di una) anche in questa stampa multipla/zip - vedi CreatePPMOD01.
                	modulo="PPMOD01";
                	nomeFileCompleto = creaPDFSingolo(userInfo, nomeFileCompleto, formato, idPdl, modulo, lingua);
                	arrayNomeFileCompleto.add(nomeFileCompleto);
                }
                 
                
                
                
                
                
            }
        } catch (AppCrash ac) {
            ac.logContext(this.getClass().getName(),
                    "Errore nella ricerca dell'ultimo progressivo del dataset DSPDLWorkbookStampaMultipla");
            throw ac;
        } finally {
            // chiude il dataset per il conteggio degli elementi trovati
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset DSPDLWorkbookStampaMultipla");
                }
            }
        }
        
        
        
        
        
       nomeFileCompleto=arrayNomeFileCompleto.toString(); 
        
                

        return nomeFileCompleto;

    }

	private String creaPDFSingolo(UserSecurityInfo userInfo, String nomeFileCompleto, String formato, String idPdl,
			String modulo, String lingua) throws AppCrash {
		try {
            String directory = _applicationSrv.getRoot() + "/Output/";
            String prefisso="workbook";

            
                Map<String, Object> data = setData(idPdl, userInfo);
               
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

				

			
		} catch (Throwable e) {
			throw new AppCrash(e);
		}
		return nomeFileCompleto;
	}

   

}
