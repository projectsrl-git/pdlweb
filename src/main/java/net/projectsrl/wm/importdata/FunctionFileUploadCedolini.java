package net.projectsrl.wm.importdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.wm.db.CaricaCedoliniDAO;
import net.projectsrl.wm.utils.Utils;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * FunctionFileUpload
 * 
 */
public class FunctionFileUploadCedolini extends FunctionProjectWebApp_base {

	private static final String PAGE = "importcedolini";
	private static final int _sizeMax = 100000000;
	private static final String DATASET_RIGA_FILE = "DataSetRigaElencoFile";

	public FunctionFileUploadCedolini() {

		super();
	}

	public FunctionFileUploadCedolini(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}

	public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		Map<String, Object> templateData = createMapFromRequest(req, userInfo);
		

		templateData.put(UploadedFiles.FILE_TYPE, req.getField(UploadedFiles.FILE_TYPE));
		templateData.put("MESSAGGIO_ATTESA", "Trasferimento file in corso...");

		_applicationSrv.displayPage(PAGE, templateData, res);
	}

	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		HttpSession session = req.getSession(false);
		Map<String, Object> templateData = createMapFromRequest(req, userInfo);
		
		req.getSession(false).setAttribute("CEDOLINI_CREATI", 0);

		uploadFiles(req);
		String nomeFileImportato = _applicationSrv.getRoot() + "Import/" + (String) session.getAttribute("FILE_NAME_CEDOLINO");

		if (!nomeFileImportato.toLowerCase().endsWith(".pdf")) {
			templateData.put("CARICATO", "NO");
			templateData.put("FILE1", 0);
			templateData.put("CEDOLINI_CREATI", 0);
			templateData.put("AZIENDA_CEDOLINI_CREATI", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI"));
			templateData.put("AZIENDA_CEDOLINI_CREATI_CODICE", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI_CODICE"));
			templateData.put("AZIENDA_CEDOLINI_CREATI_ANNO", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI_ANNO"));
			_applicationSrv.displayPage(PAGE, templateData, res);
			return;
		}

		try {
			DataSet_itf riepilogo = dividiLULinSingoliCedolini(req);

			templateData.put("ELENCO_RIEPILOGO", riepilogo);
			templateData.put("CARICATO", "SI");
			templateData.put("FILE1", 0);
			templateData.put("CEDOLINI_CREATI", req.getSession(false).getAttribute("CEDOLINI_CREATI"));
			templateData.put("AZIENDA_CEDOLINI_CREATI", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI"));
			templateData.put("AZIENDA_CEDOLINI_CREATI_CODICE", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI_CODICE"));
			templateData.put("AZIENDA_CEDOLINI_CREATI_ANNO", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI_ANNO"));

		} catch (Throwable th) {

			templateData.put("CARICATO", "NO");
			templateData.put("FILE1", 0);
			templateData.put("CEDOLINI_CREATI", 0);
			templateData.put("AZIENDA_CEDOLINI_CREATI", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI"));
			templateData.put("AZIENDA_CEDOLINI_CREATI_CODICE", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI_CODICE"));
			templateData.put("AZIENDA_CEDOLINI_CREATI_ANNO", req.getSession(false).getAttribute("AZIENDA_CEDOLINI_CREATI_ANNO"));

			AppCrash ac = new AppCrash();
			ac.logContext(this.getClass().getName(), "errore nella creazione dei cedolini ");
		}
		_applicationSrv.displayPage(PAGE, templateData, res);
	}

	@SuppressWarnings("deprecation")
	private boolean uploadFiles(SsbServletRequest req) {

		String fileType = req.getField(UploadedFiles.FILE_TYPE);

		String fileName = "";
		DiskFileUpload fu = new DiskFileUpload();
		// If file size exceeds, a FileUploadException will be thrown
		fu.setSizeMax(_sizeMax);
		try {
			List<FileItem> fileItems = fu.parseRequest(req);
			Iterator<FileItem> itr = fileItems.iterator();

			// ciclo per i file
			while (itr.hasNext()) {
				FileItem fi = itr.next();

				// Check if not form field so as to only handle the file inputs
				// else condition handles the submit button input
				if (!fi.isFormField()) {
					fileName = fi.getName();

					int positionOfLastSlash = fileName.lastIndexOf("\\");
					fileName = fileName.substring(positionOfLastSlash + 1);
					req.getSession(false).setAttribute("FILE_NAME_CEDOLINO", fileName);
					
					
					String dirCedolino= _applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.upload.cedolini");;
					File fNew = new File(dirCedolino, fileName);
                    
					/*UploadedFiles.setStatus(fileType, UploadedFiles.UPLOADING, fileName, getSessionUser(req));*/

					fi.write(fNew);

					/*UploadedFiles.setStatus(fileType, UploadedFiles.UPLOAD_COMPLETED, fileName, getSessionUser(req));*/
				}
			}

			return true;

		} catch (Throwable e) {
			new AppCrash(e);
			/*UploadedFiles.setStatus(fileType, UploadedFiles.UPLOAD_ERROR, fileName, getSessionUser(req));*/
			return false;
		}

	}

	private DataSet_itf dividiLULinSingoliCedolini(SsbServletRequest req) throws AppCrash {

		HttpSession session = req.getSession(true);

		try {

			String nomeFileCedolinoDaSessione = (String) session.getAttribute("FILE_NAME_CEDOLINO");

			String cartellaCedolini = _applicationSrv.getRoot() + Config.GetInstance().getProperty("cartella.cedolini");

			String suffisso = spostaLULCaricato(nomeFileCedolinoDaSessione, cartellaCedolini);

			// splittare pfd - inizio
			String inFile = cartellaCedolini.replace("/", "\\\\") + "CEDOLINO" + suffisso + ".pdf";
			PdfReader reader = new PdfReader(inFile);
			int numberOfPages = reader.getNumberOfPages() + 1;

			int pageNumber = 1;
			String str = PdfTextExtractor.getTextFromPage(reader, pageNumber);
			
			String meseAnno = scriviMeseAnno(str);
			String mese = meseAnno.substring(1, 3);
			String anno = meseAnno.substring(3);

			if (str.contains("Anticipazione T.F.R.")){
                mese=mese+"TFR_A";
            }
			
			String azienda = "";
			if (str.contains("Codice dipendente")) {
				azienda = str.substring(str.indexOf("VIDIMAZIONE") + 12, str.indexOf("VIDIMAZIONE") + 23).trim();
			} else {

				if (str.contains("Assunto") || str.contains("Cessato")) {
					azienda = str.substring(str.indexOf("INDIRIZZO") + 35, str.indexOf("INDIRIZZO") + 69).trim();
				} else {
					azienda = str.substring(str.indexOf("INDIRIZZO") + 12, str.indexOf("INDIRIZZO") + 69).trim();
				}
			}
			
			
			req.getSession(false).setAttribute("AZIENDA_CEDOLINI_CREATI", azienda + " - " + getNomeAzienda(azienda));
			req.getSession(false).setAttribute("AZIENDA_CEDOLINI_CREATI_CODICE", azienda);
			req.getSession(false).setAttribute("AZIENDA_CEDOLINI_CREATI_ANNO", anno);
			String dirAZIENDA = inFile.substring(0, inFile.indexOf(".pdf") - 28) + azienda + "/";
			new File(dirAZIENDA).mkdir();

			File cedoliniCEDOLINO = new File(cartellaCedolini + "CEDOLINO" + suffisso + ".pdf");
			File newCEDOLINO = new File(dirAZIENDA + "RIEMEN00000/" + "RIEMEN00000" + meseAnno + ".pdf");
			FileUtils.copyFile(cedoliniCEDOLINO, newCEDOLINO);

			cancellaPossibiliDoppioniPDF(req, dirAZIENDA, meseAnno);

			net.projectsrl.wm.utils.WMUtils.executeQuery(" DELETE FROM CARICA_CEDOLINI ");
			
			creaRigaDBLink(azienda, anno, mese, dirAZIENDA, "RIEMEN00000", "");

			creaSingoliCedolini(reader, numberOfPages, pageNumber, meseAnno, mese, anno, azienda, dirAZIENDA);

			reader.close();

			String meseControllo=mese;
			if (meseAnno.endsWith("A")){
			    meseControllo=mese+"A";
			}
			meseControllo=meseControllo.replace("TFR_A", "");

		    anno=anno.replace("A", "");
            
			ControllaNomeContenutoNelFile check1 = new ControllaNomeContenutoNelFile(meseControllo, anno, azienda);
			check1.check();
			ControllaLinkNomeDipendenteDaTabellaAPDF check2 = new ControllaLinkNomeDipendenteDaTabellaAPDF(meseControllo, anno, azienda);
			check2.check();
			ControllaNomeDirDaDirATabella check3 = new ControllaNomeDirDaDirATabella(meseControllo, anno, azienda);
			check3.check();

			cancellaPossibiliDoppioniPDF(req, dirAZIENDA, anno);
			riepilogoAnnualePDF(req, dirAZIENDA, anno);

			cedoliniCEDOLINO.delete();
			// x splittare pfd - fine

			return riepilogoControlloCedolini(azienda, anno, mese);

		} catch (Throwable e) {
			AppCrash ac = new AppCrash();
			ac.logContext(this.getClass().getName(), "errore nella creazione dei cedolini");
			throw ac;
		}

	}

	private void creaSingoliCedolini(PdfReader reader, int numberOfPages, int pageNumber, String meseAnno, String mese,
			String anno, String azienda, String dirAZIENDA) throws IOException, DocumentException, FileNotFoundException,
			BadPdfFormatException, AppCrash {

		String str;

		while (pageNumber < numberOfPages) {
			String dipendente = "";
			str = PdfTextExtractor.getTextFromPage(reader, pageNumber);
			if (str.contains("RIEPILOGO GENERALE")) {
				dipendente = "RIEGEN00000";
			} else {
				if (str.contains("Codice dipendente")) {
					dipendente = str.substring(str.indexOf("Matricola") + 10, str.indexOf("Matricola") + 22).trim();
				} else {
					dipendente = str.substring(str.indexOf("/") + 1, str.indexOf("/") + 12);
					if (dipendente.contains("/")) {
						dipendente = str.substring(str.indexOf("/") + 43, str.indexOf("/") + 54);
					}
				}
			}
			
			String codFiscale="";
			if (!str.contains("RIEPILOGO GENERALE") && (str.contains("Matricola"))) {
				codFiscale = str.substring(str.indexOf("Codice fiscale") + 96, str.indexOf("Codice fiscale") + 112).trim();
			}
			if (str.contains("RIEPILOGO GENERALE")) {
				codFiscale = "0000000000000000";
			}
			

			String dirDIPENDENTE = dirAZIENDA + dipendente + "/";
			new File(dirDIPENDENTE).mkdir();

			String dirTEMPORANEA = dirAZIENDA + dipendente + "/" + meseAnno + "/";
			new File(dirTEMPORANEA).mkdir();

			String outFile = dirTEMPORANEA + dipendente + meseAnno + "_" + pageNumber + ".pdf";
			Document document = new Document();
			PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
			document.open();
			PdfImportedPage page = writer.getImportedPage(reader, pageNumber);
			writer.addPage(page);
			document.close();
			writer.close();
			++pageNumber;

			unisciPDF(dirTEMPORANEA, dirDIPENDENTE);

			creaRigaDBLink(azienda, anno, mese, dirAZIENDA, dipendente, codFiscale);

			if (str.indexOf("TOTALE TRATTENUTE") != -1) {
				creaRigaDBFeriePermessi(azienda, anno, mese, dirAZIENDA, dipendente, str, dirDIPENDENTE);
			}


			System.out.println("dipendente=" + dipendente);
		}

		System.out.println("ok");
	}

	private CaricaCedoliniDAO getRecordDiControllo(String mese, String anno, String azienda, String dipendente) throws AppCrash {
		CaricaCedoliniDAO caricaCedolini = new CaricaCedoliniDAO();
		caricaCedolini.setField(CaricaCedoliniDAO.ANNO, anno);
		caricaCedolini.setField(CaricaCedoliniDAO.MESE, mese);
		caricaCedolini.setField(CaricaCedoliniDAO.AZIENDA, azienda);
		caricaCedolini.setField(CaricaCedoliniDAO.DIPENDENTE, dipendente);
		if (!caricaCedolini.retrieve()) {
			caricaCedolini.setField(CaricaCedoliniDAO.NR_PAGINE, "0");
			caricaCedolini.insert();
		}
		return caricaCedolini;
	}

	private String spostaLULCaricato(String nomeFileCedolinoDaSessione, String cartellaCedolini) {
		new File(cartellaCedolini).mkdir();
		String cartellaFileImportati = _applicationSrv.getRoot() + "Import/" + nomeFileCedolinoDaSessione;
		cartellaFileImportati = cartellaFileImportati.replaceAll("//", "/");
		String suffisso = Utils.getUnique();

		File oldCedolino = new File(cartellaFileImportati);
		File cedolinoRinominato = new File(cartellaCedolini + "CEDOLINO" + suffisso + ".pdf");
		oldCedolino.renameTo(cedolinoRinominato);
		return suffisso;
	}

	private void creaRigaDBFeriePermessi(String azienda, String anno, String mese, String dirAZIENDA, String dipendente,
			String str, String dirDIPENDENTE) throws AppCrash {
		String strFeriePermessi = str.substring(str.indexOf("TOTALE TRATTENUTE"), str.length());
		String strFerieMaturato = "";
		String strFerieGoduto = "";
		String strFerieResiduo = "";
		String strPermessoMaturato = "";
		String strPermessoGoduto = "";
		String strPermessoResiduo = "";
		String updateDati = "";
		
		
		if (mese.contains("TFR_A")){
		    mese="TFR_A";
		}

		
		
		if (!strFeriePermessi.contains("TOTALE TRATTENUTE\nARROTONDAMENTO")) {
			if (strFeriePermessi.indexOf("Ferie") != -1) {
				strFerieMaturato = strFeriePermessi.substring(strFeriePermessi.indexOf("Ferie") + 5,
						strFeriePermessi.indexOf("Ferie") + 22).trim();
				strFerieGoduto = strFeriePermessi.substring(strFeriePermessi.indexOf("Ferie") + 22,
						strFeriePermessi.indexOf("Ferie") + 35).trim();
				strFerieResiduo = strFeriePermessi.substring(strFeriePermessi.indexOf("Ferie") + 35,
						strFeriePermessi.indexOf("Ferie") + 47).trim();
			}
			if (strFeriePermessi.indexOf("Permessi") != -1) {
				strPermessoMaturato = strFeriePermessi.substring(strFeriePermessi.indexOf("Permessi") + 8,
						strFeriePermessi.indexOf("Permessi") + 25).trim();
				strPermessoGoduto = strFeriePermessi.substring(strFeriePermessi.indexOf("Permessi") + 25,
						strFeriePermessi.indexOf("Permessi") + 38).trim();
				strPermessoResiduo = strFeriePermessi.substring(strFeriePermessi.indexOf("Permessi") + 38,
						strFeriePermessi.indexOf("Permessi") + 50).trim();
			}
			
			if (strFeriePermessi.indexOf("Perm.Ex-Fs") != -1) {
                strPermessoMaturato = strFeriePermessi.substring(strFeriePermessi.indexOf("Perm.Ex-Fs") + 10,
                        strFeriePermessi.indexOf("Perm.Ex-Fs") + 27).trim();
                strPermessoGoduto = strFeriePermessi.substring(strFeriePermessi.indexOf("Perm.Ex-Fs") + 26,
                        strFeriePermessi.indexOf("Perm.Ex-Fs") + 39).trim();
                strPermessoResiduo = strFeriePermessi.substring(strFeriePermessi.indexOf("Perm.Ex-Fs") + 39,
                        strFeriePermessi.indexOf("Perm.Ex-Fs") + 50).trim();
            }
			
			if (!strFerieMaturato.contains("0") && !strFerieMaturato.contains("1") && !strFerieMaturato.contains("2")
					&& !strFerieMaturato.contains("3") && !strFerieMaturato.contains("4") && !strFerieMaturato.contains("5")
					&& !strFerieMaturato.contains("6") && !strFerieMaturato.contains("7") && !strFerieMaturato.contains("8")
					&& !strFerieMaturato.contains("9")) {
				strFerieMaturato = "";
			}
			if (!strFerieGoduto.contains("0") && !strFerieGoduto.contains("1") && !strFerieGoduto.contains("2")
					&& !strFerieGoduto.contains("3") && !strFerieGoduto.contains("4") && !strFerieGoduto.contains("5")
					&& !strFerieGoduto.contains("6") && !strFerieGoduto.contains("7") && !strFerieGoduto.contains("8")
					&& !strFerieGoduto.contains("9")) {
				strFerieGoduto = "";
			}
			if (!strFerieResiduo.contains("0") && !strFerieResiduo.contains("1") && !strFerieResiduo.contains("2")
					&& !strFerieResiduo.contains("3") && !strFerieResiduo.contains("4") && !strFerieResiduo.contains("5")
					&& !strFerieResiduo.contains("6") && !strFerieResiduo.contains("7") && !strFerieResiduo.contains("8")
					&& !strFerieResiduo.contains("9")) {
				strFerieResiduo = "";
			}
			if (!strPermessoMaturato.contains("0") && !strPermessoMaturato.contains("1") && !strPermessoMaturato.contains("2")
					&& !strPermessoMaturato.contains("3") && !strPermessoMaturato.contains("4")
					&& !strPermessoMaturato.contains("5") && !strPermessoMaturato.contains("6")
					&& !strPermessoMaturato.contains("7") && !strPermessoMaturato.contains("8")
					&& !strPermessoMaturato.contains("9")) {
				strPermessoMaturato = "";
			}
			if (!strPermessoGoduto.contains("0") && !strPermessoGoduto.contains("1") && !strPermessoGoduto.contains("2")
					&& !strPermessoGoduto.contains("3") && !strPermessoGoduto.contains("4") && !strPermessoGoduto.contains("5")
					&& !strPermessoGoduto.contains("6") && !strPermessoGoduto.contains("7") && !strPermessoGoduto.contains("8")
					&& !strPermessoGoduto.contains("9")) {
				strPermessoGoduto = "";
			}
			if (!strPermessoResiduo.contains("0") && !strPermessoResiduo.contains("1") && !strPermessoResiduo.contains("2")
					&& !strPermessoResiduo.contains("3") && !strPermessoResiduo.contains("4") && !strPermessoResiduo.contains("5")
					&& !strPermessoResiduo.contains("6") && !strPermessoResiduo.contains("7") && !strPermessoResiduo.contains("8")
					&& !strPermessoResiduo.contains("9")) {
				strPermessoResiduo = "";
			}

			updateDati = "update elenco_file set F" + mese + "_M='" + strFerieMaturato + "' WHERE TIPOLOGIA='C' AND AZIENDA = '"
					+ azienda + "' AND DIPENDENTE='" + dipendente + "' AND ANNO='" + anno + "'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(updateDati);
			updateDati = "update elenco_file set F" + mese + "_G='" + strFerieGoduto + "' WHERE TIPOLOGIA='C' AND AZIENDA = '"
					+ azienda + "' AND DIPENDENTE='" + dipendente + "' AND ANNO='" + anno + "'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(updateDati);
			updateDati = "update elenco_file set F" + mese + "_R='" + strFerieResiduo + "' WHERE TIPOLOGIA='C' AND AZIENDA = '"
					+ azienda + "' AND DIPENDENTE='" + dipendente + "' AND ANNO='" + anno + "'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(updateDati);

			updateDati = "update elenco_file set P" + mese + "_M='" + strPermessoMaturato + "' WHERE TIPOLOGIA='C' AND AZIENDA = '"
					+ azienda + "' AND DIPENDENTE='" + dipendente + "' AND ANNO='" + anno + "'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(updateDati);
			updateDati = "update elenco_file set P" + mese + "_G='" + strPermessoGoduto + "' WHERE TIPOLOGIA='C' AND AZIENDA = '"
					+ azienda + "' AND DIPENDENTE='" + dipendente + "' AND ANNO='" + anno + "'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(updateDati);
			updateDati = "update elenco_file set P" + mese + "_R='" + strPermessoResiduo + "' WHERE TIPOLOGIA='C' AND AZIENDA = '"
					+ azienda + "' AND DIPENDENTE='" + dipendente + "' AND ANNO='" + anno + "'";
			net.projectsrl.wm.utils.WMUtils.executeQuery(updateDati);
		}
	}

	private void creaRigaDBLink(String azienda, String anno, String mese, String dirAZIENDA, String dipendente, String codFiscale) throws AppCrash {

		String dirDipendente = dirAZIENDA + dipendente + "/";
		String meseAnno = mese + anno;

		if (anno.contains("A")) {
			mese = mese + "A";
			anno = anno.replace("A", "");
			meseAnno = mese.replace("A", "") + anno + "A";
		}
		
		String meseCcd=mese.replace("TFR_A", "");
		
		//String link = "cedolini/" + azienda + "/" + dipendente + "/" + dipendente + "_" + meseAnno + ".pdf";
		String link = "cedolini/" + azienda + "/" + dipendente + "/" + dipendente + "_" + meseCcd + anno + ".pdf";

		
		CaricaCedoliniDAO ccd = getRecordDiControllo(meseCcd, anno, azienda, dipendente);
		ccd.setField(CaricaCedoliniDAO.LINK, link);
		
		String strNrPagine=ccd.getField(CaricaCedoliniDAO.NR_PAGINE);
		
		int nrPagine=Integer.parseInt(strNrPagine);
		nrPagine++;
		
		strNrPagine=String.valueOf(nrPagine);
		
		ccd.setField(CaricaCedoliniDAO.NR_PAGINE, strNrPagine);
		
		ccd.setField(CaricaCedoliniDAO.LINK, link);
		
		ccd.update();

		String whereCondition = "TIPOLOGIA='C' AND AZIENDA='" + azienda + "' AND DIPENDENTE='" + dipendente + "' AND ANNO='" + anno
				+ "'";

		if (!esisteRigaDipendenteInElencoFile(whereCondition)) {
			String insertFile = "";
			if (!codFiscale.equals("")){
				insertFile = "insert into elenco_file (TIPOLOGIA,CARTELLA,AZIENDA,DIPENDENTE,ANNO,codice_fiscale) values ('C','"
					+ dirDipendente.replace("\\\\", "/").replace("//", "/") + "','" + azienda + "','" + dipendente + "','" + anno+ "','" + codFiscale+ "')";
				net.projectsrl.wm.utils.WMUtils.executeQuery(insertFile);
			}
			
		}

		String updateFile = "";

		if (mese.contains("TFR_A")){
            mese="TFR_A";
            updateFile = "update elenco_file set TFR_A='" + link + "'  WHERE " + whereCondition;
        }else{
            updateFile = "update elenco_file set M" + mese + "='" + link + "'  WHERE " + whereCondition;
        }
		net.projectsrl.wm.utils.WMUtils.executeQuery(updateFile);
	}

	private void unisciPDF(String dirTEMPORANEA, String dirDIPENDENTE) {

		File directory2 = new File(dirTEMPORANEA);
		int conteggio2 = directory2.listFiles().length;
		String pathMerged = dirDIPENDENTE;
		new File(pathMerged).mkdir();
		int wd = 0;

		while (wd < conteggio2) {
			try {
				String afile[] = directory2.list();
				for (int ifile = 0; ifile < afile.length; ifile++) {
					String fileAttuale = "";
					String fileAttualeSoloCodice = "";

					fileAttuale = afile[ifile];
					fileAttualeSoloCodice = fileAttuale.substring(0, fileAttuale.lastIndexOf("_"));
					
					
					File f = new File(pathMerged + fileAttualeSoloCodice + ".pdf");
					if (!f.isDirectory()) {
						if (f.exists()) {
							PDFMergerUtility ut = new PDFMergerUtility();
							ut.addSource(pathMerged + fileAttualeSoloCodice + ".pdf");
							ut.addSource(dirTEMPORANEA + fileAttuale);
							ut.setDestinationFileName(pathMerged + fileAttualeSoloCodice + ".pdf");
							try {
								ut.mergeDocuments();
								File fileToDelete = new File(dirTEMPORANEA + fileAttuale);
								fileToDelete.delete();
							} catch (COSVisitorException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							File old = new File(dirTEMPORANEA + fileAttuale), rname = new File(pathMerged + fileAttualeSoloCodice
									+ ".pdf");
							old.renameTo(rname);
							++wd;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			++wd;
		}
	}

	private void riepilogoAnnualePDF(SsbServletRequest req, String dirAZIENDA, String anno) throws IOException {
		// int contaCedolini=0;
		File directoryAzienda = new File(dirAZIENDA);
		String directoryAziendaFile[] = directoryAzienda.list();
		for (int efile = 0; efile < directoryAziendaFile.length; efile++) {
			String directoryDipendenteNome = directoryAziendaFile[efile];
			String dirDIPENDENTE = dirAZIENDA + "\\" + directoryDipendenteNome + "\\";
			File directoryDipendente = new File(dirDIPENDENTE);
			String directoryDipendenteFile[] = directoryDipendente.list();
			for (int ifile = 0; ifile < directoryDipendenteFile.length; ifile++) {

				String fileAttuale = directoryDipendenteFile[ifile];
				String fileAttualeSenzaA = fileAttuale.replace("A.pdf", ".pdf");
				if (fileAttuale.length() > 20
						&& fileAttualeSenzaA.substring(fileAttualeSenzaA.indexOf("_") + 3, fileAttualeSenzaA.indexOf(".pdf"))
								.equals(anno)) {
					String fileAttualeSoloCodice = directoryDipendenteFile[ifile].substring(0, 12)
							+ directoryDipendenteFile[ifile].substring(14, 18);
					File f = new File(dirDIPENDENTE + fileAttualeSoloCodice + ".pdf");
					if (f.getName().replace("A", "").replace(".pdf", "").equals(fileAttualeSoloCodice.replace("A", ""))) {
						if (!f.exists()) {
							File singolo = new File(dirDIPENDENTE + fileAttuale);
							File riepilogo = new File(dirDIPENDENTE + fileAttualeSoloCodice + ".pdf");
							FileUtils.copyFile(singolo, riepilogo);
							// contaCedolini=contaCedolini+1;
							// req.getSession(false).setAttribute("CEDOLINI_CREATI",
							// contaCedolini);
						} else {
							PDFMergerUtility ut = new PDFMergerUtility();
							ut.addSource(dirDIPENDENTE + fileAttualeSoloCodice + ".pdf");
							ut.addSource(dirDIPENDENTE + fileAttuale);
							ut.setDestinationFileName(dirDIPENDENTE + fileAttualeSoloCodice + ".pdf");
							// contaCedolini=contaCedolini+1;
							// req.getSession(false).setAttribute("CEDOLINI_CREATI",
							// contaCedolini);
							try {
								ut.mergeDocuments();
							} catch (COSVisitorException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					File f = new File(dirDIPENDENTE + fileAttuale);
					if (f.isDirectory()) {
						f.delete();

					}
				}
			}
		}
	}

	private void cancellaPossibiliDoppioniPDF(SsbServletRequest req, String dirAZIENDA, String anno) throws IOException {
		int contaCedolini = 1;
		File directoryAzienda = new File(dirAZIENDA);
		String directoryAziendaFile[] = directoryAzienda.list();
		int lunghezza = 22;
		if (anno.length() == 4) {
			anno = "_" + anno;
			lunghezza = 20;
		}
		for (int efile = 0; efile < directoryAziendaFile.length; efile++) {
			String directoryDipendenteNome = directoryAziendaFile[efile];
			String dirDIPENDENTE = dirAZIENDA + "\\" + directoryDipendenteNome + "\\";
			if (!directoryDipendenteNome.equals("RIEMEN00000")) {
				File directoryDipendente = new File(dirDIPENDENTE);
				String directoryDipendenteFile[] = directoryDipendente.list();
				for (int ifile = 0; ifile < directoryDipendenteFile.length; ifile++) {

					String fileAttuale = directoryDipendenteFile[ifile];
					if (fileAttuale.endsWith(anno + ".pdf") && fileAttuale.length() == lunghezza) {
						File f = new File(dirDIPENDENTE + fileAttuale);
						f.delete();
						// contaCedolini=contaCedolini+1;
						// req.getSession(false).setAttribute("CEDOLINI_CREATI",
						// contaCedolini);
					} else {
						File f = new File(dirDIPENDENTE + fileAttuale);
						if (f.isDirectory()) {
							f.delete();
							contaCedolini = contaCedolini + 1;
							req.getSession(false).setAttribute("CEDOLINI_CREATI", contaCedolini);
						}
					}
				}
			}
		}
	}

	private String scriviMeseAnno(String str) {

		String meseAnno = "";
		if (str.contains("Gennaio")) {
			meseAnno = "_01" + str.substring(str.indexOf("Gennaio") + 8, str.indexOf("Gennaio") + 14).trim();
		}
		if (str.contains("Febbraio")) {
			meseAnno = "_02" + str.substring(str.indexOf("Febbraio") + 8, str.indexOf("Febbraio") + 14).trim();
		}
		if (str.contains("Marzo")) {
			meseAnno = "_03" + str.substring(str.indexOf("Marzo") + 5, str.indexOf("Marzo") + 14).trim();
		}
		if (str.contains("Aprile")) {
			meseAnno = "_04" + str.substring(str.indexOf("Aprile") + 6, str.indexOf("Aprile") + 14).trim();
		}
		if (str.contains("Maggio")) {
			meseAnno = "_05" + str.substring(str.indexOf("Maggio") + 6, str.indexOf("Maggio") + 14).trim();
		}
		if (str.contains("Giugno")) {
			meseAnno = "_06" + str.substring(str.indexOf("Giugno") + 6, str.indexOf("Giugno") + 14).trim();
		}
		if (str.contains("Luglio")) {
			meseAnno = "_07" + str.substring(str.indexOf("Luglio") + 6, str.indexOf("Luglio") + 14).trim();
		}
		if (str.contains("Agosto")) {
			meseAnno = "_08" + str.substring(str.indexOf("Agosto") + 6, str.indexOf("Agosto") + 14).trim();
		}
		if (str.contains("Settembre")) {
			meseAnno = "_09" + str.substring(str.indexOf("Settembre") + 9, str.indexOf("Settembre") + 14).trim();
		}
		if (str.contains("Ottobre")) {
			meseAnno = "_10" + str.substring(str.indexOf("Ottobre") + 7, str.indexOf("Ottobre") + 14).trim();
		}
		if (str.contains("Novembre")) {
			meseAnno = "_11" + str.substring(str.indexOf("Novembre") + 8, str.indexOf("Novembre") + 14).trim();
		}
		if (str.contains("Dicembre")) {
			meseAnno = "_12" + str.substring(str.indexOf("Dicembre") + 8, str.indexOf("Dicembre") + 14).trim();
		}

		if (str.contains("Giugno") && str.contains("AGG.") && str.contains("14ma")) {
			meseAnno = "_06" + str.substring(str.indexOf("Giugno") + 6, str.indexOf("Giugno") + 14).trim() + "A";
		}
		if (str.contains("Dicembre") && str.contains("AGG.") && str.contains("13ma")) {
			meseAnno = "_12" + str.substring(str.indexOf("Dicembre") + 8, str.indexOf("Dicembre") + 14).trim() + "A";
		}
		return meseAnno;
	}

	private boolean esisteRigaDipendenteInElencoFile(String whereCondition) throws AppCrash {

		boolean exists = false;
		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", DATASET_RIGA_FILE);
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("CONDIZIONE", whereCondition);
			dataSet.setParam(params);
			dataSet.open();
			exists = dataSet.hasMoreElements();
		} catch (Throwable t) {
			AppCrash ac = new AppCrash(t);
			throw ac;
		} finally {
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (AppCrash ac) {
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
				}
			}
		}
		return exists;
	}

	private String getNomeAzienda(String azienda) throws AppCrash {
		String dati = "";
		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", "DataSetAziendeCerca");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("CODICE_AZIENDA_CED", azienda);
			dataSet.setParam(params);
			dataSet.open();
			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();
				dati = dbRow.getField("RAGSOC").toString().toUpperCase().trim();
			}
			dataSet.close();
		} catch (Throwable t) {
			AppCrash ac = new AppCrash(t);
			throw ac;
		} finally {
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (AppCrash ac) {
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
				}
			}
		}
		return dati;
	}

	public DataSet_itf riepilogoControlloCedolini(String azienda, String anno, String mese) throws AppCrash {

		if (anno.contains("A")) {
			mese = mese + "A";
			anno = anno.replace("A", "");
		}
		
		
		DataSet_itf dataSet = null;
		try {
			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", "DataSetControlloCedoliniCaricati");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("AZIENDA", azienda);
			params.put("ANNO", anno);
			params.put("MESE", mese);
			dataSet.setParam(params);

		} catch (Throwable t) {
			AppCrash ac = new AppCrash(t);
			throw ac;

		}
		return dataSet;
	}
}