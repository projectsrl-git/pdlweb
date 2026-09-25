-- view [V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO]
DROP VIEW IF exists V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO CASCADE;
DROP VIEW IF exists V_SIMULAZIONE_PDL_TUTTO_IMPIANTO CASCADE;
DROP VIEW IF exists V_IMPRESE_PDL_ATTIVI cascade;
DROP VIEW IF exists V_INTERFERENZE CASCADE;
DROP VIEW IF exists V_PDL;
DROP VIEW IF exists V_LIMOD64;
DROP VIEW IF exists V_LIMOD64_DETTAGLIO;
DROP VIEW IF exists V_LIMOD70;
DROP VIEW IF exists V_LIMOD70_DETTAGLIO;
DROP VIEW IF exists V_INTERFERENZE_PDL;
DROP VIEW IF exists V_INTERFERENZE_TEMP CASCADE;
DROP VIEW IF exists V_SOLO_INTERFERENZE_PDL;
drop view IF exists V_INTERFERENZE_PDL_FINAL;
drop view IF exists V_INTERFERENZE_PDL_ULTIMO;










CREATE VIEW V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO AS 

SELECT DISTINCT
	ID_AZIENDA
    ,pdl.ID_IMPIANTO
    ,pdl.ID_AREA
	,ID_PDL
	,NR_PDL
	,DESCRIZIONE_LAVORO
	,dt_attivazione
	,NOME_COGNOME_PREPOSTO_IMPRESA
	,CASE
	   WHEN FLG_PERSONALE_INTERNO THEN 'PERSONALE INTERNO'
	   ELSE IMPRESA_TESTO
	END AS NOME_IMPRESA
	,string_agg(EMAIL, ',') as EMAIL
	,case when ((risposta_verifica='' or risposta_verifica is null) and pdl.id_pdl not in (select id_pdl from dipendenze_pdl) and pdl.id_pdl not in (select id_dipendenza from dipendenze_pdl)) then false else true end as gestione_interferenza	 
	,cancellato
FROM PDL
LEFT OUTER JOIN UTENTI ON pdl.ID_UTENTE_INS=UTENTI.ID_UTENTE
left outer join area_lavoro as al on pdl.id_area=al.id_area
WHERE STATO='ACT'
and fl_tutto_impianto=true
group by id_azienda ,pdl.ID_IMPIANTO,pdl.ID_AREA,ID_PDL	,NR_PDL	,DESCRIZIONE_LAVORO	,dt_attivazione	,NOME_COGNOME_PREPOSTO_IMPRESA,cancellato order by id_pdl
;


-- view [V_SIMULAZIONE_PDL_TUTTO_IMPIANTO]
CREATE VIEW V_SIMULAZIONE_PDL_TUTTO_IMPIANTO AS 
select 
trim(RIGHT(id_pdl::char(20), 6))::int as id_pdl_corretto,
v1.ID_AZIENDA
    ,v1.ID_IMPIANTO
    ,al.ID_AREA
	,(al.ID_AREA::text||id_pdl::text)::bigint as id_pdl
	--,ID_PDL as id_pdl
	,NR_PDL
	,DESCRIZIONE_LAVORO
	,dt_attivazione
	,NOME_COGNOME_PREPOSTO_IMPRESA
	,NOME_IMPRESA
	,string_agg(EMAIL, ',') as EMAIL
	,true as fl_tutto_impianto
	,true as fl_tutto_impianto_simulato
	,gestione_interferenza
	,cancellato
 from area_lavoro as al 
inner join V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO as v1 on al.id_impianto=v1.id_impianto
WHERE  al.fl_disattivo=false and fl_tutto_impianto=false
AND AREA_SIMULABILE_TUTTO_IMPIANTO=TRUE AND (v1.ID_AREA =AL.ID_AREA_COLLEGATA_TUTTO_IMPIANTO OR AL.ID_AREA_COLLEGATA_TUTTO_IMPIANTO IS NULL)
group by v1.id_azienda,v1.id_impianto,al.ID_AREA,ID_PDL
	,NR_PDL
	,DESCRIZIONE_LAVORO
	,dt_attivazione
	,NOME_COGNOME_PREPOSTO_IMPRESA
	,NOME_IMPRESA,fl_tutto_impianto,id_pdl_corretto,gestione_interferenza,cancellato
order by al.id_area;



-- 5 V_IMPRESE_PDL_ATTIVI  /// modificata, tolto order by e aggiunta union e cambiata order by e cambiato nome vista
-- view [V_IMPRESE_PDL_ATTIVI]

CREATE VIEW V_IMPRESE_PDL_ATTIVI AS 



SELECT DISTINCT
trim(RIGHT(id_pdl::char(20), 6))::int as id_pdl_corretto,
	ID_AZIENDA
    ,pdl.ID_IMPIANTO
    ,pdl.ID_AREA
	,ID_PDL
	,NR_PDL
	,DESCRIZIONE_LAVORO
	,dt_attivazione
	,NOME_COGNOME_PREPOSTO_IMPRESA
	,CASE
	   WHEN FLG_PERSONALE_INTERNO THEN 'PERSONALE INTERNO'
	   ELSE IMPRESA_TESTO
	END AS NOME_IMPRESA
	,string_agg(EMAIL, ',') as EMAIL
