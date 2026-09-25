
package net.projectsrl.pdlweb.anagrafiche.db;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;

public class AreaLavoroDAO extends PjNDAO_base {

    private static final String DS_EQUIPMENT = "DSEquipment";

    private static final String TABLE_NAME    = "AREA_LAVORO";

    public static final String  ID_AREA       = "ID_AREA";
    public static final String  ID_IMPIANTO   = "ID_IMPIANTO";
    public static final String  DESCR_AREA    = "DESCR_AREA";

    public static final String  RECT_X        = "RECT_X";
    public static final String  RECT_Y        = "RECT_Y";
    public static final String  RECT_W        = "RECT_W";
    public static final String  RECT_H        = "RECT_H";
    public static final String  TESTO_X       = "TESTO_X";
    public static final String  TESTO_Y       = "TESTO_Y";
    public static final String  A_CAPO        = "A_CAPO";
    
    public static final String  RECT_X_STAMPA        = "RECT_X_STAMPA";
    public static final String  RECT_Y_STAMPA        = "RECT_Y_STAMPA";
    public static final String  RECT_W_STAMPA        = "RECT_W_STAMPA";
    public static final String  RECT_H_STAMPA        = "RECT_H_STAMPA";
    public static final String  TESTO_X_STAMPA       = "TESTO_X_STAMPA";
    public static final String  TESTO_Y_STAMPA       = "TESTO_Y_STAMPA";
    public static final String  A_CAPO_STAMPA        = "A_CAPO_STAMPA";

    public static final String  FL_TUTTO_IMPIANTO        = "FL_TUTTO_IMPIANTO";

    public static final String  TS_INS        = "TS_INS";
    public static final String  ID_UTENTE_INS = "ID_UTENTE_INS";
    public static final String  TS_DEL        = "TS_DEL";
    public static final String  ID_UTENTE_DEL = "ID_UTENTE_DEL";
    
    public static final String  FL_DISATTIVO    = "FL_DISATTIVO";
    
    public static final String  ID_AREA_COLLEGATA_TUTTO_IMPIANTO    = "ID_AREA_COLLEGATA_TUTTO_IMPIANTO";
    public static final String  AREA_SIMULABILE_TUTTO_IMPIANTO    = "AREA_SIMULABILE_TUTTO_IMPIANTO";

    public AreaLavoroDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AreaLavoroDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AreaLavoroDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }
    

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_AREA, Integer.class);
        addNoStringField(ID_IMPIANTO, Integer.class);

        addNoStringField(RECT_X, Integer.class);
        addNoStringField(RECT_Y, Integer.class);
        addNoStringField(RECT_W, Integer.class);
        addNoStringField(RECT_H, Integer.class);
        addNoStringField(TESTO_X, Integer.class);
        addNoStringField(TESTO_Y, Integer.class);
        addNoStringField(A_CAPO, Integer.class);
        
        addNoStringField(RECT_X_STAMPA, Integer.class);
        addNoStringField(RECT_Y_STAMPA, Integer.class);
        addNoStringField(RECT_W_STAMPA, Integer.class);
        addNoStringField(RECT_H_STAMPA, Integer.class);
        addNoStringField(TESTO_X_STAMPA, Integer.class);
        addNoStringField(TESTO_Y_STAMPA, Integer.class);
        addNoStringField(A_CAPO_STAMPA, Integer.class);
        
        addNoStringField(FL_DISATTIVO, Boolean.class);
        addNoStringField(FL_TUTTO_IMPIANTO, Boolean.class);

        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);
        
        addNoStringField(ID_AREA_COLLEGATA_TUTTO_IMPIANTO, Integer.class);
        addNoStringField(AREA_SIMULABILE_TUTTO_IMPIANTO, Boolean.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_AREA))) {
            appendField(ID_AREA, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(TS_INS)) && Util.IsNotEmpty(getAttribute(ID_UTENTE_INS))) {
            appendField(TS_INS, whereCondition);
            appendField(ID_UTENTE_INS, whereCondition);

        }

        return whereCondition;
    }
    
    @Override
    public void delete() throws AppCrash {
        setAttribute(AreaLavoroDAO.FL_DISATTIVO, true);
        update();
        
        disableEquipment(getAttributeAsString(ID_AREA));
    }

    @Override
    public void insert() throws AppCrash {

        Timestamp tsIns = new Timestamp(System.currentTimeMillis());
        setAttribute(TS_INS, tsIns);

        super.insert();

        retrieve();

    }

    private void disableEquipment(String searchKeyValue) throws AppCrash {

        String dsName=DS_EQUIPMENT;
        DataSet_itf dataSet = null;
        DataSetFactory dsFactory = DataSetFactory.getInstance();

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(WebAppConstants_itf.WHERECONDITION, " WHERE ID_AREA="+searchKeyValue);
            dataSet.setParam(params);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                Integer keyValue = (Integer) dbRow.getField(EquipmentDAO.ID_EQUIPMENT);
                EquipmentDAO rowDAO = new EquipmentDAO();

                rowDAO.setAttribute(EquipmentDAO.ID_EQUIPMENT, keyValue);
                rowDAO.setAttribute(EquipmentDAO.FL_DISATTIVO, true);
                rowDAO.update();

            }
        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(), "error using dataset " + dsName);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    ac.logContext(this.getClass().getName(), "error closing dataset " + dsName);
                }
            }
        }

    }

}
