package net.projectsrl.pdlweb.pdl.db;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import net.project.dataset.DataSetFactory;
import net.project.dataset.DataSet_itf;
import net.project.dataset.Row_itf;
import net.project.db.DBTransaction;
import net.project.db.WhereCondition;
import net.project.errors.AppCrash;
import net.project.misc.Util;
import net.project.servlet.frame.SsbServletRequest;
import net.project.servlet.security.UserSecurityInfo;
import net.projectsrl.dafne.core.DafneCostanti_itf;
import net.projectsrl.db.PjNDAO_base;
import net.projectsrl.pdlweb.pdl.core.StatiPDL;
import net.projectsrl.webapp.security.WebAppUserSecurityInfo;
import project.misc.Utils;

public class PDLDAO extends PjNDAO_base {

	private static final String DS_PROGRESSIVO_PDL = "DSProgressivoPDL";
	private static final String DS_PROGRESSIVO_RICHIESTA_PDL = "DSProgressivoRichiestaPDL";
	private static final String PREFISSO_NUMERAZIONE_TEMPORANEA = "T-";

	private static final String TABLE_NAME = "PDL";

	public static final String ID_PDL = "ID_PDL";
	public static final String ID_AZIENDA = "ID_AZIENDA";
	public static final String ID_IMPIANTO = "ID_IMPIANTO";
	public static final String ID_AREA = "ID_AREA";
	public static final String ID_EQUIPMENT = "ID_EQUIPMENT";
	public static final String NR_PDL = "NR_PDL";
	public static final String DT_PDL = "DT_PDL";
	public static final String DT_ATTIVAZIONE = "DT_ATTIVAZIONE";
	public static final String DT_SOSPENSIONE = "DT_SOSPENSIONE";
	public static final String DT_CHIUSURA = "DT_CHIUSURA";
	public static final String STATO = "STATO";
	public static final String IMPRESA_TESTO = "IMPRESA_TESTO";
	public static final String NOME_COGNOME_PREPOSTO_IMPRESA = "NOME_COGNOME_PREPOSTO_IMPRESA";
	public static final String DESCRIZIONE_LAVORO = "DESCRIZIONE_LAVORO";

	public static final String FLG_TUTA_DA_LAVORO = "FLG_TUTA_DA_LAVORO";
	public static final String FLG_SCARPE_DI_SICUREZZA = "FLG_SCARPE_DI_SICUREZZA";
	public static final String FLG_GUANTI = "FLG_GUANTI";
	public static final String FLG_OCCHIALI = "FLG_OCCHIALI";
	public static final String FLG_ELMETTO = "FLG_ELMETTO";

