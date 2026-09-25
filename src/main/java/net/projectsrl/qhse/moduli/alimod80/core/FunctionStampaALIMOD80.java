
package net.projectsrl.qhse.moduli.alimod80.core;

import java.util.HashMap;
import java.util.Map;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.bow.pdf.FunctionStampaPDFGeneric_base;

public class FunctionStampaALIMOD80 extends FunctionStampaPDFGeneric_base<CreateALIMOD80Pdf> {

    public FunctionStampaALIMOD80() {

        super();
    }

    public FunctionStampaALIMOD80(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected void setData(SsbServletRequest req, Map<String, Object> data) throws AppCrash {

        HashMap<String, String> param = new HashMap<String, String>();
        String idModulo = req.getField("ID_MODULO");
        param.put("WHERECONDITION", " WHERE ID_MODULO=" + idModulo);
        param.put("WHERECONDITION_AZIENDE", " IS NOT NULL ");

        fillDataFromSingleRowDataSet("DSALIMOD80", data, param);
        fillDataFromSingleRowDataSet("DSALIMOD80Dettaglio", data, param);

        data.put("COUNTRY", "ITALY");

    }

}
