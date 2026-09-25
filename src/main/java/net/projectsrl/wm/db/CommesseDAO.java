package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella COMMESSE Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class CommesseDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "COMMESSE";

	public static final String	ID_COMMESSA = "ID_COMMESSA";
	public static final String	CODICE = "CODICE";
	public static final String	DESCRI = "DESCRI";
	public static final String	ID_DIPENDENTE = "ID_DIPENDENTE";
	public static final String	ID_AZIENDA = "ID_AZIENDA";
	public static final String	DATA_DAL = "VALIDITA_DAL";
	public static final String	DATA_AL = "VALIDITA_AL";
	

	public CommesseDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_COMMESSA);
	}


	public CommesseDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_COMMESSA);
	}

	public CommesseDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_COMMESSA);

	}
}
