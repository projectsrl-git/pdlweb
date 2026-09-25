package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella TIMBRATURE Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class TimbratureDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "TIMBRATURE";

	public static final String	ID_TIMBRATURA = "ID_TIMBRATURA";
	public static final String	ID_DIPENDENTE = "ID_DIPENDENTE";
	public static final String	ID_AZIENDA = "ID_AZIENDA";
	public static final String	DATA_TIMBRATURA = "DATA_TIMBRATURA";
	public static final String	ORA_TIMBRATURA = "ORA_TIMBRATURA";
	public static final String	ENT_USC = "ENT_USC";
	public static final String	SCARTATA = "SCARTATA";

	public TimbratureDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_TIMBRATURA);
	}


	public TimbratureDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_TIMBRATURA);
	}

	public TimbratureDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_TIMBRATURA);

	}
}
