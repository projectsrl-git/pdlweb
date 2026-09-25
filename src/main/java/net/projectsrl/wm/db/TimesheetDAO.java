package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.projectsrl.db.PjDAO_base;
import net.projectsrl.wm.utils.Utils;

/**
 * Classe che rappresenta la tabella TIMESHT Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class TimesheetDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "TIMESHT";

	public static final String IDTIMESHT = "IDTIMESHT";
	public static final String IDRISUMANA = "IDRISUMANA";
	public static final String IDCOMMESSA = "IDCOMMESSA";
	public static final String IDCOMPXCOM = "IDCOMPXCOM";
	public static final String IDTIPOORA = "IDTIPOORA";
	public static final String IDDETTORA = "IDDETTORA";
	public static final String DATA = "DATA";
	public static final String MINUTI = "?IMINUTI";
	public static final String ORE = "?IORE";
	public static final String ANNO = "ANNO";
	public static final String MESE = "MESE";
	public static final String CODRIS = "CODRIS";
	public static final String DESRIS = "DESRIS";
	public static final String TIPOORA = "TIPOORA";
	public static final String DETTORA = "DETTORA";
	public static final String COMMESSA = "COMMESSA";
	public static final String COMPCOMM = "COMPCOMM";
	public static final String DSCOMPCOMM = "DSCOMPCOMM";
	//public static final String B_BLOCCATO = "?IB_BLOCCATO";
	public static final String ANNOTAZIONI = "ANNOTAZIONI";
	//public static final String FLGFATT = "?IFLGFATT";
	public static final String UTENTEINS = "UTENTEINS";
	public static final String D_DATAINS = "D_DATAINS";
	public static final String UTENTEMOD = "UTENTEMOD";
	public static final String D_DATAMOD = "D_DATAMOD";
	
	public TimesheetDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(IDTIMESHT);
		
	}

	public TimesheetDAO(DBTransaction transact) throws AppCrash {
		super(transact, Config.GetInstance().getProperty("DBEntity.NomeDB")
				+ NOME_TABELLA);
		setUniqueIdentifier(IDTIMESHT);
	}

	public TimesheetDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(IDTIMESHT);
	}
	

    public void update() throws AppCrash {
        super.update();
    }

    public void insert() throws AppCrash {
        setField(IDTIMESHT, Utils.getUnique());
        super.insert();
    }

    protected String whereCondition() throws AppCrash {

        String whereCondition = null;
        StringBuffer tempBuffer = new StringBuffer();

        String idCommessa = getField(IDCOMMESSA);
        String compComm = getField(COMPCOMM);
        String data = Utils.ribaltaData(getField(DATA));

        tempBuffer.append(" WHERE " + IDCOMMESSA + "  = '" + idCommessa + "' AND " + COMPCOMM + " ='" + compComm
                + "' AND " + DATA + " ='" + data + "'");

        whereCondition = tempBuffer.toString();
        return whereCondition;
    }

    public void setRigaColonnaSeek(String riga, String colonna) throws AppCrash {
        String[] campiChiave = riga.split("\\,");
        
        String risorsa = campiChiave[0];
        String mese = campiChiave[1];
        String anno = campiChiave[2];
        String idCommessa = campiChiave[3];
        String commessa = campiChiave[4];
        String compComm = campiChiave[5];
        String dsCompComm = campiChiave[6];
        String data = colonna;
        
        DipendentiDAO dipendenteDao = new DipendentiDAO();
        dipendenteDao.setField(DipendentiDAO.ID_DIPENDENTE, risorsa);
        if (dipendenteDao.retrieve()) {
            setField(IDRISUMANA, dipendenteDao.getField(DipendentiDAO.ID_DIPENDENTE));
            setField(DESRIS, dipendenteDao.getField(DipendentiDAO.NOMINATIVO));
        }
        
        setField(CODRIS, risorsa);
        setField(IDCOMMESSA, idCommessa);
        setField(COMMESSA, commessa);
        setField(COMPCOMM, compComm);
        setField(DSCOMPCOMM, dsCompComm);
        setField(ANNO, anno);
        setField(MESE, mese);
        setField(DATA, data);
    }
    
    private int[] getParts(String num) {
        int[] array = { 0, 0 };
        
        int indice =num.length()-2;
        array[0] = Integer.parseInt(num.substring(0, indice));
        array[1] = Integer.parseInt(num.substring(indice));
        return array;
    }
    
	
	
}
