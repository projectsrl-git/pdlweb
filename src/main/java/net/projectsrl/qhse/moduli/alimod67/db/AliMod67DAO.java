
package net.projectsrl.qhse.moduli.alimod67.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class AliMod67DAO extends AliModDAO_base {

    private static final String TABLE_NAME             = "ALIMOD67";

    public static final String  OSSERVATO              = "OSSERVATO";
    public static final String  MANSIONE               = "MANSIONE";
    public static final String  ATTIVITA_OSSERVATA     = "ATTIVITA_OSSERVATA";
    public static final String  OSSERVATORE            = "OSSERVATORE";
    public static final String  ALTRO_OSSERVATORE      = "ALTRO_OSSERVATORE";

    public static final String  FL_GEST_POSTAZIONE_1   = "FL_GEST_POSTAZIONE_1";
    public static final String  FL_GEST_POSTAZIONE_2   = "FL_GEST_POSTAZIONE_2";
    public static final String  FL_GEST_POSTAZIONE_3   = "FL_GEST_POSTAZIONE_3";
    public static final String  FL_GEST_POSTAZIONE_4   = "FL_GEST_POSTAZIONE_4";

    public static final String  FL_ERGONOMIA_1         = "FL_ERGONOMIA_1";
    public static final String  FL_ERGONOMIA_2         = "FL_ERGONOMIA_2";
    public static final String  FL_ERGONOMIA_3         = "FL_ERGONOMIA_3";
    public static final String  FL_ERGONOMIA_4         = "FL_ERGONOMIA_4";
    public static final String  FL_ERGONOMIA_5         = "FL_ERGONOMIA_5";
    public static final String  FL_ERGONOMIA_6         = "FL_ERGONOMIA_6";

    public static final String  FL_PROCEDURE_1         = "FL_PROCEDURE_1";
    public static final String  FL_PROCEDURE_2         = "FL_PROCEDURE_2";
    public static final String  FL_PROCEDURE_3         = "FL_PROCEDURE_3";
    public static final String  FL_PROCEDURE_4         = "FL_PROCEDURE_4";

    public static final String  FL_ATTREZZATURE_1      = "FL_ATTREZZATURE_1";
    public static final String  FL_ATTREZZATURE_2      = "FL_ATTREZZATURE_2";

    public static final String  FL_DPI_1               = "FL_DPI_1";
    public static final String  FL_DPI_2               = "FL_DPI_2";
    public static final String  FL_DPI_3               = "FL_DPI_3";
    public static final String  FL_DPI_4               = "FL_DPI_4";
    public static final String  FL_DPI_5               = "FL_DPI_5";
    public static final String  FL_DPI_6               = "FL_DPI_6";
    public static final String  FL_DPI_7               = "FL_DPI_7";

    public static final String  FL_VALUTAZIONE_1       = "FL_VALUTAZIONE_1";
    public static final String  FL_VALUTAZIONE_2       = "FL_VALUTAZIONE_2";

    public static final String  NOTE_GEST_POSTAZIONE_1 = "NOTE_GEST_POSTAZIONE_1";
    public static final String  NOTE_GEST_POSTAZIONE_2 = "NOTE_GEST_POSTAZIONE_2";
    public static final String  NOTE_GEST_POSTAZIONE_3 = "NOTE_GEST_POSTAZIONE_3";
    public static final String  NOTE_GEST_POSTAZIONE_4 = "NOTE_GEST_POSTAZIONE_4";

    public static final String  NOTE_ERGONOMIA_1       = "NOTE_ERGONOMIA_1";
    public static final String  NOTE_ERGONOMIA_2       = "NOTE_ERGONOMIA_2";
    public static final String  NOTE_ERGONOMIA_3       = "NOTE_ERGONOMIA_3";
    public static final String  NOTE_ERGONOMIA_4       = "NOTE_ERGONOMIA_4";
    public static final String  NOTE_ERGONOMIA_5       = "NOTE_ERGONOMIA_5";
    public static final String  NOTE_ERGONOMIA_6       = "NOTE_ERGONOMIA_6";

    public static final String  NOTE_PROCEDURE_1       = "NOTE_PROCEDURE_1";
    public static final String  NOTE_PROCEDURE_2       = "NOTE_PROCEDURE_2";
    public static final String  NOTE_PROCEDURE_3       = "NOTE_PROCEDURE_3";
    public static final String  NOTE_PROCEDURE_4       = "NOTE_PROCEDURE_4";

    public static final String  NOTE_ATTREZZATURE_1    = "NOTE_ATTREZZATURE_1";
    public static final String  NOTE_ATTREZZATURE_2    = "NOTE_ATTREZZATURE_2";

    public static final String  NOTE_DPI_1             = "NOTE_DPI_1";
    public static final String  NOTE_DPI_2             = "NOTE_DPI_2";
    public static final String  NOTE_DPI_3             = "NOTE_DPI_3";
    public static final String  NOTE_DPI_4             = "NOTE_DPI_4";
    public static final String  NOTE_DPI_5             = "NOTE_DPI_5";
    public static final String  NOTE_DPI_6             = "NOTE_DPI_6";
    public static final String  NOTE_DPI_7             = "NOTE_DPI_7";

    public static final String  NOTE_VALUTAZIONE_1     = "NOTE_VALUTAZIONE_1";
    public static final String  NOTE_VALUTAZIONE_2     = "NOTE_VALUTAZIONE_2";
    
    public static final String  TABELLA_PARENT        = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT      = "ID_MODULO_PARENT";

    public AliMod67DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AliMod67DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AliMod67DAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_MODULO_PARENT, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_MODULO))) {
            appendField(ID_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(NR_MODULO))) {
            appendField(NR_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(TABELLA_PARENT)) && Util.IsNotEmpty(getAttribute(ID_MODULO_PARENT))) {
            appendField(TABELLA_PARENT, whereCondition);
            appendField(ID_MODULO_PARENT, whereCondition);
        }
        
        return whereCondition;
    }

}
