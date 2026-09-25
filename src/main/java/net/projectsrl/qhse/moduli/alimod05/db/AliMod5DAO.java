
package net.projectsrl.qhse.moduli.alimod05.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class AliMod5DAO extends AliModDAO_base {

    private static final String TABLE_NAME             = "ALIMOD05";

    public static final String  SEZ1_NORMA_LEGGE_DOC              = "SEZ1_NORMA_LEGGE_DOC";
    public static final String  SEZ1_CAPITOLO               = "SEZ1_CAPITOLO";
    public static final String  SEZ1_FORNITORE_CLIENTE     = "SEZ1_FORNITORE_CLIENTE";
    public static final String  SEZ1_TIPO_SERV_PROD            = "SEZ1_TIPO_SERV_PROD";
    public static final String  SEZ1_DESCRIZIONE_NC      = "SEZ1_DESCRIZIONE_NC";
    public static final String  SEZ1_FL_GMP   = "SEZ1_FL_GMP";
    public static final String  SEZ1_EVIDENZE   = "SEZ1_EVIDENZE";
    public static final String  SEZ1_OSSERVAZIONI   = "SEZ1_OSSERVAZIONI";
    public static final String  SEZ1_VERIFICATORE_NC   = "SEZ1_VERIFICATORE_NC";
    public static final String  SEZ1_DT_NC         = "SEZ1_DT_NC";
    public static final String  SEZ1_ID_UTENTE_RES         = "SEZ1_ID_UTENTE_RES";
    
    public static final String  SEZ2_TRATTAMENTO_NC         = "SEZ2_TRATTAMENTO_NC";
    public static final String  SEZ2_DT_PREVISTA_NC         = "SEZ2_DT_PREVISTA_NC";
    public static final String  SEZ2_DT_VERIFICA_NC         = "SEZ2_DT_VERIFICA_NC";
    public static final String  SEZ2_ID_UTENTE_RES_1         = "SEZ2_ID_UTENTE_RES_1";
    public static final String  SEZ2_ID_UTENTE_RES_2         = "SEZ2_ID_UTENTE_RES_2";
    public static final String  FLG_TRATTAMENTO_NC         = "FLG_TRATTAMENTO_NC";
    public static final String  SEZ2_DT_VERIFICA_TRATT_NC         = "SEZ2_DT_VERIFICA_TRATT_NC";
    public static final String  SEZ2_ID_UTENTE_RES_3         = "SEZ2_ID_UTENTE_RES_3";

    public static final String  SEZ3_CAUSA_NC      = "SEZ3_CAUSA_NC";
    public static final String  SEZ3_DT_CAUSA_NC      = "SEZ3_DT_CAUSA_NC";
    public static final String  SEZ3_ID_UTENTE_RES               = "SEZ3_ID_UTENTE_RES";
    public static final String  TABELLA_PARENT        = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT      = "ID_MODULO_PARENT";

    public AliMod5DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AliMod5DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AliMod5DAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }
    

    @Override
    protected void init() throws AppCrash {

        super.init();
        addNoStringField(FLG_TRATTAMENTO_NC, Boolean.class);

        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);
        addNoStringField(SEZ1_ID_UTENTE_RES, Integer.class);
        addNoStringField(SEZ2_ID_UTENTE_RES_1, Integer.class);
        addNoStringField(SEZ2_ID_UTENTE_RES_2, Integer.class);
        addNoStringField(SEZ2_ID_UTENTE_RES_3, Integer.class);
        addNoStringField(SEZ3_ID_UTENTE_RES, Integer.class);
        
        addNoStringField(ID_MODULO_PARENT, Integer.class);
        
        addNoStringField(FLG_TRATTAMENTO_NC, Boolean.class);
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
