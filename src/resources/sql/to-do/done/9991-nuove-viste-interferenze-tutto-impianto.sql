-- view [V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO]
DROP VIEW IF exists V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO CASCADE;
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
	   WHEN FLG_PERSONALE_INTERNO THEN '* PERSONALE INTERNO *'
	   ELSE IMPRESA_TESTO
	END AS NOME_IMPRESA
	,string_agg(EMAIL, ',') as EMAIL	 
FROM PDL
LEFT OUTER JOIN UTENTI ON pdl.ID_UTENTE_INS=UTENTI.ID_UTENTE
left outer join area_lavoro as al on pdl.id_area=al.id_area
WHERE STATO='ACT'
and fl_tutto_impianto=true
group by id_azienda ,pdl.ID_IMPIANTO,pdl.ID_AREA,ID_PDL	,NR_PDL	,DESCRIZIONE_LAVORO	,dt_attivazione	,NOME_COGNOME_PREPOSTO_IMPRESA order by id_pdl
;


-- view [V_SIMULAZIONE_PDL_TUTTO_IMPIANTO]
DROP VIEW IF exists V_SIMULAZIONE_PDL_TUTTO_IMPIANTO CASCADE;
CREATE VIEW V_SIMULAZIONE_PDL_TUTTO_IMPIANTO AS 
select 
v1.ID_AZIENDA
    ,v1.ID_IMPIANTO
    ,al.ID_AREA
	,(al.ID_AREA::text||id_pdl::text)::int as id_pdl
	--,ID_PDL as id_pdl
	,NR_PDL
	,DESCRIZIONE_LAVORO
	,dt_attivazione
	,NOME_COGNOME_PREPOSTO_IMPRESA
	,NOME_IMPRESA
	,string_agg(EMAIL, ',') as EMAIL
 from area_lavoro as al 
inner join V_IMPRESE_PDL_ATTIVI_SU_TUTTO_IMPIANTO as v1 on al.id_impianto=v1.id_impianto
--where al.id_impianto=106 and al.fl_disattivo=false
WHERE  al.fl_disattivo=false and fl_tutto_impianto=false
group by v1.id_azienda,v1.id_impianto,al.ID_AREA,ID_PDL
	,NR_PDL
	,DESCRIZIONE_LAVORO
	,dt_attivazione
	,NOME_COGNOME_PREPOSTO_IMPRESA
	,NOME_IMPRESA
order by al.id_area;



-- 5 V_IMPRESE_PDL_ATTIVI  /// modificata, tolto order by e aggiunta union e cambiata order by e cambiato nome vista
-- view [V_IMPRESE_PDL_ATTIVI]
DROP VIEW IF exists V_IMPRESE_PDL_ATTIVI cascade;
CREATE VIEW V_IMPRESE_PDL_ATTIVI AS 


SELECT DISTINCT
	ID_AZIENDA
    ,ID_IMPIANTO
    ,ID_AREA
	,ID_PDL
	,NR_PDL
	,DESCRIZIONE_LAVORO
	,dt_attivazione
	,NOME_COGNOME_PREPOSTO_IMPRESA
	,CASE
	   WHEN FLG_PERSONALE_INTERNO THEN '* PERSONALE INTERNO *'
	   ELSE IMPRESA_TESTO
	END AS NOME_IMPRESA
	,string_agg(EMAIL, ',') as EMAIL	 
FROM PDL
LEFT OUTER JOIN UTENTI ON pdl.ID_UTENTE_INS=UTENTI.ID_UTENTE
WHERE STATO='ACT'
group by id_azienda ,ID_IMPIANTO,ID_AREA,ID_PDL	,NR_PDL	,DESCRIZIONE_LAVORO	,dt_attivazione	,NOME_COGNOME_PREPOSTO_IMPRESA 

UNION

SELECT V_SIMULAZIONE_PDL_TUTTO_IMPIANTO.* FROM V_SIMULAZIONE_PDL_TUTTO_IMPIANTO
group by id_azienda ,ID_IMPIANTO,ID_AREA,ID_PDL	,NR_PDL	,DESCRIZIONE_LAVORO	,dt_attivazione	,NOME_COGNOME_PREPOSTO_IMPRESA,NOME_IMPRESA,V_SIMULAZIONE_PDL_TUTTO_IMPIANTO.EMAIL 
order by id_area
;












--------------- rilancio le viste eliminate ---------------------------- 


-- view [V_INTERFERENZE]
DROP VIEW IF exists V_INTERFERENZE CASCADE;

