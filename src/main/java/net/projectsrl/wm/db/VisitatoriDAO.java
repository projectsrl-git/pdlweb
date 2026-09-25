
package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella VISITATORI Proprietà lette dal file di configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class VisitatoriDAO extends PjDAO_base {

    private static final String NOME_TABELLA        = "VISITATORI";

    public static final String  ID_VISITATORE       = "ID_VISITATORE";
    public static final String  NOMINATIVO          = "NOMINATIVO";
    public static final String  DATA_INGRESSO       = "DATA_INGRESSO";
    public static final String  DATA_USCITA         = "DATA_USCITA";
    public static final String  CODICE_MECCANOG       = "CODICE_MECCANOG";
    public static final String  AZIENDA             = "AZIENDA";
    public static final String  SESSO               = "SESSO";
    public static final String  DOC_IDENTITA        = "DOC_IDENTITA";
    public static final String  GRUPPO_APPARTENENZA = "GRUPPO_APPARTENENZA";
    public static final String  MANSIONE            = "MANSIONE";
    public static final String  INDIR_RESIDENZA     = "INDIR_RESIDENZA";
    public static final String  CITTA_RESIDENZA     = "CITTA_RESIDENZA";
    public static final String  CAP_RESIDENZA       = "CAP_RESIDENZA";
    public static final String  PROV_RESIDENZA      = "PROV_RESIDENZA";
    public static final String  DATA_NASCITA        = "DATA_NASCITA";
    public static final String  CITTA_NASCITA       = "CITTA_NASCITA";
    public static final String  PROV_NASCITA        = "PROV_NASCITA";
    public static final String  MAIL                = "MAIL";
    public static final String  RESPONSABILE        = "RESPONSABILE";
    public static final String  NOTE                = "NOTE";
    public static final String  NOTE_BIS            = "NOTE_BIS";
    public static final String  PROFILO_ORARIO      = "PROFILO_ORARIO";

    public VisitatoriDAO() throws AppCrash {

        super(NOME_TABELLA);
        setUniqueIdentifier(ID_VISITATORE);
    }

    public VisitatoriDAO(DBTransaction transact) throws AppCrash {

        super(transact, NOME_TABELLA);
        setUniqueIdentifier(ID_VISITATORE);
    }

    public VisitatoriDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
        setUniqueIdentifier(ID_VISITATORE);

    }

}
