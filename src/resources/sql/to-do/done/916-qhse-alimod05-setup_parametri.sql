
DELETE FROM parametri WHERE DOMINIO ='GMP' OR (DOMINIO ='DOM' AND CODICE='GMP');
insert into parametri (dominio,codice,descrizione) values ('DOM','GMP','Classificazione GMP');
insert into parametri (dominio,codice,descrizione,ordine) values ('GMP','G01','Critica',1);
insert into parametri (dominio,codice,descrizione,ordine) values ('GMP','G02','Maggiore',2);
insert into parametri (dominio,codice,descrizione,ordine) values ('GMP','G03','OOS',3);