,fl_tutto_impianto
,false as fl_tutto_impianto_simulato
,case when ((risposta_verifica='' or risposta_verifica is null) and pdl.id_pdl not in (select id_pdl from dipendenze_pdl) and pdl.id_pdl not in (select id_dipendenza from dipendenze_pdl)) then false else true end as gestione_interferenza
,cancellato
FROM PDL
LEFT OUTER JOIN UTENTI ON pdl.ID_UTENTE_INS=UTENTI.ID_UTENTE
left outer join area_lavoro as al on pdl.id_area=al.id_area
WHERE STATO='ACT'
group by id_azienda ,pdl.ID_IMPIANTO,pdl.ID_AREA,ID_PDL	,NR_PDL	,DESCRIZIONE_LAVORO	,dt_attivazione	,NOME_COGNOME_PREPOSTO_IMPRESA ,fl_tutto_impianto,id_pdl_corretto,cancellato


UNION

SELECT V_SIMULAZIONE_PDL_TUTTO_IMPIANTO.* FROM V_SIMULAZIONE_PDL_TUTTO_IMPIANTO
group by id_azienda ,ID_IMPIANTO,ID_AREA,ID_PDL	,NR_PDL	,DESCRIZIONE_LAVORO	,dt_attivazione	,NOME_COGNOME_PREPOSTO_IMPRESA,NOME_IMPRESA,V_SIMULAZIONE_PDL_TUTTO_IMPIANTO.EMAIL,fl_tutto_impianto,fl_tutto_impianto_simulato,id_pdl_corretto,gestione_interferenza,cancellato
order by id_area
;












--------------- rilancio le viste eliminate ---------------------------- 


-- view [V_INTERFERENZE]


CREATE VIEW V_INTERFERENZE AS 
SELECT 
	T2.*
	,azi.RAGSOC aS DESCR_SITO
	,IMP.DESCR_IMPIANTO
	,ARE.DESCR_AREA
	,rect_x,rect_y,rect_h,rect_w,testo_x,testo_y,a_capo
	,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa
    ,NR_IMPRESE >1 AS FLG_POSSIBILE_INTERFERENZA
FROM (
	SELECT 
		ID_AZIENDA
		,ID_IMPIANTO
		,ID_AREA
		,string_agg(NOME_IMPRESA, ',') as lista_imprese	
		,string_agg(lista_id_pdl, ',' order by lista_id_pdl) as lista_id_pdl		
		,string_agg(lista_nr_pdl, ',') as lista_nr_pdl
		,string_agg(lista_dt_attivazione, ',') as lista_dt_attivazione	
		,string_agg(lista_descrizione_lavoro, ',') as lista_descrizione_lavoro
		,string_agg(lista_preposto_impresa, ',') as lista_preposto_impresa
		,string_agg(lista_email, ';') as lista_email,gestione_interferenza
--,fl_tutto_impianto,fl_tutto_impianto_simulato
        ,COUNT(distinct nome_impresa) AS NR_IMPRESE
        ,cancellato
	FROM (
		SELECT  
			ID_AZIENDA
			,ID_IMPIANTO
			,ID_AREA
			,NOME_IMPRESA
			,string_agg(dt_attivazione, ',') as lista_dt_attivazione
			,string_agg(ID_PDL::varchar, ',') as lista_id_pdl		
			,string_agg(NR_PDL, ',') as lista_nr_pdl
			,string_agg(DESCRIZIONE_LAVORO, ',') as lista_descrizione_lavoro		
			,string_agg(NOME_COGNOME_PREPOSTO_IMPRESA, ',') as lista_preposto_impresa
			,string_agg(email, ';') as lista_email
			,gestione_interferenza
			,cancellato
			--,fl_tutto_impianto,fl_tutto_impianto_simulato
		FROM V_IMPRESE_PDL_ATTIVI 
		GROUP BY 
			ID_AZIENDA
			,ID_IMPIANTO
			,ID_AREA
			,NOME_IMPRESA,id_pdl,gestione_interferenza
			,cancellato
			--,fl_tutto_impianto,fl_tutto_impianto_simulato
			order by id_pdl
	) AS T1
	GROUP BY
		ID_AZIENDA
		,ID_IMPIANTO
		,ID_AREA,gestione_interferenza
		,cancellato
		--,fl_tutto_impianto,fl_tutto_impianto_simulato
) AS T2 
left outer join aziende as azi ON T2.ID_AZIENDA=azi.ID_AZIENDA 
left outer join impianti as IMP ON T2.ID_IMPIANTO=IMP.ID_IMPIANTO 
left outer join area_lavoro as ARE ON T2.ID_AREA= ARE.ID_AREA 
;



-- view [V_PDL]


