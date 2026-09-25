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
public class DipendenteXNoAttivitaDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "DIPENDENTE_X_NO_ATTIVITA";

	public static final String	ID_COMDIPNOATT = "ID_COMDIPNOATT";
	public static final String	ID_COMMESSA = "ID_COMMESSA";
	public static final String	ID_ATTIVITA = "ID_ATTIVITA";
	public static final String	ID_DIPENDENTE = "ID_DIPENDENTE";
	

	public DipendenteXNoAttivitaDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_COMDIPNOATT);
	}


	public DipendenteXNoAttivitaDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_COMDIPNOATT);
	}

	public DipendenteXNoAttivitaDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_COMDIPNOATT);

	}
}
