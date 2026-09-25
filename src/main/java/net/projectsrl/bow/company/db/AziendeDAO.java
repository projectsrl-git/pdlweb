
package net.projectsrl.bow.company.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class AziendeDAO extends PjNDAO_base {

    private static final String TABLE_NAME        = "AZIENDE";

    public static final String  ID_AZIENDA        = "ID_AZIENDA";
    public static final String  ID_AZIENDA_PARENT = "ID_AZIENDA_PARENT";
    public static final String  CODICE            = "CODICE";
    public static final String  RAGSOC            = "RAGSOC";
    public static final String  ALIAS_AZIENDA     = "ALIAS_AZIENDA";
    public static final String  TIPO_AZIENDA      = "TIPO_AZIENDA";

    public AziendeDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AziendeDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AziendeDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(ID_AZIENDA_PARENT, Integer.class);

    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_AZIENDA))) {
            appendField(ID_AZIENDA, whereCondition);

        }

        return whereCondition;
    }
}
