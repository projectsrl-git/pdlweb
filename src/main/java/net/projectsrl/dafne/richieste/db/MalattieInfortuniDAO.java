
package net.projectsrl.dafne.richieste.db;

import java.sql.Clob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.errors.ErrDetector;
import net.project.errors.Logger;
import net.project.misc.Config;
import net.project.misc.Util;
import net.projectsrl.db.PjNDAO_base;

public class MalattieInfortuniDAO extends PjNDAO_base {

    private static final String TABLE_NAME       = "MALATTIE_INFORTUNI";

    public static final String  ID_MALATTIA_INFORTUNIO = "ID_MALATTIA_INFORTUNIO";
    public static final String  ID_RICHIESTA     = "ID_RICHIESTA";
    public static final String  GIUSTIFICATIVO   = "GIUSTIFICATIVO";
    public static final String  MOTIVO           = "MOTIVO";
    public static final String  DT_DAL           = "DT_DAL";
    public static final String  DT_AL            = "DT_AL";
    public static final String  ORA_DAL          = "ORA_DAL";
    public static final String  ORA_AL           = "ORA_AL";
    public static final String  GIORNI           = "GIORNI";
    public static final String  ORE              = "ORE";
    public static final String  MINUTI           = "MINUTI";
    public static final String  NOTE             = "NOTE";
    public static final String  PROTOCOLLO_INPS             = "PROTOCOLLO_INPS";
    public static final String  FL_ULTIMO        = "FL_ULTIMO";
    public static final String  TS_INS           = "TS_INS";
    public static final String  ID_UTENTE_INS    = "ID_UTENTE_INS";

    public MalattieInfortuniDAO() throws AppCrash {

        super(TABLE_NAME);
    }

    public MalattieInfortuniDAO(DBTransaction transact) throws AppCrash {

        super(transact, Config.GetInstance().getProperty(DB_NAME_PROPERTY) + TABLE_NAME);
    }

    /**
     * il metodo compare darà esito zero se sono uguali, -1 se diversi
     * 
     * @author fmoda
     *
     */
    private class MalattieInfortuniComparator implements Comparator<MalattieInfortuniDAO> {

        @Override
        public int compare(MalattieInfortuniDAO richiesta1, MalattieInfortuniDAO richiesta2) {

            int result = -1;

            try {
                ErrDetector.GetInstance().param(richiesta1 != null, "richiesta1 is null");

                ErrDetector.GetInstance().param(richiesta2 != null, "richiesta2 is null");

                // ID_RICHIESTA
                if (!integerAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.ID_RICHIESTA)) {
                    return -1;
                }

                // ID_UTENTE_INS
                if (!integerAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.ID_UTENTE_INS)) {
                    return -1;
                }
                
                // GIUSTIFICATIVO
                if (!stringsAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.GIUSTIFICATIVO)) {
                    return -1;
                }