CREATE VIEW V_PDL
AS
SELECT 
	PDL.*
	,azi.RAGSOC
	,SPL.DESCRIZIONE AS DESCR_STATO
	,IMP.DESCR_IMPIANTO
	,ARE.DESCR_AREA
	,EQI.DESCR_EQUIPMENT
	,DESCRIZIONE_DELLA_SOSPENSIONE as MOTIVO_SOSPENSIONE
	,to_char(TS_ATTIVAZIONE,'yyyy/mm/dd') as DATA_ULTIMA_ATTIVAZIONE
	,to_char(TS_ATTIVAZIONE,'HH24:MI') as ORA_UTLIMA_ATTIVAZIONE 
	,initcap(UATT.nome::text)||' '||initcap(UATT.cognome::text) as UTENTE_ULTIMA_ATTIVAZIONE
	,MDI.valore_attributo as modulo_interferenze,CASE WHEN FLG_TA THEN 'In fermata' ELSE 'Non in fermata' END as stato_fermata
	,PI.lista_id_pdl as ID_PDL_INTERFERENZA
	,PI.lista_nr_pdl AS ELENCO_PDL_INTERFERENZA
	,initcap(UTE.nome::text)||' '||initcap(UTE.cognome::text) as UTENTE_VERIFICA
	,to_char(TS_VERIFICA,'yyyy/mm/dd') as DATA_VERIFICA
	,to_char(TS_VERIFICA,'HH24:MI') as ORA_VERIFICA
	,initcap(UTEMOD.nome::text)||' '||initcap(UTEMOD.cognome::text) as UTENTE_MODIFICA
	,to_char(TS_MOD,'yyyy/mm/dd') as DATA_MODIFICA
	,to_char(TS_MOD,'HH24:MI') as ORA_MODIFICA
	,initcap(UTEINS.nome::text)||' '||initcap(UTEINS.cognome::text) as UTENTE_INSERIMENTO
	,to_char(PDL.TS_INS,'yyyy/mm/dd') as DATA_INSERIMENTO
	,to_char(PDL.TS_INS,'HH24:MI') as ORA_INSERIMENTO
	,case when RISPOSTA_VERIFICA='S' then 'Si' when RISPOSTA_VERIFICA='N' then 'No' else '' end as RISP_VERIFICA
FROM PDL 
left outer join aziende as azi ON PDL.ID_AZIENDA=azi.ID_AZIENDA 
left outer join impianti as IMP ON PDL.ID_IMPIANTO=IMP.ID_IMPIANTO and  PDL.ID_AZIENDA= IMP.ID_AZIENDA
left outer join area_lavoro as ARE ON PDL.ID_AREA= ARE.ID_AREA AND PDL.ID_IMPIANTO= ARE.ID_IMPIANTO
left outer join equipment as EQI ON PDL.ID_EQUIPMENT= EQI.ID_EQUIPMENT AND PDL.ID_AREA= EQI.ID_AREA
left outer join utenti as UATT on PDL.ID_UTENTE_ATTIVAZIONE = UATT.ID_UTENTE
left outer join utenti as ute on PDL.ID_UTENTE_VERIFICA = UTE.ID_UTENTE
left outer join utenti as utemod on PDL.ID_UTENTE_MOD = UTEMOD.ID_UTENTE
left outer join utenti as uteINS on PDL.ID_UTENTE_INS = uteINS.ID_UTENTE
left outer join parametri as SPL ON PDL.STATO= SPL.CODICE and SPL.DOMINIO='SPL'
left outer join ATTRIBUTI_AZIENDA as MDI on PDL.ID_AZIENDA=MDI.ID_AZIENDA and  MDI.codice_attributo= 'MODULO_INTERFERENZE'
left outer join V_INTERFERENZE as PI on PDL.ID_AREA=PI.ID_AREA AND PDL.STATO IN ('OPE','ACT') AND PI.FLG_POSSIBILE_INTERFERENZA
;










CREATE VIEW V_LIMOD64
AS
	SELECT 
		MOD.*
		,RAGSOC  AS DESCR_SITO
		,DESCR_IMPIANTO
		,RAGSOC AS DESCR_AZIENDA
		,CODICE_IMPIANTO
		,sta.DESCRIZIONE AS DESCR_STATO
		,descr_area
		, nome||' '||cognome as nome_cognome
		,to_char(mod.TS_INS,'HH24:MI:SS') as ORA
	FROM LIMOD64 AS MOD
	LEFT OUTER JOIN AZIENDE AS AZI
	ON MOD.ID_AZIENDA=AZI.ID_AZIENDA 
	LEFT OUTER JOIN IMPIANTI AS IMP 
	ON MOD.ID_IMPIANTO=IMP.ID_IMPIANTO AND  MOD.ID_AZIENDA= IMP.ID_AZIENDA
	LEFT OUTER JOIN PARAMETRI AS STA
	ON STA.CODICE = MOD.STATO AND STA.DOMINIO ='STA'
	left outer join area_lavoro as ARE 
	ON mod.ID_AREA= ARE.ID_AREA 
	left outer join UTENTI AS UT
	ON mod.id_coord_gest_int = UT.ID_UTENTE


;

-- view [V_LIMOD64_DETTAGLIO]

DROP VIEW IF exists V_LIMOD64_DETTAGLIO;

CREATE VIEW V_LIMOD64_DETTAGLIO
AS
select *,case when nome_cognome_preposto_impresa='' then nome_Cognome_delegato_lavori_al else nome_cognome_preposto_impresa end as nome_cognome_preposto_impresa_final from(
SELECT 
	DET.* 
	,area_lavoro.descr_area
	,case when pdl.id_impianto is null then vspti.id_impianto else pdl.id_impianto end as id_impianto
	,case when pdl.id_azienda is null then vspti.id_azienda else pdl.id_azienda end as id_azienda
	,id_equipment
	,case when pdl.nr_pdl is null then vspti.nr_pdl else pdl.nr_pdl end as nr_pdl
	,dt_pdl
	,nome_cognome_delegato_lavori_al
FROM limod64_dettagli AS DET
LEFT OUTER JOIN V_PDL AS pdl
ON DET.id_pdl=pdl.id_pdl
left outer join limod64 as li
	on li.id_modulo=det.id_modulo

left outer join V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO as vspti
on (li.ID_AREA::text||vspti.id_pdl)::bigint=det.id_pdl::bigint
left outer join area_lavoro on area_lavoro.id_area= li.id_area
) as tab
;