	public static final String IMPIANTO = "IMPIANTO";
	public static final String ODL = "ODL";
	public static final String DESCR_PG = "DESCR_PG";
	public static final String DESCR_TESTO = "DESCR_TESTO";
	public static final String EQUIPMENT = "EQUIPMENT";
	public static final String FLG_MECCANICA = "FLG_MECCANICA";
	public static final String FLG_STRUMENTALE = "FLG_STRUMENTALE";
	public static final String FLG_ELETTRICA = "FLG_ELETTRICA";
	public static final String FLG_EDILE = "FLG_EDILE";
	public static final String FLG_A_FUOCO = "FLG_A_FUOCO";
	public static final String FLG_IN_QUOTA = "FLG_IN_QUOTA";
	public static final String FLG_SCAVO = "FLG_SCAVO";
	public static final String FLG_IN_SPAZI_CONFINATI = "FLG_IN_SPAZI_CONFINATI";
	public static final String FLG_COIBENTAZIONE_SCOIBENTAZIONE = "FLG_COIBENTAZIONE_SCOIBENTAZIONE";
	public static final String FLG_CIECATURA_RIMOZIONE = "FLG_CIECATURA_RIMOZIONE";
	public static final String FLG_PULIZIA_FACCHINAGGIO = "FLG_PULIZIA_FACCHINAGGIO";
	public static final String FLG_VERNICIATURA = "FLG_VERNICIATURA";
	public static final String FLG_SABBIATURA = "FLG_SABBIATURA";
	public static final String FLG_AGGOTTAMENTO = "FLG_AGGOTTAMENTO";
	public static final String ALTRO_SEZ1_TIPOLAVORO1_TESTO = "ALTRO_SEZ1_TIPOLAVORO1_TESTO";
	public static final String ALTRO_SEZ1_TIPOLAVORO2_TESTO = "ALTRO_SEZ1_TIPOLAVORO2_TESTO";
	public static final String FLG_OSSIGENO = "FLG_OSSIGENO";
	public static final String FLG_AZOTO = "FLG_AZOTO";
	public static final String FLG_ARGON = "FLG_ARGON";
	public static final String FLG_LIQUIDI_CRIOGENICI = "FLG_LIQUIDI_CRIOGENICI";
	public static final String FLG_METANO_O_GAS_NATURALE = "FLG_METANO_O_GAS_NATURALE";
	public static final String FLG_IDROCARBURI = "FLG_IDROCARBURI";
	public static final String FLG_PERLITE = "FLG_PERLITE";
	public static final String FLG_BUTANO = "FLG_BUTANO";
	public static final String FLG_SYNGAS = "FLG_SYNGAS";
	public static final String FLG_IDROGENO = "FLG_IDROGENO";
	public static final String FLG_DMDS = "FLG_DMDS";
	public static final String FLG_OLI = "FLG_OLI";
	public static final String ALTRO_SEZ1_PRODOTTI1_TESTO = "ALTRO_SEZ1_PRODOTTI1_TESTO";
	public static final String FLG_RUMORE = "FLG_RUMORE";
	public static final String FLG_ALTA_TEMPERATURA = "FLG_ALTA_TEMPERATURA";
	public static final String FLG_PRESSIONE = "FLG_PRESSIONE";
	public static final String FLG_ALTEZZA = "FLG_ALTEZZA";
	public static final String FLG_CRIOGENIA_BASSA_TEMPERATURA = "FLG_CRIOGENIA_BASSA_TEMPERATURA";
	public static final String FLG_PARTI_IN_MOVIMENTO = "FLG_PARTI_IN_MOVIMENTO";
	public static final String FLG_APPAR_CAVI_ELETTRICI = "FLG_APPAR_CAVI_ELETTRICI";
	public static final String FLG_APPAR_CAVI_ELETTRICI_2 = "FLG_APPAR_CAVI_ELETTRICI_2";
	public static final String FLG_APPAR_CAVI_ELETTRICI_3 = "FLG_APPAR_CAVI_ELETTRICI_3";
	public static final String FLG_PRODOTTI_CHIMICI = "FLG_PRODOTTI_CHIMICI";
	public static final String FLG_MATERIALI_INFIAMMABILI = "FLG_MATERIALI_INFIAMMABILI";
	public static final String ALTRO_SEZ1_ATTIVITA1_TESTO = "ALTRO_SEZ1_ATTIVITA1_TESTO";
	public static final String FLG_INTERCETTARE = "FLG_INTERCETTARE";
	public static final String FLG_DEPRESSURIZZARE = "FLG_DEPRESSURIZZARE";
	public static final String FLG_VUOTARE = "FLG_VUOTARE";
	public static final String FLG_DOPPIE_VALVOLE_CHIUSE = "FLG_DOPPIE_VALVOLE_CHIUSE";
	public static final String FLG_SPURGHI_APERTI = "FLG_SPURGHI_APERTI";
	public static final String FLG_SPURGHI_CHIUSI = "FLG_SPURGHI_CHIUSI";
	public static final String FLG_VENT_APERTI = "FLG_VENT_APERTI";
	public static final String FLG_VENT_CHIUSI = "FLG_VENT_CHIUSI";
	public static final String FLG_SCONNETTERE_LINEE = "FLG_SCONNETTERE_LINEE";
	public static final String FLG_SFLANGIARE = "FLG_SFLANGIARE";
	public static final String FLG_CONTROLLARE_PRESSIONE = "FLG_CONTROLLARE_PRESSIONE";
	public static final String FLG_CONTROLLARE_TEMPERATURA = "FLG_CONTROLLARE_TEMPERATURA";
	public static final String FLG_AREARE = "FLG_AREARE";
	public static final String FLG_DOPPIA_VALVOLA_CHIUSA_E_SPURGO_INTERMEDIO_APERTO = "FLG_DOPPIA_VALVOLA_CHIUSA_E_SPURGO_INTERMEDIO_APERTO";
	public static final String FLG_LAVARE_CON_PRODOTTI_COMPATIBILI_ALIMENTARE_FARMACEUTICO = "FLG_LAVARE_CON_PRODOTTI_COMPATIBILI_ALIMENTARE_FARMACEUTICO";
	public static final String FLG_PREDISPORRE_CARTELLI_DIVIETO_MANOVRA = "FLG_PREDISPORRE_CARTELLI_DIVIETO_MANOVRA";
	public static final String FLG_PREDISPORRE_ILLUMINAZIONE = "FLG_PREDISPORRE_ILLUMINAZIONE";
	public static final String FLG_PREDISPORRE_ATTREZZATURE_ANTINCENDIO = "FLG_PREDISPORRE_ATTREZZATURE_ANTINCENDIO";
	public static final String FLG_LOCALIZZARE_LINEE_INTERRATE = "FLG_LOCALIZZARE_LINEE_INTERRATE";
	public static final String FLG_APPLICARE_SEGNALETICA = "FLG_APPLICARE_SEGNALETICA";
	public static final String FLG_TRIPODE = "FLG_TRIPODE";
	public static final String FLG_PROTEGGERE_FOGNE = "FLG_PROTEGGERE_FOGNE";
	public static final String FLG_PONTEGGIO_PIANO_DI_LAVORO = "FLG_PONTEGGIO_PIANO_DI_LAVORO";
	public static final String FLG_PIATTAFORMA = "FLG_PIATTAFORMA";
	public static final String FLG_DELIMITARE_ZONA_LAVORO = "FLG_DELIMITARE_ZONA_LAVORO";
	public static final String FLG_SQUADRA_DI_SOCCORSO_IN_STAND_BY = "FLG_SQUADRA_DI_SOCCORSO_IN_STAND_BY";
	public static final String FLG_INSUFFLARE_AZOTO = "FLG_INSUFFLARE_AZOTO";
	public static final String FLG_INSUFFLARE_ARIA = "FLG_INSUFFLARE_ARIA";
	public static final String FLG_ESEGUIRE_DOPO_COMPLETAMENTO_E_CHIUSURA_PERMESSI_N = "FLG_ESEGUIRE_DOPO_COMPLETAMENTO_E_CHIUSURA_PERMESSI_N";
	public static final String PDL1_NR = "PDL1_NR";
	public static final String FLG_CIECATURA = "FLG_CIECATURA";
	public static final String SCHEDA_CIECATURA_NR = "SCHEDA_CIECATURA_NR";
	public static final String FLG_RIMOZIONE_TEMPORANEA_EIS = "FLG_RIMOZIONE_TEMPORANEA_EIS";
	public static final String SCHEDA_EIS_NR = "SCHEDA_EIS_NR";
	public static final String FLG_MESSA_IN_SICUREZZA_ELETTRICA = "FLG_MESSA_IN_SICUREZZA_ELETTRICA";
	public static final String MESSA_IN_SICUREZZA_ELETTRICA_TESTO = "MESSA_IN_SICUREZZA_ELETTRICA_TESTO";
	public static final String FLG_PROVA_DI_ABITABILITA = "FLG_PROVA_DI_ABITABILITA";
	public static final String PROVA_DI_ABITABILITA_TESTO = "PROVA_DI_ABITABILITA_TESTO";
	public static final String FLG_PROVA_DI_ESPLOSIVITA = "FLG_PROVA_DI_ESPLOSIVITA";
	public static final String PROVA_DI_ESPLOSIVITA_TESTO = "PROVA_DI_ESPLOSIVITA_TESTO";
	public static final String FLG_CONSEGNA_VERIFICA_ACCETTAZIONE = "FLG_CONSEGNA_VERIFICA_ACCETTAZIONE";
	public static final String LI_MOD_47 = "LI_MOD_47";
	public static final String FLG_INTERFERENZA_LI_MOD_40_LI_SR_MOD_89 = "FLG_INTERFERENZA_LI_MOD_40_LI_SR_MOD_89";
	public static final String FLG_LAVARE_CON = "FLG_LAVARE_CON";
	public static final String LAVARE_CON_TESTO = "LAVARE_CON_TESTO";
	public static final String FLG_BONIFICARE_CON = "FLG_BONIFICARE_CON";
	public static final String BONIFICARE_CON_TESTO = "BONIFICARE_CON_TESTO";
	public static final String FLG_PREDISPORRE_MANICHETTE_CON = "FLG_PREDISPORRE_MANICHETTE_CON";
	public static final String PREDISPORRE_MANICHETTE_CON_TEST = "PREDISPORRE_MANICHETTE_CON_TEST";
	public static final String ALTRO_SEZ1_PRESCRIZIONI_SIC1_TESTO = "ALTRO_SEZ1_PRESCRIZIONI_SIC1_TESTO";
	public static final String ALTRI_DOCUMENTI_SITO = "ALTRI_DOCUMENTI_SITO";

