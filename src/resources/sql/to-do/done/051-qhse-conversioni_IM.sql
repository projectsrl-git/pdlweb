--select * from "Azioni_IM";
--3704
-- select distinct "NPda","Sito" FROM "Azioni_IM"
-- 1203
-- select distinct "Stato" FROM "Azioni_IM"

delete from utenti_profili where id_profilo in (select id_profilo from profili where codice ='SMN')
;

delete from profili where codice ='SMN'
;

delete from attributi_azienda where codice_attributo ='SITE_MANAGER'
;

insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','giuseppe.vairo@airliquide.com' from aziende where codice='BRE';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','giuseppe.vairo@airliquide.com' from aziende where codice='ROD';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','paolo.lazzari@airliquide.com' from aziende where codice='LIS';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','paolo.lazzari@airliquide.com' from aziende where codice='RMO';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','cristian.bonino@airliquide.com' from aziende where codice='PAD';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','cristian.bonino@airliquide.com' from aziende where codice='OSOIM';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','attilio.oliosi@airliquide.com' from aziende where codice='TRE';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','walter.spoldi@airliquide.com' from aziende where codice='BUS';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','walter.spoldi@airliquide.com' from aziende where codice='GRU';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','renato.cappa@airliquide.com' from aziende where codice='SNZ';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','sergio.mariani@airliquide.com' from aziende where codice='CHI';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','marcello.cala@airliquide.com' from aziende where codice='LUC';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','paolo.burgassi@airliquide.com' from aziende where codice='SAL';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','roberto.loi@airliquide.com' from aziende where codice='MOD';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','alberto.ugolini@airliquide.com' from aziende where codice='POR';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','michele.poletti@airliquide.com' from aziende where codice='RAV';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','renzo.zampini@airliquide.com' from aziende where codice='FER';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','david.imbriaco@airliquide.com' from aziende where codice='ASS';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','fabio.dibiase@airliquide.com' from aziende where codice='CSE';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','fabio.dibiase@airliquide.com' from aziende where codice='LAN';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','francesco.lanera@airliquide.com' from aziende where codice='OST';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','salvatore.ucciardi@airliquide.com' from aziende where codice='CRN';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','salvatore.ucciardi@airliquide.com' from aziende where codice='PRI';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','gianluca.avanzi@airliquide.com' from aziende where codice='CAS';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','mariano.rega@airliquide.com' from aziende where codice='LIM';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','michele.pari@airliquide.com' from aziende where codice='FEB';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','fabio.durante@airliquide.com' from aziende where codice='PDASU';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','fabio.durante@airliquide.com' from aziende where codice='OSO';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','fabio.durante@airliquide.com' from aziende where codice='CAR';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','davide.dimauro@airliquide.com' from aziende where codice='PRIASU';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','ermanno.salamone@airliquide.com' from aziende where codice='PRISMR';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','maurizio.cadeddu@airliquide.com' from aziende where codice='SAR';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','lia.maiolino@airliquide.com' from aziende where codice='CAT';
insert into attributi_azienda (id_azienda,codice_attributo,valore_attributo) select id_azienda, 'SITE_MANAGER','domenico.santoro@airliquide.com' from aziende where codice='SED';


select * from attributi_azienda as aa
inner join aziende as az on  aa.id_azienda=az.id_azienda
where aa.codice_attributo ='SITE_MANAGER'
;


delete from alimod20 where id_utente_ins = (select id_utente from utenti where username='assistenza');

insert into alimod20(
	codice_bl
	,id_azienda
	,nr_modulo
	,dt_modulo
	,stato
	,titolo	
	,id_utente_con
	,id_utente_app					
	,id_utente_ins 	
)
select 
	codice_bl
	,id_azienda
	,nr_modulo
	,to_char( tt."Data Ape",'yyyy/mm/dd') as dt_modulo
	,stato
	,'Storico PdA# '||tt."NPda"||' - '||to_char( tt."Data Ape",'dd/mm/yyyy')||' - '||tt."Sito" titolo	
	,coalesce((select id_utente from  utenti where lower(cognome) = lower(tt.contributor)), 
				(select id_utente from utenti as ut
					inner join attributi_azienda as aa on aa.codice_attributo ='SITE_MANAGER' and aa.valore_attributo=ut.username
					where aa.id_azienda = t1.id_azienda )
					) as id_utente_con
	,(select id_utente from utenti as ut
					inner join attributi_azienda as aa on aa.codice_attributo ='SITE_MANAGER' and aa.valore_attributo=ut.username
					where aa.id_azienda = t1.id_azienda 
					) as id_utente_app					
	,id_utente_ins 	
