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


public class CurriculImportDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "CURRICUL";

	public static final String TAGGANCIO = "TAGGANCIO";
	
	public static final String AZIENDA_INSERIMENTO = "AZIENDA_INSERIMENTO";

	public static final String IDNAZIONE = "IDNAZIONE";

	public static final String IDTITOLO = "IDTITOLO";

	public static final String IDSTATOCIV = "IDSTATOCIV";

	//public static final String IDSETTAZI = "IDSETTAZI";

	public static final String IDTIOPL = "IDTIOPL";

	public static final String IDFIGPROF = "IDFIGPROF";

	//public static final String IDFIGPROFC = "IDFIGPROFC";

	public static final String FIGPROFC = "FIGPROFC";

	public static final String IDSTATORIS = "IDSTATORIS";

	public static final String VALUTAZ = "VALUTAZ";

	public static final String IDANAAZI = "IDANAAZI";

	public static final String IDRIFERIM = "IDRIFERIM";

	public static final String CODICE = "CODICE";

	public static final String D_REGISTRAZ = "D_REGISTRAZ";

	public static final String COGNOME = "COGNOME";

	public static final String NOME = "NOME";

	public static final String SESSO = "SESSO";

	public static final String CODFISCALE = "CODFISCALE";

	public static final String PARTITAIVA = "PARTITAIVA";

	public static final String LOCNASCITA = "LOCNASCITA";

	public static final String D_NASCITA = "D_NASCITA";

	public static final String ETA = "?IETA";

	public static final String LOCRESIDENZ = "LOCRESIDENZ";

	public static final String INDRESIDENZ = "INDRESIDENZ";

	public static final String CAPRESIDENZ = "CAPRESIDENZ";

	public static final String FAXRES = "FAXRES";

	public static final String LOCDOMICIL = "LOCDOMICIL";

	public static final String INDDOMICIL = "INDDOMICIL";

	public static final String CAPDOMICIL = "CAPDOMICIL";

	public static final String TELDOMICIL = "TELDOMICIL";

	public static final String FAXDOM = "FAXDOM";

	public static final String TELFISSO = "TELFISSO";

	public static final String TELMOBILE = "TELMOBILE";

	public static final String EMAIL = "EMAIL";
	
	public static final String EMAIL2 = "EMAIL2";		

	public static final String ANNOTAZIONI = "ANNOTAZIONI";

	public static final String CCSOCIALI = "CCSOCIALI";

	public static final String CCORGANIZZ = "CCORGANIZZ";

	public static final String CCTECNICHE = "CCTECNICHE";

	public static final String CCINFORMATIC = "CCINFORMATIC";

	public static final String CCARTISTIC = "CCARTISTIC";

	public static final String CCALTRE = "CCALTRE";

	public static final String CCHOBBY = "CCHOBBY";

	public static final String CERTATTEST = "CERTATTEST";

	public static final String PATENTI = "PATENTI";

	public static final String ULTERINFORM = "ULTERINFORM";

	//public static final String ALLEGATI = "ALLEGATI";

	public static final String D_INIZIOESP = "D_INIZIOESP";

	public static final String ESPSPECRUOL = "ESPSPECRUOL";

	public static final String D_INIZIOEC = "D_INIZIOEC";

	public static final String ESPCOMPLESS = "ESPCOMPLESS";

	public static final String TITOLOSTUD = "TITOLOSTUD";

	public static final String SKILLINFORM = "SKILLINFORM"; 
	
	public static final String SKILLALTRO = "SKILLALTRO"; 	// Ora viene salvato il campo "Sintesi Valutazione" 
															// della sezione "PROFILO"

	public static final String TARIFFCOSTO = "?ITARIFFCOSTO";

	public static final String TARIFFRICAV = "?ITARIFFRICAV";

	public static final String ANAAZI = "ANAAZI";

	public static final String B_BLOCCATO = "B_BLOCCATO";

	public static final String UTENTEINS = "UTENTEINS";

	public static final String D_DATAINS = "D_DATAINS";

	public static final String UTENTEMOD = "UTENTEMOD";

	public static final String D_DATAMOD = "D_DATAMOD";
	
	public static final String CATPROT = "CATPROT";
	
	public static final String IDSTATOCV = "IDSTATOCV";
	
	
	public static final String IDTIOPL_INFO_ATT = "IDTIOPL_INFO_ATT";
	public static final String IDTIPOCNTR_ATT = "IDTIPOCNTR_ATT";
	public static final String RETRIB_ATT = "?IRETRIB_ATT";
	public static final String UM_RETRIB_ATT = "UM_RETRIB_ATT";	
	public static final String PREAVV_ATT = "PREAVV_ATT";
	public static final String ATTIVMANS_ISTR_ATT = "ATTIVMANS_ISTR_ATT";
	
	public static final String IDTIOPL_INFO_RIC = "IDTIOPL_INFO_RIC";
	public static final String IDTIPOCNTR_RIC = "IDTIPOCNTR_RIC";
	public static final String RETRIB_RIC = "?IRETRIB_RIC";
	public static final String UM_RETRIB_RIC = "UM_RETRIB_RIC";
	public static final String ATTIVMANS_ISTR_RIC = "ATTIVMANS_ISTR_RIC";
	public static final String TRASFERTE = "TRASFERTE";	
	public static final String TURNI = "TURNI";	

	public static final String ATTIVMANS_PROF = "ATTIVMANS_PROF";
	
	
	public static final String PATENTE = "PATENTE";
	public static final String C_COMUNICATIVE = "C_COMUNICATIVE";
	public static final String C_ORGANIZZ = "C_ORGANIZZ";
	public static final String C_PROFESS = "C_PROFESS";
	public static final String C_INFORM = "C_INFORM";
	public static final String C_ALTRE = "C_ALTRE";
	public static final String PUBBLICAZIONI = "PUBBLICAZIONI";
	public static final String PRESENTAZIONI = "PRESENTAZIONI";
	public static final String PROGETTI = "PROGETTI";
	public static final String CONFERENZE = "CONFERENZE";
	public static final String SEMINARI = "SEMINARI";
	public static final String RICONOSCIMENTI = "RICONOSCIMENTI";
	public static final String APP_GRUPPI = "APP_GRUPPI";
	public static final String REFERENZE = "REFERENZE";
	
	public static final String TIPO_COMUNICAZIONE = "TIPO_COMUNICAZIONE";
	public static final String INAIL = "INAIL";
	public static final String CITTADINANZA = "CITTADINANZA";
	public static final String TIPO_RAPPORTO = "TIPO_RAPPORTO";
	public static final String ORARIO_LAVORO = "ORARIO_LAVORO";
	public static final String SOCIO = "SOCIO";
	public static final String CCNL = "CCNL";
	public static final String TRATTAMENTO = "?ITRATTAMENTO";
	public static final String LIVELLO = "LIVELLO";
	public static final String DATA_ASSUNZIONE = "DATA_ASSUNZIONE";
	public static final String DATA_TRASFORMAZIONE = "DATA_TRASFORMAZIONE";
	public static final String DATA_CESSAZIONE = "DATA_CESSAZIONE";
	public static final String QUALIFICA_DIPENDENTE = "QUALIFICA_DIPENDENTE";
	public static final String MANSIONE = "MANSIONE";
	public static final String AGEVOLAZIONI = "AGEVOLAZIONI";
	
	public static final String LUN = "LUN";
	public static final String MAR = "MAR";
	public static final String MER = "MER";
	public static final String GIO = "GIO";
	public static final String VEN = "VEN";
	public static final String SAB = "SAB";
	public static final String DOM = "DOM";
	
	public static final String TIPO_AUTORIZZ = "TIPO_AUTORIZZ";
	public static final String N_AUTORIZZ = "N_AUTORIZZ";
	public static final String MOT_AUTORIZZ = "MOT_AUTORIZZ";
	public static final String DATA_RILASCIO = "DATA_RILASCIO";
	public static final String DATA_SCADENZA = "DATA_SCADENZA";
	public static final String NOTE_EXTRACOM = "NOTE_EXTRACOM";
	

	
	
	public static final String MATRICOLA = "MATRICOLA";
	public static final String MATR_MECCANOG = "MATR_MECCANOG";
	public static final String MATR_INPS = "MATR_INPS";
	public static final String MATR_PROVVISORIA = "MATR_PROVVISORIA";
	public static final String GRUPPO_APPARTENENZA = "GRUPPO_APPARTENENZA";
	public static final String MANSIONE_ALTRO = "MANSIONE_ALTRO";
	public static final String NOTE1 = "NOTE1";
	public static final String GRUPPO_SANGUE = "GRUPPO_SANGUE";
	public static final String NOME_BANCA = "NOME_BANCA";
	public static final String CODICE_ABI = "CODICE_ABI";
	public static final String CODICE_CAB = "CODICE_CAB";
	public static final String CONTO_CORRENTE = "CONTO_CORRENTE";
	public static final String CODICE_IBAN = "CODICE_IBAN";
	public static final String GR_AUTORIZZAZ = "GR_AUTORIZZAZ";
	
	public static final String FACEBOOK = "FACEBOOK";
	public static final String LINKEDIN = "LINKEDIN";
	public static final String TWITTER = "TWITTER";
	
	public static final String TITOLO_STUDIO="TITOLO_STUDIO"; 
	
	public static final String PROVNASCITA="PROVNASCITA";
	public static final String PROVRESIDENZ="PROVRESIDENZ";
	public static final String PROVDOMICIL="PROVDOMICIL";
	
	public static final String AZIENDA_CV="AZIENDA_CV";
	public static final String FILIALE="FILIALE";
	
	
	public static final String PATENTE_AM             = "PATENTE_AM";
	public static final String PATENTE_AM_R           = "PATENTE_AM_R";          
	public static final String PATENTE_AM_S           = "PATENTE_AM_S";          
	public static final String PATENTE_A1             = "PATENTE_A1";            
	public static final String PATENTE_A1_R           = "PATENTE_A1_R";          
	public static final String PATENTE_A1_S           = "PATENTE_A1_S";          
	public static final String PATENTE_A2             = "PATENTE_A2";            
	public static final String PATENTE_A2_R           = "PATENTE_A2_R";          
	public static final String PATENTE_A2_S           = "PATENTE_A2_S";          
	public static final String PATENTE_A              = "PATENTE_A";             
	public static final String PATENTE_A_R            = "PATENTE_A_R";           
	public static final String PATENTE_A_S            = "PATENTE_A_S";           
	public static final String PATENTE_B1             = "PATENTE_B1";            
	public static final String PATENTE_B1_R           = "PATENTE_B1_R";          
	public static final String PATENTE_B1_S           = "PATENTE_B1_S";          
	public static final String PATENTE_B              = "PATENTE_B";             
	public static final String PATENTE_B_R            = "PATENTE_B_R";           
	public static final String PATENTE_B_S            = "PATENTE_B_S";           
	public static final String PATENTE_B96            = "PATENTE_B96";           
	public static final String PATENTE_B96_R          = "PATENTE_B96_R";         
	public static final String PATENTE_B96_S          = "PATENTE_B96_S";         
	public static final String PATENTE_BE             = "PATENTE_BE";            
	public static final String PATENTE_BE_R           = "PATENTE_BE_R";          
	public static final String PATENTE_BE_S           = "PATENTE_BE_S";          
	public static final String PATENTE_C1             = "PATENTE_C1";            
	public static final String PATENTE_C1_R           = "PATENTE_C1_R";          
	public static final String PATENTE_C1_S           = "PATENTE_C1_S";          
	public static final String PATENTE_C1E            = "PATENTE_C1E";           
	public static final String PATENTE_C1E_R          = "PATENTE_C1E_R";         
	public static final String PATENTE_C1E_S          = "PATENTE_C1E_S";         
	public static final String PATENTE_C              = "PATENTE_C";             
	public static final String PATENTE_C_R            = "PATENTE_C_R";           
	public static final String PATENTE_C_S            = "PATENTE_C_S";           
	public static final String PATENTE_CE             = "PATENTE_CE";            
	public static final String PATENTE_CE_R           = "PATENTE_CE_R";          
	public static final String PATENTE_CE_S           = "PATENTE_CE_S";          
	public static final String PATENTE_D1             = "PATENTE_D1";            
	public static final String PATENTE_D1_R           = "PATENTE_D1_R";          
	public static final String PATENTE_D1_S           = "PATENTE_D1_S";          
	public static final String PATENTE_D1E            = "PATENTE_D1E";           
	public static final String PATENTE_D1E_R          = "PATENTE_D1E_R";         
	public static final String PATENTE_D1E_S          = "PATENTE_D1E_S";         
	public static final String PATENTE_D              = "PATENTE_D";             
	public static final String PATENTE_D_R            = "PATENTE_D_R";           
	public static final String PATENTE_D_S            = "PATENTE_D_S";           
	public static final String PATENTE_DE             = "PATENTE_DE";            
	public static final String PATENTE_DE_R           = "PATENTE_DE_R";          
	public static final String PATENTE_DE_S           = "PATENTE_DE_S";          
	public static final String PATENTE_KA             = "PATENTE_KA";            
	public static final String PATENTE_KA_R           = "PATENTE_KA_R";          
	public static final String PATENTE_KA_S           = "PATENTE_KA_S";          
	public static final String PATENTE_KB             = "PATENTE_KB";            
	public static final String PATENTE_KB_R           = "PATENTE_KB_R";          
	public static final String PATENTE_KB_S           = "PATENTE_KB_S";          
	public static final String PATENTE_CQC_PERSONE    = "PATENTE_CQC_PERSONE";   
	public static final String PATENTE_CQC_PERSONE_R  = "PATENTE_CQC_PERSONE_R"; 
	public static final String PATENTE_CQC_PERSONE_S  = "PATENTE_CQC_PERSONE_S"; 
	public static final String PATENTE_CQC_MERCI      = "PATENTE_CQC_MERCI";     
	public static final String PATENTE_CQC_MERCI_R    = "PATENTE_CQC_MERCI_R";   
	public static final String PATENTE_CQC_MERCI_S    = "PATENTE_CQC_MERCI_S";   
	public static final String PATENTE_CFPADR_A       = "PATENTE_CFPADR_A";      
	public static final String PATENTE_CFPADR_A_R     = "PATENTE_CFPADR_A_R";    
	public static final String PATENTE_CFPADR_A_S     = "PATENTE_CFPADR_A_S";    
	public static final String PATENTE_CFPADR_B       = "PATENTE_CFPADR_B";      
	public static final String PATENTE_CFPADR_B_R     = "PATENTE_CFPADR_B_R";    
	public static final String PATENTE_CFPADR_B_S     = "PATENTE_CFPADR_B_S";    
	public static final String PATENTE_CFPADR_BE      = "PATENTE_CFPADR_BE";     
	public static final String PATENTE_CFPADR_BE_R    = "PATENTE_CFPADR_BE_R";   
	public static final String PATENTE_CFPADR_BE_S    = "PATENTE_CFPADR_BE_S";   
	public static final String PATENTE_CFPADR_BR      = "PATENTE_CFPADR_BR";     
	public static final String PATENTE_CFPADR_BR_R    = "PATENTE_CFPADR_BR_R";   
	public static final String PATENTE_CFPADR_BR_S    = "PATENTE_CFPADR_BR_S";   
	public static final String PATENTE_MULETTO        = "PATENTE_MULETTO";             
	public static final String PATENTE_MULETTO_R      = "PATENTE_MULETTO_R";           
	public static final String PATENTE_MULETTO_S      = "PATENTE_MULETTO_S";
	
	//public static final String CENTRO_COSTO      = "CENTRO_COSTO";
	
	public static final String CIVICO_RESID      = "CIVICO_RESID";
	public static final String CIVICO_DOMIC      = "CIVICO_DOMIC";
	
	public static final String CODICE_CIN      = "CODICE_CIN";
	public static final String CODICE_CHK      = "CODICE_CHK";
	
	public static final String COD_CATASTO_RESID      = "COD_CATASTO_RESID";
	public static final String COD_CATASTO_DOMIC      = "COD_CATASTO_DOMIC";
	
	public static final String COD_CATASTO_NASCITA = "COD_CATASTO_NASCITA";
	
	public static final String ID_IMPORT = "ID_IMPORT";
	

	public CurriculImportDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(CODICE);
	}


	public CurriculImportDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(CODICE);
	}

	public CurriculImportDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(CODICE);

	}

}
