
package net.projectsrl.pdlweb.moduli.limod64.core;

import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.bow.pdf.FunctionStampaPDFGeneric_base;

public class FunctionStampaLIMOD64 extends FunctionStampaPDFGeneric_base<CreateLIMOD64Pdf> {

    public FunctionStampaLIMOD64() {

        super();
    }

    public FunctionStampaLIMOD64(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected void setData(SsbServletRequest req, Map<String, Object> data) throws AppCrash {

        HashMap<String, String> param = new HashMap<String, String>();
        String idModulo = req.getField("ID_MODULO");
        param.put("WHERECONDITION", " WHERE ID_MODULO=" + idModulo);
        param.put("WHERECONDITION_AZIENDE", " IS NOT NULL ");
        
        fillDataFromSingleRowDataSet("DSLIMOD64", data, param);
        fillDataFromDataSet("DSLIMOD64Dettaglio", data, param);
        
    }



}
