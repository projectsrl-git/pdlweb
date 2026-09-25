/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists alimod80_dettagli cascade;

drop table if exists alimod80 cascade
;


-- create tables section -------------------------------------------------


-- table alimod80

 create table alimod80(
 id_modulo serial not null,
 nr_modulo character varying(10) not null,
 codice_bl character(3) not null,
 dt_modulo character(10) not null,
 stato character(3) not null,
 id_azienda bigint not null,

 responsabile_locale char(3),
 paese char(3),
 filiale char(3),
 
 tipo_comunicazione VARCHAR not null,
 codice_tipo_impianto char(3),
 codice_natura_evento char(3),
 codice_prodotto_pertinente char(3),
 codice_giorni char(3),
 codice_ore char(3),
 codice_min char(3),
 codice_ore_locale char(3),
 codice_min_locale char(3),
 azioni_evento text,
 

 codice_aff_attrezzatura_interessata char(3),
 codice_aff_comp_attr_interessata char(3),
 codice_costi_euro char(3),
 codice_livello_incidente char(3),
 codice_cliente char(3),

 reclamo_cliente char(1),
 forza_maggiore char(1),
 altri_clienti varchar,
 
 parte_interessata text,
 prodotto_rilasciato text,
 livello_incidente text,
 quantita_rilasciata text,
 unita_rilasciata char(3),
 quantita_max_permessa text,
 unita_max_permessa char(3),
 
 descrizione_evento text,
 descrizione_azioni text,

 
 albero_cause char(3),
 categoria_principale char(3),
 sottocategoria_principale char(3),
 categoria_secondaria char(3),
 sottocategoria_secondaria char(3),
 info_supplementari text,
 
 cognome_nome text,
 cellulare text,
 mail text,
 dt_report character(10) not null,
 num_filiale text,
 num_azienda text,


 id_utente_con integer not null,
 id_utente_app integer,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod80

alter table alimod80 add constraint pk_alimod80 primary key (id_modulo)
;

alter table alimod80 add constraint k1_alimod80 unique (id_modulo)
;







-- table alimod80_dettagli

create table alimod80_dettagli(
 id_dettaglio serial not null,
 id_modulo integer not null,
 nr_dettaglio character varying(10) not null,
  
 persona_infortunata text,
 codice_codice_categoria_personale char(3),
 codice_codice_tipo_infortunio char(3),
 codice_codice_attrezzatura_interessata char(3),
 codice_comp_attr_interessata char(3),
 codice_gg_prognosi char(3),
 codice_lesione_maggiore char(3),
 codice_parte_corpo char(3),
 codice_altre_lesioni char(3),
 codice_altre_lesioni_parte_corpo char(3),

 
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null,
 ts_del timestamp,
 id_utente_del integer
)
;

-- add keys for table alimod80_dettagli

alter table alimod80_dettagli add constraint pk_alimod80_dettagli primary key (id_dettaglio,id_modulo)
;

alter table alimod80_dettagli add constraint k1_alimod80_dettagli unique (id_dettaglio)
;
-- create foreign keys (relationships) section ------------------------------------------------- 

--alter table alimod20dettagli add constraint fkdettagli_alimod20 foreign key (id_modulo) references alimod20 (id_modulo) on delete cascade on update no action
;

alter table alimod80_dettagli add constraint fkdettagli_alimod80 foreign key (id_modulo) references alimod80 (id_modulo) on delete cascade on update no action
;