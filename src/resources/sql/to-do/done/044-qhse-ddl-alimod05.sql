/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists alimod05_dettagli cascade
;
drop table if exists alimod05 cascade
;


-- create tables section -------------------------------------------------


-- table alimod05

create table alimod05(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
  id_azienda bigint not null,

 
 sez1_norma_legge_doc text not null,
 sez1_capitolo text not null,
 sez1_fornitore_cliente text not null,
 sez1_tipo_serv_prod text not null,
 sez1_descrizione_nc text not null,
 sez1_FL_GMP character(3) not null,
 sez1_evidenze text not null,
 sez1_osservazioni text not null,
 sez1_verificatore_nc text not null,
 sez1_dt_nc character(10) not null,
 sez1_id_utente_res integer not null,
 
 sez2_trattamento_nc text not null,
 sez2_dt_prevista_nc character(10) not null,
 sez2_dt_verifica_nc character(10) not null,
 sez2_id_utente_res_1 integer not null,
 sez2_id_utente_res_2 integer not null,
 FLG_trattamento_nc BOOLEAN DEFAULT FALSE NOT NULL,
 sez2_dt_verifica_tratt_nc character(10) not null,
 sez2_id_utente_res_3 integer not null,

 sez3_causa_nc text not null,
 sez3_dt_causa_nc character(10) not null,
 sez3_id_utente_res integer not null,
 
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod05

alter table alimod05 add constraint pk_alimod05 primary key (id_modulo)
;

alter table alimod05 add constraint k1_alimod05 unique (id_modulo)
;


-- table alimod05_dettagli

create table alimod05_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,
 
 codice_tipo_azione character(3) not null,
 DESCRIZIONE TEXT NOT NULL,
 DT_PREVISTA CHARACTER(10) NOT NULL,
 DT_VERIFICA CHARACTER(10) NOT NULL,
 ID_UTENTE_RES_1 INTEGER NOT NULL,
 ID_UTENTE_RES_2 INTEGER NOT NULL,
 FLG_AZIONE BOOLEAN DEFAULT FALSE NOT NULL,
 DT_VERIFICA_EFFICACIA CHARACTER(10) NOT NULL,
 ID_UTENTE_RES_3 INTEGER NOT NULL,
 ORIGINE_AP TEXT NOT NULL,
 ATTESTAZIONE TEXT NOT NULL,
 QUALITY_ASSURANCE TEXT NOT NULL,

 TS_INS TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
 ID_UTENTE_INS INTEGER NOT NULL,
 TS_DEL TIMESTAMP,
 id_utente_del integer
)
;

-- add keys for table alimod05_dettagli

alter table alimod05_dettagli add constraint pk_alimod05_dettagli primary key (id_dettaglio,id_modulo)
;

alter table alimod05_dettagli add constraint k1_alimod05_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

--alter table alimod20_dettagli add constraint fk_dettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table alimod05_dettagli add constraint fk_dettagli_alimod05 foreign key (id_modulo) references alimod05 (id_modulo) on delete cascade on update no action
;


