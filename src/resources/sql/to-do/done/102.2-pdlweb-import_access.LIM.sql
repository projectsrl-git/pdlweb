-- Table: "Tab_001 Anagrafica_PdL.LIM"

DROP TABLE if exists temp_pdl;

CREATE TABLE temp_pdl
(
  "Area_lavoro" character varying(50),
  "PdL n°" character varying(50),
  "Impianto" character varying(50),
  "OdL" text,
  "Descr_PG" character varying(50),
  "Descr_Testo" character varying(50),
  "Equipment" character varying(50),
  "Meccanica" boolean,
  "Strumentale" boolean,
  "Elettrica" boolean,
  "Edile" boolean,
  "A fuoco" boolean,
  "In quota" boolean,
  "Scavo" boolean,
  "In spazi confinati" boolean,
  "Ponteggio" boolean,
  "Coibentazione/Scoibentazione" boolean,
  "Ciecatura/rimozione" boolean,
  "Pulizia/facchinaggio" boolean,
  "Verniciatura" boolean,
  "Sabbiatura" boolean,
  "Aggottamento" boolean,
  "Altro_sez1_TipoLavoro1_testo" character varying(255),
  "Altro_sez1_TipoLavoro2_testo" character varying(255),
  "Descrizione_lavoro" character varying(255),
  "Ossigeno" boolean,
  "Azoto" boolean,
  "Argon" boolean,
  "Liquidi Criogenici" boolean,
  "Metano o gas naturale" boolean,
  "Idrocarburi" boolean,
  "Perlite" boolean,
  "Butano" boolean,
  "SynGas" boolean,
  "Idrogeno" boolean,
  "DMDS" boolean,
  "Oli" boolean,
  "Vapore" boolean,
  "Altro_sez1_Prodotti1_testo" character varying(50),
  "Rumore" boolean,
  "Alta Temperatura" boolean,
  "Pressione" boolean,
  "Altezza" boolean,
  "Criogenia / bassa temperatura" boolean,
  "Parti in movimento" boolean,
  "Appar/cavi elettrici" boolean,
  "Prodotti chimici" boolean,
  "Materiali infiammabili" boolean,
  "Altro_sez1_Attivita1_testo" character varying(50),
  "Intercettare" boolean,
  "Depressurizzare" boolean,
  "Vuotare" boolean,
  "Doppie valvole chiuse" boolean,
  "Spurghi aperti" boolean,
  "Spurghi chiusi" boolean,
  "Vent aperti" boolean,
  "Vent chiusi" boolean,
  "Sconnettere linee" boolean,
  "Sflangiare" boolean,
  "Controllare Pressione" boolean,
  "Controllare Temperatura" boolean,
  "Areare" boolean,
  "Doppia valvola chiusa e spurgo intermedio aperto" boolean,
  "Lavare con prodotti compatibili alimentare /farmaceutico" boolean,
  "Predisporre cartelli divieto manovra" boolean,
  "Predisporre illuminazione" boolean,
  "Predisporre attrezzature antincendio" boolean,
  "Localizzare linee interrate" boolean,
  "Applicare segnaletica" boolean,
  "Tripode" boolean,
  "Proteggere fogne" boolean,
  "Ponteggio - Piano di lavoro" boolean,
  "Piattaforma" boolean,
  "Delimitare zona lavoro" boolean,
  "Squadra di soccorso in stand-by" boolean,
  "Insufflare azoto" boolean,
  "Insufflare aria" boolean,
  "Eseguire dopo completamento e chiusura permessi n°" boolean,
  "PdL1_nr" character varying(50),
  "Ciecatura" boolean,
  "Scheda_ciecatura_nr" character varying(50),
  "Rimozione temporanea EIS" boolean,
  "Scheda_EIS_nr" character varying(50),
  "Messa in sicurezza elettrica" boolean,
  "Messa_in_sicurezza_elettrica_testo" character varying(50),
  "Prova di abitabilità" boolean,
  "Prova_di_abitabilità_testo" character varying(50),
  "Permesso di accesso" boolean,
  "Prova di esplosività" boolean,
  "Prova di esplosività_testo" character varying(50),
  "Consegna - verifica - accettazione" boolean,
  "LI_MOD_47" character varying(50),
  "Lavare con" boolean,
  "Lavare_con_testo" character varying(50),
  "Bonificare con" boolean,
  "Bonificare_con_testo" character varying(50),
  "Predisporre manichette con" boolean,
  "Predisporre_manichette_con_test" character varying(50),
  "Altro_sez1_Prescrizioni_sic1_testo" character varying(255),
  "Produzione rifiuti pericolosi" boolean,
  "Produzione rifiuti non pericolosi" boolean,
  "Rischio spandimento" boolean,
  "Emissioni in atmosfera" boolean,
  "Precauzioni da adottare" character varying(255),
  "Tuta da lavoro" boolean DEFAULT false,
  "Scarpe di sicurezza" boolean DEFAULT false,
  "Guanti" boolean DEFAULT false,
  "Occhiali" boolean DEFAULT false,
  "Elmetto" boolean DEFAULT false,
  "Maschera antipolvere" boolean,
  "Maschera antigas" boolean,
  "Autorespiratore" boolean,
  "Visiera di protezione" boolean,
  "Protezioni auricolari" boolean,
  "Rilevatore ossigeno" boolean,
  "Esplosimetro" boolean,
  "Indumenti ignifughi speciali" boolean,
  "Stivali antifortunistici" boolean,
  "Imbrac/Cintura di sicurezza" boolean,
  "Guanti protettivi per agenti chimici" boolean,
  "Protezione viso e corpo per agenti chimici" boolean,
  "Guanti protezione per alte temperature" boolean,
  "Guanti criogenici" boolean,
  "Guanti dielettrici" boolean,
  "Elmetto dielettrico" boolean,
  "Ulteriori prescrizioni di sicurezza da adottare" text,
  "Data_sez1" timestamp without time zone DEFAULT now(),
  "Responsabile_Centrale_Delegato" character varying(50),
  "Personale_interno" boolean,
  "Personale_esterno" boolean,
  "Impresa_Testo" character varying(50),
  "Cassetta attrezzi" boolean,
  "Mola" boolean,
  "Taglia tubi" boolean,
  "Elettrosaldatrice" boolean,
  "Motosaldatrice" boolean,
  "Paranco" boolean,
  "Trapano elettrico" boolean,
  "Trapano a tenuta" boolean,
  "Sabbiatrice" boolean,
  "Attrezzi antiscintilla" boolean,
  "Carroponte" boolean,
  "Escavatrice" boolean,
  "Lancia acqua in pressione" boolean,
  "Trapano pneumatico" boolean,
  "Cannello ossiacetilenico" boolean,
  "Utensili a tensione di sicurezza" boolean,
  "Attrezzature lavaggi chimici" boolean,
  "Attrezzature per radiografie" boolean,
  "Autogrù" boolean,
  "Automezzo" boolean,
  "Carrello elevatore" boolean,
  "Motopompa" boolean,
  "Autospurgo" boolean,
  "Scovolatrice" boolean,
  "Motocompressore" boolean,
  "Altra attrezzatura" character varying(255),
  "Preparaz_del lavoro precauzioni previste durante l'esecuzione" character varying(255),
  "Rischi specifici att_ indicaz_dei mezzi protezione previsti" character varying(255),
  "Nome_Cognome_Preposto_Impresa" character varying(50),
  "Nome_Cognome_Delegato_lavori_AL" character varying(50),
  "Stato_Permesso" character varying(50) DEFAULT '0'::character varying,
  "Descrizione della sospensione" character varying(255),
  "Area ripulita" boolean,
  "Area ripulita_testo" character varying(50),
  "Capo Turno" character varying(50),
  "Data chiusura" timestamp without time zone,
  "Convalida" boolean,
  "LOTO" boolean,
  "LOTO testo" character varying(50),
  "Solo Tag-out" boolean,
  "Altro_sez1_Prescrizioni_sic2_testo" text
)
;




