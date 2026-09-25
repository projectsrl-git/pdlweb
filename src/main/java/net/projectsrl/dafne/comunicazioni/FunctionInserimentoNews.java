
package net.projectsrl.dafne.comunicazioni;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.misc.Config;
import net.project.misc.Util;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.frame.SsbServletResponse;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.dafne.db.NewsAziendeDAO;
import net.projectsrl.dafne.db.NewsDAO;
import net.projectsrl.dafne.db.NewsProfiliDAO;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.mail.DeferredMailSender;
import net.projectsrl.mail.MyAuthenticator;
import net.projectsrl.mail.SendSMTPMail;
import net.projectsrl.webapp.core.FunctionAjaxForm_base;

/**
 * FunctionInserimentoNews
 */
public class FunctionInserimentoNews extends FunctionAjaxForm_base<NewsDAO> {
	
	 private static final String DATASET_NEWS_LISTA = "DSNewsLista";
	 private static final String DATASET_NEWS_MAIL = "DSNewsMail";

    public FunctionInserimentoNews(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
    }

    @Override
    public void mostra(SsbServletRequest req, SsbServletResponse res, UserSecurityInfo userInfo) throws AppCrash {

        Map<String, Object> templateData = createMapFromRequest(req, userInfo);

        if (isAnInsert(req)) {
            templateData.put(NewsDAO.ID_NEWS, "");
        } else {
            PjNDAO_base news = new NewsDAO();
            String idNews = req.getField(NewsDAO.ID_NEWS);
            news.setAttribute(NewsDAO.ID_NEWS, idNews);
            ErrDetector.GetInstance().preCond(news.retrieve(), NewsDAO.ID_NEWS + " not found");
            news.setMapFromAttributes(templateData);
            templateData.put(DafneCostanti_itf.PROFILO_MULTIPLO, new NewsProfiliDAO().getSelectedCodeList(idNews));
            templateData.put(DafneCostanti_itf.AZIENDE_MULTIPLE, new NewsAziendeDAO().getSelectedCodeList(idNews));
        }

        _applicationSrv.displayPage(getPageName(), templateData, setPageDatasetParam(getPageName(), req, templateData),
                res);
    }


    @Override
    protected boolean isAnInsert(SsbServletRequest req) {

        return Util.IsEmpty(req.getField(NewsDAO.ID_NEWS));
    }
    
    @Override
    protected void sendResponseJSON(SsbServletResponse res, boolean result, String title, String message,
    		NewsDAO formDao) {

        try {
            PrintWriter out = res.getWriter();

            Integer id = (Integer) formDao.getAttribute(NewsDAO.ID_NEWS);


            String nr = (String) formDao.getAttribute(NewsDAO.TITOLO);
            String resultString = "{\"result\":" + result + ",\"title\":'" + title + "',\"message\":'" + message
                    + "',\"id\":" + id + ",\"nr\":'" + nr + "',\"aifa\":false}";
            
            inviaMail(id);
            
            out.println(resultString);
            out.close();

        } catch (Throwable e) {
            AppCrash ac = new AppCrash(e);
            ac.logContext(this.getClass().getName(), "errore writing succesful response");
        }
    }
    
    
    private String[] getListe(Integer id) throws AppCrash {

        String[] dati = new String[4];
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_NEWS_LISTA);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_NEWS", id.toString());
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                dati[0] = (String) dbRow.getField("LISTA_PROFILI");
                dati[1] = (String) dbRow.getField("LISTA_AZIENDE");
                dati[2] = (String) dbRow.getField("TITOLO");
                dati[3] = (String) dbRow.getField("DESCRIZIONE");
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
    
    
    protected void inviaMail(Integer id) throws AppCrash {
    	

        String from = Config.GetInstance().getProperty("mail.from.pdlweb", "noreply@pdlweb.net");
        String documento="";
        
        String listaProfili=getListe(id)[0];
    	String listaAziende=getListe(id)[1];
    	String oggetto=getListe(id)[2];
    	String corpo=getListe(id)[3];
    	
    	String destinatario="";
    	
    	DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_NEWS_MAIL);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("LISTA_PROFILI", listaProfili);
            params.put("LISTA_AZIENDE", listaAziende);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                destinatario = (String) dbRow.getField("EMAIL");
                sendMail(from, oggetto, corpo, documento, destinatario);
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
    	   
    }

	private void sendMail(String from, String oggetto, String corpo, String documento, String destinatario) {
		SendSMTPMail sendSMTPMail = new SendSMTPMail();
        sendSMTPMail.setFrom(from);
        sendSMTPMail.setSubject(oggetto);
        sendSMTPMail.setBody(corpo);
        sendSMTPMail.setTo(destinatario);
        sendSMTPMail.setBcc(from);
        sendSMTPMail.setServer(Config.GetInstance().getProperty("mail.SMTPHost.pdlweb"));
        try {
            MyAuthenticator auth = null;
            if (!Config.GetInstance().getProperty("mail.SMTPHost.user.pdlweb", "").equals("")) {
                auth = new MyAuthenticator();
            }
            if (documento.equals("")) {
                sendSMTPMail.prepareMail(auth, false, _applicationSrv.getRoot(), documento);
            } else {
                sendSMTPMail.prepareMail(auth, true, _applicationSrv.getRoot(), documento);
            }

            DeferredMailSender.getInstance().offer(sendSMTPMail);

        } catch (Throwable e) {
            new AppCrash(e);
        }
	}


}
