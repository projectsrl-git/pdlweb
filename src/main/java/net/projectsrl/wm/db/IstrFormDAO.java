
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
public class IstrFormDAO extends PjDAO_base {

	

	private static final String NOME_TABELLA = "ISTRFORM";

	// colonne
	public static final String DAGGANCIO = "DAGGANCIO";
	public static final String ID_ISTRUZIONE = "ID_ISTRUZIONE";
	public static final String IDLIVISTRN   = "IDLIVISTRN"  ;
	public static final String IDLIVISTRI   = "IDLIVISTRI"  ;
	public static final String D_INIZIO_ISTR    = "D_INIZIO_ISTR"   ;
	public static final String D_FINE_ISTR    = "D_FINE_ISTR"   ;
	public static final String DURATA    = "DURATA"   ;
	public static final String ISTITENTEFO    = "ISTITENTEFO"   ;
	public static final String MATABILPROF    = "MATABILPROF"   ;
	public static final String QUALIFICA    = "QUALIFICA"   ;
	public static final String TEMCOMPPROF    = "TEMCOMPPROF"   ;
	public static final String ATTESTATIFP    = "ATTESTATIFP"   ;
	public static final String CERTIFICAZ    = "CERTIFICAZ"   ;
	public static final String ORDINE    = "?IORDINE"   ;
	public static final String DIVIDENDO    = "DIVIDENDO"   ;
	public static final String DIVISORE    = "DIVISORE"   ;
	public static final String FLAG_PRIMARIO = "FLAG_PRIMARIO";
	public static final String TESI = "TESI";
	public static final String DESCTESI = "DESCTESI";
	public static final String LIVELLO_ISTR = "LIVELLO_ISTR";
	
	public static final String LODE    = "LODE"   ;
	

	/**
	 * Costruttore
	 */
	public IstrFormDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_ISTRUZIONE);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public IstrFormDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_ISTRUZIONE);		
	}

	public IstrFormDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_ISTRUZIONE);
	}



}
