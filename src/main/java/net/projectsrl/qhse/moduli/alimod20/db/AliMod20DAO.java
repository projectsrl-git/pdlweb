
package net.projectsrl.qhse.moduli.alimod20.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.alibow.db.AttributiAziendaDAO;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class AliMod20DAO extends AliModDAO_base {

    private static final String TABLE_NAME       = "ALIMOD20";

    public static final String  TITOLO           = "TITOLO";
    public static final String  TABELLA_PARENT   = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT = "ID_MODULO_PARENT";
    public static final String  FLG_AIFA         = "FLG_AIFA";

    public static final String  TS_INS           = "TS_INS";
    public static final String  ID_UTENTE_INS    = "ID_UTENTE_INS";
    public static final String  TS_DEL           = "TS_DEL";
    public static final String  ID_UTENTE_DEL    = "ID_UTENTE_DEL";

    public AliMod20DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AliMod20DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AliMod20DAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_MODULO_PARENT, Integer.class);
        addNoStringField(FLG_AIFA, Boolean.class);

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

    @Override
    public void insert() throws AppCrash {

        Integer idAzienda = (Integer) getAttribute(ID_AZIENDA);
        String sitoAifa = getFlagSitoAifa(idAzienda);
        setAttribute(FLG_AIFA, sitoAifa.equals("S"));
        
        super.insert();
    }

    private String getFlagSitoAifa(Integer idAzienda) throws AppCrash {

        AttributiAziendaDAO attrDAO = new AttributiAziendaDAO();
        attrDAO.setAttribute(AttributiAziendaDAO.ID_AZIENDA, idAzienda);
        attrDAO.setAttribute(AttributiAziendaDAO.CODICE_ATTRIBUTO, Constants_itf.CODICE_ATTRIBUTO_AZIENDA_SITO_AIFA);
        String sitoAifa = "N";
        try {
            attrDAO.retrieve();
            sitoAifa = (String) attrDAO.getAttribute(AttributiAziendaDAO.VALORE_ATTRIBUTO);
            if (sitoAifa == null) {
                sitoAifa = "N";
            }
        } catch (Throwable e) {
            // nulla da fare
			//
        }

        return sitoAifa;
    }

}