	public static final String FLG_PRODUZIONE_RIFIUTI_PERICOLOSI = "FLG_PRODUZIONE_RIFIUTI_PERICOLOSI";
	public static final String FLG_PRODUZIONE_RIFIUTI_NON_PERICOLOSI = "FLG_PRODUZIONE_RIFIUTI_NON_PERICOLOSI";
	public static final String FLG_RISCHIO_SPANDIMENTO = "FLG_RISCHIO_SPANDIMENTO";
	public static final String FLG_EMISSIONI_IN_ATMOSFERA = "FLG_EMISSIONI_IN_ATMOSFERA";
	public static final String PRECAUZIONI_DA_ADOTTARE = "PRECAUZIONI_DA_ADOTTARE";
	public static final String FLG_MASCHERA_ANTIPOLVERE = "FLG_MASCHERA_ANTIPOLVERE";
	public static final String FLG_MASCHERA_ANTIGAS = "FLG_MASCHERA_ANTIGAS";
	public static final String FLG_AUTORESPIRATORE = "FLG_AUTORESPIRATORE";
	public static final String FLG_VISIERA_DI_PROTEZIONE = "FLG_VISIERA_DI_PROTEZIONE";
	public static final String FLG_PROTEZIONI_AURICOLARI = "FLG_PROTEZIONI_AURICOLARI";
	public static final String FLG_RILEVATORE_OSSIGENO = "FLG_RILEVATORE_OSSIGENO";
	public static final String FLG_ESPLOSIMETRO = "FLG_ESPLOSIMETRO";
	public static final String FLG_INDUMENTI_IGNIFUGHI_SPECIALI = "FLG_INDUMENTI_IGNIFUGHI_SPECIALI";
	public static final String FLG_STIVALI_ANTIFORTUNISTICI = "FLG_STIVALI_ANTIFORTUNISTICI";
	public static final String FLG_IMBRAC_CINTURA_DI_SICUREZZA = "FLG_IMBRAC_CINTURA_DI_SICUREZZA";
	public static final String FLG_GUANTI_PROTETTIVI_PER_AGENTI_CHIMICI = "FLG_GUANTI_PROTETTIVI_PER_AGENTI_CHIMICI";
	public static final String FLG_PROTEZIONE_VISO_E_CORPO_PER_AGENTI_CHIMICI = "FLG_PROTEZIONE_VISO_E_CORPO_PER_AGENTI_CHIMICI";
	public static final String FLG_GUANTI_PROTEZIONE_PER_ALTE_TEMPERATURE = "FLG_GUANTI_PROTEZIONE_PER_ALTE_TEMPERATURE";
	public static final String FLG_GUANTI_CRIOGENICI = "FLG_GUANTI_CRIOGENICI";
	public static final String FLG_GUANTI_DIELETTRICI = "FLG_GUANTI_DIELETTRICI";
	public static final String FLG_ELMETTO_DIELETTRICO = "FLG_ELMETTO_DIELETTRICO";
	public static final String ULTERIORI_PRESCRIZIONI_DI_SICUREZZA_DA_ADOTTARE = "ULTERIORI_PRESCRIZIONI_DI_SICUREZZA_DA_ADOTTARE";
	public static final String DATA_SEZ1 = "DATA_SEZ1";
	public static final String RESPONSABILE_CENTRALE_DELEGATO = "RESPONSABILE_CENTRALE_DELEGATO";
	public static final String FLG_PERSONALE_INTERNO = "FLG_PERSONALE_INTERNO";
	public static final String FLG_PERSONALE_ESTERNO = "FLG_PERSONALE_ESTERNO";
	public static final String FLG_CASSETTA_ATTREZZI = "FLG_CASSETTA_ATTREZZI";
	public static final String FLG_MOLA = "FLG_MOLA";
	public static final String FLG_TAGLIA_TUBI = "FLG_TAGLIA_TUBI";
	public static final String FLG_ELETTROSALDATRICE = "FLG_ELETTROSALDATRICE";
	public static final String FLG_MOTOSALDATRICE = "FLG_MOTOSALDATRICE";
	public static final String FLG_PARANCO = "FLG_PARANCO";
	public static final String FLG_TRAPANO_ELETTRICO = "FLG_TRAPANO_ELETTRICO";
	public static final String FLG_TRAPANO_A_TENUTA = "FLG_TRAPANO_A_TENUTA";
	public static final String FLG_SABBIATRICE = "FLG_SABBIATRICE";
	public static final String FLG_ATTREZZI_ANTISCINTILLA = "FLG_ATTREZZI_ANTISCINTILLA";
	public static final String FLG_CARROPONTE = "FLG_CARROPONTE";
	public static final String FLG_ESCAVATRICE = "FLG_ESCAVATRICE";
	public static final String FLG_LANCIA_ACQUA_IN_PRESSIONE = "FLG_LANCIA_ACQUA_IN_PRESSIONE";
	public static final String FLG_TRAPANO_PNEUMATICO = "FLG_TRAPANO_PNEUMATICO";
	public static final String FLG_CANNELLO_OSSIACETILENICO = "FLG_CANNELLO_OSSIACETILENICO";
	public static final String FLG_UTENSILI_A_TENSIONE_DI_SICUREZZA = "FLG_UTENSILI_A_TENSIONE_DI_SICUREZZA";
	public static final String FLG_ATTREZZATURE_LAVAGGI_CHIMICI = "FLG_ATTREZZATURE_LAVAGGI_CHIMICI";
	public static final String FLG_ATTREZZATURE_PER_RADIOGRAFIE = "FLG_ATTREZZATURE_PER_RADIOGRAFIE";
	public static final String FLG_AUTOGRU = "FLG_AUTOGRU";
	public static final String FLG_AUTOMEZZO = "FLG_AUTOMEZZO";
	public static final String FLG_CARRELLO_ELEVATORE = "FLG_CARRELLO_ELEVATORE";
	public static final String FLG_MOTOPOMPA = "FLG_MOTOPOMPA";
	public static final String FLG_AUTOSPURGO = "FLG_AUTOSPURGO";
	public static final String FLG_SCOVOLATRICE = "FLG_SCOVOLATRICE";
	public static final String FLG_MOTOCOMPRESSORE = "FLG_MOTOCOMPRESSORE";
	public static final String ALTRA_ATTREZZATURA = "ALTRA_ATTREZZATURA";
	public static final String PREPARAZ_DEL_LAVORO_PRECAUZIONI_PREVISTE_DURANTE_LESECUZIONE = "PREPARAZ_DEL_LAVORO_PRECAUZIONI_PREVISTE_DURANTE_LESECUZIONE";
	public static final String RISCHI_SPECIFICI_ATT_INDICAZ_DEI_MEZZI_PROTEZIONE_PREVISTI = "RISCHI_SPECIFICI_ATT_INDICAZ_DEI_MEZZI_PROTEZIONE_PREVISTI";
	public static final String NOME_COGNOME_DELEGATO_LAVORI_AL = "NOME_COGNOME_DELEGATO_LAVORI_AL";
	public static final String STATO_PERMESSO = "STATO_PERMESSO";
	public static final String DESCRIZIONE_DELLA_SOSPENSIONE = "DESCRIZIONE_DELLA_SOSPENSIONE";
	public static final String FLG_AREA_RIPULITA = "FLG_AREA_RIPULITA";
	public static final String AREA_RIPULITA_TESTO = "AREA_RIPULITA_TESTO";
	public static final String CAPO_TURNO = "CAPO_TURNO";
	public static final String DATA_CHIUSURA = "DATA_CHIUSURA";
	public static final String FLG_CONVALIDA = "FLG_CONVALIDA";
	public static final String FLG_PONTEGGIO = "FLG_PONTEGGIO";
	public static final String FLG_VAPORE = "FLG_VAPORE";
	public static final String FLG_PERMESSO_DI_ACCESSO = "FLG_PERMESSO_DI_ACCESSO";
	public static final String FLG_LOTO = "FLG_LOTO";
	public static final String LOTO_TESTO = "LOTO_TESTO";
	public static final String FLG_SOLO_TAG_OUT = "FLG_SOLO_TAG_OUT";
	public static final String ALTRO_SEZ1_PRESCRIZIONI_SIC2_TESTO = "ALTRO_SEZ1_PRESCRIZIONI_SIC2_TESTO";
	public static final String FLG_TA = "FLG_TA";
	public static final String ANNO_TA = "ANNO_TA";
	public static final String DATA_ATTIVO = "DATA_ATTIVO";
	public static final String ORA_ATTIVO = "ORA_ATTIVO";
	public static final String ORA_CHIUSURA = "ORA_CHIUSURA";
	public static final String POSIZIONE_IN_PLANIMTERIA = "POSIZIONE_IN_PLANIMTERIA";
	public static final String FLG_STAMPA_TURNO = "FLG_STAMPA_TURNO";
	public static final String COD_MOD70 = "COD_MOD70";
	public static final String FLG_COMP_MOD70 = "FLG_COMP_MOD70";
	public static final String FLG_VALUTA_INTERFERENZA = "FLG_VALUTA_INTERFERENZA";
	public static final String FLG_PRIMA_VALUTAZIONE = "FLG_PRIMA_VALUTAZIONE";
	public static final String FLG_SUCCESSIVA_VALUTAZIONE = "FLG_SUCCESSIVA_VALUTAZIONE";
	public static final String FLG_DISTANZA_SICUREZZA_SI = "FLG_DISTANZA_SICUREZZA_SI";
	public static final String FLG_DISTANZA_SICUREZZA_NO = "FLG_DISTANZA_SICUREZZA_NO";
	public static final String COORDINATORE_MOD70 = "COORDINATORE_MOD70";
	public static final String ORA_MOD70 = "ORA_MOD70";
	public static final String DATA_MOD70 = "DATA_MOD70";

