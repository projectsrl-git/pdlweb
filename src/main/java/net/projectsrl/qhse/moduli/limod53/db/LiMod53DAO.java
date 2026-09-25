
package net.projectsrl.qhse.moduli.limod53.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class LiMod53DAO extends AliModDAO_base {

    private static final String TABLE_NAME             = "LIMOD53";

    public static final String  PERMESSO_NR              = "PERMESSO_NR";
    public static final String  DITTA               = "DITTA";
    public static final String  OPERATORE     = "OPERATORE";
    public static final String  PERSONALE            = "PERSONALE";
    public static final String  FUNZIONE            = "FUNZIONE";
    public static final String  AUDIT            = "AUDIT";
    public static final String  OSSERVATORE            = "OSSERVATORE";
    public static final String  PERSONA_OSSERVATA            = "PERSONA_OSSERVATA";

    
    public static final String 	FLG_1	="FLG_1";
    public static final String 	FLG_2	="FLG_2";
    public static final String 	FLG_3	="FLG_3";
    public static final String 	FLG_4A	="FLG_4A";
    public static final String 	FLG_4B	="FLG_4B";
    public static final String 	FLG_5	="FLG_5";
    public static final String 	FLG_6	="FLG_6";
    public static final String 	FLG_7	="FLG_7";
    public static final String 	FLG_8	="FLG_8";
    public static final String 	FLG_9	="FLG_9";
    public static final String 	FLG_10	="FLG_10";
    public static final String 	FLG_11	="FLG_11";
    public static final String 	FLG_12	="FLG_12";
    public static final String 	FLG_13	="FLG_13";
    public static final String 	FLG_14	="FLG_14";
    public static final String 	FLG_15	="FLG_15";
    
    
    public static final String 	NOTE_1	="NOTE_1";
    public static final String 	NOTE_2	="NOTE_2";
    public static final String 	NOTE_3	="NOTE_3";
    public static final String 	NOTE_4A	="NOTE_4A";
    public static final String 	NOTE_4B	="NOTE_4B";
    public static final String 	NOTE_5	="NOTE_5";
    public static final String 	NOTE_6	="NOTE_6";
    public static final String 	NOTE_7	="NOTE_7";
    public static final String 	NOTE_8	="NOTE_8";
    public static final String 	NOTE_9	="NOTE_9";
    public static final String 	NOTE_10	="NOTE_10";
    public static final String 	NOTE_11	="NOTE_11";
    public static final String 	NOTE_12	="NOTE_12";
    public static final String 	NOTE_13	="NOTE_13";
    public static final String 	NOTE_14	="NOTE_14";
    public static final String 	NOTE_15	="NOTE_15";
    public static final String  TABELLA_PARENT        = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT      = "ID_MODULO_PARENT";

    
    public LiMod53DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public LiMod53DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public LiMod53DAO(DBTransaction transact, String tableName) throws AppCrash {

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
