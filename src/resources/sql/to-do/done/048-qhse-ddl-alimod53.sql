/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists limod53_dettagli cascade;

drop table if exists limod53 cascade
;


-- create tables section -------------------------------------------------


-- table limod53

 create table limod53(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 id_azienda bigint not null,

 
 
 permesso_nr text not null,
 ditta text not null,
 operatore text not null,
 personale text not null,
 funzione text not null,
 audit text not null,
 osservatore text not null,
 persona_osservata text not null,
 
 flg_1	character(2) not null,
 flg_2	character(2) not null,
 flg_3	character(2) not null,
 flg_4a	character(2) not null,
 flg_4b	character(2) not null,
 flg_5	character(2) not null,
 flg_6	character(2) not null,
 flg_7	character(2) not null,
 flg_8	character(2) not null,
 flg_9	character(2) not null,
 flg_10	character(2) not null,
 flg_11	character(2) not null,
 flg_12	character(2) not null,
 flg_13	character(2) not null,
 flg_14	character(2) not null,
 flg_15	character(2) not null,
 
 note_1		text not null,
 note_2		text not null,
 note_3		text not null,
 note_4a	text not null,
 note_4b	text not null,
 note_5		text not null,
 note_6		text not null,
 note_7		text not null,
 note_8		text not null,
 note_9		text not null,
 note_10	text not null,
 note_11	text not null,
 note_12	text not null,
 note_13	text not null,
 note_14	text not null,
 note_15	text not null,

 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table limod53

alter table limod53 add constraint pk_limod53 primary key (id_modulo)
;

alter table limod53 add constraint k1_limod53 unique (id_modulo)
;







-- table limod53_dettagli

create table limod53_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,
  
 situazione text not null,
 
 azione text not null,
 a_cura_di text not null,
 entro_il text not null,
 
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table limod53_dettagli

alter table limod53_dettagli add constraint pk_limod53_dettagli primary key (id_dettaglio,id_modulo)
;

alter table limod53_dettagli add constraint k1_limod53_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

--alter table alimod20dettagli add constraint fkdettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table limod53_dettagli add constraint fkdettagli_limod53 foreign key (id_modulo) references limod53 (id_modulo) on delete cascade on update no action
;