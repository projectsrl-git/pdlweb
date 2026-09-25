package net.projectsrl.wm.cv.core;

import java.util.HashMap;

import net.project.misc.Config;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;
import net.projectsrl.wm.utils.Utils;

/**
 * FunctionRicercaCurriculum
 * 
 */
public class FunctionRicercaCurriculum extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_curriculum";
    private static final String PAGE_RISULTATI = "browse_curriculum";
    public FunctionRicercaCurriculum() {

        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
   }

    public FunctionRicercaCurriculum(ApplicationServices_itf applServices, String functionID, String functionName) {

        super(applServices, functionID, functionName);
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

 
    	/**
    	 * Prepara la where condition da passare alla query per la visualizzazione
    	 * della pagina di ricerca
    	 * 
    	 * @param SsbServletRequest+
    	 *            req
    	 * @param HashMap
    	 *            queryParameter
    	 * 
    	 * @return HashMap
    	 */
    	protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {

    		String codice = req.getField("CODICE").trim();
            String d_registraz_da 	= req.getField("D_REGISTRAZ_DA");
            String d_registraz_a 	= req.getField("D_REGISTRAZ_A");        
            String cognome 			= req.getField("COGNOME").trim();
            String nome 			= req.getField("NOME").trim();
            String sesso 			= req.getField("SESSO");
            String chiaveMemo		= req.getField("CHIAVE_MEMO").trim();
            String residenzaDomicilio = req.getField("RESIDENZA_DOMICILIO").trim();
            String aziendaSessione = (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
            String azienda		= req.getField("AZIENDA_TENDINA");
            

    		String dtDaSql = "";
    		String dtASql = "";

    		String str_composed_where_cond = "";
    		
    		 if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
    	    		str_composed_where_cond = str_composed_where_cond + " and (CURRICUL.AZIENDA_INSERIMENTO IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%') OR CURRICUL.AZIENDA_CV IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%'))";
    	        }


    		if (!codice.trim().equals("")) {
    			// Se viene valorizzato il codice, ricerco per codice, ignorando gli
    			// altri parametri
    			str_composed_where_cond = str_composed_where_cond + " and CURRICUL.codice like '%" + codice + "%'";

    		} else {

    			// Se non viene valorizzato il campo codice, verifico gli altri
    			// parametri

    	        	// DATA REGISTRAZIONE
    				if (d_registraz_a.trim().equals("") && !d_registraz_da.trim().equals("")) {
    					dtDaSql = Utils.ribaltaData(d_registraz_da);
    					dtASql ="";
    					str_composed_where_cond = str_composed_where_cond + " and D_REGISTRAZ >= '" + dtDaSql + "'";
    				}
    				if (!d_registraz_a.trim().equals("") && d_registraz_da.trim().equals("")) {
    					dtDaSql ="";
    					dtASql = Utils.ribaltaData(d_registraz_a);
    					str_composed_where_cond = str_composed_where_cond + " and D_REGISTRAZ <= '" + dtASql + "'";
    				}
    				if (!d_registraz_a.trim().equals("") && !d_registraz_da.trim().equals("")) {
    					dtDaSql = Utils.ribaltaData(d_registraz_da);
    					dtASql = Utils.ribaltaData(d_registraz_a);
    					str_composed_where_cond = str_composed_where_cond + " and D_REGISTRAZ between '" + dtDaSql + "' and '" + dtASql + "'";
    				}
    	            
    	            // COGNOME
    	            if (!cognome.trim().equals("")) {
    	            	str_composed_where_cond = str_composed_where_cond + " and CURRICUL.cognome like '"+Utils.escapeSqlParameter(cognome)+"%'";
    	            }	

    	            // NOME
    	            if (!nome.trim().equals("")) {
    	            	str_composed_where_cond = str_composed_where_cond + " and CURRICUL.nome like '"+nome.toUpperCase()+"%'";
    	            }	            

    	            // SESSO
    	            if (!sesso.trim().equals("")) {	
    	            	str_composed_where_cond = str_composed_where_cond + " and sesso = '"+sesso.toUpperCase()+"'";
    	            }            
    	            
    	            // RESIDENZA DOMICILIO
    	            if (!residenzaDomicilio.trim().equals("")) {	
    	            	str_composed_where_cond = str_composed_where_cond + " and ( LOCRESIDENZ LIKE '%"+residenzaDomicilio.toUpperCase()+"%' OR LOCDOMICIL LIKE '%"+residenzaDomicilio.toUpperCase()+"%' )";
    	            }         
    	            
    	            if (!azienda.trim().equalsIgnoreCase("")) {	
    	            	str_composed_where_cond = str_composed_where_cond + " AND AZIENDA_CV = '"+azienda+"'";
    	            }
    	            
    	            // CHIAVE_MEMO
    	            if (!chiaveMemo.trim().equals("")) {
    	            	String additionalWhereCondition=Config.GetInstance().getProperty("DS.DataSetBrowseCurriculum.AdditionalWhereCondition");
    	            	if (additionalWhereCondition!=null || !additionalWhereCondition.equals("") ) {
    	            		additionalWhereCondition=additionalWhereCondition.replaceAll("#CHIAVE_MEMO#",chiaveMemo.trim());
        	            	str_composed_where_cond = str_composed_where_cond + " and "+additionalWhereCondition;
						}
    	            }           		
    		} 
    		str_composed_where_cond=str_composed_where_cond.trim().toUpperCase();
    		if (str_composed_where_cond.startsWith("AND ")) {
    			str_composed_where_cond=" WHERE "+str_composed_where_cond.substring(4);
    		}

    		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond + " order by codice,cognome,nome");
    		
    		return queryParameter;
    	}
        
        
     

  
}
