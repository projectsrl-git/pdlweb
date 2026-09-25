
package net.projectsrl.cataloghicloud.anagrafiche.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class ProdottiGasComposizioneDAO extends PjNDAO_base {

    private static final String TABLE_NAME               = "PRODOTTI_GAS_COMP";

    public static final String  ID_PRODOTTI_GAS_COMP     = "ID_PRODOTTI_GAS_COMP";
    public static final String  ID_FAMIGLIA              = "ID_FAMIGLIA";
    public static final String  CODICE_COMPONENTE        = "CODICE_COMPONENTE";
    public static final String  COMPOSIZIONE_PERC        = "COMPOSIZIONE_PERC";
    public static final String  CODICE_STRUMENTO_ANALISI = "CODICE_STRUMENTO_ANALISI";
    public static final String  CODICE_FREQUENZA_ANALISI = "CODICE_FREQUENZA_ANALISI";

    public static final String  TS_INS                   = "TS_INS";
    public static final String  ID_UTENTE_INS            = "ID_UTENTE_INS";

    public ProdottiGasComposizioneDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public ProdottiGasComposizioneDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public ProdottiGasComposizioneDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_PRODOTTI_GAS_COMP, Integer.class);
        addNoStringField(ID_FAMIGLIA, Integer.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_PRODOTTI_GAS_COMP))) {
            appendField(ID_PRODOTTI_GAS_COMP, whereCondition);
        }

        return whereCondition;
    }
}
