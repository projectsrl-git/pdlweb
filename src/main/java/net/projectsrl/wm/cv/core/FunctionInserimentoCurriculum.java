
package net.projectsrl.wm.cv.core;

import java.io.File;
import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.core.Costanti_itf;
import net.projectsrl.core.FunctionInserimento;
import net.projectsrl.db.PjDAO_base;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.wm.db.ColloquiDAO;
import net.projectsrl.wm.db.CurriculDAO;
import net.projectsrl.wm.db.EsperienzeDAO;
import net.projectsrl.wm.db.IntervCDAO;
import net.projectsrl.wm.db.IstrFormDAO;
import net.projectsrl.wm.mail.DeferredMailSender;
import net.projectsrl.wm.mail.SendSMTPMail;
import net.projectsrl.wm.utils.RandomPasswordGenerator;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionInserimentoCurriculum
 * 
 */
public class FunctionInserimentoCurriculum extends FunctionInserimento {

    private static final String PAGE                       = "inserimento_curriculum";
    private static final String DATASET_UTENTI             = "DataSetUtenti";
    private static final String DATASET_PROFILO_ORARIO     = "DataSetProfiloOrario";
    private static final String DATASET_PROFILO_XDIP_COUNT = "DataSetProfiloXDipendenteCount";

    int                         noOfCAPSAlpha              = 1;
    int                         noOfDigits                 = 1;
    int                         noOfSplChars               = 0;
    int                         minLen                     = 6;
    int                         maxLen                     = 7;

    public static final String  PAGE_STAMPA                = "stampa_cv";

    public FunctionInserimentoCurriculum() {
        super();
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetCV");
    }

    public FunctionInserimentoCurriculum(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageMostra(PAGE);
        setPageElabora(PAGE);
        setDatasetTestata("DataSetCV");
    }

