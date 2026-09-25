
package net.projectsrl.bow.parameters;

import java.util.Map;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.DeleteQueryRows;
import net.projectsrl.webapp.core.InsertMultipleReferences;
import project.misc.Utils;

public class ParametriDAO extends PjNDAO_base {

    private static final String TABLE_NAME           = "PARAMETRI";
    private String              _listaAziende        = null;
    private static final String ID_AZIENDA           = "ID_AZIENDA";
    private static final String DS_PARAMETRI_AZIENDA = "DSParametriAzienda";

    public static final String  ID_PARAMETRO         = "ID_PARAMETRO";
    public static final String  DOMINIO              = "DOMINIO";
    public static final String  CODICE               = "CODICE";
    public static final String  DESCRIZIONE          = "DESCRIZIONE";
    public static final String  FL_DISATTIVO         = "FL_DISATTIVO";

    private class DeleteParametriAzienda extends DeleteQueryRows<ParametriAziendaDAO, Integer> {

        public DeleteParametriAzienda() {

            super(DS_PARAMETRI_AZIENDA, ParametriAziendaDAO.ID_PARAMETRO, ParametriAziendaDAO.ID_PARAMETRO_AZIENDA);

        }

    }

    private class InsertParametriAzienda extends InsertMultipleReferences<ParametriAziendaDAO> {

        public InsertParametriAzienda() {

            super(DS_PARAMETRI_AZIENDA, ID_PARAMETRO, ID_AZIENDA);
        }

    }

    public ParametriDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public ParametriDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public ParametriDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_PARAMETRO, Integer.class);
        addNoStringField(FL_DISATTIVO, Boolean.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_PARAMETRO))) {
            appendField(ID_PARAMETRO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(DOMINIO)) && Util.IsNotEmpty(getAttribute(CODICE))) {
            appendField(DOMINIO, whereCondition);
            appendField(CODICE, whereCondition);
        }

        return whereCondition;
    }

    @Override
    public void setAttributesFromRequest(SsbServletRequest req) throws AppCrash {

        super.setAttributesFromRequest(req);
        
        String listaAziende = req.getField("AZIENDE_MULTIPLE");
        if (Utils.IsNotEmpty(listaAziende)) {
            _listaAziende = listaAziende;
        }

    }

    @Override
    public void insert() throws AppCrash {

        super.insert();

        updateParametriAzienda();

    }
    
    @Override
    public void update() throws AppCrash {

        super.update();

        updateParametriAzienda();

    }

    private void updateParametriAzienda() throws AppCrash {

        String dominio=(String) getAttribute(DOMINIO);
        if (Util.IsEmpty(dominio) || !dominio.equals("DOM")) {
            return;
        }

        Integer idParametro = getPkValue();

        new DeleteParametriAzienda().delete(idParametro.toString());

        if (Utils.IsNotEmpty(_listaAziende)) {
            new InsertParametriAzienda().insert(_listaAziende, idParametro);
        }
    }    

    @Override
    public void setMapFromAttributes(Map<String, Object> map) throws AppCrash {

        Integer idParametro = getPkValue();
        
        super.setMapFromAttributes(map);
        
        map.put("AZIENDE_MULTIPLE",new InsertParametriAzienda().getSelectedCodeList(idParametro.toString()));
    }
    

    private Integer getPkValue() throws AppCrash {

        Integer idParametro = (Integer) getAttribute(ID_PARAMETRO);
        if (idParametro == null) {
            retrieve();
            idParametro = (Integer) getAttribute(ID_PARAMETRO);
        }
        return idParametro;
    }
    

}