	public static final String TS_ATTIVAZIONE = "TS_ATTIVAZIONE";
	public static final String ID_UTENTE_ATTIVAZIONE = "ID_UTENTE_ATTIVAZIONE";
	public static final String CODICE_TURNO_ATTIVAZIONE = "CODICE_TURNO_ATTIVAZIONE";
	public static final String TS_FINE_TURNO_ATTIVAZIONE = "TS_FINE_TURNO_ATTIVAZIONE";
    public static final String MOTIVO_SCADENZA = "MOTIVO_SCADENZA";


	public static final String TS_INS = "TS_INS";
	public static final String ID_UTENTE_INS = "ID_UTENTE_INS";
	public static final String TS_DEL = "TS_DEL";
	public static final String ID_UTENTE_DEL = "ID_UTENTE_DEL";

	public static final String ID_UTENTE_VERIFICA = "ID_UTENTE_VERIFICA";
	public static final String TS_VERIFICA = "TS_VERIFICA";
	public static final String RISPOSTA_VERIFICA = "RISPOSTA_VERIFICA";
	
	public static final String FLG_PERMESSO_ELETTRICO = "FLG_PERMESSO_ELETTRICO";
	public static final String PERMESSO_ELETTRICO_TESTO = "PERMESSO_ELETTRICO_TESTO";
	
	public static final String TS_MOD = "TS_MOD";
	public static final String ID_UTENTE_MOD = "ID_UTENTE_MOD";
	
	public static final String DT_APERTURA = "DT_APERTURA";
	public static final String DT_PRIMA_ATTIVAZIONE = "DT_PRIMA_ATTIVAZIONE";
	public static final String ID_UTENTE_CHIUSURA = "ID_UTENTE_CHIUSURA";
	public static final String ID_UTENTE_PRIMA_ATTIVAZIONE = "ID_UTENTE_PRIMA_ATTIVAZIONE";
	
	public static final String CANCELLATO = "CANCELLATO";
	public static final String ID_UTENTE_CANCELLAZIONE = "ID_UTENTE_CANCELLAZIONE";
	public static final String TS_CANCELLAZIONE = "TS_CANCELLAZIONE";
	
	public static final String NOME_COGNOME_PREPOSTO_IMPRESA_2 = "NOME_COGNOME_PREPOSTO_IMPRESA_2";
	public static final String NOME_COGNOME_DELEGATO_LAVORI_AL_2 = "NOME_COGNOME_DELEGATO_LAVORI_AL_2";
	
	public static final String FLG_MODULO_INGLESE = "FLG_MODULO_INGLESE";
	
	
	public static final String REVISIONE = "REVISIONE";
	public static final String V9_ATTREZZATURA_INSTALLAZIONE_OGGETTO ="V9_ATTREZZATURA_INSTALLAZIONE_OGGETTO";
	public static final String V9_ATTREZZATURA_INSTALLAZIONE_SICUREZZA ="V9_ATTREZZATURA_INSTALLAZIONE_SICUREZZA";
	public static final String V9_NOMINATIVO_RICHIEDENTE ="V9_NOMINATIVO_RICHIEDENTE";
	public static final String FLG_V9_ELETTRICA ="FLG_V9_ELETTRICA";
	public static final String FLG_V9_CHIMICA ="FLG_V9_CHIMICA";
	public static final String FLG_V9_PRESSIONE ="FLG_V9_PRESSIONE";
	public static final String FLG_V9_IDRAULICA ="FLG_V9_IDRAULICA";
	public static final String FLG_V9_SOST_PERICOLOSE ="FLG_V9_SOST_PERICOLOSE";
	public static final String FLG_V9_ALTRA_ENERG_MECC ="FLG_V9_ALTRA_ENERG_MECC";
	public static final String FLG_V9_TERMICA ="FLG_V9_TERMICA";
	public static final String FLG_V9_SOST_INFIAMMABILI_ESPLOSIVE ="FLG_V9_SOST_INFIAMMABILI_ESPLOSIVE";
	public static final String FLG_V9_ALTEZZA_INF_2M ="FLG_V9_ALTEZZA_INF_2M";
	public static final String FLG_V9_ALTEZZA_SUP_2M ="FLG_V9_ALTEZZA_SUP_2M";
	public static final String FLG_V9_SOLLEVAMENTI ="FLG_V9_SOLLEVAMENTI";
	public static final String FLG_V9_ALTRO ="FLG_V9_ALTRO";
	public static final String FLG_V9_APP_CAVI_ELETTRICI ="FLG_V9_APP_CAVI_ELETTRICI";
	public static final String V9_ALTRO_SEZ1_ATTIVITA1_TESTO ="V9_ALTRO_SEZ1_ATTIVITA1_TESTO";
	public static final String FLG_V9_SPAZI_ANGUSTI ="FLG_V9_SPAZI_ANGUSTI";
	public static final String FLG_V9_MICROCLIMA ="FLG_V9_MICROCLIMA";
	
	public static final String FLG_V9_ISOLARE = "FLG_V9_ISOLARE";
	public static final String FLG_V9_PULIZIA_OSSIGENO = "FLG_V9_PULIZIA_OSSIGENO";
	public static final String FLG_V9_SOFFIAGGIO_AZOTO = "FLG_V9_SOFFIAGGIO_AZOTO";
	public static final String FLG_V9_SOFFIAGGIO_ARIA = "FLG_V9_SOFFIAGGIO_ARIA";
	public static final String FLG_V9_VENTILAZIONE_FORZATA = "FLG_V9_VENTILAZIONE_FORZATA";
	public static final String FLG_V9_ANALIZZATORE_GAS_TOSSICI = "FLG_V9_ANALIZZATORE_GAS_TOSSICI";
	public static final String FLG_V9_PROTEGGERE_CADITOIE_TOMBINI = "FLG_V9_PROTEGGERE_CADITOIE_TOMBINI";
	public static final String FLG_V9_SUPERVISIONE_DELEGATO_LAVORI_AL = "FLG_V9_SUPERVISIONE_DELEGATO_LAVORI_AL";
	public static final String FLG_V9_VISIONARE_SCHEDE_SICUREZZA = "FLG_V9_VISIONARE_SCHEDE_SICUREZZA";
	public static final String FLG_V9_VISIONARE_DOCUMENTI_SITO = "FLG_V9_VISIONARE_DOCUMENTI_SITO";
	public static final String FLG_V9_PDL_A_CALDO = "FLG_V9_PDL_A_CALDO";
	public static final String FLG_V9_SALDATURA = "FLG_V9_SALDATURA";
	public static final String FLG_V9_TAGLIO_MOLATURA = "FLG_V9_TAGLIO_MOLATURA";
	public static final String FLG_V9_VERIFICA_PIANO_SOLLEVAMENTO = "FLG_V9_VERIFICA_PIANO_SOLLEVAMENTO";
	public static final String FLG_V9_PRODUZIONE_RIFIUTI_DA_CARATTERIZZARE = "FLG_V9_PRODUZIONE_RIFIUTI_DA_CARATTERIZZARE";

