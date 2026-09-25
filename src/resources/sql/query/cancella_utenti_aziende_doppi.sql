
delete from utenti_aziende where id_utente_azienda in (
select id_utente_azienda from 
utenti_aziende
LEFT OUTER JOIN (
   SELECT MIN(id_utente_azienda) as RowId, id_utente,ID_AZIENDA
   FROM utenti_aziende 
   GROUP BY id_utente,ID_AZIENDA
) as KeepRows ON
   utenti_aziende.id_utente_azienda = KeepRows.RowId
WHERE
   KeepRows.RowId IS NULL)