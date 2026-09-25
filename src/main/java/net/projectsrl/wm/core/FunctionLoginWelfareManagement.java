package net.projectsrl.wm.core;

import it.project.webapp.accesscontrol.FunctionLogin;
import it.project.webapp.core.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import project.misc.Utils;

public class FunctionLoginWelfareManagement extends FunctionLogin {

	private static final String DATASET_MENU_AREE_NAME = "DataSetMenuAree";
	private static final String DATASET_MENU_AREE_DETT_NAME = "DataSetMenuAreeDett";
	private static final String DATASET_MENU_AREE_SUB_DETT_NAME = "DataSetMenuAreeDettSub";
	private static final String DATASET_UTENTI         = "DataSetUtenteSingolo";

	@Override
	protected void authenticate(UserSecurityInfo userInfo, SsbServletRequest req) throws AppCrash {

		super.authenticate(userInfo, req);
	}

	public FunctionLoginWelfareManagement() {

		super();
	}

	public FunctionLoginWelfareManagement(ApplicationServices_itf applServices, String functionID,
			String functionName) {

		super(applServices, functionID, functionName);
	}

	
	protected void saveCognomeNomeUserInSession(SsbServletRequest req, HttpSession session) throws AppCrash {

        DataSet_itf dataSet = null;

        String user = getSessionUser(session);
        String ruolo = getSessionRole(session);

        try {
            if (user == null || user.equals("")) {
                user = null;
                return;
            }

            if (ruolo == null || ruolo.equals("")) {
                ruolo = null;
                return;
            }

            DataSetFactory dsFactory = DataSetFactory.getInstance();

            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_UTENTI);

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("USERID", user);

            dataSet.setParam(params);
            dataSet.open();

            String cognome = "";
            String nome = "";
            String idCodice = "";
            String userId = "";
            String ruoloUtente = "";
            String emailUtente="";
            String scadenzaUtente="";
            String attivoUtente="";
            String matricola="";
            String azienda="";
            String pwdScaduta="";
            
            String nominativo="";
            String dataNascita="";
            String cittaNascita="";
            String provNascita="";
            String indResidenza="";
            String cittaResidenza="";
            String provResidenza="";
            String codFiscale="";
            
            String approvaFPS="";
            String approvaRendicontazioni="";
            String codCurricul="";
            String idDipendente="";
            
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();

                cognome = dbRow.getField("COGNOME").toString().trim();
                nome = dbRow.getField("NOME").toString().trim();
                idCodice = dbRow.getField("ID_CODICE").toString().trim();
                userId = dbRow.getField("USERID").toString().trim();
                ruoloUtente = dbRow.getField("RUOLO").toString().trim();
                emailUtente = dbRow.getField("EMAIL").toString().trim();
                scadenzaUtente = dbRow.getField("DATA_SC").toString().trim();
                attivoUtente = dbRow.getField("ATTIVO").toString().trim();
                matricola = dbRow.getField("MATRICOLA").toString().trim();
                azienda = dbRow.getField("AZIENDA").toString().trim();
                
                nominativo = dbRow.getField("NOMINATIVO").toString().trim();
                dataNascita = dbRow.getField("DATA_NASCITA").toString().trim();
                cittaNascita = dbRow.getField("CITTA_NASCITA").toString().trim();
                provNascita = dbRow.getField("PROV_NASCITA").toString().trim();
                indResidenza = dbRow.getField("INDIR_RESIDENZA").toString().trim();
                cittaResidenza = dbRow.getField("CITTA_RESIDENZA").toString().trim();
                provResidenza = dbRow.getField("PROV_RESIDENZA").toString().trim();
                codFiscale = dbRow.getField("COD_FISCALE").toString().trim();
                pwdScaduta = dbRow.getField("PWD_SCADUTA").toString().trim();
                
                approvaFPS = dbRow.getField("APPROVATORE_FPS").toString().trim();
                approvaRendicontazioni = dbRow.getField("APPROVATORE_RENDICONTAZIONI").toString().trim();
                codCurricul = dbRow.getField("CODICE_CURRICUL").toString().trim();
                idDipendente = dbRow.getField("ID_DIPENDENTE").toString().trim();
                
            }

            if (cognome == null) {
                cognome = "";
            }

            if (nome == null) {
                nome = "";
            }
            
            if (idCodice == null) {
                idCodice = "";
            }
            
            if (userId == null) {
                userId = "";
            }
            
            if (ruoloUtente == null) {
                ruoloUtente = "";
            }
            
