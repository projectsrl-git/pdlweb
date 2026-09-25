
package net.projectsrl.cataloghicloud.anagrafiche.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class ImpurezzeGarantiteDAO extends PjNDAO_base {

    private static final String TABLE_NAME               = "IMPUREZZE_GARANTITE";

    public static final String  ID_IMPUREZZE_GARANTITE   = "ID_IMPUREZZE_GARANTITE";
    public static final String  ID_FAMIGLIA              = "ID_FAMIGLIA";
    public static final String  CODICE_IMPUREZZA         = "CODICE_IMPUREZZA";
    public static final String  SOGLIA_ANALISI           = "SOGLIA_ANALISI";
    public static final String  DA_PROCESSO              = "DA_PROCESSO";

    public static final String  CODICE_STRUMENTO_ANALISI = "CODICE_STRUMENTO_ANALISI";
    public static final String  CODICE_FREQUENZA_ANALISI = "CODICE_FREQUENZA_ANALISI";

    public static final String  TS_INS                   = "TS_INS";
    public static final String  ID_UTENTE_INS            = "ID_UTENTE_INS";
    public static final String  TS_DEL                   = "TS_DEL";
    public static final String  ID_UTENTE_DEL            = "ID_UTENTE_DEL";

    public ImpurezzeGarantiteDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public ImpurezzeGarantiteDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public ImpurezzeGarantiteDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_IMPUREZZE_GARANTITE, Integer.class);
        addNoStringField(ID_FAMIGLIA, Integer.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_IMPUREZZE_GARANTITE))) {
            appendField(ID_IMPUREZZE_GARANTITE, whereCondition);
        }

        return whereCondition;
    }
}
