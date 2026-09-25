
package net.projectsrl.pdlweb.interferenze.core;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.bow.pdf.FunctionStampaPDF_base;
import project.misc.Utils;

public class FunctionStampaModuloPlanimetria extends FunctionStampaPDF_base {

    public FunctionStampaModuloPlanimetria() {

        super();
    }

    public FunctionStampaModuloPlanimetria(ApplicationServices_itf applServices, String functionID,
            String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    protected String creaFilePdf(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {

        String outputFileName = null;
        try {
            String idImpianto = (String) req.getField("ID_IMPIANTO");
            String codImpianto = (String) req.getField("CODICE_IMPIANTO");
            String turnoSelezionato = (String) req.getSession(false).getAttribute("CODICE_TURNO_SELEZIONATO");

            if (turnoSelezionato==null){
            	turnoSelezionato="";
            }
            
            if (Utils.IsEmpty(idImpianto)) {
                return null;
            }

            CreaPDFImpiantoInterferenze creaMappa = new CreaPDFImpiantoInterferenze(new Integer(idImpianto));
            String fileNamePlanimetria = creaMappa.creaMappaArea(_applicationSrv.getRoot(),turnoSelezionato);

            outputFileName = fileNamePlanimetria.replace(CreaPDFImpiantoInterferenze.IMG_PATH, OUTPUT_PATH)
                    .replace("_temp", "").replace(CreaPDFImpiantoInterferenze.PRINT_SUFFIX,
                            "_" + getStringDataOggiTrattino() + "_" + turnoSelezionato + "_" + codImpianto);

            File tempPDFFile = new File(fileNamePlanimetria);
            tempPDFFile.renameTo(new File(outputFileName));
            Files.deleteIfExists(tempPDFFile.toPath());

        } catch (Throwable e) {
            throw new AppCrash(e);
        }

        return outputFileName;

    }

    private static String getStringDataOggiTrattino() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
        return sdf.format(new Date());
    }

}