                // MOTIVO
                if (!stringsAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.MOTIVO)) {
                    return -1;
                }

                // DT_DAL
                if (!stringsAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.DT_DAL)) {
                    return -1;
                }

                // DT_AL
                if (!stringsAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.DT_AL)) {
                    return -1;
                }

                // ORA_DAL
                if (!stringsAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.ORA_DAL)) {
                    return -1;
                }

                // ORA_AL
                if (!stringsAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.ORA_AL)) {
                    return -1;
                }

                // NOTE
                if (!stringsAreEquals(richiesta1, richiesta2, MalattieInfortuniDAO.NOTE)) {
                    return -1;
                }
                
                result = 0;

            } catch (Throwable e) {
                AppCrash ac = new AppCrash(e);
                ac.logContext(this.getClass().getName(), "richiesta1:" + richiesta1 + " - richiesta2:" + richiesta2);
                result = -1;

            }
            return result;
        }

        private boolean integerAreEquals(MalattieInfortuniDAO richiesta1, MalattieInfortuniDAO richiesta2, String fieldName)
                throws AppCrash {

            boolean areEquals = false;
            Integer integer1 = (Integer) richiesta1.getAttribute(fieldName);
            Integer integer2 = (Integer) richiesta2.getAttribute(fieldName);
            areEquals = integer1.intValue() == integer2.intValue();
            return areEquals;
        }

        private boolean stringsAreEquals(MalattieInfortuniDAO richiesta1, MalattieInfortuniDAO richiesta2, String fieldName)
                throws AppCrash {

            boolean areEquals = false;
            String string1 = (String) richiesta1.getAttribute(fieldName);
            String string2 = (String) richiesta2.getAttribute(fieldName);
            if (string1 != null && string2 != null) {
                areEquals = string1.trim().equals(string2.trim());
            }
            return areEquals;
        }
    }

    /**
     * Questo metodo
     * 
     * @return
     * @throws AppCrash
     * 
     * @see net.ssb.db.NDAO_base#whereCondition()
     */
    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_RICHIESTA)) && Util.IsNotEmpty(getAttribute(FL_ULTIMO))) {
            appendField(ID_RICHIESTA, whereCondition);
            appendField(FL_ULTIMO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(ID_RICHIESTA))) {
            appendField(ID_RICHIESTA, whereCondition);
        }

        return whereCondition;
    }

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_MALATTIA_INFORTUNIO, Integer.class);
        addNoStringField(ID_RICHIESTA, Integer.class);
        addNoStringField(GIORNI, Integer.class);
        addNoStringField(ORE, Integer.class);
        addNoStringField(MINUTI, Integer.class);
        addNoStringField(FL_ULTIMO, Boolean.class);
        addNoStringField(TS_INS, Timestamp.class);
        addNoStringField(ID_UTENTE_INS, Integer.class);
        addNoStringField(MOTIVO, Clob.class);

    }

    @Override
    public void insert() throws AppCrash {

        setAttribute(ID_RICHIESTA, getAttribute(ID_RICHIESTA));
        setAttribute(FL_ULTIMO, Boolean.TRUE);
        setDateDifference();
        super.insert();

    }

    @Override
    public void delete() throws AppCrash {

        // il log richieste non deve essere cancellato
    }

    public void updateLastAsOldAndInsertNew() throws AppCrash {

        MalattieInfortuniDAO lastEntry = new MalattieInfortuniDAO();
        lastEntry.setAttribute(ID_RICHIESTA, getAttribute(ID_RICHIESTA));
        lastEntry.setAttribute(FL_ULTIMO, Boolean.TRUE);

        if (lastEntry.retrieve()) {

            // se esiste un log richieste verifico che ci siano modifiche rispetto alla versione salvata
            MalattieInfortuniComparator rc = new MalattieInfortuniComparator();
            if (rc.compare(this, lastEntry) != 0) {
                lastEntry.setAttribute(FL_ULTIMO, Boolean.FALSE);
                lastEntry.update(); 
                
                insert();
            }
        }
    }

    /**
     * calculate difference between 2 dates an set to GIORNI-ORE-MINUTI fields
     * 
     * @param String dateStart
     * @param Strting dateStop
     * @throws AppCrash
     */
    private void setDateDifference() throws AppCrash {

        String dateStart = (String) getAttribute(DT_DAL);
        String dateStop = (String) getAttribute(DT_AL);

        String hourStart = (String) getAttribute(ORA_DAL);
        String hourStop = (String) getAttribute(ORA_AL);

        // HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/ddHH:mm");

        Date d1 = null;
        Date d2 = null;

        try {

            d1 = format.parse(dateStart + hourStart);
            d2 = format.parse(dateStop + hourStop);

            // in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Logger.GetInstance().log0(diffDays + " days, ");
            Logger.GetInstance().log0(diffHours + " hours, ");
            Logger.GetInstance().log0(diffMinutes + " minutes, ");
            Logger.GetInstance().log0(diffSeconds + " seconds.");

            Integer days = new Integer("" + diffDays);
            Integer hours = new Integer("" + diffHours);
            Integer minutes = new Integer("" + diffMinutes);

            setAttribute(GIORNI, days);
            setAttribute(ORE, hours);
            setAttribute(MINUTI, minutes);

        } catch (Throwable th) {
            AppCrash ac = new AppCrash(th);
            ac.logContext(this.getClass().getName(), " dateStart:" + dateStart + " - dateStop:" + dateStop);
        }

    }

}