CREATE VIEW V_LIMOD70
AS
		SELECT distinct
		MOD.*
		,RAGSOC  AS DESCR_SITO
		,DESCR_IMPIANTO
		,RAGSOC AS DESCR_AZIENDA
		,CODICE_IMPIANTO
		,descr_area
		,sta.DESCRIZIONE AS DESCR_STATO
		,VAL.DESCRIZIONE AS descr_VALUTAZIONE
		,nome||' '||cognome as firma
		,CASE distanza_rispetto WHEN 'S' THEN 'Si' WHEN 'N' THEN 'No' ELSE '' END as distanzarispetto
	FROM LIMOD70 AS MOD
	LEFT OUTER JOIN AZIENDE AS AZI
	ON MOD.ID_AZIENDA=AZI.ID_AZIENDA 
	LEFT OUTER JOIN IMPIANTI AS IMP 
	ON MOD.ID_IMPIANTO=IMP.ID_IMPIANTO AND  MOD.ID_AZIENDA= IMP.ID_AZIENDA
	LEFT OUTER JOIN PARAMETRI AS STA
	ON STA.CODICE = MOD.STATO AND STA.DOMINIO ='STA'
	LEFT OUTER JOIN PARAMETRI AS VAL
ON VAL.CODICE=MOD.VALUTAZIONE AND VAL.DOMINIO = 'VAL'
left outer join area_lavoro as ARE 
	ON mod.ID_AREA= ARE.ID_AREA 
	LEFT OUTER JOIN V_UTENTI_PROFILI_AZIENDE AS VUPR
	ON mod.ID_UTENTE_INS = VUPR.ID_UTENTE
;

-- view [V_LIMOD70_DETTAGLIO]
DROP VIEW IF exists V_LIMOD70_DETTAGLIO;

CREATE VIEW V_LIMOD70_DETTAGLIO
AS
select *,case when nome_cognome_preposto_impresa='' then nome_Cognome_delegato_lavori_al else nome_cognome_preposto_impresa end as nome_cognome_preposto_impresa_final from(
SELECT distinct
	DET.* 
	,area_lavoro.descr_area
	,case when pdl.id_impianto is null then vspti.id_impianto else pdl.id_impianto end as id_impianto
	,case when pdl.id_azienda is null then vspti.id_azienda else pdl.id_azienda end as id_azienda
	,li.id_area
	,id_equipment
	,case when pdl.nr_pdl is null then vspti.nr_pdl else pdl.nr_pdl end as nr_pdl
	,dt_pdl
	,odl
	,CASE WHEN FLG_PERSONALE_INTERNO THEN 'PERSONALE INTERNO' ELSE case when pdl.impresa_testo is null then vspti.nome_impresa else pdl.impresa_testo end END as impresa_testo
	,case when pdl.nome_cognome_preposto_impresa is null then vspti.nome_cognome_preposto_impresa else pdl.nome_cognome_preposto_impresa end as nome_cognome_preposto_impresa
	,nome_cognome_delegato_lavori_al
	,NOME_COGNOME
	,case when pdl.descrizione_lavoro is null then vspti.descrizione_lavoro else pdl.descrizione_lavoro end as descrizione_lavoro
	,to_char(DET.TS_INS,'HH24:MI:SS') as ORA_INS
	, valutazione
	,VAL.DESCRIZIONE AS descr_VALUTAZIONE
	,distanza_rispetto
	,CASE distanza_rispetto WHEN 'S' THEN 'Si' WHEN 'N' THEN 'No' ELSE '' END as distanzarispetto
FROM limod70_dettagli AS DET
LEFT OUTER JOIN V_PDL AS pdl
ON DET.id_pdl=pdl.id_pdl
LEFT OUTER JOIN V_UTENTI_PROFILI_AZIENDE AS VUPR
	ON det.ID_UTENTE_INS = VUPR.ID_UTENTE
	left outer join limod70 as li
	on li.id_modulo=det.id_modulo
	LEFT OUTER JOIN PARAMETRI AS VAL
ON VAL.CODICE=li.VALUTAZIONE AND VAL.DOMINIO = 'VAL'
left outer join V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO as vspti
on (li.ID_AREA::text||vspti.id_pdl)::bigint=det.id_pdl::bigint
left outer join area_lavoro on area_lavoro.id_area= li.id_area
) as tab;
	
	
	
	
	


-- view [V_INTERFERENZE_PDL]


CREATE VIEW V_INTERFERENZE_PDL AS 

--nuova query interferenze


select id_azienda,id_impianto,id_area,id_pdl,nr_pdl,descrizione_lavoro,dt_attivazione,nome_cognome_preposto_impresa,nome_impresa,email,fl_tutto_impianto,descr_area,rect_x,rect_y,rect_h,rect_w,
testo_x,testo_y,a_capo,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa,flg_possibile_interferenza,stato,fl_tutto_impianto_simulato,ID_PDL_CORRETTO,
case when fl_tutto_impianto_simulato and colore='red' then '' else colore end as colore,cancellato from (
SELECT distinct
	PDL.* 
	,are.DESCR_AREA
	,are.rect_x,are.rect_y,are.rect_h,are.rect_w,are.testo_x,are.testo_y,are.a_capo 
	,are.rect_x_stampa,are.rect_y_stampa,are.rect_h_stampa,are.rect_w_stampa,are.testo_x_stampa,are.testo_y_stampa,are.a_capo_stampa
	,coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) as flg_possibile_interferenza
	, case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then 'mediumseagreen' else 
		case when (pdl.id_pdl not in (select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo where stato='APP')) OR ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) ) then 'red'  else 'darkorange'
	end end as colore
	, case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '' else 
	case when (pdl.id_pdl not in (select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo where stato='APP')) OR ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) ) then 'Da processare' else 'Processata' 
	end end as stato
