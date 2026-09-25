
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
public class CvVociTariffaDAO extends PjDAO_base {

	private static final String NOME_TABELLA = "CV_VOCITARIFFA";

	// colonne
	public static final String DAGGANCIO = "DAGGANCIO";
	public static final String ID_VOCE_TARIFFA = "ID_VOCE_TARIFFA";
	public static final String VOCE_TARIFFA = "VOCE_TARIFFA";
	public static final String PERCENTUALE = "PERCENTUALE";	

	/**
	 * Costruttore
	 */
	public CvVociTariffaDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_VOCE_TARIFFA);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public CvVociTariffaDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_VOCE_TARIFFA);		
	}

	public CvVociTariffaDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_VOCE_TARIFFA);
	}



}
