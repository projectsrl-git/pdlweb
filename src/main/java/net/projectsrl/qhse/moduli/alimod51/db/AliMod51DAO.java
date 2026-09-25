
package net.projectsrl.qhse.moduli.alimod51.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class AliMod51DAO extends AliModDAO_base {

    private static final String TABLE_NAME             = "ALIMOD51";

    public static final String  DATORE_DELEGATO              = "DATORE_DELEGATO";
    public static final String  RSPP               = "RSPP";
    public static final String  MEDICO     = "MEDICO";
    public static final String  RLS            = "RLS";
    public static final String  TABELLA_PARENT        = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT      = "ID_MODULO_PARENT";
    
    public AliMod51DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AliMod51DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AliMod51DAO(DBTransaction transact, String tableName) throws AppCrash {

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
