update PASSWORD set FL_VALIDA = false;
INSERT INTO PASSWORD (ID_UTENTE,PASSWORD,DT_SCADENZA,FL_VALIDA) SELECT ID_UTENTE,'32ca9fc1a0f5b633e3f4c8c1bbecde9bedb9573','9999/12/31',TRUE FROM UTENTI
;

update utenti set email='contributor.alirep@gmail.com' where id_utente in (select id_utente from utenti_profili where id_profilo in (select id_profilo from profili where codice='CON'));
update utenti set email='approver.alirep@gmail.com' where id_utente in (select id_utente from utenti_profili where id_profilo in (select id_profilo from profili where codice='APP'));

update DISTRIBUTION_LIST set lista_mail = 'contributor.alirep@gmail.com;approver.alirep@gmail.com' 
;
delete from email_log  
;