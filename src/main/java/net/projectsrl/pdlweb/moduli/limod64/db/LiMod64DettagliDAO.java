
package net.projectsrl.pdlweb.moduli.limod64.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.pdlweb.moduli.db.LiModDettagliDAO_base;

public class LiMod64DettagliDAO extends LiModDettagliDAO_base {

    private static final String TABLE_NAME             = "LIMOD64_DETTAGLI";

    public static final String  ID_PDL    		= "ID_PDL";
    public static final String  IMPRESA_TESTO    		= "IMPRESA_TESTO";
    public static final String  NOME_COGNOME_PREPOSTO_IMPRESA = "NOME_COGNOME_PREPOSTO_IMPRESA";
    public static final String ODL		="ODL";
    public static final String DESCR_ATTIVITA		="DESCR_ATTIVITA";
    
    public static final String  STATO_RIGA    	= "STATO_RIGA";
    
    
    public static final String  TS_INS          = "TS_INS";
    public static final String	ID_UTENTE_INS          = "ID_UTENTE_INS";
    public static final String  TABELLA_PARENT         = "TABELLA_PARENT";
    public static final String  ID_DETTAGLIO_PARENT    = "ID_DETTAGLIO_PARENT";
    

    public LiMod64DettagliDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public LiMod64DettagliDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public LiMod64DettagliDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

    	addNoStringField(ID_PDL, Integer.class);
        super.init();

        addNoStringField(ID_DETTAGLIO_PARENT, Integer.class);
        
    
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_DETTAGLIO))) {
            appendField(ID_DETTAGLIO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(NR_DETTAGLIO))) {
            appendField(NR_DETTAGLIO, whereCondition);
        }

        return whereCondition;
    }

  


    @Override
    public void insert() throws AppCrash {
        super.insert();
    }

    @Override
    public void update() throws AppCrash {
    	
        Integer idDettaglio=(Integer) getAttribute(ID_DETTAGLIO);
        LiMod64DettagliDAO daoOld=new LiMod64DettagliDAO();
        daoOld.setAttribute(ID_DETTAGLIO, idDettaglio);
        daoOld.retrieve();
        String nr=(String) daoOld.getAttribute(NR_DETTAGLIO);
        
        if (Util.IsEmpty((String) getAttribute(NR_DETTAGLIO))) {
            setAttribute(NR_DETTAGLIO, nr);
        }
        
    	setAttribute(STATO_RIGA, StatiRichiesta.APPROVED.getCode());
    	
        super.update();
        
        setNewState();
        
    }
    
    private void setNewState() throws AppCrash {
        Integer idModulo = (Integer) getAttribute(ID_MODULO);
        LiMod64DAO dao = new LiMod64DAO();
        dao.setAttribute(LiMod64DAO.ID_MODULO, idModulo);
        dao.setAttribute(LiMod64DAO.STATO, StatiRichiesta.APPROVED.getCode());
        dao.update();
        
        
    }

}
