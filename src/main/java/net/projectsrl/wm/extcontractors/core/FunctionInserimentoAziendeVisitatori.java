package net.projectsrl.wm.extcontractors.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoAziendeVisitatori
 * 
 */
public class FunctionInserimentoAziendeVisitatori extends FunctionInserimento {

	private static final String PAGE = "inserimento_aziendevisitatori";
	

	public FunctionInserimentoAziendeVisitatori() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetAziendeVisitatori");
	}

	public FunctionInserimentoAziendeVisitatori(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetAziendeVisitatori");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("ID_AZIENDA",Utils.getUnique());
			templateData.put("CODICE_PARENT",(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
			String progressivo = prossimoProgressivo();
			templateData.put("CODICE", progressivo);

			return templateData;
		}
		return templateData;

	}
	
    private String prossimoProgressivo() throws AppCrash {

        String progressivo = "000001";

        DataSet_itf dataSet = null;

        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DataSetUltimoIdAziendeVisitatori");
            dataSet.open();

            if (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                String ultimoProgressivo = (String) dbRow.getField("ULTIMO");
                int ultimoProgressivoInt = 0;
                if (ultimoProgressivo != null && !ultimoProgressivo.equals("")) {
                    ultimoProgressivoInt = Integer.parseInt(ultimoProgressivo) + 1;
                    progressivo = String.format("%06d", ultimoProgressivoInt);
                }
            }

        } catch (AppCrash ac) {
            ac.logContext(this.getClass().getName(), "");
            throw ac;

        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "");
                    throw ac;
                }
            }
        }
        return progressivo;
    }
    
    
    @SuppressWarnings("unchecked")
   	protected HashMap saveVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
   		insertDettaglioAteco(req);
   		templateData.put("CODICE_ORIGINE",req.getField("CODICE"));
   		return templateData;
   	}
       
       private void insertDettaglioAteco( SsbServletRequest req) throws AppCrash {
       	String sqlDelete ="DELETE FROM AZIENDE_ATECO WHERE DAGGANCIO='"+req.getField("CODICE")+"' AND ATECO='"+req.getField("SETTORE")+"'";
   		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete);
   		
   		if(!req.getField("SETTORE").equals("")){
   			String sqlInsert ="INSERT INTO AZIENDE_ATECO (DAGGANCIO,ATECO) VALUES ('"+req.getField("CODICE")+"','"+req.getField("SETTORE")+"')";
   			net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsert);
   		}
       }
}

