
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
public class EsperienzeDAO extends PjDAO_base {

	private static final String NOME_TABELLA = "ESPPROF";

	// colonne
	public static final String DAGGANCIO = "DAGGANCIO";
	public static final String ID_ESPERIENZA = "ID_ESPERIENZA";
	public static final String IDTIOPL   = "IDTIOPL"  ;
	public static final String D_INIZIO    = "D_INIZIO"   ;
	public static final String D_FINE    = "D_FINE"   ;
	public static final String DURATA    = "DURATA"   ;
	public static final String MANSIONRESP    = "MANSIONRESP"   ;
	public static final String ATTIVSVOLTE    = "ATTIVSVOLTE"   ;
	public static final String SETTOREESP    = "SETTOREESP"   ;
	public static final String AZRAGSOC    = "AZRAGSOC"   ;
	public static final String AZINDIRIZZO    = "AZINDIRIZZO"   ;
	public static final String ATTIVMANS_ISTR    = "ATTIVMANS_ISTR"   ;
	public static final String CONSTRUM    = "CONSTRUM"   ;
	
	public static final String ORDINE    = "?IORDINE"   ;
	
	

	/**
	 * Costruttore
	 */
	public EsperienzeDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_ESPERIENZA);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public EsperienzeDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_ESPERIENZA);		
	}

	public EsperienzeDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_ESPERIENZA);
	}



}
