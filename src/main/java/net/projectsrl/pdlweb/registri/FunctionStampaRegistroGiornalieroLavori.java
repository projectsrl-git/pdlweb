
package net.projectsrl.pdlweb.registri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.bow.pdf.FunctionStampaPDF_base;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.pdf.CreatePDF;
import net.projectsrl.webapp.core.WebAppConstants_itf;

public class FunctionStampaRegistroGiornalieroLavori extends FunctionStampaPDF_base {

    private static final String DATASET = "DSPDL";

    public FunctionStampaRegistroGiornalieroLavori(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);

    }

    @Override
    public boolean isAuthenticationRequired() {

        return false;
    }

    @Override
    protected String creaFilePdf(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {

        String nomeFileCompleto = null;

        try {
            String directory = _applicationSrv.getRoot() + "/Output/";
            Map<String, Object> data = new HashMap<String, Object>();
            setData(data, req, userInfo);
            CreatePDF pdf = new CreateLIMOD16(directory + "LIMOD16.pdf", directory, data);
            nomeFileCompleto = pdf.createPDF();

        } catch (Throwable e) {
            throw new AppCrash(e);
        }

        return nomeFileCompleto;

    }

    private void setData(Map<String, Object> data, SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {

        String whereCondition = req.getField("WHERECONDITION");
        HashMap<String, String> params = new HashMap<String, String>();

        if (whereCondition.replaceAll("\\s+", "").startsWith("AND")) {
            whereCondition = " WHERE 1=1 " + whereCondition;
        }
        params.put("WHERECONDITION", whereCondition);
        params.put(DafneCostanti_itf.WHERECONDITION_AZIENDE,
                getSpecificUserInfo(userInfo).getField(DafneCostanti_itf.WHERECONDITION_AZIENDE));

        String profilo = getSpecificUserInfo(userInfo).getField(WebAppConstants_itf.PROFILO);

        if (profilo.contains("CPT")) {
            data.put(WebAppConstants_itf.COGNOME_UTENTE,
                    getSpecificUserInfo(userInfo).getField(WebAppConstants_itf.COGNOME_UTENTE));
            data.put(WebAppConstants_itf.NOME_UTENTE,
                    getSpecificUserInfo(userInfo).getField(WebAppConstants_itf.NOME_UTENTE));
        } else {
            data.put(WebAppConstants_itf.COGNOME_UTENTE, " ");
            data.put(WebAppConstants_itf.NOME_UTENTE, " ");
        }

        List<String[]> report = new ArrayList<String[]>();
        data.put("report", report);

        fillDataFromDataSet(DATASET, data, params);

    }

    @Override
    protected void fillRowMapFromDataSet(Map<String, Object> data, Row_itf dbRow, String[] columnNames, Map<String, Object> rowMap)
            throws AppCrash {

        if (data.get("RAGSOC") == null && dbRow.getField("RAGSOC")!=null) {
            data.put("RAGSOC",  (String) dbRow.getField("RAGSOC"));
        }

        List<String[]> report = (List<String[]>) data.get("report");

        String[] row = new String[] { (String) dbRow.getField("NR_PDL"), (String) dbRow.getField("DESCRIZIONE_LAVORO"),
                (String) dbRow.getField("DESCR_EQUIPMENT"), (String) dbRow.getField("DESCR_AREA"),
                (String) dbRow.getField("IMPRESA_TESTO"), (String) dbRow.getField("RESPONSABILE_CENTRALE_DELEGATO"),
                (String) dbRow.getField("NOME_COGNOME_DELEGATO_LAVORI_AL"), (String) dbRow.getField("DT_PDL") };

        report.add(row);
    }

}