	public static final String V9_ALTRO_SEZ1_DISPOSITIVI_PROTEZIONE = "V9_ALTRO_SEZ1_DISPOSITIVI_PROTEZIONE";
	public static final String V9_SEZ2_ALTRO_MISURE_SICUREZZA = "V9_SEZ2_ALTRO_MISURE_SICUREZZA";
	public static final String V9_SEZ2_RISCHI_SPECIFICI_NOMINATIVO = "V9_SEZ2_RISCHI_SPECIFICI_NOMINATIVO";
	public static final String V9_ALTRO_SEZ1_ASPETTI_AMBIENTALI = "V9_ALTRO_SEZ1_ASPETTI_AMBIENTALI";
	public static final String V9_ALTRO_SEZ1_PERICOLO_RISCHIO = "V9_ALTRO_SEZ1_PERICOLO_RISCHIO";
	public static final String MOTIVAZIONE = "MOTIVAZIONE";
	
	public static final String ID_V9_NOMINATIVO_RICHIEDENTE = "ID_V9_NOMINATIVO_RICHIEDENTE";
	public static final String ID_V9_SEZ2_RISCHI_SPECIFICI_NOMINATIVO = "ID_V9_SEZ2_RISCHI_SPECIFICI_NOMINATIVO";	
	
	private String              _dipendenze               = null;

	public PDLDAO() throws AppCrash {

		super(TABLE_NAME);
	}

	public PDLDAO(DBTransaction transact) throws AppCrash {

		super(transact, TABLE_NAME);
	}

	public PDLDAO(DBTransaction transact, String tableName) throws AppCrash {

		super(transact, tableName);
	}

