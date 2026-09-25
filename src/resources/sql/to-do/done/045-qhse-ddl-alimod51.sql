/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists alimod51_dettagli cascade
;
drop table if exists alimod51 cascade
;


-- create tables section -------------------------------------------------


-- table alimod51

create table alimod51(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 id_azienda bigint not null,

 datore_delegato text not null,
 rspp text not null,
 medico text not null,
 rls text not null,
  
 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod51

alter table alimod51 add constraint pk_alimod51 primary key (id_modulo)
;

alter table alimod51 add constraint k1_alimod51 unique (id_modulo)
;


-- table alimod51_dettagli

create table alimod51_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,
 
 argomento text not null,
 azione_nota_commento text not null,
 a_cura_di text not null,
 entro_il text not null,

 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod51_dettagli

alter table alimod51_dettagli add constraint pk_alimod51_dettagli primary key (id_dettaglio,id_modulo)
;

alter table alimod51_dettagli add constraint k1_alimod51_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

--alter table alimod20_dettagli add constraint fk_dettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table alimod51_dettagli add constraint fk_dettagli_alimod51 foreign key (id_modulo) references alimod51 (id_modulo) on delete cascade on update no action
;


