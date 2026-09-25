
package net.projectsrl.cataloghicloud.anagrafiche.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class ProdottiGasDAO extends PjNDAO_base {

    private static final String TABLE_NAME                   = "PRODOTTI_GAS";

    public static final String  ID_PRODOTTI_GAS              = "ID_PRODOTTI_GAS";
    public static final String  ID_FAMIGLIA                  = "ID_FAMIGLIA";
    public static final String  CODICE_PRODOTTO              = "CODICE_PRODOTTO";
    public static final String  DESCRIZIONE_PRODOTTO         = "DESCRIZIONE_PRODOTTO";
    public static final String  CODICE_FAMIGLIA_ALLESTIMENTO = "CODICE_FAMIGLIA_ALLESTIMENTO";
    public static final String  CODICE_ALLESTIMENTO          = "CODICE_ALLESTIMENTO";
    public static final String  CODICE_ETICHETTA_ADR         = "CODICE_ETICHETTA_ADR";
    public static final String  CODICE_SAP_ETICHETTA         = "CODICE_SAP_ETICHETTA";

    public static final String  TS_INS                       = "TS_INS";
    public static final String  ID_UTENTE_INS                = "ID_UTENTE_INS";
    public static final String  TS_DEL                       = "TS_DEL";
    public static final String  ID_UTENTE_DEL                = "ID_UTENTE_DEL";

    public ProdottiGasDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public ProdottiGasDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public ProdottiGasDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_PRODOTTI_GAS, Integer.class);
        addNoStringField(ID_FAMIGLIA, Integer.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_PRODOTTI_GAS))) {
            appendField(ID_PRODOTTI_GAS, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(CODICE_PRODOTTO))) {
            appendField(CODICE_PRODOTTO, whereCondition);
        }

        return whereCondition;
    }
}
