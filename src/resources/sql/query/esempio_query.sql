SELECT 	id_utente::"varchar", 
	username, 
	cognome, 
	nome, 
	email, 
	to_char(ts_ins,'yyyy/mm/dd') as date_ins, 
	to_char(ts_ins,'HH24:MI:SS') as time_ins, 
	to_timestamp(to_char(ts_ins,'yyyy/mm/dd'), 'yyyy/mm/dd'),
	to_timestamp(to_char(ts_ins,'yyyy/mm/dd')||to_char(ts_ins,'HH24:MI'), 'yyyy/mm/ddHH24:MI'),
	regexp_replace(initcap(nome::text), '([a-z ])+'::text, '.'::text, 'g'::text) || initcap(cognome::text) AS abbreviato,
	ts_login, 
	id_utente_ins::"varchar"
  FROM utenti;


 

select * from (
select "Sito",substring("Responsabile",0,POSITION('.' in "Responsabile")) as cognome, substring("Responsabile",POSITION('.' in "Responsabile")+1,1) as nome, * 
from "Azioni_IM" 
) as t1
where cognome in (select cognome from utenti)  