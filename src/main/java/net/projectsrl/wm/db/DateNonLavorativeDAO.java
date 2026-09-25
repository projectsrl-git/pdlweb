package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella CALENDARIO_LAVORATIVO Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class DateNonLavorativeDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "DATE_NON_LAVORATIVE";

	public static final String	ID_RIGA = "ID_RIGA";
	public static final String	DATA = "DATA";
	public static final String	GIORNO = "GIORNO";
	public static final String	DESCRIZIONE = "DESCRIZIONE";
	public static final String	ANNO = "ANNO";
	public static final String  RICORSIVA = "RICORSIVA";

	public DateNonLavorativeDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_RIGA);
	}


	public DateNonLavorativeDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_RIGA);
	}

	public DateNonLavorativeDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_RIGA);

	}
}