FROM V_IMPRESE_PDL_ATTIVI AS PDL 
left outer JOIN V_INTERFERENZE AS PI ON PDL.ID_AREA = PI.ID_AREA AND PI.FLG_POSSIBILE_INTERFERENZA
left outer join area_lavoro as ARE ON PDL.ID_AREA= ARE.ID_AREA AND PDL.ID_IMPIANTO= ARE.ID_IMPIANTO
) as tab
;










-- view [V_INTERFERENZE_TEMP]


CREATE VIEW V_INTERFERENZE_TEMP AS 


SELECT 
	lista_id_pdl,T2.ID_IMPIANTO
	
	
    ,NR_IMPRESE >1 AS FLG_POSSIBILE_INTERFERENZA
    ,cancellato
FROM (
	SELECT 
		ID_AZIENDA
		,ID_IMPIANTO
		
		,string_agg(NOME_IMPRESA, ',') as lista_imprese	
		,string_agg(lista_id_pdl, ',' order by lista_id_pdl) as lista_id_pdl		
		,string_agg(lista_nr_pdl, ',') as lista_nr_pdl
		,string_agg(lista_dt_attivazione, ',') as lista_dt_attivazione	
		,string_agg(lista_descrizione_lavoro, ',') as lista_descrizione_lavoro
		,string_agg(lista_preposto_impresa, ',') as lista_preposto_impresa
        ,COUNT(distinct nome_impresa) AS NR_IMPRESE
        ,cancellato
	FROM (
		SELECT  
			ID_AZIENDA
			,ID_IMPIANTO
			
			,NOME_IMPRESA
			,string_agg(dt_attivazione, ',') as lista_dt_attivazione
			,string_agg(ID_PDL::varchar, ',') as lista_id_pdl		
			,string_agg(NR_PDL, ',') as lista_nr_pdl
			,string_agg(DESCRIZIONE_LAVORO, ',') as lista_descrizione_lavoro		
			,string_agg(NOME_COGNOME_PREPOSTO_IMPRESA, ',') as lista_preposto_impresa
			,cancellato
		FROM V_IMPRESE_PDL_ATTIVI 
		GROUP BY 
			ID_AZIENDA
			,ID_IMPIANTO
			,ID_AREA
			,NOME_IMPRESA,id_pdl
			,cancellato
			order by id_pdl
	) AS T1
	GROUP BY
		ID_AZIENDA
		,ID_IMPIANTO
		,cancellato
		
) AS T2 
left outer join aziende as azi ON T2.ID_AZIENDA=azi.ID_AZIENDA 
left outer join impianti as IMP ON T2.ID_IMPIANTO=IMP.ID_IMPIANTO 
WHERE NR_IMPRESE>1
;






-- view [V_SOLO_INTERFERENZE_PDL]


CREATE VIEW V_SOLO_INTERFERENZE_PDL AS 

SELECT distinct
	PDL.* 
	,PI.DESCR_AREA
	,rect_x,rect_y,rect_h,rect_w,testo_x,testo_y,a_capo 
	,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa
	, case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then 'mediumseagreen' else 
		case when (pdl.id_pdl_corretto not in (select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo where stato='APP')) OR ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) ) then 'red'  else 'darkorange'
	end end as colore
,case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '' else 
	case when (pdl.id_pdl_corretto not in 
	(select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo and pdl.id_area=limod70.id_area where stato='APP')) 
	then 'Da processare' else 'Processata' 
	end end as stato
FROM V_IMPRESE_PDL_ATTIVI AS PDL 
INNER JOIN V_INTERFERENZE AS PI ON PDL.ID_AREA = PI.ID_AREA AND PI.FLG_POSSIBILE_INTERFERENZA


;



-- view [V_INTERFERENZE_TEMP_NEW]
DROP VIEW IF exists V_INTERFERENZE_TEMP_NEW CASCADE;

CREATE VIEW V_INTERFERENZE_TEMP_NEW AS 

SELECT 
id_azienda,id_impianto,id_area,id_pdl,nr_pdl,descrizione_lavoro,dt_attivazione,nome_cognome_preposto_impresa,nome_impresa,email,fl_tutto_impianto,descr_area,rect_x,rect_y,rect_h,rect_w,testo_x,testo_y,a_capo,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa,stato,fl_tutto_impianto_simulato,colore,cancellato
 FROM v_solo_interferenze_pdl WHERE  id_pdl in (select id_pdl from v_interferenze_pdl where id_area in (select id_area from V_INTERFERENZE_PDL where fl_tutto_impianto_simulato=false group by id_area)) order by id_area,nr_pdl;

 
 
 
 
 
-- view [V_INTERFERENZE_TEMP_INTERMEDIA_NEW]
DROP VIEW IF exists V_INTERFERENZE_TEMP_INTERMEDIA_NEW CASCADE;

