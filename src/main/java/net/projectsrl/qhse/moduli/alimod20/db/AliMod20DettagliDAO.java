
package net.projectsrl.qhse.moduli.alimod20.db;

import java.sql.Timestamp;
import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.projectsrl.alibow.core.Constants_itf;
import net.projectsrl.bow.parameters.ParametriDAO;
import net.projectsrl.dafne.db.UtentiDAO;
import net.projectsrl.qhse.moduli.db.AliModDettagliDAO_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.wm.utils.Utils;

public class AliMod20DettagliDAO extends AliModDettagliDAO_base {

    private static final String TABLE_NAME             = "ALIMOD20_DETTAGLI";

    public static final String  CODICE_TIPO_AZIONE     = "CODICE_TIPO_AZIONE";
    public static final String  RIFERIMENTO_ORIGINE    = "RIFERIMENTO_ORIGINE";
    public static final String  DESCRIZIONE            = "DESCRIZIONE";
    public static final String  ID_UTENTE_RES          = "ID_UTENTE_RES";
    public static final String  ASSEGNATARIO           = "ASSEGNATARIO";
    public static final String  ALTRO_EMAIL            = "ALTRO_EMAIL";
    public static final String  DT_SCADENZA            = "DT_SCADENZA";
    public static final String  DT_CHIUSURA            = "DT_CHIUSURA";
    public static final String  COSTO_AZIONE           = "COSTO_AZIONE";
    public static final String  EFFORT                 = "EFFORT";
    public static final String  STATO_AZIONE           = "STATO_AZIONE";
    public static final String  CODICE_CLASSIFICAZIONE = "CODICE_CLASSIFICAZIONE";
    public static final String  TABELLA_PARENT         = "TABELLA_PARENT";
    public static final String  ID_DETTAGLIO_PARENT    = "ID_DETTAGLIO_PARENT";
    public static final String  NOTE_AVANZAMENTO       = "NOTE_AVANZAMENTO";
    public static final String  AVANZAMENTO            = "AVANZAMENTO";
    public static final String  TS_MOD                 = "TS_MOD";
    public static final String  ID_UTENTE_MOD          = "ID_UTENTE_MOD";

    public AliMod20DettagliDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AliMod20DettagliDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AliMod20DettagliDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_DETTAGLIO_PARENT, Integer.class);
        addNoStringField(ID_UTENTE_RES, Integer.class);
        addNoStringField(COSTO_AZIONE, Float.class);
        addNoStringField(EFFORT, Integer.class);

        addNoStringField(TS_MOD, Timestamp.class);
        addNoStringField(ID_UTENTE_MOD, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_DETTAGLIO))) {
            appendField(ID_DETTAGLIO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(NR_DETTAGLIO))) {
            appendField(NR_DETTAGLIO, whereCondition);
        }

        return whereCondition;
    }

    private void closeActionPlan() throws AppCrash {

        Integer idModulo = (Integer) getAttribute(ID_MODULO);
        AliMod20DAO dao = new AliMod20DAO();
        dao.setAttribute(AliMod20DAO.ID_MODULO, idModulo);
        dao.setAttribute(AliMod20DAO.STATO, "CLO");
        dao.update();
    }

    private boolean isActionPlanClosed() throws AppCrash {

        Integer idModulo = (Integer) getAttribute(ID_MODULO);
        String dsName = "DSALIMOD20Dettaglio";
        boolean isActionPlanClosed = true;

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> param = new HashMap<String, String>();
            param.put(WebAppConstants_itf.WHERECONDITION, "WHERE ID_MODULO=" + idModulo);
            dataSet.setParam(param);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if (!dbRow.getField(STATO_AZIONE).equals("CHK")) {
                    isActionPlanClosed = false;
                }
            }
        } catch (AppCrash ac) {
            ac.logContext(this.getClass().getName(),
                    "Errore nella ricerca dell'ultimo progressivo del dataset " + dsName);
            throw ac;
        } finally {
            // chiude il dataset per il conteggio degli elementi trovati
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (Throwable t) {
                    AppCrash ac = new AppCrash(t);
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset " + dsName);
                }
            }
        }

        return isActionPlanClosed;

    }

    @Override
    public void insert() throws AppCrash {

        setAttribute(STATO_AZIONE, Constants_itf.STATO_AZIONE_APERTA);

        String avanzamento = getNewAvanzamento(Utils.getStringDataOggi(), (Integer) getAttribute(ID_UTENTE_INS),
                Constants_itf.STATO_AZIONE_APERTA);

        setAttribute(AVANZAMENTO, avanzamento);

        super.insert();
    }

    private String getNewAvanzamento(String today, Integer idUtente, String codiceStatoAzione) throws AppCrash {

        ErrDetector.GetInstance().param(Utils.IsNotEmpty(today), "today is null");
        ErrDetector.GetInstance().param(idUtente != null, "idUtente is null");
        ErrDetector.GetInstance().param(Utils.IsNotEmpty(codiceStatoAzione), "codiceStatoAzione is null");

        UtentiDAO utente = new UtentiDAO();
        utente.setAttribute(UtentiDAO.ID_UTENTE, idUtente);
        utente.retrieve();

        String nomeUtente = (String) utente.getAttribute(UtentiDAO.NOME) + " "
                + (String) utente.getAttribute(UtentiDAO.COGNOME);

        ParametriDAO parametriDAO = new ParametriDAO();
        parametriDAO.setAttribute(ParametriDAO.CODICE, codiceStatoAzione);
        parametriDAO.setAttribute(ParametriDAO.DOMINIO, Constants_itf.DOMINIO_PARAMETRO_STATO_AZIONE);
        parametriDAO.retrieve();

        String stato = (String) parametriDAO.getAttribute(ParametriDAO.DESCRIZIONE);

        return  today + " - " + nomeUtente + " - " + stato;

    }

    @Override
    public void update() throws AppCrash {

        Integer idDettaglio = (Integer) getAttribute(ID_DETTAGLIO);
        AliMod20DettagliDAO daoOld = new AliMod20DettagliDAO();
        daoOld.setAttribute(ID_DETTAGLIO, idDettaglio);
        daoOld.retrieve();
        String statoAzioneOld = (String) daoOld.getAttribute(STATO_AZIONE);
        String nr = (String) daoOld.getAttribute(NR_DETTAGLIO);

        String statoAzioneNew = (String) getAttribute(STATO_AZIONE);
        
        if (Util.IsEmpty(statoAzioneNew)) {
            statoAzioneNew=statoAzioneOld;
            setAttribute(STATO_AZIONE, statoAzioneOld);
        }

        if (Util.IsEmpty((String) getAttribute(NR_DETTAGLIO))) {
            setAttribute(NR_DETTAGLIO, nr);
        }

        super.update();
        
        if (statoAzioneNew!=null && !statoAzioneNew.equals(statoAzioneOld)) {
            String avanzamento = (String) super.getAttribute(AVANZAMENTO);
            String newAvanzamento=getNewAvanzamento(Utils.getStringDataOggi(), (Integer) getAttribute(ID_UTENTE_MOD),
                    statoAzioneNew);
            setAttribute(AVANZAMENTO, avanzamento+(Util.IsNotEmpty(avanzamento)?"; ":"")+newAvanzamento);
            
            super.update();

        }

        if (isActionPlanClosed()) {
            closeActionPlan();
        }
        


    }

}
