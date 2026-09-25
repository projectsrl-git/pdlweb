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
public class ProfiloXDipendenteDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "PROFILO_X_DIPENDENTE";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String	ID_PROXDIP = "ID_PROXDIP";
	public static final String	ID_PRODIP = "ID_PRODIP";
	public static final String	ID_PROFILO = "ID_PROFILO";
	public static final String	ID_DIPENDENTE = "ID_DIPENDENTE";
	public static final String	PREDEFINITO = "PREDEFINITO";
	

	public ProfiloXDipendenteDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_PROXDIP);
	}


	public ProfiloXDipendenteDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_PROXDIP);
	}

	public ProfiloXDipendenteDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_PROXDIP);

	}
}