CREATE VIEW V_INTERFERENZE_TEMP_INTERMEDIA_NEW AS 
SELECT 
		ID_AZIENDA
		,ID_IMPIANTO
		,id_area,descr_area
		,string_agg(distinct NOME_IMPRESA, ',') as lista_imprese	
		,string_agg(lista_id_pdl, ',' order by lista_id_pdl) as lista_id_pdl		
		,string_agg(lista_nr_pdl, ',') as lista_nr_pdl
		,string_agg(lista_dt_attivazione, ',') as lista_dt_attivazione	
		,string_agg(lista_descrizione_lavoro, ',') as lista_descrizione_lavoro
		,string_agg(lista_preposto_impresa, ',') as lista_preposto_impresa
        --,COUNT(distinct nome_impresa) AS NR_IMPRESE
        ,cancellato
	FROM (
		SELECT  
			ID_AZIENDA
			,ID_IMPIANTO
			,id_area,descr_area
			,NOME_IMPRESA
			,string_agg(dt_attivazione, ',') as lista_dt_attivazione
			,string_agg(ID_PDL::varchar, ',') as lista_id_pdl		
			,string_agg(NR_PDL, ',') as lista_nr_pdl
			,string_agg(DESCRIZIONE_LAVORO, ',') as lista_descrizione_lavoro		
			,string_agg(NOME_COGNOME_PREPOSTO_IMPRESA, ',') as lista_preposto_impresa
			,fl_tutto_impianto_simulato
			,cancellato
		FROM v_interferenze_temp_new 
		GROUP BY 
			ID_AZIENDA
			,ID_IMPIANTO
			,ID_AREA,descr_area
			,NOME_IMPRESA,id_pdl,fl_tutto_impianto_simulato
			,cancellato
			order by id_pdl
	) AS T1
	GROUP BY
		ID_AZIENDA
		,ID_IMPIANTO
		,id_area,descr_area,cancellato;
 
 
 
 




 -- view [V_INTERFERENZE_TEMP_NEW_FULL]
DROP VIEW IF exists V_INTERFERENZE_TEMP_NEW_FULL CASCADE;

CREATE VIEW V_INTERFERENZE_TEMP_NEW_FULL AS 



select string_agg(lista_id_pdl, ',') as lista_id_pdl,string_agg(id_area::varchar, ',') as lista_id_area from(

SELECT 
		ID_AZIENDA
		,vitin.ID_IMPIANTO
		,vitin.id_area,vitin.descr_area
		,lista_imprese	
		,lista_id_pdl		
		,lista_nr_pdl
		,lista_dt_attivazione	
		,lista_descrizione_lavoro
		,lista_preposto_impresa
		,fl_tutto_impianto
		,cancellato
	FROM v_interferenze_temp_intermedia_new as vitin
left outer join area_lavoro ala on vitin.id_area =ala.id_area
-- where ala.fl_tutto_impianto=true or
--lista_imprese not in (
--select string_agg(distinct NOME_IMPRESA, ',') from v_interferenze_temp_new as vitn where id_area in (select id_area from area_lavoro as al where fl_tutto_impianto=true and vitn.id_area=al.id_area) 
--))as tab ;
) as tab;











-- view [V_INTERFERENZE_PDL_FINAL]


CREATE VIEW V_INTERFERENZE_PDL_FINAL AS 




SELECT 
CASE
  WHEN (stato IS NULL OR stato = '') then 'mediumseagreen'
  WHEN (stato='Processata') THEN 'darkorange'
    WHEN (stato='Da processare') then
  case when (gestione_interferenza=false and id_area = ANY( string_to_array((select lista_id_area from V_INTERFERENZE_TEMP_NEW_FULL), ',')::integer[] )) then 'red'
	when(gestione_interferenza=true) then 'darkorange' end
  ELSE ''
 END AS colore
 ,
descr_area,id_azienda,id_impianto,id_area,id_pdl,nr_pdl,descrizione_lavoro,dt_attivazione,nome_cognome_preposto_impresa,nome_impresa,email,fl_tutto_impianto,rect_x,rect_y,rect_h,rect_w,
testo_x,testo_y,a_capo,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa,flg_possibile_interferenza,stato,fl_tutto_impianto_simulato,ID_PDL_CORRETTO,gestione_interferenza
,cancellato
from(
SELECT distinct
	PDL.* 
	,are.DESCR_AREA
	,are.rect_x,are.rect_y,are.rect_h,are.rect_w,are.testo_x,are.testo_y,are.a_capo 
	,are.rect_x_stampa,are.rect_y_stampa,are.rect_h_stampa,are.rect_w_stampa,are.testo_x_stampa,are.testo_y_stampa,are.a_capo_stampa
	,coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) as flg_possibile_interferenza

	,case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '' else 
	case when (pdl.id_pdl_corretto not in 
	(select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo and limod70.id_area=pdl.id_area where stato='APP')) 
	then 'Da processare' else 'Processata' 
	end end as stato
FROM V_IMPRESE_PDL_ATTIVI AS PDL 
left outer JOIN V_INTERFERENZE AS PI ON PDL.ID_AREA = PI.ID_AREA AND PI.FLG_POSSIBILE_INTERFERENZA
left outer join area_lavoro as ARE ON PDL.ID_AREA= ARE.ID_AREA AND PDL.ID_IMPIANTO= ARE.ID_IMPIANTO
) as tab4
order by descr_area;





-- view [V_INTERFERENZE_PDL_FINAL_ELENCO]

DROP VIEW IF exists V_INTERFERENZE_PDL_FINAL_ELENCO CASCADE;

CREATE VIEW V_INTERFERENZE_PDL_FINAL_ELENCO AS 



SELECT 
CASE
  WHEN (stato IS NULL OR stato = '') then 'mediumseagreen'
  WHEN (stato='Processata') THEN 'darkorange'
    WHEN (stato='Da processare') then
  case when (gestione_interferenza=false and id_area = ANY( string_to_array((select lista_id_area from V_INTERFERENZE_TEMP_NEW_FULL), ',')::integer[] )) then 'red'
	when(gestione_interferenza=true and fl_tutto_impianto_simulato=false) then 'darkorange' end
  ELSE ''
 END AS colore
 ,
