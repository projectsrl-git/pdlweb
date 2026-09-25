
package net.projectsrl.pdlweb.interferenze.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class StoricoInterferenzeDAO extends PjNDAO_base {

	private static final String TABLE_NAME = "STORICO_INTERFERENZE";

	public static final String ID_MODULO = "ID_MODULO";
	public static final String ID_IMPIANTO = "ID_IMPIANTO";
	public static final String ID_AZIENDA = "ID_AZIENDA";
	public static final String CODICE_TURNO = "CODICE_TURNO";
	public static final String NOME_FILE = "NOME_FILE";
	public static final String NOME_FILE_COMPLETO = "NOME_FILE_COMPLETO";
	public static final String ORA_STAMPA = "ORA_STAMPA";
	
	public static final String DATA_STAMPA = "DATA_STAMPA";
	public static final String ID_UTENTE = "ID_UTENTE";
		
	public StoricoInterferenzeDAO() throws AppCrash {

		super(TABLE_NAME);
	}

	public StoricoInterferenzeDAO(DBTransaction transact) throws AppCrash {

		super(transact, TABLE_NAME);
	}

	public StoricoInterferenzeDAO(DBTransaction transact, String tableName) throws AppCrash {

		super(transact, tableName);
	}

	@Override
	protected void init() throws AppCrash {

		super.init();

		addNoStringField(ID_MODULO, Integer.class);
		addNoStringField(ID_AZIENDA, Integer.class);
		addNoStringField(ID_IMPIANTO, Integer.class);
		
		addNoStringField(ID_UTENTE, Integer.class);
	}

	@Override
	protected WhereCondition whereCondition() throws AppCrash {

		WhereCondition whereCondition = new WhereCondition(this);

		if (Util.IsNotEmpty(getAttribute(ID_MODULO))) {
			appendField(ID_MODULO, whereCondition);
		} 

		return whereCondition;
	}
	    

}