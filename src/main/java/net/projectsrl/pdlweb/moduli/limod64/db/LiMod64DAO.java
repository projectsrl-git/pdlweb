
package net.projectsrl.pdlweb.moduli.limod64.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.errors.ParamCrash;
import net.project.misc.Util;
import net.projectsrl.dafne.richieste.core.StatiRichiesta;
import net.projectsrl.pdlweb.moduli.db.LiModDAO_base;
import net.projectsrl.webapp.core.WebAppUtils;

public class LiMod64DAO extends LiModDAO_base {

	private static final String TABLE_NAME      = "LIMOD64";

    public static final String  ID_MODULO    = "ID_MODULO";
    public static final String  DT_MODULO         = "DT_MODULO";
    public static final String  CODICE_TURNO = "CODICE_TURNO";
    public static final String  ID_AZIENDA = "ID_AZIENDA";
    public static final String  ID_IMPIANTO = "ID_IMPIANTO";
    public static final String  ID_AREA = "ID_AREA";
    public static final String  STATO = "STATO";
    public static final String  NR_REV = "NR_REV";
    public static final String  DT_REV = "DT_REV";
    public static final String  ID_COORD_GEST_INT          = "ID_COORD_GEST_INT";
    public static final String  NOME_FILE          = "NOME_FILE";
    
    public static final String  ID_UTENTE_INS          = "ID_UTENTE_INS";
    public static final String  TS_INS            = "TS_INS";
    
    public static final String  TABELLA_PARENT   = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT = "ID_MODULO_PARENT";
    
    
    public static final String   FLG_A_1_1    ="FLG_A_1_1"; 
    public static final String   FLG_A_1_2    ="FLG_A_1_2"; 
    public static final String   FLG_A_1_3    ="FLG_A_1_3"; 
    public static final String   FLG_A_1_4    ="FLG_A_1_4"; 
    public static final String   FLG_A_1_5    ="FLG_A_1_5"; 
    public static final String   FLG_A_1_6    ="FLG_A_1_6"; 
                                                            
    public static final String   FLG_A_2_1    ="FLG_A_2_1"; 
    public static final String   FLG_A_2_2    ="FLG_A_2_2"; 
    public static final String   FLG_A_2_3    ="FLG_A_2_3"; 
    public static final String   FLG_A_2_4    ="FLG_A_2_4"; 
    public static final String   FLG_A_2_5    ="FLG_A_2_5"; 
    public static final String   FLG_A_2_6    ="FLG_A_2_6"; 
                                                     
    public static final String   FLG_A_3_1    ="FLG_A_3_1"; 
    public static final String   FLG_A_3_2    ="FLG_A_3_2"; 
    public static final String   FLG_A_3_3    ="FLG_A_3_3"; 
    public static final String   FLG_A_3_4    ="FLG_A_3_4"; 
    public static final String   FLG_A_3_5    ="FLG_A_3_5"; 
    public static final String   FLG_A_3_6    ="FLG_A_3_6"; 
                                                      
    public static final String   FLG_A_4_1    ="FLG_A_4_1"; 
    public static final String   FLG_A_4_2    ="FLG_A_4_2"; 
    public static final String   FLG_A_4_3    ="FLG_A_4_3"; 
    public static final String   FLG_A_4_4    ="FLG_A_4_4"; 
    public static final String   FLG_A_4_5    ="FLG_A_4_5"; 
    public static final String   FLG_A_4_6    ="FLG_A_4_6"; 
                                                        
    public static final String   FLG_A_5_1    ="FLG_A_5_1"; 
    public static final String   FLG_A_5_2    ="FLG_A_5_2"; 
    public static final String   FLG_A_5_3    ="FLG_A_5_3"; 
    public static final String   FLG_A_5_4    ="FLG_A_5_4"; 
    public static final String   FLG_A_5_5    ="FLG_A_5_5"; 
    public static final String   FLG_A_5_6    ="FLG_A_5_6"; 
                                                        
    public static final String   FLG_A_6_1    ="FLG_A_6_1"; 
    public static final String   FLG_A_6_2    ="FLG_A_6_2"; 
    public static final String   FLG_A_6_3    ="FLG_A_6_3"; 
    public static final String   FLG_A_6_4    ="FLG_A_6_4"; 
    public static final String   FLG_A_6_5    ="FLG_A_6_5"; 
    public static final String   FLG_A_6_6    ="FLG_A_6_6"; 
                                                       
    public static final String   FLG_A_7_1    ="FLG_A_7_1"; 
    public static final String   FLG_A_7_2    ="FLG_A_7_2"; 
    public static final String   FLG_A_7_3    ="FLG_A_7_3"; 
    public static final String   FLG_A_7_4    ="FLG_A_7_4"; 
    public static final String   FLG_A_7_5    ="FLG_A_7_5"; 
    public static final String   FLG_A_7_6    ="FLG_A_7_6"; 
                                                        
    public static final String   FLG_A_8_1    ="FLG_A_8_1"; 
    public static final String   FLG_A_8_2    ="FLG_A_8_2"; 
    public static final String   FLG_A_8_3    ="FLG_A_8_3"; 
    public static final String   FLG_A_8_4    ="FLG_A_8_4"; 
    public static final String   FLG_A_8_5    ="FLG_A_8_5"; 
    public static final String   FLG_A_8_6    ="FLG_A_8_6"; 
                                                     
    public static final String   FLG_A_9_1    ="FLG_A_9_1"; 
    public static final String   FLG_A_9_2    ="FLG_A_9_2"; 
    public static final String   FLG_A_9_3    ="FLG_A_9_3"; 
    public static final String   FLG_A_9_4    ="FLG_A_9_4"; 
    public static final String   FLG_A_9_5    ="FLG_A_9_5"; 
    public static final String   FLG_A_9_6    ="FLG_A_9_6"; 
                                                      
