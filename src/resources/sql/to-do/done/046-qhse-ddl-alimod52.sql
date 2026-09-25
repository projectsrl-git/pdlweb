/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists alimod52_dettagli cascade;

drop table if exists alimod52 cascade
;


-- create tables section -------------------------------------------------


-- table alimod52

create table alimod52(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 id_azienda bigint not null,

 mese character(2) not null,
 durata_riunione text not null,
 relatore text not NULL,
  
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod52

alter table alimod52 add constraint pk_alimod52 primary key (id_modulo)
;

alter table alimod52 add constraint k1_alimod52 unique (id_modulo)
;


-- table alimod52_dettagli

create table alimod52_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,
 
 tipo_dettaglio text not null,
 
 argomento text not null,
 
 azione_nota_commento text not null,
 a_cura_di text not null,
 entro_il text not null,
 
 mese_riunione character(2) not null,
 data_riunione character(10) not null,
 nominativo text not null,
 team text not null,
 data character(10) not null,

 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod52_dettagli

alter table alimod52_dettagli add constraint pk_alimod52_dettagli primary key (id_dettaglio,id_modulo)
;

alter table alimod52_dettagli add constraint k1_alimod52_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

--alter table alimod20dettagli add constraint fkdettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table alimod52_dettagli add constraint fkdettagli_alimod52 foreign key (id_modulo) references alimod52 (id_modulo) on delete cascade on update no action
;