insert into temp_pdl select * from "Tab_001 Anagrafica_PdL.LIM";

ALTER TABLE temp_pdl RENAME "Area_lavoro" TO AREA_LAVORO;
ALTER TABLE TEMP_PDL RENAME "PdL n°" TO NR_PDL;
ALTER TABLE TEMP_PDL RENAME "Impianto" TO IMPIANTO;
ALTER TABLE TEMP_PDL RENAME "OdL" TO ODL;
ALTER TABLE TEMP_PDL RENAME "Descr_PG" TO DESCR_PG;
ALTER TABLE TEMP_PDL RENAME "Descr_Testo" TO DESCR_TESTO;
ALTER TABLE TEMP_PDL RENAME "Equipment" TO EQUIPMENT;
ALTER TABLE TEMP_PDL RENAME "Meccanica" TO FLG_MECCANICA;
ALTER TABLE TEMP_PDL RENAME "Strumentale" TO FLG_STRUMENTALE;
ALTER TABLE TEMP_PDL RENAME "Elettrica" TO FLG_ELETTRICA;
ALTER TABLE TEMP_PDL RENAME "Edile" TO FLG_EDILE;
ALTER TABLE TEMP_PDL RENAME "A fuoco" TO FLG_A_FUOCO;
ALTER TABLE TEMP_PDL RENAME "In quota" TO FLG_IN_QUOTA;
ALTER TABLE TEMP_PDL RENAME "Scavo" TO FLG_SCAVO;
ALTER TABLE TEMP_PDL RENAME "In spazi confinati" TO FLG_IN_SPAZI_CONFINATI;
ALTER TABLE TEMP_PDL RENAME "Coibentazione/Scoibentazione" TO FLG_COIBENTAZIONE_SCOIBENTAZIONE;
ALTER TABLE TEMP_PDL RENAME "Ciecatura/rimozione" TO FLG_CIECATURA_RIMOZIONE;
ALTER TABLE TEMP_PDL RENAME "Pulizia/facchinaggio" TO FLG_PULIZIA_FACCHINAGGIO;
ALTER TABLE TEMP_PDL RENAME "Verniciatura" TO FLG_VERNICIATURA;
ALTER TABLE TEMP_PDL RENAME "Sabbiatura" TO FLG_SABBIATURA;
ALTER TABLE TEMP_PDL RENAME "Aggottamento" TO FLG_AGGOTTAMENTO;
ALTER TABLE TEMP_PDL RENAME "Altro_sez1_TipoLavoro1_testo" TO ALTRO_SEZ1_TIPOLAVORO1_TESTO;
ALTER TABLE TEMP_PDL RENAME "Altro_sez1_TipoLavoro2_testo" TO ALTRO_SEZ1_TIPOLAVORO2_TESTO;
ALTER TABLE TEMP_PDL RENAME "Descrizione_lavoro" TO DESCRIZIONE_LAVORO;
ALTER TABLE TEMP_PDL RENAME "Ossigeno" TO FLG_OSSIGENO;
ALTER TABLE TEMP_PDL RENAME "Azoto" TO FLG_AZOTO;
ALTER TABLE TEMP_PDL RENAME "Argon" TO FLG_ARGON;
ALTER TABLE TEMP_PDL RENAME "Liquidi Criogenici" TO FLG_LIQUIDI_CRIOGENICI;
ALTER TABLE TEMP_PDL RENAME "Metano o gas naturale" TO FLG_METANO_O_GAS_NATURALE;
ALTER TABLE TEMP_PDL RENAME "Idrocarburi" TO FLG_IDROCARBURI;
ALTER TABLE TEMP_PDL RENAME "Perlite" TO FLG_PERLITE;
ALTER TABLE TEMP_PDL RENAME "Butano" TO FLG_BUTANO;
ALTER TABLE TEMP_PDL RENAME "SynGas" TO FLG_SYNGAS;
ALTER TABLE TEMP_PDL RENAME "Idrogeno" TO FLG_IDROGENO;
ALTER TABLE TEMP_PDL RENAME "DMDS" TO FLG_DMDS;
ALTER TABLE TEMP_PDL RENAME "Oli" TO FLG_OLI;
ALTER TABLE TEMP_PDL RENAME "Altro_sez1_Prodotti1_testo" TO ALTRO_SEZ1_PRODOTTI1_TESTO;
ALTER TABLE TEMP_PDL RENAME "Rumore" TO FLG_RUMORE;
ALTER TABLE TEMP_PDL RENAME "Alta Temperatura" TO FLG_ALTA_TEMPERATURA;
ALTER TABLE TEMP_PDL RENAME "Pressione" TO FLG_PRESSIONE;
ALTER TABLE TEMP_PDL RENAME "Altezza" TO FLG_ALTEZZA;
ALTER TABLE TEMP_PDL RENAME "Criogenia / bassa temperatura" TO FLG_CRIOGENIA_BASSA_TEMPERATURA;
ALTER TABLE TEMP_PDL RENAME "Parti in movimento" TO FLG_PARTI_IN_MOVIMENTO;
ALTER TABLE TEMP_PDL RENAME "Appar/cavi elettrici" TO FLG_APPAR_CAVI_ELETTRICI;
ALTER TABLE TEMP_PDL RENAME "Prodotti chimici" TO FLG_PRODOTTI_CHIMICI;
ALTER TABLE TEMP_PDL RENAME "Materiali infiammabili" TO FLG_MATERIALI_INFIAMMABILI;
ALTER TABLE TEMP_PDL RENAME "Altro_sez1_Attivita1_testo" TO ALTRO_SEZ1_ATTIVITA1_TESTO;
ALTER TABLE TEMP_PDL RENAME "Intercettare" TO FLG_INTERCETTARE;
ALTER TABLE TEMP_PDL RENAME "Depressurizzare" TO FLG_DEPRESSURIZZARE;
ALTER TABLE TEMP_PDL RENAME "Vuotare" TO FLG_VUOTARE;
ALTER TABLE TEMP_PDL RENAME "Doppie valvole chiuse" TO FLG_DOPPIE_VALVOLE_CHIUSE;
ALTER TABLE TEMP_PDL RENAME "Spurghi aperti" TO FLG_SPURGHI_APERTI;
ALTER TABLE TEMP_PDL RENAME "Spurghi chiusi" TO FLG_SPURGHI_CHIUSI;
ALTER TABLE TEMP_PDL RENAME "Vent aperti" TO FLG_VENT_APERTI;
ALTER TABLE TEMP_PDL RENAME "Vent chiusi" TO FLG_VENT_CHIUSI;
ALTER TABLE TEMP_PDL RENAME "Sconnettere linee" TO FLG_SCONNETTERE_LINEE;
ALTER TABLE TEMP_PDL RENAME "Sflangiare" TO FLG_SFLANGIARE;
ALTER TABLE TEMP_PDL RENAME "Controllare Pressione" TO FLG_CONTROLLARE_PRESSIONE;
ALTER TABLE TEMP_PDL RENAME "Controllare Temperatura" TO FLG_CONTROLLARE_TEMPERATURA;
ALTER TABLE TEMP_PDL RENAME "Areare" TO FLG_AREARE;
ALTER TABLE TEMP_PDL RENAME "Doppia valvola chiusa e spurgo intermedio aperto" TO FLG_DOPPIA_VALVOLA_CHIUSA_E_SPURGO_INTERMEDIO_APERTO;
ALTER TABLE TEMP_PDL RENAME "Lavare con prodotti compatibili alimentare /farmaceutico" TO FLG_LAVARE_CON_PRODOTTI_COMPATIBILI_ALIMENTARE_FARMACEUTICO;
ALTER TABLE TEMP_PDL RENAME "Predisporre cartelli divieto manovra" TO FLG_PREDISPORRE_CARTELLI_DIVIETO_MANOVRA;
ALTER TABLE TEMP_PDL RENAME "Predisporre illuminazione" TO FLG_PREDISPORRE_ILLUMINAZIONE;
ALTER TABLE TEMP_PDL RENAME "Predisporre attrezzature antincendio" TO FLG_PREDISPORRE_ATTREZZATURE_ANTINCENDIO;
ALTER TABLE TEMP_PDL RENAME "Localizzare linee interrate" TO FLG_LOCALIZZARE_LINEE_INTERRATE;
ALTER TABLE TEMP_PDL RENAME "Applicare segnaletica" TO FLG_APPLICARE_SEGNALETICA;
ALTER TABLE TEMP_PDL RENAME "Tripode" TO FLG_TRIPODE;
ALTER TABLE TEMP_PDL RENAME "Proteggere fogne" TO FLG_PROTEGGERE_FOGNE;
ALTER TABLE TEMP_PDL RENAME "Ponteggio - Piano di lavoro" TO FLG_PONTEGGIO_PIANO_DI_LAVORO;
ALTER TABLE TEMP_PDL RENAME "Piattaforma" TO FLG_PIATTAFORMA;
ALTER TABLE TEMP_PDL RENAME "Delimitare zona lavoro" TO FLG_DELIMITARE_ZONA_LAVORO;
ALTER TABLE TEMP_PDL RENAME "Squadra di soccorso in stand-by" TO FLG_SQUADRA_DI_SOCCORSO_IN_STAND_BY;
ALTER TABLE TEMP_PDL RENAME "Insufflare azoto" TO FLG_INSUFFLARE_AZOTO;
ALTER TABLE TEMP_PDL RENAME "Insufflare aria" TO FLG_INSUFFLARE_ARIA;
ALTER TABLE TEMP_PDL RENAME "Eseguire dopo completamento e chiusura permessi n°" TO FLG_ESEGUIRE_DOPO_COMPLETAMENTO_E_CHIUSURA_PERMESSI_N;
ALTER TABLE TEMP_PDL RENAME "PdL1_nr" TO PDL1_NR;
ALTER TABLE TEMP_PDL RENAME "Ciecatura" TO FLG_CIECATURA;
ALTER TABLE TEMP_PDL RENAME "Scheda_ciecatura_nr" TO SCHEDA_CIECATURA_NR;
ALTER TABLE TEMP_PDL RENAME "Rimozione temporanea EIS" TO FLG_RIMOZIONE_TEMPORANEA_EIS;
ALTER TABLE TEMP_PDL RENAME "Scheda_EIS_nr" TO SCHEDA_EIS_NR;
ALTER TABLE TEMP_PDL RENAME "Messa in sicurezza elettrica" TO FLG_MESSA_IN_SICUREZZA_ELETTRICA;
ALTER TABLE TEMP_PDL RENAME "Messa_in_sicurezza_elettrica_testo" TO MESSA_IN_SICUREZZA_ELETTRICA_TESTO;
ALTER TABLE TEMP_PDL RENAME "Prova di abitabilità" TO FLG_PROVA_DI_ABITABILITA;
ALTER TABLE TEMP_PDL RENAME "Prova_di_abitabilità_testo" TO PROVA_DI_ABITABILITA_TESTO;
ALTER TABLE TEMP_PDL RENAME "Prova di esplosività" TO FLG_PROVA_DI_ESPLOSIVITA;
ALTER TABLE TEMP_PDL RENAME "Prova di esplosività_testo" TO PROVA_DI_ESPLOSIVITA_TESTO;
ALTER TABLE TEMP_PDL RENAME "Consegna - verifica - accettazione" TO FLG_CONSEGNA_VERIFICA_ACCETTAZIONE;
ALTER TABLE TEMP_PDL RENAME "LI_MOD_47" TO LI_MOD_47;
ALTER TABLE TEMP_PDL RENAME "Lavare con" TO FLG_LAVARE_CON;
ALTER TABLE TEMP_PDL RENAME "Lavare_con_testo" TO LAVARE_CON_TESTO;
ALTER TABLE TEMP_PDL RENAME "Bonificare con" TO FLG_BONIFICARE_CON;
ALTER TABLE TEMP_PDL RENAME "Bonificare_con_testo" TO BONIFICARE_CON_TESTO;
ALTER TABLE TEMP_PDL RENAME "Predisporre manichette con" TO FLG_PREDISPORRE_MANICHETTE_CON;
ALTER TABLE TEMP_PDL RENAME "Predisporre_manichette_con_test" TO PREDISPORRE_MANICHETTE_CON_TEST;
ALTER TABLE TEMP_PDL RENAME "Altro_sez1_Prescrizioni_sic1_testo" TO ALTRO_SEZ1_PRESCRIZIONI_SIC1_TESTO;
ALTER TABLE TEMP_PDL RENAME "Produzione rifiuti pericolosi" TO FLG_PRODUZIONE_RIFIUTI_PERICOLOSI;
ALTER TABLE TEMP_PDL RENAME "Produzione rifiuti non pericolosi" TO FLG_PRODUZIONE_RIFIUTI_NON_PERICOLOSI;
ALTER TABLE TEMP_PDL RENAME "Rischio spandimento" TO FLG_RISCHIO_SPANDIMENTO;
ALTER TABLE TEMP_PDL RENAME "Emissioni in atmosfera" TO FLG_EMISSIONI_IN_ATMOSFERA;
ALTER TABLE TEMP_PDL RENAME "Precauzioni da adottare" TO PRECAUZIONI_DA_ADOTTARE;
ALTER TABLE TEMP_PDL RENAME "Tuta da lavoro" TO FLG_TUTA_DA_LAVORO;
ALTER TABLE TEMP_PDL RENAME "Scarpe di sicurezza" TO FLG_SCARPE_DI_SICUREZZA;
ALTER TABLE TEMP_PDL RENAME "Guanti" TO FLG_GUANTI;
ALTER TABLE TEMP_PDL RENAME "Occhiali" TO FLG_OCCHIALI;
ALTER TABLE TEMP_PDL RENAME "Elmetto" TO FLG_ELMETTO;
ALTER TABLE TEMP_PDL RENAME "Maschera antipolvere" TO FLG_MASCHERA_ANTIPOLVERE;
ALTER TABLE TEMP_PDL RENAME "Maschera antigas" TO FLG_MASCHERA_ANTIGAS;
ALTER TABLE TEMP_PDL RENAME "Autorespiratore" TO FLG_AUTORESPIRATORE;
ALTER TABLE TEMP_PDL RENAME "Visiera di protezione" TO FLG_VISIERA_DI_PROTEZIONE;
ALTER TABLE TEMP_PDL RENAME "Protezioni auricolari" TO FLG_PROTEZIONI_AURICOLARI;
ALTER TABLE TEMP_PDL RENAME "Rilevatore ossigeno" TO FLG_RILEVATORE_OSSIGENO;
ALTER TABLE TEMP_PDL RENAME "Esplosimetro" TO FLG_ESPLOSIMETRO;
ALTER TABLE TEMP_PDL RENAME "Indumenti ignifughi speciali" TO FLG_INDUMENTI_IGNIFUGHI_SPECIALI;
ALTER TABLE TEMP_PDL RENAME "Stivali antifortunistici" TO FLG_STIVALI_ANTIFORTUNISTICI;
ALTER TABLE TEMP_PDL RENAME "Imbrac/Cintura di sicurezza" TO FLG_IMBRAC_CINTURA_DI_SICUREZZA;
ALTER TABLE TEMP_PDL RENAME "Guanti protettivi per agenti chimici" TO FLG_GUANTI_PROTETTIVI_PER_AGENTI_CHIMICI;
ALTER TABLE TEMP_PDL RENAME "Protezione viso e corpo per agenti chimici" TO FLG_PROTEZIONE_VISO_E_CORPO_PER_AGENTI_CHIMICI;
ALTER TABLE TEMP_PDL RENAME "Guanti protezione per alte temperature" TO FLG_GUANTI_PROTEZIONE_PER_ALTE_TEMPERATURE;
ALTER TABLE TEMP_PDL RENAME "Guanti criogenici" TO FLG_GUANTI_CRIOGENICI;
ALTER TABLE TEMP_PDL RENAME "Guanti dielettrici" TO FLG_GUANTI_DIELETTRICI;
ALTER TABLE TEMP_PDL RENAME "Elmetto dielettrico" TO FLG_ELMETTO_DIELETTRICO;
ALTER TABLE TEMP_PDL RENAME "Ulteriori prescrizioni di sicurezza da adottare" TO ULTERIORI_PRESCRIZIONI_DI_SICUREZZA_DA_ADOTTARE;
ALTER TABLE TEMP_PDL RENAME "Data_sez1" TO DATA_SEZ1;
ALTER TABLE TEMP_PDL RENAME "Responsabile_Centrale_Delegato" TO RESPONSABILE_CENTRALE_DELEGATO;
ALTER TABLE TEMP_PDL RENAME "Personale_interno" TO FLG_PERSONALE_INTERNO;
ALTER TABLE TEMP_PDL RENAME "Personale_esterno" TO FLG_PERSONALE_ESTERNO;
ALTER TABLE TEMP_PDL RENAME "Impresa_Testo" TO IMPRESA_TESTO;
ALTER TABLE TEMP_PDL RENAME "Cassetta attrezzi" TO FLG_CASSETTA_ATTREZZI;
ALTER TABLE TEMP_PDL RENAME "Mola" TO FLG_MOLA;
ALTER TABLE TEMP_PDL RENAME "Taglia tubi" TO FLG_TAGLIA_TUBI;
ALTER TABLE TEMP_PDL RENAME "Elettrosaldatrice" TO FLG_ELETTROSALDATRICE;
ALTER TABLE TEMP_PDL RENAME "Motosaldatrice" TO FLG_MOTOSALDATRICE;
ALTER TABLE TEMP_PDL RENAME "Paranco" TO FLG_PARANCO;
ALTER TABLE TEMP_PDL RENAME "Trapano elettrico" TO FLG_TRAPANO_ELETTRICO;
ALTER TABLE TEMP_PDL RENAME "Trapano a tenuta" TO FLG_TRAPANO_A_TENUTA;
ALTER TABLE TEMP_PDL RENAME "Sabbiatrice" TO FLG_SABBIATRICE;
ALTER TABLE TEMP_PDL RENAME "Attrezzi antiscintilla" TO FLG_ATTREZZI_ANTISCINTILLA;
ALTER TABLE TEMP_PDL RENAME "Carroponte" TO FLG_CARROPONTE;
ALTER TABLE TEMP_PDL RENAME "Escavatrice" TO FLG_ESCAVATRICE;
ALTER TABLE TEMP_PDL RENAME "Lancia acqua in pressione" TO FLG_LANCIA_ACQUA_IN_PRESSIONE;
ALTER TABLE TEMP_PDL RENAME "Trapano pneumatico" TO FLG_TRAPANO_PNEUMATICO;
ALTER TABLE TEMP_PDL RENAME "Cannello ossiacetilenico" TO FLG_CANNELLO_OSSIACETILENICO;
ALTER TABLE TEMP_PDL RENAME "Utensili a tensione di sicurezza" TO FLG_UTENSILI_A_TENSIONE_DI_SICUREZZA;
ALTER TABLE TEMP_PDL RENAME "Attrezzature lavaggi chimici" TO FLG_ATTREZZATURE_LAVAGGI_CHIMICI;
ALTER TABLE TEMP_PDL RENAME "Attrezzature per radiografie" TO FLG_ATTREZZATURE_PER_RADIOGRAFIE;
ALTER TABLE TEMP_PDL RENAME "Autogrù" TO FLG_AUTOGRU;
ALTER TABLE TEMP_PDL RENAME "Automezzo" TO FLG_AUTOMEZZO;
ALTER TABLE TEMP_PDL RENAME "Carrello elevatore" TO FLG_CARRELLO_ELEVATORE;
ALTER TABLE TEMP_PDL RENAME "Motopompa" TO FLG_MOTOPOMPA;
ALTER TABLE TEMP_PDL RENAME "Autospurgo" TO FLG_AUTOSPURGO;
ALTER TABLE TEMP_PDL RENAME "Scovolatrice" TO FLG_SCOVOLATRICE;
ALTER TABLE TEMP_PDL RENAME "Motocompressore" TO FLG_MOTOCOMPRESSORE;
ALTER TABLE TEMP_PDL RENAME "Altra attrezzatura" TO ALTRA_ATTREZZATURA;
ALTER TABLE TEMP_PDL RENAME "Preparaz_del lavoro precauzioni previste durante l'esecuzione" TO PREPARAZ_DEL_LAVORO_PRECAUZIONI_PREVISTE_DURANTE_LESECUZIONE;
ALTER TABLE TEMP_PDL RENAME "Rischi specifici att_ indicaz_dei mezzi protezione previsti" TO RISCHI_SPECIFICI_ATT_INDICAZ_DEI_MEZZI_PROTEZIONE_PREVISTI;
ALTER TABLE TEMP_PDL RENAME "Nome_Cognome_Preposto_Impresa" TO NOME_COGNOME_PREPOSTO_IMPRESA;
ALTER TABLE TEMP_PDL RENAME "Nome_Cognome_Delegato_lavori_AL" TO NOME_COGNOME_DELEGATO_LAVORI_AL;
ALTER TABLE TEMP_PDL RENAME "Stato_Permesso" TO STATO_PERMESSO;
ALTER TABLE TEMP_PDL RENAME "Descrizione della sospensione" TO DESCRIZIONE_DELLA_SOSPENSIONE;
ALTER TABLE TEMP_PDL RENAME "Area ripulita" TO FLG_AREA_RIPULITA;
ALTER TABLE TEMP_PDL RENAME "Area ripulita_testo" TO AREA_RIPULITA_TESTO;
ALTER TABLE TEMP_PDL RENAME "Capo Turno" TO CAPO_TURNO;
ALTER TABLE TEMP_PDL RENAME "Data chiusura" TO DATA_CHIUSURA;
ALTER TABLE TEMP_PDL RENAME "Convalida" TO FLG_CONVALIDA;
ALTER TABLE TEMP_PDL RENAME "Ponteggio" TO FLG_PONTEGGIO;
ALTER TABLE TEMP_PDL RENAME "Vapore" TO FLG_VAPORE;
ALTER TABLE TEMP_PDL RENAME "Permesso di accesso" TO FLG_PERMESSO_DI_ACCESSO;
ALTER TABLE TEMP_PDL RENAME "LOTO" TO FLG_LOTO;
ALTER TABLE TEMP_PDL RENAME "LOTO testo" TO LOTO_TESTO;
ALTER TABLE TEMP_PDL RENAME "Solo Tag-out" TO FLG_SOLO_TAG_OUT;
ALTER TABLE TEMP_PDL RENAME "Altro_sez1_Prescrizioni_sic2_testo" TO ALTRO_SEZ1_PRESCRIZIONI_SIC2_TESTO;


