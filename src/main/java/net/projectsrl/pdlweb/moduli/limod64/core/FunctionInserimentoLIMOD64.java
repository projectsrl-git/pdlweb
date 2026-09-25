
package net.projectsrl.pdlweb.moduli.limod64.core;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.errors.ParamCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.pdlweb.moduli.core.FunctionInserimentoLIMOD_base;
import net.projectsrl.pdlweb.moduli.limod64.db.LiMod64DAO;
import net.projectsrl.pdlweb.moduli.limod64.db.LiMod64DettagliDAO;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;
import net.projectsrl.webapp.authentication.MenuItem;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.webapp.core.WebAppUtils;

public class FunctionInserimentoLIMOD64 extends FunctionInserimentoLIMOD_base<LiMod64DAO> {
	
	private static final String DS_PROGRESSIVO_MODULO = "DSProgressivoLIMOD";
	private static final String DS_PROGRESSIVO_MODULO_REV = "DSProgressivoLIMODRev";
	private static final String DS_PROGRESSIVO_REVISION = "DSProgressivoRevisioneLIMOD";
	private static final String PARAM_TABLE_NAME      = "TABLE_NAME";
    private static final String PARAM_FIELD_NAME      = "FIELD_NAME";
    private static final String PARAM_NR_MODULO		  =  "NR_MODULO";
    private static final String PARAM_ID_MODULO_PARENT		  =  "ID_MODULO_PARENT";
    private static final String PARAM_CONDITION      = "PARAM_CONDITION";

