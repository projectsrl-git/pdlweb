/*
created: 12/11/2017
modified: 11/01/2018
model: ali-qhse
database: postgresql 9.4
*/




-- drop tables section ---------------------------------------------------

drop table if exists utente_turno cascade;

-- create tables section -------------------------------------------------


-- table utente_turno

 CREATE TABLE utente_turno(
 ID_UTENTE_TURNO SERIAL NOT NULL,
 ID_UTENTE INT NOT NULL,
 CODICE_TURNO CHARACTER(3),
 DATA_FINE_TURNO CHARACTER(10),
 ORA_FINE_TURNO CHARACTER(5),
 
 TS_LOGIN TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
 TS_LOGOUT TIMESTAMP
)
;

-- add keys for table utente_turno

alter table utente_turno add constraint pk_utente_turno primary key (ID_UTENTE_TURNO)
;

alter table utente_turno add constraint k1_utente_turno unique (ID_UTENTE_TURNO)
;