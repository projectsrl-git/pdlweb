package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella PROFILO_ORARIO Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class ProfiloOrarioDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "PROFILO_ORARIO";

	public static final String	ID_PROFILO = "ID_PROFILO";
	public static final String	NOME_PROFILO = "NOME_PROFILO";
	public static final String	LUN_ORA_ENTRATA_1 = "LUN_ORA_ENTRATA_1";
	public static final String	LUN_ORA_USCITA_1 = "LUN_ORA_USCITA_1";
	public static final String	LUN_ORA_ENTRATA_2 = "LUN_ORA_ENTRATA_2";
	public static final String	LUN_ORA_USCITA_2 = "LUN_ORA_USCITA_2";
	public static final String	LUN_MINUTI_PAUSA = "LUN_MINUTI_PAUSA";
	
	public static final String	MAR_ORA_ENTRATA_1 = "MAR_ORA_ENTRATA_1";
	public static final String	MAR_ORA_USCITA_1 = "MAR_ORA_USCITA_1";
	public static final String	MAR_ORA_ENTRATA_2 = "MAR_ORA_ENTRATA_2";
	public static final String	MAR_ORA_USCITA_2 = "MAR_ORA_USCITA_2";
	public static final String	MAR_MINUTI_PAUSA = "MAR_MINUTI_PAUSA";
	
	public static final String	MER_ORA_ENTRATA_1 = "MER_ORA_ENTRATA_1";
	public static final String	MER_ORA_USCITA_1 = "MER_ORA_USCITA_1";
	public static final String	MER_ORA_ENTRATA_2 = "MER_ORA_ENTRATA_2";
	public static final String	MER_ORA_USCITA_2 = "MER_ORA_USCITA_2";
	public static final String	MER_MINUTI_PAUSA = "MER_MINUTI_PAUSA";
	
	public static final String	GIO_ORA_ENTRATA_1 = "GIO_ORA_ENTRATA_1";
	public static final String	GIO_ORA_USCITA_1 = "GIO_ORA_USCITA_1";
	public static final String	GIO_ORA_ENTRATA_2 = "GIO_ORA_ENTRATA_2";
	public static final String	GIO_ORA_USCITA_2 = "GIO_ORA_USCITA_2";
	public static final String	GIO_MINUTI_PAUSA = "GIO_MINUTI_PAUSA";
	
	public static final String	VEN_ORA_ENTRATA_1 = "VEN_ORA_ENTRATA_1";
	public static final String	VEN_ORA_USCITA_1 = "VEN_ORA_USCITA_1";
	public static final String	VEN_ORA_ENTRATA_2 = "VEN_ORA_ENTRATA_2";
	public static final String	VEN_ORA_USCITA_2 = "VEN_ORA_USCITA_2";
	public static final String	VEN_MINUTI_PAUSA = "VEN_MINUTI_PAUSA";
	
	public static final String	SAB_ORA_ENTRATA_1 = "SAB_ORA_ENTRATA_1";
	public static final String	SAB_ORA_USCITA_1 = "SAB_ORA_USCITA_1";
	public static final String	SAB_ORA_ENTRATA_2 = "SAB_ORA_ENTRATA_2";
	public static final String	SAB_ORA_USCITA_2 = "SAB_ORA_USCITA_2";
	public static final String	SAB_MINUTI_PAUSA = "SAB_MINUTI_PAUSA";
	
	public static final String	DOM_ORA_ENTRATA_1 = "DOM_ORA_ENTRATA_1";
	public static final String	DOM_ORA_USCITA_1 = "DOM_ORA_USCITA_1";
	public static final String	DOM_ORA_ENTRATA_2 = "DOM_ORA_ENTRATA_2";
	public static final String	DOM_ORA_USCITA_2 = "DOM_ORA_USCITA_2";
	public static final String	DOM_MINUTI_PAUSA = "DOM_MINUTI_PAUSA";
	
	public static final String	INDENNITA_PAUSA = "INDENNITA_PAUSA";
	public static final String	MAGG_TURNO_NOTTURNO = "MAGG_TURNO_NOTTURNO";
	public static final String	TIMB_LIBERE = "TIMB_LIBERE";
	public static final String	TIMBRATURE = "TIMBRATURE";
	public static final String	AZIENDA = "AZIENDA";
	public static final String	PREDEFINITO = "PREDEFINITO";
	

	public ProfiloOrarioDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_PROFILO);
	}


	public ProfiloOrarioDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_PROFILO);
	}

	public ProfiloOrarioDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_PROFILO);

	}
}
