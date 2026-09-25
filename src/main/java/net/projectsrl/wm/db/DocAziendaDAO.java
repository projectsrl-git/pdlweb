
package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

public class DocAziendaDAO extends PjDAO_base {

    private static final String NOME_TABELLA = "DOCAZIENDA";

    public static final String  ID_DOCAZIENDA	= "ID_DOCAZIENDA";
    public static final String  AZIENDA	= "AZIENDA";


    public DocAziendaDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_DOCAZIENDA);
	}


	public DocAziendaDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_DOCAZIENDA);
	}

	public DocAziendaDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_DOCAZIENDA);

	}
}
