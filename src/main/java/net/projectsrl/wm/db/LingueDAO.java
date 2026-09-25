
package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.db.PjDAO_base;



/**
 * Classe che rappresenta la tabella EspProf 
 * Proprietà lette dal file di configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class LingueDAO extends PjDAO_base {

	private static final String NOME_TABELLA = "LINGUECU";

	// colonne
	public static final String DAGGANCIO = "DAGGANCIO";
	public static final String IDLINGUECU = "IDLINGUECU";
	public static final String IDLINGUA   = "IDLINGUA"  ;
	public static final String B_MADRELING    = "B_MADRELING"   ;
	public static final String C_ASCOLTO    = "C_ASCOLTO"   ;
	public static final String C_LETTURA    = "C_LETTURA"   ;
	public static final String C_INTERAZIONE    = "C_INTERAZIONE"   ;
	public static final String C_ORALE    = "C_ORALE"   ;
	public static final String C_SCRITTA    = "C_SCRITTA"   ;
	public static final String CERTIFICAZIONE    = "CERTIFICAZIONE"   ;
	
	public static final String ORDINE    = "?IORDINE"   ;
	
	

	/**
	 * Costruttore
	 */
	public LingueDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(IDLINGUECU);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public LingueDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(IDLINGUECU);		
	}

	public LingueDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(IDLINGUECU);
	}



}
