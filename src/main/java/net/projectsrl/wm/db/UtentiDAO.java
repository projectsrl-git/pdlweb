
package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.db.PjDAO_base;

public class UtentiDAO extends PjDAO_base {

    private static final String NOME_TABELLA = "UTENTI";

    public static final String  USERID       = "USERID";
    public static final String  ID_CODICE    = "ID_CODICE";
    public static final String  PASSWORD     = "PASSWORD";
    public static final String  RUOLO        = "RUOLO";
    public static final String  EMAIL        = "EMAIL";
    public static final String  DATA         = "DATA";
    public static final String  NOME         = "NOME";
    public static final String  COGNOME      = "COGNOME";
    public static final String  DATA_SC      = "DATA_SC";
    public static final String  ATTIVO		 = "ATTIVO";
    public static final String  AZIENDA      = "AZIENDA";
    public static final String ID_DIPENDENTE = "ID_DIPENDENTE";
    public static final String PWD_SCADUTA = "PWD_SCADUTA";
    
               
    public UtentiDAO() throws AppCrash {

        super(NOME_TABELLA);
		setUniqueIdentifier(ID_CODICE);
    }

    public UtentiDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB") + NOME_TABELLA);
		setUniqueIdentifier(ID_CODICE);
    }

    public UtentiDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
		setUniqueIdentifier(ID_CODICE);

    }

}