    public FunctionInserimentoLIMOD64(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
        	
        	String turnoSelezionato = "";
        	if (req.getSession(false).getAttribute("CODICE_TURNO_SELEZIONATO")!=null){
        		turnoSelezionato = (String) req.getSession(false).getAttribute("CODICE_TURNO_SELEZIONATO");
        	}
        	
        	
        	String idModuloOrigin=req.getField("ID_MODULO_ORIGIN");
        	String idArea=req.getField("ID_AREA_ORIGIN");
        	String idImpianto=req.getField("ID_IMPIANTO_ORIGIN");
        	String idAzienda=req.getField("ID_AZIENDA_ORIGIN");
        	cancellaBozzeStessaArea(idArea);
        	String nrModulo = getNextSequentialNumber64("LIMOD64","NR_MODULO",idModuloOrigin);
        	String nrRevisione = getNextRevisionSequentialNumber("LIMOD64","NR_REV",nrModulo);
        	
            LiMod64DAO limod64 = new LiMod64DAO();

            limod64.setAttribute(LiMod64DAO.ID_MODULO, "");
            limod64.setAttribute(LiMod64DAO.NR_MODULO, nrModulo);
            limod64.setAttribute(LiMod64DAO.NR_REV, nrRevisione);
            limod64.setAttribute(LiMod64DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
            //limod64.setAttribute(LiMod64DAO.STATO, StatiRichiesta.DRAFT.getCode());
            limod64.setAttribute(LiMod64DAO.STATO, StatiRichiesta.APPROVED.getCode());
            //limod64.setAttribute(LiMod64DAO.STATO, StatiRichiesta.APPROVED.getCode());
            limod64.setAttribute(LiMod64DAO.ID_IMPIANTO, idImpianto);
            limod64.setAttribute(LiMod64DAO.ID_AZIENDA, idAzienda);
            limod64.setAttribute(LiMod64DAO.ID_AREA, idArea);
            limod64.setAttribute(LiMod64DAO.ID_MODULO_PARENT, idModuloOrigin);
            limod64.setAttribute(LiMod64DAO.TABELLA_PARENT, "LIMOD70");
            limod64.setAttribute(LiMod64DAO.CODICE_TURNO, turnoSelezionato);
            limod64.setAttribute(LiMod64DAO.DT_REV, project.misc.Utils.getStringDataOggi());
            limod64.setAttribute(LiMod64DAO.TS_INS, new Timestamp(System.currentTimeMillis()));
            limod64.setAttribute(LiMod64DAO.ID_UTENTE_INS, getSpecificUserInfo(userInfo).getIdUtente());
            limod64.setAttribute(LiMod64DAO.ID_UTENTE_INS, getSpecificUserInfo(userInfo).getIdUtente());
            
            limod64.insert();
            
            int idModulo=(int) limod64.getAttribute("ID_MODULO");
            String nrDettaglio = getNextSequentialNumber("LIMOD64_DETTAGLI","NR_DETTAGLIO");
            datiDettaglio(nrDettaglio,idModuloOrigin, idModulo,idArea,userInfo);
            
            PjNDAO_base rowToUpdate = new LiMod64DAO();
            String idRow = Integer.toString(idModulo);
            rowToUpdate.setAttribute(LiMod64DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), LiMod64DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

        } else {
            PjNDAO_base rowToUpdate = new LiMod64DAO();
            String idRow = req.getField(LiMod64DAO.ID_MODULO);
            rowToUpdate.setAttribute(LiMod64DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), LiMod64DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
            LiMod64DAO formDao) {

        try {
            PrintWriter out = res.getWriter();

            Integer id = (Integer) formDao.getAttribute(AliModDAO_base.ID_MODULO);

            String nr = (String) formDao.getAttribute(AliModDAO_base.NR_MODULO);
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"id\":" + id + ",\"nr\":'" + nr + "',\"aifa\":false}";
            
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }

    
    private String getNextSequentialNumber64(String table, String field, String idModuloOrigin) throws AppCrash {

        String dsName = DS_PROGRESSIVO_MODULO_REV;

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();
        Integer lastSequentialNumeber = new Integer(0);

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> param = new HashMap<String, String>();
            param.put(PARAM_TABLE_NAME, table);
            param.put(PARAM_FIELD_NAME, field);
            param.put(PARAM_ID_MODULO_PARENT, idModuloOrigin);
            if (table.contains("_DETTAGLI")){
            	param.put(PARAM_CONDITION, "");
            }else{
            	param.put(PARAM_CONDITION, " WHERE STATO='APP'");
            }
            dataSet.setParam(param);
            dataSet.open();

            if (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if (dbRow != null) {
                    String strLast = (String) dbRow.getField("ultimo");
                    if (strLast != null && !strLast.trim().equals("")) {
                        lastSequentialNumeber = Integer.valueOf(strLast);
                        //lastSequentialNumeber += 1;
                    }
                }
            }else{
            	dsName = DS_PROGRESSIVO_MODULO;
            	dataSet = dsFactory.makeDataSet("", dsName);

                param = new HashMap<String, String>();
                param.put(PARAM_TABLE_NAME, table);
                param.put(PARAM_FIELD_NAME, field);
                if (table.contains("_DETTAGLI")){
                	param.put(PARAM_CONDITION, "");
                }else{
                	param.put(PARAM_CONDITION, " WHERE STATO='APP'");
                }
                dataSet.setParam(param);
                dataSet.open();

                if (dataSet.hasMoreElements()) {
                    Row_itf dbRow = (Row_itf) dataSet.nextElement();

                    if (dbRow != null) {
                        String strLast = (String) dbRow.getField("ultimo");
                        if (strLast != null && !strLast.trim().equals("")) {
                            lastSequentialNumeber = Integer.valueOf(strLast);
                            lastSequentialNumeber += 1;
                        }
                    }
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

        if (lastSequentialNumeber == null || lastSequentialNumeber.intValue() == 0) {
            lastSequentialNumeber = new Integer(1);
        }

        return ""+lastSequentialNumeber;

    }
    
    
    
    private String getNextSequentialNumber(String table, String field) throws AppCrash {

        String dsName = DS_PROGRESSIVO_MODULO;

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();
        Integer lastSequentialNumeber = new Integer(0);

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> param = new HashMap<String, String>();
            param.put(PARAM_TABLE_NAME, table);
            param.put(PARAM_FIELD_NAME, field);
            
            if (table.contains("_DETTAGLI")){
            	param.put(PARAM_CONDITION, "");
            }else{
            	param.put(PARAM_CONDITION, " WHERE STATO='APP'");
            }
            dataSet.setParam(param);
            dataSet.open();

            if (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if (dbRow != null) {
                    String strLast = (String) dbRow.getField("ultimo");
                    if (strLast != null && !strLast.trim().equals("")) {
                        lastSequentialNumeber = Integer.valueOf(strLast);
                        lastSequentialNumeber += 1;
                    }
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

        if (lastSequentialNumeber == null || lastSequentialNumeber.intValue() == 0) {
            lastSequentialNumeber = new Integer(1);
        }

        return ""+lastSequentialNumeber;

    }
    
    
    private String getNextRevisionSequentialNumber(String table, String field, String nrModulo) throws AppCrash {

        String dsName = DS_PROGRESSIVO_REVISION;

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();
        Integer lastSequentialNumeber = new Integer(0);

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> param = new HashMap<String, String>();
            param.put(PARAM_TABLE_NAME, table);
            param.put(PARAM_FIELD_NAME, field);
            param.put(PARAM_NR_MODULO, nrModulo);
            dataSet.setParam(param);
            dataSet.open();

            if (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if (dbRow != null) {
                    String strLast = (String) dbRow.getField("ultimo");
                    if (strLast != null && !strLast.trim().equals("")) {
                        lastSequentialNumeber = Integer.valueOf(strLast);
                        lastSequentialNumeber += 1;
                    }
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

        if (lastSequentialNumeber == null || lastSequentialNumeber.intValue() == 0) {
            lastSequentialNumeber = new Integer(1);
        }

        return ""+lastSequentialNumeber;

    }
    
    
    
    
    private void datiDettaglio(String nrDettaglio, String idModuloOrigin, int idModulo, String idArea, UserSecurityInfo userInfo) throws AppCrash {
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DSLIMOD70Dettaglio");
            HashMap<String, String> params = new HashMap<String, String>();
            //params.put("WHERECONDITION", "where id_area="+idArea+" and id_modulo="+idModuloOrigin+ "and distanza_rispetto='N'");
            params.put("WHERECONDITION", "where id_modulo="+idModuloOrigin);
            dataSet.setParam(params);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                int idPDL = (int) dbRow.getField("ID_PDL");
                String descrLavoro = (String) dbRow.getField("DESCRIZIONE_LAVORO");
                
                LiMod64DettagliDAO limod64dettagli = new LiMod64DettagliDAO();

                limod64dettagli.setAttribute(LiMod64DettagliDAO.ID_MODULO, idModulo);
                limod64dettagli.setAttribute(LiMod64DettagliDAO.NR_DETTAGLIO,nrDettaglio);
                limod64dettagli.setAttribute(LiMod64DettagliDAO.IMPRESA_TESTO, dbRow.getField("IMPRESA_TESTO"));
                limod64dettagli.setAttribute(LiMod64DettagliDAO.NOME_COGNOME_PREPOSTO_IMPRESA, dbRow.getField("NOME_COGNOME_PREPOSTO_IMPRESA"));
                limod64dettagli.setAttribute(LiMod64DettagliDAO.ODL, dbRow.getField("ODL"));
                limod64dettagli.setAttribute(LiMod64DettagliDAO.ID_PDL, idPDL);
                limod64dettagli.setAttribute(LiMod64DettagliDAO.DESCR_ATTIVITA, descrLavoro);
                limod64dettagli.setAttribute(LiMod64DettagliDAO.TS_INS, new Timestamp(System.currentTimeMillis()));
                limod64dettagli.setAttribute(LiMod64DettagliDAO.ID_UTENTE_INS, getSpecificUserInfo(userInfo).getIdUtente());
                limod64dettagli.setAttribute(LiMod64DettagliDAO.STATO_RIGA, StatiRichiesta.DRAFT.getCode());
                
                limod64dettagli.insert();
                
            }
        } catch (AppCrash ac) {
            ac.logContext(this.getClass().getName(), "");
            throw ac;

        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "");
                    throw ac;
                }
            }
        }
    }
    
    
    @Override
    protected String getPathDescription(Map<String, Object> map) {

        String pathDescri = "";

        try {
            MenuItem selectedMenuItem = (MenuItem) map.get(WebAppConstants_itf.SELECTED_MENU_ITEM);
            if (selectedMenuItem != null) {
                if (selectedMenuItem.getPathDescri() != null) {
                    pathDescri = selectedMenuItem.getPathDescri();
                }
            }

        } catch (ClassCastException e) {

            String menuId = "189";
            String menuIdSup = "188";
            String languageISO = "it";
            String label = "Ricerca";
            String function = "RicercaLIMOD64";
            String link = "astro?FUNCTIONID=RicercaLIMOD64";
            int itemLevel = 2;
            String path = "1520";
            String linkChain = "astro?FUNCTIONID=Home;#;astro?FUNCTIONID=RicercaLIMOD64";
            boolean readOnly = false;
            int nrOfChildren = 0;
            String icon = "";

            pathDescri = "Home / LIMOD64 - Documento integrativo al DUVRI standard / Visualizza - Modifica";

            MenuItem selectedMenuItem = new MenuItem(menuId, menuIdSup, languageISO, label, function, link, itemLevel,
                    path, pathDescri, linkChain, readOnly, nrOfChildren, icon);

            map.put(WebAppConstants_itf.SELECTED_MENU_ITEM, selectedMenuItem);

            map.put(WebAppConstants_itf.INCLUDED_MENU, "include/included_menu.include");
            map.put(WebAppConstants_itf.SELECTED_PATH, path);

        }

        return pathDescri;

    }
    
    private void cancellaBozzeStessaArea(String idArea) throws AppCrash, ParamCrash {
		    	String sqlStatement2 = "delete from limod64 where stato='DRA' and id_area="+idArea;
        ErrDetector.GetInstance().param(sqlStatement2 != null, "sqlStatement null");
        WebAppUtils.executeQuery(sqlStatement2);
	}

}
