select distinct pdl.id_azienda,aziende.ragsoc,aziende.codice, count(*) from pdl 
inner join aziende on pdl.id_azienda = aziende.id_azienda
group by pdl.id_azienda,aziende.ragsoc,aziende.codice;


select ts_attivazione,TS_FINE_TURNO_ATTIVAZIONE,ID_UTENTE_ATTIVAZIONE,nr_pdl from pdl where ts_attivazione is not null order by ts_attivazione desc;

/* 

select * from pdl where id_azienda=34;

ferrera
select * from aziende where codice='FEB';
select * from impianti where id_azienda=20;

select * from area_lavoro where id_impianto in (18,19,20,21);
insert into area_lavoro (id_impianto,descr_area,id_utente_ins) values (18,'Area non specificata',1);
insert into area_lavoro (id_impianto,descr_area,id_utente_ins) values (19,'Area non specificata',1);
insert into area_lavoro (id_impianto,descr_area,id_utente_ins) values (20,'Area non specificata',1);
insert into area_lavoro (id_impianto,descr_area,id_utente_ins) values (21,'Area non specificata',1);

insert into equipment (id_area,descr_equipment) select id_area,'Equipment non specificata' from area_lavoro where id_impianto in (18,19,20,21);

18;20;Sannazzaro - ASU
19;20;Sannazzaro - Comuni
20;20;Sannazzaro - Impianto Azoto
21;20;Sannazzaro - Vaporizzazione O2 in ENI
update aziende set codice='CSE' WHERE ID_AZIENDA=28; select * from aziende where codice ='CAS';

select count(*) from pdl;
delete from pdl where id_azienda =20
*/