/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists limod70 cascade
;
drop table if exists limod70_dettagli cascade;


-- create tables section -------------------------------------------------


-- table limod70

 create table limod70(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 id_impianto int not null,
 id_azienda int not null,
 id_area int not null,
 codice_turno character(3),
  valutazione character varying(3) ,
 distanza_rispetto character varying(1),
 nome_file text,
 ora character(8),
 
  tabella_parent character varying(30),
 id_modulo_parent integer,

 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null
)
;

-- add keys for table limod70

alter table limod70 add constraint pk_limod70 primary key (id_modulo)
;

alter table limod70 add constraint k1_limod70 unique (id_modulo)
;








-- table limod70_dettagli

create table limod70_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,
 
 id_pdl integer,

 tabella_parent character varying(30),
 id_dettaglio_parent integer,

 
 ts_ins timestamp default current_timestamp ,
 id_utente_ins integer 
)
;

-- add keys for table limod70_dettagli

alter table limod70_dettagli add constraint pk_limod70_dettagli primary key (id_dettaglio,id_modulo)
;

alter table limod70_dettagli add constraint k1_limod70_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

--alter table alimod20dettagli add constraint fkdettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table limod70_dettagli add constraint fkdettagli_limod70 foreign key (id_modulo) references limod70 (id_modulo) on delete cascade on update no action
;