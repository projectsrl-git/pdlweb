
package net.projectsrl.pdlweb.pdl.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.errors.ParamCrash;
import net.projectsrl.webapp.core.WebAppUtils;


public class PdlWebUtils {

	public static void updatePdlStatus() throws ParamCrash, AppCrash {
		
		
		String sqlStatement1 = "update pdl set stato='OPE' where stato<>'OPE' and id_pdl in (select id_pdl from pdl where ts_fine_turno_attivazione<now() and stato='ACT')";
        ErrDetector.GetInstance().param(sqlStatement1 != null, "sqlStatement null");
        WebAppUtils.executeQuery(sqlStatement1);
        
        String sqlStatement2 = "update pdl set stato='OPE' where stato<>'OPE' and id_pdl in (SELECT id_pdl from pdl where ts_fine_turno_attivazione<now() and stato='SUS' "
        		+ "and (EXTRACT(epoch from age( to_timestamp(dt_prima_attivazione, 'YYYY/MM/DD'), now())) / 86400)::int>-30 "
        		+ "and ((EXTRACT(epoch from age( to_timestamp(dt_pdl, 'YYYY/MM/DD'), now())) / 86400)::int>-30 or dt_prima_attivazione<>'' or dt_prima_attivazione is not null))";
        ErrDetector.GetInstance().param(sqlStatement2 != null, "sqlStatement null");
        WebAppUtils.executeQuery(sqlStatement2);
        
        
        // sqlStatement3 aggiornato il 29-02-2024
        String sqlStatement3NON_USATO = "update pdl set MOTIVO_SCADENZA='Scaduto per scadenza validita''',stato='EXP' where stato<>'EXP' and id_pdl in (SELECT id_pdl from pdl where"
        		+ "(stato='SUS' or stato='OPE' or stato='ACT') "
        		+ "and ((EXTRACT(epoch from age( to_timestamp(dt_prima_attivazione, 'YYYY/MM/DD'), now())) / 86400)::int<-30 or dt_prima_attivazione='' or dt_prima_attivazione is null) "
        		+ "and (EXTRACT(epoch from age( to_timestamp(dt_pdl, 'YYYY/MM/DD'), now())) / 86400)::int<-30 "
        		+ "and ((EXTRACT(epoch from age( to_timestamp(dt_apertura, 'YYYY/MM/DD'), now())) / 86400)::int<-30 or dt_apertura='' or dt_apertura is null))";
        
        
//        String sqlStatement3 = "update pdl set MOTIVO_SCADENZA='Scaduto per scadenza validita''',stato='EXP' where stato<>'EXP' and id_pdl in (SELECT id_pdl from pdl where"
//        		+ "(stato='SUS' or stato='OPE' or stato='ACT') "
//        		+ "and EXTRACT(EPOCH FROM (now() - TS_ATTIVAZIONE)/ 86400)::int>30) ";
        
        String sqlStatement3 = "update pdl set MOTIVO_SCADENZA='Scaduto per scadenza validita''',stato='EXP' where stato<>'EXP' and id_pdl in (SELECT id_pdl from pdl where"
        		+ "(stato='SUS' or stato='OPE' or stato='ACT') "
        		+ "and ((EXTRACT(epoch from age( to_timestamp(dt_prima_attivazione, 'YYYY/MM/DD'), now())) / 86400)::int<-30 and dt_prima_attivazione<>'') "
        		+ "and (EXTRACT(epoch from age( to_timestamp(dt_pdl, 'YYYY/MM/DD'), now())) / 86400)::int<-30)";
        
        
        //ErrDetector.GetInstance().param(sqlStatement3 != null, "sqlStatement null");
        //WebAppUtils.executeQuery(sqlStatement3);
        
	}
	
	
	
	 public static String getMailFromIdUtente(String idUtente) throws AppCrash {
			DataSet_itf dataSet = null;
			 DataSetFactory dsFactory = DataSetFactory.getInstance();
			 String destinatari="";

			    try {
			        dataSet = dsFactory.makeDataSet("", "DSUtenti");

			        HashMap<String, String> param = new HashMap<String, String>();
			        param.put("WHERECONDITION", " WHERE ID_UTENTE="+idUtente);
			        param.put("WHERECONDITION_AZIENDE", " IS NOT NULL");
			        dataSet.setParam(param);
			        dataSet.open();

			        while (dataSet.hasMoreElements()) {
			            Row_itf dbRow = (Row_itf) dataSet.nextElement();
			            	destinatari = destinatari+dbRow.getField("EMAIL").toString()+";";
			            }
			        
			    } catch (AppCrash ac) {
			        ac.logContext("",
			                "Errore nella ricerca dell'ultimo progressivo del dataset " + "DSRCAAPNumber");
			        throw ac;
			    } finally {
			        // chiude il dataset per il conteggio degli elementi trovati
			        if (dataSet != null) {
			            try {
			                dataSet.close();
			            } catch (Throwable t) {
			                AppCrash ac = new AppCrash(t);
			                ac.logContext("", "Errore nella close del dataset " + "DSRCAAPNumber");
			            }
			        }
			    }
			    return destinatari;
		}
	 
	 
	 
