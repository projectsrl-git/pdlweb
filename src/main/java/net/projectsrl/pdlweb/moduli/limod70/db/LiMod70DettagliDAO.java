
package net.projectsrl.pdlweb.moduli.limod70.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.pdlweb.moduli.db.LiModDettagliDAO_base;

public class LiMod70DettagliDAO extends LiModDettagliDAO_base {

    private static final String TABLE_NAME             = "LIMOD70_DETTAGLI";

    
    public static final String  ID_PDL    		= "ID_PDL";
   
    public static final String  TS_INS          = "TS_INS";
    public static final String	ID_UTENTE_INS          = "ID_UTENTE_INS";
    public static final String  TABELLA_PARENT         = "TABELLA_PARENT";
    public static final String  ID_DETTAGLIO_PARENT    = "ID_DETTAGLIO_PARENT";
    

    public LiMod70DettagliDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public LiMod70DettagliDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public LiMod70DettagliDAO(DBTransaction transact, String tableName) throws AppCrash {

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
        LiMod70DettagliDAO daoOld=new LiMod70DettagliDAO();
        daoOld.setAttribute(ID_DETTAGLIO, idDettaglio);
        daoOld.retrieve();
        String nr=(String) daoOld.getAttribute(NR_DETTAGLIO);
        
        if (Util.IsEmpty((String) getAttribute(NR_DETTAGLIO))) {
            setAttribute(NR_DETTAGLIO, nr);
        }
        
        super.update();
        
        setNewState();
        
    }
    
    private void setNewState() throws AppCrash {
        Integer idModulo = (Integer) getAttribute(ID_MODULO);
        LiMod70DAO dao = new LiMod70DAO();
        dao.setAttribute(LiMod70DAO.ID_MODULO, idModulo);
        dao.setAttribute(LiMod70DAO.STATO, StatiRichiesta.APPROVED.getCode());
        dao.update();
    }

}
