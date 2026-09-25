DELETE FROM parametri WHERE DOMINIO ='UNI' OR (DOMINIO ='DOM' AND CODICE='UNI');
insert into parametri (dominio,codice,descrizione) values ('DOM','UNI','Unità di misura');
insert into parametri (dominio,codice,descrizione) values ('UNI','C01','Decibel');
insert into parametri (dominio,codice,descrizione) values ('UNI','C02','kg');
insert into parametri (dominio,codice,descrizione) values ('UNI','C03','Liter/Litri');
insert into parametri (dominio,codice,descrizione) values ('UNI','C04','m3');
insert into parametri (dominio,codice,descrizione) values ('UNI','C05','metric tons/tonnellate');
insert into parametri (dominio,codice,descrizione) values ('UNI','C06','mg/l');
insert into parametri (dominio,codice,descrizione) values ('UNI','C07','Pounds/Libbre');
insert into parametri (dominio,codice,descrizione) values ('UNI','C08','Short tons/');
insert into parametri (dominio,codice,descrizione) values ('UNI','C09','US Gallons/Galloni USA');



DELETE FROM parametri WHERE DOMINIO ='RCA' OR (DOMINIO ='DOM' AND CODICE='RCA');
insert into parametri (dominio,codice,descrizione) values ('DOM','RCA','Albero delle cause');
insert into parametri (dominio,codice,descrizione) values ('RCA','C01','Done/Fatta');
insert into parametri (dominio,codice,descrizione) values ('RCA','C02','Planned on/Pianificata al ->');
insert into parametri (dominio,codice,descrizione) values ('RCA','C03','No RCA, because/Albero delle cause non realizzato perché');



DELETE FROM parametri WHERE DOMINIO ='CPR' OR (DOMINIO ='DOM' AND CODICE='CPR');
insert into parametri (dominio,codice,descrizione) values ('DOM','CPR','Causa principale');
insert into parametri (dominio,codice,descrizione) values ('CPR','C01','Design/Progettazione (D)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C02','External Phenomenon/Fenomeni esterni (E)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C03','Human Factors/Fattori umani (H)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C04','Maintenance/Manutenzione (M)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C05','Management of Change/Gestione delle modifiche (C)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C06','Management Oversight/Inadempienza Management (MO)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C07','Manufacturing & Construction/Fabbricazione e costruzione (MC)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C08','Operating Procedures/Procedure operative (O)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C09','Risk Assessment & Hazard Analysis/Valutazione dei rschi e analisi dei pericoli (R)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C10','Safe Work Practices/Procedure di sicurezza sul lavoro (S)');
insert into parametri (dominio,codice,descrizione) values ('CPR','C11','Training & Qualification/Formazione e abilitazione (T)');




