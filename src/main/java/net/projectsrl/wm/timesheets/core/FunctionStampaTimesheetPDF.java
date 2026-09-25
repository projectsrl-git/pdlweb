
package net.projectsrl.wm.timesheets.core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;

/**
 * Function FunctionStampaTimesheetPDF
 * 
 */
public class FunctionStampaTimesheetPDF extends FunctionXmlTimesheet {

    public static final String  PAGE_STAMPA_PDF       = "stampa_elenco_timesheet_pdf";
    public static final String  XML_NAME_PDF          = "elencotimesheetPDF.xml";
    public static final String  XSL_NAME_PDF_COMPLETA = "elencotimesheetPDF.xsl";
    private static final String DATASET_DIPENDENTI    = "DataSetRisorsaSelezionata";
    private static final String DATASET_PARAMETRI     = "DataSetParametriGiustificativi";
    private static final String DATASET_LOGO          = "DataSetAllegatiLogo";

    public FunctionStampaTimesheetPDF() {
        super();
    }

    public FunctionStampaTimesheetPDF(ApplicationServices_itf applServices, String functionID, String functionName) {

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
        templateData.put("ANNO_STAMPA_TIMESHEET", session.getAttribute("ANNO_STAMPA_TIMESHEET"));
        templateData.put("MESE_STAMPA_TIMESHEET", session.getAttribute("MESE_STAMPA_TIMESHEET"));
        templateData.put("AZIENDA_STAMPA_TIMESHEET", session.getAttribute("AZIENDA_STAMPA_TIMESHEET"));
        templateData.put("RISORSA_STAMPA_TIMESHEET", session.getAttribute("RISORSA_STAMPA_TIMESHEET"));

        String queryCodici = "";
        String stringaGST = "";
        String stringaSommaOreGST = "";
        String stringaSommaOreGSTNumero = "";
        String stringaTitoliOreGST = "";
        String stringaTitoliOreGSTNumero = "";
        String stringaCompostaOreGST = "";
        String stringaCompostaTotaliOreGST = "";
        String stringaSommaOreTotaliGST = "";
        int contatore = 0;
        DataSet_itf dataSetCodici = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSetCodici = dsFactory.makeDataSet("", DATASET_PARAMETRI);
            HashMap<String, String> params = new HashMap<String, String>();
            dataSetCodici.open();

            while (dataSetCodici.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSetCodici.nextElement();
                String parametro = dbRow.getField("CODICE").toString().trim().substring(3);
                String tipoParametro = dbRow.getField("TIPO").toString().trim();

                String descrizioneParametroSpace = StringUtils.rightPad(dbRow.getField("DESCRI").toString().trim(), 35);
                queryCodici = queryCodici + ";" + parametro;
                stringaGST = stringaGST + ", 0 AS ORE_" + parametro + " ";
                contatore = contatore + 1;
                stringaSommaOreGST = stringaSommaOreGST + ", SUM(ORE_" + parametro + ") AS ORE_" + parametro + " ";
                stringaSommaOreTotaliGST = stringaSommaOreTotaliGST + ", CAST(SUM(ORE_" + parametro
                        + ") AS decimal(3,2)) AS ORE_" + parametro + " ";
                String parametroSpace = "";
                if (parametro.length() == 1) {
                    parametroSpace = parametro + "  ";
                }
                if (parametro.length() == 2) {
                    parametroSpace = parametro + " ";
                }
                if (parametro.length() == 3) {
                    parametroSpace = parametro;
                }

                if (!tipoParametro.equals("OO") && !tipoParametro.equals("OS")) {
                    stringaCompostaOreGST = stringaCompostaOreGST + "+CASE SUM(ORE_" + parametro
                            + ") WHEN 0 THEN '' ELSE  '" + parametroSpace + "|'+RTRIM(CAST(CAST(SUM(ORE_" + parametro
                            + ") AS decimal(3,1)) AS char))+'|' END";
                }
                if (!tipoParametro.equals("OO") && !tipoParametro.equals("OS")) {
                    stringaCompostaTotaliOreGST = stringaCompostaTotaliOreGST + "+CASE SUM(ORE_" + parametro
                            + ") WHEN 0 THEN '' ELSE  '" + parametroSpace + descrizioneParametroSpace
                            + "|'+RTRIM(CAST(CAST(SUM(ORE_" + parametro + ") AS decimal(3,1)) AS char))+'|' END";
                }
            }
            templateData.put("STRINGA_GST", stringaGST);
            stringaSommaOreGST = stringaSommaOreGST + stringaSommaOreGSTNumero + stringaTitoliOreGST
                    + stringaTitoliOreGSTNumero;
            templateData.put("SOMME_ORE_STAMPA_TIMESHEET",
                    stringaSommaOreGST + "," + stringaCompostaOreGST.substring(1) + " AS ORE ");
            templateData.put("SOMME_ORE_TOTALI_STAMPA_TIMESHEET",
                    stringaSommaOreGST + "," + stringaCompostaTotaliOreGST.substring(1) + " AS ORE ");

            dataSetCodici.close();

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSetCodici != null) {
                try {
                    dataSetCodici.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
                }
            }
        }

        String query = "";
        String parametri = "";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_PARAMETRI);
            HashMap<String, String> params = new HashMap<String, String>();
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                String parametro = dbRow.getField("CODICE").toString().trim().substring(3);
                String stringaAs = (String) templateData.get("STRINGA_GST");
                int lenStringaAs = stringaAs.length() - 15;
                int fineParametro = 0;

                if (stringaAs.contains(parametro)) {
                    int inizioParametro = stringaAs.indexOf("ORE_" + parametro) - 5;
                    String singolo = stringaAs.substring(inizioParametro);
                    if (inizioParametro > lenStringaAs) {
                        fineParametro = stringaAs.length();
                    } else {
                        fineParametro = singolo.indexOf(",") + 2;
                    }

                    String parametriInizio = stringaAs.substring(0, inizioParametro);
                    String parametriFine = "";
                    if (inizioParametro > lenStringaAs) {
                        parametriFine = "";
                    } else {
                        parametriFine = stringaAs.substring(inizioParametro + fineParametro - 2);
                    }

                    String parametroAttuale = ", ISNULL(CASE ORE "
                            + " WHEN 0 THEN CAST(SUBSTRING(USCITA_1,1,2) AS INT)-CAST(SUBSTRING(ENTRATA_1,1,2) AS INT)+CAST(SUBSTRING(USCITA_2,1,2) AS INT)-CAST(SUBSTRING(ENTRATA_2,1,2) AS INT) "
                            + " ELSE COALESCE(ORE,0) END,0) AS ORE_" + parametro + " ";
                    parametri = parametriInizio + parametroAttuale + parametriFine;
                    parametri = parametri.replace(",,", ",");
                    parametri = parametri.replace(", ,", ",");
                }

                if (query.equals("")) {
                    query = "SELECT ";
                } else {
                    query = query + " UNION SELECT ";
                }
                query = query + " CL.*, RAGSOC," + " UPPER(SUBSTRING(GIORNO, 1, 1)) AS G_BREVE, "
                        + " SUBSTRING(CL.DATA, 9, LEN(CL.DATA))  AS DATA_GIORNO, " + " CASE SUBSTRING(CL.DATA, 6, 2) "
                        + " WHEN 01 THEN 'Gennaio' WHEN 02 THEN 'Febbraio' WHEN 03 THEN 'Marzo' WHEN 04 THEN 'Aprile' WHEN 05 THEN 'Maggio' WHEN 06 THEN 'Giugno' WHEN 07 THEN 'Luglio' WHEN 08 THEN 'Agosto' WHEN 09 THEN 'Settembre' WHEN 10 THEN 'Ottobre' WHEN 11 THEN 'Novembre' WHEN 12 THEN 'Dicembre' "
                        + "END AS MESE " + parametri + " FROM CALENDARIO_LAVORATIVO  AS CL "
                        + " LEFT OUTER JOIN TIMESHT AS TMS ON TMS.DATA=CL.DATA AND TMS.COMPCOMM='" + parametro + "'"
                        + " LEFT OUTER JOIN AZIENDE ON CODICE=AZIENDA "
                        + " LEFT OUTER JOIN PARA ON PARA.CODICE='GST'+COALESCE(COMPCOMM,'" + parametro + "') "
                        + " WHERE  CL.ANNO = '" + session.getAttribute("ANNO_STAMPA_TIMESHEET") + "'"
                        + " AND SUBSTRING(CL.DATA, 6, 2) = '" + session.getAttribute("MESE_STAMPA_TIMESHEET") + "'"
                        + " AND CL.AZIENDA = '" + session.getAttribute("AZIENDA_STAMPA_TIMESHEET") + "' " + " UNION "
                        + " SELECT CL.*, RAGSOC," + " UPPER(SUBSTRING(GIORNO, 1, 1)) AS G_BREVE, "
                        + " SUBSTRING(CL.DATA, 9, LEN(CL.DATA))  AS DATA_GIORNO, " + " CASE SUBSTRING(CL.DATA, 6, 2) "
                        + " WHEN 01 THEN 'Gennaio' WHEN 02 THEN 'Febbraio' WHEN 03 THEN 'Marzo' WHEN 04 THEN 'Aprile' WHEN 05 THEN 'Maggio' WHEN 06 THEN 'Giugno' WHEN 07 THEN 'Luglio' WHEN 08 THEN 'Agosto' WHEN 09 THEN 'Settembre' WHEN 10 THEN 'Ottobre' WHEN 11 THEN 'Novembre' WHEN 12 THEN 'Dicembre' "
                        + " END AS MESE " + parametri + " FROM CALENDARIO_LAVORATIVO  AS CL "
                        + " LEFT OUTER JOIN TIMESHT AS TMS ON TMS.DATA=CL.DATA AND TMS.COMPCOMM='" + parametro + "'"
                        + " LEFT OUTER JOIN AZIENDE ON CODICE=AZIENDA "
                        + " LEFT OUTER JOIN PARA ON PARA.CODICE='GST'+COALESCE(COMPCOMM,'" + parametro + "') "
                        + " WHERE  CL.ANNO = '" + session.getAttribute("ANNO_STAMPA_TIMESHEET") + "' "
                        + " AND SUBSTRING(CL.DATA, 6, 2) = '" + session.getAttribute("MESE_STAMPA_TIMESHEET") + "' "
                        + " AND CL.AZIENDA = '" + session.getAttribute("AZIENDA_STAMPA_TIMESHEET") + "' "
                        + " AND IDRISUMANA='" + session.getAttribute("RISORSA_STAMPA_TIMESHEET") + "'";
            }
            templateData.put("CONDIZIONE_QUERY", query);

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

        templateData.put("WHERE_CONDITION_RIMBORSI",
                "WHERE STATO_APPROVAZIONE='S' AND DIPENDENTE ='" + session.getAttribute("RISORSA_STAMPA_TIMESHEET")
                        + "' AND AZIENDA_TENDINA ='" + session.getAttribute("AZIENDA_STAMPA_TIMESHEET")
                        + "' AND SUBSTRING(DATA_RIMBORSO,6,2)='" + session.getAttribute("MESE_STAMPA_TIMESHEET")
                        + "' AND SUBSTRING(DATA_RIMBORSO,1,4)='" + session.getAttribute("ANNO_STAMPA_TIMESHEET") + "'");

        templateData.put("aggiornato_a", strDataOra);
        String logo = "";
        String logoAzienda = getLogo(req.getField("AZIENDA_TENDINA"));
        logo = _applicationSrv.getRoot() + "areadocumenti\\" + req.getField("AZIENDA_TENDINA") + "\\" + logoAzienda;
        if (logoAzienda.equals("")) {
            logoAzienda = getLogo((String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
            logo = _applicationSrv.getRoot() + "areadocumenti\\"
                    + (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE") + "\\" + logoAzienda;
        }

        Map dataSourceParamPDF[] = setPageDatasetParam(PAGE_STAMPA_PDF, req, templateData);

        String fileName = "Timesheet";
        salvaFormatoPDFInDiversiModuli(fileName, PAGE_STAMPA_PDF, templateData, dataSourceParamPDF);

        try {
            templateData = setTemplateDataFromRequest(templateData, req);

            fileName = "Timesheet";
            fileName = "./doc/" + fileName + ".pdf";
            templateData.put("filename", fileName);

            String nominativo = getNominativoDipendente((String) session.getAttribute("RISORSA_STAMPA_TIMESHEET"));
            readPDFFileTimesheet(fileName, "Timesheet", "FunctionStampaTimesheetPDF", res,
                    (String) session.getAttribute("ANNO_STAMPA_TIMESHEET"),
                    (String) session.getAttribute("MESE_STAMPA_TIMESHEET"), nominativo);
            res.getOutputStream().flush();
        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            throw ac;
        }

    }

    protected @SuppressWarnings("unchecked") void salvaFormatoPDFInDiversiModuli(String fileName, String page_stampa2,
            HashMap templateData, Map[] dataSourceParam) throws AppCrash {

        salvaFormatoPDF(fileName, PAGE_STAMPA_PDF, templateData, dataSourceParam, XML_NAME_PDF, XSL_NAME_PDF_COMPLETA);
    }

    private String getNominativoDipendente(String idvoucher_atch) throws AppCrash {

        String nomeDipendente = "";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_DIPENDENTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE_SESSIONE", idvoucher_atch);
            dataSet.setParam(params);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                nomeDipendente = dbRow.getField("NOMINATIVO").toString().trim();
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

        return nomeDipendente;
    }

    private String getLogo(String aziendaLogo) throws AppCrash {

        String dati = "";
        String idTrovato = "";
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
                if (aziendaLogo.equals(idTrovato)) {
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

    protected void readPDFFileTimesheet(String fileName, String setHeaderElenco, String functionName,
            SsbServletResponse res, String anno, String mese, String risorsa) throws AppCrash {

        res.setContentType("application/donwload");
        res.setHeader("Content-Disposition",
                "attachment; filename=\"" + setHeaderElenco + "_" + risorsa + "_" + mese + "_" + anno + ".pdf\"");

        InputStream ist = null;

        String text = "";
        try {
            ist = new FileInputStream(_applicationSrv.getRoot() + fileName);

            byte[] buffer = new byte[1024];
            int length;

            ServletOutputStream op = res.getOutputStream();
            while ((ist != null) && ((length = ist.read(buffer)) != -1)) {
                op.write(buffer, 0, length);
            }
            ist.close();

        } catch (Throwable e) {

            new AppCrash(e).logContext(functionName, "CRASH " + text);

        } finally {

            if (ist != null) {
                try {
                    ist.close();
                } catch (Throwable th) {
                    new AppCrash(_functionName + " errore nella chiusura di InputStream ist");
                }
            }

        }
    }
    
    
    

}
