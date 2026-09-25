
package net.projectsrl.dafne.richieste.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.misc.Util;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.dafne.db.TableIdentity;
import net.projectsrl.db.PjNDAO_base;

public class TipiRichiestaDAO extends PjNDAO_base {

    private static final String TABLE_NAME        = "TIPI_RICHIESTA";

    public static final String  ID_TIPO_RICHIESTA = "ID_TIPO_RICHIESTA";
    public static final String  CODICE            = "CODICE";
    public static final String  DESCRIZIONE       = "DESCRIZIONE";

    public TipiRichiestaDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public TipiRichiestaDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty(DB_NAME_PROPERTY) + TABLE_NAME);
    }

    public TipiRichiestaDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);

    }

    /**
     * Questo metodo
     * 
     * @return
     * @throws AppCrash
     * 
     * @see net.ssb.db.NDAO_base#whereCondition()
     */
    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_TIPO_RICHIESTA))) {
            appendField(ID_TIPO_RICHIESTA, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(CODICE))) {
            appendField(CODICE, whereCondition);
        }

        return whereCondition;
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_TIPO_RICHIESTA, Integer.class);

    }

    @Override
    public void delete() throws AppCrash {

        String idTipoRichiesta = getAttributeAsString(ID_TIPO_RICHIESTA);
        new TipoRichiesteAziendaDAO().delete(idTipoRichiesta);
        super.delete();

    }

    @Override
    public void setAttributesFromRequest(SsbServletRequest req) throws AppCrash {

        super.setAttributesFromRequest(req);

    }
    
    @Override
    public void insert() throws AppCrash {

        super.insert();
        Integer rowId = new TableIdentity(TABLE_NAME).getTableIdentity();
        setAttribute(ID_TIPO_RICHIESTA, rowId);
        
    }    
}