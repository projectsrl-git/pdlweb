select distinct 
	are.id_area
	,"Equipment"
	,case when "EIS"='*' then true else false end 
from (select distinct "Impianto","Area di Lavoro","Equipment","EIS"  from "Tab_008 Lista Equipment.PRISMR") as t1 
left outer join AREA_LAVORO as are on "Area di Lavoro"=descr_area and are.id_impianto in (select id_impianto from impianti where id_azienda in (select id_azienda from aziende where codice ='PRISMR') )
where "Equipment" is not null and are.id_area is not null
;

-- scarto queste equipment: 3
-- select * from "Tab_008 Lista Equipment.PRISMR" where "Equipment" is null or "Area di Lavoro" is null;


select distinct "Impianto","Area di Lavoro","Equipment","EIS"  from "Tab_008 Lista Equipment.PRISMR"
where "Equipment" not in (
select distinct 
	"Equipment"
from (select distinct "Impianto","Area di Lavoro","Equipment","EIS"  from "Tab_008 Lista Equipment.PRISMR") as t1 
left outer join AREA_LAVORO as are on "Area di Lavoro"=descr_area and are.id_impianto in (select id_impianto from impianti where id_azienda in (select id_azienda from aziende where codice ='PRISMR') )
where "Equipment" is not null and are.id_area is not null
)
;



SELECT 
DISTINCT
(SELECT ID_AZIENDA from aziende where  codice ='PRISMR') as ID_AZIENDA
,"Nome Azienda"
,"Riferimento Capo Cantiere" 
FROM "Tab_004 Ditte Terze.PRISMR"
where "Nome Azienda" is not null and "Riferimento Capo Cantiere"  is not null
;


select * from (
select "Impianto","Area di Lavoro","Equipment","EIS",count(*) as conta from "Tab_008 Lista Equipment.PRISMR" group by "Equipment","Area di Lavoro","Impianto","EIS"
) as t1 where conta>1
;




select count(*) from "Tab_001 Anagrafica_PdL.PRISMR";
select count(*) from temp_pdl;

select count(*) from "Tab_001 Anagrafica_PdL.PRISMR"
where "Equipment" is not null
and "Area_lavoro" is not null
and "Impianto" is not null;









INSERT INTO area_lavoro (id_impianto,descr_area,fl_disattivo,id_utente_ins) 
select distinct 
	imp.id_impianto 
	,"Area_lavoro" as descr_area
	,true
	,(select id_utente from utenti where username='assistenza')
from "Tab_001 Anagrafica_PdL.PRISMR" as t1
left outer join impianti as imp
on  coalesce(t1."Impianto",'ND') = imp.codice_impianto and imp.id_azienda in  (select id_azienda from aziende where codice ='PRISMR')
where "Area_lavoro" not in (select descr_area from area_lavoro where id_impianto in (select id_impianto from impianti where id_azienda in (select id_azienda from aziende where codice ='PRISMR')))
;	







select 
	distinct
	are.id_area
	--,equ.id_equipment
	,COALESCE(t1."Equipment",'NON DISPONIBILE') as descr_equipment
	,false as fl_eis
	,t1."Area_lavoro"
	,t1."Equipment"
from "Tab_001 Anagrafica_PdL.PRISMR" as t1
left outer join impianti as imp on imp.codice_impianto = COALESCE(t1."Impianto",'ND') and imp.id_azienda in (select id_azienda from aziende where codice ='PRISMR')
left outer join area_lavoro as are on COALESCE(t1."Area_lavoro",'NON DISPONIBILE') = are.descr_area and are.id_impianto = imp.id_impianto
left outer join equipment as equ on t1."Equipment" = equ.descr_equipment and equ.id_area = are.id_area
where equ.id_equipment is null
;





