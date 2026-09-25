package net.projectsrl.wm.cv.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionStampaCVWord
 * 
 */
public class FunctionStampaCVWord extends FunctionInserimentoCurriculum {



	private static final String PAGE = "stampa_cv_anteprima";

	public FunctionStampaCVWord() {

		super();
	}

	public FunctionStampaCVWord(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
	}

	
	@SuppressWarnings("unchecked")
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
		
		HashMap templateData = (HashMap) setCommonTags(req, userInfo);
		templateData = templateData = super.setTemplateDataFromRequest(templateData, req);
		
		Map dataSourceParam[]=setPageDatasetParam(PAGE_STAMPA, req, templateData);
		
		Date currentTime = new Date();
		SimpleDateFormat formatterTimestamp = new SimpleDateFormat("dd/MM/yyyy");
		String strDataOra = formatterTimestamp.format(currentTime);
		
		templateData.put("aggiornato_a",strDataOra);
		
		templateData.put("MATRICE_COMPETENZE",req.getField("MATRICE_COMP"));
		templateData.put("SCHEDA_PROFILO",req.getField("SCHEDA_PROF"));
		templateData.put("SCHEDA_COLLOQUI",req.getField("SCHEDA_COLL"));		
		
		String nome = req.getField("NOME");
		if (nome.length() != 0) nome=nome.substring(0, 1).toUpperCase() + nome.substring(1);

		templateData.put("NOME",nome);
		
		String cognome = req.getField("COGNOME");
		if (cognome.length() != 0) cognome=cognome.substring(0, 1) + cognome.substring(1).toLowerCase();

		templateData.put("COGNOME",cognome);
		
		String fileNameNew=(String) (cognome)+"_"+(nome);
		String modulo = (String) req.getField("MODULO");
		String societa_sel = Utils.leggeStringaElencoCampi("SOCIETA_SELEZ", req.getField("SOCIETA_SELEZ"));		
		String punto_b = Utils.leggeStringaElencoCampi("PUNTO_B", req.getField("SOCIETA_SELEZ"));		
		String punto_c = Utils.leggeStringaElencoCampi("PUNTO_C", req.getField("SOCIETA_SELEZ"));		
		
		if (modulo.equals(PROJECT)) {
			templateData.put(TAG_MODULO,PROJECT);
			salvaFormatoWord(PROJECT+"_"+fileNameNew, PAGE_STAMPA, templateData,dataSourceParam);
		}
		
		if (modulo.equals(EUROPEO)) {
			templateData.put(TAG_MODULO,EUROPEO);
			salvaFormatoWord(EUROPEO+"_"+fileNameNew, PAGE_STAMPA, templateData,dataSourceParam);
		}
		
		if (modulo.equals(ALTRAN)) {
			templateData.put(TAG_MODULO,ALTRAN);
			salvaFormatoWord(ALTRAN+"_"+fileNameNew, PAGE_STAMPA, templateData,dataSourceParam);
		}

		if (modulo.equals(MODULO_PRIVACY)) {
			templateData.put(TAG_MODULO,MODULO_PRIVACY);
			templateData.put("SOCIETA_SEL",societa_sel);			
			templateData.put("PUNTO_B",punto_b);			
			templateData.put("PUNTO_C",punto_c);			
			salvaFormatoWord(MODULO_PRIVACY+"_"+fileNameNew, PAGE_STAMPA, templateData,dataSourceParam);
		}
		
		try {
			templateData = loadVar(templateData, req);
			
			fileNameNew="./doc/"+req.getField(TAG_MODULO)+"_"+fileNameNew+".doc";
			templateData.put("filename",fileNameNew);
			
			_applicationSrv.displayPage(PAGE, templateData, res);
			
		} catch (Throwable e) {
			throw new AppCrash(e);
		}		
		
	}
	
}