	 public static String getNomeCognomeFromIdUtente(String idUtente) throws AppCrash {
			DataSet_itf dataSet = null;
			 DataSetFactory dsFactory = DataSetFactory.getInstance();
			 String destinatari="";

			    try {
			        dataSet = dsFactory.makeDataSet("", "DSUtenti");

			        HashMap<String, String> param = new HashMap<String, String>();
			        param.put("WHERECONDITION", " WHERE ID_UTENTE="+idUtente);
			        param.put("WHERECONDITION_AZIENDE", " IS NOT NULL");
			        dataSet.setParam(param);
			        dataSet.open();

			        while (dataSet.hasMoreElements()) {
			            Row_itf dbRow = (Row_itf) dataSet.nextElement();
			            	destinatari = dbRow.getField("NOME_UTENTE").toString()+" "+dbRow.getField("COGNOME_UTENTE").toString();
			            }
			        
			    } catch (AppCrash ac) {
			        ac.logContext("",
			                "Errore nella ricerca dell'ultimo progressivo del dataset " + "DSRCAAPNumber");
			        throw ac;
			    } finally {
			        // chiude il dataset per il conteggio degli elementi trovati
			        if (dataSet != null) {
			            try {
			                dataSet.close();
			            } catch (Throwable t) {
			                AppCrash ac = new AppCrash(t);
			                ac.logContext("", "Errore nella close del dataset " + "DSRCAAPNumber");
			            }
			        }
			    }
			    return destinatari;
		}
	 
	 
	 
	 public static String getMailFromNominativoUtente(String nominativo) throws AppCrash {
			DataSet_itf dataSet = null;
			 DataSetFactory dsFactory = DataSetFactory.getInstance();
			 String destinatari="";

			    try {
			        dataSet = dsFactory.makeDataSet("", "DSUtenti");

			        HashMap<String, String> param = new HashMap<String, String>();
			        param.put("WHERECONDITION", " WHERE nome_cognome='"+nominativo+"'");
			        param.put("WHERECONDITION_AZIENDE", " IS NOT NULL");
			        dataSet.setParam(param);
			        dataSet.open();

			        while (dataSet.hasMoreElements()) {
			            Row_itf dbRow = (Row_itf) dataSet.nextElement();
			            	destinatari = destinatari+dbRow.getField("EMAIL").toString()+";";
			            }
			        
			    } catch (AppCrash ac) {
			        ac.logContext("",
			                "Errore nella ricerca dell'ultimo progressivo del dataset " + "DSRCAAPNumber");
			        throw ac;
			    } finally {
			        // chiude il dataset per il conteggio degli elementi trovati
			        if (dataSet != null) {
			            try {
			                dataSet.close();
			            } catch (Throwable t) {
			                AppCrash ac = new AppCrash(t);
			                ac.logContext("", "Errore nella close del dataset " + "DSRCAAPNumber");
			            }
			        }
			    }
			    return destinatari;
		}
	 
	 
	 
	 public static String getUltimaRevisione(String idPdl) throws AppCrash {
			DataSet_itf dataSet = null;
			 DataSetFactory dsFactory = DataSetFactory.getInstance();
			 String revisione="";

			    try {
			        dataSet = dsFactory.makeDataSet("", "DSUltimaRevisioneAzienda");

			        HashMap<String, String> param = new HashMap<String, String>();
			        param.put("WHERECONDITION", " where id_azienda = (select id_azienda from pdl where id_pdl="+idPdl+")");
			        dataSet.setParam(param);
			        dataSet.open();

			        while (dataSet.hasMoreElements()) {
			            Row_itf dbRow = (Row_itf) dataSet.nextElement();
			            	revisione = (dbRow.getField("REVISIONE") != null) ? dbRow.getField("REVISIONE").toString() : "";
			            }
			        
			    } catch (AppCrash ac) {
			        ac.logContext("",
			                "Errore nella ricerca dell'ultimo progressivo del dataset " + "DSRCAAPNumber");
			        throw ac;
			    } finally {
			        // chiude il dataset per il conteggio degli elementi trovati
			        if (dataSet != null) {
			            try {
			                dataSet.close();
			            } catch (Throwable t) {
			                AppCrash ac = new AppCrash(t);
			                ac.logContext("", "Errore nella close del dataset " + "DSRCAAPNumber");
			            }
			        }
			    }
			    return revisione;
		}



}
