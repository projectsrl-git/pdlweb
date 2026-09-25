
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
public class FormazioneProfessionaleDAO extends PjDAO_base {

	private static final String NOME_TABELLA = "FORMAZIONE_PROFESSIONALE";

	// colonne
	public static final String DAGGANCIO            = "DAGGANCIO";         
	public static final String ID_FORMAZPROF        = "ID_FORMAZPROF";     
	public static final String TITOLO_ATTIVITA      = "TITOLO_ATTIVITA";   
	public static final String SOGGETTO_ATTIVITA    = "SOGGETTO_ATTIVITA"; 
	public static final String SEDE_SOGGETTO        = "SEDE_SOGGETTO";     
	public static final String CONCLUSO             = "CONCLUSO";          
	public static final String DURATA_ATTIVITA      = "DURATA_ATTIVITA";   
	public static final String ATTESTAZIONE         = "ATTESTAZIONE";      
	public static final String ALTRE_ATTESTAZIONI   = "ALTRE_ATTESTAZIONI";
	public static final String TIROCINIO            = "TIROCINIO";         
	public static final String ENTE                 = "ENTE";              
	public static final String OBBLIGO_LEGGE        = "OBBLIGO_LEGGE";     
	public static final String FORMAZIONE_VALIDA    = "FORMAZIONE_VALIDA"; 
	
	

	/**
	 * Costruttore
	 */
	public FormazioneProfessionaleDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_FORMAZPROF);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public FormazioneProfessionaleDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_FORMAZPROF);		
	}

	public FormazioneProfessionaleDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_FORMAZPROF);
	}



}
