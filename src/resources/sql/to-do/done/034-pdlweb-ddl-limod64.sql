/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists limod64 cascade
;
drop table if exists limod64_dettagli cascade;


-- create tables section -------------------------------------------------


-- table limod64

 create table limod64(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 id_impianto int not null,
 id_azienda int not null,
 id_area int not null,
 codice_turno character(3),
 nr_rev character(10) not null,
 dt_rev character(10) not null,
 id_coord_gest_int int,
 nome_file text,
 
  tabella_parent character varying(30),
 id_modulo_parent integer,

 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null
)
;

-- add keys for table limod64

alter table limod64 add constraint pk_limod64 primary key (id_modulo)
;

alter table limod64 add constraint k1_limod64 unique (id_modulo)
;








-- table limod64_dettagli

create table limod64_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,

  id_pdl integer,
  impresa_testo character varying(50) not null,
  nome_cognome_preposto_impresa character varying(50) not null,
  odl character varying(50),
  descr_attivita text,
  flg_a_1 boolean default false not null,
  flg_a_2 boolean default false not null,
  flg_a_3 boolean default false not null,
  flg_a_4 boolean default false not null,
  flg_a_5 boolean default false not null,
  flg_a_6 boolean default false not null,
  flg_a_7 boolean default false not null,
  flg_a_8 boolean default false not null,
  flg_a_9 boolean default false not null,
  flg_a_10 boolean default false not null,
  flg_a_11 boolean default false not null,
  flg_a_12 boolean default false not null,
  flg_a_13 boolean default false not null,
  flg_a_14 boolean default false not null,
  flg_a_15 boolean default false not null,
  note_a_15 text,
  
  flg_b_1  boolean default false not null,
  flg_b_2  boolean default false not null,
  flg_b_3  boolean default false not null,
  flg_b_4  boolean default false not null,
  flg_b_5  boolean default false not null,
  flg_b_6  boolean default false not null,
  flg_b_7  boolean default false not null,
  flg_b_8  boolean default false not null,
  flg_b_9  boolean default false not null,
  flg_b_10 boolean default false not null,
  flg_b_11 boolean default false not null,
  flg_b_12 boolean default false not null,
  flg_b_13 boolean default false not null,
  flg_b_14 boolean default false not null,
  flg_b_15 boolean default false not null,
  flg_b_16 boolean default false not null,
  flg_b_17 boolean default false not null,
  flg_b_18 boolean default false not null,
  note_b_18 text,
  
  flg_c1_1  boolean default false not null,
  flg_c1_2  boolean default false not null,
  flg_c1_3  boolean default false not null,
  flg_c1_4  boolean default false not null,
  flg_c1_5  boolean default false not null,
  flg_c1_6  boolean default false not null,
  note_c1_1 text,
  note_c1_2 text,
  note_c1_3 text,
  note_c1_4 text,
  note_c1_5 text,
  note_c1_6 text,
  
  flg_c2_1  boolean default false not null,
  flg_c2_2  boolean default false not null,
  flg_c2_3  boolean default false not null,
  flg_c2_4  boolean default false not null,
  note_c2_1 text,
  note_c2_2 text,
  note_c2_3 text,
  note_c2_4 text,
  
  flg_c3_1  boolean default false not null,
  flg_c3_2  boolean default false not null,
  flg_c3_3  boolean default false not null,
  flg_c3_4  boolean default false not null,
  note_c3_3 text,
  note_c3_4 text,
  stato_riga character(3) not null,
  
 tabella_parent character varying(30),
 id_dettaglio_parent integer,
 
 ts_ins timestamp default current_timestamp ,
 id_utente_ins integer 
)
;

-- add keys for table limod64_dettagli

alter table limod64_dettagli add constraint pk_limod64_dettagli primary key (id_dettaglio,id_modulo)
;

alter table limod64_dettagli add constraint k1_limod64_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

--alter table alimod20dettagli add constraint fkdettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table limod64_dettagli add constraint fkdettagli_limod64 foreign key (id_modulo) references limod64 (id_modulo) on delete cascade on update no action
;