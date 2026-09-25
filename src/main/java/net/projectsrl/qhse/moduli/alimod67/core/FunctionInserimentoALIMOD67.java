
package net.projectsrl.qhse.moduli.alimod67.core;

import java.io.File;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.qhse.moduli.alimod67.db.AliMod67DAO;
import net.projectsrl.qhse.moduli.core.FunctionInserimentoALIMOD_base;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class FunctionInserimentoALIMOD67 extends FunctionInserimentoALIMOD_base<AliMod67DAO> {

    public FunctionInserimentoALIMOD67(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(AliMod67DAO.ID_MODULO, "");

            templateData.put(AliMod67DAO.ID_UTENTE_CON, getSpecificUserInfo(userInfo).getIdUtente());
            templateData.put(AliMod67DAO.DT_MODULO, project.misc.Utils.getStringDataOggi());
            templateData.put(AliMod67DAO.STATO, StatiRichiesta.DRAFT.getCode());

        } else {
            
            
            PjNDAO_base rowToUpdate = new AliMod67DAO();
            String idRow = req.getField(AliMod67DAO.ID_MODULO);
            
            String fileFirma1="Output/alimod67_firma1_"+idRow+".png";
            File firma1 = new File(_applicationSrv.getRoot()  +"/"+fileFirma1) ;
            if (firma1.exists()) {
                templateData.put( "FILE_FIRMA1",fileFirma1);
            }
            
            String fileFirma2="Output/alimod67_firma2_"+idRow+".png";
            File firma2 = new File(_applicationSrv.getRoot()  +"/"+fileFirma2) ;
            if (firma2.exists()) {
                templateData.put( "FILE_FIRMA2",fileFirma2);
            }
            
            String fileFirma3="Output/alimod67_firma3_"+idRow+".png";
            File firma3 = new File(_applicationSrv.getRoot() +"/"+fileFirma3) ;
            if (firma3.exists()) {
                templateData.put( "FILE_FIRMA3",fileFirma3);
            }
            
            rowToUpdate.setAttribute(AliMod67DAO.ID_MODULO, idRow);
            ErrDetector.GetInstance().preCond(rowToUpdate.retrieve(), AliMod67DAO.ID_MODULO + " not found");
            rowToUpdate.setMapFromAttributes(templateData);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }
    
    @Override
    protected void update(SsbServletRequest req, AliMod67DAO formDao, UserSecurityInfo userInfo) throws AppCrash {

        super.update(req, formDao, userInfo);
        String idModulo = req.getField(AliModDAO_base.ID_MODULO);
        creaFirma("alimod67_firma1_"+idModulo+".png",req.getField("FIRMA1"));
        
        creaFirma("alimod67_firma2_"+idModulo+".png",req.getField("FIRMA2"));
        
        creaFirma("alimod67_firma3_"+idModulo+".png",req.getField("FIRMA3"));
    }    
    
}