    @SuppressWarnings("unchecked")
    protected HashMap loadVar(HashMap templateData, SsbServletRequest req) throws AppCrash {

        String option = req.getField(OPZIONE_INSERIMENTO_MODIFICA);
        templateData.put("AZIENDA_INSERIMENTO", (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE"));
        templateData.put("ANNOCORRENTE", new Integer(Utils.getAnnoOggi()));
        templateData.put("CATPROT", req.getField("CATPROT"));
        templateData.put("AZIENDA_CV", req.getField("AZIENDA_CV"));
        templateData.put("CHECK_LINE1", "");
        templateData.put("CHECK_LINE2", "");
        templateData.put("CHECK_LINE3", "");
        templateData.put("CHECK_LINE4", "");
        templateData.put("CHECK_LINE5", "");
        templateData.put("CHECK_LINE6", "");
        templateData.put("CHECK_LINE7", "");
        templateData.put("CHECK_LINE8", "");
        templateData.put("CHECK_LINE9", "");
        req.getSession(false).setAttribute(Costanti_itf.ID_CATPROT_SESSIONE, req.getField("CATPROT"));

        if (option == null || option.equals("") || option.equals(OPZIONE_INSERIMENTO)) {
            templateData.put("D_REGISTRAZ", Utils.getStringDataOggi());
            templateData.put("IDNAZIONE", "ITA");
            String idUnivoco = Utils.getUnique();
            templateData.put("TAGGANCIO", idUnivoco);
            templateData.put("ID_SOGGETTO", idUnivoco);
            templateData.put("ID_DIPENDENTE_SESSIONE", idUnivoco);
            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_INSERIMENTO);
            putTaggancioInSession(req, templateData);
            return templateData;

        }
        putTaggancioInSession(req, templateData);
        return templateData;

    }

    @SuppressWarnings("unchecked")
    private void putTaggancioInSession(SsbServletRequest req, HashMap templateData) {

        String idTaggancio = (String) templateData.get("TAGGANCIO");
        if (idTaggancio == null || idTaggancio.equals("")) {
            idTaggancio = req.getField("TAGGANCIO");
            if (idTaggancio == null || idTaggancio.equals("")) {
                return;
            }
        }

        req.getSession(false).setAttribute(Costanti_itf.ID_CURRICULUM_SESSIONE, idTaggancio);
        templateData.put("AZIENDA_UPLOAD", (String) req.getSession(false).getAttribute("AZIENDA_CEDOLINI"));
        templateData.put("MATRICOLA_UPLOAD", (String) req.getSession(false).getAttribute("MATRICOLA"));
    }

    /**
     * I campi contenenti i dati deiprospect mi arrivano con CODICE + RAGIONE SOCIALE in questo formato
     * "F:IDANAAZIRIC=000000001;F:ANAAZIRIC=PROJECT SRL DI CAVA MANARA"
     * 
     * Devo separare i valori con apposito metodo di Utils.leggeStringaElencoCampi(<nome campo>)
     */
    @SuppressWarnings("unchecked")
    protected HashMap setTemplateDataFromRequest(HashMap templateData, SsbServletRequest req) {

        templateData = super.setTemplateDataFromRequest(templateData, req);

        templateData.put(CurriculDAO.IDANAAZI, Utils.leggeStringaElencoCampi("IDANAAZI", req.getField("IDANAAZI")));
        templateData.put(CurriculDAO.ANAAZI, Utils.leggeStringaElencoCampi("ANAAZI", req.getField("IDANAAZI")));

        return templateData;
    }

    protected PjDAO_base setDAOFieldsFromRequest(SsbServletRequest req, PjDAO_base dao) throws AppCrash {

        dao = super.setDAOFieldsFromRequestPrivate(req, dao);

        if (dao instanceof CurriculDAO) {
            dao.setField(CurriculDAO.IDANAAZI, Utils.leggeStringaElencoCampi("IDANAAZI", req.getField("IDANAAZI")));
            dao.setField(CurriculDAO.ANAAZI, Utils.leggeStringaElencoCampi("ANAAZI", req.getField("IDANAAZI")));
            dao.setField(CurriculDAO.ANNOTAZIONI, req.getField("CV_ANNOTAZIONI"));
            dao.setField(CurriculDAO.IDTIOPL, req.getField("CV_IDTIOPL"));
        }

        if (dao instanceof EsperienzeDAO) {
            dao.setField(EsperienzeDAO.DURATA, req.getField("ESP_DURATA"));
        }

        if (dao instanceof ColloquiDAO) {
            dao.setField(ColloquiDAO.ANNOTAZIONI, req.getField("COLL_ANNOTAZIONI"));
            dao.setField(ColloquiDAO.IDTIOPL, req.getField("COLL_IDTIOPL"));
            dao.setField(ColloquiDAO.ID_COLLOQUIO, req.getField("COLL_ID_COLLOQUIO"));
        }

        if (dao instanceof IstrFormDAO) {
            dao.setField(IstrFormDAO.DURATA, req.getField("ISTR_DURATA"));
        }

        java.util.Calendar cal = new java.util.GregorianCalendar(2007, 7, 1);
        cal.get(java.util.Calendar.DAY_OF_WEEK);

        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected HashMap putTemplateFieldFromDAO(HashMap templateData, PjDAO_base dao) throws AppCrash {

        templateData = super.putTemplateFieldFromDAO(templateData, dao);

        if (dao instanceof EsperienzeDAO) {
            templateData.put("ESP_DURATA", dao.getField(EsperienzeDAO.DURATA));
        }

        if (dao instanceof CurriculDAO) {
            templateData.put("CV_ANNOTAZIONI", dao.getField(CurriculDAO.ANNOTAZIONI));
            templateData.put("CV_IDTIOPL", dao.getField(CurriculDAO.IDTIOPL));
            templateData.put("CATPROT", dao.getField(CurriculDAO.CATPROT));
            templateData.put("AZIENDA_CV", dao.getField(CurriculDAO.AZIENDA_CV));
        }

        if (dao instanceof ColloquiDAO) {
            templateData.put("COLL_ANNOTAZIONI", dao.getField(ColloquiDAO.ANNOTAZIONI));
            templateData.put("COLL_IDTIOPL", dao.getField(ColloquiDAO.IDTIOPL));
            templateData.put("COLL_ID_COLLOQUIO", dao.getField(ColloquiDAO.ID_COLLOQUIO));
        }

        if (dao instanceof IntervCDAO) {
            templateData.put("INT_ID_COLLOQUIO", dao.getField(IntervCDAO.ID_COLLOQUIO));
        }
        if (dao instanceof IstrFormDAO) {
            templateData.put("ISTR_DURATA", dao.getField(IstrFormDAO.DURATA));
        }

        return templateData;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected HashMap clearTemplateFieldFromDAO(HashMap templateData, PjDAO_base dao) throws AppCrash {

        templateData = super.clearTemplateFieldFromDAO(templateData, dao);

        if (dao instanceof EsperienzeDAO) {
            templateData.put("ESP_ANNOTAZIONI", "");
            templateData.put("ESP_IDSETTAZI", "");
            templateData.put("ESP_DURATA", "");
        }

        if (dao instanceof CurriculDAO) {
            templateData.put("CV_ANNOTAZIONI", "");
            templateData.put("CV_IDTIOPL", "");
        }

        if (dao instanceof ColloquiDAO) {
            templateData.put("COLL_ANNOTAZIONI", "");
            templateData.put("COLL_IDTIOPL", "");
            templateData.put("COLL_ID_COLLOQUIO", "");
        }

        if (dao instanceof IntervCDAO) {
            templateData.put("INT_ID_COLLOQUIO", "");
        }
        if (dao instanceof IstrFormDAO) {
            templateData.put("ISTR_DURATA", "");
        }

        return templateData;
    }

    @SuppressWarnings("unchecked")
    public void elabora(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        HashMap<String, Object> templateData = setTemplateDataFromRequest(setCommonTags(req, userInfo), req);
        templateData.put("TABS", req.getField("TABS"));

        saveVarStandard(templateData, req, res);

        if (req.getField("OPZIONE_INSERIMENTO_MODIFICA").equals(OPZIONE_INSERIMENTO)) {
            String codice1 = req.getField("CODICE").substring(0, 6);
            String codice3 = req.getField("CODICE").substring(8);
            String progressivo = prossimoProgressivo(codice1);
            String sqlSistemaCodiceCV = "update curricul set codice = '" + codice1 + progressivo + codice3
                    + "' where taggancio = (select taggancio from"
                    + " (select codice,taggancio,max(taggancio) over (partition by codice) max_taggancio from curricul) as aaa"
                    + " where taggancio = max_taggancio and codice = '" + req.getField("CODICE") + "')";
            net.projectsrl.wm.utils.WMUtils.executeQuery(sqlSistemaCodiceCV);
            templateData.put("CODICE", codice1 + progressivo + codice3);
        }
        String sqlDeleteProfiliVuoti = "DELETE FROM PROFILO_X_DIPENDENTE WHERE ID_PRODIP='' AND ID_PROFILO=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteProfiliVuoti);

        if (!templateData.get("IMP_BASE_DIP").equals("1")) {
            String idDipendente = req.getField("ID_DIPENDENTE");
            String sqlDeleteAssociazioneCommessa = "DELETE FROM COMMESSA_X_DIPENDENTE WHERE ID_DIPENDENTE='"
                    + idDipendente + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteAssociazioneCommessa);
            String idComdip = "00000000000000000000" + idDipendente;
            String sqlInsertAssociazioneCommessa = "INSERT INTO COMMESSA_X_DIPENDENTE (ID_COMDIP,ID_COMMESSA,ID_DIPENDENTE,PREDEFINITA) VALUES ('"
                    + idComdip + "','00000000000000000000','" + idDipendente + "','S')";
            net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsertAssociazioneCommessa);
        }

        if (!req.getField("AZIENDA_CV").equals("")) {
            String idToInsert = getProfiloOrario(req.getField("AZIENDA_CV"));
            String nProfili = getCountProfili(req.getField("ID_DIPENDENTE"));
            if (nProfili.equals("0")) {
                String queryInsert1 = "INSERT INTO PROFILO_X_DIPENDENTE (DAGGANCIO, ID_PROXDIP,ID_PRODIP,ID_PROFILO,ID_DIPENDENTE,PREDEFINITO) "
                        + "VALUES ('" + req.getField("ID_DIPENDENTE") + "','" + Utils.getUnique() + "','" + idToInsert
                        + req.getField("ID_DIPENDENTE") + "','" + idToInsert + "','" + req.getField("ID_DIPENDENTE")
                        + "','S')";
                net.projectsrl.wm.utils.WMUtils.executeQuery(queryInsert1);
            }
        } else {
            String sqlDeleteProfiliNonUsati = "DELETE FROM PROFILO_X_DIPENDENTE WHERE DAGGANCIO = '"
                    + req.getField("ID_DIPENDENTE") + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteProfiliNonUsati);
        }

        if (req.getField("ASSOCIAZIONE").equals("PROFILO_ORARIO") && req.getField("PREDEFINITO").equals("S")) {
            String queryUpdate = "UPDATE PROFILO_X_DIPENDENTE SET PREDEFINITO='' WHERE ID_DIPENDENTE='"
                    + req.getField("ID_DIPENDENTE") + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate);
            String queryUpdate9 = "UPDATE PROFILO_X_DIPENDENTE SET PREDEFINITO='S' WHERE ID_DIPENDENTE='"
                    + req.getField("ID_DIPENDENTE") + "' AND ID_PROFILO='" + req.getField("ID_PROFILO") + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(queryUpdate9);
        }
        String idToUpdate = req.getField("ID");
        String idUnivocoProfilo = req.getField("ID_UNIVOCO");
        String sqlUpdate1 = "";
        String sqlUpdate2 = "";
        if (req.getField("TIPO").equals("PROFILO_ORARIO")) {
            sqlUpdate1 = "UPDATE PROFILO_X_DIPENDENTE SET PREDEFINITO = 'S' WHERE ID_PROXDIP='" + idUnivocoProfilo
                    + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate1);
            sqlUpdate2 = "UPDATE PROFILO_X_DIPENDENTE SET PREDEFINITO = 'N' WHERE ID_DIPENDENTE='" + idToUpdate
                    + "' AND ID_PROXDIP<>'" + idUnivocoProfilo + "'";
            net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdate2);
        }

        String sqlDeleteCentriCostoVuoti = "DELETE FROM CV_CENTRICOSTO WHERE CENTRO_COSTO=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteCentriCostoVuoti);

        String sqlDeletePATVuoti = "DELETE FROM CV_PAT WHERE PAT=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeletePATVuoti);

        String sqlDeleteTCVuoti = "DELETE FROM CV_TIPICOSTO WHERE TIPO_COSTO=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteTCVuoti);

        String sqlDeleteVCVuoti = "DELETE FROM CV_VOCITARIFFA WHERE VOCE_TARIFFA=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteVCVuoti);

        String sqlDeleteEsperienzeVuote = "DELETE FROM ESPPROF WHERE IDTIOPL='' AND D_INIZIO='' AND D_FINE ='' AND DURATA ='' AND Substring(MANSIONRESP,0,100)='' AND Substring(ATTIVSVOLTE,0,100)='' AND Substring(SETTOREESP,0,100)='' AND Substring(ANNOTAZIONI,0,100)='' AND AZRAGSOC='' AND AZINDIRIZZO='' AND Substring(CONSTRUM,0,100)='' AND ATTIVMANS_ISTR=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteEsperienzeVuote);

        String sqlDeleteFormazioneVuote = "DELETE FROM ISTRFORM WHERE IDLIVISTRN='' AND IDLIVISTRI='' AND D_INIZIO_ISTR='' AND D_FINE_ISTR='' AND DURATA='' AND Substring(ISTITENTEFO,0,100)='' AND Substring(MATABILPROF,0,100)='' AND QUALIFICA='' AND Substring(TEMCOMPPROF,0,100)='' AND Substring(ATTESTATIFP,0,100)='' AND Substring(CERTIFICAZ,0,100)='' AND TESI='' AND Substring(DESCTESI,0,100)='' AND FLAG_PRIMARIO='' AND LODE = '' AND DIVIDENDO='' AND DIVISORE='' AND LIVELLO_ISTR=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteFormazioneVuote);

        String sqlDeleteLingueVuote = "DELETE FROM LINGUECU WHERE IDLINGUA=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteLingueVuote);

        String sqlDeleteColloquiVuoti = "DELETE FROM COLLOQUI WHERE IDTCONTATT='' AND IDTIOPL='' AND IDTIPOCNTR='' AND COMUNE='' AND D_DTCONTATT='' AND Substring(SINTESIESIT,0,100)='' AND Substring(QUALIFICLAV,0,100)='' AND Substring(RETRIBUZION,0,100)='' AND Substring(PROFILOPROF,0,100)='' AND Substring(SPECIALIZZ,0,100)='' AND Substring(SITUAZIONE,0,100)='' AND Substring(DISPONTRASF,0,100)='' AND Substring(ANNOTAZIONI,0,100)='' AND Substring(DISPONDAL,0,100)='' AND Substring(RISULTATO,0,100)='' AND Substring(IMPRESVALUT,0,100)='' AND Substring(ANNOTRISER,0,100)='' AND Substring(AZIONIDAINT,0,100)='' AND Substring(PROPDAFORM,0,100)='' AND Substring(ALTREINDIC,0,100)='' AND RISCOLLOQUIO='' AND Substring(RICHIESTASPEC,0,100)=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteColloquiVuoti);

        String sqlDeleteBadgeVuoti = "DELETE FROM BADGE WHERE CODICE_BADGE='' AND CODICE_MECCANOG='' AND TIPOLOGIA='' AND ID_SOGGETTO='' AND DATA_INIZIO='' AND DATA_FINE='' AND SOSPESO=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteBadgeVuoti);

        String sqlDeleteFormazioniProfessionaliVuote = "DELETE FROM FORMAZIONE_PROFESSIONALE WHERE TITOLO_ATTIVITA='' AND SOGGETTO_ATTIVITA='' AND SEDE_SOGGETTO='' AND CONCLUSO='' AND DURATA_ATTIVITA='' AND ATTESTAZIONE='' AND ALTRE_ATTESTAZIONI='' AND TIROCINIO='' AND ENTE='' AND OBBLIGO_LEGGE='' AND FORMAZIONE_VALIDA = ''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteFormazioniProfessionaliVuote);

        sqlDeleteProfiliVuoti = "DELETE FROM PROFILO_X_DIPENDENTE WHERE ID_PROFILO=''";
        net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteProfiliVuoti);

        templateData.put("SALVATO", "Salvataggio effettuato con successo");
        templateData.put("SALVATO_REMINDER", "Ultimo salvataggio effettuato alle ore " + Utils.getOrario());

        String dirAZIENDA = _applicationSrv.getRoot() + "areadocumenti/" + req.getField("AZIENDA_CV") + "/";
        new File(dirAZIENDA).mkdir();
        String dirMATRICOLA = _applicationSrv.getRoot() + "areadocumenti/" + req.getField("AZIENDA_CV") + "/"
                + req.getField("MATRICOLA") + "/";
        new File(dirMATRICOLA).mkdir();

        String aziendaSalvata = getUtente(req.getField("TAGGANCIO"))[2];
        String mailSalvata = getUtente(req.getField("TAGGANCIO"))[1];

        if (templateData.get("ESISTE").equals("NO") || templateData.get("ESISTE").equals("")) {
            String new_pwd = "";
            char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);
            new_pwd = new String(pswd);
            if (new_pwd.length() < 7) {
                new_pwd = new_pwd + Utils.getUnique().substring(14, 18);
            } else {
                new_pwd = new_pwd + Utils.getUnique().substring(14, 17);
            }

            String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");
            String destinatari_mail = req.getField("EMAIL");
            String nominativo = req.getField("NOME") + " " + req.getField("COGNOME");

            String azienda = req.getField("AZIENDA_CV");
            String idDipendente = req.getField("TAGGANCIO");
            String idUnivoco = Utils.getUnique();
            String esiste = getUtente(idDipendente)[0];

            if (esiste.equals("S")) {
                String sqlUpdateUtente = "UPDATE UTENTI SET USERID='" + destinatari_mail + "', EMAIL='"
                        + destinatari_mail + "', AZIENDA='" + azienda + "' WHERE ID_DIPENDENTE='" + idDipendente + "'";
                net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateUtente);
                templateData = inviaMail(req, templateData, from, new_pwd, destinatari_mail, nominativo, azienda,
                        aziendaSalvata, esiste);
                idUnivoco = getIdUnivocoUtente(idDipendente);
            } else {
                if (azienda.equals("")) {
                    String sqlUpdateUtente = "UPDATE UTENTI SET USERID='" + req.getField("EMAIL") + "', AZIENDA='"
                            + req.getField("AZIENDA_CV") + "', EMAIL='" + req.getField("EMAIL") + "', NOME='"
                            + req.getField("NOME").replace("'", "''") + "', COGNOME='"
                            + req.getField("COGNOME").replace("'", "''") + "' WHERE ID_DIPENDENTE='"
                            + req.getField("TAGGANCIO") + "'";
                    net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateUtente);
                }
                String sqlDeleteUtente = "DELETE FROM UTENTI WHERE USERID='" + destinatari_mail + "'";
                net.projectsrl.wm.utils.WMUtils.executeQuery(sqlDeleteUtente);
                String sqlInsertUtente = "INSERT INTO UTENTI (USERID,PASSWORD,RUOLO,ID_CODICE,NOME,COGNOME,EMAIL,DATA,DATA_SC,ATTIVO,AZIENDA,ID_DIPENDENTE,PWD_SCADUTA)"
                        + " VALUES ('" + destinatari_mail + "','" + new_pwd + "','D','" + idUnivoco + "','"
                        + req.getField("NOME").replace("'", "''") + "','" + req.getField("COGNOME").replace("'", "''")
                        + "','" + destinatari_mail + "','" + Utils.getStringDataOggiRibaltata() + "','2999/12/31','S','"
                        + azienda + "','" + idDipendente + "','S')";
                net.projectsrl.wm.utils.WMUtils.executeQuery(sqlInsertUtente);
                String sqlUpdateDipendente = "UPDATE CURRICUL SET ID_UTENTE='" + idUnivoco + "' WHERE TAGGANCIO='"
                        + idDipendente + "'";
                net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateDipendente);

            }

            templateData.put(OPZIONE_INSERIMENTO_MODIFICA, OPZIONE_MODIFICA);
            templateData.put("ID_UTENTE", idUnivoco);
            templateData.put("TAGGANCIO", idDipendente);
            if (req.getField("INVIO_MAIL").equals("yes")) {
                if (!destinatari_mail.equals(mailSalvata)) {
                    if (!req.getField("AZIENDA").equals("")) {
                        templateData = inviaMail(req, templateData, from, new_pwd, destinatari_mail, nominativo,
                                azienda, aziendaSalvata, esiste);
                    }
                }
            }
        } else {
            if (!templateData.get("ESISTE_GIA").equals("SI")) {
                String sqlUpdateUtente3 = "UPDATE UTENTI SET USERID='" + req.getField("EMAIL") + "', AZIENDA='"
                        + req.getField("AZIENDA_CV") + "', EMAIL='" + req.getField("EMAIL") + "', NOME='"
                        + req.getField("NOME").replace("'", "''") + "', COGNOME='"
                        + req.getField("COGNOME").replace("'", "''") + "' WHERE ID_DIPENDENTE='"
                        + req.getField("TAGGANCIO") + "'";
                net.projectsrl.wm.utils.WMUtils.executeQuery(sqlUpdateUtente3);
                String new_pwd = "";
                char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits,
                        noOfSplChars);
                new_pwd = new String(pswd);
                if (new_pwd.length() < 7) {
                    new_pwd = new_pwd + Utils.getUnique().substring(14, 18);
                } else {
                    new_pwd = new_pwd + Utils.getUnique().substring(14, 17);
                }
                String from = Config.GetInstance().getProperty("mail.from", "noreply@projectsrl.net");
                String destinatari_mail = req.getField("EMAIL");
                String nominativo = req.getField("NOME") + " " + req.getField("COGNOME");

                String azienda = req.getField("AZIENDA_CV");
                String idDipendente = req.getField("TAGGANCIO");
                String idUnivoco = Utils.getUnique();
                String esiste = getUtente(idDipendente)[0];

                if (req.getField("INVIO_MAIL").equals("yes")) {
                    if (!destinatari_mail.equals(mailSalvata)) {
                        if (!req.getField("AZIENDA_CV").equals("")) {
                            templateData = inviaMail(req, templateData, from, new_pwd, destinatari_mail, nominativo,
                                    azienda, aziendaSalvata, esiste);
                        }
                    }
                    if (!aziendaSalvata.equals(req.getField("AZIENDA_CV"))) {
                        if (!req.getField("AZIENDA_CV").equals("")) {
                            templateData = inviaMail(req, templateData, from, new_pwd, destinatari_mail, nominativo,
                                    azienda, aziendaSalvata, esiste);
                        }
                    }
                }
            }
        }

        _applicationSrv.displayPage(PAGE, templateData, setPageDatasetParam(PAGE, req, templateData), res);
    }

    @SuppressWarnings({ "unchecked" })
    private HashMap inviaMail(SsbServletRequest req, HashMap templateData, String from, String new_pwd,
            String destinatari_mail, String nominativo, String azienda, String aziendaSalvata, String esiste)
            throws AppCrash {

        String portale = Config.GetInstance().getProperty("indirizzo.portale");
        String elencoDestinatari = destinatari_mail;
        String oggetto = "";

        String corpo = "Gentile " + nominativo.toUpperCase()
                + ",\n/nQuesta è una mail inviata automaticamente da DAFNE.\n/n";
        if (esiste.equals("S") && !aziendaSalvata.equals("")) {
        } else {
            oggetto = "Nuovo utente DAFNE";
            corpo += "E' stato creato il suo profilo utente per l'accesso a <a href='" + portale + "'>" + portale
                    + "</a>, qui di seguito trova le credenziali per l'accesso al portale:\n" + "/n";
            corpo += "<i>Userid</i>: " + destinatari_mail + "\n/n";
            corpo += "<i>Password</i>: " + new_pwd + "\n/n";
            corpo += "\n/nAcceda al sistema utilizzando la nuova password. Potrà modificarla con una nuova password utilizzando l'apposita pagina di Cambio Password.\n\n/n/nCordiali Saluti\n/n<i>Il Team DAFNE</i>";
        }
        System.out.println(elencoDestinatari);
        SendSMTPMail sendSMTPMail = new SendSMTPMail();
        sendSMTPMail.setFrom(from);
        sendSMTPMail.setSubject(oggetto);
        sendSMTPMail.setBody(corpo);
        sendSMTPMail.setTo(elencoDestinatari);
        sendSMTPMail.setServer(Config.GetInstance().getProperty("mail.SMTPHost"));

        try {
            MyAuthenticator auth = null;
            if (!Config.GetInstance().getProperty("mail.SMTPHost.user", "").equals("")) {
                auth = new MyAuthenticator();
            }
            sendSMTPMail.prepareMail(auth, false, "", "", "S");

            DeferredMailSender.getInstance().offer(sendSMTPMail);
            templateData.put("EMAIL_INVIATA", "OK");
            templateData.put("EMAIL_INVIATA_MESSAGE",
                    Config.GetInstance().getProperty("Message.email_inviata_ok", NO_MESSAGE));

        } catch (Throwable e) {
            templateData.put("EMAIL_INVIATA", "KO");
            templateData.put("EMAIL_INVIATA_MESSAGE",
                    Config.GetInstance().getProperty("Message.email_inviata_ko", NO_MESSAGE));
            new AppCrash(e);
        }

        return templateData;
    }

    private String[] getUtente(String idDipendente) throws AppCrash {

        String[] dati = { "", "", "" };
        String idTrovato = "";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_UTENTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE", idDipendente);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("ID_DIPENDENTE").toString().trim();
                if (idDipendente.equals(idTrovato)) {
                    dati[0] = "S";
                    dati[1] = dbRow.getField("EMAIL").toString().trim();
                    dati[2] = dbRow.getField("AZIENDA").toString().trim();
                }
            }
            dataSet.close();
        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
                }
            }
        }
        return dati;
    }

    private String getProfiloOrario(String azienda) throws AppCrash {

        String dati = "";
        String idTrovato = "";
        String predefinito = "";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_PROFILO_ORARIO);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("AZIENDA", azienda);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("AZIENDA").toString().trim();
                predefinito = dbRow.getField("PREDEFINITO").toString().trim();
                if (azienda.equals(idTrovato) && (predefinito.equals("S"))) {
                    dati = dbRow.getField("ID_PROFILO").toString().trim();
                }
            }
            dataSet.close();
        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
                }
            }
        }
        return dati;
    }

    private String getCountProfili(String idDipendente) throws AppCrash {

        String dati = "0";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_PROFILO_XDIP_COUNT);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE", idDipendente);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                dati = dbRow.getField("CONTEGGIO").toString().trim();

            }
            dataSet.close();
        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
                }
            }
        }
        return dati;
    }

    private String getIdUnivocoUtente(String idDipendente) throws AppCrash {

        String idUtente = "";
        String idTrovato = "";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_UTENTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE", idDipendente);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("ID_DIPENDENTE").toString().trim();
                if (idDipendente.equals(idTrovato)) {
                    idUtente = dbRow.getField("ID_CODICE").toString().trim();
                }
            }
            dataSet.close();
        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "Errore nella close del dataset");
                }
            }
        }
        return idUtente;
    }

    private String prossimoProgressivo(String codice1) throws AppCrash {

        String progressivo = "01";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", "DataSetUltimoCodiceCV");
            dataSet.open();

            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                String codiceShortDB = (String) dbRow.getField("CODICE_SHORT");
                int ultimoProgressivoInt = 0;
                if (codice1.equals(codiceShortDB)) {
                    ultimoProgressivoInt = Integer.parseInt((String) dbRow.getField("CODICE_NUM1")) + 1;
                    progressivo = String.format("%02d", ultimoProgressivoInt);
                }
            }
        } catch (AppCrash ac) {
            ac.logContext(this.getClass().getName(), "");
            throw ac;

        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext(this.getClass().getName(), "");
                    throw ac;
                }
            }
        }
        return progressivo;
    }


}
