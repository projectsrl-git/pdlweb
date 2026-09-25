package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella BADGE Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class TrasferteDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "TRASFERTE";

	public static final String	ID_TRASFERTA = "ID_TRASFERTA";
	public static final String	DESCRIZIONE = "DESCRIZIONE";
	public static final String	NOTE = "NOTE";
	public static final String	STATO_APPROVAZIONE = "STATO_APPROVAZIONE";
	public static final String	DIPENDENTE = "DIPENDENTE";
	public static final String	DATA_DAL = "DATA_DAL";
	public static final String	DATA_AL = "DATA_AL";
	public static final String	ORE_DAL = "ORE_DAL";
	public static final String	ORE_AL = "ORE_AL";
	public static final String	ORE = "?IORE";
	public static final String	REVOCATO = "REVOCATO";
	public static final String	AZIENDA_TENDINA = "AZIENDA_TENDINA";
	public static final String	APPROVAZIONE = "APPROVAZIONE";
	public static final String	CLIENTE = "CLIENTE";
	public static final String	COMMESSA = "COMMESSA";
	public static final String	LUOGO = "LUOGO";
	public static final String	TIPO = "TIPO";
	

	public TrasferteDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_TRASFERTA);
	}


	public TrasferteDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_TRASFERTA);
	}

	public TrasferteDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_TRASFERTA);

	}
}
