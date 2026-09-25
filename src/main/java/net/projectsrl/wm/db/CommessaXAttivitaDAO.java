package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella PROFILO_X_DIPENDENTE Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class CommessaXAttivitaDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "COMMESSA_X_ATTIVITA";

	public static final String	ID_COMATT = "ID_COMATT";
	public static final String	ID_COMMESSA = "ID_COMMESSA";
	public static final String	ID_ATTIVITA = "ID_ATTIVITA";
	public static final String	ASSOCIATA_DAL = "ASSOCIATA_DAL";
	public static final String	ASSOCIATA_AL = "ASSOCIATA_AL";
	public static final String	PREDEFINITA = "PREDEFINITA";
	

	public CommessaXAttivitaDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_COMATT);
	}


	public CommessaXAttivitaDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_COMATT);
	}

	public CommessaXAttivitaDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_COMATT);

	}
}
