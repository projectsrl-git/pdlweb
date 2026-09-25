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
public class AziendeTipiCostoDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "AZIENDE_TIPI_COSTO";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String ID_TIPO_COSTO = "ID_TIPO_COSTO";
	public static final String TIPO_COSTO_CODICE = "TIPO_COSTO_CODICE";
	public static final String TIPO_COSTO_DESCRIZIONE = "TIPO_COSTO_DESCRIZIONE";
	

	/**
	 * Costruttore
	 */
	public AziendeTipiCostoDAO() throws AppCrash {
		super( NOME_TABELLA);
		setUniqueIdentifier(ID_TIPO_COSTO);		
	}

	/**
	 * Costruttore con DBTransaction.
	 * 
	 * @param transact  transazione
	 * 
	 */
	public AziendeTipiCostoDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_TIPO_COSTO);		
	}

	public AziendeTipiCostoDAO(DBTransaction transact, String tableName)
			throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_TIPO_COSTO);
	}
}
