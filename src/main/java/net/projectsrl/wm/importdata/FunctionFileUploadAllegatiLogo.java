
package net.projectsrl.wm.importdata;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.wm.utils.Utils;

public class FunctionFileUploadAllegatiLogo extends FunctionFileUploadDocumentiParametri_base {

    public FunctionFileUploadAllegatiLogo() {

        super();
    }

    public FunctionFileUploadAllegatiLogo(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        super.mostra(req, res, userInfo);
    }

    
    
    @Override
    protected String getPaginaChiusuraPopup() {

        return "file_upload_allegati_policy_chiudi";
    }

    protected void loadData(String sessionUser, String absolutePathFileName, SsbServletRequest req) throws AppCrash {

        String fileName = absolutePathFileName.substring(absolutePathFileName.lastIndexOf("\\") + 1);

        String idTaggancio = (String) req.getSession(false).getAttribute("ID_DOCAZIENDA_SESSIONE");
        String azienda = (String) req.getSession(false).getAttribute("DOCAZIENDA_SESSIONE");
        if (idTaggancio != null) {
            String inserisciNome = "INSERT INTO DOCAZIENDA_ATCH (ID_DOCAZIENDA,ID_DOCAZIENDA_ATCH,PERCORSO,FILENAME,AZIENDA,MATRICOLA,DATA_CARICAMENTO,ORA_CARICAMENTO) VALUES ('" + idTaggancio
                    + "','" + Utils.getUnique() + "','','"+fileName + "','"+azienda+"','L','"+Utils.getStringDataOggiRibaltata()+"','"+Utils.getOrario()+"')";
            net.projectsrl.wm.utils.WMUtils.executeQuery(inserisciNome);
        }
    }
    
    /**
     * aggiungere ID_POLICY in pagina
     * 
     * @param SsbServletRequest req
     * @param UserSecurityInfo userInfo
     * 
     * @return java.util.Hashtable
     */
    protected HashMap<String, Object> setCommonTags(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {
    
        HashMap<String, Object> templateData = super.setCommonTags(req, userInfo);
        
        String idTaggancio = (String) req.getSession(false).getAttribute(Costanti_itf.ID_DOCAZIENDA_SESSIONE);
        if (idTaggancio != null) {
            templateData.put("ID_DOCAZIENDA", idTaggancio);
        }
        
        return templateData;

    }    

}
