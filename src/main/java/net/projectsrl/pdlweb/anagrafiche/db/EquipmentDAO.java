
package net.projectsrl.pdlweb.anagrafiche.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class EquipmentDAO extends PjNDAO_base {

	
    private static final String TABLE_NAME      = "EQUIPMENT";

    public static final String  ID_EQUIPMENT    = "ID_EQUIPMENT";
    public static final String  ID_AREA         = "ID_AREA";
    public static final String  DESCR_EQUIPMENT = "DESCR_EQUIPMENT";
    public static final String  FL_EIS          = "FL_EIS";
    public static final String  NOTE            = "NOTE";
    public static final String  FL_DISATTIVO    = "FL_DISATTIVO";

    public EquipmentDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public EquipmentDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public EquipmentDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_EQUIPMENT, Integer.class);
        addNoStringField(ID_AREA, Integer.class);
        addNoStringField(FL_EIS, Boolean.class);
        addNoStringField(FL_DISATTIVO, Boolean.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_EQUIPMENT))) {
            appendField(ID_EQUIPMENT, whereCondition);
        }

        return whereCondition;
    }
    
    @Override
	public void delete() throws AppCrash {
    	setAttribute(EquipmentDAO.FL_DISATTIVO, true);
    	update();
    }

}
