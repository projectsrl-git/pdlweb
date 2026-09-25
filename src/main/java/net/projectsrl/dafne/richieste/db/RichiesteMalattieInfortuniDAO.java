
package net.projectsrl.dafne.richieste.db;

import java.sql.Timestamp;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.misc.Util;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.dafne.db.TableIdentity;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;

public class RichiesteMalattieInfortuniDAO extends PjNDAO_base {

    private static final String TABLE_NAME        = "RICHIESTE";

    private MalattieInfortuniDAO     _malattieInfortuni;

    public static final String  ID_RICHIESTA      = "ID_RICHIESTA";
    public static final String  ID_TIPO_RICHIESTA = "ID_TIPO_RICHIESTA";
    public static final String  ID_RISORSA        = "ID_RISORSA";
    public static final String  ID_AZIENDA        = "ID_AZIENDA";
    public static final String  ID_DIREZIONE      = "ID_DIREZIONE";
    public static final String  STATO_RICHIESTA   = "STATO_RICHIESTA";
    public static final String  FL_DEL            = "FL_DEL";
    public static final String  TS_INS            = "TS_INS";
    public static final String  ID_UTENTE_INS     = "ID_UTENTE_INS";
    public static final String  TS_MOD            = "TS_MOD";
    public static final String  ID_UTENTE_MOD     = "ID_UTENTE_MOD";
    public static final String  TS_DEL            = "TS_DEL";
    public static final String  ID_UTENTE_DEL     = "ID_UTENTE_DEL";

    public RichiesteMalattieInfortuniDAO() throws AppCrash {

        super(TABLE_NAME);
        _malattieInfortuni = new MalattieInfortuniDAO();
    }

    public RichiesteMalattieInfortuniDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty(DB_NAME_PROPERTY) + TABLE_NAME);
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

        if (Util.IsNotEmpty(getAttribute(ID_RICHIESTA))) {
            appendField(ID_RICHIESTA, whereCondition);
        }

        return whereCondition;
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_RICHIESTA, Integer.class);
        addNoStringField(ID_TIPO_RICHIESTA, Integer.class);
        addNoStringField(ID_RISORSA, Integer.class);
        addNoStringField(ID_DIREZIONE, Integer.class);
        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(FL_DEL, Boolean.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(TS_MOD, Timestamp.class);
        addNoStringField(ID_UTENTE_MOD, Integer.class);
        addNoStringField(TS_DEL, Timestamp.class);
        addNoStringField(ID_UTENTE_DEL, Integer.class);

    }

    @Override
    public void insert() throws AppCrash {

        Integer idUtenteIns = (Integer) getAttribute(ID_UTENTE_INS);
        Timestamp tsIns = new Timestamp(System.currentTimeMillis());
        setAttribute(TS_INS, tsIns);
        setAttribute(STATO_RICHIESTA, StatiRichiesta.WAITNG_FOR_FIRST_APPROVAL.getCode());

        super.insert();

        Integer rowId = new TableIdentity(TABLE_NAME).getTableIdentity();
        setAttribute(ID_RICHIESTA, rowId);

        _malattieInfortuni.setAttribute(ID_RICHIESTA, rowId);
        _malattieInfortuni.setAttribute(ID_UTENTE_INS, idUtenteIns);
        _malattieInfortuni.setAttribute(TS_INS, tsIns);
        _malattieInfortuni.insert();
    }

    @Override
    public void update() throws AppCrash {

        Integer idRichiesta = (Integer) getAttribute(ID_RICHIESTA);
        Integer idUtenteMod = (Integer) getAttribute(ID_UTENTE_MOD);
        Timestamp tsMod = new Timestamp(System.currentTimeMillis());
        setAttribute(TS_MOD, tsMod);

        super.update();

        _malattieInfortuni.setAttribute(ID_RICHIESTA, idRichiesta);
        _malattieInfortuni.setAttribute(ID_UTENTE_INS, idUtenteMod);
        _malattieInfortuni.setAttribute(TS_INS, tsMod);
        _malattieInfortuni.updateLastAsOldAndInsertNew();

    }

    @Override
    public void setAttributesFromRequest(SsbServletRequest req) throws AppCrash {

        super.setAttributesFromRequest(req);

        _malattieInfortuni.setAttributesFromRequest(req);

    }

}
