package net.projectsrl.wm.timesheets.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionAssociaDipendente
 * 
 */
public class FunctionTimbratureAttivitaPopup extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "timbratureattivita_popup";
	

	public FunctionTimbratureAttivitaPopup() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetTimeshtTAP");
	}

	public FunctionTimbratureAttivitaPopup(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetTimeshtTAP");
	}
	
	public void mostra(SsbServletRequest req, SsbServletResponse res,UserSecurityInfo userInfo) throws AppCrash {
        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
   	 	templateData.put("ID_COMMESSA",req.getField("COMMESSA_ATTIVITA_ID"));
   	 	templateData.put("ANNO",req.getField("ANNO_T"));
   	 	templateData.put("MESE",req.getField("MESE_T"));
        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData),
                res);
	}
	
	 public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		    HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
		    if (req.getField("DATA_DAL").equals(req.getField("DATA_AL"))){
		    	req.setField("DATA", req.getField("DATA_DAL"));
		    	saveVarStandard(templateData, req,res);
		    }else{
		    	String dataDalCompleta = req.getField("DATA_DAL");
		    	String dataAlCompleta = req.getField("DATA_AL");
		    	String dataDalAnno = dataDalCompleta.substring(6);
		    	String dataDalMese = dataDalCompleta.substring(3,5);
		    	String idTimesht=Utils.getUnique();
		    	String idRisumana=req.getField("IDRISUMANA");
		    	String idCommessa=req.getField("IDCOMMESSA");
		    	String ore=req.getField("ORE");
		    	String codris=req.getField("CODRIS");
		    	String commmessa=req.getField("COMMESSA");
		    	String compComm=req.getField("COMPCOMM");
		    	
		    	int mesi=0;
		    	int i =0;
		    	

		    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		    	Date d1 = null;
		    	Date d2 = null;
		    	try {
		    	    d1 = format.parse(dataDalCompleta);
		    	    d2 = format.parse(dataAlCompleta);
		    	} catch (ParseException e) {
		    	    e.printStackTrace();
		    	}    
		    	long diff = d2.getTime() - d1.getTime();
		    	long diffDays = diff / (60 * 60 * 1000 * 24);    
		    	int fine = (int) diffDays+1;

		    	
		    	
		    	Calendar startCalendar = new GregorianCalendar();
		    	startCalendar.setTime(d1);
		    	Calendar endCalendar = new GregorianCalendar();
		    	endCalendar.setTime(d2);

		    	int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		    	mesi = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		    	
		    	String dataDaScrivere=dataDalCompleta;
		    	while (i<fine){
		    		
		    		String deleteGiorno = "delete from timesht where idrisumana='"+idRisumana+"' and idcommessa='"+idCommessa+"' and compcomm='"+compComm+"' and data='"+Utils.ribaltaData(dataDaScrivere)+"'";
	         	    net.projectsrl.wm.utils.WMUtils.executeQuery(deleteGiorno);
	         	    
			    	String insertGiorno = "insert into timesht (IDTIMESHT,IDRISUMANA,IDCOMMESSA,IDCOMPXCOM,DATA,MINUTI,ORE,ANNO,MESE,CODRIS,COMMESSA,COMPCOMM,DSCOMPCOMM) values "
			    		+ "('"+idTimesht+"','"+idRisumana+"','"+idCommessa+"','','"+Utils.ribaltaData(dataDaScrivere)+"',0,"+ore+",'"+dataDalAnno+"','"+dataDalMese+"','"+codris+"','"+commmessa+"','"+compComm+"','')";
	         	    net.projectsrl.wm.utils.WMUtils.executeQuery(insertGiorno);
	         	    
	         	    String updateGiorno =" update timesht set idcompxcom=coalesce((select COMMESSA_X_ATTIVITA.predefinita from COMMESSA_X_DIPENDENTE left outer join COMMESSA_X_ATTIVITA on COMMESSA_X_ATTIVITA.ID_COMMESSA = COMMESSA_X_DIPENDENTE.id_Commessa"
	         	   		+ " where ID_DIPENDENTE='"+idRisumana+"' and ID_ATTIVITA='"+compComm+"'),''), dscompcomm=(select descri from para where codice ='GST"+compComm+"')"
	         	   		+ "where IDRisUmana='"+idRisumana+"' and IDCommessa='"+idCommessa+"' and compcomm='"+compComm+"' and data='"+Utils.ribaltaData(dataDaScrivere)+"'";    	
	         	    net.projectsrl.wm.utils.WMUtils.executeQuery(updateGiorno);
	         	    
	         	    String prossimoGiorno="";
	         	    String prossimoMese=dataDaScrivere.substring(3,5);
	         	    String prossimoAnno=dataDaScrivere.substring(6);
	         	    
	         	    
	         	    if (mesi>=1){
		         	    if ((Integer.parseInt(dataDaScrivere.substring(0,2))+1)>Integer.parseInt(Utils.getUltimoGiornoMese(dataDaScrivere.substring(3,5), dataDaScrivere.substring(6)))){
		         	    	prossimoGiorno="01";
		         	    	if ((Integer.parseInt(dataDaScrivere.substring(3,5))+1)>12){
		         	    		prossimoMese="01";
		         	    		prossimoAnno=Integer.toString(Integer.parseInt(prossimoAnno)+1);
		         	    	}else{
		         	    		prossimoMese=Integer.toString(Integer.parseInt(prossimoMese)+1);
		         	    	}
		         	    }else{
		         	    	prossimoGiorno=Integer.toString(Integer.parseInt(dataDaScrivere.substring(0,2))+1);
		         	    }
	         	    }else{        	    
	         	    	prossimoGiorno=Integer.toString(Integer.parseInt(dataDaScrivere.substring(0,2))+1);
	         	    }
	         	    
	         	    

	         	    if (prossimoGiorno.equals("1") || prossimoGiorno.equals("2") || prossimoGiorno.equals("3") || prossimoGiorno.equals("4") || prossimoGiorno.equals("5") || prossimoGiorno.equals("6") || prossimoGiorno.equals("7") || prossimoGiorno.equals("8") || prossimoGiorno.equals("9")){
	         	    	prossimoGiorno="0"+prossimoGiorno;
	         	    }
	         	    
	         	    if (prossimoMese.equals("1") || prossimoMese.equals("2") || prossimoMese.equals("3") || prossimoMese.equals("4") || prossimoMese.equals("5") || prossimoMese.equals("6") || prossimoMese.equals("7") || prossimoMese.equals("8") || prossimoMese.equals("9")){
	         	    	prossimoMese="0"+prossimoMese;
	         	    }
	         	    
	         	    if (prossimoAnno.equals("1") || prossimoAnno.equals("2") || prossimoAnno.equals("3") || prossimoAnno.equals("4") || prossimoAnno.equals("5") || prossimoAnno.equals("6") || prossimoAnno.equals("7") || prossimoAnno.equals("8") || prossimoAnno.equals("9")){
	         	    	prossimoAnno="0"+prossimoAnno;
	         	    }


	         	    dataDaScrivere=prossimoGiorno+"/"+prossimoMese+"/"+prossimoAnno;
		    		++i;
		    	}
		    	
		    	
		    }
		    templateData.put("SALVATO", "Salvataggio effettuato con successo");
		    
		    String idTimbratura=req.getField("ID");
		   
		    String sqlUpdate ="";
		    if (req.getField("TIPO").equals("S")){
		    	sqlUpdate ="update timbrature set SCARTATA = (CASE WHEN SCARTATA='N' THEN 'S' ELSE 'N' END) where id_timbratura='"+idTimbratura+"'";
		    }else{
		    	sqlUpdate ="update timbrature set ENT_USC = (CASE WHEN ENT_USC='E' THEN 'U' ELSE 'E' END) where id_timbratura='"+idTimbratura+"'";
		    }
		    net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate);
		    String sqlDeleteVuoti="delete from timesht where compcomm=''";
		    net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteVuoti);
		    
		    
		    _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
		    }

}