CREATE VIEW V_INTERFERENZE AS 
SELECT 
	T2.*
	,azi.RAGSOC aS DESCR_SITO
	,IMP.DESCR_IMPIANTO
	,ARE.DESCR_AREA,fl_tutto_impianto
	,rect_x,rect_y,rect_h,rect_w,testo_x,testo_y,a_capo
	,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa
    ,NR_IMPRESE >1 AS FLG_POSSIBILE_INTERFERENZA
FROM (
	SELECT 
		ID_AZIENDA
		,ID_IMPIANTO
		,ID_AREA
		,string_agg(NOME_IMPRESA, ',') as lista_imprese	
		,string_agg(lista_id_pdl, ',' order by lista_id_pdl::int) as lista_id_pdl		
		,string_agg(lista_nr_pdl, ',') as lista_nr_pdl
		,string_agg(lista_dt_attivazione, ',') as lista_dt_attivazione	
		,string_agg(lista_descrizione_lavoro, ',') as lista_descrizione_lavoro
		,string_agg(lista_preposto_impresa, ',') as lista_preposto_impresa
		,string_agg(lista_email, ';') as lista_email
        ,COUNT(distinct nome_impresa) AS NR_IMPRESE
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
		FROM V_IMPRESE_PDL_ATTIVI 
		GROUP BY 
			ID_AZIENDA
			,ID_IMPIANTO
			,ID_AREA
			,NOME_IMPRESA,id_pdl
			order by id_pdl
	) AS T1
	GROUP BY
		ID_AZIENDA
		,ID_IMPIANTO
		,ID_AREA
) AS T2 
left outer join aziende as azi ON T2.ID_AZIENDA=azi.ID_AZIENDA 
left outer join impianti as IMP ON T2.ID_IMPIANTO=IMP.ID_IMPIANTO 
left outer join area_lavoro as ARE ON T2.ID_AREA= ARE.ID_AREA 
;



-- view [V_PDL]
DROP VIEW IF exists V_PDL;

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
FROM PDL 
left outer join aziende as azi ON PDL.ID_AZIENDA=azi.ID_AZIENDA 
left outer join impianti as IMP ON PDL.ID_IMPIANTO=IMP.ID_IMPIANTO and  PDL.ID_AZIENDA= IMP.ID_AZIENDA
left outer join area_lavoro as ARE ON PDL.ID_AREA= ARE.ID_AREA AND PDL.ID_IMPIANTO= ARE.ID_IMPIANTO
left outer join equipment as EQI ON PDL.ID_EQUIPMENT= EQI.ID_EQUIPMENT AND PDL.ID_AREA= EQI.ID_AREA
left outer join utenti as UATT on PDL.ID_UTENTE_ATTIVAZIONE = UATT.ID_UTENTE
left outer join parametri as SPL ON PDL.STATO= SPL.CODICE and SPL.DOMINIO='SPL'
left outer join ATTRIBUTI_AZIENDA as MDI on PDL.ID_AZIENDA=MDI.ID_AZIENDA and  MDI.codice_attributo= 'MODULO_INTERFERENZE'
left outer join V_INTERFERENZE as PI on PDL.ID_AREA=PI.ID_AREA AND PDL.STATO IN ('OPE','ACT') AND PI.FLG_POSSIBILE_INTERFERENZA
;







DROP VIEW IF exists V_LIMOD64;
DROP VIEW IF exists V_LIMOD64_DETTAGLIO;

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

CREATE VIEW V_LIMOD64_DETTAGLIO
AS
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
;






DROP VIEW IF exists V_LIMOD70;
DROP VIEW IF exists V_LIMOD70_DETTAGLIO;

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

CREATE VIEW V_LIMOD70_DETTAGLIO
AS
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
	,CASE WHEN FLG_PERSONALE_INTERNO THEN '* PERSONALE INTERNO *' ELSE case when pdl.impresa_testo is null then vspti.nome_impresa else pdl.impresa_testo end END as impresa_testo
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
left outer join area_lavoro on area_lavoro.id_area= li.id_area;
	
	
	
	
	


-- view [V_INTERFERENZE_PDL]
DROP VIEW IF exists V_INTERFERENZE_PDL;

CREATE VIEW V_INTERFERENZE_PDL AS 

