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
public class AziendeCentriCostoDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "AZIENDE_CENTRI_COSTO";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String ID_CENTRO_COSTO = "ID_CENTRO_COSTO";
	public static final String CENTRO_COSTO_CODICE = "CENTRO_COSTO_CODICE";
	public static final String CENTRO_COSTO_DESCRIZIONE = "CENTRO_COSTO_DESCRIZIONE";
	

	/**
	 * Costruttore
	 */
	public AziendeCentriCostoDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_CENTRO_COSTO);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public AziendeCentriCostoDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_CENTRO_COSTO);		
	}

	public AziendeCentriCostoDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_CENTRO_COSTO);
	}
}
