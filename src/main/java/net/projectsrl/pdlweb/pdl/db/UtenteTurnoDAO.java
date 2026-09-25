
package net.projectsrl.pdlweb.pdl.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class UtenteTurnoDAO extends PjNDAO_base {

    private static final String TABLE_NAME     = "UTENTE_TURNO";

    public static final String  ID_UTENTE_TURNO    = "ID_UTENTE_TURNO";
    public static final String  ID_UTENTE     = "ID_UTENTE";
    public static final String  CODICE_TURNO = "CODICE_TURNO";
    public static final String  DATA_FINE_TURNO = "DATA_FINE_TURNO";
    public static final String  ORA_FINE_TURNO = "ORA_FINE_TURNO";

    public static final String  TS_LOGIN         = "TS_LOGIN";
    public static final String  TS_LOGOUT         = "TS_LOGOUT";

    public UtenteTurnoDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public UtenteTurnoDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public UtenteTurnoDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_UTENTE_TURNO, Integer.class);
        addNoStringField(ID_UTENTE, Integer.class);
        addNoStringField(TS_LOGIN, Timestamp.class);
        addNoStringField(TS_LOGOUT, Timestamp.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_UTENTE_TURNO))) {
            appendField(ID_UTENTE_TURNO, whereCondition);
        }

        return whereCondition;
    }

}
