package net.projectsrl.wm.anagraficaaziende.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoAziende
 * 
 */
public class FunctionInserimentoAziende extends FunctionInserimento {

	private static final String PAGE = "inserimento_aziende";
	

	public FunctionInserimentoAziende() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetAziendeMain");
	}

	public FunctionInserimentoAziende(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetAziendeMain");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
		

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("CODICE_PARENT",(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
			templateData.put("TAGGANCIO",Utils.getUnique());
			return templateData;
		}
		return templateData;
	}
	
	
	 @SuppressWarnings("unchecked")
		public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {
	        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
	        templateData.put("TABS", req.getField("TABS"));
	       
	        saveVarStandard(templateData, req,res);
	        updateAziendaModifica(req);
	        templateData.put("CODICE_ORIGINE",req.getField("CODICE"));
	        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
	 }
	

    
    private void updateAziendaModifica( SsbServletRequest req) throws AppCrash {
    	String taggancio=req.getField("TAGGANCIO");
    	String codiceNew=req.getField("CODICE");
    	String codiceOrigine=req.getField("CODICE_ORIGINE");
    	String sqlUpdate ="UPDATE APPROVATORI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE APPROVAZIONI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE APPROVAZIONI_RENDICONTAZIONI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE AZIENDE_VISITATORI SET CODICE_PARENT='"+codiceNew+"' WHERE CODICE_PARENT = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE BADGE SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE CALENDARIO_LAVORATIVO SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE CURRICUL SET AZIENDA_CV='"+codiceNew+"' WHERE AZIENDA_CV = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE CURRICUL SET AZIENDA_INSERIMENTO='"+codiceNew+"' WHERE AZIENDA_INSERIMENTO = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE DOCAZIENDA SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE DOCAZIENDA_ATCH SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE ELENCO_FILE SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE FERIE_PERMESSI SET AZIENDA_TENDINA='"+codiceNew+"' WHERE AZIENDA_TENDINA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE FERIE_PERMESSI_ATCH SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE IMPOSTAZIONI_BASE SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE IMPOSTAZIONI_FERIEPERMESSI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE IMPOSTAZIONI_RENDICONTAZIONI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE IMPOSTAZIONI_STRAORDINARI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE MALATTIE_INFORTUNI SET AZIENDA_TENDINA='"+codiceNew+"' WHERE AZIENDA_TENDINA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE MALATTIE_INFORTUNI_ATCH SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE MOD_TRASFERTE SET AZIENDA_TENDINA='"+codiceNew+"' WHERE AZIENDA_TENDINA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE MOD_TRASFERTE_ATCH SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE MODULISTICA_ATCH SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE PARA SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE PROFILO_ORARIO SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE RENDICONTAZIONI SET AZIENDA_TENDINA='"+codiceNew+"' WHERE AZIENDA_TENDINA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE RENDICONTAZIONI_ATCH SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE RICHIEDENTI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE SEDI_AZIENDALI SET CODICE_PARENT='"+codiceNew+"' WHERE CODICE_PARENT = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE STRAORDINARI SET AZIENDA_TENDINA='"+codiceNew+"' WHERE AZIENDA_TENDINA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE TRASFERTE SET AZIENDA_TENDINA='"+codiceNew+"' WHERE AZIENDA_TENDINA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE TRASFERTE_ATCH SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		sqlUpdate ="UPDATE UTENTI SET AZIENDA='"+codiceNew+"' WHERE AZIENDA = '"+codiceOrigine+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		
		String sqlDelete1 ="DELETE FROM AZIENDE_ATECO WHERE DAGGANCIO = '"+taggancio+"' AND ATECO=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete1);
		String sqlDelete2 ="DELETE FROM AZIENDE_CCNL WHERE DAGGANCIO = '"+taggancio+"' AND CCNL=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete2);
		String sqlDelete3 ="DELETE FROM AZIENDE_INPS WHERE DAGGANCIO = '"+taggancio+"' AND INPS=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete3);
		String sqlDelete4 ="DELETE FROM AZIENDE_BANCHE WHERE DAGGANCIO = '"+taggancio+"' AND NOME_BANCA=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete4);
		String sqlDelete5 ="DELETE FROM AZIENDE_ENTEA WHERE DAGGANCIO = '"+taggancio+"' AND ENTEA=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete5);
		String sqlDelete6 ="DELETE FROM AZIENDE_ENTEP WHERE DAGGANCIO = '"+taggancio+"' AND ENTEP=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete6);
		String sqlDelete7 ="DELETE FROM AZIENDE_CENTRI_COSTO WHERE DAGGANCIO = '"+taggancio+"' AND CENTRO_COSTO_CODICE=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete7);
		String sqlDelete8 ="DELETE FROM AZIENDE_TIPI_COSTO WHERE DAGGANCIO = '"+taggancio+"' AND TIPO_COSTO_CODICE=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete8);
		String sqlDelete9 ="DELETE FROM AZIENDE_PAT WHERE DAGGANCIO = '"+taggancio+"' AND PAT=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete9);
		String sqlDelete10 ="DELETE FROM AZIENDE_VOCI_TARIFFA WHERE DAGGANCIO = '"+taggancio+"' AND VOCE_TARIFFA=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete10);
		
		
    }
}

