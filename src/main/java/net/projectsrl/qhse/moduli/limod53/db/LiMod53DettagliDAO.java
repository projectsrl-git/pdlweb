
package net.projectsrl.qhse.moduli.limod53.db;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.qhse.moduli.db.AliModDettagliDAO_base;
import net.projectsrl.webapp.core.WebAppConstants_itf;

public class LiMod53DettagliDAO extends AliModDettagliDAO_base {

    private static final String TABLE_PARENT_NAME = "LIMOD53";

    //private static final String AZIONE_CORRETTIVA   = "COR";

    private static final String TABLE_NAME          = "LIMOD53_DETTAGLI";

    //public static final String  TIPO_DETTAGLIO = "TIPO_DETTAGLIO";
    
    public static final String  SITUAZIONE = "SITUAZIONE";
    public static final String  AZIONE = "AZIONE";
    public static final String  A_CURA_DI = "A_CURA_DI";
    public static final String  ENTRO_IL = "ENTRO_IL";
    public static final String  TABELLA_PARENT         = "TABELLA_PARENT";
    public static final String  ID_DETTAGLIO_PARENT    = "ID_DETTAGLIO_PARENT";
    public static final String  STATO_AZIONE    = "STATO_AZIONE";
    

    public LiMod53DettagliDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public LiMod53DettagliDAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public LiMod53DettagliDAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_DETTAGLIO_PARENT, Integer.class);
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
        LiMod53DAO dao = new LiMod53DAO();
        dao.setAttribute(LiMod53DAO.ID_MODULO, idModulo);
        dao.setAttribute(LiMod53DAO.STATO, "CLO");
        dao.update();
    }

    private boolean isActionPlanClosed() throws AppCrash {

        Integer idModulo = (Integer) getAttribute(ID_MODULO);
        String dsName = "DSLIMOD53Dettaglio";
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

        /*AliMod52DAO aliMod52DAO = new AliMod52DAO();
        aliMod52DAO.setAttribute(ID_MODULO, getAttribute(ID_MODULO));
        aliMod52DAO.retrieve();

        AliMod52DettagliDAO aliMod51DettagliDAO = new AliMod52DettagliDAO();
        aliMod51DettagliDAO.setAttribute(ID_DETTAGLIO, getAttribute(ID_DETTAGLIO));
        aliMod51DettagliDAO.retrieve();

        AliMod20DAO aliMod20DAO = new AliMod20DAO();
        aliMod20DAO.setAttribute(AliMod20DAO.TABELLA_PARENT, TABLE_PARENT_NAME);
        aliMod20DAO.setAttribute(AliMod20DAO.ID_MODULO_PARENT, aliMod52DAO.getAttribute(AliModDAO_base.ID_MODULO));
        if (!aliMod20DAO.retrieve()) {
            aliMod20DAO.setAttribute(AliModDAO_base.ID_AZIENDA, aliMod52DAO.getAttribute(AliModDAO_base.ID_AZIENDA));
            aliMod20DAO.setAttribute(AliModDAO_base.CODICE_BL, aliMod52DAO.getAttribute(AliModDAO_base.CODICE_BL));
            aliMod20DAO.setAttribute(AliModDAO_base.ID_UTENTE_INS, aliMod52DAO.getAttribute(AliModDAO_base.ID_UTENTE_INS));
            aliMod20DAO.setAttribute(AliModDAO_base.ID_UTENTE_CON, aliMod52DAO.getAttribute(AliModDAO_base.ID_UTENTE_CON));
            aliMod20DAO.setAttribute(AliMod20DAO.TITOLO,
                    "PdA da Visita Comportamentale di Sicurezza #" + aliMod52DAO.getAttribute(AliModDAO_base.NR_MODULO)
                            + " del " + aliMod52DAO.getAttribute(AliModDAO_base.DT_MODULO));
            aliMod20DAO.setAttribute(AliModDAO_base.DT_MODULO, Utils.getStringDataOggiRibaltata());
            aliMod20DAO.setAttribute(AliModDAO_base.STATO, StatiRichiesta.WAITNG_FOR_FIRST_APPROVAL.getCode());

            aliMod20DAO.insert();
        }

        AliMod20DettagliDAO aliMod20DettagliDAO = new AliMod20DettagliDAO();
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_MODULO, aliMod20DAO.getAttribute(ID_MODULO));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.DESCRIZIONE, "");
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.RIFERIMENTO_ORIGINE,
                "Visita Comportamentale di Sicurezza #" + aliMod52DAO.getAttribute(AliMod52DAO.NR_MODULO) + " del "
                        + aliMod52DAO.getAttribute(AliMod52DAO.DT_MODULO));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.CODICE_TIPO_AZIONE, "");
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.DT_SCADENZA, Utils.getStringDataOggiRibaltata());
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.CODICE_CLASSIFICAZIONE, "SEC");
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.STATO_AZIONE, "OPE");
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_UTENTE_RES,
                aliMod52DAO.getAttribute(AliMod52DAO.ID_UTENTE_CON));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_UTENTE_INS,
                aliMod51DettagliDAO.getAttribute(AliMod52DAO.ID_UTENTE_INS));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.ID_DETTAGLIO_PARENT,
                aliMod51DettagliDAO.getAttribute(ID_DETTAGLIO));
        aliMod20DettagliDAO.setAttribute(AliMod20DettagliDAO.TABELLA_PARENT, TABLE_NAME);
        aliMod20DettagliDAO.insert();*/
    }
    @Override
    public void update() throws AppCrash {
        
        Integer idDettaglio=(Integer) getAttribute(ID_DETTAGLIO);
        LiMod53DettagliDAO daoOld=new LiMod53DettagliDAO();
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
