package net.projectsrl.wm.anagraficaaziende.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;

/**
 * FunctionInserimentoSediAziendali
 * 
 */
public class FunctionInserimentoSediAziendali extends FunctionInserimento {

	private static final String PAGE = "inserimento_sediaziendali";
	

	public FunctionInserimentoSediAziendali() {
		super();
		setPageMostra(PAGE);
		setPageElabora(PAGE);
		setDatasetTestata("DataSetSediAziendali");
	}

	public FunctionInserimentoSediAziendali(ApplicationServices_itf applServices, String functionID, String functionName) {

		super(applServices, functionID, functionName);
		setPageMostra(PAGE);
		setPageElabora(PAGE);		
		setDatasetTestata("DataSetSediAziendali");
	}

	@SuppressWarnings("unchecked")
	protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		        
		String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

		if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
			templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
			templateData.put("CODICE_PARENT",(String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
			String progressivo = prossimoProgressivo();
			templateData.put("CODICE", progressivo);
			templateData.put("RAGSOC","Sede di");
			templateData.put("SEDE_LEGALE","S");
			templateData.put("SEDE_OPERATIVA","S");

			return templateData;
		}
		return templateData;

	}
	
    private String prossimoProgressivo() throws AppCrash {

        String progressivo = "000001";

        DataSet_itf dataSet = null;

        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DataSetUltimoIdSedeAziendale");
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
}