delete from equipment where id_area in (select id_area from area_lavoro where id_impianto in (select id_impianto from impianti where id_azienda=(SELECT ID_AZIENDA from aziende where  codice ='LIM')));

delete from area_lavoro where id_impianto in (select id_impianto from impianti where id_azienda=(SELECT ID_AZIENDA from aziende where  codice ='LIM'));

delete from impianti where id_azienda=(SELECT ID_AZIENDA from aziende where  codice ='LIM');

-- impianti
insert into impianti (id_azienda,codice_impianto,descr_impianto,id_utente_ins)
select 
	(SELECT ID_AZIENDA from aziende where  codice ='LIM')
	,"Impianto"
	,case when coalesce("Descrizione Impianto" ,'')='' then "Impianto" else "Descrizione Impianto" end
	,(select id_utente from utenti where username='assistenza')
from "Tab_005 Impianti.LIM";


--AREA_LAVORO
insert into AREA_LAVORO (id_impianto,descr_area,id_utente_ins) 
SELECT 
	DISTINCT 
	id_impianto
	,"Area di Lavoro"
	,(select id_utente from utenti where username='assistenza')
FROM "Tab_008 Lista Equipment.LIM" as tle 
left outer join impianti as imp on tle."Impianto"=imp.codice_impianto and imp.id_azienda in (select id_azienda from aziende where codice ='LIM') 
where "Area di Lavoro" is not null
and id_impianto is not null
;



