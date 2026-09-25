
package net.projectsrl.pdlweb.pdl.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class ImpiantiDAO extends PjNDAO_base {

    private static final String TABLE_NAME     = "IMPIANTI";

    public static final String  ID_IMPIANTO    = "ID_IMPIANTO";
    public static final String  ID_AZIENDA     = "ID_AZIENDA";
    public static final String  DESCR_IMPIANTO = "DESCR_IMPIANTO";
    public static final String  FILENAME_PLANIMETRIA = "FILENAME_PLANIMETRIA";
    
    public static final String  FL_DISATTIVO    = "FL_DISATTIVO";

    public static final String  TS_INS         = "TS_INS";
    public static final String  ID_UTENTE_INS  = "ID_UTENTE_INS";
    public static final String  TS_DEL         = "TS_DEL";
    public static final String  ID_UTENTE_DEL  = "ID_UTENTE_DEL";

    public ImpiantiDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public ImpiantiDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public ImpiantiDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_IMPIANTO, Integer.class);
        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);
        addNoStringField(FL_DISATTIVO, Boolean.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_IMPIANTO))) {
            appendField(ID_IMPIANTO, whereCondition);
        }

        return whereCondition;
    }

}
