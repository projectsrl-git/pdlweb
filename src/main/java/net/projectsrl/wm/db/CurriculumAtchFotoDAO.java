package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella FERIE_PERMESSI Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class CurriculumAtchFotoDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "CURRICULUM_ATCH";

	public static final String	DAGGANCIO_ATCH = "DAGGANCIO_ATCH";
	public static final String	DAGGANCIO = "DAGGANCIO";
	public static final String	PERCORSO = "PERCORSO";
	public static final String	FILENAME = "FILENAME";
	public static final String	AZIENDA = "AZIENDA";
	public static final String	MATRICOLA = "MATRICOLA";
	public static final String	DATA_CARICAMENTO = "DATA_CARICAMENTO";
	public static final String	ORA_CARICAMENTO = "ORA_CARICAMENTO";


	public CurriculumAtchFotoDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(DAGGANCIO_ATCH);
	}


	public CurriculumAtchFotoDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(DAGGANCIO_ATCH);
	}

	public CurriculumAtchFotoDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(DAGGANCIO_ATCH);

	}
}