descr_area,id_azienda,id_impianto,id_area,id_pdl,nr_pdl,descrizione_lavoro,dt_attivazione,nome_cognome_preposto_impresa,nome_impresa,email,fl_tutto_impianto,rect_x,rect_y,rect_h,rect_w,
testo_x,testo_y,a_capo,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa,flg_possibile_interferenza,stato,fl_tutto_impianto_simulato,ID_PDL_CORRETTO,gestione_interferenza
,cancellato
from(
SELECT distinct
	PDL.* 
	,are.DESCR_AREA
	,are.rect_x,are.rect_y,are.rect_h,are.rect_w,are.testo_x,are.testo_y,are.a_capo 
	,are.rect_x_stampa,are.rect_y_stampa,are.rect_h_stampa,are.rect_w_stampa,are.testo_x_stampa,are.testo_y_stampa,are.a_capo_stampa
	,coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) as flg_possibile_interferenza
	,case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '' else 
	case when (pdl.id_pdl_corretto not in 
	(select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo and limod70.id_area=pdl.id_area where stato='APP')) 
	then 'Da processare' else 'Processata' 
	end end as stato
FROM V_IMPRESE_PDL_ATTIVI AS PDL 
left outer JOIN V_INTERFERENZE AS PI ON PDL.ID_AREA = PI.ID_AREA AND PI.FLG_POSSIBILE_INTERFERENZA
left outer join area_lavoro as ARE ON PDL.ID_AREA= ARE.ID_AREA AND PDL.ID_IMPIANTO= ARE.ID_IMPIANTO
) as tab4
order by descr_area;






-- view [V_COLORE_AREA]


DROP VIEW IF exists V_COLORE_AREA CASCADE;

CREATE VIEW V_COLORE_AREA AS
SELECT DISTINCT ON (id_area) colore,id_area FROM   V_INTERFERENZE_PDL_FINAL  ORDER  BY id_area,colore desc;






-- view [V_DIPENDENZE_PDL]
DROP VIEW IF exists V_DIPENDENZE_PDL CASCADE;

CREATE VIEW V_DIPENDENZE_PDL AS 

select dp.*,pd.impresa_testo,pd.nr_pdl,pd2.nr_pdl as nr_pdl_dipendenza,pd2.impresa_testo as impresa_testo_dipendenza from dipendenze_pdl as dp 
left outer join pdl as pd on dp.id_pdl=pd.id_pdl
LEFT OUTER JOIN PDL AS PD2 ON DP.ID_DIPENDENZA=PD2.ID_PDL;




-- view [V_PDL_VERIFICATI_PREVENTIVI]
DROP VIEW IF exists V_PDL_VERIFICATI_PREVENTIVI CASCADE;

CREATE VIEW V_PDL_VERIFICATI_PREVENTIVI AS 

select * from (

SELECT * 
,(SELECT string_agg(nr_pdl||' '||pa.descrizione,',') FROM PDL as vpdl left outer join parametri as pa on codice=vpdl.stato and dominio='SPL' WHERE vpdl.stato in ('OPE','ACT') and vpdl.id_area=pdl.id_area and vpdl.id_pdl<>pdl.id_pdl and vpdl.impresa_testo<>pdl.impresa_testo) AS lista_nr_pdl 
,(SELECT array_agg(id_pdl) FROM PDL as vpdl WHERE vpdl.stato in ('OPE','ACT') and vpdl.id_area=pdl.id_area and vpdl.id_pdl<>pdl.id_pdl and vpdl.impresa_testo<>pdl.impresa_testo) AS lista_id_pdl 
,(SELECT array_agg(ID_DIPENDENZA) FROM V_DIPENDENZE_PDL as dp WHERE pdl.id_pdl=dp.id_pdl) AS verificati_preventivi 
,(SELECT string_agg(dp.nr_pdl_dipendenza,', ') FROM V_DIPENDENZE_PDL as dp WHERE pdl.id_pdl=dp.id_pdl)::varchar AS nr_pdl_verificati_preventivi 
,case when (SELECT count(id_pdl) FROM PDL as vpdl WHERE vpdl.stato in ('OPE','ACT') and vpdl.id_area=pdl.id_area and vpdl.id_pdl<>pdl.id_pdl and vpdl.impresa_testo<>pdl.impresa_testo and vpdl.id_pdl not in (select id_pdl from limod70_dettagli))=0 then true else false end AS processato 
FROM V_PDL as pdl left outer join parametri on codice=stato and dominio='SPL' 
ORDER BY pdl.DT_PDL DESC, pdl.NR_PDL DESC

) as tab
where array_length(verificati_preventivi, 1) > 0;



-- view [V_INTERFERENZE_PDL_ULTIMO] non utilizzata

/*
CREATE VIEW V_INTERFERENZE_PDL_ULTIMO AS 

select 
colore,descr_area,id_azienda,id_impianto,id_area,id_pdl,nr_pdl,descrizione_lavoro,dt_attivazione,nome_cognome_preposto_impresa,nome_impresa,email,fl_tutto_impianto,rect_x,rect_y,rect_h,rect_w,
testo_x,testo_y,a_capo,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa,flg_possibile_interferenza,stato,fl_tutto_impianto_simulato,ID_PDL_CORRETTO
from V_INTERFERENZE_PDL_FINAL as vfinal
*/





