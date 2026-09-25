package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;
import net.projectsrl.random.RandomIdentifier;

/**
 * Classe che rappresenta la tabella APPROVATORI Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class CaricaCedoliniDAO extends PjDAO_base {

	private static final String NOME_TABELLA = "CARICA_CEDOLINI";

	public static final String ID_CEDOLINO = "ID_CEDOLINO";
	public static final String AZIENDA = "AZIENDA";
	public static final String DIPENDENTE = "DIPENDENTE";
	public static final String ANNO = "ANNO";
	public static final String MESE = "MESE";
	public static final String LINK = "LINK";
	public static final String CHECK1 = "CHECK1";
	public static final String CHECK2 = "CHECK2";
	public static final String CHECK3 = "CHECK3";
	public static final String NR_PAGINE = "?INR_PAGINE";
	public static final String PUBBLICA = "PUBBLICA";

	public CaricaCedoliniDAO() throws AppCrash {

		super(NOME_TABELLA);
		setUniqueIdentifier(ID_CEDOLINO);
	}

	public CaricaCedoliniDAO(DBTransaction transact) throws AppCrash {

		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_CEDOLINO);
	}

	public CaricaCedoliniDAO(DBTransaction transact, String tableName) throws AppCrash {

		super(transact, tableName);
		setUniqueIdentifier(ID_CEDOLINO);

	}
	
	@Override
	public void insert() throws AppCrash {
		
		setField(ID_CEDOLINO,RandomIdentifier.GetInstance().getIdentifier(15));
		super.insert();
	}	

	@Override
	protected String whereCondition() throws AppCrash {
		String whereCondition = null;
		StringBuffer tempBuffer = new StringBuffer();

		String azienda = getField(AZIENDA);
		String anno = getField(ANNO);
		String mese = getField(MESE);
		String dipendente = getField(DIPENDENTE);

		if (!"".equals(azienda) && !"".equals(anno)  && !"".equals(mese)  && !"".equals(dipendente) ) {
			tempBuffer.append(" WHERE "+AZIENDA+"  = '").append(azienda).append("'");
			tempBuffer.append(" AND "+ANNO+"  = '").append(anno).append("'");
			tempBuffer.append(" AND "+MESE+"  = '").append(mese).append("'");
			tempBuffer.append(" AND "+DIPENDENTE+"  = '").append(dipendente).append("'");
		}

		whereCondition = tempBuffer.toString();
		return whereCondition;
	}
}