--equipment
insert into equipment (id_area,descr_equipment,fl_eis)
select distinct 
	are.id_area
	,"Equipment"
	,case when "EIS"='*' then true else false end 
from (select distinct "Impianto","Area di Lavoro","Equipment","EIS"  from "Tab_008 Lista Equipment.LIM") as t1 
left outer join AREA_LAVORO as are on "Area di Lavoro"=descr_area and are.id_impianto in (select id_impianto from impianti where id_azienda in (select id_azienda from aziende where codice ='LIM') )
where "Equipment" is not null and are.id_area is not null
;

-- scarto queste equipment: 3
-- select * from "Tab_008 Lista Equipment.LIM" where "Equipment" is null or "Area di Lavoro" is null;


DELETE FROM PREPOSTI_IMPRESA WHERE ID_AZIENDA = (SELECT ID_AZIENDA from aziende where  codice ='LIM');

INSERT INTO PREPOSTI_IMPRESA (ID_AZIENDA,NOME_IMPRESA,NOME_COGNOME_PREPOSTO_IMPRESA)
SELECT 
DISTINCT
(SELECT ID_AZIENDA from aziende where  codice ='LIM') as ID_AZIENDA
,"Nome Azienda"
,"Riferimento Capo Cantiere" 
FROM "Tab_004 Ditte Terze.LIM"
where "Nome Azienda" is not null and "Riferimento Capo Cantiere"  is not null
;