	@Override
	protected void init() throws AppCrash {

		super.init();

		addNoStringField(ID_PDL, Integer.class);
		addNoStringField(ID_AZIENDA, Integer.class);
		addNoStringField(ID_IMPIANTO, Integer.class);
		addNoStringField(ID_AREA, Integer.class);
		addNoStringField(ID_EQUIPMENT, Integer.class);

		addNoStringField(FLG_MECCANICA, Boolean.class);
		addNoStringField(FLG_STRUMENTALE, Boolean.class);
		addNoStringField(FLG_ELETTRICA, Boolean.class);
		addNoStringField(FLG_EDILE, Boolean.class);
		addNoStringField(FLG_A_FUOCO, Boolean.class);
		addNoStringField(FLG_IN_QUOTA, Boolean.class);
		addNoStringField(FLG_SCAVO, Boolean.class);
		addNoStringField(FLG_IN_SPAZI_CONFINATI, Boolean.class);
		addNoStringField(FLG_COIBENTAZIONE_SCOIBENTAZIONE, Boolean.class);
		addNoStringField(FLG_CIECATURA_RIMOZIONE, Boolean.class);
		addNoStringField(FLG_PULIZIA_FACCHINAGGIO, Boolean.class);
		addNoStringField(FLG_VERNICIATURA, Boolean.class);
		addNoStringField(FLG_SABBIATURA, Boolean.class);
		addNoStringField(FLG_AGGOTTAMENTO, Boolean.class);

		addNoStringField(FLG_OSSIGENO, Boolean.class);
		addNoStringField(FLG_AZOTO, Boolean.class);
		addNoStringField(FLG_ARGON, Boolean.class);
		addNoStringField(FLG_LIQUIDI_CRIOGENICI, Boolean.class);
		addNoStringField(FLG_METANO_O_GAS_NATURALE, Boolean.class);
		addNoStringField(FLG_IDROCARBURI, Boolean.class);
		addNoStringField(FLG_PERLITE, Boolean.class);
		addNoStringField(FLG_BUTANO, Boolean.class);
		addNoStringField(FLG_SYNGAS, Boolean.class);
		addNoStringField(FLG_IDROGENO, Boolean.class);
		addNoStringField(FLG_DMDS, Boolean.class);
		addNoStringField(FLG_OLI, Boolean.class);

		addNoStringField(FLG_RUMORE, Boolean.class);
		addNoStringField(FLG_ALTA_TEMPERATURA, Boolean.class);
		addNoStringField(FLG_PRESSIONE, Boolean.class);
		addNoStringField(FLG_ALTEZZA, Boolean.class);
		addNoStringField(FLG_CRIOGENIA_BASSA_TEMPERATURA, Boolean.class);
		addNoStringField(FLG_PARTI_IN_MOVIMENTO, Boolean.class);
		addNoStringField(FLG_APPAR_CAVI_ELETTRICI, Boolean.class);
		addNoStringField(FLG_APPAR_CAVI_ELETTRICI_2, Boolean.class);
		addNoStringField(FLG_APPAR_CAVI_ELETTRICI_3, Boolean.class);
		addNoStringField(FLG_PRODOTTI_CHIMICI, Boolean.class);
		addNoStringField(FLG_MATERIALI_INFIAMMABILI, Boolean.class);

		addNoStringField(FLG_INTERCETTARE, Boolean.class);
		addNoStringField(FLG_DEPRESSURIZZARE, Boolean.class);
		addNoStringField(FLG_VUOTARE, Boolean.class);
		addNoStringField(FLG_DOPPIE_VALVOLE_CHIUSE, Boolean.class);
		addNoStringField(FLG_SPURGHI_APERTI, Boolean.class);
		addNoStringField(FLG_SPURGHI_CHIUSI, Boolean.class);
		addNoStringField(FLG_VENT_APERTI, Boolean.class);
		addNoStringField(FLG_VENT_CHIUSI, Boolean.class);
		addNoStringField(FLG_SCONNETTERE_LINEE, Boolean.class);
		addNoStringField(FLG_SFLANGIARE, Boolean.class);
		addNoStringField(FLG_CONTROLLARE_PRESSIONE, Boolean.class);
		addNoStringField(FLG_CONTROLLARE_TEMPERATURA, Boolean.class);
		addNoStringField(FLG_AREARE, Boolean.class);
		addNoStringField(FLG_DOPPIA_VALVOLA_CHIUSA_E_SPURGO_INTERMEDIO_APERTO, Boolean.class);
		addNoStringField(FLG_LAVARE_CON_PRODOTTI_COMPATIBILI_ALIMENTARE_FARMACEUTICO, Boolean.class);
		addNoStringField(FLG_PREDISPORRE_CARTELLI_DIVIETO_MANOVRA, Boolean.class);
		addNoStringField(FLG_PREDISPORRE_ILLUMINAZIONE, Boolean.class);
		addNoStringField(FLG_PREDISPORRE_ATTREZZATURE_ANTINCENDIO, Boolean.class);
		addNoStringField(FLG_LOCALIZZARE_LINEE_INTERRATE, Boolean.class);
		addNoStringField(FLG_APPLICARE_SEGNALETICA, Boolean.class);
		addNoStringField(FLG_TRIPODE, Boolean.class);
		addNoStringField(FLG_PROTEGGERE_FOGNE, Boolean.class);
		addNoStringField(FLG_PONTEGGIO_PIANO_DI_LAVORO, Boolean.class);
		addNoStringField(FLG_PIATTAFORMA, Boolean.class);
		addNoStringField(FLG_DELIMITARE_ZONA_LAVORO, Boolean.class);
		addNoStringField(FLG_SQUADRA_DI_SOCCORSO_IN_STAND_BY, Boolean.class);
		addNoStringField(FLG_INSUFFLARE_AZOTO, Boolean.class);
		addNoStringField(FLG_INSUFFLARE_ARIA, Boolean.class);
		addNoStringField(FLG_ESEGUIRE_DOPO_COMPLETAMENTO_E_CHIUSURA_PERMESSI_N, Boolean.class);

		addNoStringField(FLG_CIECATURA, Boolean.class);

		addNoStringField(FLG_RIMOZIONE_TEMPORANEA_EIS, Boolean.class);

		addNoStringField(FLG_MESSA_IN_SICUREZZA_ELETTRICA, Boolean.class);

		addNoStringField(FLG_PROVA_DI_ABITABILITA, Boolean.class);

		addNoStringField(FLG_PROVA_DI_ESPLOSIVITA, Boolean.class);

		addNoStringField(FLG_CONSEGNA_VERIFICA_ACCETTAZIONE, Boolean.class);

		addNoStringField(FLG_INTERFERENZA_LI_MOD_40_LI_SR_MOD_89, Boolean.class);
		addNoStringField(FLG_LAVARE_CON, Boolean.class);

		addNoStringField(FLG_BONIFICARE_CON, Boolean.class);

		addNoStringField(FLG_PREDISPORRE_MANICHETTE_CON, Boolean.class);

		addNoStringField(FLG_PRODUZIONE_RIFIUTI_PERICOLOSI, Boolean.class);
		addNoStringField(FLG_PRODUZIONE_RIFIUTI_NON_PERICOLOSI, Boolean.class);
		addNoStringField(FLG_RISCHIO_SPANDIMENTO, Boolean.class);
		addNoStringField(FLG_EMISSIONI_IN_ATMOSFERA, Boolean.class);

		addNoStringField(FLG_TUTA_DA_LAVORO, Boolean.class);
		addNoStringField(FLG_SCARPE_DI_SICUREZZA, Boolean.class);
		addNoStringField(FLG_GUANTI, Boolean.class);
		addNoStringField(FLG_OCCHIALI, Boolean.class);
		addNoStringField(FLG_ELMETTO, Boolean.class);
		addNoStringField(FLG_MASCHERA_ANTIPOLVERE, Boolean.class);
		addNoStringField(FLG_MASCHERA_ANTIGAS, Boolean.class);
		addNoStringField(FLG_AUTORESPIRATORE, Boolean.class);
		addNoStringField(FLG_VISIERA_DI_PROTEZIONE, Boolean.class);
		addNoStringField(FLG_PROTEZIONI_AURICOLARI, Boolean.class);
		addNoStringField(FLG_RILEVATORE_OSSIGENO, Boolean.class);
		addNoStringField(FLG_ESPLOSIMETRO, Boolean.class);
		addNoStringField(FLG_INDUMENTI_IGNIFUGHI_SPECIALI, Boolean.class);
		addNoStringField(FLG_STIVALI_ANTIFORTUNISTICI, Boolean.class);
		addNoStringField(FLG_IMBRAC_CINTURA_DI_SICUREZZA, Boolean.class);
		addNoStringField(FLG_GUANTI_PROTETTIVI_PER_AGENTI_CHIMICI, Boolean.class);
		addNoStringField(FLG_PROTEZIONE_VISO_E_CORPO_PER_AGENTI_CHIMICI, Boolean.class);
		addNoStringField(FLG_GUANTI_PROTEZIONE_PER_ALTE_TEMPERATURE, Boolean.class);
		addNoStringField(FLG_GUANTI_CRIOGENICI, Boolean.class);
		addNoStringField(FLG_GUANTI_DIELETTRICI, Boolean.class);
		addNoStringField(FLG_ELMETTO_DIELETTRICO, Boolean.class);

		addNoStringField(FLG_PERSONALE_INTERNO, Boolean.class);
		addNoStringField(FLG_PERSONALE_ESTERNO, Boolean.class);

		addNoStringField(FLG_CASSETTA_ATTREZZI, Boolean.class);
		addNoStringField(FLG_MOLA, Boolean.class);
		addNoStringField(FLG_TAGLIA_TUBI, Boolean.class);
		addNoStringField(FLG_ELETTROSALDATRICE, Boolean.class);
		addNoStringField(FLG_MOTOSALDATRICE, Boolean.class);
		addNoStringField(FLG_PARANCO, Boolean.class);
		addNoStringField(FLG_TRAPANO_ELETTRICO, Boolean.class);
		addNoStringField(FLG_TRAPANO_A_TENUTA, Boolean.class);
		addNoStringField(FLG_SABBIATRICE, Boolean.class);
		addNoStringField(FLG_ATTREZZI_ANTISCINTILLA, Boolean.class);
		addNoStringField(FLG_CARROPONTE, Boolean.class);
		addNoStringField(FLG_ESCAVATRICE, Boolean.class);
		addNoStringField(FLG_LANCIA_ACQUA_IN_PRESSIONE, Boolean.class);
		addNoStringField(FLG_TRAPANO_PNEUMATICO, Boolean.class);
		addNoStringField(FLG_CANNELLO_OSSIACETILENICO, Boolean.class);
		addNoStringField(FLG_UTENSILI_A_TENSIONE_DI_SICUREZZA, Boolean.class);
		addNoStringField(FLG_ATTREZZATURE_LAVAGGI_CHIMICI, Boolean.class);
		addNoStringField(FLG_ATTREZZATURE_PER_RADIOGRAFIE, Boolean.class);
		addNoStringField(FLG_AUTOGRU, Boolean.class);
		addNoStringField(FLG_AUTOMEZZO, Boolean.class);
		addNoStringField(FLG_CARRELLO_ELEVATORE, Boolean.class);
		addNoStringField(FLG_MOTOPOMPA, Boolean.class);
		addNoStringField(FLG_AUTOSPURGO, Boolean.class);
		addNoStringField(FLG_SCOVOLATRICE, Boolean.class);
		addNoStringField(FLG_MOTOCOMPRESSORE, Boolean.class);

		addNoStringField(FLG_AREA_RIPULITA, Boolean.class);

		addNoStringField(FLG_CONVALIDA, Boolean.class);
		addNoStringField(FLG_PONTEGGIO, Boolean.class);
		addNoStringField(FLG_VAPORE, Boolean.class);
		addNoStringField(FLG_PERMESSO_DI_ACCESSO, Boolean.class);
		addNoStringField(FLG_LOTO, Boolean.class);

		addNoStringField(FLG_SOLO_TAG_OUT, Boolean.class);

		addNoStringField(FLG_TA, Boolean.class);

		addNoStringField(FLG_STAMPA_TURNO, Boolean.class);

		addNoStringField(FLG_COMP_MOD70, Boolean.class);
		addNoStringField(FLG_VALUTA_INTERFERENZA, Boolean.class);
		addNoStringField(FLG_PRIMA_VALUTAZIONE, Boolean.class);
		addNoStringField(FLG_SUCCESSIVA_VALUTAZIONE, Boolean.class);
		addNoStringField(FLG_DISTANZA_SICUREZZA_SI, Boolean.class);
		addNoStringField(FLG_DISTANZA_SICUREZZA_NO, Boolean.class);

		addNoStringField(DATA_SEZ1, Timestamp.class);

		addNoStringField(DATA_CHIUSURA, Timestamp.class);
		addNoStringField(DATA_ATTIVO, Timestamp.class);
		addNoStringField(ORA_ATTIVO, Timestamp.class);
		addNoStringField(ORA_CHIUSURA, Timestamp.class);
		addNoStringField(ORA_MOD70, Timestamp.class);
		addNoStringField(DATA_MOD70, Timestamp.class);

		addNoStringField(ANNO_TA, Integer.class);

		addNoStringField(TS_ATTIVAZIONE, Timestamp.class);
		addNoStringField(ID_UTENTE_ATTIVAZIONE, Integer.class);
		addNoStringField(TS_FINE_TURNO_ATTIVAZIONE, Timestamp.class);

		addNoStringField(TS_INS, Timestamp.class);
		addNoStringField(ID_UTENTE_INS, Integer.class);
		addNoStringField(TS_DEL, Timestamp.class);
		addNoStringField(ID_UTENTE_DEL, Integer.class);
		
		addNoStringField(ID_UTENTE_VERIFICA, Integer.class);
		addNoStringField(TS_VERIFICA, Timestamp.class);
		
		addNoStringField(FLG_PERMESSO_ELETTRICO, Boolean.class);
		
		addNoStringField(TS_MOD, Timestamp.class);
		addNoStringField(ID_UTENTE_MOD, Integer.class);
		
		addNoStringField(ID_UTENTE_CHIUSURA, Integer.class);
		addNoStringField(ID_UTENTE_PRIMA_ATTIVAZIONE, Integer.class);
		addNoStringField(CANCELLATO, Boolean.class);
		
		addNoStringField(ID_UTENTE_CANCELLAZIONE, Integer.class);
		addNoStringField(TS_CANCELLAZIONE, Timestamp.class);
		
		addNoStringField(FLG_MODULO_INGLESE, Boolean.class);
		
		
		addNoStringField(FLG_V9_ELETTRICA, Boolean.class);
		addNoStringField(FLG_V9_CHIMICA, Boolean.class);
		addNoStringField(FLG_V9_PRESSIONE, Boolean.class);
		addNoStringField(FLG_V9_IDRAULICA, Boolean.class);
		addNoStringField(FLG_V9_SOST_PERICOLOSE, Boolean.class);
		addNoStringField(FLG_V9_ALTRA_ENERG_MECC, Boolean.class);
		addNoStringField(FLG_V9_TERMICA, Boolean.class);
		addNoStringField(FLG_V9_SOST_INFIAMMABILI_ESPLOSIVE, Boolean.class);
		addNoStringField(FLG_V9_ALTEZZA_INF_2M, Boolean.class);
		addNoStringField(FLG_V9_ALTEZZA_SUP_2M, Boolean.class);
		addNoStringField(FLG_V9_SOLLEVAMENTI, Boolean.class);
		addNoStringField(FLG_V9_ALTRO, Boolean.class);
		addNoStringField(FLG_V9_APP_CAVI_ELETTRICI, Boolean.class);
		addNoStringField(FLG_V9_SPAZI_ANGUSTI, Boolean.class);
		addNoStringField(FLG_V9_MICROCLIMA, Boolean.class);
		
		addNoStringField(FLG_V9_ISOLARE, Boolean.class);
		addNoStringField(FLG_V9_PULIZIA_OSSIGENO, Boolean.class);
		addNoStringField(FLG_V9_SOFFIAGGIO_AZOTO, Boolean.class);
		addNoStringField(FLG_V9_SOFFIAGGIO_ARIA, Boolean.class);
		addNoStringField(FLG_V9_VENTILAZIONE_FORZATA, Boolean.class);
		addNoStringField(FLG_V9_ANALIZZATORE_GAS_TOSSICI, Boolean.class);
		addNoStringField(FLG_V9_PROTEGGERE_CADITOIE_TOMBINI, Boolean.class);
		addNoStringField(FLG_V9_SUPERVISIONE_DELEGATO_LAVORI_AL, Boolean.class);
		addNoStringField(FLG_V9_VISIONARE_SCHEDE_SICUREZZA, Boolean.class);
		addNoStringField(FLG_V9_VISIONARE_DOCUMENTI_SITO, Boolean.class);
		addNoStringField(FLG_V9_PDL_A_CALDO, Boolean.class);
		addNoStringField(FLG_V9_SALDATURA, Boolean.class);
		addNoStringField(FLG_V9_TAGLIO_MOLATURA, Boolean.class);
		addNoStringField(FLG_V9_VERIFICA_PIANO_SOLLEVAMENTO, Boolean.class);
		addNoStringField(FLG_V9_PRODUZIONE_RIFIUTI_DA_CARATTERIZZARE, Boolean.class);
		
		addNoStringField(ID_V9_NOMINATIVO_RICHIEDENTE, Integer.class);
		addNoStringField(ID_V9_SEZ2_RISCHI_SPECIFICI_NOMINATIVO, Integer.class);
		
	}