    public static final String   FLG_A_10_1   ="FLG_A_10_1";
    public static final String   FLG_A_10_2   ="FLG_A_10_2";
    public static final String   FLG_A_10_3   ="FLG_A_10_3";
    public static final String   FLG_A_10_4   ="FLG_A_10_4";
    public static final String   FLG_A_10_5   ="FLG_A_10_5";
    public static final String   FLG_A_10_6   ="FLG_A_10_6";
                                                      
    public static final String   FLG_A_11_1   ="FLG_A_11_1";
    public static final String   FLG_A_11_2   ="FLG_A_11_2";
    public static final String   FLG_A_11_3   ="FLG_A_11_3";
    public static final String   FLG_A_11_4   ="FLG_A_11_4";
    public static final String   FLG_A_11_5   ="FLG_A_11_5";
    public static final String   FLG_A_11_6   ="FLG_A_11_6";
                                                       
    public static final String   FLG_A_12_1   ="FLG_A_12_1";
    public static final String   FLG_A_12_2   ="FLG_A_12_2";
    public static final String   FLG_A_12_3   ="FLG_A_12_3";
    public static final String   FLG_A_12_4   ="FLG_A_12_4";
    public static final String   FLG_A_12_5   ="FLG_A_12_5";
    public static final String   FLG_A_12_6   ="FLG_A_12_6";
                                                       
    public static final String   FLG_A_13_1   ="FLG_A_13_1";
    public static final String   FLG_A_13_2   ="FLG_A_13_2";
    public static final String   FLG_A_13_3   ="FLG_A_13_3";
    public static final String   FLG_A_13_4   ="FLG_A_13_4";
    public static final String   FLG_A_13_5   ="FLG_A_13_5";
    public static final String   FLG_A_13_6   ="FLG_A_13_6";
                                                        
    public static final String   FLG_A_14_1   ="FLG_A_14_1";
    public static final String   FLG_A_14_2   ="FLG_A_14_2";
    public static final String   FLG_A_14_3   ="FLG_A_14_3";
    public static final String   FLG_A_14_4   ="FLG_A_14_4";
    public static final String   FLG_A_14_5   ="FLG_A_14_5";
    public static final String   FLG_A_14_6   ="FLG_A_14_6";
                                                        
    public static final String   FLG_A_15_1   ="FLG_A_15_1";
    public static final String   FLG_A_15_2   ="FLG_A_15_2";
    public static final String   FLG_A_15_3   ="FLG_A_15_3";
    public static final String   FLG_A_15_4   ="FLG_A_15_4";
    public static final String   FLG_A_15_5   ="FLG_A_15_5";
    public static final String   FLG_A_15_6   ="FLG_A_15_6";
                                                           
    public static final String   FLG_B_1_1    ="FLG_B_1_1"; 
    public static final String   FLG_B_1_2    ="FLG_B_1_2"; 
    public static final String   FLG_B_1_3    ="FLG_B_1_3"; 
    public static final String   FLG_B_1_4    ="FLG_B_1_4"; 
    public static final String   FLG_B_1_5    ="FLG_B_1_5"; 
    public static final String   FLG_B_1_6    ="FLG_B_1_6"; 
                                                        
    public static final String   FLG_B_2_1    ="FLG_B_2_1"; 
    public static final String   FLG_B_2_2    ="FLG_B_2_2"; 
    public static final String   FLG_B_2_3    ="FLG_B_2_3"; 
    public static final String   FLG_B_2_4    ="FLG_B_2_4"; 
    public static final String   FLG_B_2_5    ="FLG_B_2_5"; 
    public static final String   FLG_B_2_6    ="FLG_B_2_6"; 
                                                        
    public static final String   FLG_B_3_1    ="FLG_B_3_1"; 
    public static final String   FLG_B_3_2    ="FLG_B_3_2"; 
    public static final String   FLG_B_3_3    ="FLG_B_3_3"; 
    public static final String   FLG_B_3_4    ="FLG_B_3_4"; 
    public static final String   FLG_B_3_5    ="FLG_B_3_5"; 
    public static final String   FLG_B_3_6    ="FLG_B_3_6"; 
                                                          
    public static final String   FLG_B_4_1    ="FLG_B_4_1"; 
    public static final String   FLG_B_4_2    ="FLG_B_4_2"; 
    public static final String   FLG_B_4_3    ="FLG_B_4_3"; 
    public static final String   FLG_B_4_4    ="FLG_B_4_4"; 
    public static final String   FLG_B_4_5    ="FLG_B_4_5"; 
    public static final String   FLG_B_4_6    ="FLG_B_4_6"; 
                                                         
    public static final String   FLG_B_5_1    ="FLG_B_5_1"; 
    public static final String   FLG_B_5_2    ="FLG_B_5_2"; 
    public static final String   FLG_B_5_3    ="FLG_B_5_3"; 
    public static final String   FLG_B_5_4    ="FLG_B_5_4"; 
    public static final String   FLG_B_5_5    ="FLG_B_5_5"; 
    public static final String   FLG_B_5_6    ="FLG_B_5_6"; 
                                                        
    public static final String   FLG_B_6_1    ="FLG_B_6_1"; 
    public static final String   FLG_B_6_2    ="FLG_B_6_2"; 
    public static final String   FLG_B_6_3    ="FLG_B_6_3"; 
    public static final String   FLG_B_6_4    ="FLG_B_6_4"; 
    public static final String   FLG_B_6_5    ="FLG_B_6_5"; 
    public static final String   FLG_B_6_6    ="FLG_B_6_6"; 
                                                       
    public static final String   FLG_B_7_1    ="FLG_B_7_1"; 
    public static final String   FLG_B_7_2    ="FLG_B_7_2"; 
    public static final String   FLG_B_7_3    ="FLG_B_7_3"; 
    public static final String   FLG_B_7_4    ="FLG_B_7_4"; 
    public static final String   FLG_B_7_5    ="FLG_B_7_5"; 
    public static final String   FLG_B_7_6    ="FLG_B_7_6"; 
                                                       
