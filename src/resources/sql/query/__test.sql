update PDL set STATO='CLO'
where DT_PDL < (select to_char(current_date - interval '30 day','yyyy/mm/dd')) and STATO='OPE' OR STATO='';

update PDL set CAPO_TURNO='INTERNO' where STATO='CLO' AND CAPO_TURNO is null;