-- aggiunge impianti/aree/equipment mancanti

INSERT INTO impianti (id_azienda,codice_impianto,descr_impianto,fl_disattivo,id_utente_ins) 
select distinct 
	(select id_azienda from aziende where codice ='LIM') as id_azienda
	,"Impianto" as codice_impianto
	,"Impianto" as descr_impianto
	,true as fl_disattivo
	,(select id_utente from utenti where username='assistenza')
from "Tab_001 Anagrafica_PdL.LIM" 
where "Impianto" not in (select codice_impianto from impianti where id_azienda in (select id_azienda from aziende where codice ='LIM'))
;	



INSERT INTO impianti (id_azienda,codice_impianto,descr_impianto,fl_disattivo,id_utente_ins) 
VALUES (
	(select id_azienda from aziende where codice ='LIM')
	,'ND'
	,'NON DISPONIBILE'
	,true
	,(select id_utente from utenti where username='assistenza')
);





INSERT INTO area_lavoro (id_impianto,descr_area,fl_disattivo,id_utente_ins) 
select 
	distinct
	imp.id_impianto
	--,are.id_area
	,COALESCE("Area_lavoro",'NON DISPONIBILE') as descr_area
	,true
	,(select id_utente from utenti where username='assistenza')
from "Tab_001 Anagrafica_PdL.LIM" as t1
left outer join impianti as imp on imp.codice_impianto = COALESCE(t1."Impianto",'ND') and imp.id_azienda in (select id_azienda from aziende where codice ='LIM')
left outer join area_lavoro as are on t1."Area_lavoro" = are.descr_area and are.id_impianto = imp.id_impianto
where are.id_area is null 
;

--"Area_lavoro"='TUTTO IMPIANTO'


insert into equipment (id_area,descr_equipment,fl_eis,fl_disattivo)
select 
	distinct
	are.id_area
	--,equ.id_equipment
	,COALESCE(t1."Equipment",'NON DISPONIBILE') as descr_equipment
	,false as fl_eis
	,true as fl_disattivo