    public static final String   FLG_B_8_1    ="FLG_B_8_1"; 
    public static final String   FLG_B_8_2    ="FLG_B_8_2"; 
    public static final String   FLG_B_8_3    ="FLG_B_8_3"; 
    public static final String   FLG_B_8_4    ="FLG_B_8_4"; 
    public static final String   FLG_B_8_5    ="FLG_B_8_5"; 
    public static final String   FLG_B_8_6    ="FLG_B_8_6"; 
                                                        
    public static final String   FLG_B_9_1    ="FLG_B_9_1"; 
    public static final String   FLG_B_9_2    ="FLG_B_9_2"; 
    public static final String   FLG_B_9_3    ="FLG_B_9_3"; 
    public static final String   FLG_B_9_4    ="FLG_B_9_4"; 
    public static final String   FLG_B_9_5    ="FLG_B_9_5"; 
    public static final String   FLG_B_9_6    ="FLG_B_9_6"; 
                                                            
    public static final String   FLG_B_10_1   ="FLG_B_10_1";
    public static final String   FLG_B_10_2   ="FLG_B_10_2";
    public static final String   FLG_B_10_3   ="FLG_B_10_3";
    public static final String   FLG_B_10_4   ="FLG_B_10_4";
    public static final String   FLG_B_10_5   ="FLG_B_10_5";
    public static final String   FLG_B_10_6   ="FLG_B_10_6";
                                                        
    public static final String   FLG_B_11_1   ="FLG_B_11_1";
    public static final String   FLG_B_11_2   ="FLG_B_11_2";
    public static final String   FLG_B_11_3   ="FLG_B_11_3";
    public static final String   FLG_B_11_4   ="FLG_B_11_4";
    public static final String   FLG_B_11_5   ="FLG_B_11_5";
    public static final String   FLG_B_11_6   ="FLG_B_11_6";
                                                       
    public static final String   FLG_B_12_1   ="FLG_B_12_1";
    public static final String   FLG_B_12_2   ="FLG_B_12_2";
    public static final String   FLG_B_12_3   ="FLG_B_12_3";
    public static final String   FLG_B_12_4   ="FLG_B_12_4";
    public static final String   FLG_B_12_5   ="FLG_B_12_5";
    public static final String   FLG_B_12_6   ="FLG_B_12_6";
                                                       
    public static final String   FLG_B_13_1   ="FLG_B_13_1";
    public static final String   FLG_B_13_2   ="FLG_B_13_2";
    public static final String   FLG_B_13_3   ="FLG_B_13_3";
    public static final String   FLG_B_13_4   ="FLG_B_13_4";
    public static final String   FLG_B_13_5   ="FLG_B_13_5";
    public static final String   FLG_B_13_6   ="FLG_B_13_6";
                                                      
    public static final String   FLG_B_14_1   ="FLG_B_14_1";
    public static final String   FLG_B_14_2   ="FLG_B_14_2";
    public static final String   FLG_B_14_3   ="FLG_B_14_3";
    public static final String   FLG_B_14_4   ="FLG_B_14_4";
    public static final String   FLG_B_14_5   ="FLG_B_14_5";
    public static final String   FLG_B_14_6   ="FLG_B_14_6";
                                                      
    public static final String   FLG_B_15_1   ="FLG_B_15_1";
    public static final String   FLG_B_15_2   ="FLG_B_15_2";
    public static final String   FLG_B_15_3   ="FLG_B_15_3";
    public static final String   FLG_B_15_4   ="FLG_B_15_4";
    public static final String   FLG_B_15_5   ="FLG_B_15_5";
    public static final String   FLG_B_15_6   ="FLG_B_15_6";
                                                       
    public static final String   FLG_B_16_1   ="FLG_B_16_1";
    public static final String   FLG_B_16_2   ="FLG_B_16_2";
    public static final String   FLG_B_16_3   ="FLG_B_16_3";
    public static final String   FLG_B_16_4   ="FLG_B_16_4";
    public static final String   FLG_B_16_5   ="FLG_B_16_5";
    public static final String   FLG_B_16_6   ="FLG_B_16_6";
                                                       
    public static final String   FLG_B_17_1   ="FLG_B_17_1";
    public static final String   FLG_B_17_2   ="FLG_B_17_2";
    public static final String   FLG_B_17_3   ="FLG_B_17_3";
    public static final String   FLG_B_17_4   ="FLG_B_17_4";
    public static final String   FLG_B_17_5   ="FLG_B_17_5";
    public static final String   FLG_B_17_6   ="FLG_B_17_6";
                                                     
    public static final String   FLG_B_18_1   ="FLG_B_18_1";
    public static final String   FLG_B_18_2   ="FLG_B_18_2";
    public static final String   FLG_B_18_3   ="FLG_B_18_3";
    public static final String   FLG_B_18_4   ="FLG_B_18_4";
    public static final String   FLG_B_18_5   ="FLG_B_18_5";
    public static final String   FLG_B_18_6   ="FLG_B_18_6";
                                                   
    public static final String   FLG_C1_1_1   ="FLG_C1_1_1";
    public static final String   FLG_C1_1_2   ="FLG_C1_1_2";
    public static final String   FLG_C1_1_3   ="FLG_C1_1_3";
    public static final String   FLG_C1_1_4   ="FLG_C1_1_4";
    public static final String   FLG_C1_1_5   ="FLG_C1_1_5";
    public static final String   FLG_C1_1_6   ="FLG_C1_1_6";
                                                    
    public static final String   FLG_C1_2_1   ="FLG_C1_2_1";
    public static final String   FLG_C1_2_2   ="FLG_C1_2_2";
    public static final String   FLG_C1_2_3   ="FLG_C1_2_3";
    public static final String   FLG_C1_2_4   ="FLG_C1_2_4";
    public static final String   FLG_C1_2_5   ="FLG_C1_2_5";
    public static final String   FLG_C1_2_6   ="FLG_C1_2_6";
                                                     
