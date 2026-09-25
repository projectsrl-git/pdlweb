--select * from "Azioni_LI" where "Sito" is not null;
--2933
-- select distinct "NPda","Sito" FROM "Azioni_LI"
-- 691

delete from alimod20 where titolo like 'Storico%' and codice_bl='BLI';

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
	'BLI' codice_bl
	,az.id_azienda
	, old."NPda" nr_modulo
	, case when "NPda" in (select "NPda"  FROM "Azioni_IM" where "Stato" != 'Chiuso con Verifica' AND "Stato" !='Completato') then 'APP' else 'CLO' end stato
	, 0 id_utente_app
	, (select id_utente from utenti where username='assistenza') as id_utente_ins
	,az.ragsoc
	,az.codice
	,old."Sito"
from 
	"Azioni_LI" old
inner join aziende as az 
on 	case 
		when old."Sito"='LI-Castelnuovo-ASU' then upper('Castelnuovo - ASU')
		when old."Sito"='LI-Priolo-SMR' then upper('Priolo - SMR.IA')
		when old."Sito"='LI-Padova' then upper('Padova - ASU')
		when old."Sito"='LI-Priolo-ASU' then upper('Priolo - ASU')
		when old."Sito"='LI-Priolo-IA' then upper('Priolo - SMR.IA')
		when old."Sito"='LI-Ferrera' then upper('Ferrera Erbognone - ASU')
		when old."Sito"='LI-Cargnacco-VSA' then upper('Cargnacco - VSA')
		when old."Sito"='LI-Limito' then upper('Limito - ASU')	
		when old."Sito"='LI-Milano' then upper('Milano - Sede')	
		when old."Sito"='LI-Osoppo-VSA' then upper('Osoppo - VSA')	
		when old."Sito"='LI-Sarroch' then upper('Sarroch - ASU')	
		else upper(old."Sito")
	end
	like '%'||(upper(az.ragsoc))||'%' and tipo_azienda like '%BLI%'
) as t1 
inner join (select *
				,case 
					when "Responsabile" ='utente' then 'guest' 
					else substring(lower("Responsabile"),1,strpos("Responsabile", '.')-1)					  
				end as contributor 
			from "Azioni_LI" where "NAzione" in (select min("NAzione"::integer) FROM "Azioni_LI" group by "NPda","Sito")
			) as tt 
on tt."NPda" = t1.nr_modulo and  tt."Sito" = t1."Sito"
where t1.id_azienda is not null
;


delete from alimod20_dettagli where id_utente_ins = (select id_utente from utenti where username='assistenza') and id_modulo in (select id_modulo from alimod20  where codice_bl='BLI');
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
	(select id_modulo from alimod20 where nr_modulo = t1."NPda" and id_azienda=t1.id_azienda and codice_bl='BLI') as nr_modulo
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
	"Azioni_LI" old
inner join aziende as az 
on 	case 
		when old."Sito"='LI-Castelnuovo-ASU' then upper('Castelnuovo - ASU')
		when old."Sito"='LI-Priolo-SMR' then upper('Priolo - SMR.IA')
		when old."Sito"='LI-Padova' then upper('Padova - ASU')
		when old."Sito"='LI-Priolo-ASU' then upper('Priolo - ASU')
		when old."Sito"='LI-Priolo-IA' then upper('Priolo - SMR.IA')
		when old."Sito"='LI-Ferrera' then upper('Ferrera Erbognone - ASU')
		when old."Sito"='LI-Cargnacco-VSA' then upper('Cargnacco - VSA')
		when old."Sito"='LI-Limito' then upper('Limito - ASU')	
		when old."Sito"='LI-Milano' then upper('Milano - Sede')	
		when old."Sito"='LI-Osoppo-VSA' then upper('Osoppo - VSA')	
		when old."Sito"='LI-Sarroch' then upper('Sarroch - ASU')	
		else upper(old."Sito")
	end
	like '%'||(upper(az.ragsoc))||'%' and tipo_azienda like '%BLI%'
) as t1
;


--	, (select to_char("Data Ape",'yyyy/mm/dd') from "Azioni_LI" where "NAzione" = (select min("NAzione"::integer) FROM "Azioni_LI" where "NPda" = old."NPda" group by "NPda")) dt_modulo

--	,lower((select "Responsabile" from "Azioni_LI" where "NAzione" = (select min("NAzione"::integer) FROM "Azioni_LI" where "NPda" = old."NPda" group by "NPda"))::varchar) as contributor

--inner join (select * from "Azioni_LI" where "NAzione" = (select min("NAzione"::integer) FROM "Azioni_LI" where "NPda" = old."NPda" group by "NPda") as tt 
--on tt."NPda"=old."NPda" and 	