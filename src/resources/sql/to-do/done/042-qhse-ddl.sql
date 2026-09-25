/*
created: 12/11/2017
modified: 14/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists public.alimod80 cascade
;
drop table if exists public.alimod20_master cascade
;
drop table if exists public.alimod50 cascade
;
drop table if exists public.alimod50_dettagli cascade
;
drop table if exists public.alimod05 cascade
;
drop table if exists public.alimod05_dettagli cascade
;
drop table if exists public.alimod20 cascade
;
drop table if exists public.alimod20_dettagli cascade
;
drop table if exists public.alimod67 cascade
;
drop table if exists public.alimod67_dettagli cascade
;

-- create tables section -------------------------------------------------

-- table public.alimod67_dettagli

create table public.alimod67_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(15) not null,
 azioni_raccomandate text,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod67_dettagli

alter table public.alimod67_dettagli add constraint pk_alimod67_dettagli primary key (id_dettaglio,id_modulo)
;

alter table public.alimod67_dettagli add constraint k1_alimod67_dettagli unique (id_dettaglio)
;

-- table public.alimod67

create table public.alimod67(
 id_modulo serial not null,
 codice_bl character(3) not null,
 id_azienda bigint not null,
 nr_modulo character varying(15) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 osservato text not null,
 mansione text not null,
 attivita_osservata text not null,
 osservatore text not null,
 altro_osservatore text,
 fl_gest_postazione_1 character(2),
 fl_gest_postazione_2 character(2),
 fl_gest_postazione_3 character(2),
 fl_gest_postazione_4 character(2),
 fl_ergonomia_1 character(2),
 fl_ergonomia_2 character(2),
 fl_ergonomia_3 character(2),
 fl_ergonomia_4 character(2),
 fl_ergonomia_5 character(2),
 fl_ergonomia_6 character(2),
 fl_procedure_1 character(2),
 fl_procedure_2 character(2),
 fl_procedure_3 character(2),
 fl_procedure_4 character(2),
 fl_attrezzature_1 character(2),
 fl_attrezzature_2 character(2),
 fl_dpi_1 character(2),
 fl_dpi_2 character(2),
 fl_dpi_3 character(2),
 fl_dpi_4 character(2),
 fl_dpi_5 character(2),
 fl_dpi_6 character(2),
 fl_dpi_7 character(2),
 fl_valutazione_1 character(2),
 fl_valutazione_2 character(2),
 note_gest_postazione_1 text,
 note_gest_postazione_2 text,
 note_gest_postazione_3 text,
 note_gest_postazione_4 text,
 note_ergonomia_1 text,
 note_ergonomia_2 text,
 note_ergonomia_3 text,
 note_ergonomia_4 text,
 note_ergonomia_5 text,
 note_ergonomia_6 text,
 note_procedure_1 text,
 note_procedure_2 text,
 note_procedure_3 text,
 note_procedure_4 text,
 note_attrezzature_1 text,
 note_attrezzature_2 text,
 note_dpi_1 text,
 note_dpi_2 text,
 note_dpi_3 text,
 note_dpi_4 text,
 note_dpi_5 text,
 note_dpi_6 text,
 note_dpi_7 text,
 note_valutazione_1 text,
 note_valutazione_2 text,
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod67

alter table public.alimod67 add constraint pk_alimod67 primary key (id_modulo)
;

alter table public.alimod67 add constraint k1_alimod67 unique (id_modulo)
;

-- table public.alimod20_dettagli

create table public.alimod20_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(15) not null,
 codice_tipo_azione character(3) not null,
 riferimento_origine text,
 descrizione text,
 id_utente_res integer not null,
 assegnatario character varying(50),
 dt_scadenza character(10) not null,
 dt_chiusura character(10),
 costo_azione numeric(10,2),
 effort integer,
 stato_azione character(3) not null,
 codice_classificazione character(3) not null,
 tabella_parent character varying(30),
 id_dettaglio_parent integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod20_dettagli

alter table public.alimod20_dettagli add constraint pk_alimod20_dettagli primary key (id_dettaglio,id_modulo)
;

alter table public.alimod20_dettagli add constraint k1_alimod20_dettagli unique (id_dettaglio)
;

-- table public.alimod20

create table public.alimod20(
 id_modulo serial not null,
 codice_bl character(3) not null,
 id_azienda integer not null,
 nr_modulo character varying(15) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 titolo character varying(100) not null,
 id_utente_con integer not null,
 id_utente_app integer,
 tabella_parent character varying(30),
 id_modulo_parent integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod20

alter table public.alimod20 add constraint pk_alimod20 primary key (id_modulo)
;

alter table public.alimod20 add constraint k1_alimod20 unique (id_modulo)
;

-- table public.alimod05_dettagli

create table public.alimod05_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(15) not null,
 codice_tipo_azione character(3) not null,
 descrizione text not null,
 dt_prevista character(10) not null,
 dt_verifica character(10) not null,
 id_utente_res_1 integer not null,
 id_utente_res_2 integer not null,
 flg_azione boolean default false not null,
 dt_verifica_efficacia character(10) not null,
 id_utente_res_3 integer not null,
 origine_ap text not null,
 attestazione text not null,
 quality_assurance text not null,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod05_dettagli

alter table public.alimod05_dettagli add constraint pk_alimod05_dettagli primary key (id_dettaglio,id_modulo)
;

alter table public.alimod05_dettagli add constraint k1_alimod05_dettagli unique (id_dettaglio)
;

-- table public.alimod05

create table public.alimod05(
 id_modulo serial not null,
 codice_bl character(3) not null,
 id_azienda integer not null,
 nr_modulo character varying(15) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 sez1_norma_legge_doc text,
 sez1_capitolo text,
 sez1_fornitore_cliente text,
 sez1_tipo_serv_prod text,
 sez1_descrizione_nc text,
 sez1_fl_gmp character(3),
 sez1_evidenze text,
 sez1_osservazioni text,
 sez1_verificatore_nc text,
 sez1_dt_nc character(10),
 sez1_id_utente_res integer,
 sez2_trattamento_nc text,
 sez2_dt_prevista_nc character(10),
 sez2_dt_verifica_nc character(10),
 sez2_id_utente_res_1 integer,
 sez2_id_utente_res_2 integer,
 sez2_fl_trattamento_nc boolean default false,
 sez2_dt_verifica_tratt_nc character(10),
 sez2_id_utente_res_3 integer,
 sez3_causa_nc text,
 sez3_dt_causa_nc character(10),
 sez3_id_utente_res integer,
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod05

alter table public.alimod05 add constraint pk_alimod05 primary key (id_modulo)
;

alter table public.alimod05 add constraint k1_alimod05 unique (id_modulo)
;

-- table public.alimod50_dettagli

create table public.alimod50_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(15) not null,
 riferimento_origine text,
 descrizione text not null,
 fl_conforme character(2) not null,
 note text,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod50_dettagli

alter table public.alimod50_dettagli add constraint pk_alimod50_dettagli primary key (id_dettaglio,id_modulo)
;

alter table public.alimod50_dettagli add constraint k1_alimod50_dettagli unique (id_dettaglio)
;

-- table public.alimod50

create table public.alimod50(
 id_modulo serial not null,
 codice_bl character(3) not null,
 id_azienda integer not null,
 nr_modulo character varying(15) not null,
 dt_modulo character(15) not null,
 codice_tipo_verifica character(3) not null,
 stato character(3) not null,
 titolo character varying(100) not null,
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod50

alter table public.alimod50 add constraint pk_alimod50 primary key (id_modulo)
;

alter table public.alimod50 add constraint k1_alimod50 unique (id_modulo)
;

-- table public.alimod20_master

create table public.alimod20_master(
 id_alimod20_master serial not null,
 nome_responsabile character varying(50) not null,
 descrizione_sito character varying(50) not null,
 nr_pda character varying(10) not null,
 nr_azione character varying(10) not null,
 tipo_azione character varying(50) not null,
 business_line character(2),
 titolo_pda character varying not null,
 descrizione_azione character varying not null,
 dt_pda character(10) not null,
 dt_chiusura character(10),
 dt_scadenza character(10),
 costo_azione numeric(10,2),
 stato_azione character varying(20),
 perc_avanzamento numeric(10,2),
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod20_master

alter table public.alimod20_master add constraint pk_alimod20_master primary key (id_alimod20_master)
;

alter table public.alimod20_master add constraint id_alimod20_master unique (id_alimod20_master)
;

-- table public.alimod80

create table public.alimod80(
 id_modulo serial not null,
 codice_bl character(3) not null,
 id_azienda integer not null,
 nr_modulo character varying(15) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 tipo_comunicazione character varying(50) not null,
 azioni_evento character varying,
 codice_tipo_impianto character(3),
 codice_natura_evento character(3),
 codice_prodotto_pertinente character(3),
 codice_giorni character(3),
 codice_ore character(3),
 codice_min character(3),
 codice_codice_categoria_personale character(3),
 codice_codice_tipo_infortunio character(3),
 codice_codice_attrezzatura_interessata character(3),
 codice_comp_attr_interessata character(3),
 codice_gg_prognosi character(3),
 codice_lesione_maggiore character(3),
 codice_parte_corpo character(3),
 codice_altre_lesioni character(3),
 codice_altre_lesioni_parte_corpo character(3),
 descrizione_evento text,
 descrizione_azioni text,
 codice_aff_attrezzatura_interessata character(3),
 codice_aff_comp_attr_interessata character(3),
 codice_costi_euro character(3),
 codice_livello_incidente character(3),
 codice_cliente character(3),
 reclamo_cliente character(1),
 forza_maggiore character(1),
 altri_clienti character varying,
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table public.alimod80

alter table public.alimod80 add constraint pk_alimod80 primary key (id_modulo)
;

alter table public.alimod80 add constraint k1_alimod80 unique (id_modulo)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

alter table public.alimod05_dettagli add constraint fk_dettagli_alimod05 foreign key (id_modulo) references public.alimod05 (id_modulo) on delete cascade on update no action
;

alter table public.alimod67_dettagli add constraint fk_dettagli_alimod67 foreign key (id_modulo) references public.alimod67 (id_modulo) on delete cascade on update no action
;

alter table public.alimod20_dettagli add constraint fk_dettagli_alimod20 foreign key (id_modulo) references public.alimod20 (id_modulo) on delete cascade on update no action
;

alter table public.alimod50_dettagli add constraint fk_dettagli_alimod50 foreign key (id_modulo) references public.alimod50 (id_modulo) on delete cascade on update no action
;


