/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists alimod50_dettagli cascade
;
drop table if exists alimod20_dettagli cascade
;
drop table if exists alimod80 cascade
;
drop table if exists alimod50 cascade
;
drop table if exists alimod20 cascade
;
drop table if exists alimod20_master cascade
;

-- create tables section -------------------------------------------------

-- table alimod20_master

create table alimod20_master(
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
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod20_master

alter table alimod20_master add constraint pk_alimod20_master primary key (id_alimod20_master)
;

-- table alimod20

create table alimod20(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 dt_modulo character(10) not null,
 codice_sito character varying(10) not null,
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

-- add keys for table alimod20

alter table alimod20 add constraint pk_alimod20 primary key (id_modulo)
;

alter table alimod20 add constraint k1_alimod20 unique (id_modulo)
;

-- table alimod50

create table alimod50(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 codice_tipo_verifica character(3) not null,
 dt_modulo character(10) not null,
 codice_sito character varying(10) not null,
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

-- add keys for table alimod50

alter table alimod50 add constraint pk_alimod50 primary key (id_modulo)
;

alter table alimod50 add constraint k1_alimod50 unique (id_modulo)
;

-- table alimod80
DROP TABLE IF EXISTS alimod80 CASCADE;
create table alimod80(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 dt_modulo character(10) not null,
 codice_bl character(3) not null,
 codice_sito character varying(10) not null,
 stato character(3) not null,
 tipo_comunicazione VARCHAR not null,
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer,
 codice_tipo_impianto character varying(3),
 codice_natura_evento character varying(3),
 codice_prodotto_pertinente character varying(3),
 AZIONI_EVENTO VARCHAR
)
;

-- add keys for table alimod80

alter table alimod80 add constraint pk_alimod80 primary key (id_modulo)
;

alter table alimod80 add constraint k1_alimod80 unique (id_modulo)
;

-- table alimod20_dettagli

create table alimod20_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_azione character varying(10) not null,
 codice_tipo_azione character(3) not null,
 riferimento_origine text,
 descrizione text,
 id_utente_res integer not null,
 id_utente_ass integer,
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

-- add keys for table alimod20_dettagli

alter table alimod20_dettagli add constraint pk_alimod20_dettagli primary key (id_dettaglio,id_modulo)
;

alter table alimod20_dettagli add constraint k1_alimod20_dettagli unique (id_dettaglio)
;

-- table alimod50_dettagli

create table alimod50_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,
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

-- add keys for table alimod50_dettagli

alter table alimod50_dettagli add constraint pk_alimod50_dettagli primary key (id_dettaglio,id_modulo)
;

alter table alimod50_dettagli add constraint k1_alimod50_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

alter table alimod20_dettagli add constraint fk_dettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table alimod50_dettagli add constraint fk_dettagli_alimod50 foreign key (id_modulo) references alimod50 (id_modulo) on delete cascade on update no action
;