-- vista creata per non rilanciare v_pdl per non eliminarle tutte
DROP VIEW IF exists V_PDL1;
CREATE VIEW V_PDL1
AS
SELECT 
	PDL.*
	,azi.RAGSOC
	,SPL.DESCRIZIONE AS DESCR_STATO
	,IMP.DESCR_IMPIANTO
	,ARE.DESCR_AREA
	,EQI.DESCR_EQUIPMENT
	,DESCRIZIONE_DELLA_SOSPENSIONE as MOTIVO_SOSPENSIONE
	,to_char(TS_ATTIVAZIONE,'yyyy/mm/dd') as DATA_ULTIMA_ATTIVAZIONE
	,to_char(TS_ATTIVAZIONE,'HH24:MI') as ORA_UTLIMA_ATTIVAZIONE 
	,initcap(UATT.nome::text)||' '||initcap(UATT.cognome::text) as UTENTE_ULTIMA_ATTIVAZIONE
	,initcap(UCHI.nome::text)||' '||initcap(UCHI.cognome::text) as UTENTE_CHIUSURA
	,MDI.valore_attributo as modulo_interferenze,CASE WHEN FLG_TA THEN 'In fermata' ELSE 'Non in fermata' END as stato_fermata
	,PI.lista_id_pdl as ID_PDL_INTERFERENZA
	,PI.lista_nr_pdl AS ELENCO_PDL_INTERFERENZA
	,initcap(UTE.nome::text)||' '||initcap(UTE.cognome::text) as UTENTE_VERIFICA
	,to_char(TS_VERIFICA,'yyyy/mm/dd') as DATA_VERIFICA
	,to_char(TS_VERIFICA,'HH24:MI') as ORA_VERIFICA
	,initcap(UTEMOD.nome::text)||' '||initcap(UTEMOD.cognome::text) as UTENTE_MODIFICA
	,to_char(TS_MOD,'yyyy/mm/dd') as DATA_MODIFICA
	,to_char(TS_MOD,'HH24:MI') as ORA_MODIFICA
	,initcap(UTEINS.nome::text)||' '||initcap(UTEINS.cognome::text) as UTENTE_INSERIMENTO
	,to_char(PDL.TS_INS,'yyyy/mm/dd') as DATA_INSERIMENTO
	,to_char(PDL.TS_INS,'HH24:MI') as ORA_INSERIMENTO
	
	,initcap(UCAN.nome::text)||' '||initcap(UCAN.cognome::text) as UTENTE_CANCELLAZIONE
	,to_char(PDL.TS_CANCELLAZIONE,'yyyy/mm/dd') as DATA_CANCELLAZIONE
	,to_char(PDL.TS_CANCELLAZIONE,'HH24:MI') as ORA_CANCELLAZIONE
	,initcap(UAPP.nome::text)||' '||initcap(UAPP.cognome::text) as UTENTE_PRIMA_ATTIVAZIONE
	,initcap(UARP.nome::text)||' '||initcap(UARP.cognome::text) as NOMINATIVO_RICHIEDENTE
	,initcap(RSN.nome::text)||' '||initcap(RSN.cognome::text) as SEZ2_RISCHI_SPECIFICI_NOMINATIVO
	
	,case when RISPOSTA_VERIFICA='S' then 'Si' when RISPOSTA_VERIFICA='N' then 'No' else '' end as RISP_VERIFICA
	,'' AS nr_pdl_verificati_preventivi 
FROM PDL 
left outer join aziende as azi ON PDL.ID_AZIENDA=azi.ID_AZIENDA 
left outer join impianti as IMP ON PDL.ID_IMPIANTO=IMP.ID_IMPIANTO and  PDL.ID_AZIENDA= IMP.ID_AZIENDA
left outer join area_lavoro as ARE ON PDL.ID_AREA= ARE.ID_AREA AND PDL.ID_IMPIANTO= ARE.ID_IMPIANTO
left outer join equipment as EQI ON PDL.ID_EQUIPMENT= EQI.ID_EQUIPMENT AND PDL.ID_AREA= EQI.ID_AREA
left outer join utenti as UATT on PDL.ID_UTENTE_ATTIVAZIONE = UATT.ID_UTENTE
left outer join utenti as UAPP on PDL.ID_UTENTE_PRIMA_ATTIVAZIONE = UAPP.ID_UTENTE
left outer join utenti as UCHI on PDL.ID_UTENTE_CHIUSURA = UCHI.ID_UTENTE
left outer join utenti as UCAN on PDL.ID_UTENTE_CANCELLAZIONE = UCAN.ID_UTENTE
left outer join utenti as ute on PDL.ID_UTENTE_VERIFICA = UTE.ID_UTENTE
left outer join utenti as utemod on PDL.ID_UTENTE_MOD = UTEMOD.ID_UTENTE
left outer join utenti as uteINS on PDL.ID_UTENTE_INS = uteINS.ID_UTENTE
left outer join utenti as UARP on PDL.ID_V9_NOMINATIVO_RICHIEDENTE = UARP.ID_UTENTE
left outer join utenti as RSN on PDL.ID_V9_SEZ2_RISCHI_SPECIFICI_NOMINATIVO = RSN.ID_UTENTE
left outer join parametri as SPL ON PDL.STATO= SPL.CODICE and SPL.DOMINIO='SPL'
left outer join ATTRIBUTI_AZIENDA as MDI on PDL.ID_AZIENDA=MDI.ID_AZIENDA and  MDI.codice_attributo= 'MODULO_INTERFERENZE'
left outer join V_INTERFERENZE as PI on PDL.ID_AREA=PI.ID_AREA AND PDL.STATO IN ('OPE','ACT') AND PI.FLG_POSSIBILE_INTERFERENZA
;