    public static final String   FLG_C1_3_1   ="FLG_C1_3_1";
    public static final String   FLG_C1_3_2   ="FLG_C1_3_2";
    public static final String   FLG_C1_3_3   ="FLG_C1_3_3";
    public static final String   FLG_C1_3_4   ="FLG_C1_3_4";
    public static final String   FLG_C1_3_5   ="FLG_C1_3_5";
    public static final String   FLG_C1_3_6   ="FLG_C1_3_6";
                                                  
    public static final String   FLG_C1_4_1   ="FLG_C1_4_1";
    public static final String   FLG_C1_4_2   ="FLG_C1_4_2";
    public static final String   FLG_C1_4_3   ="FLG_C1_4_3";
    public static final String   FLG_C1_4_4   ="FLG_C1_4_4";
    public static final String   FLG_C1_4_5   ="FLG_C1_4_5";
    public static final String   FLG_C1_4_6   ="FLG_C1_4_6";
                                                      
    public static final String   FLG_C1_5_1   ="FLG_C1_5_1";
    public static final String   FLG_C1_5_2   ="FLG_C1_5_2";
    public static final String   FLG_C1_5_3   ="FLG_C1_5_3";
    public static final String   FLG_C1_5_4   ="FLG_C1_5_4";
    public static final String   FLG_C1_5_5   ="FLG_C1_5_5";
    public static final String   FLG_C1_5_6   ="FLG_C1_5_6";
                                                     
    public static final String   FLG_C1_6_1   ="FLG_C1_6_1";
    public static final String   FLG_C1_6_2   ="FLG_C1_6_2";
    public static final String   FLG_C1_6_3   ="FLG_C1_6_3";
    public static final String   FLG_C1_6_4   ="FLG_C1_6_4";
    public static final String   FLG_C1_6_5   ="FLG_C1_6_5";
    public static final String   FLG_C1_6_6   ="FLG_C1_6_6";
                                                  
    public static final String   FLG_C2_1_1   ="FLG_C2_1_1";
    public static final String   FLG_C2_1_2   ="FLG_C2_1_2";
    public static final String   FLG_C2_1_3   ="FLG_C2_1_3";
    public static final String   FLG_C2_1_4   ="FLG_C2_1_4";
    public static final String   FLG_C2_1_5   ="FLG_C2_1_5";
    public static final String   FLG_C2_1_6   ="FLG_C2_1_6";
                                                       
    public static final String   FLG_C2_2_1   ="FLG_C2_2_1";
    public static final String   FLG_C2_2_2   ="FLG_C2_2_2";
    public static final String   FLG_C2_2_3   ="FLG_C2_2_3";
    public static final String   FLG_C2_2_4   ="FLG_C2_2_4";
    public static final String   FLG_C2_2_5   ="FLG_C2_2_5";
    public static final String   FLG_C2_2_6   ="FLG_C2_2_6";
                                                    
    public static final String   FLG_C2_3_1   ="FLG_C2_3_1";
    public static final String   FLG_C2_3_2   ="FLG_C2_3_2";
    public static final String   FLG_C2_3_3   ="FLG_C2_3_3";
    public static final String   FLG_C2_3_4   ="FLG_C2_3_4";
    public static final String   FLG_C2_3_5   ="FLG_C2_3_5";
    public static final String   FLG_C2_3_6   ="FLG_C2_3_6";
                                                     
    public static final String   FLG_C2_4_1   ="FLG_C2_4_1";
    public static final String   FLG_C2_4_2   ="FLG_C2_4_2";
    public static final String   FLG_C2_4_3   ="FLG_C2_4_3";
    public static final String   FLG_C2_4_4   ="FLG_C2_4_4";
    public static final String   FLG_C2_4_5   ="FLG_C2_4_5";
    public static final String   FLG_C2_4_6   ="FLG_C2_4_6";
                                                     
    public static final String   FLG_C3_1_1   ="FLG_C3_1_1";
    public static final String   FLG_C3_1_2   ="FLG_C3_1_2";
    public static final String   FLG_C3_1_3   ="FLG_C3_1_3";
    public static final String   FLG_C3_1_4   ="FLG_C3_1_4";
    public static final String   FLG_C3_1_5   ="FLG_C3_1_5";
    public static final String   FLG_C3_1_6   ="FLG_C3_1_6";
                                                 
    public static final String   FLG_C3_2_1   ="FLG_C3_2_1";
    public static final String   FLG_C3_2_2   ="FLG_C3_2_2";
    public static final String   FLG_C3_2_3   ="FLG_C3_2_3";
    public static final String   FLG_C3_2_4   ="FLG_C3_2_4";
    public static final String   FLG_C3_2_5   ="FLG_C3_2_5";
    public static final String   FLG_C3_2_6   ="FLG_C3_2_6";
                                                            
    public static final String   FLG_C3_3_1   ="FLG_C3_3_1";
    public static final String   FLG_C3_3_2   ="FLG_C3_3_2";
    public static final String   FLG_C3_3_3   ="FLG_C3_3_3";
    public static final String   FLG_C3_3_4   ="FLG_C3_3_4";
    public static final String   FLG_C3_3_5   ="FLG_C3_3_5";
    public static final String   FLG_C3_3_6   ="FLG_C3_3_6";
                                                         
    public static final String   FLG_C3_4_1   ="FLG_C3_4_1";
    public static final String   FLG_C3_4_2   ="FLG_C3_4_2";
    public static final String   FLG_C3_4_3   ="FLG_C3_4_3";
    public static final String   FLG_C3_4_4   ="FLG_C3_4_4";
    public static final String   FLG_C3_4_5   ="FLG_C3_4_5";
    public static final String   FLG_C3_4_6   ="FLG_C3_4_6";
                                                            
                                                            
                                                            
                                                            
    public static final String   NOTE_A_15 ="NOTE_A_15";    
                                                            
