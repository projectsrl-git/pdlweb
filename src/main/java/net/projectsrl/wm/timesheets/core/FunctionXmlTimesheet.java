
package net.projectsrl.wm.timesheets.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;

import it.project.iride.core.FunctionMostraPaginaConDataset;
import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.gui.PageFactory;
import net.project.servlet.gui.Page_itf;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.PDFCreator;
import net.projectsrl.wm.db.TimesheetDAO;
import net.projectsrl.wm.utils.Utils;

public class FunctionXmlTimesheet extends FunctionMostraPaginaConDataset {
	
	private static final String DATASET_AZIENDE_DIPENDENTI = "DataSetAziendeDipendenti";

    public FunctionXmlTimesheet() {

        super();
    }

    public FunctionXmlTimesheet(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        super.elabora(req, res, userInfo);

    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

    	HttpSession session = req.getSession(true);
        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);

        String anno = req.getField("ANNO");
        String mese = req.getField("MESE");
        
        String tipoOO = req.getField("TIPOGST_OO");
        String tipoOS = req.getField("TIPOGST_OS");
        String tipoAI = req.getField("TIPOGST_AI");
        String tipoFP = req.getField("TIPOGST_FP");
        String tipologiaGST="";
        
        if (tipoOO.equals("") && tipoOS.equals("") && tipoAI.equals("") && tipoFP.equals("")){
        	templateData.put("TIPOLOGIA_GST", "");
        }else{
        	if (!tipoOO.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoOO+"'";
            }	 
        	if (!tipoOS.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoOS+"'";
            }	 
        	if (!tipoAI.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoAI+"'";
            }	 
        	if (!tipoFP.trim().equalsIgnoreCase("")) {
        		tipologiaGST = tipologiaGST + " and TIPO='"+tipoFP+"'";
            }	 
        	if(tipologiaGST.startsWith(" and ")){
        		tipologiaGST=" "+tipologiaGST.substring(4);
        	}
        	tipologiaGST=tipologiaGST.replace("and", "or");
        	templateData.put("TIPOLOGIA_GST", "AND ("+tipologiaGST+") ");
        }
        
        templateData.put("TIPOGST_OO", tipoOO);
        templateData.put("TIPOGST_OS", tipoOS);
        templateData.put("TIPOGST_AI", tipoAI);
        templateData.put("TIPOGST_FP", tipoFP);

        
        String codRis = (String) session.getAttribute("ID_DIPENDENTE_SESSIONE");
        templateData.put("ANNO", anno);
        templateData.put("MESE", mese);
        
        String risorsaSelezionata = req.getField("ID_DIPENDENTE_SESSIONE");
        String risorsaEsatta="";
        if (!risorsaSelezionata.equals("")){
            templateData.put("ID_DIPENDENTE_SESSIONE", risorsaSelezionata);
            risorsaEsatta=risorsaSelezionata;
        }else{
        	templateData.put("ID_DIPENDENTE_SESSIONE", codRis);
        	risorsaEsatta=codRis;
        }
        
        
        templateData.put("AZIENDA",getAzienda(risorsaEsatta));
        
        session.setAttribute("ANNO_STAMPA_TIMESHEET",anno);        
        session.setAttribute("MESE_STAMPA_TIMESHEET",mese);        
        session.setAttribute("AZIENDA_STAMPA_TIMESHEET",getAzienda(risorsaEsatta));        
        session.setAttribute("RISORSA_STAMPA_TIMESHEET",risorsaEsatta);
        
