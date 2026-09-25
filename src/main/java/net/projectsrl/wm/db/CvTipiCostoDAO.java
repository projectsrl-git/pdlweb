
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
public class CvTipiCostoDAO extends PjDAO_base {

	private static final String NOME_TABELLA = "CV_TIPICOSTO";

	// colonne
	public static final String DAGGANCIO = "DAGGANCIO";
	public static final String ID_TIPO_COSTO = "ID_TIPO_COSTO";
	public static final String TIPO_COSTO = "TIPO_COSTO";	

	/**
	 * Costruttore
	 */
	public CvTipiCostoDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_TIPO_COSTO);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public CvTipiCostoDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_TIPO_COSTO);		
	}

	public CvTipiCostoDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_TIPO_COSTO);
	}



}
