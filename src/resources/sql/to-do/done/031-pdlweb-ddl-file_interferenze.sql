/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists file_interferenze cascade
;


-- create tables section -------------------------------------------------


-- table file_interferenze

create table file_interferenze(
 id_file serial not null,
 id_impianto int not null,
 nome_file text not null,
 ts_ins timestamp default current_timestamp not null,
 id_utente_ins integer not null
)
;

-- add keys for table file_interferenze

alter table file_interferenze add constraint pk_file_interferenze primary key (id_file)
;

alter table file_interferenze add constraint k1_file_interferenze unique (id_file)
;