	@Override
	protected WhereCondition whereCondition() throws AppCrash {

		WhereCondition whereCondition = new WhereCondition(this);

		if (Util.IsNotEmpty(getAttribute(ID_PDL))) {
			appendField(ID_PDL, whereCondition);
		} else if (Util.IsNotEmpty(getAttribute(ID_IMPIANTO)) && Util.IsNotEmpty(getAttribute(NR_PDL))) {
			appendField(ID_IMPIANTO, whereCondition);
			appendField(NR_PDL, whereCondition);
		}

		return whereCondition;
	}

	@Override
	public void insert() throws AppCrash {

		Integer idAzienda = (Integer) getAttribute(ID_AZIENDA);
		String stato = (String) getAttribute(STATO);

		String nrPDL;

		if (StatiPDL.IN_ATTESA.getCode().equals(stato)) {

			// e' una richiesta (bozza) in attesa di approvazione: numerazione
			// temporanea dedicata, non ufficiale, che non consuma la numerazione del PDL
			nrPDL = getNextSequentialTemporaryNumber(idAzienda.toString());

		} else {

			nrPDL = getNextSequentialNumber(idAzienda.toString());
		}

		setAttribute(NR_PDL, nrPDL);

		super.insert();
		retrieve();
		
		
       /* String profiloUtente=getSpecificUserInfo(userInfo).getRoleId();
        if(profiloUtente.contains("PRE")){
        	String idModulo = getAttribute(ID_PDL).toString();
            String modulo = "PDL";
        	SendMail mail = new SendMail(getSpecificUserInfo(userInfo));
        	PDLDAO formDao = new PDLDAO();
            mail.sendMailRequestPDL(formDao, Constants_itf.APPROVE, StatiPDL.IN_ATTESA.getCode(), StatiPDL.APERTO.getCode(),modulo, idModulo);
        }*/
		
		
		inserisciDipendenze(); 


	}
	
	private void inserisciDipendenze() throws AppCrash {

        Integer idPDL = (Integer) getAttribute(ID_PDL);
        String idPDLString = idPDL.toString();

        if (Util.IsNotEmpty(_dipendenze)) {
            new DipendenzeDAO().delete(idPDLString);
            new DipendenzeDAO().insert(_dipendenze, idPDL);
        }
        
    }

	private String getNextSequentialNumber(String idAzienda) throws AppCrash {

		Integer prossimoProgressivo = getNextSequentialProgressive(DS_PROGRESSIVO_PDL, idAzienda);

		SimpleDateFormat sdf = new SimpleDateFormat("yy", java.util.Locale.ITALY);
		String yy = sdf.format(new Date());
		return String.format("%04d", prossimoProgressivo) + "-" + yy;

	}

