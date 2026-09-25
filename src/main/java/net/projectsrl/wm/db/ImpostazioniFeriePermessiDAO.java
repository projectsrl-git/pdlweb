
package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella IMPOSTAZIONI_STRAORDINARI Proprietà lette dal file di configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class ImpostazioniFeriePermessiDAO extends PjDAO_base {

    private static final String NOME_TABELLA     = "IMPOSTAZIONI_FERIEPERMESSI";

    public static final String  ID_UNIVOCO       = "ID_UNIVOCO";
    public static final String  AZIENDA          = "AZIENDA";
    public static final String  ID_DIPENDENTE    = "ID_DIPENDENTE";
    public static final String  APPROVAZIONE     = "APPROVAZIONE";

    public ImpostazioniFeriePermessiDAO() throws AppCrash {

        super(NOME_TABELLA);
        setUniqueIdentifier(ID_UNIVOCO);
    }

    public ImpostazioniFeriePermessiDAO(DBTransaction transact) throws AppCrash {

        super(transact, NOME_TABELLA);
        setUniqueIdentifier(ID_UNIVOCO);
    }

    public ImpostazioniFeriePermessiDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
        setUniqueIdentifier(ID_UNIVOCO);

    }

}
