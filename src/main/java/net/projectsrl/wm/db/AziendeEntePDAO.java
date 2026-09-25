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
public class AziendeEntePDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "AZIENDE_ENTEP";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String	ID_ENTEP = "ID_ENTEP";
	public static final String	ENTEP = "ENTEP";
	
	

	/**
	 * Costruttore
	 */
	public AziendeEntePDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_ENTEP);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public AziendeEntePDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_ENTEP);		
	}

	public AziendeEntePDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_ENTEP);
	}
}
