package net.projectsrl.wm.documenti.core;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimento;

/**
 * FunctionCreaPDFCedolino - incorporata in FunctionFileUploadCedolini, questa classe non è mai richiamata ma solo utilizzata di là
 * 
 */
public class FunctionCreaPDFCedolino extends FunctionInserimento {

    private static final String PAGE   = "index";
    private static final String PAGE_R = "index";
    
    public FunctionCreaPDFCedolino() {

        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetMProv");
    }

    public FunctionCreaPDFCedolino(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetMProv");
    }

   
    @SuppressWarnings("unchecked")
	public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
    	
    	HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);

    	String dirCEDOLINO = "c:/cedolini/";
        new File(dirCEDOLINO).mkdir();
        
    	String cartellaFileImportati=_applicationSrv.getRoot()+"Import/";
    	cartellaFileImportati=cartellaFileImportati.replaceAll("//","/");
    	
    	File oldCEDOLINO = new File(cartellaFileImportati+"CEDOLINO.pdf"), rnameCEDOLINO = new File(dirCEDOLINO+"CEDOLINO.pdf");
    	oldCEDOLINO.renameTo(rnameCEDOLINO);
    	
    	// splittare pfd - inizio
        try {
        	String inFile = "";
        	inFile = "c:\\cedolini\\CEDOLINO.pdf";
       	   	
        	PdfReader reader = new PdfReader(inFile);
            int n = reader.getNumberOfPages();
            int i = 0;
            int y = 0;
            int paginaIniziale=0;
            int paginaFinale=0;
            int paginaInizialeNext=0;
            int prossimaPaginaIniziale=0;
            
            while ( i < n ) {
            	
            	// lettura interno file
    	        String str=PdfTextExtractor.getTextFromPage(reader, i+1); 
    	        
    	        String meseAnno=scriviMeseAnno(str);

    	        String azienda = "";
    	        if (str.contains("Codice dipendente")){
    	        	azienda=str.substring(str.indexOf("VIDIMAZIONE")+12,str.indexOf("VIDIMAZIONE")+23).trim();
    	        }else{
    	        	azienda=str.substring(str.indexOf("INDIRIZZO")+12,str.indexOf("INDIRIZZO")+69).trim();
    	        }
    	        
    	        String dirAZIENDA = inFile.substring(0, inFile.indexOf(".pdf")-8)+azienda+"/";
    	        new File(dirAZIENDA).mkdir();
    	        
    	        
    	        if (str.contains("RIEPILOGO GENERALE")){
    	        	str="RIEGEN00000";
    	        }else{
	    	        if (str.contains("Codice dipendente")){
	    	        	str=str.substring(str.indexOf("Matricola")+10,str.indexOf("Matricola")+22).trim();  
	    	        }else{
	    	        	str=str.substring(str.indexOf("/")+1,str.indexOf("/")+12);
	    	        }
    	        }
    	        
    	        
    	        String dipendente = str;
    	        String dirDIPENDENTE = dirAZIENDA+dipendente+"/";
    	        new File(dirDIPENDENTE).mkdir();
    	        
    	        String outFile="";
    	        outFile = dirDIPENDENTE + str + meseAnno +".pdf";
    	        
    	        if (i>0){
    	        	prossimaPaginaIniziale=paginaInizialeNext;
    	        }
    	        
    	        
    	        // cerco l'ultima pagina del file del dipendente
    	        while ( y < n ) {
                	 
                	// lettura interno file
        	        String strNext=PdfTextExtractor.getTextFromPage(reader, y+1); 
        	        if (strNext.contains("RIEPILOGO GENERALE")){
        	        	strNext="RIEGEN00000";
        	        }else{
        	        	if (strNext.contains("Codice dipendente")){
    	    	        	strNext=strNext.substring(strNext.indexOf("Matricola")+10,strNext.indexOf("Matricola")+22).trim();  
    	    	        }else{
    	    	        	strNext=strNext.substring(strNext.indexOf("/")+1,strNext.indexOf("/")+12);
    	    	        }
        	        }
        	        ++y;
        	        
        	        if (!str.equals(strNext)){
        	        	paginaFinale=y-1;
        	        	if (i==1){
        	        		paginaInizialeNext=y-1;
        	        	}else{
        	        		paginaInizialeNext=y;
        	        	}
        	        	
        	        	break;
        	        }
    	        }
    	        
    	        if (y==n){
    	        	paginaFinale=n;
    	        }
    	        //fine lettura interno file  
                
                Document document = new Document();
                
                if (i>0){
                	PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
                    document.open();
                    PdfImportedPage page;
                	if (i==1){
                		page = writer.getImportedPage(reader,paginaIniziale+1);
                	}else{
                		page = writer.getImportedPage(reader,prossimaPaginaIniziale);
                	}

					writer.addPage(page);
					if (i==1){
						  paginaIniziale=paginaIniziale+1;
						  while ( paginaIniziale+1 < paginaFinale ){
		                    	PdfImportedPage page1 = writer.getImportedPage(reader, paginaIniziale+1);
		                    	writer.addPage(page1);
		                    	paginaIniziale=paginaIniziale+1;
		                    }
					}else{
						  while ( prossimaPaginaIniziale+1 <= paginaFinale ){
		                    	PdfImportedPage page1 = writer.getImportedPage(reader, prossimaPaginaIniziale+1);
		                    	writer.addPage(page1);
		                    	prossimaPaginaIniziale=prossimaPaginaIniziale+1;
		                    }
					}

                    document.close();
                    writer.close();
                    i=paginaFinale;
                }else{
                	++i;
                }
            }
            	reader.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    
    	// splittare pfd - fine

    
	 		
/*		// cancello cartelle non più utilizzate
		File cartellaCEDOLINO = new File("c:/cedolini/");
		File[] filesCEDOLINO = cartellaCEDOLINO.listFiles();
		for (File f : filesCEDOLINO)
		f.delete();
		// fine cancellazione cartelle
*/		
 		
 		

        _applicationSrv.displayPage(PAGE_R, templateData, setPageDatasetParam(PAGE_R, req, templateData), res);

    }
    
    
    private String scriviMeseAnno(String str) {

    	String meseAnno="";
    	if (str.contains("Gennaio")){
        	meseAnno="_01"+str.substring(str.indexOf("Gennaio")+8,str.indexOf("Gennaio")+13).trim();
        }
        if (str.contains("Febbraio")){
        	meseAnno="_02"+str.substring(str.indexOf("Febbraio")+8,str.indexOf("Febbraio")+13).trim();
        }
        if (str.contains("Marzo")){
        	meseAnno="_03"+str.substring(str.indexOf("Marzo")+5,str.indexOf("Marzo")+10).trim();
        }
        if (str.contains("Aprile")){
        	meseAnno="_04"+str.substring(str.indexOf("Aprile")+6,str.indexOf("Aprile")+11).trim();
        }
        if (str.contains("Maggio")){
        	meseAnno="_05"+str.substring(str.indexOf("Maggio")+6,str.indexOf("Maggio")+11).trim();
        }
        if (str.contains("Giugno")){
        	meseAnno="_06"+str.substring(str.indexOf("Giugno")+6,str.indexOf("Giugno")+11).trim();
        }
        if (str.contains("Luglio")){
        	meseAnno="_07"+str.substring(str.indexOf("Luglio")+6,str.indexOf("Luglio")+11).trim();
        }
        if (str.contains("Agosto")){
        	meseAnno="_08"+str.substring(str.indexOf("Agosto")+6,str.indexOf("Agosto")+11).trim();
        }
        if (str.contains("Settembre")){
        	meseAnno="_09"+str.substring(str.indexOf("Settembre")+9,str.indexOf("Settembre")+14).trim();
        }
        if (str.contains("Ottobre")){
        	meseAnno="_10"+str.substring(str.indexOf("Ottobre")+7,str.indexOf("Ottobre")+12).trim();
        }
        if (str.contains("Novembre")){
        	meseAnno="_11"+str.substring(str.indexOf("Novembre")+8,str.indexOf("Novembre")+13).trim();
        }
        if (str.contains("Dicembre")){
        	meseAnno="_12"+str.substring(str.indexOf("Dicembre")+8,str.indexOf("Dicembre")+13).trim();
        }
        return meseAnno;
    }
    
    

}
