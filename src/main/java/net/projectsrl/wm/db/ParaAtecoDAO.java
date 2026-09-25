
package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.db.PjDAO_base;

public class ParaAtecoDAO extends PjDAO_base {

    private static final String NOME_TABELLA = "PARA_ATECO";

    public static final String  CODICE	= "CODICE";
    public static final String  DESCRI	= "DESCRI";
    public static final String  LIBERA	= "LIBERA";
    

    public ParaAtecoDAO() throws AppCrash {

        super(NOME_TABELLA);
		setUniqueIdentifier(CODICE);
    }

    public ParaAtecoDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB") + NOME_TABELLA);
		setUniqueIdentifier(CODICE);
    }

    public ParaAtecoDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
		setUniqueIdentifier(CODICE);

    }
}
