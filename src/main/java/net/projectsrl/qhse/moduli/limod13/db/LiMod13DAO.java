
package net.projectsrl.qhse.moduli.limod13.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class LiMod13DAO extends AliModDAO_base {

    private static final String TABLE_NAME             = "LIMOD13";

    public static final String  ORA              = "ORA";
    public static final String  UT               = "UT";
    public static final String  PRODOTTO     = "PRODOTTO";
    public static final String  AUDIT            = "AUDIT";
    public static final String  AUTISTA            = "AUTISTA";
    public static final String  NOTE            = "NOTE";
    public static final String  OPERATORE_VERIFICATO            = "OPERATORE_VERIFICATO";
    public static final String  VERIFICATORE            = "VERIFICATORE";
    
    public static final String  FLG_1            = "FLG_1";
    public static final String  FLG_2            = "FLG_2";
    public static final String  FLG_3            = "FLG_3";
    public static final String  FLG_4            = "FLG_4";
    public static final String  FLG_5            = "FLG_5";
    public static final String  FLG_6            = "FLG_6";
    public static final String  FLG_7            = "FLG_7";
    public static final String  FLG_8            = "FLG_8";
    public static final String  FLG_9            = "FLG_9";
    public static final String  FLG_10            = "FLG_10";
    public static final String  FLG_11            = "FLG_11";
    public static final String  FLG_12            = "FLG_12";
    public static final String  FLG_13            = "FLG_13";
    public static final String  FLG_14            = "FLG_14";
    public static final String  FLG_15            = "FLG_15";
    public static final String  FLG_16            = "FLG_16";
    public static final String  FLG_17            = "FLG_17";
    public static final String  FLG_18            = "FLG_18";
    public static final String  FLG_19            = "FLG_19";
    public static final String  FLG_20            = "FLG_20";
    public static final String  FLG_21            = "FLG_21";
    public static final String  FLG_22            = "FLG_22";
    public static final String  FLG_23            = "FLG_23";
    public static final String  FLG_24            = "FLG_24";
    public static final String  FLG_25            = "FLG_25";
    public static final String  FLG_26            = "FLG_26";
    public static final String  FLG_27            = "FLG_27";
    public static final String  TABELLA_PARENT        = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT      = "ID_MODULO_PARENT";
    
    
    
    public LiMod13DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public LiMod13DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public LiMod13DAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_MODULO_PARENT, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_MODULO))) {
            appendField(ID_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(NR_MODULO))) {
            appendField(NR_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(TABELLA_PARENT)) && Util.IsNotEmpty(getAttribute(ID_MODULO_PARENT))) {
            appendField(TABELLA_PARENT, whereCondition);
            appendField(ID_MODULO_PARENT, whereCondition);
        }
        
        return whereCondition;
    }

}
