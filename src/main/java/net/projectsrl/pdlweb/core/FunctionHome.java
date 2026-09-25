
package net.projectsrl.pdlweb.core;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import project.misc.Utils;

public class FunctionHome extends net.projectsrl.bow.core.FunctionHome {

    public FunctionHome(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        String listaAziende = getSpecificUserInfo(userInfo).getField("LISTA_AZIENDE_UTENTE");
        String[] arrayAziende = listaAziende.split(",");

        String page = getPageName();
        String jsonTree = createJsonTree(userInfo);
        templateData.put("jsonTree", jsonTree);

        String directory = _applicationSrv.getRoot() + "/img/";

        String fileImgPlanimetria = "planimetria.default.png";
        for (int i = 0; i < arrayAziende.length; i++) {
            if (new File(directory + "planimetria." + arrayAziende[i] + ".png").exists()) {
                fileImgPlanimetria = "planimetria." + arrayAziende[i] + ".png";
                break;
            }

        }
        templateData.put("fileImgPlanimetria", fileImgPlanimetria);

        //templateData.put("PDL_APERTI", getPDL("OPE", userInfo)[0]);
        //templateData.put("PDL_ATTIVI", getPDL("ACT", userInfo)[1]);
        //templateData.put("PDL_SOSPESI", getPDL("SUS", userInfo)[2]);
        //templateData.put("PDL_CHIUSI", getPDL("CLO", userInfo)[3]);
        templateData.put("WHERECONDITION_AZIENDE",getSpecificUserInfo(userInfo).getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));

        templateData.put("DATA_OGGI", Utils.getStringDataOggi());

        //templateData.put("DATA_GRAFICO_HOME_X", getDatiGraficoHome(userInfo)[0]);
        //templateData.put("DATA_GRAFICO_HOME_APERTI", getDatiGraficoHome(userInfo)[1]);
        //templateData.put("DATA_GRAFICO_HOME_ATTIVI", getDatiGraficoHome(userInfo)[2]);
        //templateData.put("DATA_GRAFICO_HOME_SOSPESI", getDatiGraficoHome(userInfo)[3]);

        templateData.put("CODICE_TURNO_SELEZIONATO", req.getSession(false).getAttribute("CODICE_TURNO_SELEZIONATO"));

        _applicationSrv.displayPage(page, templateData, setPageDatasetParam(page, req, templateData), res);
    }

    private String[] getDatiGraficoHome(UserSecurityInfo userInfo) throws AppCrash {

        String[] dati = new String[4];
        Arrays.fill(dati, "");

        DataSet_itf dataSet = null;
        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DSGraficoHome");
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("WHERECONDITION_AZIENDE",
                    getSpecificUserInfo(userInfo).getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                dati[0] = dati[0] + "'" + dbRow.getField("DT_GIORNO_ITA") + "',";
                dati[1] = dati[1] + dbRow.getField("TOT_OPE") + ",";
                dati[2] = dati[2] + dbRow.getField("TOT_ACT") + ",";
                dati[3] = dati[3] + dbRow.getField("TOT_SUS") + ",";

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

    private String[] getPDL(String stato, UserSecurityInfo userInfo) throws AppCrash {

        String[] dati = new String[4];
        Arrays.fill(dati, "");
        DataSet_itf dataSet = null;
        try {

            DataSetFactory dsFactory = DataSetFactory.getInstance();

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DSGraficoTortaHome");
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("WHERECONDITION_AZIENDE",
                    getSpecificUserInfo(userInfo).getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));
            dataSet.setParam(params);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                dati[0] = dbRow.getField("TOT_OPE").toString().trim();
                dati[1] = dbRow.getField("TOT_ACT").toString().trim();
                dati[2] = dbRow.getField("TOT_SUS").toString().trim();
                dati[3] = dbRow.getField("TOT_CLO").toString().trim();
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


    private String createJsonTree(UserSecurityInfo userInfo) throws AppCrash {

        String dsName = "DSStrutturaTree";
        StringBuilder jsonTail = new StringBuilder();

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> params = new HashMap<String, String>();

            params.put(DafneCostanti_itf.WHERECONDITION_AZIENDE,
                    getSpecificUserInfo(userInfo).getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));
            dataSet.setParam(params);
            dataSet.open();

            int rowCounter = 0;
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                // { "id" : "ajson4", "parent" : "ajson2", "text" : "Child 2" },
                if (dbRow != null) {

                    if (rowCounter > 0) {
                        jsonTail.append(",");
                    }

                    String id = (String) dbRow.getField("id");
                    String parent = (String) dbRow.getField("parent");
                    String text = (String) dbRow.getField("node_text");
                    String icon = (String) dbRow.getField("icon");

                    jsonTail.append("{id:\"" + id + "\",parent:\"" + parent + "\",text:\"" + text + "\",icon:\"" + icon
                            + "\"}");

                    rowCounter++;
                }
            }
        } catch (AppCrash ac) {
            ac.logContext(this.getClass().getName(),
                    "Errore nella ricerca dell'ultimo progressivo del dataset " + dsName);
            throw ac;
        } finally {
            // chiude il dataset per il conteggio degli elementi trovati
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset " + dsName);
                }
            }
        }

        return jsonTail.toString();

    }

}
