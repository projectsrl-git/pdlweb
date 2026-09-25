
package net.projectsrl.qhse.core;

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
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.wm.utils.Utils;

public class FunctionHome extends net.projectsrl.bow.core.FunctionHome {

    public FunctionHome(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }
    
    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        setCounters(templateData,userInfo);

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }    
    
    private String setCounters(Map<String, Object> templateData, UserSecurityInfo userInfo) throws AppCrash {
        templateData.put("ALIMOD20", 0);
        templateData.put("ALIMOD50", 0);
        templateData.put("ALIMOD67", 0);
        templateData.put("ALIMOD80", 0);
        templateData.put("ANNO", Utils.getAnnoOggi());

        String dsName = "DSAliRepContatori";

        DataSet_itf dataSet = null;

        DataSetFactory dsFactory = DataSetFactory.getInstance();
        Integer lastSequentialNumeber = new Integer(0);

        try {
            dataSet = dsFactory.makeDataSet("", dsName);

            HashMap<String, String> param = new HashMap<String, String>();
            param.put("WHERECONDITION"," WHERE DT_MODULO >= '"+Utils.getAnnoOggi()+"/01/01' ");
            param.put(DafneCostanti_itf.WHERECONDITION_AZIENDE,getSpecificUserInfo(userInfo).getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));
            dataSet.setParam(param);
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                if (dbRow != null) {
                    templateData.put((String) dbRow.getField("MODULO"), dbRow.getField("TOT_MODULI"));
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

        return "" + lastSequentialNumeber;

    }
}
