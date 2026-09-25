
package net.projectsrl.qhse.moduli.alimod05.db;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.qhse.moduli.alimod20.db.AliMod20DAO;
import net.projectsrl.qhse.moduli.alimod20.db.AliMod20DettagliDAO;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;
import net.projectsrl.qhse.moduli.db.AliModDettagliDAO_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.wm.utils.Utils;

public class AliMod5DettagliDAO extends AliModDettagliDAO_base {

    private static final String TABLE_PARENT_NAME = "ALIMOD05";

    private static final String AZIONE_CORRETTIVA   = "COR";

    private static final String TABLE_NAME          = "ALIMOD05_DETTAGLI";

    public static final String  CODICE_TIPO_AZIONE = "CODICE_TIPO_AZIONE";
    public static final String  DESCRIZIONE = "DESCRIZIONE";
    public static final String  DT_PREVISTA = "DT_PREVISTA";
    public static final String  DT_VERIFICA = "DT_VERIFICA";
    public static final String  ID_UTENTE_RES_1 = "ID_UTENTE_RES_1";
    public static final String  ID_UTENTE_RES_2 = "ID_UTENTE_RES_2";
    public static final String  FLG_AZIONE = "FLG_AZIONE";
    public static final String  DT_VERIFICA_EFFICACIA = "DT_VERIFICA_EFFICACIA";
    public static final String  ID_UTENTE_RES_3 = "ID_UTENTE_RES_3";
    public static final String  STATO_AZIONE           = "STATO_AZIONE";
    public static final String  TABELLA_PARENT         = "TABELLA_PARENT";
    public static final String  ID_DETTAGLIO_PARENT    = "ID_DETTAGLIO_PARENT";
    public static final String  ORIGINE_AP = "ORIGINE_AP";
    public static final String  ATTESTAZIONE = "ATTESTAZIONE";
    public static final String  QUALITY_ASSURANCE = "QUALITY_ASSURANCE";

    public AliMod5DettagliDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public AliMod5DettagliDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public AliMod5DettagliDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    
    @Override
    protected void init() throws AppCrash {

        super.init();
        addNoStringField(ID_DETTAGLIO_PARENT, Integer.class);
        
        addNoStringField(ID_UTENTE_RES_1, Integer.class);
        addNoStringField(ID_UTENTE_RES_2, Integer.class);
        addNoStringField(ID_UTENTE_RES_3, Integer.class);
        addNoStringField(FLG_AZIONE, Boolean.class);
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
        setAttribute(STATO_AZIONE, "OPE");
        super.insert();

        AliMod5DAO aliMod05DAO = new AliMod5DAO();
        aliMod05DAO.setAttribute(ID_MODULO, getAttribute(ID_MODULO));
        aliMod05DAO.retrieve();

        AliMod5DettagliDAO aliMod50DettagliDAO = new AliMod5DettagliDAO();
        aliMod50DettagliDAO.setAttribute(ID_DETTAGLIO, getAttribute(ID_DETTAGLIO));
        aliMod50DettagliDAO.retrieve();

        AliMod20DAO aliMod20DAO = new AliMod20DAO();
        aliMod20DAO.setAttribute(AliMod20DAO.TABELLA_PARENT, TABLE_PARENT_NAME);
        aliMod20DAO.setAttribute(AliMod20DAO.ID_MODULO_PARENT, aliMod05DAO.getAttribute(AliModDAO_base.ID_MODULO));
        if (!aliMod20DAO.retrieve()) {
            aliMod20DAO.setAttribute(AliModDAO_base.ID_AZIENDA, aliMod05DAO.getAttribute(AliModDAO_base.ID_AZIENDA));
            aliMod20DAO.setAttribute(AliModDAO_base.CODICE_BL, aliMod05DAO.getAttribute(AliModDAO_base.CODICE_BL));
            aliMod20DAO.setAttribute(AliModDAO_base.ID_UTENTE_INS, aliMod05DAO.getAttribute(AliModDAO_base.ID_UTENTE_INS));
            aliMod20DAO.setAttribute(AliModDAO_base.ID_UTENTE_CON, aliMod05DAO.getAttribute(AliModDAO_base.ID_UTENTE_CON));
            aliMod20DAO.setAttribute(AliMod20DAO.TITOLO,
                    "PdA da Visita Comportamentale di Sicurezza #" + aliMod05DAO.getAttribute(AliModDAO_base.NR_MODULO)
                            + " del " + aliMod05DAO.getAttribute(AliModDAO_base.DT_MODULO));
            aliMod20DAO.setAttribute(AliModDAO_base.DT_MODULO, Utils.getStringDataOggiRibaltata());
            aliMod20DAO.setAttribute(AliModDAO_base.STATO, StatiRichiesta.WAITNG_FOR_FIRST_APPROVAL.getCode());

            aliMod20DAO.insert();
        }

        AliMod20DettagliDAO aliMod20DettagliDAO = new AliMod20DettagliDAO();
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_MODULO, aliMod20DAO.getAttribute(ID_MODULO));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.DESCRIZIONE, "");
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.RIFERIMENTO_ORIGINE,
                "Visita Comportamentale di Sicurezza #" + aliMod05DAO.getAttribute(AliMod5DAO.NR_MODULO) + " del "
                        + aliMod05DAO.getAttribute(AliMod5DAO.DT_MODULO));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.CODICE_TIPO_AZIONE, AZIONE_CORRETTIVA);
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.DT_SCADENZA, Utils.getStringDataOggiRibaltata());
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.CODICE_CLASSIFICAZIONE, "SEC");
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.STATO_AZIONE, "OPE");
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_UTENTE_RES,
                aliMod05DAO.getAttribute(AliMod5DAO.ID_UTENTE_CON));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_UTENTE_INS,
                aliMod50DettagliDAO.getAttribute(AliMod5DAO.ID_UTENTE_INS));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_DETTAGLIO_PARENT,
                aliMod50DettagliDAO.getAttribute(ID_DETTAGLIO));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.TABELLA_PARENT, TABLE_NAME);
        aliMod20DettagliDAO.insert();
    }

    @Override
    public void update() throws AppCrash {
        
        Integer idDettaglio=(Integer) getAttribute(ID_DETTAGLIO);
        AliMod5DettagliDAO daoOld=new AliMod5DettagliDAO();
        daoOld.setAttribute(ID_DETTAGLIO, idDettaglio);
        daoOld.retrieve();
        String statoAzioneOld=(String) daoOld.getAttribute(STATO_AZIONE);
        String nr=(String) daoOld.getAttribute(NR_DETTAGLIO);
                
        if (Util.IsEmpty((String) getAttribute(STATO_AZIONE))) {
            setAttribute(STATO_AZIONE, statoAzioneOld);
        }
        
        if (Util.IsEmpty((String) getAttribute(NR_DETTAGLIO))) {
            setAttribute(NR_DETTAGLIO, nr);
        }
        
        super.update();
        
        if (isActionPlanClosed()) {
            closeActionPlan();   
        }
}
}
