select * from pdl where DT_PDL < (select to_char(current_date - interval '30 day','yyyy/mm/dd'))  AND STATO='OPE'

update pdl set stato='EXP' where DT_PDL < (select to_char(current_date - interval '30 day','yyyy/mm/dd'))  AND STATO='OPE'

select * from pdl where stato='EXP' 

update PDL set CAPO_TURNO='INTERNO' where STATO='CLO' AND CAPO_TURNO is null;