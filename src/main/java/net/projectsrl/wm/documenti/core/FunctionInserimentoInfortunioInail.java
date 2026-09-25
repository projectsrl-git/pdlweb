package net.projectsrl.wm.documenti.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInfortunioInail
 * 
 */
public class FunctionInserimentoInfortunioInail extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_infortunioinail";
	
	private static final String DATASET_DIPENDENTI = "DataSetDipendenti";
	public static final String PAGE_STAMPA_PDF = "stampa_inail_pdf";
	public static final String XML_NAME_PDF = "inailPDF.xml";
	public static final String XSL_NAME_PDF_COMPLETA = "inailPDF.xsl";

	public FunctionInserimentoInfortunioInail() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetInfortunioInail");
	}

	public FunctionInserimentoInfortunioInail(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetInfortunioInail");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
		

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("TAGGANCIO",Utils.getUnique());
			return templateData;
		}
		return templateData;
	}
	
	
	@SuppressWarnings("unchecked")
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
        saveVarStandard(templateData, req,res);
        templateData.put("RUOLO_SESSIONE", getSessionRole(req));
		templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		templateData.put("SALVATO", "Salvataggio effettuato con successo");
		templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
		
		String dirAZIENDA =_applicationSrv.getRoot()+Config.GetInstance().getProperty("cartella.documenti")+req.getField("AZIENDA_TENDINA")+"/";
        new File(dirAZIENDA).mkdir();
        String dirDIPENDENTE =dirAZIENDA+ "Infortuni_INAIL/";
        new File(dirDIPENDENTE).mkdir();
        
        Date currentTime = new Date();
		SimpleDateFormat formatterTimestamp = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
		String strDataOra = formatterTimestamp.format(currentTime);
		
		HttpSession session = req.getSession(true);
		templateData.put("aggiornato_a",strDataOra);
		String logoAzienda="";
	    String logo = _applicationSrv.getRoot() + "areadocumenti\\"+req.getField("AZIENDA_TENDINA")+"\\"+logoAzienda;
	    if (logoAzienda.equals("")){
        	logo=_applicationSrv.getRoot() + "img\\logo\\white_logo.jpg";
    	}
	    templateData.put("logo",logo);
	    templateData.put("sfondo",_applicationSrv.getRoot()+"img\\moduli\\infortunio_1.jpg");
	    templateData.put("sfondo2",_applicationSrv.getRoot()+"img\\moduli\\infortunio_2.jpg");
	    templateData.put("sfondo3",_applicationSrv.getRoot()+"img\\moduli\\infortunio_3.jpg");
	    templateData.put("sfondo4",_applicationSrv.getRoot()+"img\\moduli\\infortunio_4.jpg");
	    templateData.put("sfondo5",_applicationSrv.getRoot()+"img\\moduli\\infortunio_5.jpg");
		
		Map dataSourceParamPDF[]=setPageDatasetParam(PAGE_STAMPA_PDF, req, templateData);
		
		String fileName="InfortunioINAIL_"+templateData.get("TAGGANCIO");		
		salvaFormatoPDFInDiversiModuli(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParamPDF);

        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
	 }
 
 	protected void salvaFormatoPDFInDiversiModuli(String fileName, String page_stampa2, HashMap templateData, Map[] dataSourceParam) throws AppCrash {
		salvaFormatoPDF(fileName, PAGE_STAMPA_PDF, templateData,dataSourceParam,XML_NAME_PDF,XSL_NAME_PDF_COMPLETA);
	}
 
 
 @SuppressWarnings("unchecked")
	protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {
 	
     
 	String dataDal		= Utils.ribaltaData(req.getField("DATA_DAL_FP"));
    String dataAl		= Utils.ribaltaData(req.getField("DATA_AL_FP"));
 	String azienda		= req.getField("AZIENDA_TENDINA");
    String dipendente	= req.getField("DIPENDENTE");
    String taggancio	= req.getField("TAGGANCIO");
     
     
    
    
     String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
     
     if(dipendente.equals("") && azienda.trim().equalsIgnoreCase("")){
     	dipendente= (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
     }
     
     

     String str_composed_where_cond 	= "";
     
     if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
 		str_composed_where_cond = str_composed_where_cond + " and AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
     }

     if (!dataDal.trim().equalsIgnoreCase("")) {	
     	str_composed_where_cond = str_composed_where_cond + " AND DATA_DAL >= '"+dataDal+"'";
     }
     if (!dataAl.trim().equalsIgnoreCase("")) {	
     	str_composed_where_cond = str_composed_where_cond + " AND DATA_AL <= '"+dataAl+"'";
     }
     
     if (!azienda.trim().equalsIgnoreCase("")) {	
     	str_composed_where_cond = str_composed_where_cond + " AND INFORTUNIO_INAIL.AZIENDA_TENDINA = '"+azienda+"'";
     }
     if (!dipendente.trim().equalsIgnoreCase("")) {	
     	str_composed_where_cond = str_composed_where_cond + " AND SL_ID_DIPENDENTE = '"+dipendente+"'";
     }
     
     str_composed_where_cond = str_composed_where_cond + " AND INFORTUNIO_INAIL.TAGGANCIO='"+taggancio+"'";
     
     
         
		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
     if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
     	if (str_composed_where_cond.startsWith("(")){
     		str_composed_where_cond=" ("+str_composed_where_cond.substring(4);
     	}else{
     		str_composed_where_cond=" "+str_composed_where_cond.substring(4);
     	}
     	str_composed_where_cond=" WHERE "+str_composed_where_cond;
     }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
     
 	return queryParameter;
 }    
 
 
}

