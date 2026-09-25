package net.projectsrl.wm.archivio.core;

import java.util.HashMap;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.errors.AppCrash;
import net.project.servlet.frame.ApplicationServices_itf;
import net.project.servlet.frame.SsbServletRequest;
import net.projectsrl.core.FunctionRicerca;

/**
 * FunctionRicercaDocumentiCaricati
 * 
 */
public class FunctionRicercaArchivioDocumenti extends FunctionRicerca {

    private static final String PAGE_IMPOSTA = "ricerca_archiviodocumenti";
    private static final String PAGE_RISULTATI = "browse_archiviodocumenti";  
    private static final String DATASET_DIPENDENTI = "DataSetDipendenti";
    

    public FunctionRicercaArchivioDocumenti() {
        super();
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }

    public FunctionRicercaArchivioDocumenti(ApplicationServices_itf applServices, String functionID, String functionName) {
        super(applServices, functionID, functionName);
        setPageImposta(PAGE_IMPOSTA);
        setPageRisultati(PAGE_RISULTATI);
    }
   

    /**
     * Prepara la where condition da passare alla query per la visualizzazione della pagina di ricerca
     * 
     * @param SsbServletRequest+ req
     * @param HashMap queryParameter
     * 
     * @return HashMap
     */
    @SuppressWarnings("unchecked")
	protected HashMap prepareWhereCondition(SsbServletRequest req, HashMap<String, String> queryParameter) {
    	
    	
        String azienda			= req.getField("AZIENDA_TENDINA");
        String aziendaSessione 	= (String) req.getSession(false).getAttribute("AZIENDA_SESSIONE");
        String ruolo		 	= (String) req.getSession(false).getAttribute("RUOLO_SESSIONE");
        String dipendenteSessione = (String) req.getSession(false).getAttribute("ID_DIPENDENTE_SESSIONE");
        String dipendente		= req.getField("DIPENDENTE");
        String matricolaSessione = (String) req.getSession(false).getAttribute("MATRICOLA");
        
        if (ruolo.equals("D")){
        	azienda="";
        	aziendaSessione="";
        	dipendente = dipendenteSessione;
        }else{
        	try {
				matricolaSessione=getMatricola(dipendente);
			} catch (AppCrash e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        String str_composed_where_cond = "";
        
        if (!aziendaSessione.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND AZIENDE.CODICE IN (SELECT CODICE FROM GERARCHIA_AZIENDE WHERE GERARCHIA LIKE '%"+aziendaSessione+"%')";
        }

        if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DIPENDENTI.AZIENDA = '"+azienda+"'";
        }
        
        if (!dipendente.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond = str_composed_where_cond + " AND DAGGANCIO = '"+dipendente+"'";
        }else{
        	str_composed_where_cond = str_composed_where_cond + " AND 1=0";
        }
		
        if (!str_composed_where_cond.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond=" "+str_composed_where_cond.substring(4);
        	str_composed_where_cond=" WHERE "+str_composed_where_cond;
        }

		queryParameter.put("COMPOSED_WHERE_COND", str_composed_where_cond);
		 
		
		
		
		
		
		String str_composed_where_cond2 = "";
		
		if (!matricolaSessione.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond2 = str_composed_where_cond2 + " AND tabella.MATRICOLA = '"+matricolaSessione+"'";
        }else{
        	str_composed_where_cond2 = str_composed_where_cond2 + " AND 1=0";
        }
		if (ruolo.equals("D")){
        	azienda=aziendaSessione;
        }
		if (!azienda.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond2 = str_composed_where_cond2 + " AND DIPENDENTI.AZIENDA = '"+azienda+"'";
        }
		
		if (!str_composed_where_cond2.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond2=" "+str_composed_where_cond2.substring(4);
        	str_composed_where_cond2=" WHERE "+str_composed_where_cond2;
        }
		
		
		queryParameter.put("COMPOSED_WHERE_COND2", str_composed_where_cond2);
		
		
		
		
		String str_composed_where_cond3 = "";
		
		if (!dipendente.trim().equalsIgnoreCase("")) {	
        	str_composed_where_cond3 = str_composed_where_cond3 + " AND DAGGANCIO = '"+dipendente+"'";
        }else{
        	str_composed_where_cond3 = str_composed_where_cond3 + " AND 1=0";
        }
		
		if (!str_composed_where_cond3.trim().equalsIgnoreCase("")) {
        	str_composed_where_cond3=" "+str_composed_where_cond3.substring(4);
        	str_composed_where_cond3=" WHERE "+str_composed_where_cond3;
        }
		
		
		queryParameter.put("COMPOSED_WHERE_COND3", str_composed_where_cond3);
    	
        
    	return queryParameter;
    }    
    
    
    private String getMatricola(String idDipendente) throws AppCrash {
        String matricola = "";
        String idTrovato="";
        DataSet_itf dataSet = null;
        try {
            DataSetFactory dsFactory = DataSetFactory.getInstance();
            dsFactory = DataSetFactory.getInstance();
            dataSet = dsFactory.makeDataSet("", DATASET_DIPENDENTI);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("ID_DIPENDENTE", idDipendente);
            dataSet.setParam(params);
            dataSet.open();
            while (dataSet.hasMoreElements()) {
                Row_itf dbRow = (Row_itf) dataSet.nextElement();
                idTrovato = dbRow.getField("ID_DIPENDENTE").toString().trim();
                if (idDipendente.equals(idTrovato)){
                	matricola = dbRow.getField("MATRICOLA").toString().trim();
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
        return matricola;
    }
    
}
