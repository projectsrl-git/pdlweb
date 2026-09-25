
package net.projectsrl.wm.configurazione.core;

import java.util.HashMap;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoRichiedenti
 * 
 */
public class FunctionInserimentoRichiedenti extends FunctionInserimento {

    private static final String PAGE   = "inserimento_richiedenti";
    
    public FunctionInserimentoRichiedenti() {

        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetRichiedenti");
    }

    public FunctionInserimentoRichiedenti(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetRichiedenti");
    }

    @SuppressWarnings("unchecked")
    protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

        String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);

        if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
            templateData.put("ID_GRUPPO", Utils.getUnique());
            return templateData;
        }
        return templateData;

    } 
    
    
    @SuppressWarnings("unchecked")
	protected HashMap saveVar(HashMap templateData, SsbServletRequest req) throws AppCrash {
		insertDettaglioRichiedenti(req);
		insertTabellaApprovazioni(req);
		return templateData;
	}
    
    private void insertDettaglioRichiedenti( SsbServletRequest req) throws AppCrash {
    	String sqlDelete ="DELETE FROM RICHIEDENTI_DETT WHERE ID_GRUPPO='"+req.getField("ID_GRUPPO")+"' AND ID_RICHIEDENTE='"+req.getField("A_1")+"'";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete);
		
		String sqlInsert ="INSERT INTO RICHIEDENTI_DETT (ID_GRUPPO,ID_RICHIEDENTE) VALUES ('"+req.getField("ID_GRUPPO")+"','"+req.getField("A_1")+"')";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsert);
    }
    
    
    private void insertTabellaApprovazioni( SsbServletRequest req) throws AppCrash {
    	
    	String sqlDelete ="DELETE FROM APPROVAZIONI WHERE ID_GRUPPO='"+req.getField("ID_GRUPPO")+"' AND LIVELLO_1='' AND LIVELLO_2='' AND LIVELLO_3='' AND LIVELLO_4='' AND LIVELLO_5=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete);
		
		String sqlInsert ="INSERT INTO APPROVAZIONI (ID_GRUPPO,AZIENDA) VALUES ('"+req.getField("ID_GRUPPO")+"','"+req.getField("AZIENDA")+"')";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsert);
		
		String sqlDelete2 ="DELETE FROM APPROVAZIONI_RENDICONTAZIONI WHERE ID_GRUPPO='"+req.getField("ID_GRUPPO")+"' AND LIVELLO_1='' AND LIVELLO_2='' AND LIVELLO_3='' AND LIVELLO_4='' AND LIVELLO_5=''";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDelete2);
		
		String sqlInsert2 ="INSERT INTO APPROVAZIONI_RENDICONTAZIONI (ID_GRUPPO,AZIENDA) VALUES ('"+req.getField("ID_GRUPPO")+"','"+req.getField("AZIENDA")+"')";
		net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsert2);
    }
    
    
}
