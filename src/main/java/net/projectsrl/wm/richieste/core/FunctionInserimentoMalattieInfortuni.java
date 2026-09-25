package net.projectsrl.wm.richieste.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.core.FunctionInserimentoSenzaControlloPreEsistenza;
import net.projectsrl.db.DbUtils;
import net.projectsrl.wm.utils.Utils;
import net.projectsrl.wm.utils.WMUtils;

/**
 * FunctionInserimentoMalattieInfortuni
 * 
 */
public class FunctionInserimentoMalattieInfortuni extends FunctionInserimentoSenzaControlloPreEsistenza {

	private static final String PAGE = "inserimento_malattieinfortuni";
	private static final String DATASET_FP = "DataSetGSTMalattieInfortuni";
	private String _datasetTestata="";
	private static final String DATASET_DIP = "DataSetDipendentiCerca";
	

	public FunctionInserimentoMalattieInfortuni() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetMalattieInfortuni");
	}

	public FunctionInserimentoMalattieInfortuni(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetMalattieInfortuni");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);  
		
		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_MALATTIAINFORTUNIO", Utils.getUnique());
			templateData.put("ORE", getDatiPermesso(req.getField("TIPO_PERMESSO"))[0]);
			templateData.put("DOCUMENTAZIONE", getDatiPermesso(req.getField("TIPO_PERMESSO"))[1]);
			templateData.put("AZIENDA", WMUtils.getDatiDipendente(req.getField("ID_DIPENDENTE")));
			if (getSessionRole(req).equals("D")){
				templateData.put("DIPENDENTE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
				templateData.put("AZIENDA", WMUtils.getDatiDipendente((String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE")));
			}else{
				templateData.put("AZIENDA", WMUtils.getDatiDipendente(req.getField("ID_DIPENDENTE")));
			}
			if (templateData.get("AZIENDA_TENDINA")==null || templateData.get("AZIENDA_TENDINA").equals("")){
				templateData.put("AZIENDA_TENDINA", WMUtils.getDatiDipendente((String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE")));
			}
		}
		putIdInSession(req, templateData);
		return templateData;

	}
	
	
	
	@SuppressWarnings("unchecked")
	public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

		HashMap templateData = (HashMap) setCommonTags(req, userInfo);

		if (refresh(PAGE,req, templateData, res)) {
			return;
		}
		
	    // pulisce template dettagli
		//
		templateData = pulisceTuttiTemplateDettagli(templateData);

		templateData = saveVarStandard(templateData, req, res);
		
		

		if(req.getField("DATA_DAL").equals(req.getField("DATA_AL"))){
			
			String deleteGiorno = "delete from TIMESHT where idrisumana = '"+req.getField("DIPENDENTE")+"' and idcommessa='00000000000000000000' and data = '"+req.getField("DATA_DAL")+"' and compcomm='"+req.getField("ID_MALATTIAINFORTUNIO")+"'";
     	    net.projectsrl.wm.utils.WMUtils.executeQuery(deleteGiorno);
     	    
			String queryInsertTimesheet="INSERT INTO TIMESHT (IDTIMESHT, IDRISUMANA,IDCOMMESSA,IDCOMPXCOM,DATA,MINUTI,ORE,ANNO,MESE,CODRIS,COMMESSA,COMPCOMM) SELECT"
					+ "'"+Utils.getUnique()+"' AS IDTIMESHT,"
					+ "DIPENDENTE AS IDRISUMANA,"
					+ "'00000000000000000000' AS IDCOMMESSA,"
					+ "'' AS IDCOMPXCOM,"
					+ "DATA_DAL AS DATA,"
					+ "'0' AS MINUTI,"
					+ "ORE AS ORE,"
					+ "SUBSTRING(DATA_DAL,1,4) AS ANNO,"
					+ "SUBSTRING(DATA_DAL,6,2) AS MESE,"
					+ "DIPENDENTE AS CODRIS,"
					+ "'00000000000000000000' AS COMMESSA,"
					+ "TIPO_PERMESSO AS COMPCOMM "
					+ "FROM MALATTIE_INFORTUNI WHERE ID_MALATTIAINFORTUNIO='"+req.getField("ID_MALATTIAINFORTUNIO")+"'";
			try {
				net.projectsrl.wm.utils.WMUtils.executeQuery(queryInsertTimesheet);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String dataDalCompleta = req.getField("DATA_DAL");
	    	String dataAlCompleta = req.getField("DATA_AL");
	    	String dataDalAnno = dataDalCompleta.substring(6);
	    	String dataDalMese = dataDalCompleta.substring(3,5);
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
	    		
	    		String deleteGiorno = "delete from TIMESHT where idrisumana = '"+req.getField("DIPENDENTE")+"' and idcommessa='00000000000000000000' and data = '"+Utils.ribaltaData(dataDaScrivere)+"' and compcomm='"+req.getField("ID_MALATTIAINFORTUNIO")+"'";
         	    net.projectsrl.wm.utils.WMUtils.executeQuery(deleteGiorno);
         	    
	    		String queryInsertTimesheet="INSERT INTO TIMESHT (IDTIMESHT, IDRISUMANA,IDCOMMESSA,IDCOMPXCOM,DATA,MINUTI,ORE,ANNO,MESE,CODRIS,COMMESSA,COMPCOMM) SELECT"
						+ " '"+Utils.getUnique()+"' AS IDTIMESHT,"
						+ "DIPENDENTE AS IDRISUMANA,"
						+ "'00000000000000000000' AS IDCOMMESSA,"
						+ "'' AS IDCOMPXCOM,"
						+ "'"+Utils.ribaltaData(dataDaScrivere)+"' AS DATA,"
						+ "'0' AS MINUTI,"
						+ "ORE AS ORE,"
						+ "SUBSTRING(DATA_DAL,1,4) AS ANNO,"
						+ "SUBSTRING(DATA_DAL,6,2) AS MESE,"
						+ "DIPENDENTE AS CODRIS,"
						+ "'00000000000000000000' AS COMMESSA,"
						+ "TIPO_PERMESSO AS COMPCOMM "
						+ "FROM MALATTIE_INFORTUNI WHERE ID_MALATTIAINFORTUNIO='"+req.getField("ID_MALATTIAINFORTUNIO")+"'";
	    			try{
						net.projectsrl.wm.utils.WMUtils.executeQuery(queryInsertTimesheet);
					} catch (AppCrash e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
         	    
	    		
         	    //String updateGiorno =" update timesht set idcompxcom=coalesce((select COMMESSA_X_ATTIVITA.predefinita from COMMESSA_X_DIPENDENTE left outer join COMMESSA_X_ATTIVITA on COMMESSA_X_ATTIVITA.ID_COMMESSA = COMMESSA_X_DIPENDENTE.id_Commessa"
         	   	//	+ " where ID_DIPENDENTE='"+idRisumana+"' and ID_ATTIVITA='"+compComm+"'),''), dscompcomm=(select descri from para where codice ='GST"+compComm+"')"
         	   	//	+ "where IDRisUmana='"+idRisumana+"' and IDCommessa='"+idCommessa+"' and compcomm='"+compComm+"' and data='"+Utils.ribaltaData(dataDaScrivere)+"'";    	
         	    //net.projectsrl.wm.utils.WMUtils.executeQuery(updateGiorno);
         	    
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
		
		
		
		
		templateData=nessunaOpzioniDettagli(templateData);
		templateData.put("RUOLO_SESSIONE", getSessionRole(req));
		templateData.put("ID_DIPENDENTE_SESSIONE", (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE"));
        templateData.put("AZIENDA_SESSIONE", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
		templateData.put("SALVATO", "Salvataggio effettuato con successo");
		templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore "+Utils.getOrario());
		_applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req,templateData), res);
		
	}
	
	@SuppressWarnings("unchecked")
	private HashMap pulisceTuttiTemplateDettagli(HashMap templateData) throws AppCrash {
		
		if (!ciSonoDettagli()) {
			return templateData;
		}		
		
		String[] arr = getArrayDsDettagli();

		for (int i = 0; i < arr.length; i++) {
			String dsName=arr[i];
			
			clearTemplateFieldFromDAO(templateData,DbUtils.makeDAOFromDsName(dsName));
			
		}
		
		return templateData;
	}
	
	private String[] getArrayDsDettagli() {
		String elencoDataSet=Config.GetInstance().getProperty("DS." + _datasetTestata + ".ElencoDSDettagli");
		String[] arr = elencoDataSet.split("\\,");
		return arr;
	}

	
	
	@SuppressWarnings("unchecked")
    private void putIdInSession(SsbServletRequest req, HashMap templateData) throws AppCrash {

        String idMalattiaInfortunio = (String) templateData.get("ID_MALATTIAINFORTUNIO");
        if (idMalattiaInfortunio == null || idMalattiaInfortunio.equals("")) {
        	idMalattiaInfortunio=req.getField("ID_MALATTIAINFORTUNIO");  
            if (idMalattiaInfortunio == null || idMalattiaInfortunio.equals("")) {
                return;
            }            
        }
        
        req.getSession(false).setAttribute(Costanti_itf.ID_MALATTIAINFORTUNIO_SESSIONE, idMalattiaInfortunio);   
        if(getSessionRole(req).equals("D")){
        	templateData.put("AZIENDA_UPLOAD",(String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI"));
        	 req.getSession(false).setAttribute(Costanti_itf.AZIENDA_FERIEPERMESSO, (String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI"));
        }else{
        	if ((String) templateData.get("DIPENDENTE")==null){
        		templateData.put("AZIENDA", WMUtils.getDatiDipendente(""));
                req.getSession(false).setAttribute(Costanti_itf.AZIENDA_FERIEPERMESSO, WMUtils.getDatiDipendente(""));
        	}else{
        		templateData.put("AZIENDA", WMUtils.getDatiDipendente((String) templateData.get("DIPENDENTE")));
                req.getSession(false).setAttribute(Costanti_itf.AZIENDA_FERIEPERMESSO, WMUtils.getDatiDipendente((String) templateData.get("DIPENDENTE")));
        	}
        }
    }
	
	private String[] getDatiPermesso(String codPermesso) throws AppCrash {
        String[] dati = { "", ""};
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_FP);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("CODICE", codPermesso);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("CODICE").toString().trim();
                if (codPermesso.equals(idTrovato)){
                	String oreMinime=dbRow.getField("ORE_MINIME").toString().trim();
                	String documentazione=dbRow.getField("DOC").toString().trim();
                	dati[0] = oreMinime;
                	dati[1] = documentazione;
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