            if (emailUtente == null) {
                emailUtente = "";
            }
            
            if (scadenzaUtente == null) {
                scadenzaUtente = "";
            }
            
            
            if (attivoUtente == null) {
                attivoUtente = "";
            }
            
            if (matricola == null) {
                matricola = "";
            }
            
            if (azienda == null) {
                azienda = "";
            }
            if (codCurricul == null) {
            	codCurricul = "";
            }
            
            
            if (nominativo == null) {
            	nominativo = "";
            }
            if (dataNascita == null) {
            	dataNascita = "";
            }
            if (cittaNascita == null) {
            	cittaNascita = "";
            }
            if (provNascita == null) {
            	provNascita = "";
            }
            if (indResidenza == null) {
            	indResidenza = "";
            }
            if (cittaResidenza == null) {
            	cittaResidenza = "";
            }
            if (provResidenza == null) {
            	provResidenza = "";
            }
            if (codFiscale == null) {
            	codFiscale = "";
            }
            
            if (approvaFPS == null) {
            	approvaFPS = "";
            }
            
            if (pwdScaduta == null) {
            	pwdScaduta = "";
            }
            
            if (approvaRendicontazioni == null) {
            	approvaRendicontazioni = "";
            }
            
            if (idDipendente == null) {
            	idDipendente = "";
            }

            session.setAttribute("USER_COGNOME", cognome);
            session.setAttribute("USER_NOME", nome);
            session.setAttribute("ID_CODICE", idCodice);
            session.setAttribute("USERID", userId);
            session.setAttribute("RUOLO_SESSIONE", ruoloUtente);
            session.setAttribute("USER_MAIL", emailUtente);
            session.setAttribute("DATA_SC", scadenzaUtente);
            session.setAttribute("ATTIVO", attivoUtente);
            session.setAttribute("MATRICOLA", matricola);
            session.setAttribute("AZIENDA_CEDOLINI", azienda);
            session.setAttribute("NOMINATIVO_SESSIONE", nominativo);
            session.setAttribute("DATA_NASCITA_SESSIONE", dataNascita);
            session.setAttribute("CITTA_NASCITA_SESSIONE", cittaNascita);
            session.setAttribute("PROV_NASCITA_SESSIONE", provNascita);
            session.setAttribute("INDIR_RESIDENZA_SESSIONE", indResidenza);
            session.setAttribute("CITTA_RESIDENZA_SESSIONE", cittaResidenza);
            session.setAttribute("PROV_RESIDENZA_SESSIONE", provResidenza);
            session.setAttribute("COD_FISCALE_SESSIONE", codFiscale);
            session.setAttribute("APPROVA_FPS_SESSIONE", approvaFPS);
            session.setAttribute("APPROVA_RENDICONTAZIONI_SESSIONE", approvaRendicontazioni);
            session.setAttribute("PWD_SCADUTA", pwdScaduta);
            session.setAttribute("COD_CURRICUL_SESSIONE", codCurricul);
            session.setAttribute("ID_DIPENDENTE_SESSIONE", idDipendente);
            
            
            if (scadenzaUtente.equals(Utils.getStringDataOggi())){
            	session.setAttribute("ATTIVO", "N");
            }
            
            String dirAZIENDA = _applicationSrv.getRoot()+"areadocumenti/"+azienda+"/";
	        new File(dirAZIENDA).mkdir();
	        String dirMATRICOLA = _applicationSrv.getRoot()+"areadocumenti/"+azienda+"/"+matricola+"/";
	        new File(dirMATRICOLA).mkdir();