DELETE FROM parametri WHERE DOMINIO ='CSE' OR (DOMINIO ='DOM' AND CODICE='CSE');
insert into parametri (dominio,codice,descrizione) values ('DOM','CSE','Causa secondaria');
insert into parametri (dominio,codice,descrizione) values ('CSE','C01','(C1) Change made without a MOC/Modifica effettuata senza MOC');
insert into parametri (dominio,codice,descrizione) values ('CSE','C02','(C2) MOC did not receive proper reviews/MOC non sottoposta ad adeguate revisioni');
insert into parametri (dominio,codice,descrizione) values ('CSE','C03','(C3) MOC did not adequately address risks/MOC non adeguatamente indirizzata al rischio');
insert into parametri (dominio,codice,descrizione) values ('CSE','C04','(C4) Change not implemented as per approved MOC/Modifica non implementata secondo MOC approvata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C05','(D1) Design error/Errore progettazione');
insert into parametri (dominio,codice,descrizione) values ('CSE','C06','(D2) Inadequate EIS/EIS non adeguati');
insert into parametri (dominio,codice,descrizione) values ('CSE','C07','(D3) Integration of engineered systems not adequately considered/Integrazione dei sistemi progettati non adeguatamente considerata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C08','(D4) Design not properly reviewed/Progettazione non adeguatamente verificata ');
insert into parametri (dominio,codice,descrizione) values ('CSE','C09','(E1) Weather and natural events/Condizioni atmosferiche ed eventi naturali');
insert into parametri (dominio,codice,descrizione) values ('CSE','C10','(E2) Power failure or transient/Interruzione di corrente o transitorio');
insert into parametri (dominio,codice,descrizione) values ('CSE','C11','(E3) External fire, explosion or  toxic release/Incendio, esplosione o rilascio di sostanza tossica');
insert into parametri (dominio,codice,descrizione) values ('CSE','C12','(E4) Theft, tampering, sabotage or other vandalism/Furto, manossione, sabotaggio o altri atti vandalici');
insert into parametri (dominio,codice,descrizione) values ('CSE','C13','(E5) Other/Altri fenomeni esterni');
insert into parametri (dominio,codice,descrizione) values ('CSE','C14','(H1) Violation of regulatory requirement or procedure/Violazione di requisiti normativi o di procedure');
insert into parametri (dominio,codice,descrizione) values ('CSE','C15','(H2) Miscommunication/Cattiva comunicazione');
insert into parametri (dominio,codice,descrizione) values ('CSE','C16','(H3) Hazard not taken into account/Pericolo non considerato');
insert into parametri (dominio,codice,descrizione) values ('CSE','C17','(H4) Misuse-no use of available tool/Utilizzo scorretto-non utilizzo di attrezzi disponibili');
insert into parametri (dominio,codice,descrizione) values ('CSE','C18','(M1) Maintenance error/Errore di manutenzione');
insert into parametri (dominio,codice,descrizione) values ('CSE','C19','(M2) Defective or inadequate procedure/Procedura difettosa o inadeguata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C20','(M3) No procedure/Procedura inesistente');
insert into parametri (dominio,codice,descrizione) values ('CSE','C21','(M4)Current revision of procedure not used/Versione corrente della procedura non utilizzata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C22','(M5) Defective or inadequate maintenance plan/Piano di manutenzione difettoso o inadeguato');
insert into parametri (dominio,codice,descrizione) values ('CSE','C23','(M6) Inadequate maintenance instructions from suppliers/Istruzioni di manutenzione inadeguate da parte del fornitore');
insert into parametri (dominio,codice,descrizione) values ('CSE','C24','(MC1) Not built to design/Non costruito secondo progetto');
insert into parametri (dominio,codice,descrizione) values ('CSE','C25','(MC2) Deficient workmanship/Manodopera (esecuzione) carente');
insert into parametri (dominio,codice,descrizione) values ('CSE','C26','(MC3) Deficient commissioning/Installazione e avviamento carenti');
insert into parametri (dominio,codice,descrizione) values ('CSE','C27','(MC4) Defective material/Materiale difettoso');
insert into parametri (dominio,codice,descrizione) values ('CSE','C28','(MO1) Inadequate administrative control/Controllo amministrativo inadeguato');
insert into parametri (dominio,codice,descrizione) values ('CSE','C29','(MO2) Work organization or planning deficiency/Carenza dell''organizzazione del lavoro o della pianificazione');
insert into parametri (dominio,codice,descrizione) values ('CSE','C30','(MO3) Inadequate supervision/Supervisione inadeguata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C31','(MO4) Improper resource allocation/Distribuzione delle risorse inappropriata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C32','(MO5) Policy not adequately defined, disseminated or enforced/Politica non adeguatamente definita, diffusa o applicata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C33','(O1) Operating error/Errore operativo');
insert into parametri (dominio,codice,descrizione) values ('CSE','C34','(O2) Defective or inadequate procedure/Procedura difettosa o inadeguata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C35','(O3) No procedure/Procedura inesistente');
insert into parametri (dominio,codice,descrizione) values ('CSE','C36','(O4) Current revision of procedure not used/Versione corrente della procedura non utilizzata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C37','(O5) Procedure not compliant with regulations/Procedura non conforme alla normativa');
insert into parametri (dominio,codice,descrizione) values ('CSE','C38','(O6) Procedure not compliant with design operating constraints/Procedura non conforme ai vincoli operativi progettuali');
insert into parametri (dominio,codice,descrizione) values ('CSE','C39','(R1) Unidentified risk factor/Fattore di rischio non identificato');
insert into parametri (dominio,codice,descrizione) values ('CSE','C40','(R2) Risk not properly assessed/Valutazione del rischio non appropriata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C41','(R3) Risk mitigation inadequate/Mitigazione del rischio non adeguata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C42','(S1) Work Permit not used or inadequate/Permesso di lavoro non utilizzato o non adeguato');
insert into parametri (dominio,codice,descrizione) values ('CSE','C43','(S2) Required PPE not used or out of date/DPI richiesti non utilizzati o scaduti');
insert into parametri (dominio,codice,descrizione) values ('CSE','C44','(S3) Poor housekeeping/Ordine e pulizia carenti');
insert into parametri (dominio,codice,descrizione) values ('CSE','C45','(S4) Job Hazard analysis not performed or deficient/Analisi dei pericoli legati alla mansione non effettuata o carente');
insert into parametri (dominio,codice,descrizione) values ('CSE','C46','(S5) Inadequate work environment or tool not available/Ambiente di lavoro non adeguato o attrezzi non disponibili');
insert into parametri (dominio,codice,descrizione) values ('CSE','C47','(T1) No training provided/Formazione non erogata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C48','(T2) Required qualification not provided/Abilitazione richiesta non fornita');
insert into parametri (dominio,codice,descrizione) values ('CSE','C49','(T3) Inadequate training/Formazione inadeguata');
insert into parametri (dominio,codice,descrizione) values ('CSE','C50','(T4) Insufficient refresh training/Aggiornamento della formazione insufficiente');
