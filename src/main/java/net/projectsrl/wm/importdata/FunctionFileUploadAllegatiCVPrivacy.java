
package net.projectsrl.wm.importdata;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.wm.utils.Utils;

public class FunctionFileUploadAllegatiCVPrivacy extends FunctionFileUploadDocumentiCV_base {

    public FunctionFileUploadAllegatiCVPrivacy() {

        super();
    }

    public FunctionFileUploadAllegatiCVPrivacy(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        super.mostra(req, res, userInfo);
    }

    
    
    @Override
    protected String getPaginaChiusuraPopup() {

        return "file_upload_allegati_cv_chiudi";
    }

    protected void loadData(String sessionUser, String absolutePathFileName, SsbServletRequest req) throws AppCrash {

        String fileName = absolutePathFileName.substring(absolutePathFileName.lastIndexOf("\\") + 1);

        String idTaggancio = (String) req.getSession(false).getAttribute(Costanti_itf.ID_CURRICULUM_SESSIONE);
        if (idTaggancio != null) {
            String inserisciNome = "INSERT INTO CURRICULUM_ATCH_PRIVACY (DAGGANCIO,DAGGANCIO_ATCH,PERCORSO,FILENAME,AZIENDA,MATRICOLA,DATA_CARICAMENTO,ORA_CARICAMENTO) VALUES ('" + idTaggancio
                    + "','" + Utils.getUnique() + "','','"+fileName + "','','P','"+Utils.getStringDataOggiRibaltata()+"','"+Utils.getOrario()+"')";
            net.projectsrl.wm.utils.WMUtils.executeQuery(inserisciNome);
        }
    }
    
    /**
     * aggiungere ID_MALATTIAINFORTUNIO in pagina
     * 
     * @param SsbServletRequest req
     * @param UserSecurityInfo userInfo
     * 
     * @return java.util.Hashtable
     */
    protected HashMap<String, Object> setCommonTags(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {
    
        HashMap<String, Object> templateData = super.setCommonTags(req, userInfo);
        
        String idTaggancio = (String) req.getSession(false).getAttribute(Costanti_itf.ID_CURRICULUM_SESSIONE);
        if (idTaggancio != null) {
            templateData.put("TAGGANCIO", idTaggancio);
        }
        
        templateData.put("TABS", "Activate12");
        templateData.put("CARTELLA_INIZIALE", "tabs-12");
        
        return templateData;

    }    

}