            dataSet.close();

        } catch (Throwable t) {
            AppCrash ac = new AppCrash(t);
            throw ac;
        } finally {
            if (dataSet != null) {
                try {
                    dataSet.close();
                } catch (AppCrash ac) {
                    ac.logContext("FunctionWebApp_base", "Errore nella close del dataset");
                }
            }
        }
    }
	
	protected void saveMenuInSession(SsbServletRequest req, HttpSession session) throws AppCrash {

		DataSet_itf dataSet = null;

		String user = getSessionUser(session);
		String ruolo = getSessionRole(session);

		try {
			if (user == null || user.equals("")) {
				user = null;
				return;
			}

			if (ruolo == null || ruolo.equals("")) {
				ruolo = null;
				return;
			}

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("RUOLO_SESSIONE", ruolo);

			DataSetFactory dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", DATASET_MENU_AREE_NAME);
			dataSet.setParam(params);
			dataSet.open();

			ArrayList<MenuItem> menuAree = new ArrayList<MenuItem>();

			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				String menuId = dbRow.getField("CODICE").toString().trim();
				String itemLevel = dbRow.getField("ORDINE").toString().trim();
				String caption = dbRow.getField("DESCRIZIONE").toString().trim();
				String url = dbRow.getField("LINK").toString().trim();
				String colore = dbRow.getField("COLORE").toString().trim();
				String dimensione = dbRow.getField("DIMENSIONE").toString().trim();
				String immagine = dbRow.getField("IMMAGINE").toString().trim();

				MenuItem menuItem = new MenuItem();
				menuItem.setMenuId(menuId);
				menuItem.setItemLevel(itemLevel);
				menuItem.setCaption(caption);
				menuItem.setURL(url);
				menuItem.setCOLORE(colore);
				menuItem.setDIMENSIONE(dimensione);
				menuItem.setIMMAGINE(immagine);

				menuAree.add(menuItem);
			}

			dataSet.close();

			session.setAttribute("voci_menu", menuAree);

			/****************************/

			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", DATASET_MENU_AREE_DETT_NAME);
			dataSet.setParam(params);
			dataSet.open();

			ArrayList<MenuItem> menuAreeDett = new ArrayList<MenuItem>();

			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				String menuId = dbRow.getField("CODICE").toString().trim();
				String itemLevel = dbRow.getField("ORDINE").toString().trim();
				String caption = dbRow.getField("DESCRIZIONE").toString().trim();
				String url = dbRow.getField("LINK").toString().trim();
				String colore = dbRow.getField("COLORE").toString().trim();
				String dimensione = dbRow.getField("DIMENSIONE").toString().trim();
				String immagine = dbRow.getField("IMMAGINE").toString().trim();

				MenuItem menuItem = new MenuItem();
				menuItem.setMenuId(menuId);
				menuItem.setItemLevel(itemLevel);
				menuItem.setCaption(caption);
				menuItem.setURL(url);
				menuItem.setCOLORE(colore);
				menuItem.setDIMENSIONE(dimensione);
				menuItem.setIMMAGINE(immagine);

				menuAreeDett.add(menuItem);
			}

			dataSet.close();

			session.setAttribute("dett_voci_menu", menuAreeDett);

			/****************************/

			dsFactory = DataSetFactory.getInstance();
			dataSet = dsFactory.makeDataSet("", DATASET_MENU_AREE_SUB_DETT_NAME);
			dataSet.setParam(params);
			dataSet.open();

			ArrayList<MenuItem> menuAreeDettSub = new ArrayList<MenuItem>();

			while (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				String menuId = dbRow.getField("CODICE").toString().trim();
				String itemLevel = dbRow.getField("ORDINE").toString().trim();
				String caption = dbRow.getField("DESCRIZIONE").toString().trim();
				String url = dbRow.getField("LINK").toString().trim();
				String colore = dbRow.getField("COLORE").toString().trim();
				String dimensione = dbRow.getField("DIMENSIONE").toString().trim();
				String immagine = dbRow.getField("IMMAGINE").toString().trim();

				MenuItem menuItem = new MenuItem();
				menuItem.setMenuId(menuId);
				menuItem.setItemLevel(itemLevel);
				menuItem.setCaption(caption);
				menuItem.setURL(url);
				menuItem.setCOLORE(colore);
				menuItem.setDIMENSIONE(dimensione);
				menuItem.setIMMAGINE(immagine);

				menuAreeDettSub.add(menuItem);
			}

			dataSet.close();

			session.setAttribute("dett_sub_voci_menu", menuAreeDettSub);

		} catch (Throwable t) {
			AppCrash ac = new AppCrash(t);
			throw ac;
		} finally {
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (AppCrash ac) {
					ac.logContext("FunctionWelfareManagement", "Errore nella close del dataset");
				}
			}
		}

	}

	@Override
	protected HashMap<String, Object> setCommonTags(SsbServletRequest req, UserSecurityInfo userInfo) throws AppCrash {

		HashMap<String, Object> templateData = super.setCommonTags(req, userInfo);

		String tagIncludedFrameTablet = Config.GetInstance().getProperty("Page.DefaultMenuTabletName");
		templateData.put("INCLUDED_FRAME_TABLET", tagIncludedFrameTablet);

		return templateData;

	}

	@Override
	protected void setSessionProperties(SsbServletRequest req) throws AppCrash {
		HttpSession session = req.getSession(true);
		session.setAttribute("DBURL", Config.GetInstance().getProperty("DB.ConnectionURL"));

	}

}
