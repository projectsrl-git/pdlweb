
package net.projectsrl.pdlweb.moduli.limod70.db;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.errors.ParamCrash;
import net.project.misc.Util;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.pdlweb.moduli.db.LiModDAO_base;
import net.projectsrl.webapp.core.WebAppUtils;

public class LiMod70DAO extends LiModDAO_base {
	
	private static final String DS_PROGRESSIVO_MODULO = "DSProgressivoLIMOD";
	private static final String PARAM_TABLE_NAME      = "TABLE_NAME";
    private static final String PARAM_FIELD_NAME      = "FIELD_NAME";
    private static final String PARAM_CONDITION      = "PARAM_CONDITION";
    
    private static final String TABLE_NAME      = "LIMOD70";

    public static final String  CODICE_TURNO = "CODICE_TURNO";
    public static final String  ID_IMPIANTO = "ID_IMPIANTO";
    public static final String  ID_AREA = "ID_AREA";
    public static final String  STATO = "STATO";
    public static final String  NOME_FILE = "NOME_FILE";
    public static final String  ORA = "ORA";
    
    public static final String  TABELLA_PARENT   = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT = "ID_MODULO_PARENT";
    
    public static final String  VALUTAZIONE     = "VALUTAZIONE";
    public static final String  DISTANZA_RISPETTO            = "DISTANZA_RISPETTO";

    public LiMod70DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public LiMod70DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public LiMod70DAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

     
        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(ID_IMPIANTO, Integer.class);
        addNoStringField(ID_AREA, Integer.class);
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

    @Override
    public void insert() throws AppCrash {
        
    	cancellaBozzeStessaArea();
    	
    	String nrModulo = getNextSequentialNumber();
        setAttribute(NR_MODULO, nrModulo);
        
        super.insert();
        super.retrieve();
    }

	private void cancellaBozzeStessaArea() throws AppCrash, ParamCrash {
		Integer idArea=(Integer) getAttribute(ID_AREA);
    	String sqlStatement2 = "delete from limod70 where stato='DRA' and id_area="+idArea;
        ErrDetector.GetInstance().param(sqlStatement2 != null, "sqlStatement null");
        WebAppUtils.executeQuery(sqlStatement2);
	}
    
    
    @Override
    public void update() throws AppCrash {
        
        Integer idModulo=(Integer) getAttribute(ID_MODULO);
        LiMod70DAO daoOld=new LiMod70DAO();
        daoOld.setAttribute(ID_MODULO, idModulo);
        daoOld.retrieve();
        String nrModuloOld=(String) daoOld.getAttribute(NR_MODULO);
        String dtModuloOld=(String) daoOld.getAttribute(DT_MODULO);
        String statoOld=(String) daoOld.getAttribute(STATO);
        String oraOld=(String) daoOld.getAttribute(ORA);
                
        if (Util.IsEmpty((String) getAttribute(NR_MODULO))) {
            setAttribute(NR_MODULO, nrModuloOld);
        }
        
        if (Util.IsEmpty((String) getAttribute(DT_MODULO))) {
            setAttribute(DT_MODULO, dtModuloOld);
        }
        
        if (Util.IsEmpty((String) getAttribute(STATO))) {
            setAttribute(STATO, statoOld);
        }
        
        if (Util.IsEmpty((String) getAttribute(ORA))) {
            setAttribute(ORA, oraOld);
        }
        
        setAttribute(STATO, StatiRichiesta.APPROVED.getCode());
        
        super.update();
        
        
    }
    
   
    
    private String getNextSequentialNumber() throws AppCrash {

        String dsName = DS_PROGRESSIVO_MODULO;

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();
        Integer lastSequentialNumeber = new Integer(0);

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> param = new HashMap<String, String>();
            param.put(PARAM_TABLE_NAME, getTableName());
            param.put(PARAM_FIELD_NAME, NR_MODULO);
            if (getTableName().contains("_DETTAGLI")){
            	param.put(PARAM_CONDITION, "");
            }else{
            	param.put(PARAM_CONDITION, " WHERE STATO='APP'");
            }
            dataSet.setParam(param);
            dataSet.open();

            if (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if (dbRow != null) {
                    String strLast = (String) dbRow.getField("ultimo");
                    if (strLast != null && !strLast.trim().equals("")) {
                        lastSequentialNumeber = Integer.valueOf(strLast);
                        lastSequentialNumeber += 1;
                    }
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

        if (lastSequentialNumeber == null || lastSequentialNumeber.intValue() == 0) {
            lastSequentialNumeber = new Integer(1);
        }

        return ""+lastSequentialNumeber;

    }

}
