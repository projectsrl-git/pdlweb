
package net.projectsrl.cataloghicloud.anagrafiche.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class FamigliaProdottiGasDAO extends PjNDAO_base {

    private static final String TABLE_NAME       = "FAMIGLIA_PRODOTTI_GAS";

    public static final String  ID_FAMIGLIA      = "ID_FAMIGLIA";
    public static final String  CODICE_FAMIGLIA  = "CODICE_FAMIGLIA";
    public static final String  NOME_COMMERCIALE = "NOME_COMMERCIALE";
    public static final String  FL_OBSOLETO      = "FL_OBSOLETO";
    public static final String  FL_PUBBLICATO    = "FL_PUBBLICATO";
    public static final String  PRESSIONE_CARICA = "PRESSIONE_CARICA";
    public static final String  SCHEDA_SICUREZZA = "SCHEDA_SICUREZZA";
    public static final String  SITI_ABILITATI   = "SITI_ABILITATI";
    public static final String  COLORE_CORPO     = "COLORE_CORPO";
    public static final String  COLORE_OGIVA     = "COLORE_OGIVA";

    public static final String  TS_INS           = "TS_INS";
    public static final String  ID_UTENTE_INS    = "ID_UTENTE_INS";
    public static final String  TS_DEL           = "TS_DEL";
    public static final String  ID_UTENTE_DEL    = "ID_UTENTE_DEL";

    public FamigliaProdottiGasDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public FamigliaProdottiGasDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public FamigliaProdottiGasDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_FAMIGLIA, Integer.class);
        addNoStringField(FL_OBSOLETO, Boolean.class);
        addNoStringField(FL_PUBBLICATO, Boolean.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_FAMIGLIA))) {
            appendField(ID_FAMIGLIA, whereCondition);
            return whereCondition;
        }

        if (Util.IsNotEmpty(getAttribute(CODICE_FAMIGLIA))) {
            appendField(CODICE_FAMIGLIA, whereCondition);
            return whereCondition;
        }

        return whereCondition;
    }

}
