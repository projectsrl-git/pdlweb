
package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.db.PjDAO_base;




/**
 * Classe che rappresenta la tabella INTERVC 
 * Proprietà lette dal file di configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class IntervCDAO extends PjDAO_base {

	private static final String NOME_TABELLA = "INTERVC";

	// colonne
	public static final String DAGGANCIO = "DAGGANCIO";
	public static final String ID_INTERVISTATORE = "ID_INTERVISTATORE";
	public static final String IDRISUMANA   = "IDRISUMANA"  ;
	public static final String RISUMANA   = "RISUMANA"  ;
	public static final String ORDINE    = "?IORDINE"   ;
	public static final String ID_COLLOQUIO = "ID_COLLOQUIO";
	
	

	/**
	 * Costruttore
	 */
	public IntervCDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_INTERVISTATORE);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public IntervCDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_INTERVISTATORE);		
	}

	public IntervCDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_INTERVISTATORE);
	}



}
