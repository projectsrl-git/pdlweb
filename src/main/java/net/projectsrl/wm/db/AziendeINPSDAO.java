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
public class AziendeINPSDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "AZIENDE_INPS";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String	ID_INPS = "ID_INPS";
	public static final String	INPS = "INPS";
	
	

	/**
	 * Costruttore
	 */
	public AziendeINPSDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_INPS);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public AziendeINPSDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_INPS);		
	}

	public AziendeINPSDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_INPS);
	}
}
