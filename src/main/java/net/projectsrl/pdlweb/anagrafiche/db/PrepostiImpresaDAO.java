
package net.projectsrl.pdlweb.anagrafiche.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class PrepostiImpresaDAO extends PjNDAO_base {

    private static final String TABLE_NAME                    = "PREPOSTI_IMPRESA";

    public static final String  ID_PREPOSTO_IMPRESA           = "ID_PREPOSTO_IMPRESA";
    public static final String  ID_AZIENDA                    = "ID_AZIENDA";
    public static final String  NOME_IMPRESA                  = "NOME_IMPRESA";
    public static final String  NOME_COGNOME_PREPOSTO_IMPRESA = "NOME_COGNOME_PREPOSTO_IMPRESA";

    public PrepostiImpresaDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public PrepostiImpresaDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public PrepostiImpresaDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_PREPOSTO_IMPRESA, Integer.class);
        addNoStringField(ID_AZIENDA, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_PREPOSTO_IMPRESA))) {
            appendField(ID_PREPOSTO_IMPRESA, whereCondition);
        }

        return whereCondition;
    }

}
