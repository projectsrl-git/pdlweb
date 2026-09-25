
package net.projectsrl.dafne.authentication;

import java.sql.Timestamp;

import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.misc.Util;
import net.project.misc.hash;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.dafne.core.ProfiliUtente;
import net.projectsrl.dafne.db.UtentiDAO;
import net.projectsrl.webapp.authentication.UsernamePasswordAuthenticationProvider;
import net.projectsrl.webapp.core.WebAppConstants_itf;
import net.projectsrl.webapp.security.WebAppUserSecurityInfo;

public class DafneAuthenticationProvider extends UsernamePasswordAuthenticationProvider<Integer> {

    public DafneAuthenticationProvider() {

        super();
    }

    public DafneAuthenticationProvider(String configName) {

        super(configName);
    }

    @Override
    protected void setSpecificUserSecurityInfo(WebAppUserSecurityInfo<Integer> userInfo, Row_itf dbRow)
            throws AppCrash {

        super.setSpecificUserSecurityInfo(userInfo, dbRow);
        setUserLastLoginTimestamp(userInfo);

        String includedHeader = Config.GetInstance().getProperty("Page.Header.include");
        userInfo.setField(DafneCostanti_itf.INCLUDED_HEADER, includedHeader);

        String includedBoxTitle = Config.GetInstance().getProperty("Page.BoxTitle.include");
        userInfo.setField(DafneCostanti_itf.INCLUDED_BOX_TITLE, includedBoxTitle);

        String includedHeadInsertForm = Config.GetInstance().getProperty("Page.HeadInsertForm.include");
        userInfo.setField(DafneCostanti_itf.INCLUDED_HEAD_INSERT_FORM, includedHeadInsertForm);

        String includedFooter = Config.GetInstance().getProperty("Page.Footer.include");
        userInfo.setField(DafneCostanti_itf.INCLUDED_FOOTER, includedFooter);

        // dati azienda
        String aziendeUtenti = (String) dbRow.getField(DafneCostanti_itf.AZIENDE_UTENTE);
        userInfo.setField(DafneCostanti_itf.AZIENDE_UTENTE, aziendeUtenti.split(","));

        String aliasAziendaUtenti = (String) dbRow.getField(DafneCostanti_itf.ALIAS_AZIENDA_UTENTE);
        userInfo.setField(DafneCostanti_itf.ALIAS_AZIENDA_UTENTE, aliasAziendaUtenti);

        String codiceAziendaUtente = (String) dbRow.getField(DafneCostanti_itf.CODICE_AZIENDA_UTENTE);
        userInfo.setField(DafneCostanti_itf.AZIENDA_MENU, codiceAziendaUtente);
        userInfo.setField(DafneCostanti_itf.AZIENDA_SESSIONE, codiceAziendaUtente);

        String whereConditionAziende = " !='' ";
        if (Util.IsNotEmpty(aziendeUtenti)) {
            whereConditionAziende = " IN (" + aziendeUtenti + ") ";
        }
        userInfo.setField(DafneCostanti_itf.WHERECONDITION_AZIENDE, whereConditionAziende);

        String profilo = (String) dbRow.getField(WebAppConstants_itf.PROFILO);

        String direzioniUtente = (String) dbRow.getField(DafneCostanti_itf.DIREZIONI_UTENTE);
        String whereConditionDirezioni = " IS NULL ";
        if (profilo.contains(ProfiliUtente.HUMAN_RESOURCES_MANAGER.getCode())) {
            whereConditionDirezioni = " IS NOT NULL ";
        } else if (Util.IsNotEmpty(direzioniUtente)) {
            whereConditionDirezioni = " IN (" + direzioniUtente + ") ";
        }
        userInfo.setField(DafneCostanti_itf.WHERECONDITION_DIREZIONI, whereConditionDirezioni);

        String risorseUtente = (String) dbRow.getField(DafneCostanti_itf.RISORSE_UTENTE);
        String whereConditionRisorse = " IS NULL ";
        if (profilo.contains(ProfiliUtente.HUMAN_RESOURCES_MANAGER.getCode())) {
            whereConditionRisorse = " IS NOT NULL ";
        } else if (Util.IsNotEmpty(risorseUtente)) {
            whereConditionRisorse = " IN (" + risorseUtente + ") ";
        }
        userInfo.setField(DafneCostanti_itf.WHERECONDITION_RISORSE, whereConditionRisorse);

        String whereConditionRisorseRichieste = " IS NULL ";
        if (profilo.contains(ProfiliUtente.HUMAN_RESOURCES_MANAGER.getCode())) {
            whereConditionRisorseRichieste = " IS NOT NULL ";
        } else if (Util.IsNotEmpty(risorseUtente)) {
            whereConditionRisorseRichieste = "";
            whereConditionRisorseRichieste += " IN (" + risorseUtente + ") OR (SELECT COUNT(*) FROM (";
            whereConditionRisorseRichieste += " SELECT ID_RISORSA FROM RISORSE WHERE ID_APPROVATORI LIKE '%'+CAST(ID_RISORSA AS VARCHAR(5))+',%' OR ID_APPROVATORI LIKE '% '+CAST(ID_RISORSA AS VARCHAR(5))) AS T1 WHERE ID_RISORSA IN ("
                    + risorseUtente + ") ";
            whereConditionRisorseRichieste += ")>0";
        }
        userInfo.setField(DafneCostanti_itf.WHERECONDITION_RISORSE_RICHIESTE, whereConditionRisorseRichieste);
        
        String whereConditionRisorseCedolini = "";
        if (profilo.contains(ProfiliUtente.HUMAN_RESOURCES_MANAGER.getCode()) || profilo.contains(ProfiliUtente.ADMINISTRATOR.getCode()) || profilo.contains(ProfiliUtente.ADMIN_AZIENDA.getCode())) {
            whereConditionRisorseCedolini = " OR 1=1 ";
        }
        userInfo.setField(DafneCostanti_itf.WHERECONDITION_RISORSE_CEDOLINI, whereConditionRisorseCedolini);

    }

    private void setUserLastLoginTimestamp(WebAppUserSecurityInfo<Integer> userInfo) throws AppCrash {

        UtentiDAO utente = new UtentiDAO();
        utente.setAttribute(UtentiDAO.ID_UTENTE, userInfo.getIdUtente());
        utente.setAttribute(UtentiDAO.TS_LOGIN, new Timestamp(System.currentTimeMillis()));
        utente.update();
    }

    @Override
    protected boolean isValidPassword(String dbPassword, String reqPassword, String expireDate) throws AppCrash {

        boolean isValidPassword = hash.doSHA1(reqPassword).equals(dbPassword) || reqPassword.equals(dbPassword);

        return isValidPassword;
    }

}