from (
select 
	distinct
	'BIM' codice_bl
	,az.id_azienda
	, old."NPda" nr_modulo
	, case when "NPda" in (select "NPda"  FROM "Azioni_IM" where "Stato" != 'Chiuso con Verifica' AND "Stato" !='Completato') then 'APP' else 'CLO' end stato
	, 0 id_utente_app
	, (select id_utente from utenti where username='assistenza') as id_utente_ins
	,az.ragsoc
	,az.codice
	,old."Sito"
from 
	"Azioni_IM" old
inner join aziende as az 
on 	case 
		when old."Sito"='IM-Sant Albino' then upper('S. Albino')
		when old."Sito"='IM-Milano' then upper('Milano - Sede')
		when old."Sito"='IM-Ferrera-H2' then upper('Sannazzaro H2')
		when old."Sito"='IM-Palermo' then upper('Carini')
		when old."Sito"='IM-Genova' then upper('Busalla (GE)')				
		else upper(old."Sito")
	end
	like '%'||(upper(az.ragsoc))||'%' and tipo_azienda like '%BIM%'
) as t1 
inner join (select *
				,case 
					when "Responsabile" ='utente' then 'guest' 
					else substring(lower("Responsabile"),1,strpos("Responsabile", '.')-1)					  
				end as contributor 
			from "Azioni_IM" where "NAzione" in (select min("NAzione"::integer) FROM "Azioni_IM" group by "NPda","Sito")
			) as tt 
on tt."NPda" = t1.nr_modulo and  tt."Sito" = t1."Sito"
;

delete from alimod20_dettagli where id_utente_ins = (select id_utente from utenti where username='assistenza')
;

insert into alimod20_dettagli (
  id_modulo
  ,nr_dettaglio
  ,codice_tipo_azione 
  ,descrizione 
  ,id_utente_res 
  ,dt_scadenza 
  ,dt_chiusura 
  ,stato_azione 
  ,codice_classificazione 
  ,note_avanzamento 
  ,id_utente_ins 
)  
select 
	(select id_modulo from alimod20 where nr_modulo = t1."NPda" and id_azienda=t1.id_azienda) as nr_modulo
	,"NAzione" nr_dettaglio
	,CASE WHEN upper("Categoria") = 'CORRETTIVA' then 'COR' else 'MIG' end codice_tipo_azione
	,"Titolo" descrizione	
	,coalesce((select id_utente from  utenti where lower(cognome) = lower(t1.contributor)), 
				(select id_utente from utenti as ut
					inner join attributi_azienda as aa on aa.codice_attributo ='SITE_MANAGER' and aa.valore_attributo=ut.username
					where aa.id_azienda = t1.id_azienda )
					) as id_utente_res
	,to_char( coalesce(t1."Data Scad",(select "Data Ape" from alimod20 where nr_modulo = t1."NPda" and id_azienda=t1.id_azienda)  ) ,'yyyy/mm/dd') as dt_scadenza
	,to_char( t1."Data Chiu",'yyyy/mm/dd') as dt_chiusura	
	,case when "Stato" = 'Completato' then 'CLO' when "Stato" = 'Chiuso con Verifica' then 'CHK' ELSE 'OPE' end stato_azione
	,'' codice_classificazione
	,'Categoria: '||"Categoria"||' - Stato: '||"Stato"||' - Responsabile: '||"Responsabile" as note_avanzamento
	,id_utente_ins 	
from (
select 
	az.id_azienda
	, (select id_utente from utenti where username='assistenza') as id_utente_ins
	,az.ragsoc
	,az.codice
	,case 
		when "Responsabile" ='utente' then 'guest' 
		else substring(lower("Responsabile"),1,strpos("Responsabile", '.')-1)					  
	end as contributor 
	,old.*
from 
	"Azioni_IM" old
inner join aziende as az 
on 	case 
		when old."Sito"='IM-Sant Albino' then upper('S. Albino')
		when old."Sito"='IM-Milano' then upper('Milano - Sede')
		when old."Sito"='IM-Ferrera-H2' then upper('Sannazzaro H2')
		when old."Sito"='IM-Palermo' then upper('Carini')
		when old."Sito"='IM-Genova' then upper('Busalla (GE)')				
		else upper(old."Sito")
	end
	like '%'||(upper(az.ragsoc))||'%' and tipo_azienda like '%BIM%'
) as t1
;





--	, (select to_char("Data Ape",'yyyy/mm/dd') from "Azioni_IM" where "NAzione" = (select min("NAzione"::integer) FROM "Azioni_IM" where "NPda" = old."NPda" group by "NPda")) dt_modulo

--	,lower((select "Responsabile" from "Azioni_IM" where "NAzione" = (select min("NAzione"::integer) FROM "Azioni_IM" where "NPda" = old."NPda" group by "NPda"))::varchar) as contributor

--inner join (select * from "Azioni_IM" where "NAzione" = (select min("NAzione"::integer) FROM "Azioni_IM" where "NPda" = old."NPda" group by "NPda") as tt 
--on tt."NPda"=old."NPda" and 	