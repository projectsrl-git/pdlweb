package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella AZIENDE Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class AziendeBancheDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "AZIENDE_BANCHE";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String NOME_BANCA = "NOME_BANCA";
	public static final String CODICE_ABI = "CODICE_ABI";
	public static final String CODICE_CAB = "CODICE_CAB";
	public static final String CONTO_CORRENTE = "CONTO_CORRENTE";
	public static final String CODICE_IBAN = "CODICE_IBAN";
	public static final String CODICE_CIN      = "CODICE_CIN";
	public static final String CODICE_CHK      = "CODICE_CHK";
	public static final String ID_BANCA      = "ID_BANCA";
	

	public AziendeBancheDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_BANCA);
	}


	public AziendeBancheDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_BANCA);
	}

	public AziendeBancheDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_BANCA);

	}
}
