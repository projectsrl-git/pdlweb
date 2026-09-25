update ALIMOD20 set stato = 'CLO' where id_modulo in (
	SELECT 
		MOD.id_modulo
	FROM ALIMOD20 AS MOD
	left outer join (select count(*) as conta, id_modulo from ALIMOD20_dettagli where stato_azione='CLO' or  stato_azione='CHK' group by id_modulo ) as clo
	on clo.id_modulo = mod.id_modulo
	left outer join (select count(*) as conta, id_modulo from ALIMOD20_dettagli group by id_modulo ) as tot
	on tot.id_modulo = mod.id_modulo
	where coalesce(clo.conta,0) = coalesce(tot.conta,0) 
)
;

update ALIMOD20 set stato = 'APP' where id_modulo in (
	SELECT 
		MOD.id_modulo
	FROM ALIMOD20 AS MOD
	left outer join (select count(*) as conta, id_modulo from ALIMOD20_dettagli where stato_azione='CLO' or  stato_azione='CHK' group by id_modulo ) as clo
	on clo.id_modulo = mod.id_modulo
	left outer join (select count(*) as conta, id_modulo from ALIMOD20_dettagli group by id_modulo ) as tot
	on tot.id_modulo = mod.id_modulo
	where coalesce(clo.conta,0) < coalesce(tot.conta,0) and mod.stato!='DRA'
)
;