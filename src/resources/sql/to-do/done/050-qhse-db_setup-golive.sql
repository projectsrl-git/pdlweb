-- PROFILI 
DELETE FROM PROFILI where codice in ('RPO','QUA','QAS','SMN');
INSERT INTO PROFILI (CODICE,DESCRIZIONE) VALUES ('RPO','Report Owner');
INSERT INTO PROFILI (CODICE,DESCRIZIONE) VALUES ('QUA','QP');
INSERT INTO PROFILI (CODICE,DESCRIZIONE) VALUES ('QAS','QA');
INSERT INTO PROFILI (CODICE,DESCRIZIONE) VALUES ('SMN','Site Manager');


-- utenti 
INSERT INTO utenti(username,nome,cognome,email) 
select distinct email,initcap(substring(email,0,POSITION('.' in email))) as nome,initcap(substring(email,POSITION('.' in email)+1,POSITION('@' in email)-POSITION('.' in email)-1)) as cognome,email
from utenti_qhse where email not in (select username from utenti);


--- UTENTI_AZIENDE
INSERT INTO UTENTI_AZIENDE (ID_UTENTE,ID_AZIENDA) 
select distinct ut.id_utente, az.id_azienda
from utenti_qhse as t1
inner join utenti as ut on t1.email=ut.username
inner join aziende as az on t1.sito=az.codice
;



----- UTENTI_PROFILI 
INSERT INTO UTENTI_PROFILI (ID_UTENTE,ID_PROFILO) 
select distinct ut.id_utente, pr.id_profilo
from utenti_qhse as t1
inner join utenti as ut on t1.email=ut.username
inner join profili as pr on t1.profilo like '%'||pr.codice||'%'
;


--- UTENTI_LINGUE
DELETE FROM UTENTI_LINGUE;
ALTER SEQUENCE UTENTI_LINGUE_id_utenti_lingue_seq RESTART WITH 1;
INSERT INTO UTENTI_LINGUE (ID_UTENTE,id_lingue_iso,FL_DEFAULT) SELECT ID_UTENTE,(select id_lingue_iso from LINGUE_ISO where codice_iso='it') as id_lingue_iso,true FROM UTENTI
;


-- password
INSERT INTO PASSWORD (ID_UTENTE,PASSWORD,DT_SCADENZA,FL_VALIDA) SELECT ID_UTENTE,'32ca9fc1a0f5b633e3f4c8c1bbecde9bedb9573','2017/12/31',TRUE FROM UTENTI
where id_utente not in (select id_utente from password)
;
