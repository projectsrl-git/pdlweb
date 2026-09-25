
package net.projectsrl.pdlweb.pdl.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.DeleteQueryRows;
import net.projectsrl.webapp.core.ForeingnKeysRows_itf;
import net.projectsrl.webapp.core.InsertMultipleReferences;

public class DipendenzeDAO extends PjNDAO_base implements ForeingnKeysRows_itf {

    private static final String TABLE_NAME              = "DIPENDENZE_PDL";
    private static final String DATA_SET_DIPENDENZE_PDL = "DataSetDipendenzePDL";

    public static final String  ID_DIPENDENZE_PDL         = "ID_DIPENDENZE_PDL";
    public static final String  ID_PDL                 = "ID_PDL";
    public static final String  ID_DIPENDENZA              = "ID_DIPENDENZA";

    public DipendenzeDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public DipendenzeDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty(DB_NAME_PROPERTY) + TABLE_NAME);
    }

    public DipendenzeDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);

    }

    private class DeleteDipendenzePDL extends DeleteQueryRows<DipendenzeDAO, Integer> {

        public DeleteDipendenzePDL() {
            super(DATA_SET_DIPENDENZE_PDL, DipendenzeDAO.ID_PDL, DipendenzeDAO.ID_DIPENDENZE_PDL);
        }

    }

    private class InsertDipendenzePDL extends InsertMultipleReferences<DipendenzeDAO> {

        public InsertDipendenzePDL() {
            super(DATA_SET_DIPENDENZE_PDL,ID_PDL, ID_DIPENDENZA);
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

        if (Util.IsNotEmpty(getAttribute(ID_DIPENDENZE_PDL))) {
            appendField(ID_DIPENDENZE_PDL, whereCondition);
        }

        return whereCondition;

    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_DIPENDENZE_PDL, Integer.class);
        addNoStringField(ID_PDL, Integer.class);
        addNoStringField(ID_DIPENDENZA, Integer.class);

    }

    public void insert(String codeList, Integer keyId) throws AppCrash {

        new InsertDipendenzePDL().insert(codeList, keyId);

    }

    @Override
    public void delete(String idNews) throws AppCrash {

        new DeleteDipendenzePDL().delete(idNews);

    }

    public Object getSelectedCodeList(String idNews) throws AppCrash {

        return new InsertDipendenzePDL().getSelectedCodeList(idNews);
    }

}