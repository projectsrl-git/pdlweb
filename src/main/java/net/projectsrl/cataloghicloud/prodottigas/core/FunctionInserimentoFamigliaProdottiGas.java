
package net.projectsrl.cataloghicloud.prodottigas.core;

import java.io.PrintWriter;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.cataloghicloud.anagrafiche.db.FamigliaProdottiGasDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.random.RandomIdentifier;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;


public class FunctionInserimentoFamigliaProdottiGas extends FunctionAjaxForm_base<FamigliaProdottiGasDAO> {

    public FunctionInserimentoFamigliaProdottiGas(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);
        
        if (isAnInsert(req)) {
            templateData.put(FamigliaProdottiGasDAO.ID_FAMIGLIA, "");

            // altri campi nel form
            String codiceFamiglia = "FAM_"+RandomIdentifier.GetInstance().getIdentifier(10);
            templateData.put(FamigliaProdottiGasDAO.CODICE_FAMIGLIA, codiceFamiglia);
        } else {
            PjNDAO_base rowToUpdate = new FamigliaProdottiGasDAO();
            String idRow = req.getField(FamigliaProdottiGasDAO.ID_FAMIGLIA);
            rowToUpdate.setAttribute(FamigliaProdottiGasDAO.ID_FAMIGLIA, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), FamigliaProdottiGasDAO.ID_FAMIGLIA + " not found");
            rowToUpdate.setMapFromAttributes(templateData);

            // altri campi nel form
            templateData.put("ID_PRODOTTI_GAS_NEXT", idRow);
            


        }        

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }

    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(FamigliaProdottiGasDAO.ID_FAMIGLIA));
    }

    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message, FamigliaProdottiGasDAO formDao) {

        try {
            PrintWriter out = res.getWriter();
            
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message + "'}";
            
            String codiceFamiglia=(String) formDao.getAttribute(FamigliaProdottiGasDAO.CODICE_FAMIGLIA);
            
            if (codiceFamiglia != null) {
                FamigliaProdottiGasDAO dao=new FamigliaProdottiGasDAO();
                dao.setAttribute(FamigliaProdottiGasDAO.CODICE_FAMIGLIA, codiceFamiglia);
                dao.retrieve();
                Integer idFamiglia=(Integer) dao.getAttribute(FamigliaProdottiGasDAO.ID_FAMIGLIA);
                resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message + "',\"idFamiglia\":" + idFamiglia + "}";
            }

            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }

}
