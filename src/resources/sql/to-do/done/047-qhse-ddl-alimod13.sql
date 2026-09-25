/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists limod13_dettagli cascade;

drop table if exists limod13 cascade
;


-- create tables section -------------------------------------------------


-- table limod13

 create table limod13(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 id_azienda bigint not null,

 
 
 ora character(5) not null,
 ut text not null,
 prodotto text not null,
 audit text not null,
 autista text not null,
 note text not null,
 operatore_verificato text not null,
 verificatore text not null,
 
 flg_1	character(2) not null,
 flg_2	character(2) not null,
 flg_3	character(2) not null,
 flg_4	character(2) not null,
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
 flg_16	character(2) not null,
 flg_17	character(2) not null,
 flg_18	character(2) not null,
 flg_19	character(2) not null,
 flg_20	character(2) not null,
 flg_21	character(2) not null,
 flg_22	character(2) not null,
 flg_23	character(2) not null,
 flg_24	character(2) not null,
 flg_25	character(2) not null,
 flg_26	character(2) not null,
 flg_27	character(2) not null,

 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table limod13

alter table limod13 add constraint pk_limod13 primary key (id_modulo)
;

alter table limod13 add constraint k1_limod13 unique (id_modulo)
;