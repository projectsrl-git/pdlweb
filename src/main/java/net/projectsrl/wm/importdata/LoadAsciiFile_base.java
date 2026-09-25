
package net.projectsrl.wm.importdata;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import net.project.db.DBTransaction;
import net.project.errors.AppCrash;
import net.project.servlet.frame.SsbServletRequest;

public abstract class LoadAsciiFile_base {

    private ArrayList<String> _fieldNames  = new ArrayList<String>();
    private ArrayList<String> _fieldValues = new ArrayList<String>();

    public abstract void setDefaultData(HashMap<String, String> defaultData) throws AppCrash;

    public void loadData(String user, String absolutePathFileName, SsbServletRequest req) throws AppCrash {

        String text = "";

        DBTransaction dbTransaction = new DBTransaction();

        try {

            InputStream ist = new FileInputStream(absolutePathFileName);
            BufferedReader istream = new BufferedReader(new InputStreamReader(ist));
            int rowCounter = 0;
    
            while (true) {

                text = istream.readLine();
                
                if (text == null) {
                    break;
                }
                if (text.equals("")) {
                    continue;
                }

                

                	
            	 rowCounter++;

            	 setFieldNames(text);
            	 if (!text.contains("CODICE DITTA") && !text.contains("DENOMINAZIONE")) {
            		 setFieldValues(text);
                 }
                 
                 store(dbTransaction, user, req);
                 
 		        
 				        
            }

            dbTransaction.commit();

        } catch (Throwable e) {
            if (dbTransaction != null) {
                dbTransaction.rollBack();
            }
            new AppCrash(e).logContext("LoadAsciiFile_base::", "CARICAMENTO INTERROTTO - riga " + text);
        } finally {
            if (dbTransaction != null) {
                dbTransaction.end();
            }
        }

    }

    protected void setFieldNames(String text) {

        int index = 0;
        String[] dataArray = text.split(getSeparator());
        for (String singoloCampo : dataArray) {
            _fieldNames.add(singoloCampo);
            index++;
        }
    }

    protected void setFieldValues(String text) {

        int index = 0;
        _fieldValues = new ArrayList<String>();

        String[] dataArray = text.split(getSeparator());
        for (String singoloCampo : dataArray) {

            if (singoloCampo == null) {
                singoloCampo = "";
            }
            _fieldValues.add(singoloCampo);
            index++;

        }

    }

    protected ArrayList<String> getFieldNames() {

        return _fieldNames;
    }

    protected void setFieldNames(ArrayList<String> names) {

        _fieldNames = names;
    }

    protected ArrayList<String> getFieldValues() {

        return _fieldValues;
    }

    /**
     * Ritorna il valore con indice <index>
     * 
     * @param int index indice nell'array dei campi
     * 
     * @return String valore del campo
     */
    protected String getStringValue(int index) {

        if (_fieldValues.size() < index) {
            return "";
        }

        String value = _fieldValues.get(index);

        if (value != null) {
            value = value.trim();
        }

        return value;
    }

    /**
     * Ritorna il valore con indice <index> e ne controlla la lunghezza: se più lungo di <lenght> byte allora ne fa la
     * substring
     * 
     * @param int index indice nell'array dei campi
     * @param int lenght lunghezza da controllare
     * 
     * @return String valore del campo
     */
    protected String getStringValue(int index, int lenght) {

        String value = getStringValue(index);

        if (value.length() > lenght) {
            value = value.substring(0, lenght);
        }

        return value;
    }

    protected String getNumericValue(int index) {

        if (_fieldValues.size() <= index) {
            return "0";
        }

        String value = _fieldValues.get(index);

        if (value == null || value.trim().equals("")) {
            value = "0";
        }

        value = value.trim().replace(",", ".");

        return value;
    }

    protected void setFieldValues(ArrayList<String> values) {

        _fieldValues = values;
    }

    //protected abstract void store(DBTransaction dbtransaction, String user, SsbServletRequest req) throws AppCrash;

    protected void store(DBTransaction dbtransaction, String user, SsbServletRequest req) throws AppCrash {

        store(dbtransaction, "", req);
    }

    protected abstract String getSeparator();

}
