
package net.projectsrl.dafne.richieste.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.misc.Util;
import net.projectsrl.dafne.db.TableIdentity;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.DeleteQueryRows;
import net.projectsrl.webapp.core.ForeingnKeysRows_itf;
import net.projectsrl.webapp.core.InsertMultipleReferences;

public class TipoRichiesteAziendaDAO extends PjNDAO_base implements ForeingnKeysRows_itf {

    private static final String TABLE_NAME                      = "TIPO_RICHIESTE_AZIENDA";
    private static final String DATA_SET_TIPO_RICHIESTE_AZIENDA = "DataSetTipoRichiesteAzienda";

    public static final String  ID_TIPO_RICHIESTA_AZIENDA       = "ID_TIPO_RICHIESTA_AZIENDA";
    public static final String  ID_TIPO_RICHIESTA               = "ID_TIPO_RICHIESTA";
    public static final String  ID_AZIENDA                      = "ID_AZIENDA";
    public static final String  NUM_LIVELLI_APPR                = "NUM_LIVELLI_APPR";

    public TipoRichiesteAziendaDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public TipoRichiesteAziendaDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty(DB_NAME_PROPERTY) + TABLE_NAME);
    }

    public TipoRichiesteAziendaDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);

    }

    private class DeleteTipoRichiestaAzienda extends DeleteQueryRows<TipoRichiesteAziendaDAO, Integer> {

        public DeleteTipoRichiestaAzienda() {
            super(DATA_SET_TIPO_RICHIESTE_AZIENDA, TipoRichiesteAziendaDAO.ID_TIPO_RICHIESTA,
                    TipoRichiesteAziendaDAO.ID_TIPO_RICHIESTA_AZIENDA);

        }

    }

    private class InsertTipoRichiestaAzienda extends InsertMultipleReferences<TipoRichiesteAziendaDAO> {

        public InsertTipoRichiestaAzienda() {
            super(DATA_SET_TIPO_RICHIESTE_AZIENDA, ID_TIPO_RICHIESTA, ID_AZIENDA);
        }

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

        if (Util.IsNotEmpty(getAttribute(ID_TIPO_RICHIESTA_AZIENDA))) {
            appendField(ID_TIPO_RICHIESTA_AZIENDA, whereCondition);
        }

        return whereCondition;

    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_TIPO_RICHIESTA_AZIENDA, Integer.class);
        addNoStringField(ID_TIPO_RICHIESTA, Integer.class);
        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(NUM_LIVELLI_APPR, Integer.class);

    }

    public void insert(String codeList, Integer keyId) throws AppCrash {

        new InsertTipoRichiestaAzienda().insert(codeList, keyId);

    }

    @Override
    public void delete(String idTipoRichiesta) throws AppCrash {

        new DeleteTipoRichiestaAzienda().delete(idTipoRichiesta);

    }

    @Override
    public void insert() throws AppCrash {

        super.insert();
        Integer rowId = new TableIdentity(TABLE_NAME).getTableIdentity();
        setAttribute(ID_TIPO_RICHIESTA_AZIENDA, rowId);
        
    }

    public Object getSelectedCodeList(String idTipoRichiesta) throws AppCrash {

        return new InsertTipoRichiestaAzienda().getSelectedCodeList(idTipoRichiesta);
    }

}