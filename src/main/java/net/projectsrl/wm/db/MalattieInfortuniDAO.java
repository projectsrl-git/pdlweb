package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella MALATTIE_INFORTUNI Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class MalattieInfortuniDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "MALATTIE_INFORTUNI";

	public static final String	ID_MALATTIAINFORTUNIO = "ID_MALATTIAINFORTUNIO";
	public static final String	ID_MALATTIAINFORTUNIO_ORIGINE = "ID_MALATTIAINFORTUNIO_ORIGINE";
	public static final String	DIPENDENTE = "DIPENDENTE";
	public static final String	TIPO_PERMESSO = "TIPO_PERMESSO";
	public static final String	DATA_DAL = "DATA_DAL";
	public static final String	DATA_AL = "DATA_AL";
	public static final String	ORE_DAL = "ORE_DAL";
	public static final String	ORE_AL = "ORE_AL";
	public static final String	ORE = "?IORE";
	public static final String	REVOCATO = "REVOCATO";
	public static final String	AZIENDA_TENDINA = "AZIENDA_TENDINA";
	public static final String	APPROVAZIONE = "APPROVAZIONE";
	public static final String  PROTOCOLLO_INPS = "PROTOCOLLO_INPS";
	

	public MalattieInfortuniDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_MALATTIAINFORTUNIO);
	}


	public MalattieInfortuniDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_MALATTIAINFORTUNIO);
	}

	public MalattieInfortuniDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_MALATTIAINFORTUNIO);

	}
}