--nuova query interferenze
SELECT distinct
	case when ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) )  
	then 'CAMBIATA-DA PROCESSARE (ROSSO)'  else 'OK PROCESSATA (ARANCIO)'
	end as colore2,
	PDL.* 
	,are.DESCR_AREA,are.fl_tutto_impianto
	,are.rect_x,are.rect_y,are.rect_h,are.rect_w,are.testo_x,are.testo_y,are.a_capo 
	,are.rect_x_stampa,are.rect_y_stampa,are.rect_h_stampa,are.rect_w_stampa,are.testo_x_stampa,are.testo_y_stampa,are.a_capo_stampa
	,coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) as flg_possibile_interferenza
	, case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '#3CB371' else 
		case when (pdl.id_pdl not in (select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo where stato='APP')) OR ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) ) then '#ff3f3f'  else '#ff9933'
	end end as colore
	, case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '' else 
	case when (pdl.id_pdl not in (select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo where stato='APP')) OR ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) ) then 'Da processare' else 'Processata' 
	end end as stato
FROM V_IMPRESE_PDL_ATTIVI AS PDL 
left outer JOIN V_INTERFERENZE AS PI ON PDL.ID_AREA = PI.ID_AREA AND PI.FLG_POSSIBILE_INTERFERENZA
left outer join area_lavoro as ARE ON PDL.ID_AREA= ARE.ID_AREA AND PDL.ID_IMPIANTO= ARE.ID_IMPIANTO
left outer join limod70_dettagli on limod70_dettagli.id_pdl=pdl.id_pdl
;






-- view [V_INTERFERENZE_TEMP]
DROP VIEW IF exists V_INTERFERENZE_TEMP CASCADE;

CREATE VIEW V_INTERFERENZE_TEMP AS 


SELECT 
	lista_id_pdl,T2.ID_IMPIANTO
	
	
    ,NR_IMPRESE >1 AS FLG_POSSIBILE_INTERFERENZA
FROM (
	SELECT 
		ID_AZIENDA
		,ID_IMPIANTO
		
		,string_agg(NOME_IMPRESA, ',') as lista_imprese	
		,string_agg(lista_id_pdl, ',' order by lista_id_pdl::int) as lista_id_pdl		
		,string_agg(lista_nr_pdl, ',') as lista_nr_pdl
		,string_agg(lista_dt_attivazione, ',') as lista_dt_attivazione	
		,string_agg(lista_descrizione_lavoro, ',') as lista_descrizione_lavoro
		,string_agg(lista_preposto_impresa, ',') as lista_preposto_impresa
        ,COUNT(distinct nome_impresa) AS NR_IMPRESE
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
		FROM V_IMPRESE_PDL_ATTIVI 
		GROUP BY 
			ID_AZIENDA
			,ID_IMPIANTO
			,ID_AREA
			,NOME_IMPRESA,id_pdl
			order by id_pdl
	) AS T1
	GROUP BY
		ID_AZIENDA
		,ID_IMPIANTO
		
) AS T2 
left outer join aziende as azi ON T2.ID_AZIENDA=azi.ID_AZIENDA 
left outer join impianti as IMP ON T2.ID_IMPIANTO=IMP.ID_IMPIANTO 
WHERE NR_IMPRESE>1
;






-- view [V_INTERFERENZE_PDL]
DROP VIEW IF exists V_SOLO_INTERFERENZE_PDL;

CREATE VIEW V_SOLO_INTERFERENZE_PDL AS 

SELECT 
	PDL.* 
	,PI.DESCR_AREA
	,rect_x,rect_y,rect_h,rect_w,testo_x,testo_y,a_capo 
	,rect_x_stampa,rect_y_stampa,rect_h_stampa,rect_w_stampa,testo_x_stampa,testo_y_stampa,a_capo_stampa
	, case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '#3CB371' else 
		case when (pdl.id_pdl not in (select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo where stato='APP')) OR ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) ) then '#ff3f3f'  else '#ff9933'
	end end as colore

,case when (coalesce(flg_possibile_interferenza,flg_possibile_interferenza,false) is false) then '' else 
	case when (pdl.id_pdl not in (select id_pdl from limod70_dettagli left outer join limod70 on limod70.id_modulo=limod70_dettagli.id_modulo where stato='APP')) OR ( lista_id_pdl <> (select string_agg(ID_PDL::varchar, ',') from limod70_dettagli left outer join limod70 on limod70_dettagli.id_modulo=limod70.id_modulo where id_area=pdl.id_area group by limod70.ts_ins order by limod70.ts_ins desc limit 1) ) then 'Da processare' else 'Processata' 
	end end as stato
FROM V_IMPRESE_PDL_ATTIVI AS PDL 
INNER JOIN V_INTERFERENZE AS PI ON PDL.ID_AREA = PI.ID_AREA AND PI.FLG_POSSIBILE_INTERFERENZA
;