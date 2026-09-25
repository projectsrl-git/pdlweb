ALTER TABLE "Tab_001 Anagrafica_PdL" RENAME TO "Tab_001 Anagrafica_PdL.CAS";
ALTER TABLE "Tab_002 Capi turno" RENAME TO "Tab_002 Capi turno.CAS";
ALTER TABLE "Tab_003 Delegato Lavori" RENAME TO "Tab_003 Delegato Lavori.CAS";
ALTER TABLE "Tab_004 Ditte Terze" RENAME TO "Tab_004 Ditte Terze.CAS";
ALTER TABLE "Tab_005 Impianti" RENAME TO "Tab_005 Impianti.CAS";
ALTER TABLE "Tab_006 Personale Interno" RENAME TO "Tab_006 Personale Interno.CAS";
ALTER TABLE "Tab_007 Resp_Centrale - Delegato" RENAME TO "Tab_007 Resp_Centrale - Delegato.CAS";
ALTER TABLE "Tab_008 Lista Equipment" RENAME TO "Tab_008 Lista Equipment.CAS";

drop table if exists "Tab_010 Stato permesso";


/*

drop table if exists "Tab_001 Anagrafica_PdL.PRISMR"          ;
drop table if exists "Tab_002 Capi turno.PRISMR"              ;
drop table if exists "Tab_003 Delegato Lavori.PRISMR"         ;
drop table if exists "Tab_004 Ditte Terze.PRISMR"             ;
drop table if exists "Tab_005 Impianti.PRISMR"                ;
drop table if exists "Tab_006 Personale Interno.PRISMR"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.PRISMR";
drop table if exists "Tab_008 Lista Equipment.PRISMR"         ;

drop table if exists "Tab_001 Anagrafica_PdL.PRIASU"          ;
drop table if exists "Tab_002 Capi turno.PRIASU"              ;
drop table if exists "Tab_003 Delegato Lavori.PRIASU"         ;
drop table if exists "Tab_004 Ditte Terze.PRIASU"             ;
drop table if exists "Tab_005 Impianti.PRIASU"                ;
drop table if exists "Tab_006 Personale Interno.PRIASU"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.PRIASU";
drop table if exists "Tab_008 Lista Equipment.PRIASU"         ;

drop table if exists "Tab_001 Anagrafica_PdL.CAR"          ;
drop table if exists "Tab_002 Capi turno.CAR"              ;
drop table if exists "Tab_003 Delegato Lavori.CAR"         ;
drop table if exists "Tab_004 Ditte Terze.CAR"             ;
drop table if exists "Tab_005 Impianti.CAR"                ;
drop table if exists "Tab_006 Personale Interno.CAR"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.CAR";
drop table if exists "Tab_008 Lista Equipment.CAR"         ;

drop table if exists "Tab_001 Anagrafica_PdL.CAS"          ;
drop table if exists "Tab_002 Capi turno.CAS"              ;
drop table if exists "Tab_003 Delegato Lavori.CAS"         ;
drop table if exists "Tab_004 Ditte Terze.CAS"             ;
drop table if exists "Tab_005 Impianti.CAS"                ;
drop table if exists "Tab_006 Personale Interno.CAS"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.CAS";
drop table if exists "Tab_008 Lista Equipment.CAS"         ;

drop table if exists "Tab_001 Anagrafica_PdL.LIM"          ;
drop table if exists "Tab_002 Capi turno.LIM"              ;
drop table if exists "Tab_003 Delegato Lavori.LIM"         ;
drop table if exists "Tab_004 Ditte Terze.LIM"             ;
drop table if exists "Tab_005 Impianti.LIM"                ;
drop table if exists "Tab_006 Personale Interno.LIM"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.LIM";
drop table if exists "Tab_008 Lista Equipment.LIM"         ;


drop table if exists "Tab_001 Anagrafica_PdL.OSO"          ;
drop table if exists "Tab_002 Capi turno.OSO"              ;
drop table if exists "Tab_003 Delegato Lavori.OSO"         ;
drop table if exists "Tab_004 Ditte Terze.OSO"             ;
drop table if exists "Tab_005 Impianti.OSO"                ;
drop table if exists "Tab_006 Personale Interno.OSO"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.OSO";
drop table if exists "Tab_008 Lista Equipment.OSO"         ;


drop table if exists "Tab_001 Anagrafica_PdL.PAD"          ;
drop table if exists "Tab_002 Capi turno.PAD"              ;
drop table if exists "Tab_003 Delegato Lavori.PAD"         ;
drop table if exists "Tab_004 Ditte Terze.PAD"             ;
drop table if exists "Tab_005 Impianti.PAD"                ;
drop table if exists "Tab_006 Personale Interno.PAD"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.PAD";
drop table if exists "Tab_008 Lista Equipment.PAD"         ;


drop table if exists "Tab_001 Anagrafica_PdL.SAR"          ;
drop table if exists "Tab_002 Capi turno.SAR"              ;
drop table if exists "Tab_003 Delegato Lavori.SAR"         ;
drop table if exists "Tab_004 Ditte Terze.SAR"             ;
drop table if exists "Tab_005 Impianti.SAR"                ;
drop table if exists "Tab_006 Personale Interno.SAR"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.SAR";
drop table if exists "Tab_008 Lista Equipment.SAR"         ;


drop table if exists "Tab_001 Anagrafica_PdL.FEB"          ;
drop table if exists "Tab_002 Capi turno.FEB"              ;
drop table if exists "Tab_003 Delegato Lavori.FEB"         ;
drop table if exists "Tab_004 Ditte Terze.FEB"             ;
drop table if exists "Tab_005 Impianti.FEB"                ;
drop table if exists "Tab_006 Personale Interno.FEB"       ;
drop table if exists "Tab_007 Resp_Centrale - Delegato.FEB";
drop table if exists "Tab_008 Lista Equipment.FEB"         ;

*/

