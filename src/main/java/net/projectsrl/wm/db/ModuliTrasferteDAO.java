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
public class ModuliTrasferteDAO extends PjDAO_base {
	

	private static final String NOME_TABELLA = "MOD_TRASFERTE";

	public static final String	ID_MODTRASFERTA =		  	  "ID_MODTRASFERTA";		          
	public static final String	ID_MODTRASFERTA_ORIGINE =  	  "ID_MODTRASFERTA_ORIGINE";
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
	public static final String	MATRICOLA_DIPENDENTE =		  "MATRICOLA_DIPENDENTE";   
	public static final String	ALLEGATO =				      "ALLEGATO";
	public static final String	FILENAME =				      "FILENAME";   
	

	public ModuliTrasferteDAO() throws AppCrash {
		super(NOME_TABELLA);
		setUniqueIdentifier(ID_MODTRASFERTA);
	}


	public ModuliTrasferteDAO(DBTransaction transact) throws AppCrash {
		super(transact, NOME_TABELLA);
		setUniqueIdentifier(ID_MODTRASFERTA);
	}

	public ModuliTrasferteDAO(DBTransaction transact, String tableName) throws AppCrash {
		super(transact, tableName);
		setUniqueIdentifier(ID_MODTRASFERTA);

	}
}
