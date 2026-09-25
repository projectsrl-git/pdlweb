delete from ALLESTIMENTI;
ALTER SEQUENCE ALLESTIMENTI_id_allestimento_seq RESTART WITH 1;
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',1,'Taglia autorizzata',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',2,'Valvola',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',3,'Cappellotto',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',4,'Cappellotto',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',5,'Termoretraibile',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',6,'Sigillo riempimento',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',7,'Cartellino',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',8,'Barcode',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('REG',9,'Anello di scadenza',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));

insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',1,'Taglia autorizzata',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',2,'Valvola',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',3,'Cappellotto',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',4,'Cappellotto',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',5,'Termoretraibile',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',6,'Sigillo riempimento',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',7,'Cartellino',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',8,'Barcode',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',9,'Anello di scadenza',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('SMA',10,'Adesivo Sicurezza giallo',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));

insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',1,'Taglia autorizzata',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',2,'Valvola',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',3,'Cappellotto',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',4,'Grani a punta',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',5,'Viti in plastica',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',6,'Sigillo riempimento',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',7,'Cartellino',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',8,'Barcode',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',9,'Anello di scadenza',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',10,'Adesivo Avvertenza',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('ALT',11,'Adesivo NON RIMUOVERE',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));

insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',1,'Taglia autorizzata',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',2,'Valvola',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',3,'Termoretraibile',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',4,'Sigillo riempimento',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',5,'Cartellino',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',6,'Barcode',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',7,'Anello scadenza',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',8,'Staffa supporto valvola',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',9,'Blocchetto valvola',false,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',10,'Logo ADR + brand',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));
insert into ALLESTIMENTI (codice_allestimento,ordine, caratteristica,  fl_materiale,id_utente_ins)  values ('PAC',11,'Pittogrammi ADR',true,(SELECT ID_UTENTE FROM UTENTI WHERE USERNAME='armando.canto@airliquide.com'));



delete from materiali;
ALTER SEQUENCE materiali_id_materiale_seq RESTART WITH 1;
insert into materiali (descrizione,codice_sap) values ('Tipo EXAL - Blu RAL 5015','21163');

insert into materiali (descrizione,codice_sap) values ('Su leva riempimento','22569');
insert into materiali (descrizione,codice_sap) values ('Fissaggio cappellotto 2X','45328');
insert into materiali (descrizione,codice_sap) values ('Fissaggio coperchio cappellotto','45329');
insert into materiali (descrizione,codice_sap) values ('Tipo ARGON RPV non cromato','45514');
insert into materiali (descrizione,codice_sap) values ('Tipo ALTOP ARGON','45525');
insert into materiali (descrizione,codice_sap) values ('Argon per pacchi','45548');
insert into materiali (descrizione,codice_sap) values ('Su cappellotto','46939');
insert into materiali (descrizione,codice_sap) values ('Tipo Altop - Blu RAL 5015','58429');
insert into materiali (descrizione,codice_sap) values ('Grigio, sulla valvola','58435');


insert into materiali (descrizione,codice_sap) values ('Su pannello frontale','58622');
insert into materiali (descrizione,codice_sap) values ('Tipo Smartop Argon','63942');
insert into materiali (descrizione,codice_sap) values ('Sulla valvola Smartop','108505');
insert into materiali (descrizione,codice_sap) values ('Su 3 lati senza valvola','152915');

insert into materiali (descrizione,codice_sap) values ('Tipo Scandina Blu RAL 5015','159605');
insert into materiali (descrizione,codice_sap) values ('Attorno alla vavola','XXX');


insert into materiali (descrizione,codice_sap) values ('Su foro riempimento','YYY');
