
package net.projectsrl.wm.importdata;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.wm.utils.Utils;

public class FunctionFileUploadAllegatiArchivio extends FunctionFileUploadDocumenti_base {

    public FunctionFileUploadAllegatiArchivio() {

        super();
    }

    public FunctionFileUploadAllegatiArchivio(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        super.mostra(req, res, userInfo);
    }

    
    
    @Override
    protected String getPaginaChiusuraPopup() {

        return "file_upload_allegati_archivio_chiudi";
    }

    protected void loadData(String sessionUser, String absolutePathFileName, SsbServletRequest req) throws AppCrash {

    	String fileName = absolutePathFileName.substring(absolutePathFileName.lastIndexOf("\\") + 1);
    	String azienda = (String) req.getSession(false).getAttribute(Costanti_itf.AZIENDA_FERIEPERMESSO);
    	String matricola = (String) req.getSession(false).getAttribute(Costanti_itf.MATRICOLA_FERIEPERMESSO);
    	
        if (azienda != null) {
            String inserisciNome = "INSERT INTO FERIE_PERMESSI_ATCH (ID_FERIEPERMESSO,ID_FERIEPERMESSO_ATCH,PERCORSO,FILENAME,AZIENDA,MATRICOLA,DATA_CARICAMENTO,ORA_CARICAMENTO) VALUES "
            		+ "('','" + Utils.getUnique() + "','" + azienda+"/"+matricola+"/','"+fileName + "','"+(String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI")+"','"+(String) req.getSession(false).getAttribute("MATRICOLA")+"','"+Utils.getStringDataOggiRibaltata()+"','"+Utils.getOrario()+"')";
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
        
        String idFeriePermesso = (String) req.getSession(false).getAttribute(Costanti_itf.ID_FERIEPERMESSO_SESSIONE);
        if (idFeriePermesso != null) {
            templateData.put("ID_FERIEPERMESSO_SESSIONE", idFeriePermesso);
        }
        
        return templateData;

    }    

}
