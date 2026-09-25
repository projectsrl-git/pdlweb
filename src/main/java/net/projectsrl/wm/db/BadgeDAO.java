package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella BADGE Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class BadgeDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "BADGE";

	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String	ID_BADGE = "ID_BADGE";
	public static final String	CODICE_BADGE = "CODICE_BADGE";
	public static final String	CODICE_MECCANOG = "CODICE_MECCANOG";
	public static final String	TIPOLOGIA = "TIPOLOGIA";
	public static final String	AZIENDA = "AZIENDA";
	public static final String	ID_SOGGETTO = "ID_SOGGETTO";
	public static final String	DATA_INIZIO = "DATA_INIZIO";
	public static final String	DATA_FINE = "DATA_FINE";
	public static final String	SOSPESO = "SOSPESO";
	

	public BadgeDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_BADGE);
	}


	public BadgeDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(ID_BADGE);
	}

	public BadgeDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_BADGE);

	}
}
