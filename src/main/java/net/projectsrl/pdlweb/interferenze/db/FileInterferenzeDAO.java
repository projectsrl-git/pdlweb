
package net.projectsrl.pdlweb.interferenze.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class FileInterferenzeDAO extends PjNDAO_base {

    private static final String TABLE_NAME    = "FILE_INTERFERENZE";

    public static final String  ID_FILE		  = "ID_FILE";
    public static final String  ID_IMPIANTO   = "ID_IMPIANTO";
    public static final String  NOME_FILE   = "NOME_FILE";
    public static final String  TS_INS        = "TS_INS";
    public static final String  ID_UTENTE_INS = "ID_UTENTE_INS";
    
    public FileInterferenzeDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public FileInterferenzeDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public FileInterferenzeDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_FILE, Integer.class);
        addNoStringField(ID_IMPIANTO, Integer.class);
        
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_FILE))) {
            appendField(ID_FILE, whereCondition);
        }

        return whereCondition;
    }
    
    @Override
    public void insert() throws AppCrash {
    	
        Timestamp tsIns = new Timestamp(System.currentTimeMillis());
        setAttribute(TS_INS, tsIns);

        super.insert();

        retrieve();

    }

}