    public static final String   NOTE_B_18 ="NOTE_B_18";    
                                                            
    public static final String   NOTE_C1 ="NOTE_C1";    
        
                                                            
    public static final String   NOTE_C2_1 ="NOTE_C2_1";    
    public static final String   NOTE_C2_2 ="NOTE_C2_2";    
    public static final String   NOTE_C2_3 ="NOTE_C2_3";    
    public static final String   NOTE_C2_4 ="NOTE_C2_4";    
                                                            
    public static final String   NOTE_C3_3 ="NOTE_C3_3";    
    public static final String   NOTE_C3_4 ="NOTE_C3_4";    

    public LiMod64DAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public LiMod64DAO(DBTransaction transact) throws AppCrash {

        super(transact, TABLE_NAME);
    }

    public LiMod64DAO(DBTransaction transact, String tableName) throws AppCrash {

        super(transact, tableName);
    }

    @Override
    protected void init() throws AppCrash {

        super.init();
     
        addNoStringField(ID_AZIENDA, Integer.class);
        addNoStringField(ID_IMPIANTO, Integer.class);
        addNoStringField(ID_AREA, Integer.class);
        addNoStringField(ID_MODULO_PARENT, Integer.class);
        addNoStringField(ID_COORD_GEST_INT, Integer.class);
        
        
        
        addNoStringField(FLG_A_1_1, Boolean.class);
        addNoStringField(FLG_A_1_2, Boolean.class);
        addNoStringField(FLG_A_1_3, Boolean.class);
        addNoStringField(FLG_A_1_4, Boolean.class);
        addNoStringField(FLG_A_1_5, Boolean.class);
        addNoStringField(FLG_A_1_6, Boolean.class);
                                           
        addNoStringField(FLG_A_2_1, Boolean.class);
        addNoStringField(FLG_A_2_2, Boolean.class);
        addNoStringField(FLG_A_2_3, Boolean.class);
        addNoStringField(FLG_A_2_4, Boolean.class);
        addNoStringField(FLG_A_2_5, Boolean.class);
        addNoStringField(FLG_A_2_6, Boolean.class);
                                    
        addNoStringField(FLG_A_3_1, Boolean.class);
        addNoStringField(FLG_A_3_2, Boolean.class);
        addNoStringField(FLG_A_3_3, Boolean.class);
        addNoStringField(FLG_A_3_4, Boolean.class);
        addNoStringField(FLG_A_3_5, Boolean.class);
        addNoStringField(FLG_A_3_6, Boolean.class);
                                     
        addNoStringField(FLG_A_4_1, Boolean.class);
        addNoStringField(FLG_A_4_2, Boolean.class);
        addNoStringField(FLG_A_4_3, Boolean.class);
        addNoStringField(FLG_A_4_4, Boolean.class);
        addNoStringField(FLG_A_4_5, Boolean.class);
        addNoStringField(FLG_A_4_6, Boolean.class);
                                       
        addNoStringField(FLG_A_5_1, Boolean.class);
        addNoStringField(FLG_A_5_2, Boolean.class);
        addNoStringField(FLG_A_5_3, Boolean.class);
        addNoStringField(FLG_A_5_4, Boolean.class);
        addNoStringField(FLG_A_5_5, Boolean.class);
        addNoStringField(FLG_A_5_6, Boolean.class);
                                       
        addNoStringField(FLG_A_6_1, Boolean.class);
        addNoStringField(FLG_A_6_2, Boolean.class);
        addNoStringField(FLG_A_6_3, Boolean.class);
        addNoStringField(FLG_A_6_4, Boolean.class);
        addNoStringField(FLG_A_6_5, Boolean.class);
        addNoStringField(FLG_A_6_6, Boolean.class);
                                      
        addNoStringField(FLG_A_7_1, Boolean.class);
        addNoStringField(FLG_A_7_2, Boolean.class);
        addNoStringField(FLG_A_7_3, Boolean.class);
        addNoStringField(FLG_A_7_4, Boolean.class);
        addNoStringField(FLG_A_7_5, Boolean.class);
        addNoStringField(FLG_A_7_6, Boolean.class);
                                       
        addNoStringField(FLG_A_8_1, Boolean.class);
        addNoStringField(FLG_A_8_2, Boolean.class);
        addNoStringField(FLG_A_8_3, Boolean.class);
        addNoStringField(FLG_A_8_4, Boolean.class);
        addNoStringField(FLG_A_8_5, Boolean.class);
        addNoStringField(FLG_A_8_6, Boolean.class);
                                    
        addNoStringField(FLG_A_9_1, Boolean.class);
        addNoStringField(FLG_A_9_2, Boolean.class);
        addNoStringField(FLG_A_9_3, Boolean.class);
        addNoStringField(FLG_A_9_4, Boolean.class);
        addNoStringField(FLG_A_9_5, Boolean.class);
        addNoStringField(FLG_A_9_6, Boolean.class);
                                                           
        addNoStringField(FLG_A_10_1, Boolean.class);
        addNoStringField(FLG_A_10_2, Boolean.class);
        addNoStringField(FLG_A_10_3, Boolean.class);
        addNoStringField(FLG_A_10_4, Boolean.class);
        addNoStringField(FLG_A_10_5, Boolean.class);
        addNoStringField(FLG_A_10_6, Boolean.class);
                                        
        addNoStringField(FLG_A_11_1, Boolean.class);
        addNoStringField(FLG_A_11_2, Boolean.class);
        addNoStringField(FLG_A_11_3, Boolean.class);
        addNoStringField(FLG_A_11_4, Boolean.class);
        addNoStringField(FLG_A_11_5, Boolean.class);
        addNoStringField(FLG_A_11_6, Boolean.class);
                                         
        addNoStringField(FLG_A_12_1, Boolean.class);
        addNoStringField(FLG_A_12_2, Boolean.class);
        addNoStringField(FLG_A_12_3, Boolean.class);
        addNoStringField(FLG_A_12_4, Boolean.class);
        addNoStringField(FLG_A_12_5, Boolean.class);
        addNoStringField(FLG_A_12_6, Boolean.class);
                                         
        addNoStringField(FLG_A_13_1, Boolean.class);
        addNoStringField(FLG_A_13_2, Boolean.class);
        addNoStringField(FLG_A_13_3, Boolean.class);
        addNoStringField(FLG_A_13_4, Boolean.class);
        addNoStringField(FLG_A_13_5, Boolean.class);
        addNoStringField(FLG_A_13_6, Boolean.class);
                                          
        addNoStringField(FLG_A_14_1, Boolean.class);
        addNoStringField(FLG_A_14_2, Boolean.class);
        addNoStringField(FLG_A_14_3, Boolean.class);
        addNoStringField(FLG_A_14_4, Boolean.class);
        addNoStringField(FLG_A_14_5, Boolean.class);
        addNoStringField(FLG_A_14_6, Boolean.class);
                                          
        addNoStringField(FLG_A_15_1, Boolean.class);
        addNoStringField(FLG_A_15_2, Boolean.class);
        addNoStringField(FLG_A_15_3, Boolean.class);
        addNoStringField(FLG_A_15_4, Boolean.class);
        addNoStringField(FLG_A_15_5, Boolean.class);
        addNoStringField(FLG_A_15_6, Boolean.class);
                                                                
        addNoStringField(FLG_B_1_1, Boolean.class);
        addNoStringField(FLG_B_1_2, Boolean.class);
        addNoStringField(FLG_B_1_3, Boolean.class);
        addNoStringField(FLG_B_1_4, Boolean.class);
        addNoStringField(FLG_B_1_5, Boolean.class);
        addNoStringField(FLG_B_1_6, Boolean.class);
                                   
        addNoStringField(FLG_B_2_1, Boolean.class);
        addNoStringField(FLG_B_2_2, Boolean.class);
        addNoStringField(FLG_B_2_3, Boolean.class);
        addNoStringField(FLG_B_2_4, Boolean.class);
        addNoStringField(FLG_B_2_5, Boolean.class);
        addNoStringField(FLG_B_2_6, Boolean.class);
                                   
        addNoStringField(FLG_B_3_1, Boolean.class);
        addNoStringField(FLG_B_3_2, Boolean.class);
        addNoStringField(FLG_B_3_3, Boolean.class);
        addNoStringField(FLG_B_3_4, Boolean.class);
        addNoStringField(FLG_B_3_5, Boolean.class);
        addNoStringField(FLG_B_3_6, Boolean.class);
                                     
        addNoStringField(FLG_B_4_1, Boolean.class);
        addNoStringField(FLG_B_4_2, Boolean.class);
        addNoStringField(FLG_B_4_3, Boolean.class);
        addNoStringField(FLG_B_4_4, Boolean.class);
        addNoStringField(FLG_B_4_5, Boolean.class);
        addNoStringField(FLG_B_4_6, Boolean.class);
                                    
        addNoStringField(FLG_B_5_1, Boolean.class);
        addNoStringField(FLG_B_5_2, Boolean.class);
        addNoStringField(FLG_B_5_3, Boolean.class);
        addNoStringField(FLG_B_5_4, Boolean.class);
        addNoStringField(FLG_B_5_5, Boolean.class);
        addNoStringField(FLG_B_5_6, Boolean.class);
                                   
        addNoStringField(FLG_B_6_1, Boolean.class);
        addNoStringField(FLG_B_6_2, Boolean.class);
        addNoStringField(FLG_B_6_3, Boolean.class);
        addNoStringField(FLG_B_6_4, Boolean.class);
        addNoStringField(FLG_B_6_5, Boolean.class);
        addNoStringField(FLG_B_6_6, Boolean.class);
                                  
        addNoStringField(FLG_B_7_1, Boolean.class);
        addNoStringField(FLG_B_7_2, Boolean.class);
        addNoStringField(FLG_B_7_3, Boolean.class);
        addNoStringField(FLG_B_7_4, Boolean.class);
        addNoStringField(FLG_B_7_5, Boolean.class);
        addNoStringField(FLG_B_7_6, Boolean.class);
                                  
        addNoStringField(FLG_B_8_1, Boolean.class);
        addNoStringField(FLG_B_8_2, Boolean.class);
        addNoStringField(FLG_B_8_3, Boolean.class);
        addNoStringField(FLG_B_8_4, Boolean.class);
        addNoStringField(FLG_B_8_5, Boolean.class);
        addNoStringField(FLG_B_8_6, Boolean.class);
                                   
        addNoStringField(FLG_B_9_1, Boolean.class);
        addNoStringField(FLG_B_9_2, Boolean.class);
        addNoStringField(FLG_B_9_3, Boolean.class);
        addNoStringField(FLG_B_9_4, Boolean.class);
        addNoStringField(FLG_B_9_5, Boolean.class);
        addNoStringField(FLG_B_9_6, Boolean.class);
                                                                 
        addNoStringField(FLG_B_10_1, Boolean.class);
        addNoStringField(FLG_B_10_2, Boolean.class);
        addNoStringField(FLG_B_10_3, Boolean.class);
        addNoStringField(FLG_B_10_4, Boolean.class);
        addNoStringField(FLG_B_10_5, Boolean.class);
        addNoStringField(FLG_B_10_6, Boolean.class);
                                   
        addNoStringField(FLG_B_11_1, Boolean.class);
        addNoStringField(FLG_B_11_2, Boolean.class);
        addNoStringField(FLG_B_11_3, Boolean.class);
        addNoStringField(FLG_B_11_4, Boolean.class);
        addNoStringField(FLG_B_11_5, Boolean.class);
        addNoStringField(FLG_B_11_6, Boolean.class);
                                   
        addNoStringField(FLG_B_12_1, Boolean.class);
        addNoStringField(FLG_B_12_2, Boolean.class);
        addNoStringField(FLG_B_12_3, Boolean.class);
        addNoStringField(FLG_B_12_4, Boolean.class);
        addNoStringField(FLG_B_12_5, Boolean.class);
        addNoStringField(FLG_B_12_6, Boolean.class);
                                   
        addNoStringField(FLG_B_13_1, Boolean.class);
        addNoStringField(FLG_B_13_2, Boolean.class);
        addNoStringField(FLG_B_13_3, Boolean.class);
        addNoStringField(FLG_B_13_4, Boolean.class);
        addNoStringField(FLG_B_13_5, Boolean.class);
        addNoStringField(FLG_B_13_6, Boolean.class);
                                   
        addNoStringField(FLG_B_14_1, Boolean.class);
        addNoStringField(FLG_B_14_2, Boolean.class);
        addNoStringField(FLG_B_14_3, Boolean.class);
        addNoStringField(FLG_B_14_4, Boolean.class);
        addNoStringField(FLG_B_14_5, Boolean.class);
        addNoStringField(FLG_B_14_6, Boolean.class);
                                   
        addNoStringField(FLG_B_15_1, Boolean.class);
        addNoStringField(FLG_B_15_2, Boolean.class);
        addNoStringField(FLG_B_15_3, Boolean.class);
        addNoStringField(FLG_B_15_4, Boolean.class);
        addNoStringField(FLG_B_15_5, Boolean.class);
        addNoStringField(FLG_B_15_6, Boolean.class);
                                   
        addNoStringField(FLG_B_16_1, Boolean.class);
        addNoStringField(FLG_B_16_2, Boolean.class);
        addNoStringField(FLG_B_16_3, Boolean.class);
        addNoStringField(FLG_B_16_4, Boolean.class);
        addNoStringField(FLG_B_16_5, Boolean.class);
        addNoStringField(FLG_B_16_6, Boolean.class);
                                   
        addNoStringField(FLG_B_17_1, Boolean.class);
        addNoStringField(FLG_B_17_2, Boolean.class);
        addNoStringField(FLG_B_17_3, Boolean.class);
        addNoStringField(FLG_B_17_4, Boolean.class);
        addNoStringField(FLG_B_17_5, Boolean.class);
        addNoStringField(FLG_B_17_6, Boolean.class);
                                   
        addNoStringField(FLG_B_18_1, Boolean.class);
        addNoStringField(FLG_B_18_2, Boolean.class);
        addNoStringField(FLG_B_18_3, Boolean.class);
        addNoStringField(FLG_B_18_4, Boolean.class);
        addNoStringField(FLG_B_18_5, Boolean.class);
        addNoStringField(FLG_B_18_6, Boolean.class);
                                   
        addNoStringField(FLG_C1_1_1, Boolean.class);
        addNoStringField(FLG_C1_1_2, Boolean.class);
        addNoStringField(FLG_C1_1_3, Boolean.class);
        addNoStringField(FLG_C1_1_4, Boolean.class);
        addNoStringField(FLG_C1_1_5, Boolean.class);
        addNoStringField(FLG_C1_1_6, Boolean.class);
                                   
        addNoStringField(FLG_C1_2_1, Boolean.class);
        addNoStringField(FLG_C1_2_2, Boolean.class);
        addNoStringField(FLG_C1_2_3, Boolean.class);
        addNoStringField(FLG_C1_2_4, Boolean.class);
        addNoStringField(FLG_C1_2_5, Boolean.class);
        addNoStringField(FLG_C1_2_6, Boolean.class);
                                   
        addNoStringField(FLG_C1_3_1, Boolean.class);
        addNoStringField(FLG_C1_3_2, Boolean.class);
        addNoStringField(FLG_C1_3_3, Boolean.class);
        addNoStringField(FLG_C1_3_4, Boolean.class);
        addNoStringField(FLG_C1_3_5, Boolean.class);
        addNoStringField(FLG_C1_3_6, Boolean.class);
                                   
        addNoStringField(FLG_C1_4_1, Boolean.class);
        addNoStringField(FLG_C1_4_2, Boolean.class);
        addNoStringField(FLG_C1_4_3, Boolean.class);
        addNoStringField(FLG_C1_4_4, Boolean.class);
        addNoStringField(FLG_C1_4_5, Boolean.class);
        addNoStringField(FLG_C1_4_6, Boolean.class);
                                   
        addNoStringField(FLG_C1_5_1, Boolean.class);
        addNoStringField(FLG_C1_5_2, Boolean.class);
        addNoStringField(FLG_C1_5_3, Boolean.class);
        addNoStringField(FLG_C1_5_4, Boolean.class);
        addNoStringField(FLG_C1_5_5, Boolean.class);
        addNoStringField(FLG_C1_5_6, Boolean.class);
                                   
        addNoStringField(FLG_C1_6_1, Boolean.class);
        addNoStringField(FLG_C1_6_2, Boolean.class);
        addNoStringField(FLG_C1_6_3, Boolean.class);
        addNoStringField(FLG_C1_6_4, Boolean.class);
        addNoStringField(FLG_C1_6_5, Boolean.class);
        addNoStringField(FLG_C1_6_6, Boolean.class);
                                   
        addNoStringField(FLG_C2_1_1, Boolean.class);
        addNoStringField(FLG_C2_1_2, Boolean.class);
        addNoStringField(FLG_C2_1_3, Boolean.class);
        addNoStringField(FLG_C2_1_4, Boolean.class);
        addNoStringField(FLG_C2_1_5, Boolean.class);
        addNoStringField(FLG_C2_1_6, Boolean.class);
                                   
        addNoStringField(FLG_C2_2_1, Boolean.class);
        addNoStringField(FLG_C2_2_2, Boolean.class);
        addNoStringField(FLG_C2_2_3, Boolean.class);
        addNoStringField(FLG_C2_2_4, Boolean.class);
        addNoStringField(FLG_C2_2_5, Boolean.class);
        addNoStringField(FLG_C2_2_6, Boolean.class);
                                   
        addNoStringField(FLG_C2_3_1, Boolean.class);
        addNoStringField(FLG_C2_3_2, Boolean.class);
        addNoStringField(FLG_C2_3_3, Boolean.class);
        addNoStringField(FLG_C2_3_4, Boolean.class);
        addNoStringField(FLG_C2_3_5, Boolean.class);
        addNoStringField(FLG_C2_3_6, Boolean.class);
                                   
        addNoStringField(FLG_C2_4_1, Boolean.class);
        addNoStringField(FLG_C2_4_2, Boolean.class);
        addNoStringField(FLG_C2_4_3, Boolean.class);
        addNoStringField(FLG_C2_4_4, Boolean.class);
        addNoStringField(FLG_C2_4_5, Boolean.class);
        addNoStringField(FLG_C2_4_6, Boolean.class);
                                   
        addNoStringField(FLG_C3_1_1, Boolean.class);
        addNoStringField(FLG_C3_1_2, Boolean.class);
        addNoStringField(FLG_C3_1_3, Boolean.class);
        addNoStringField(FLG_C3_1_4, Boolean.class);
        addNoStringField(FLG_C3_1_5, Boolean.class);
        addNoStringField(FLG_C3_1_6, Boolean.class);
                                   
        addNoStringField(FLG_C3_2_1, Boolean.class);
        addNoStringField(FLG_C3_2_2, Boolean.class);
        addNoStringField(FLG_C3_2_3, Boolean.class);
        addNoStringField(FLG_C3_2_4, Boolean.class);
        addNoStringField(FLG_C3_2_5, Boolean.class);
        addNoStringField(FLG_C3_2_6, Boolean.class);
                                   
        addNoStringField(FLG_C3_3_1, Boolean.class);
        addNoStringField(FLG_C3_3_2, Boolean.class);
        addNoStringField(FLG_C3_3_3, Boolean.class);
        addNoStringField(FLG_C3_3_4, Boolean.class);
        addNoStringField(FLG_C3_3_5, Boolean.class);
        addNoStringField(FLG_C3_3_6, Boolean.class);
                                   
        addNoStringField(FLG_C3_4_1, Boolean.class);
        addNoStringField(FLG_C3_4_2, Boolean.class);
        addNoStringField(FLG_C3_4_3, Boolean.class);
        addNoStringField(FLG_C3_4_4, Boolean.class);
        addNoStringField(FLG_C3_4_5, Boolean.class);
        addNoStringField(FLG_C3_4_6, Boolean.class);

    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_MODULO))) {
            appendField(ID_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(NR_MODULO))) {
            appendField(NR_MODULO, whereCondition);
            if (Util.IsNotEmpty(getAttribute(NR_REV))) {
                appendField(NR_REV, whereCondition);
            }
        } else if (Util.IsNotEmpty(getAttribute(TABELLA_PARENT)) && Util.IsNotEmpty(getAttribute(ID_MODULO_PARENT))) {
            appendField(TABELLA_PARENT, whereCondition);
            appendField(ID_MODULO_PARENT, whereCondition);
        }

        return whereCondition;
    }
    
    @Override
    public void update() throws AppCrash {
        
        Integer idModulo=(Integer) getAttribute(ID_MODULO);
        LiMod64DAO daoOld=new LiMod64DAO();
        daoOld.setAttribute(ID_MODULO, idModulo);
        daoOld.retrieve();
        String nrModuloOld=(String) daoOld.getAttribute(NR_MODULO);
        String dtModuloOld=(String) daoOld.getAttribute(DT_MODULO);
        String statoOld=(String) daoOld.getAttribute(STATO);
        String nrRev=(String) daoOld.getAttribute(NR_REV);
        String dtRev=(String) daoOld.getAttribute(DT_REV);
        String tabellaParent=(String) daoOld.getAttribute(TABELLA_PARENT);
        
                
        if (Util.IsEmpty((String) getAttribute(NR_MODULO))) {
            setAttribute(NR_MODULO, nrModuloOld);
        }
        
        if (Util.IsEmpty((String) getAttribute(DT_MODULO))) {
            setAttribute(DT_MODULO, dtModuloOld);
        }
        
        if (Util.IsEmpty((String) getAttribute(STATO))) {
            setAttribute(STATO, statoOld);
        }
        
        if (Util.IsEmpty((String) getAttribute(NR_REV))) {
            setAttribute(NR_REV, nrRev);
        }
        
        if (Util.IsEmpty((String) getAttribute(DT_REV))) {
            setAttribute(DT_REV, dtRev);
        }
        
        if (Util.IsEmpty((String) getAttribute(TABELLA_PARENT))) {
            setAttribute(TABELLA_PARENT, tabellaParent);
        }
 
        
        setAttribute(STATO, StatiRichiesta.APPROVED.getCode());
        
        super.update();
        
        
    }

    @Override
    public void insert() throws AppCrash {
    	
    	cancellaBozzeStessaArea();
        
        super.insert();
        super.retrieve();
    }
    
    private void cancellaBozzeStessaArea() throws AppCrash, ParamCrash {
		Integer idArea=(Integer) getAttribute(ID_AREA);
    	String sqlStatement2 = "delete from limod64 where stato='DRA' and id_area="+idArea;
        ErrDetector.GetInstance().param(sqlStatement2 != null, "sqlStatement null");
        WebAppUtils.executeQuery(sqlStatement2);
	}

}
