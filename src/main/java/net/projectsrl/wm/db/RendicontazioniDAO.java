package net.projectsrl.wm.db;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.projectsrl.db.PjDAO_base;

/**
 * Classe che rappresenta la tabella RENDICONTAZIONI Proprietà lette dal file di
 * configurazione:
 * <p>
 * DATABASE.NAME = nome del database
 */
public class RendicontazioniDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "RENDICONTAZIONI";

	public static final String	ID_RENDICONTAZIONE =		  "ID_RENDICONTAZIONE";		          
	public static final String	ID_RENDICONTAZIONE_ORIGINE =  "ID_RENDICONTAZIONE_ORIGINE";
	public static final String	DIPENDENTE =				  "DIPENDENTE";				                      
	public static final String	QUALITA =					  "QUALITA";					                          
	public static final String	DATA_DAL =					  "DATA_DAL";					                        
	public static final String	DATA_AL = 					  "DATA_AL"; 					                        
	public static final String	ORE_DAL = 					  "ORE_DAL"; 					                        
	public static final String	ORE_AL = 					  "ORE_AL"; 					                          
	public static final String	ORE =						  "?IORE";						                                
	public static final String	INCARICO =					  "INCARICO";					                        
	public static final String	CLIENTE =					  "CLIENTE";					                          
	public static final String	COMMESSA =					  "COMMESSA";					                        
	public static final String	LUOGO =						  "LUOGO";						                            
	public static final String	MODELLO =					  "MODELLO";					                          
	public static final String	MARCA =						  "MARCA";						                            
	public static final String	AZIENDA_TENDINA =			  "AZIENDA_TENDINA";			              
	public static final String	DOCUMENTAZIONE = 			  "DOCUMENTAZIONE"; 			              
	public static final String	NOMINATIVO =				  "NOMINATIVO";				                      
	public static final String	DATA_NASCITA =	        	  "DATA_NASCITA";	        	      
	public static final String	CITTA_NASCITA =	        	  "CITTA_NASCITA";	        	    
	public static final String	PROV_NASCITA =	        	  "PROV_NASCITA";	        	      
	public static final String	INDIR_RESIDENZA =	    	  "INDIR_RESIDENZA";	    	        
	public static final String	CITTA_RESIDENZA =       	  "CITTA_RESIDENZA";       	    
	public static final String	CAP_RESIDENZA =         	  "CAP_RESIDENZA";         	    
	public static final String	COD_FISCALE =				  "COD_FISCALE";				                    
	public static final String	MOTIVAZIONE =				  "MOTIVAZIONE";				                    
	public static final String	SPESE_VIAGGIO_IMPORTO =		  "?ISPESE_VIAGGIO_IMPORTO";		    
	public static final String	SPESE_TRASPORTO_IMPORTO =	  "?ISPESE_TRASPORTO_IMPORTO";	  
	public static final String	SPESE_PARCHEGGIO_IMPORTO =	  "?ISPESE_PARCHEGGIO_IMPORTO";	 
	public static final String	SPESE_VITTO_IMPORTO =		  "?ISPESE_VITTO_IMPORTO";		        
	public static final String	SPESE_ALLOGGIO_IMPORTO =	  "?ISPESE_ALLOGGIO_IMPORTO";	    
	public static final String	SPESE_PEDAGGI_IMPORTO =		  "?ISPESE_PEDAGGI_IMPORTO";		    
	public static final String	SPESE_ALTRO_IMPORTO =		  "?ISPESE_ALTRO_IMPORTO";		        
	public static final String	SPESE_TOTALE_IMPORTO =		  "?ISPESE_TOTALE_IMPORTO";		      
	public static final String	SPESE_VIAGGIO_NOTE =		  "SPESE_VIAGGIO_NOTE";		          
	public static final String	SPESE_TRASPORTO_NOTE =		  "SPESE_TRASPORTO_NOTE";		      
	public static final String	SPESE_PARCHEGGIO_NOTE =		  "SPESE_PARCHEGGIO_NOTE";		    
	public static final String	SPESE_VITTO_NOTE =			  "SPESE_VITTO_NOTE";			            
	public static final String	SPESE_ALLOGGIO_NOTE =		  "SPESE_ALLOGGIO_NOTE";		        
	public static final String	SPESE_PEDAGGI_NOTE =		  "SPESE_PEDAGGI_NOTE";		          
	public static final String	SPESE_ALTRO_NOTE =			  "SPESE_ALTRO_NOTE";			            
	public static final String	SPESE_TOTALE_NOTE =			  "SPESE_TOTALE_NOTE";			          
	public static final String	LIV1_1 =					  "LIV1_1";					                            
	public static final String	LIV1_2 =					  "LIV1_2";					                            
	public static final String	LIV1_3 =					  "LIV1_3";					                            
	public static final String	LIV1_4 =					  "LIV1_4";					                            
	public static final String	LIV1_5 =					  "LIV1_5";					                            
	public static final String	LIV2_1 =					  "LIV2_1";					                            
	public static final String	LIV2_2 =					  "LIV2_2";					                            
	public static final String	LIV2_3 =					  "LIV2_3";					                            
	public static final String	LIV2_4 =					  "LIV2_4";					                            
	public static final String	LIV2_5 =					  "LIV2_5";					                            
	public static final String	LIV3_1 =					  "LIV3_1";					                            
	public static final String	LIV3_2 =					  "LIV3_2";					                            
	public static final String	LIV3_3 =					  "LIV3_3";					                            
	public static final String	LIV3_4 =					  "LIV3_4";					                            
	public static final String	LIV3_5 =					  "LIV3_5";					                            
	public static final String	LIV4_1 =					  "LIV4_1";					                            
	public static final String	LIV4_2 =					  "LIV4_2";					                            
	public static final String	LIV4_3 =					  "LIV4_3";					                            
	public static final String	LIV4_4 =					  "LIV4_4";					                            
	public static final String	LIV4_5 =					  "LIV4_5";					                            
	public static final String	LIV5_1 =					  "LIV5_1";					                            
	public static final String	LIV5_2 =					  "LIV5_2";					                            
	public static final String	LIV5_3 =					  "LIV5_3";					                            
	public static final String	LIV5_4 =					  "LIV5_4";					                            
	public static final String	LIV5_5 =					  "LIV5_5";					                            
	public static final String	APPROVAZIONE =				  "APPROVAZIONE";
	public static final String	ADMIN =				 		  "ADMIN";	
	public static final String	SPESE_VIAGGIO_IMPORTO_A =	  "?ISPESE_VIAGGIO_IMPORTO_A";		    
	public static final String	SPESE_TRASPORTO_IMPORTO_A =	  "?ISPESE_TRASPORTO_IMPORTO_A";	  
	public static final String	SPESE_PARCHEGGIO_IMPORTO_A =  "?ISPESE_PARCHEGGIO_IMPORTO_A";	 
	public static final String	SPESE_VITTO_IMPORTO_A =		  "?ISPESE_VITTO_IMPORTO_A";		        
	public static final String	SPESE_ALLOGGIO_IMPORTO_A =	  "?ISPESE_ALLOGGIO_IMPORTO_A";	    
	public static final String	SPESE_PEDAGGI_IMPORTO_A =	  "?ISPESE_PEDAGGI_IMPORTO_A";		    
	public static final String	SPESE_ALTRO_IMPORTO_A =		  "?ISPESE_ALTRO_IMPORTO_A";		        
	public static final String	SPESE_TOTALE_IMPORTO_A =	  "?ISPESE_TOTALE_IMPORTO_A";	
	
	public static final String	SPESE_VIAGGIO_VALUTA =		  "SPESE_VIAGGIO_VALUTA";		    
	public static final String	SPESE_TRASPORTO_VALUTA =	  "SPESE_TRASPORTO_VALUTA";	  
	public static final String	SPESE_PARCHEGGIO_VALUTA =	  "SPESE_PARCHEGGIO_VALUTA";	 
	public static final String	SPESE_VITTO_VALUTA =		  "SPESE_VITTO_VALUTA";		        
	public static final String	SPESE_ALLOGGIO_VALUTA =	  	  "SPESE_ALLOGGIO_VALUTA";	    
	public static final String	SPESE_PEDAGGI_VALUTA =		  "SPESE_PEDAGGI_VALUTA";		    
	public static final String	SPESE_ALTRO_VALUTA =		  "SPESE_ALTRO_VALUTA";		        
	public static final String	SPESE_TOTALE_VALUTA =		  "SPESE_TOTALE_VALUTA";	
	

	public RendicontazioniDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_RENDICONTAZIONE);
	}


	public RendicontazioniDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_RENDICONTAZIONE);
	}

	public RendicontazioniDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_RENDICONTAZIONE);

	}
}