        templateData.put("righeXml", creaRigheXml(anno, mese, risorsaEsatta, getAzienda(risorsaEsatta), tipologiaGST));
        
        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }
    
    
    private String getAzienda(String risorsaEsatta) throws AppCrash {
        String aziendaTrovata = "";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_AZIENDE_DIPENDENTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE", risorsaEsatta);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                aziendaTrovata = dbRow.getField("AZIENDA").toString().trim();
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
        return aziendaTrovata;
    }
    

    private List<String> creaRigheXml(String anno, String mese, String codiRis, String azienda, String tipologiaGST) throws AppCrash {

        DataSet_itf dataSetRiga = null;

        List<String> righeXml = new ArrayList<String>();
        String rigaBaseConCodiciColonna = "";

        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dataSetRiga = dsFactory.makeDataSet("", "DataSetTimesht");
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("ID_DIPENDENTE_SESSIONE", codiRis);
            parameters.put("ANNO", anno);
            parameters.put("MESE", mese);
            parameters.put("AZIENDA", azienda);
            if (tipologiaGST.equals("")){
            	parameters.put("TIPOLOGIA_GST", "");
            }else{
            	parameters.put("TIPOLOGIA_GST", " AND ("+tipologiaGST+")");
            }
            
            dataSetRiga.setParam(parameters);
            dataSetRiga.open();

            rigaBaseConCodiciColonna = setRigaBaseConCodiciColonna(anno, mese, azienda);

            String idCommessa = "";
            String commessa = "";
            String compcomm = "";
            String dsccompcomm = "";
            String riga = "";
            String valida = "";

            // il dataset è ordinato per COMMMESSA + COMPCOMM
            String elementi="";
            
            while (dataSetRiga.hasMoreElements()) {
            	elementi="si";
                Row_itf dbRow = (Row_itf) dataSetRiga.nextElement();

                // Cambia l'attivita'?
                //
                if (!dbRow.getField("IDCOMMESSA").toString().trim().equals(idCommessa)
                        || !dbRow.getField("COMPCOMM").toString().trim().equals(compcomm)) {

                    // se cambia l'attivita inizio una nuova riga
                    if (!idCommessa.equals("")) {
                        // e chiudo quella precedente
                        riga = replaceColonnaZeroEColoriAlert(riga);
                        riga += " />\n";
                        righeXml.add(riga);
                    }

                    idCommessa = dbRow.getField("IDCOMMESSA").toString().trim();
                    commessa = dbRow.getField("COMMESSA").toString().trim();
                    compcomm = dbRow.getField("COMPCOMM").toString().trim();
                    dsccompcomm = dbRow.getField("DSCOMPCOMM").toString().trim();
                    valida = dbRow.getField("IDCOMPXCOM").toString().trim();
                    if (valida.equals("S")){
                    	riga = "<I Height='25' KEY='" + idCommessa + "," + commessa + "," + compcomm + "," + dsccompcomm
	                            + "' COMMESSA_ATTIVITA='   " + dsccompcomm + "' COMMESSA_ATTIVITABackground='#FFFFFF' "
	                            + " ADD='||||||astro?FUNCTIONID=CancellazioneAttivita&IDCOMMESSA="
	                            + idCommessa + "&COMPCOMM=" + compcomm + "&MESE=" + mese + "&ANNO=" + anno
	                            + "|_top' ADDBackground='#FFFFFF'" + rigaBaseConCodiciColonna;
                    }else{
	                    riga = "<I Height='25' KEY='" + idCommessa + "," + commessa + "," + compcomm + "," + dsccompcomm
	                            + "' COMMESSA_ATTIVITA='   " + dsccompcomm + "' COMMESSA_ATTIVITABackground='#FFFFFF' "
	                            + " ADD='|img/minus_ico.gif|||||astro?FUNCTIONID=CancellazioneAttivita&IDCOMMESSA="
	                            + idCommessa + "&COMPCOMM=" + compcomm + "&MESE=" + mese + "&ANNO=" + anno
	                            + "|_top' ADDBackground='#FFFFFF' ADDTip='Rimuovi attivita' " + rigaBaseConCodiciColonna;
                    }
                }

                String data = dbRow.getField("DATA").toString().trim();
                String ore = "0";
                if (dbRow.getField("ORE")==null){
                	ore = "0";
                }else{
                	ore = dbRow.getField("ORE").toString().trim();
                }
                
                String minuti = dbRow.getField("MINUTI_DEC").toString().trim();
                String giorno = data.substring(0, 2);
                String flgfatt = dbRow.getField("FLGFATT").toString().trim();
                String bloccato = dbRow.getField("B_BLOCCATO").toString().trim();

                if (giorno.equals("")) {
                    continue;
                }

                riga = replaceColonnaValoreEColore(riga, "G" + giorno, ore + "." + minuti, flgfatt.equals("true")
                        || bloccato.equals("true"));

            }
            

            // per legare attività predefinite
            if (elementi.equals("")){
	            if (!dataSetRiga.hasMoreElements()) {
		            DataSetFactory dsFactory2 = DataSetFactory.getInstance();
		            dataSetRiga = dsFactory2.makeDataSet("", "DataSetAggiungiAttivita");
		            HashMap<String, String> parameters2 = new HashMap<String, String>();
		            parameters2.put("DIPENDENTE_ASSOCIATO_ALLA_COMMESSA", codiRis);
		            dataSetRiga.setParam(parameters2);
		            dataSetRiga.open();
		            while (dataSetRiga.hasMoreElements()) {
		                Row_itf dbRow = (Row_itf) dataSetRiga.nextElement();
		
		                // Cambia l'attivita'?
		                //
		                if (!dbRow.getField("ID_COMMESSA").toString().trim().equals(idCommessa)
		                        || !dbRow.getField("ID_ATTIVITA").toString().trim().equals(compcomm)) {
		
		                    // se cambia l'attivita inizio una nuova riga
		                    if (!idCommessa.equals("")) {
		                        // e chiudo quella precedente
		                        riga = replaceColonnaZeroEColoriAlert(riga);
		                        riga += " />\n";
		                        righeXml.add(riga);
		                    }
	
		                    idCommessa = dbRow.getField("ID_COMMESSA").toString().trim();
		                    commessa = dbRow.getField("ID_COMMESSA").toString().trim();
		                    compcomm = dbRow.getField("ID_ATTIVITA").toString().trim();
		                    dsccompcomm = dbRow.getField("DESCRI").toString().trim();
		                    valida = dbRow.getField("VALIDA").toString().trim();
		                    
		                    TimesheetDAO timesheetDAO = new TimesheetDAO();
		                    timesheetDAO.setField(TimesheetDAO.ANNO, anno);
		                    timesheetDAO.setField(TimesheetDAO.MESE, mese);
		                    timesheetDAO.setField(TimesheetDAO.IDCOMMESSA, idCommessa);
		                    timesheetDAO.setField(TimesheetDAO.COMMESSA, commessa);
		                    timesheetDAO.setField(TimesheetDAO.CODRIS, codiRis);
		                    timesheetDAO.setField(TimesheetDAO.IDRISUMANA, codiRis);
		                    timesheetDAO.setField(TimesheetDAO.COMPCOMM, compcomm);
		                    timesheetDAO.setField(TimesheetDAO.DSCOMPCOMM, dsccompcomm);
		                    timesheetDAO.setField(TimesheetDAO.IDCOMPXCOM, valida);
		                    timesheetDAO.setField(TimesheetDAO.DATA, anno + "/" + mese + "/01");
		                    timesheetDAO.setField(TimesheetDAO.IDTIMESHT, Utils.getUnique());
		                    timesheetDAO.insert();
		                    
		                    
		                    riga = "<I Height='25' KEY='" + idCommessa + "," + commessa + "," + compcomm + "," + dsccompcomm
		                            + "' COMMESSA_ATTIVITA='   " + dsccompcomm + "' COMMESSA_ATTIVITABackground='#FFFFFF' "
		                            + " ADD='||||||astro?FUNCTIONID=CancellazioneAttivita&IDCOMMESSA="
		                            + idCommessa + "&COMPCOMM=" + compcomm + "&MESE=" + mese + "&ANNO=" + anno
		                            + "|_top' ADDBackground='#FFFFFF' ADDTip='Rimuovi attivita' " + rigaBaseConCodiciColonna;
		                }
		
		              
		                String ore = "0";
		                String minuti = "0";
		                String giorno = "";
		                String flgfatt = "";
		                String bloccato = "";
		
		                if (giorno.equals("")) {
		                    continue;
		                }
		
		                riga = replaceColonnaValoreEColore(riga, "G" + giorno, ore + "." + minuti, flgfatt.equals("true")
		                        || bloccato.equals("true"));
		
		            }
	            }
            }
            // fine

            riga = replaceColonnaZeroEColoriAlert(riga);
            if (!riga.trim().equals("")) {
                riga += "  />";
                righeXml.add(riga);
            }

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            // chiude il dataset per il conteggio degli elementi trovati
            if (dataSetRiga != null) {
                try {
                    dataSetRiga.close();
                } catch (AppCrash ac) {
                    ac.logContext("FunctionTimesheet", "Errore nella close del dataset");
                }
            }

        }

        return righeXml;

    }

    private String setRigaBaseConCodiciColonna(String anno, String mese, String azienda) throws AppCrash {

        String rigaBaseConCodiciColonna = "";

        DataSetFactory dsFactory = DataSetFactory.getInstance();

        DataSet_itf dataSetColonna = dsFactory.makeDataSet("", "DataSetCalendarioLavorativoTimesheet");
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("ANNO", anno);
        parameters.put("MESE", mese);
        parameters.put("AZIENDA", azienda);
        dataSetColonna.setParam(parameters);
        dataSetColonna.open();
        while (dataSetColonna.hasMoreElements()) {
            Row_itf dbRowColonna = (Row_itf) dataSetColonna.nextElement();

            String codice = "G" + dbRowColonna.getField("DATA_GIORNO").toString().trim();
            rigaBaseConCodiciColonna += "[#" + codice + "#] ";

        }

        return rigaBaseConCodiciColonna;
    }

    private String replaceColonnaZeroEColoriAlert(String riga) {

        while (true) {

            int startCampo = riga.indexOf("[#");

            if (startCampo < 0) {
                break;
            }

            startCampo = startCampo + 2;

            int endCampo = riga.indexOf("#]");

            if (endCampo < 0) {
                break;
            }

            String campo = riga.substring(startCampo, endCampo);

            //String valore = "0";
            String valore = "";

            String campoConMarker = "[#" + campo + "#]";
            String colonnaZero = campo + "='" + valore + "' ";
            riga = riga.replace(campoConMarker, colonnaZero);
        }

        return riga;

    }

    private String replaceColonnaValoreEColore(String riga, String campo, String valore, boolean bloccato) {

        String campoConMarker = "[#" + campo + "#]";

        String colonnaValoreEColore = campo + "='" + valore + "' ";
        if (bloccato) {
            colonnaValoreEColore = colonnaValoreEColore + campo + "CanEdit='0' ";
        }

        return riga.replace(campoConMarker, colonnaValoreEColore);

    }
    
    protected HashMap<String, String> prepareWhereCondition(HashMap<String, String> templateData,
            HashMap<String, String> queryParameter) {

        return templateData;
    }

    protected String salvaFormatoPDF(String fileName, String page, HashMap templateData, Map[] dataSourceParam,
            String xmlName, String xslName) throws AppCrash {
    
        if (fileName.contains("Modulitrasferte")) {
            String allegato = templateData.get("AZIENDA_TENDINA") + "/" + templateData.get("DIPENDENTE") + "/"
                    + templateData.get("ID_MODTRASFERTA") + "/" + Utils.normalizeASCIIFilename(fileName) + ".pdf";
            String queryUpdate = "UPDATE MOD_TRASFERTE SET ALLEGATO='" + allegato + "', FILENAME='"
                    + Utils.normalizeASCIIFilename(fileName) + "' WHERE ID_MODTRASFERTA='"
                    + templateData.get("ID_MODTRASFERTA") + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
            fileName = _applicationSrv.getRoot() + Config.GetInstance().getProperty("cartella.moduli.trasferte")
                    + allegato;
        }
    
        if (fileName.contains("Rendicontazione")) {
            String allegato = templateData.get("AZIENDA_TENDINA") + "/" + templateData.get("DIPENDENTE") + "/"
                    + templateData.get("ID_RENDICONTAZIONE") + "/" + Utils.normalizeASCIIFilename(fileName) + ".pdf";
            String queryUpdate = "UPDATE RENDICONTAZIONI SET ALLEGATO='" + allegato + "', FILENAME='"
                    + Utils.normalizeASCIIFilename(fileName) + "' WHERE ID_RENDICONTAZIONE='"
                    + templateData.get("ID_RENDICONTAZIONE") + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
            fileName = _applicationSrv.getRoot() + Config.GetInstance().getProperty("cartella.moduli.rendicontazioni")
                    + allegato;
    
        }
    
        if (fileName.contains("InfortunioINAIL")) {
            fileName = _applicationSrv.getRoot() + Config.GetInstance().getProperty("Page." + page + ".storePath", "./")
                    + "/" + templateData.get("AZIENDA_TENDINA") + "/Infortuni_INAIL/"
                    + Utils.normalizeASCIIFilename(fileName) + ".pdf";
    
            String queryUpdate = "UPDATE INFORTUNIO_INAIL SET ALLEGATO='" + "areadocumenti/"
                    + templateData.get("AZIENDA_TENDINA") + "/Infortuni_INAIL/InfortunioINAIL_"
                    + templateData.get("TAGGANCIO") + ".pdf" + "', FILENAME='InfortunioINAIL_"
                    + templateData.get("TAGGANCIO") + ".pdf' WHERE TAGGANCIO='" + templateData.get("TAGGANCIO") + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
        }
    
        if (!fileName.contains("Modulitrasferte") && !fileName.contains("Rendicontazione")
                && !fileName.contains("InfortunioINAIL")) {
            fileName = _applicationSrv.getRoot() + Config.GetInstance().getProperty("Page." + page + ".storePath", "./")
                    + "/" + Utils.normalizeASCIIFilename(fileName) + ".pdf";
        }
    
        File baseDir = new File(_applicationSrv.getRoot());
        File xsltfile = new File(baseDir, "xsl/" + xslName);
        File xmltfile = new File(baseDir, "WEB-INF/template/" + xmlName);
    
        PageFactory pf = PageFactory.getInstance();
    
        Page_itf template = pf.makePage(page);
    
        template.setPageRootData(templateData);
        for (int i = 0; i < dataSourceParam.length; i++) {
            if (dataSourceParam[i] != null) {
                template.setDataSourceParam(dataSourceParam[i], i);
            }
        }
    
        writeByteArrayOutputStreamToPDFFile(fileName, template, xmltfile, xsltfile);
        return fileName;
    }

    private void writeByteArrayOutputStreamToPDFFile(String fileName, Page_itf template, File xmlFile, File xslFileName)
            throws AppCrash {
    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
        String xmlFileName = xmlFile.getName() + ".tmp";
        // Creazione file XML
        try {
            PrintWriter pw = new PrintWriter(baos);
            template.display(pw);
            pw.flush();
    
            FileOutputStream fos = new FileOutputStream(xmlFileName, false);
            OutputStreamWriter wrtout = new OutputStreamWriter(fos);
    
            String s = baos.toString("UTF-8");
    
            wrtout.write(s);
    
            wrtout.flush();
            wrtout.close();
    
        } catch (Throwable e) {
            throw new AppCrash(e);
        }
    
        File pdfFile = new File(fileName);
    
        // Creazione file PDF
        try {
            PDFCreator.convertXML2PDF(xmlFileName, xslFileName, pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FOPException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