	/**
	 * Numero "temporaneo" assegnato alle sole richieste di PDL non ancora completate (STATO=WAI). Ha un formato
	 * dedicato (es. "T-0001-26") con un progressivo a se stante che NON incide sulla numerazione ufficiale dei
	 * PDL: verra' sostituito dal numero ufficiale (getNextSequentialNumber) solo quando la richiesta viene
	 * completata e diventa un PDL vero e proprio.
	 */
	private String getNextSequentialTemporaryNumber(String idAzienda) throws AppCrash {

		Integer prossimoProgressivo = getNextSequentialProgressive(DS_PROGRESSIVO_RICHIESTA_PDL, idAzienda);

		SimpleDateFormat sdf = new SimpleDateFormat("yy", java.util.Locale.ITALY);
		String yy = sdf.format(new Date());
		return PREFISSO_NUMERAZIONE_TEMPORANEA + String.format("%04d", prossimoProgressivo) + "-" + yy;

	}

	private Integer getNextSequentialProgressive(String dsName, String idAzienda) throws AppCrash {

		DataSet_itf dataSet = null;

		DataSetFactory dsFactory = DataSetFactory.getInstance();
		Integer lastSequentialNumeber = new Integer(0);

		try {
			dataSet = dsFactory.makeDataSet("", dsName);

			HashMap<String, String> param = new HashMap<String, String>();
			param.put(ID_AZIENDA, idAzienda);
			dataSet.setParam(param);
			dataSet.open();

			if (dataSet.hasMoreElements()) {
				Row_itf dbRow = (Row_itf) dataSet.nextElement();

				if (dbRow != null) {
					String strLast = (String) dbRow.getField("ultimo");
					if (Util.IsNotEmpty(strLast)) {
						lastSequentialNumeber = Integer.valueOf(strLast);
						lastSequentialNumeber += 1;
					}
				}
			}
		} catch (AppCrash ac) {
			ac.logContext(this.getClass().getName(),
					"Errore nella ricerca dell'ultimo progressivo del dataset " + dsName);
			throw ac;
		} finally {
			// chiude il dataset per il conteggio degli elementi trovati
			if (dataSet != null) {
				try {
					dataSet.close();
				} catch (Throwable t) {
					AppCrash ac = new AppCrash(t);
					ac.logContext(this.getClass().getName(), "Errore nella close del dataset " + dsName);
				}
			}
		}

		if (lastSequentialNumeber == null || lastSequentialNumeber.intValue() == 0) {
			lastSequentialNumeber = new Integer(1);
		}

		return lastSequentialNumeber;
	}

	@Override
	public void setAttribute(String attribID, Object value) throws AppCrash {

		if (attribID.equals(ID_EQUIPMENT) && value instanceof String && value != null
				&& ((String) value).contains("|")) {
			String idEquipment = ((String) value).split("\\|")[0];
			super.setAttribute(attribID, idEquipment);
			return;
		}

		super.setAttribute(attribID, value);
	}

	@Override
	protected void putFieldInTemplateMap(Map<String, Object> map, String fieldName, String elementValue) {

		elementValue = StringEscapeUtils.escapeJavaScript(elementValue.trim());

		map.put(fieldName, elementValue);
	}
	
	
	@Override
    public void delete() throws AppCrash {
		
        String idPDL = getAttributeAsString(ID_PDL);
        new DipendenzeDAO().delete(idPDL);
        super.delete();
    }
	
	
	@Override
    public void cancella(UserSecurityInfo userInfo) throws AppCrash {

		Integer idUtente=(Integer) getSpecificUserInfo(userInfo).getIdUtente();
		setAttribute("CANCELLATO", true);
		setAttribute("ID_UTENTE_CANCELLAZIONE", idUtente);
		setAttribute("TS_CANCELLAZIONE", new Timestamp(System.currentTimeMillis()));
		super.update();
		
    }
	
	@Override
    public void resume() throws AppCrash {

		setAttribute("CANCELLATO", false);
		super.update();
    }
	
	 @Override
	    public void setAttributesFromRequest(SsbServletRequest req) throws AppCrash {

	        super.setAttributesFromRequest(req);

	        _dipendenze = req.getField(DafneCostanti_itf.PDL_MULTIPLO);

	    }
	 
	 
	 @Override
	    public void update() throws AppCrash {

		 	assegnaNumeroUfficialeSeCompletataRichiesta();

		 	if(getAttribute("STATO")!=null){
		 		if(getAttribute("STATO").equals(StatiPDL.APERTO.getCode())){
					setAttribute(DT_APERTURA, Utils.getStringDataOggiRibaltata());
				}else{
					setAttribute(DT_APERTURA, "");
				}
		 	}
		 	
		 
	        super.update();

	        Integer idPDL = (Integer) getAttribute(ID_PDL);
	        String idPDLString = idPDL.toString();

	        if (Util.IsNotEmpty(_dipendenze)) {
	            new DipendenzeDAO().delete(idPDLString);
	            new DipendenzeDAO().insert(_dipendenze, idPDL);
	        }
	        
	    }

	    /**
	     * Se il PDL che si sta salvando era una richiesta in attesa di approvazione (STATO=WAI, numerazione
	     * temporanea "T-nnnn-aa") e sta transitando verso lo stato APERTO (cioe' viene completata la richiesta
	     * e diventa a tutti gli effetti un PDL), le viene assegnato in questo momento il numero di PDL
	     * "ufficiale" (progressivo definitivo, es. "0246-26") in sostituzione del numero temporaneo.
	     * 
	     * Lo stato precedente viene sempre letto dal DB (e non dall'attributo in memoria) perche' i chiamanti
	     * di update() possono gia' aver sovrascritto l'attributo STATO con il nuovo valore prima di invocare
	     * questo metodo (es. FunctionApprovalWorkFlow) oppure non avere mai valorizzato lo stato precedente
	     * (es. il form di InserimentoPDLv9, che lavora su una nuova istanza del DAO valorizzata dalla request).
	     */
	    private void assegnaNumeroUfficialeSeCompletataRichiesta() throws AppCrash {

	        Integer idPDL = (Integer) getAttribute(ID_PDL);

	        if (Util.IsEmpty(idPDL)) {
	            return;
	        }

	        PDLDAO rigaEsistente = new PDLDAO();
	        rigaEsistente.setAttribute(ID_PDL, idPDL);

	        if (!rigaEsistente.retrieve()) {
	            return;
	        }

	        String statoPrecedente = (String) rigaEsistente.getAttribute(STATO);
	        String statoNuovo = (String) getAttribute(STATO);

	        boolean eraUnaRichiestaInAttesa = StatiPDL.IN_ATTESA.getCode().equals(statoPrecedente);
	        boolean diventaUnPdlAperto = StatiPDL.APERTO.getCode().equals(statoNuovo);

	        if (eraUnaRichiestaInAttesa && diventaUnPdlAperto) {

	            Integer idAzienda = (Integer) rigaEsistente.getAttribute(ID_AZIENDA);
	            String nrPdlUfficiale = getNextSequentialNumber(idAzienda.toString());

	            setAttribute(NR_PDL, nrPdlUfficiale);
	        }
	    }


	    protected WebAppUserSecurityInfo<?> getSpecificUserInfo(UserSecurityInfo userInfo) {

	        return (WebAppUserSecurityInfo<?>) userInfo;
	    }	    

}