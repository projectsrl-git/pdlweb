/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists storico_interferenze cascade;

-- create tables section -------------------------------------------------


-- table storico_interferenze

 CREATE TABLE STORICO_INTERFERENZE(
 ID_MODULO SERIAL NOT NULL,
 ID_IMPIANTO INT NOT NULL,
 ID_AZIENDA INT NOT NULL,
 CODICE_TURNO CHARACTER(3),
 NOME_FILE TEXT,
 NOME_FILE_COMPLETO TEXT,
 ORA_STAMPA CHARACTER(5),
 
 DATA_STAMPA CHARACTER(10),
 ID_UTENTE INTEGER NOT NULL
)
;

-- add keys for table storico_interferenze

alter table storico_interferenze add constraint pk_storico_interferenze primary key (id_modulo)
;

alter table storico_interferenze add constraint k1_storico_interferenze unique (id_modulo)
;