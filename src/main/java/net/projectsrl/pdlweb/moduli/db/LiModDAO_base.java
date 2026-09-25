
package net.projectsrl.pdlweb.moduli.db;

import java.sql.Timestamp;
import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public abstract class LiModDAO_base extends PjNDAO_base {

	private static final String DS_PROGRESSIVO_MODULO = "DSProgressivoLIMOD";
	private static final String PARAM_TABLE_NAME      = "TABLE_NAME";
    private static final String PARAM_FIELD_NAME      = "FIELD_NAME";
    private static final String PARAM_CONDITION      = "PARAM_CONDITION";
    

    public static final String  ID_MODULO             = "ID_MODULO";
    public static final String  ID_AZIENDA            = "ID_AZIENDA";

    public static final String  NR_MODULO             = "NR_MODULO";
    public static final String  DT_MODULO             = "DT_MODULO";
    
    public static final String  TS_INS                = "TS_INS";
    public static final String  ID_UTENTE_INS         = "ID_UTENTE_INS";
    
    public LiModDAO_base(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    public LiModDAO_base(String tableName, String configName) throws AppCrash {

        super(tableName, configName);

    }

    public LiModDAO_base(String tableName) throws AppCrash {

        super(tableName);

    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_MODULO, Integer.class);
        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_MODULO))) {
            appendField(ID_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(NR_MODULO))) {
            appendField(NR_MODULO, whereCondition);
        }

        return whereCondition;
    }

    @Override
    public void insert() throws AppCrash {

        //String nrModulo = getNextSequentialNumber();
        //setAttribute(NR_MODULO, nrModulo);

        super.insert();
        super.retrieve();
    }

    private String getNextSequentialNumber() throws AppCrash {

        String dsName = DS_PROGRESSIVO_MODULO;

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();
        Integer lastSequentialNumeber = new Integer(0);

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> param = new HashMap<String, String>();
            param.put(PARAM_TABLE_NAME, getTableName());
            param.put(PARAM_FIELD_NAME, NR_MODULO);
            if (getTableName().contains("_DETTAGLI")){
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

}
