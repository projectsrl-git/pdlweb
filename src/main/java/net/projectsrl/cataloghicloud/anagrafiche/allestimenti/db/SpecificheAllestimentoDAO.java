
package net.projectsrl.cataloghicloud.anagrafiche.allestimenti.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class SpecificheAllestimentoDAO extends PjNDAO_base {

    private static final String TABLE_NAME      = "SPECIFICHE_ALLESTIMENTO";

    public static final String  ID_SPECIFICA    = "ID_SPECIFICA";
    public static final String  ID_FAMIGLIA     = "ID_FAMIGLIA";
    public static final String  ID_ALLESTIMENTO = "ID_ALLESTIMENTO";
    public static final String  DESCRIZIONE     = "DESCRIZIONE";
    public static final String  ID_MATERIALE    = "ID_MATERIALE";

    public static final String  TS_INS          = "TS_INS";
    public static final String  ID_UTENTE_INS   = "ID_UTENTE_INS";
    public static final String  TS_DEL          = "TS_DEL";
    public static final String  ID_UTENTE_DEL   = "ID_UTENTE_DEL";

    public SpecificheAllestimentoDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public SpecificheAllestimentoDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public SpecificheAllestimentoDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_ALLESTIMENTO, Integer.class);
        addNoStringField(ID_SPECIFICA, Integer.class);
        addNoStringField(ID_FAMIGLIA, Integer.class);
        addNoStringField(ID_MATERIALE, Integer.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_ALLESTIMENTO))) {
            appendField(ID_ALLESTIMENTO, whereCondition);
            return whereCondition;
        }

        return whereCondition;
    }

}