/*
select * from aziende where tipo_azienda like '%PDL%';

*/

/*
-- Table: "Tab_004 Ditte Terze"

DROP TABLE IF EXISTS PREPOSTI_IMPRESA CASCADE;

CREATE TABLE PREPOSTI_IMPRESA
(
	ID_PREPOSTO_IMPRESA SERIAL,
	ID_AZIENDA integer,
	NOME_IMPRESA character varying(50),
	NOME_COGNOME_PREPOSTO_IMPRESA character varying(50)
);
*/




/*
ALTER TABLE PDL ADD COLUMN AREA_LAVORO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN IMPIANTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN ODL character varying(50)    ;
ALTER TABLE PDL ADD COLUMN DESCR_PG character varying(50)    ;
ALTER TABLE PDL ADD COLUMN DESCR_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN EQUIPMENT character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_MECCANICA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_STRUMENTALE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ELETTRICA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_EDILE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_A_FUOCO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_IN_QUOTA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SCAVO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_IN_SPAZI_CONFINATI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_COIBENTAZIONE_SCOIBENTAZIONE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_CIECATURA_RIMOZIONE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PULIZIA_FACCHINAGGIO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_VERNICIATURA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SABBIATURA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_AGGOTTAMENTO boolean     ;
ALTER TABLE PDL ADD COLUMN ALTRO_SEZ1_TIPOLAVORO1_TESTO character varying(255)    ;
ALTER TABLE PDL ADD COLUMN ALTRO_SEZ1_TIPOLAVORO2_TESTO character varying(255)    ;

ALTER TABLE PDL ADD COLUMN FLG_OSSIGENO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_AZOTO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ARGON boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_LIQUIDI_CRIOGENICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_METANO_O_GAS_NATURALE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_IDROCARBURI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PERLITE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_BUTANO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SYNGAS boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_IDROGENO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_DMDS boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_OLI boolean     ;
ALTER TABLE PDL ADD COLUMN ALTRO_SEZ1_PRODOTTI1_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_RUMORE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ALTA_TEMPERATURA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PRESSIONE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ALTEZZA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_CRIOGENIA_BASSA_TEMPERATURA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PARTI_IN_MOVIMENTO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_APPAR_CAVI_ELETTRICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PRODOTTI_CHIMICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_MATERIALI_INFIAMMABILI boolean     ;
ALTER TABLE PDL ADD COLUMN ALTRO_SEZ1_ATTIVITA1_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_INTERCETTARE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_DEPRESSURIZZARE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_VUOTARE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_DOPPIE_VALVOLE_CHIUSE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SPURGHI_APERTI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SPURGHI_CHIUSI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_VENT_APERTI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_VENT_CHIUSI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SCONNETTERE_LINEE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SFLANGIARE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_CONTROLLARE_PRESSIONE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_CONTROLLARE_TEMPERATURA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_AREARE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_DOPPIA_VALVOLA_CHIUSA_E_SPURGO_INTERMEDIO_APERTO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_LAVARE_CON_PRODOTTI_COMPATIBILI_ALIMENTARE_FARMACEUTICO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PREDISPORRE_CARTELLI_DIVIETO_MANOVRA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PREDISPORRE_ILLUMINAZIONE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PREDISPORRE_ATTREZZATURE_ANTINCENDIO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_LOCALIZZARE_LINEE_INTERRATE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_APPLICARE_SEGNALETICA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_TRIPODE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PROTEGGERE_FOGNE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PONTEGGIO_PIANO_DI_LAVORO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PIATTAFORMA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_DELIMITARE_ZONA_LAVORO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SQUADRA_DI_SOCCORSO_IN_STAND_BY boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_INSUFFLARE_AZOTO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_INSUFFLARE_ARIA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ESEGUIRE_DOPO_COMPLETAMENTO_E_CHIUSURA_PERMESSI_N boolean     ;
ALTER TABLE PDL ADD COLUMN PDL1_NR character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_CIECATURA boolean     ;
ALTER TABLE PDL ADD COLUMN SCHEDA_CIECATURA_NR character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_RIMOZIONE_TEMPORANEA_EIS boolean     ;
ALTER TABLE PDL ADD COLUMN SCHEDA_EIS_NR character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_MESSA_IN_SICUREZZA_ELETTRICA boolean     ;
ALTER TABLE PDL ADD COLUMN MESSA_IN_SICUREZZA_ELETTRICA_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_PROVA_DI_ABITABILITA boolean     ;
ALTER TABLE PDL ADD COLUMN PROVA_DI_ABITABILITA_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_PROVA_DI_ESPLOSIVITA boolean     ;
ALTER TABLE PDL ADD COLUMN PROVA_DI_ESPLOSIVITA_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_CONSEGNA_VERIFICA_ACCETTAZIONE boolean     ;
ALTER TABLE PDL ADD COLUMN LI_MOD_47 character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_INTERFERENZA_LI_MOD_40_LI_SR_MOD_89 boolean DEFAULT true   ;
ALTER TABLE PDL ADD COLUMN FLG_LAVARE_CON boolean     ;
ALTER TABLE PDL ADD COLUMN LAVARE_CON_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_BONIFICARE_CON boolean     ;
ALTER TABLE PDL ADD COLUMN BONIFICARE_CON_TESTO text     ;
ALTER TABLE PDL ADD COLUMN FLG_PREDISPORRE_MANICHETTE_CON boolean     ;
ALTER TABLE PDL ADD COLUMN PREDISPORRE_MANICHETTE_CON_TEST character varying(50)    ;
ALTER TABLE PDL ADD COLUMN ALTRO_SEZ1_PRESCRIZIONI_SIC1_TESTO text     ;
ALTER TABLE PDL ADD COLUMN FLG_PRODUZIONE_RIFIUTI_PERICOLOSI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PRODUZIONE_RIFIUTI_NON_PERICOLOSI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_RISCHIO_SPANDIMENTO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_EMISSIONI_IN_ATMOSFERA boolean     ;
ALTER TABLE PDL ADD COLUMN PRECAUZIONI_DA_ADOTTARE text     ;
ALTER TABLE PDL ADD COLUMN FLG_TUTA_DA_LAVORO boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_SCARPE_DI_SICUREZZA boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_GUANTI boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_OCCHIALI boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_ELMETTO boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_MASCHERA_ANTIPOLVERE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_MASCHERA_ANTIGAS boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_AUTORESPIRATORE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_VISIERA_DI_PROTEZIONE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PROTEZIONI_AURICOLARI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_RILEVATORE_OSSIGENO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ESPLOSIMETRO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_INDUMENTI_IGNIFUGHI_SPECIALI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_STIVALI_ANTIFORTUNISTICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_IMBRAC_CINTURA_DI_SICUREZZA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_GUANTI_PROTETTIVI_PER_AGENTI_CHIMICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PROTEZIONE_VISO_E_CORPO_PER_AGENTI_CHIMICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_GUANTI_PROTEZIONE_PER_ALTE_TEMPERATURE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_GUANTI_CRIOGENICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_GUANTI_DIELETTRICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ELMETTO_DIELETTRICO boolean     ;
ALTER TABLE PDL ADD COLUMN ULTERIORI_PRESCRIZIONI_DI_SICUREZZA_DA_ADOTTARE text     ;
ALTER TABLE PDL ADD COLUMN DATA_SEZ1 timestamp without time zone DEFAULT now();
ALTER TABLE PDL ADD COLUMN RESPONSABILE_CENTRALE_DELEGATO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_PERSONALE_INTERNO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PERSONALE_ESTERNO boolean     ;
ALTER TABLE PDL ADD COLUMN IMPRESA_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_CASSETTA_ATTREZZI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_MOLA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_TAGLIA_TUBI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ELETTROSALDATRICE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_MOTOSALDATRICE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PARANCO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_TRAPANO_ELETTRICO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_TRAPANO_A_TENUTA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SABBIATRICE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ATTREZZI_ANTISCINTILLA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_CARROPONTE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ESCAVATRICE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_LANCIA_ACQUA_IN_PRESSIONE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_TRAPANO_PNEUMATICO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_CANNELLO_OSSIACETILENICO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_UTENSILI_A_TENSIONE_DI_SICUREZZA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ATTREZZATURE_LAVAGGI_CHIMICI boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_ATTREZZATURE_PER_RADIOGRAFIE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_AUTOGRU boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_AUTOMEZZO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_CARRELLO_ELEVATORE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_MOTOPOMPA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_AUTOSPURGO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_SCOVOLATRICE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_MOTOCOMPRESSORE boolean     ;
ALTER TABLE PDL ADD COLUMN ALTRA_ATTREZZATURA character varying(255)    ;
ALTER TABLE PDL ADD COLUMN PREPARAZ_DEL_LAVORO_PRECAUZIONI_PREVISTE_DURANTE_LESECUZIONE text     ;
ALTER TABLE PDL ADD COLUMN RISCHI_SPECIFICI_ATT_INDICAZ_DEI_MEZZI_PROTEZIONE_PREVISTI text     ;
ALTER TABLE PDL ADD COLUMN NOME_COGNOME_PREPOSTO_IMPRESA character varying(50)    ;
ALTER TABLE PDL ADD COLUMN NOME_COGNOME_DELEGATO_LAVORI_AL character varying(50)    ;
ALTER TABLE PDL ADD COLUMN STATO_PERMESSO character varying(50) DEFAULT '0'::character varying ;
ALTER TABLE PDL ADD COLUMN DESCRIZIONE_DELLA_SOSPENSIONE character varying(255)    ;
ALTER TABLE PDL ADD COLUMN FLG_AREA_RIPULITA boolean     ;
ALTER TABLE PDL ADD COLUMN AREA_RIPULITA_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN CAPO_TURNO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN DATA_CHIUSURA timestamp without time zone  ;
ALTER TABLE PDL ADD COLUMN FLG_CONVALIDA boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PONTEGGIO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_VAPORE boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_PERMESSO_DI_ACCESSO boolean     ;
ALTER TABLE PDL ADD COLUMN FLG_LOTO boolean     ;
ALTER TABLE PDL ADD COLUMN LOTO_TESTO character varying(50)    ;
ALTER TABLE PDL ADD COLUMN FLG_SOLO_TAG_OUT boolean     ;
ALTER TABLE PDL ADD COLUMN ALTRO_SEZ1_PRESCRIZIONI_SIC2_TESTO text     ;
ALTER TABLE PDL ADD COLUMN FLG_TA boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN ANNO_TA integer DEFAULT 0   ;
ALTER TABLE PDL ADD COLUMN DATA_ATTIVO timestamp without time zone  ;
ALTER TABLE PDL ADD COLUMN ORA_ATTIVO timestamp without time zone  ;
ALTER TABLE PDL ADD COLUMN ORA_CHIUSURA timestamp without time zone  ;
ALTER TABLE PDL ADD COLUMN POSIZIONE_IN_PLANIMTERIA character varying(255)    ;
ALTER TABLE PDL ADD COLUMN FLG_STAMPA_TURNO boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN COD_MOD70 character varying(255)    ;
ALTER TABLE PDL ADD COLUMN FLG_COMP_MOD70 boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_VALUTA_INTERFERENZA boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_PRIMA_VALUTAZIONE boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_SUCCESSIVA_VALUTAZIONE boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_DISTANZA_SICUREZZA_SI boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN FLG_DISTANZA_SICUREZZA_NO boolean DEFAULT false   ;
ALTER TABLE PDL ADD COLUMN COORDINATORE_MOD70 character varying(255)    ;
ALTER TABLE PDL ADD COLUMN ORA_MOD70 timestamp without time zone  ;
ALTER TABLE PDL ADD COLUMN DATA_MOD70 timestamp without time zone  ;

ALTER TABLE IMPIANTI ADD COLUMN CODICE_IMPIANTO character varying(20) not null default '';
*/