from "Tab_001 Anagrafica_PdL.LIM" as t1
left outer join impianti as imp on imp.codice_impianto = COALESCE(t1."Impianto",'ND') and imp.id_azienda in (select id_azienda from aziende where codice ='LIM')
left outer join area_lavoro as are on COALESCE(t1."Area_lavoro",'NON DISPONIBILE') = are.descr_area and are.id_impianto = imp.id_impianto
left outer join equipment as equ on t1."Equipment" = equ.descr_equipment and equ.id_area = are.id_area
where equ.id_equipment is null
;


update pdl set 
id_impianto=(select id_impianto from impianti where descr_impianto = 'Limito - ASU e Liquefattori') 
,id_area=(select id_area from area_lavoro where descr_area = 'Distillazione Criogenica' and id_impianto = (select id_impianto from impianti where descr_impianto = 'Limito - ASU e Liquefattori')) 
,id_equipment = (select id_equipment from equipment where descr_equipment='TE2510 A/B/C - Trasm. temperatura MP GOX') 
where id_pdl=126996;
select * from pdl where id_pdl=126996;
;
	


DELETE FROM PDL WHERE ID_AZIENDA=(SELECT ID_AZIENDA from aziende where  codice ='LIM') and substring(dt_pdl,1,4)<>'2018';
--126996

INSERT INTO PDL (
	ID_AZIENDA,  
	ID_IMPIANTO,
	id_area,
	id_equipment,
	DT_PDL,
	STATO, 
	ID_UTENTE_INS,
	NR_PDL,
	IMPIANTO,
	ODL,
	DESCR_PG,
	DESCR_TESTO,
	EQUIPMENT,
	FLG_MECCANICA,
	FLG_STRUMENTALE,
	FLG_ELETTRICA,
	FLG_EDILE,
	FLG_A_FUOCO,
	FLG_IN_QUOTA,
	FLG_SCAVO,
	FLG_IN_SPAZI_CONFINATI,
	FLG_COIBENTAZIONE_SCOIBENTAZIONE,
	FLG_CIECATURA_RIMOZIONE,
	FLG_PULIZIA_FACCHINAGGIO,
	FLG_VERNICIATURA,
	FLG_SABBIATURA,
	FLG_AGGOTTAMENTO,
	ALTRO_SEZ1_TIPOLAVORO1_TESTO,
	ALTRO_SEZ1_TIPOLAVORO2_TESTO,
	DESCRIZIONE_LAVORO,
	FLG_OSSIGENO,
	FLG_AZOTO,
	FLG_ARGON,
	FLG_LIQUIDI_CRIOGENICI,
	FLG_METANO_O_GAS_NATURALE,
	FLG_IDROCARBURI,
	FLG_PERLITE,
	FLG_BUTANO,
	FLG_SYNGAS,
	FLG_IDROGENO,
	FLG_DMDS,
	FLG_OLI,
	ALTRO_SEZ1_PRODOTTI1_TESTO,
	FLG_RUMORE,
	FLG_ALTA_TEMPERATURA,
	FLG_PRESSIONE,
	FLG_ALTEZZA,
	FLG_CRIOGENIA_BASSA_TEMPERATURA,
	FLG_PARTI_IN_MOVIMENTO,
	FLG_APPAR_CAVI_ELETTRICI,
	FLG_PRODOTTI_CHIMICI,
	FLG_MATERIALI_INFIAMMABILI,
	ALTRO_SEZ1_ATTIVITA1_TESTO,
	FLG_INTERCETTARE,
	FLG_DEPRESSURIZZARE,
	FLG_VUOTARE,
	FLG_DOPPIE_VALVOLE_CHIUSE,
	FLG_SPURGHI_APERTI,
	FLG_SPURGHI_CHIUSI,
	FLG_VENT_APERTI,
	FLG_VENT_CHIUSI,
	FLG_SCONNETTERE_LINEE,
	FLG_SFLANGIARE,
	FLG_CONTROLLARE_PRESSIONE,
	FLG_CONTROLLARE_TEMPERATURA,
	FLG_AREARE,
	FLG_DOPPIA_VALVOLA_CHIUSA_E_SPURGO_INTERMEDIO_APERTO,
	FLG_LAVARE_CON_PRODOTTI_COMPATIBILI_ALIMENTARE_FARMACEUTICO,
	FLG_PREDISPORRE_CARTELLI_DIVIETO_MANOVRA,
	FLG_PREDISPORRE_ILLUMINAZIONE,
	FLG_PREDISPORRE_ATTREZZATURE_ANTINCENDIO,
	FLG_LOCALIZZARE_LINEE_INTERRATE,
	FLG_APPLICARE_SEGNALETICA,
	FLG_TRIPODE,
	FLG_PROTEGGERE_FOGNE,
	FLG_PONTEGGIO_PIANO_DI_LAVORO,
	FLG_PIATTAFORMA,
	FLG_DELIMITARE_ZONA_LAVORO,
	FLG_SQUADRA_DI_SOCCORSO_IN_STAND_BY,
	FLG_INSUFFLARE_AZOTO,
	FLG_INSUFFLARE_ARIA,
	FLG_ESEGUIRE_DOPO_COMPLETAMENTO_E_CHIUSURA_PERMESSI_N,
	PDL1_NR,
	FLG_CIECATURA,
	SCHEDA_CIECATURA_NR,
	FLG_RIMOZIONE_TEMPORANEA_EIS,
	SCHEDA_EIS_NR,
	FLG_MESSA_IN_SICUREZZA_ELETTRICA,
	MESSA_IN_SICUREZZA_ELETTRICA_TESTO,
	FLG_PROVA_DI_ABITABILITA,
	PROVA_DI_ABITABILITA_TESTO,
	FLG_PROVA_DI_ESPLOSIVITA,
	PROVA_DI_ESPLOSIVITA_TESTO,
	FLG_CONSEGNA_VERIFICA_ACCETTAZIONE,
	LI_MOD_47,
	FLG_LAVARE_CON,
	LAVARE_CON_TESTO,
	FLG_BONIFICARE_CON,
	BONIFICARE_CON_TESTO,
	FLG_PREDISPORRE_MANICHETTE_CON,
	PREDISPORRE_MANICHETTE_CON_TEST,
	ALTRO_SEZ1_PRESCRIZIONI_SIC1_TESTO,
	FLG_PRODUZIONE_RIFIUTI_PERICOLOSI,
	FLG_PRODUZIONE_RIFIUTI_NON_PERICOLOSI,
	FLG_RISCHIO_SPANDIMENTO,
	FLG_EMISSIONI_IN_ATMOSFERA,
	PRECAUZIONI_DA_ADOTTARE,
	FLG_TUTA_DA_LAVORO,
	FLG_SCARPE_DI_SICUREZZA,
	FLG_GUANTI,
	FLG_OCCHIALI,
	FLG_ELMETTO,
	FLG_MASCHERA_ANTIPOLVERE,
	FLG_MASCHERA_ANTIGAS,
	FLG_AUTORESPIRATORE,
	FLG_VISIERA_DI_PROTEZIONE,
	FLG_PROTEZIONI_AURICOLARI,
	FLG_RILEVATORE_OSSIGENO,
	FLG_ESPLOSIMETRO,
	FLG_INDUMENTI_IGNIFUGHI_SPECIALI,
	FLG_STIVALI_ANTIFORTUNISTICI,
	FLG_IMBRAC_CINTURA_DI_SICUREZZA,
	FLG_GUANTI_PROTETTIVI_PER_AGENTI_CHIMICI,
	FLG_PROTEZIONE_VISO_E_CORPO_PER_AGENTI_CHIMICI,
	FLG_GUANTI_PROTEZIONE_PER_ALTE_TEMPERATURE,
	FLG_GUANTI_CRIOGENICI,
	FLG_GUANTI_DIELETTRICI,
	FLG_ELMETTO_DIELETTRICO,
	ULTERIORI_PRESCRIZIONI_DI_SICUREZZA_DA_ADOTTARE,
	DATA_SEZ1,
	RESPONSABILE_CENTRALE_DELEGATO,
	FLG_PERSONALE_INTERNO,
	FLG_PERSONALE_ESTERNO,
	IMPRESA_TESTO,
	FLG_CASSETTA_ATTREZZI,
	FLG_MOLA,
	FLG_TAGLIA_TUBI,
	FLG_ELETTROSALDATRICE,
	FLG_MOTOSALDATRICE,
	FLG_PARANCO,
	FLG_TRAPANO_ELETTRICO,
	FLG_TRAPANO_A_TENUTA,
	FLG_SABBIATRICE,
	FLG_ATTREZZI_ANTISCINTILLA,
	FLG_CARROPONTE,
	FLG_ESCAVATRICE,
	FLG_LANCIA_ACQUA_IN_PRESSIONE,
	FLG_TRAPANO_PNEUMATICO,
	FLG_CANNELLO_OSSIACETILENICO,
	FLG_UTENSILI_A_TENSIONE_DI_SICUREZZA,
	FLG_ATTREZZATURE_LAVAGGI_CHIMICI,
	FLG_ATTREZZATURE_PER_RADIOGRAFIE,
	FLG_AUTOGRU,
	FLG_AUTOMEZZO,
	FLG_CARRELLO_ELEVATORE,
	FLG_MOTOPOMPA,
	FLG_AUTOSPURGO,
	FLG_SCOVOLATRICE,
	FLG_MOTOCOMPRESSORE,
	ALTRA_ATTREZZATURA,
	PREPARAZ_DEL_LAVORO_PRECAUZIONI_PREVISTE_DURANTE_LESECUZIONE,
	RISCHI_SPECIFICI_ATT_INDICAZ_DEI_MEZZI_PROTEZIONE_PREVISTI,
	NOME_COGNOME_PREPOSTO_IMPRESA,
	NOME_COGNOME_DELEGATO_LAVORI_AL,
	STATO_PERMESSO,
	DESCRIZIONE_DELLA_SOSPENSIONE,
	FLG_AREA_RIPULITA,
	AREA_RIPULITA_TESTO,
	CAPO_TURNO,
	DT_CHIUSURA,
	FLG_CONVALIDA,
	FLG_PONTEGGIO,
	FLG_VAPORE,
	FLG_PERMESSO_DI_ACCESSO,
	FLG_LOTO,
	LOTO_TESTO,
	FLG_SOLO_TAG_OUT,
	ALTRO_SEZ1_PRESCRIZIONI_SIC2_TESTO)
