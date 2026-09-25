
package net.projectsrl.qhse.moduli.alimod80.db;

import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.projectsrl.qhse.moduli.db.AliModDAO_base;

public class AliMod80DAO extends AliModDAO_base {

	private static final String TABLE_NAME = "ALIMOD80";

	public static final String RESPONSABILE_LOCALE = "RESPONSABILE_LOCALE";
	public static final String PAESE = "PAESE";
	public static final String FILIALE = "FILIALE";

	public static final String TIPO_COMUNICAZIONE = "TIPO_COMUNICAZIONE";
	public static final String CODICE_TIPO_IMPIANTO = "CODICE_TIPO_IMPIANTO";
	public static final String CODICE_NATURA_EVENTO = "CODICE_NATURA_EVENTO";
	public static final String CODICE_PRODOTTO_PERTINENTE = "CODICE_PRODOTTO_PERTINENTE";
	public static final String CODICE_GIORNI = "CODICE_GIORNI";
	public static final String CODICE_ORE = "CODICE_ORE";
	public static final String CODICE_MIN = "CODICE_MIN";
	public static final String CODICE_ORE_LOCALE = "CODICE_ORE_LOCALE";
	public static final String CODICE_MIN_LOCALE = "CODICE_MIN_LOCALE";
	public static final String AZIONI_EVENTO = "AZIONI_EVENTO";

	public static final String CODICE_AFF_ATTREZZATURA_INTERESSATA = "CODICE_AFF_ATTREZZATURA_INTERESSATA";
	public static final String CODICE_AFF_COMP_ATTR_INTERESSATA = "CODICE_AFF_COMP_ATTR_INTERESSATA";
	public static final String CODICE_COSTI_EURO = "CODICE_COSTI_EURO";
	public static final String CODICE_LIVELLO_INCIDENTE = "CODICE_LIVELLO_INCIDENTE";
	public static final String CODICE_CLIENTE = "CODICE_CLIENTE";

	public static final String RECLAMO_CLIENTE = "RECLAMO_CLIENTE";
	public static final String FORZA_MAGGIORE = "FORZA_MAGGIORE";
	public static final String ALTRI_CLIENTI = "ALTRI_CLIENTI";

	public static final String PARTE_INTERESSATA = "PARTE_INTERESSATA";
	public static final String PRODOTTO_RILASCIATO = "PRODOTTO_RILASCIATO";
	public static final String LIVELLO_INCIDENTE = "LIVELLO_INCIDENTE";
	public static final String QUANTITA_RILASCIATA = "QUANTITA_RILASCIATA";
	public static final String UNITA_RILASCIATA = "UNITA_RILASCIATA";
	public static final String QUANTITA_MAX_PERMESSA = "QUANTITA_MAX_PERMESSA";
	public static final String UNITA_MAX_PERMESSA = "UNITA_MAX_PERMESSA";

	public static final String DESCRIZIONE_EVENTO = "DESCRIZIONE_EVENTO";
	public static final String DESCRIZIONE_AZIONI = "DESCRIZIONE_AZIONI";

	public static final String ALBERO_CAUSE = "ALBERO_CAUSE";
	public static final String CATEGORIA_PRINCIPALE = "CATEGORIA_PRINCIPALE";
	public static final String SOTTOCATEGORIA_PRINCIPALE = "SOTTOCATEGORIA_PRINCIPALE";
	public static final String CATEGORIA_SECONDARIA = "CATEGORIA_SECONDARIA";
	public static final String SOTTOCATEGORIA_SECONDARIA = "SOTTOCATEGORIA_SECONDARIA";
	public static final String INFO_SUPPLEMENTARI = "INFO_SUPPLEMENTARI";

	public static final String COGNOME_NOME = "COGNOME_NOME";
	public static final String CELLULARE = "CELLULARE";
	public static final String MAIL = "MAIL";
	public static final String DT_REPORT = "DT_REPORT";
	public static final String NUM_FILIALE = "NUM_FILIALE";
	public static final String NUM_AZIENDA = "NUM_AZIENDA";
	
	public static final String  TABELLA_PARENT        = "TABELLA_PARENT";
    public static final String  ID_MODULO_PARENT      = "ID_MODULO_PARENT";

	public AliMod80DAO() throws AppCrash {

		super(TABLE_NAME);
	}

	public AliMod80DAO(DBTransaction transact) throws AppCrash {

		super(transact, TABLE_NAME);
	}

	public AliMod80DAO(DBTransaction transact, String tableName) throws AppCrash {

		super(transact, tableName);
	}

    @Override
    protected void init() throws AppCrash {

        super.init();

        addNoStringField(ID_MODULO_PARENT, Integer.class);
    }

    @Override
    protected WhereCondition whereCondition() throws AppCrash {

        WhereCondition whereCondition = new WhereCondition(this);

        if (Util.IsNotEmpty(getAttribute(ID_MODULO))) {
            appendField(ID_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(NR_MODULO))) {
            appendField(NR_MODULO, whereCondition);
        } else if (Util.IsNotEmpty(getAttribute(TABELLA_PARENT)) && Util.IsNotEmpty(getAttribute(ID_MODULO_PARENT))) {
            appendField(TABELLA_PARENT, whereCondition);
            appendField(ID_MODULO_PARENT, whereCondition);
        }
        
        return whereCondition;
    }

}
