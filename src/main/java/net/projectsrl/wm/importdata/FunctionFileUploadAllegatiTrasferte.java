
package net.projectsrl.wm.importdata;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.wm.utils.Utils;

public class FunctionFileUploadAllegatiTrasferte extends FunctionFileUploadDocumenti_base {

    public FunctionFileUploadAllegatiTrasferte() {

        super();
    }

    public FunctionFileUploadAllegatiTrasferte(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        super.mostra(req, res, userInfo);
    }

    
    
    @Override
    protected String getPaginaChiusuraPopup() {

        return "file_upload_allegati_trasferte_chiudi";
    }

    protected void loadData(String sessionUser, String absolutePathFileName, SsbServletRequest req) throws AppCrash {

        String fileName = absolutePathFileName.substring(absolutePathFileName.lastIndexOf("\\") + 1);

        String idFeriePermesso = (String) req.getSession(false).getAttribute(Costanti_itf.ID_TRASFERTA_SESSIONE);
        if (idFeriePermesso != null) {
            String inserisciNome = "INSERT INTO TRASFERTE_ATCH (ID_TRASFERTA,ID_TRASFERTA_ATCH,PERCORSO,FILENAME,AZIENDA,MATRICOLA,DATA_CARICAMENTO,ORA_CARICAMENTO) VALUES ('" + idFeriePermesso
                    + "','" + Utils.getUnique() + "','" + (String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI")+"/"+(String) req.getSession(false).getAttribute("MATRICOLA")+"/','"+fileName + "','"+(String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI")+"','"+(String) req.getSession(false).getAttribute("MATRICOLA")+"','"+Utils.getStringDataOggiRibaltata()+"','"+Utils.getOrario()+"')";
            net.projectsrl.wm.utils.WMUtils.executeQuery(inserisciNome);
        }
    }
    
    /**
     * aggiungere ID_FERIEPERMESSO in pagina
     * 
     * @param SsbServletRequest req
     * @param UserSecurityInfo userInfo
     * 
     * @return java.util.Hashtable
     */
    protected HashMap<String, Object> setCommonTags(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {
    
        HashMap<String, Object> templateData = super.setCommonTags(req, userInfo);
        
        String idFeriePermesso = (String) req.getSession(false).getAttribute(Costanti_itf.ID_TRASFERTA_SESSIONE);
        if (idFeriePermesso != null) {
            templateData.put("ID_TRASFERTA_SESSIONE", idFeriePermesso);
        }
        
        return templateData;

    }    

}
