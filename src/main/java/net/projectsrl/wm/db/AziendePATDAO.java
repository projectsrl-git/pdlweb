package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella AZIENDE Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class AziendePATDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "AZIENDE_PAT";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String	ID_PAT = "ID_PAT";
	public static final String	PAT = "PAT";
	
	

	/**
	 * Costruttore
	 */
	public AziendePATDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_PAT);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public AziendePATDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_PAT);		
	}

	public AziendePATDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_PAT);
	}
}
