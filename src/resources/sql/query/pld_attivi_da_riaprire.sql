select * from pdl where stato='ACT' and codice_turno_attivazione='001';

--update  pdl set stato='ACT' where id_pdl in ('154969','155112','155127');

update  pdl set stato='OPE' where stato='ACT' and codice_turno_attivazione='001';

select * from pdl where DT_PDL < (select to_char(current_date - interval '30 day','yyyy/mm/dd'))  AND STATO='OPE'

update pdl set stato='EXP' where DT_PDL < (select to_char(current_date - interval '30 day','yyyy/mm/dd'))  AND STATO='OPE'


