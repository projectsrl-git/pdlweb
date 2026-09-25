package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella AZIENDE VISITATORI Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class AziendeVisitatoriDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "AZIENDE_VISITATORI";

	public static final String	CODICE = "CODICE";
	public static final String	CODICE_PARENT = "CODICE_PARENT";
	public static final String	RAGSOC = "RAGSOC";
	public static final String	RAGRID = "RAGRID";
	public static final String	SETTORE = "SETTORE";
	public static final String	INDIRIZZO = "INDIRIZZO";
	public static final String	CITTA = "CITTA";
	public static final String	CAP = "CAP";
	public static final String	PROV = "PROV";
	public static final String	RESP1 = "RESP1";
	public static final String	MAIL1 = "MAIL1";
	public static final String	TEL_FISSO1 = "TEL_FISSO1";
	public static final String	TEL_CELLULARE1 = "TEL_CELLULARE1";
	public static final String	RESP2 = "RESP2";
	public static final String	MAIL2 = "MAIL2";
	public static final String	TEL_FISSO2 = "TEL_FISSO2";
	public static final String	TEL_CELLULARE2 = "TEL_CELLULARE2";
	public static final String	MAIL_AMMINIS = "MAIL_AMMINIS";
	
	public static final String	ID_AZIENDA = "ID_AZIENDA";
	
	

	public AziendeVisitatoriDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_AZIENDA);
	}


	public AziendeVisitatoriDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_AZIENDA);
	}

	public AziendeVisitatoriDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_AZIENDA);

	}
}
