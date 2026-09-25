
package net.projectsrl.cataloghicloud.prodottigas.core;

import java.util.Map;

import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.bow.parameters.ParametriDAO;
import net.projectsrl.cataloghicloud.anagrafiche.db.FamigliaProdottiGasDAO;
import net.projectsrl.cataloghicloud.anagrafiche.db.ProdottiGasDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.webapp.core.FunctionProjectWebApp_base;

public class FunctionSchedaProdotto extends FunctionProjectWebApp_base {

    public FunctionSchedaProdotto(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        String idProdottoGas = req.getField(ProdottiGasDAO.ID_PRODOTTI_GAS);

        String codiceProdotto = req.getField(ProdottiGasDAO.CODICE_PRODOTTO);
        PjNDAO_base prodottiGasDAO = new ProdottiGasDAO();

        try {
            if (Util.IsNotEmpty(idProdottoGas)) {
                
                prodottiGasDAO.setAttribute(ProdottiGasDAO.ID_PRODOTTI_GAS, idProdottoGas);
                ricercaProdotto(templateData, prodottiGasDAO);
                
            } else if (Util.IsNotEmpty(codiceProdotto)) {
                
                prodottiGasDAO.setAttribute(ProdottiGasDAO.CODICE_PRODOTTO, codiceProdotto);
                ricercaProdotto(templateData, prodottiGasDAO);
                
            }
        } catch (Throwable e) {
            templateData = createMapFromRequest(req, userInfo);
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }
    



    private void ricercaProdotto(Map<String, Object> templateData, PjNDAO_base prodottiGasDAO) throws AppCrash {

        if (prodottiGasDAO.retrieve()) {
            prodottiGasDAO.setMapFromAttributes(templateData);

            Integer idFamiglia = (Integer) prodottiGasDAO.getAttribute(ProdottiGasDAO.ID_FAMIGLIA);
            if (Util.IsNotEmpty(idFamiglia)) {
                PjNDAO_base famigliaDAO = new FamigliaProdottiGasDAO();
                famigliaDAO.setAttribute(FamigliaProdottiGasDAO.ID_FAMIGLIA, idFamiglia);
                if (famigliaDAO.retrieve()) {
                    famigliaDAO.setMapFromAttributes(templateData);
                }
            }

            String codiceAllestimento = (String) prodottiGasDAO.getAttribute(ProdottiGasDAO.CODICE_ALLESTIMENTO);

            if (Util.IsNotEmpty(codiceAllestimento)) {
                PjNDAO_base parametriDAO = new ParametriDAO();
                parametriDAO.setAttribute(ParametriDAO.DOMINIO, "ALS");
                parametriDAO.setAttribute(ParametriDAO.CODICE, codiceAllestimento);
                if (parametriDAO.retrieve()) {
                    String descrizioneAllestimento = (String) parametriDAO.getAttribute(ParametriDAO.DESCRIZIONE);
                    templateData.put("DESCR_ALLESTIMENTO", descrizioneAllestimento);
                }
            }

        }
    }
}
