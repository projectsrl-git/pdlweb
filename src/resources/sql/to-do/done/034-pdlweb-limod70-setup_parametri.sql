DELETE FROM parametri WHERE DOMINIO ='VAL' OR (DOMINIO ='DOM' AND CODICE='VAL');
insert into parametri (dominio,codice,descrizione) values ('DOM','VAL','Valutazione');
insert into parametri (dominio,codice,descrizione) values ('VAL','PRI','Prima valutazione');
insert into parametri (dominio,codice,descrizione) values ('VAL','NUO','Valutazione per incremento attività o nuova impresa');



DELETE FROM parametri WHERE DOMINIO ='CTU' OR (DOMINIO ='DOM' AND CODICE='CTU');
insert into parametri (dominio,codice,descrizione) values ('DOM','CTU','Codice turno');
insert into parametri (dominio,codice,descrizione) values ('CTU','001','PRIMO TURNO (6-14)');
insert into parametri (dominio,codice,descrizione) values ('CTU','002','SECONDO TURNO (14-22)');
insert into parametri (dominio,codice,descrizione) values ('CTU','003','TERZO TURNO (22-6)');
insert into parametri (dominio,codice,descrizione) values ('CTU','004','QUARTO TURNO (6-18)');
insert into parametri (dominio,codice,descrizione) values ('CTU','005','QUINTO TURNO (18-6)');