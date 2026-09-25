DROP VIEW IF EXISTS v_parametri cascade  ;
DROP VIEW IF EXISTS V_PDL;
DROP VIEW IF EXISTS v_alimod20_dettaglio ;
DROP VIEW IF EXISTS v_alimod80;
DROP VIEW IF EXISTS v_alimod50;
DROP VIEW IF EXISTS v_alimod20;
DROP VIEW IF EXISTS v_alimod67;
DROP VIEW IF EXISTS v_allestimenti ;
DROP VIEW IF EXISTS v_specifiche_allestimenti  ;
DROP VIEW IF EXISTS v_sitiquse_bl ;
DROP VIEW IF EXISTS v_alimod05 ;
DROP VIEW IF EXISTS v_alimod51  ;


ALTER TABLE PARAMETRI ALTER COLUMN DESCRIZIONE TYPE VARCHAR (150);

DELETE FROM parametri WHERE DOMINIO ='M52' OR (DOMINIO ='DOM' AND CODICE='M52');
insert into parametri (dominio,codice,descrizione) values ('DOM','M52','Selezione dettagli modulo 52');
insert into parametri (dominio,codice,descrizione,ordine) values ('M52','D01','Argomenti trattati/materiali consegnati',1);
insert into parametri (dominio,codice,descrizione,ordine) values ('M52','D02','Azioni/Note e commenti',2);
insert into parametri (dominio,codice,descrizione,ordine) values ('M52','D03','Presenze personale ALI Industria / Società Controllata',3);
insert into parametri (dominio,codice,descrizione,ordine) values ('M52','D04','Presenze personale interinale, collaboratori a progetto, collaboratori occasionali, stagisti, consulenti',4);
insert into parametri (dominio,codice,descrizione,ordine) values ('M52','D05','Presenze personale di società esterne',5);
insert into parametri (dominio,codice,descrizione,ordine) values ('M52','D06','Altri',6);