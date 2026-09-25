package net.projectsrl.wm.jobmanagement.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;

/**
 * Function FunctionStampaCommessePDF
 * 
 */
public class FunctionStampaCommessePDF extends FunctionRicercaCommesse {

	public static final String PAGE_STAMPA_PDF = "stampa_elenco_commesse_pdf";
	public static final String XML_NAME_PDF = "elencocommessePDF.xml";
	public static final String XSL_NAME_PDF_COMPLETA = "elencocommessePDF.xsl";	
	private static final String DATASET_LOGO = "DataSetAllegatiLogo";
	
	public FunctionStampaCommessePDF() {
		super();
	}

	public FunctionStampaCommessePDF(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}


	@SuppressWarnings("unchecked")
	public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
		  
		HashMap templateData = (HashMap) setCommonTags(req, userInfo);
		templateData = super.setTemplateDataFromRequest(templateData, req);
		
		Date currentTime = new Date();
		SimpleDateFormat formatterTimestamp = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
		String strDataOra = formatterTimestamp.format(currentTime);
		
		HttpSession session = req.getSession(true);
		templateData.put("aggiornato_a",strDataOra);
	    String logo = "";
        String logoAzienda=getLogo(req.getField("AZIENDA_TENDINA"));
        logo=_applicationSrv.getRoot() + "areadocumenti\\"+req.getField("AZIENDA_TENDINA")+"\\"+logoAzienda;
        if (logoAzienda.equals("")){
        	logoAzienda=getLogo((String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
        	logo=_applicationSrv.getRoot() + "areadocumenti\\"+(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE")+"\\"+logoAzienda;
        }
		
		Map dataSourceParamPDF[]=setPageDatasetParam(PAGE_STAMPA_PDF, req, templateData);
		
		String fileName="Elenco_commesse";		
		salvaFormatoPDFInDiversiModuli(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParamPDF);
		

		try {
			templateData= setTemplateDataFromRequest(templateData, req);

			fileName="Elenco_commesse";
			fileName="./doc/"+fileName+".pdf";
			templateData.put("filename",fileName);
			
            readPDFFile(fileName, "Elenco_commesse", "FunctionStampaCommessePDF", res);
            res.getOutputStream().flush();
        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            throw ac;
                }

	}
	
	protected @SuppressWarnings("unchecked") void salvaFormatoPDFInDiversiModuli(String fileName, String page_stampa2, HashMap templateData, Map[] dataSourceParam) throws AppCrash {
		salvaFormatoPDF(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParam,XML_NAME_PDF,XSL_NAME_PDF_COMPLETA);
	}
	
	private String getLogo(String aziendaLogo) throws AppCrash {
        String dati = "";
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_LOGO);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("AZIENDA", aziendaLogo);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("AZIENDA").toString().trim();
                if (aziendaLogo.equals(idTrovato)){
                	dati = dbRow.getField("FILENAME").toString().trim();
                }
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
	
	
}
