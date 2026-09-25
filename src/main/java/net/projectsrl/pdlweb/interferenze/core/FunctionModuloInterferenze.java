
package net.projectsrl.pdlweb.interferenze.core;

import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.pdlweb.pdl.core.PdlWebUtils;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;
import net.projectsrl.wm.utils.Utils;

public class FunctionModuloInterferenze extends FunctionProjectWebApp_base {
	
	private static final String DATASET_INTERFERENZE = "DSInterferenzePDL";

    public FunctionModuloInterferenze(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

    	PdlWebUtils.updatePdlStatus();
    	
    	//System.gc();
    	
        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        String idImpianto = (String) templateData.get("ID_IMPIANTO");
        String turnoSelezionato = (String) req.getSession(false).getAttribute("CODICE_TURNO_SELEZIONATO");
        if (turnoSelezionato==null){
        	turnoSelezionato="";
        }

        if (idImpianto != null && Utils.IsNotEmpty(idImpianto)) {

            CreaMappaImpiantoInterferenze creaMappa = new CreaMappaImpiantoInterferenze(new Integer(idImpianto));
            String fileNamePlanimetria= creaMappa.creaMappaArea(_applicationSrv.getRoot(),turnoSelezionato);
            templateData.put("IMMAGINE", fileNamePlanimetria);
            


            
            
            
            String flTuttoImpianto="";
            
            DataSet_itf dataSet = null;
    		try {
    			dataSet = DataSetFactory.getInstance().makeDataSet("", DATASET_INTERFERENZE);
    			HashMap<String, String> params = new HashMap<String, String>();
    			params.put("ID_IMPIANTO", idImpianto);
    			dataSet.setParam(params);
    			dataSet.open();

    			while (dataSet.hasMoreElements()) {
    				Row_itf dbRow = (Row_itf) dataSet.nextElement();
    				
    				if ((Boolean) dbRow.getField("FL_TUTTO_IMPIANTO")){
    					flTuttoImpianto="S";
    				}
    			}

    		} catch (AppCrash ac) {
    			ac.logContext(this.getClass().getName(),
    					"Errore nella ricerca dell'ultimo progressivo del dataset " + "DSInterferenzePDL");
    			try {
    				throw ac;
    			} catch (AppCrash e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		} finally {
    			// chiude il dataset per il conteggio degli elementi trovati
    			if (dataSet != null) {
    				try {
    					dataSet.close();
    				} catch (Throwable t) {
    					AppCrash ac = new AppCrash(t);
    					ac.logContext(this.getClass().getName(), "Errore nella close del dataset " + "DSInterferenzePDL");
    				}
    			}
    		}
    		
    		templateData.put("FL_TUTTO_IMPIANTO", flTuttoImpianto);
            
            
            
            
            
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

}
