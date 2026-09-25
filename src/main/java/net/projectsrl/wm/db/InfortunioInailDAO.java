package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;



/**
 * Classe che rappresenta la tabella CURRICULUM Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */


public class InfortunioInailDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "INFORTUNIO_INAIL";

	public static final String TAGGANCIO = "TAGGANCIO";
	public static final String INAIL_DATA_SPEDIZIONE = "INAIL_DATA_SPEDIZIONE";
	public static final String INAIL_SEDE1 = "INAIL_SEDE1";
	public static final String INAIL_SEDE2 = "INAIL_SEDE2";
	public static final String INAIL_SEDE3 = "INAIL_SEDE3";
	
	public static final String SL_ID_DIPENDENTE = "SL_ID_DIPENDENTE";
	public static final String SL_COGNOME = "SL_COGNOME";
	public static final String SL_NOME = "SL_NOME";
	public static final String SL_CODFIS = "SL_CODFIS";
	public static final String SL_ID_NAZIONE = "SL_ID_NAZIONE";
	public static final String SL_CITTADINANZA = "SL_CITTADINANZA";
	public static final String SL_SESSO = "SL_SESSO";
	public static final String SL_CIVILE = "SL_CIVILE";
	public static final String SL_NASCITA = "SL_NASCITA";
	public static final String SL_PROV = "SL_PROV";
	public static final String SL_DATA_NASCITA = "SL_DATA_NASCITA";
	public static final String SL_DATA_DECESSO = "SL_DATA_DECESSO";
	public static final String SL_RESIDENZA = "SL_RESIDENZA";
	public static final String SL_NAZIONE = "SL_NAZIONE";
	public static final String SL_COMUNE = "SL_COMUNE";
	public static final String SL_PROV_RES = "SL_PROV_RES";
	public static final String SL_CAP_RES = "SL_CAP_RES";
	public static final String SL_ISTAT_RES = "SL_ISTAT_RES";
	public static final String SL_ASL_RES = "SL_ASL_RES";
	public static final String SL_DOMICILIO = "SL_DOMICILIO";
	public static final String SL_NAZIONE_DOM = "SL_NAZIONE_DOM";
	public static final String SL_COMUNE_DOM = "SL_COMUNE_DOM";
	public static final String SL_PROV_DOM = "SL_PROV_DOM";
	public static final String SL_CAP_DOM = "SL_CAP_DOM";
	public static final String SL_ISTAT_DOM = "SL_ISTAT_DOM";
	public static final String SL_ASL_DOM = "SL_ASL_DOM";
	public static final String SL_PREF_INTERNAZ = "SL_PREF_INTERNAZ";
	public static final String SL_TEL_ABITAZ = "SL_TEL_ABITAZ";
	public static final String SL_PREF_INTERNAZ_C = "SL_PREF_INTERNAZ_C";
	public static final String SL_TEL_CELLULARE = "SL_TEL_CELLULARE";
	public static final String SL_EMAIL = "SL_EMAIL";
	public static final String SL_PEC = "SL_PEC";
	public static final String SL_STUDENTE = "SL_STUDENTE";
	public static final String SL_PARENTE = "SL_PARENTE";
	public static final String SL_CARICO = "SL_CARICO";
	public static final String SL_TUTELA = "SL_TUTELA";
	public static final String SL_LAV = "SL_LAV";
	public static final String SL_CONTR = "SL_CONTR";
	public static final String SL_DATA_ASSUNZIONE = "SL_DATA_ASSUNZIONE";
	public static final String SL_DATA_FINE = "SL_DATA_FINE";
	public static final String SL_CCNL_SET = "SL_CCNL_SET";
	public static final String SL_CCNL_CAT = "SL_CCNL_CAT";
	public static final String SL_QUALIFICA = "SL_QUALIFICA";
	public static final String SL_VP_ISTAT = "SL_VP_ISTAT";
	
	public static final String SD_INDUSTRIA = "SD_INDUSTRIA";
	public static final String SD_FISCALE = "SD_FISCALE";
	public static final String SD_COGNOMENOME = "SD_COGNOMENOME";
	public static final String SD_CODICEDITTA= "SD_CODICEDITTA";
	public static final String SD_POSIZIONE= "SD_POSIZIONE";
	public static final String SD_SETTORE = "SD_SETTORE";
	public static final String SD_POLIZZA = "SD_POLIZZA";
	public static final String SD_VOCETARIFFA = "SD_VOCETARIFFA";
	public static final String SD_PUBBLICHE = "SD_PUBBLICHE";
	public static final String SD_CFAS = "SD_CFAS";
	public static final String SD_AS = "SD_AS";
	public static final String SD_CFS = "SD_CFS";
	public static final String SD_STRUTTURA = "SD_STRUTTURA";
	public static final String SD_INAIL = "SD_INAIL";
	public static final String SD_AGRICOLTURA = "SD_AGRICOLTURA";
	public static final String SD_A_CF = "SD_A_CF";
	public static final String SD_A_COGNOME= "SD_A_COGNOME";
	public static final String SD_A_MATRICOLA = "SD_A_MATRICOLA";
	public static final String SD_SERVIZI = "SD_SERVIZI";
	public static final String SD_S_COGNOME = "SD_S_COGNOME";
	public static final String SD_S_NOME= "SD_S_NOME";
	public static final String SD_S_CODFISCALE = "SD_S_CODFISCALE";
	public static final String SD_S_INPS = "SD_S_INPS";
	public static final String SD_OCCASIONALE = "SD_OCCASIONALE";
	public static final String SD_O_COGNOME = "SD_O_COGNOME";
	public static final String SD_O_NOME= "SD_O_NOME";
	public static final String SD_O_CODFISCALE = "SD_O_CODFISCALE";
	public static final String SD_IND_DATLAV = "SD_IND_DATLAV";
	public static final String SD_IND_NAZIONE = "SD_IND_NAZIONE";
	public static final String SD_IND_COMUNE = "SD_IND_COMUNE";
	public static final String SD_IND_PROV = "SD_IND_PROV";
	public static final String SD_IND_CAP = "SD_IND_CAP";
	public static final String SD_IND_ISTAT = "SD_IND_ISTAT";
	public static final String SD_IND_ASL = "SD_IND_ASL";
	public static final String SD_IND_PREF = "SD_IND_PREF";
	public static final String SD_IND_TEL = "SD_IND_TEL";
	public static final String SD_IND_MAIL = "SD_IND_MAIL";
	public static final String SD_IND_PEC = "SD_IND_PEC";
	public static final String SD_UNITA_PROD = "SD_UNITA_PROD";
	public static final String SD_REGINF = "SD_REGINF";
	public static final String SD_UP_DATLAV = "SD_UP_DATLAV";
	public static final String SD_UP_NAZIONE = "SD_UP_NAZIONE";
	public static final String SD_UP_COMUNE = "SD_UP_COMUNE";
	public static final String SD_UP_PROV = "SD_UP_PROV";
	public static final String SD_UP_CAP = "SD_UP_CAP";
	public static final String SD_UP_ISTAT = "SD_UP_ISTAT";
	public static final String SD_UP_ASL = "SD_UP_ASL";
	public static final String SD_UP_PREF = "SD_UP_PREF";
	public static final String SD_UP_TEL = "SD_UP_TEL";
	public static final String SD_UP_MAIL = "SD_UP_MAIL";
	public static final String SD_UP_PEC = "SD_UP_PEC";
	public static final String SD_COR_DATLAV = "SD_COR_DATLAV";
	public static final String SD_COR_NAZIONE = "SD_COR_NAZIONE";
	public static final String SD_COR_COMUNE = "SD_COR_COMUNE";
	public static final String SD_COR_PROV = "SD_COR_PROV";
	public static final String SD_COR_CAP = "SD_COR_CAP";
	public static final String SD_COR_ISTAT = "SD_COR_ISTAT";
	public static final String SD_COR_ASL = "SD_COR_ASL";
	public static final String SD_COR_PREF = "SD_COR_PREF";
	public static final String SD_COR_TEL = "SD_COR_TEL";
	public static final String SD_COR_MAIL = "SD_COR_MAIL";
	public static final String SD_COR_PEC = "SD_COR_PEC";
	public static final String SD_REPARTO = "SD_REPARTO";
	public static final String SD_LAVORAZIONE = "SD_LAVORAZIONE";
	public static final String SD_ASSEGNO = "SD_ASSEGNO";
	public static final String SD_ACCREDITO = "SD_ACCREDITO";
	public static final String SD_IBAN = "SD_IBAN";
	
	public static final String SI_LUOGO = "SI_LUOGO";
	public static final String SI_INDIRIZZO = "SI_INDIRIZZO";	
	public static final String SI_NAZIONE = "SI_NAZIONE";
	public static final String SI_COMUNE = "SI_COMUNE";
	public static final String SI_PROVINCIA = "SI_PROVINCIA";	
	public static final String SI_CAP = "SI_CAP";
	public static final String SI_ISTAT = "SI_ISTAT";
	public static final String SI_ASL = "SI_ASL";
	public static final String SI_INF = "SI_INF";
	public static final String SI_INC_M = "SI_INC_M";
	public static final String SI_INC_ST = "SI_INC_ST";
	public static final String SI_INC_FE = "SI_INC_FE";
	public static final String SI_INC_AE = "SI_INC_AE";
	public static final String SI_INC_NA = "SI_INC_NA";
	public static final String SI_INC_ALTRO = "SI_INC_ALTRO";
	public static final String SI_ALTRA = "SI_ALTRA";
	public static final String SI_CODFIS = "SI_CODFIS";
	public static final String SI_DENOMINAZIONE = "SI_DENOMINAZIONE";
	public static final String SI_APPALTO = "SI_APPALTO";
	public static final String SI_DATA_EVENTO = "SI_DATA_EVENTO";
	public static final String SI_FESTIVO = "SI_FESTIVO";
	public static final String SI_ORE = "SI_ORE";
	public static final String SI_ORA = "SI_ORA";
	public static final String SI_TURNO = "SI_TURNO";
	public static final String SI_INFORTUNATO = "SI_INFORTUNATO";
	public static final String SI_INF_DATA = "SI_INF_DATA";
	public static final String SI_INF_ORE = "SI_INF_ORE";
	public static final String SI_INF_FATTO = "SI_INF_FATTO";
	public static final String SI_DATPRES = "SI_DATPRES";
	public static final String SI_DESCRI = "SI_DESCRI";
	public static final String SI_PERCHE= "SI_PERCHE";
	public static final String SI_PRIMO_DATA= "SI_PRIMO_DATA";
	public static final String SI_PRIMO_RISERVATA= "SI_PRIMO_RISERVATA";
	public static final String SI_MALINF= "SI_MALINF";
	public static final String SI_PRIMO_PROGNOSI_DAL = "SI_PRIMO_PROGNOSI_DAL";
	public static final String SI_PRIMO_PROGNOSI_AL = "SI_PRIMO_PROGNOSI_AL";
	public static final String SI_ALTRO_DATA = "SI_ALTRO_DATA";
	public static final String SI_ALTRO_PROGNOSI_DAL = "SI_ALTRO_PROGNOSI_DAL";
	public static final String SI_ALTRO_PROGNOSI_AL = "SI_ALTRO_PROGNOSI_AL";
	public static final String SI_DESCINF = "SI_DESCINF";
	public static final String SI_DOVE = "SI_DOVE";
	public static final String SI_MOMENTO = "SI_MOMENTO";
	public static final String SI_CONSUETO = "SI_CONSUETO";
	public static final String SI_COSA = "SI_COSA";
	public static final String SI_IMPREVISTO = "SI_IMPREVISTO";
	public static final String SI_AVVENUTO = "SI_AVVENUTO";
	public static final String SI_ALTEZZA = "SI_ALTEZZA";
	public static final String SI_NATURA = "SI_NATURA";
	public static final String SI_SEDE = "SI_SEDE";
	public static final String SI_MORTE = "SI_MORTE";
	public static final String SI_POTEVA = "SI_POTEVA";
	public static final String SI_INABILITA = "SI_INABILITA";
	public static final String SI_DURATA = "SI_DURATA";
	public static final String SI_ESITO = "SI_ESITO";
	public static final String SI_ALTRE = "SI_ALTRE";
	public static final String SI_COMUNICAZIONE = "SI_COMUNICAZIONE";
	
	public static final String ST_P_COGNOME= "ST_P_COGNOME";					
	public static final String ST_P_NOME= "ST_P_NOME";		
	public static final String ST_P_INDIRIZZO = "ST_P_INDIRIZZO";	
	public static final String ST_P_PREFISSO = "ST_P_PREFISSO";	
	public static final String ST_P_TELEFONO = "ST_P_TELEFONO";	
	public static final String ST_P_NAZIONE= "ST_P_NAZIONE";		
	public static final String ST_P_COMUNE = "ST_P_COMUNE";	
	public static final String ST_P_PROVINCIA = "ST_P_PROVINCIA";	
	public static final String ST_P_CAP = "ST_P_CAP";	
	public static final String ST_P_ISTAT= "ST_P_ISTAT";	
	public static final String ST_S_COGNOME= "ST_S_COGNOME";		
	public static final String ST_S_NOME= "ST_S_NOME";		
	public static final String ST_S_INDIRIZZO = "ST_S_INDIRIZZO";	
	public static final String ST_S_PREFISSO = "ST_S_PREFISSO";	
	public static final String ST_S_TELEFONO = "ST_S_TELEFONO";	
	public static final String ST_S_NAZIONE= "ST_S_NAZIONE";		
	public static final String ST_S_COMUNE = "ST_S_COMUNE";	
	public static final String ST_S_PROVINCIA = "ST_S_PROVINCIA";	
	public static final String ST_S_CAP = "ST_S_CAP";		
	public static final String ST_S_ISTAT = "ST_S_ISTAT";
	
	public static final String SM_PV_TARGA= "SM_PV_TARGA";					
	public static final String SM_PV_COMPAGNIA= "SM_PV_COMPAGNIA";		
	public static final String SM_PV_AUTORITA = "SM_PV_AUTORITA";	
	public static final String SM_PC_COGNOME= "SM_PC_COGNOME";					
	public static final String SM_PC_NOME= "SM_PC_NOME";		
	public static final String SM_PC_INDIRIZZO = "SM_PC_INDIRIZZO";	
	public static final String SM_PC_PREFISSO = "SM_PC_PREFISSO";	
	public static final String SM_PC_TELEFONO = "SM_PC_TELEFONO";	
	public static final String SM_PC_NAZIONE= "SM_PC_NAZIONE";		
	public static final String SM_PC_COMUNE = "SM_PC_COMUNE";	
	public static final String SM_PC_PROVINCIA = "SM_PC_PROVINCIA";	
	public static final String SM_PC_CAP = "SM_PC_CAP";	
	public static final String SM_PC_ISTAT= "SM_PC_ISTAT";	
	public static final String SM_SC_COGNOME= "SM_SC_COGNOME";		
	public static final String SM_SC_NOME= "SM_SC_NOME";		
	public static final String SM_SC_INDIRIZZO = "SM_SC_INDIRIZZO";	
	public static final String SM_SC_PREFISSO = "SM_SC_PREFISSO";	
	public static final String SM_SC_TELEFONO = "SM_SC_TELEFONO";	
	public static final String SM_SC_NAZIONE= "SM_SC_NAZIONE";		
	public static final String SM_SC_COMUNE = "SM_SC_COMUNE";	
	public static final String SM_SC_PROVINCIA = "SM_SC_PROVINCIA";	
	public static final String SM_SC_CAP = "SM_SC_CAP";		
	public static final String SM_SC_ISTAT = "SM_SC_ISTAT";
	public static final String SM_SV_TARGA= "SM_SV_TARGA";					
	public static final String SM_SV_COMPAGNIA= "SM_SV_COMPAGNIA";		
	public static final String SM_SV_AUTORITA = "SM_SV_AUTORITA";	
	public static final String SM_PCP_COGNOME= "SM_PCP_COGNOME";					
	public static final String SM_PCP_NOME= "SM_PCP_NOME";		
	public static final String SM_PCP_INDIRIZZO = "SM_PCP_INDIRIZZO";	
	public static final String SM_PCP_PREFISSO = "SM_PCP_PREFISSO";	
	public static final String SM_PCP_TELEFONO = "SM_PCP_TELEFONO";	
	public static final String SM_PCP_NAZIONE= "SM_PCP_NAZIONE";		
	public static final String SM_PCP_COMUNE = "SM_PCP_COMUNE";	
	public static final String SM_PCP_PROVINCIA = "SM_PCP_PROVINCIA";	
	public static final String SM_PCP_CAP = "SM_PCP_CAP";	
	public static final String SM_PCP_ISTAT= "SM_PCP_ISTAT";	
	public static final String SM_SCP_COGNOME= "SM_SCP_COGNOME";		
	public static final String SM_SCP_NOME= "SM_SCP_NOME";		
	public static final String SM_SCP_INDIRIZZO = "SM_SCP_INDIRIZZO";	
	public static final String SM_SCP_PREFISSO = "SM_SCP_PREFISSO";	
	public static final String SM_SCP_TELEFONO = "SM_SCP_TELEFONO";	
	public static final String SM_SCP_NAZIONE= "SM_SCP_NAZIONE";		
	public static final String SM_SCP_COMUNE = "SM_SCP_COMUNE";	
	public static final String SM_SCP_PROVINCIA = "SM_SCP_PROVINCIA";	
	public static final String SM_SCP_CAP = "SM_SCP_CAP";		
	public static final String SM_SCP_ISTAT = "SM_SCP_ISTAT";
	public static final String SM_PCP_RAGIONE = "SM_PCP_RAGIONE";
	public static final String SM_SCP_RAGIONE = "SM_SCP_RAGIONE";
	
	public static final String SR_ORARIA = "SR_ORARIA";						
	public static final String SR_ORE = "SR_ORE";     						
	public static final String SR_GIORNALIERA = "SR_GIORNALIERA";					
	public static final String SR_MENSILE = "SR_MENSILE";   						
	public static final String SR_CONV = "SR_CONV";    						
	public static final String SR_CONVART = "SR_CONVART"; 						
	public static final String SR_VOUCHER = "SR_VOUCHER";   						
	public static final String SR_IMPORTO = "?ISR_IMPORTO"; 						
	public static final String SR_MENSILE_IMPORTO = "?ISR_MENSILE_IMPORTO";   						
	public static final String SR_DATADAL = "SR_DATADAL";   						
	public static final String SR_STRAORD = "?ISR_STRAORD";   						
	public static final String SR_FESTIVITA = "?ISR_FESTIVITA"; 						
	public static final String SR_PRESTAZIONI = "?ISR_PRESTAZIONI";					
	public static final String SR_DIARIA = "?ISR_DIARIA";  						
	public static final String SR_INDENNITA = "?ISR_INDENNITA"; 						
	public static final String SR_FESTIVITA_S = "?ISR_FESTIVITA_S"; 						
	public static final String SR_TRED_PERC = "?ISR_TRED_PERC"; 						
	public static final String SR_TRED_IMPORTO = "?ISR_TRED_IMPORTO"; 						
	public static final String SR_PREMIO_PERC = "?ISR_PREMIO_PERC"; 						
	public static final String SR_PREMIO_IMPORTO = "?ISR_PREMIO_IMPORTO";						
	public static final String SR_ALTRA_PERC = "?ISR_ALTRA_PERC";  						
	public static final String SR_ALTRA_IMPORTO = "?ISR_ALTRA_IMPORTO";						
	public static final String SR_FERIE_PERC = "?ISR_FERIE_PERC";  						
	public static final String SR_FERIE_GIORNI = "SR_FERIE_GIORNI";						
	public static final String SR_MAGG_PERC = "?ISR_MAGG_PERC";					
	public static final String SR_DURATA = "SR_DURATA";					
	public static final String SR_RETRIB_ORA = "?ISR_RETRIB_ORA";						
	public static final String SR_ALTRO_1 = "SR_ALTRO_1";   						
	public static final String SR_ALTRO_1_ORE = "SR_ALTRO_1_ORE";					
	public static final String SR_ALTRO_1_RETRIB = "?ISR_ALTRO_1_RETRIB";						
	public static final String SR_ALTRO_2 = "SR_ALTRO_2"; 						
	public static final String SR_ALTRO_2_ORE = "SR_ALTRO_2_ORE";					
	public static final String SR_ALTRO_2_RETRIB = "?ISR_ALTRO_2_RETRIB";						
	public static final String SR_ALTRO_3 = "SR_ALTRO_3"; 						
	public static final String SR_ALTRO_3_ORE = "SR_ALTRO_3_ORE";					
	public static final String SR_ALTRO_3_RETRIB = "?ISR_ALTRO_3_RETRIB";						
	public static final String SR_ALTRO_4 = "SR_ALTRO_4"; 						
	public static final String SR_ALTRO_4_ORE = "SR_ALTRO_4_ORE";					
	public static final String SR_ALTRO_4_RETRIB = "?ISR_ALTRO_4_RETRIB";
	
	public static final String FA_DATORE = "FA_DATORE";
	public static final String FA_DELEGATO = "FA_DELEGATO";
	public static final String FA_MANDATARIO = "FA_MANDATARIO";
	public static final String FA_COGNOME = "FA_COGNOME";
	public static final String FA_NOME = "FA_NOME";
	public static final String FA_CODFIS = "FA_CODFIS";
	public static final String FA_NATO = "FA_NATO";
	public static final String FA_DATA_NASCITA = "FA_DATA_NASCITA";
	public static final String FA_INDIRIZZO = "FA_INDIRIZZO";
	public static final String FA_NAZIONE = "FA_NAZIONE";
	public static final String FA_COMUNE = "FA_COMUNE";
	public static final String FA_PROV = "FA_PROV";
	public static final String FA_CAP = "FA_CAP";
	public static final String FA_ISTAT = "FA_ISTAT";
	public static final String FA_PREFISSO = "FA_PREFISSO";
	public static final String FA_TEL = "FA_TEL";
	public static final String FA_MAIL = "FA_MAIL";
	public static final String FA_PEC = "FA_PEC";
	public static final String FA_ALLEGATI = "FA_ALLEGATI";
	public static final String FA_NOTE = "FA_NOTE";
	public static final String FA_DATA = "FA_DATA";
	
	public static final String AZIENDA_TENDINA = "AZIENDA_TENDINA";	 

	

	public InfortunioInailDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(TAGGANCIO);
	}


	public InfortunioInailDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(TAGGANCIO);
	}

	public InfortunioInailDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(TAGGANCIO);

	}

}