SELECT 
	(SELECT ID_AZIENDA from aziende where  codice ='LIM') as ID_AZIENDA,
	imp.ID_IMPIANTO,
	are.id_area,
	equ.id_equipment,
	to_char(data_sez1,'yyyy/mm/dd') as DT_PDL, 
	CASE WHEN STATO_PERMESSO='Aperto' THEN 'OPE' WHEN STATO_PERMESSO='Attivo' THEN 'ACT' WHEN STATO_PERMESSO='Sospeso' THEN 'SUS' WHEN STATO_PERMESSO='Chiuso' THEN 'CLO' ELSE '' END AS STATO,
	(select id_utente from utenti where username='assistenza'),
	NR_PDL,
	IMPIANTO,
	ODL,
	DESCR_PG,
	DESCR_TESTO,
	EQUIPMENT,
	FLG_MECCANICA,
	FLG_STRUMENTALE,
	FLG_ELETTRICA,
	FLG_EDILE,
	FLG_A_FUOCO,
	FLG_IN_QUOTA,
	FLG_SCAVO,
	FLG_IN_SPAZI_CONFINATI,
	FLG_COIBENTAZIONE_SCOIBENTAZIONE,
	FLG_CIECATURA_RIMOZIONE,
	FLG_PULIZIA_FACCHINAGGIO,
	FLG_VERNICIATURA,
	FLG_SABBIATURA,
	FLG_AGGOTTAMENTO,
	ALTRO_SEZ1_TIPOLAVORO1_TESTO,
	ALTRO_SEZ1_TIPOLAVORO2_TESTO,
	coalesce(DESCRIZIONE_LAVORO,'') as DESCRIZIONE_LAVORO,
	FLG_OSSIGENO,
	FLG_AZOTO,
	FLG_ARGON,
	FLG_LIQUIDI_CRIOGENICI,
	FLG_METANO_O_GAS_NATURALE,
	FLG_IDROCARBURI,
	FLG_PERLITE,
	FLG_BUTANO,
	FLG_SYNGAS,
	FLG_IDROGENO,
	FLG_DMDS,
	FLG_OLI,
	ALTRO_SEZ1_PRODOTTI1_TESTO,
	FLG_RUMORE,
	FLG_ALTA_TEMPERATURA,
	FLG_PRESSIONE,
	FLG_ALTEZZA,
	FLG_CRIOGENIA_BASSA_TEMPERATURA,
	FLG_PARTI_IN_MOVIMENTO,
	FLG_APPAR_CAVI_ELETTRICI,
	FLG_PRODOTTI_CHIMICI,
	FLG_MATERIALI_INFIAMMABILI,
	ALTRO_SEZ1_ATTIVITA1_TESTO,
	FLG_INTERCETTARE,
	FLG_DEPRESSURIZZARE,
	FLG_VUOTARE,
	FLG_DOPPIE_VALVOLE_CHIUSE,
	FLG_SPURGHI_APERTI,
	FLG_SPURGHI_CHIUSI,
	FLG_VENT_APERTI,
	FLG_VENT_CHIUSI,
	FLG_SCONNETTERE_LINEE,
	FLG_SFLANGIARE,
	FLG_CONTROLLARE_PRESSIONE,
	FLG_CONTROLLARE_TEMPERATURA,
	FLG_AREARE,
	FLG_DOPPIA_VALVOLA_CHIUSA_E_SPURGO_INTERMEDIO_APERTO,
	FLG_LAVARE_CON_PRODOTTI_COMPATIBILI_ALIMENTARE_FARMACEUTICO,
	FLG_PREDISPORRE_CARTELLI_DIVIETO_MANOVRA,
	FLG_PREDISPORRE_ILLUMINAZIONE,
	FLG_PREDISPORRE_ATTREZZATURE_ANTINCENDIO,
	FLG_LOCALIZZARE_LINEE_INTERRATE,
	FLG_APPLICARE_SEGNALETICA,
	FLG_TRIPODE,
	FLG_PROTEGGERE_FOGNE,
	FLG_PONTEGGIO_PIANO_DI_LAVORO,
	FLG_PIATTAFORMA,
	FLG_DELIMITARE_ZONA_LAVORO,
	FLG_SQUADRA_DI_SOCCORSO_IN_STAND_BY,
	FLG_INSUFFLARE_AZOTO,
	FLG_INSUFFLARE_ARIA,
	FLG_ESEGUIRE_DOPO_COMPLETAMENTO_E_CHIUSURA_PERMESSI_N,
	PDL1_NR,
	FLG_CIECATURA,
	SCHEDA_CIECATURA_NR,
	FLG_RIMOZIONE_TEMPORANEA_EIS,
	SCHEDA_EIS_NR,
	FLG_MESSA_IN_SICUREZZA_ELETTRICA,
	MESSA_IN_SICUREZZA_ELETTRICA_TESTO,
	FLG_PROVA_DI_ABITABILITA,
	PROVA_DI_ABITABILITA_TESTO,
	FLG_PROVA_DI_ESPLOSIVITA,
	PROVA_DI_ESPLOSIVITA_TESTO,
	FLG_CONSEGNA_VERIFICA_ACCETTAZIONE,
	LI_MOD_47,
	FLG_LAVARE_CON,
	LAVARE_CON_TESTO,
	FLG_BONIFICARE_CON,
	BONIFICARE_CON_TESTO,
	FLG_PREDISPORRE_MANICHETTE_CON,
	PREDISPORRE_MANICHETTE_CON_TEST,
	ALTRO_SEZ1_PRESCRIZIONI_SIC1_TESTO,
	FLG_PRODUZIONE_RIFIUTI_PERICOLOSI,
	FLG_PRODUZIONE_RIFIUTI_NON_PERICOLOSI,
	FLG_RISCHIO_SPANDIMENTO,
	FLG_EMISSIONI_IN_ATMOSFERA,
	PRECAUZIONI_DA_ADOTTARE,
	FLG_TUTA_DA_LAVORO,
	FLG_SCARPE_DI_SICUREZZA,
	FLG_GUANTI,
	FLG_OCCHIALI,
	FLG_ELMETTO,
	FLG_MASCHERA_ANTIPOLVERE,
	FLG_MASCHERA_ANTIGAS,
	FLG_AUTORESPIRATORE,
	FLG_VISIERA_DI_PROTEZIONE,
	FLG_PROTEZIONI_AURICOLARI,
	FLG_RILEVATORE_OSSIGENO,
	FLG_ESPLOSIMETRO,
	FLG_INDUMENTI_IGNIFUGHI_SPECIALI,
	FLG_STIVALI_ANTIFORTUNISTICI,
	FLG_IMBRAC_CINTURA_DI_SICUREZZA,
	FLG_GUANTI_PROTETTIVI_PER_AGENTI_CHIMICI,
	FLG_PROTEZIONE_VISO_E_CORPO_PER_AGENTI_CHIMICI,
	FLG_GUANTI_PROTEZIONE_PER_ALTE_TEMPERATURE,
	FLG_GUANTI_CRIOGENICI,
	FLG_GUANTI_DIELETTRICI,
	FLG_ELMETTO_DIELETTRICO,
	ULTERIORI_PRESCRIZIONI_DI_SICUREZZA_DA_ADOTTARE,
	DATA_SEZ1,
	RESPONSABILE_CENTRALE_DELEGATO,
	FLG_PERSONALE_INTERNO,
	FLG_PERSONALE_ESTERNO,
	IMPRESA_TESTO,
	FLG_CASSETTA_ATTREZZI,
	FLG_MOLA,
	FLG_TAGLIA_TUBI,
	FLG_ELETTROSALDATRICE,
	FLG_MOTOSALDATRICE,
	FLG_PARANCO,
	FLG_TRAPANO_ELETTRICO,
	FLG_TRAPANO_A_TENUTA,
	FLG_SABBIATRICE,
	FLG_ATTREZZI_ANTISCINTILLA,
	FLG_CARROPONTE,
	FLG_ESCAVATRICE,
	FLG_LANCIA_ACQUA_IN_PRESSIONE,
	FLG_TRAPANO_PNEUMATICO,
	FLG_CANNELLO_OSSIACETILENICO,
	FLG_UTENSILI_A_TENSIONE_DI_SICUREZZA,
	FLG_ATTREZZATURE_LAVAGGI_CHIMICI,
	FLG_ATTREZZATURE_PER_RADIOGRAFIE,
	FLG_AUTOGRU,
	FLG_AUTOMEZZO,
	FLG_CARRELLO_ELEVATORE,
	FLG_MOTOPOMPA,
	FLG_AUTOSPURGO,
	FLG_SCOVOLATRICE,
	FLG_MOTOCOMPRESSORE,
	ALTRA_ATTREZZATURA,
	PREPARAZ_DEL_LAVORO_PRECAUZIONI_PREVISTE_DURANTE_LESECUZIONE,
	RISCHI_SPECIFICI_ATT_INDICAZ_DEI_MEZZI_PROTEZIONE_PREVISTI,
	NOME_COGNOME_PREPOSTO_IMPRESA,
	NOME_COGNOME_DELEGATO_LAVORI_AL,
	STATO_PERMESSO,
	DESCRIZIONE_DELLA_SOSPENSIONE,
	FLG_AREA_RIPULITA,
	AREA_RIPULITA_TESTO,
	CAPO_TURNO,
	to_char(DATA_CHIUSURA,'yyyy/mm/dd'),
	FLG_CONVALIDA,
	FLG_PONTEGGIO,
	FLG_VAPORE,
	FLG_PERMESSO_DI_ACCESSO,
	FLG_LOTO,
	LOTO_TESTO,
	FLG_SOLO_TAG_OUT,
	ALTRO_SEZ1_PRESCRIZIONI_SIC2_TESTO
FROM TEMP_PDL as p1
left outer join impianti as imp on  coalesce(p1.IMPIANTO,'ND') = imp.codice_impianto and imp.id_impianto in (select id_impianto from impianti where id_azienda in (select id_azienda from aziende where codice ='LIM') )
left outer join area_lavoro as are on  COALESCE(p1.area_lavoro,'NON DISPONIBILE') = are.descr_area and are.id_impianto = imp.id_impianto
left outer join equipment as equ on COALESCE(p1.equipment,'NON DISPONIBILE') = equ.descr_equipment and equ.id_area=are.id_area 
where 
imp.id_impianto is not null
and are.id_area is not null
and equ.id_equipment is not null
;


--2200
/*
update PDL set STATO='CLO'
where DT_PDL < (select to_char(current_date - interval '30 day','yyyy/mm/dd')) and STATO='OPE' OR STATO='';

update PDL set CAPO_TURNO='INTERNO' where STATO='CLO' AND CAPO_TURNO is null;
*/