
package net.projectsrl.dafne.richieste.db;

import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.misc.Util;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.dafne.db.TableIdentity;
import net.projectsrl.db.PjNDAO_base;

/**
 * Classe che rappresenta la tabella DEALER Proprietà lette dal file di configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class ApprovatoriRichiestaDAO extends PjNDAO_base {

    private static final String TABLE_NAME                     = "APPROVATORI_RICHIESTE";
    private static final String DATA_SET_APPROVATORI_RICHIESTE = "DSApprovatoriRichiesta";

    public static final String  ID_APPROVATORE                 = "ID_APPROVATORE";
    public static final String  ID_TIPO_RICHIESTA              = "ID_TIPO_RICHIESTA";
    public static final String  ID_AZIENDA                     = "ID_AZIENDA";
    public static final String  ID_DIREZIONE                   = "ID_DIREZIONE";
    public static final String  ID_RISORSA                     = "ID_RISORSA";
    public static final String  LIVELLO_APPR                   = "LIVELLO_APPR";

    private static final String LIST_SEPARATOR                 = ";";

    public ApprovatoriRichiestaDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public ApprovatoriRichiestaDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty(DB_NAME_PROPERTY) + TABLE_NAME);
    }

    public ApprovatoriRichiestaDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);

    }

    /**
     * Questo metodo
     * 
     * @return
     * @throws AppCrash
     * 
     * @see net.ssb.db.NDAO_base#whereCondition()
     */
    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_APPROVATORE))) {
            appendField(ID_APPROVATORE, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(ID_TIPO_RICHIESTA)) && Util.IsNotEmpty(getAttribute(ID_DIREZIONE))
                && Util.IsNotEmpty(getAttribute(ID_RISORSA))) {
            appendField(ID_TIPO_RICHIESTA, whereCondition);
            appendField(ID_DIREZIONE, whereCondition);
            appendField(ID_RISORSA, whereCondition);
        }

        return whereCondition;
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_APPROVATORE, Integer.class);
        addNoStringField(ID_TIPO_RICHIESTA, Integer.class);
        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(ID_DIREZIONE, Integer.class);
        addNoStringField(ID_RISORSA, Integer.class);
        addNoStringField(LIVELLO_APPR, Integer.class);
    }

    public void delete(Integer idTipoRichiesta, Integer idDirezione, Integer idRisorsa) throws AppCrash {

        DataSet_itf dataSet = null;
        DataSetFactory dsFactory = DataSetFactory.getInstance();

        try {
            dataSet = dsFactory.makeDataSet("", DATA_SET_APPROVATORI_RICHIESTE);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(ID_TIPO_RICHIESTA, idTipoRichiesta);
            params.put(ID_DIREZIONE, idDirezione);
            params.put(ID_RISORSA, idRisorsa);
            dataSet.setParam(params);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                Integer keyValue = (Integer) dbRow.getField(ID_APPROVATORE);
                ApprovatoriRichiestaDAO rowDAO = new ApprovatoriRichiestaDAO();

                rowDAO.setAttribute(ID_APPROVATORE, keyValue);
                rowDAO.delete();

            }
        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(), "error using dataset " + DATA_SET_APPROVATORI_RICHIESTE);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    ac.logContext(this.getClass().getName(), "error closing dataset " + DATA_SET_APPROVATORI_RICHIESTE);
                }
            }
        }

    }
    
   

    public void insert(String codeList, SsbServletRequest req) throws AppCrash {

        String[] selected = codeList.split(LIST_SEPARATOR);
        for (int i = 0; i < selected.length; i++) {
            Integer attributeId = (Integer) new Integer(selected[i]);

            ApprovatoriRichiestaDAO referenceDAO;
            try {
                referenceDAO = new ApprovatoriRichiestaDAO();
                referenceDAO.setAttributesFromRequest(req);
                referenceDAO.setAttribute(ID_RISORSA, attributeId);
                referenceDAO.insert();
            } catch (Throwable e) {
                AppCrash ac = new AppCrash();
                ac.logContext(this.getClass().getName(),
                        "codeList:" + codeList + " - referenceFieldName:" + ID_RISORSA);
            }
        }
    }

    public String getSelectedCodeList(Integer idTipoRichiesta, Integer idDirezione, Integer idRisorsa) throws AppCrash {

        DataSet_itf dataSet = null;
        DataSetFactory dsFactory = DataSetFactory.getInstance();

        String selectedList = "";
        try {
            dataSet = dsFactory.makeDataSet("", DATA_SET_APPROVATORI_RICHIESTE);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(ID_TIPO_RICHIESTA, idTipoRichiesta);
            params.put(ID_DIREZIONE, idDirezione);
            params.put(ID_RISORSA, idRisorsa);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if (Util.IsNotEmpty(selectedList)) {
                    selectedList += LIST_SEPARATOR;
                }

                selectedList += dbRow.getField(ID_APPROVATORE);
            }
        } catch (AppCrash ac) {
            ac.logContext(this.getClass().getName(),
                    "Errore nella ricerca del max del dataset " + DATA_SET_APPROVATORI_RICHIESTE);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    ac.logContext(this.getClass().getName(),
                            "Errore nella close del dataset " + DATA_SET_APPROVATORI_RICHIESTE);
                }
            }
        }
        return selectedList;
    }

    @Override
    public void insert() throws AppCrash {

        super.insert();
        
        Integer rowId = new TableIdentity(TABLE_NAME).getTableIdentity();
        setAttribute(ID_APPROVATORE, rowId);
        
    }

    
    
}