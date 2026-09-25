-- Table: DISTRIBUTION_LIST

DROP TABLE IF EXISTS DISTRIBUTION_LIST CASCADE;

CREATE TABLE DISTRIBUTION_LIST
(
	id_distribution_list serial not null,
	modulo character varying(30) not null,
	id_azienda integer not null,
	lista_mail text not null
);

INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;massimo.musin@airliquide.com;francesco.iannetti@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;massimo.musin@airliquide.com;francesco.iannetti@airliquide.com' FROM AZIENDE WHERE CODICE='OSO';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;giuseppe.coviello@airliquide.com;giuseppe.casati@airliquide.com' FROM AZIENDE WHERE CODICE='PDASU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'gianluca.avanzi@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;nadia.mazzarella@airliquide.com;sara.voltolina@airliquide.com' FROM AZIENDE WHERE CODICE='CAS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'mariano.rega@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;miriam.capone@airliqide.com;giuseppe.colpani@airliquide.com' FROM AZIENDE WHERE CODICE='LIM';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'walter.camillo@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;miriam.capone@airliqide.com;giuseppe.colpani@airliquide.com' FROM AZIENDE WHERE CODICE='CET';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'michele.pari@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;maurizio.licitra@airliquide.com;maurizio.licitra@airliquide.com' FROM AZIENDE WHERE CODICE='FEB';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'maurizio.cadeddu@airliquide.com;giampaolo.pelliccia@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;valentina.mulas@airliquide.com;gianfranco.dessi@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'davide.dimauro@airliquide.com;giampaolo.pelliccia@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;giovanni.avolio@airliquide.com;giovanni.avolio@airliquide.com' FROM AZIENDE WHERE CODICE='PRISU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'ermanno.salamone@airliquide.com;giampaolo.pelliccia@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;carmelo.manitta@airliquide.com;gianluca.bosinco@airliquide.com' FROM AZIENDE WHERE CODICE='PRISMR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'giuseppe.vairo@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='BRE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'giuseppe.vairo@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='ROD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'paolo.lazzari@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='LIS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'paolo.lazzari@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='RMO';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'cristian.bonino@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='PAD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'cristian.bonino@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='UDI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'attilio.oliosi@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='TRE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'walter.spoldi@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='BUS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'walter.spoldi@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='GRU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'renato.cappa@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='SNZ';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'sergio.mariani@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.mariani@airliquide.com;davide.mariani@airliquide.com' FROM AZIENDE WHERE CODICE='CHI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'marcello.cala@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;eleonora.nardi@airliquide.com' FROM AZIENDE WHERE CODICE='LUC';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'paolo.burgassi@airliquide.com;paride.fiuzzi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;eleonora.nardi@airliquide.com' FROM AZIENDE WHERE CODICE='SAL';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'roberto.loi@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;sara.pignagnoli@airliquide.com;sara.pignagnoli@airliquide.com' FROM AZIENDE WHERE CODICE='MOD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'alberto.ugolini@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;raffaele.degirolamo@airliquide.com;raffaele.degirolamo@airliquide.com' FROM AZIENDE WHERE CODICE='POR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'michele.poletti@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alberto.deruvo@airliquide.com;alberto.deruvo@airliquide.com' FROM AZIENDE WHERE CODICE='RAV';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'renzo.zampini@airliquide.com;paride.fiuzzi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alberto.deruvo@airliquide.com;alberto.deruvo@airliquide.com' FROM AZIENDE WHERE CODICE='FER';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'david.imbriaco@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;ferruccio.mazzoleni@airliquide.com;ferruccio.mazzoleni@airliquide.com' FROM AZIENDE WHERE CODICE='ASS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'fabio.dibiase@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='CSE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'fabio.dibiase@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='LAN';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'francesco.lanera@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='OST';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'salvatore.ucciardi@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alfonso.mattia@airliquide.com;alfonso.mattia@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'salvatore.ucciardi@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alfonso.mattia@airliquide.com;alfonso.mattia@airliquide.com' FROM AZIENDE WHERE CODICE='PRI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'alessandra.teruzzi@airliquide.com;ada.petringa@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.larosa@airliquide.com;fabio.larosa@airliquide.com' FROM AZIENDE WHERE CODICE='LIS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD50' AS MODULO, ID_AZIENDA,'lia.maiolino@airliquide.com;ada.petringa@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mario.locastro@airliquide.com;mario.locastro@airliquide.com' FROM AZIENDE WHERE CODICE='CAT';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;massimo.musin@airliquide.com;francesco.iannetti@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;massimo.musin@airliquide.com;francesco.iannetti@airliquide.com' FROM AZIENDE WHERE CODICE='OSO';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;giuseppe.coviello@airliquide.com;giuseppe.casati@airliquide.com' FROM AZIENDE WHERE CODICE='PDASU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'gianluca.avanzi@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;nadia.mazzarella@airliquide.com;sara.voltolina@airliquide.com' FROM AZIENDE WHERE CODICE='CAS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'mariano.rega@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;miriam.capone@airliqide.com;giuseppe.colpani@airliquide.com' FROM AZIENDE WHERE CODICE='LIM';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'walter.camillo@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;miriam.capone@airliqide.com;giuseppe.colpani@airliquide.com' FROM AZIENDE WHERE CODICE='CET';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'michele.pari@airliquide.com;paolo.boccalero@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;maurizio.licitra@airliquide.com;maurizio.licitra@airliquide.com' FROM AZIENDE WHERE CODICE='FEB';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'maurizio.cadeddu@airliquide.com;giampaolo.pelliccia@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;valentina.mulas@airliquide.com;gianfranco.dessi@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'davide.dimauro@airliquide.com;giampaolo.pelliccia@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;giovanni.avolio@airliquide.com;giovanni.avolio@airliquide.com' FROM AZIENDE WHERE CODICE='PRISU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'ermanno.salamone@airliquide.com;giampaolo.pelliccia@airliquide.com;domenico.santoro@airliquide.com;thomas.perotti@airliquide.com;carmelo.manitta@airliquide.com;gianluca.bosinco@airliquide.com' FROM AZIENDE WHERE CODICE='PRISMR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'giuseppe.vairo@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='BRE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'giuseppe.vairo@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='ROD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'paolo.lazzari@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='LIS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'paolo.lazzari@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='RMO';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'cristian.bonino@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='PAD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'cristian.bonino@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='UDI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'attilio.oliosi@airliquide.com;fernando.bonini@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='TRE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'walter.spoldi@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='BUS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'walter.spoldi@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='GRU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'renato.cappa@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='SNZ';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'sergio.mariani@airliquide.com;walter.spoldi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;davide.mariani@airliquide.com;davide.mariani@airliquide.com' FROM AZIENDE WHERE CODICE='CHI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'marcello.cala@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;eleonora.nardi@airliquide.com' FROM AZIENDE WHERE CODICE='LUC';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'paolo.burgassi@airliquide.com;paride.fiuzzi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;eleonora.nardi@airliquide.com' FROM AZIENDE WHERE CODICE='SAL';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'roberto.loi@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;sara.pignagnoli@airliquide.com;sara.pignagnoli@airliquide.com' FROM AZIENDE WHERE CODICE='MOD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'alberto.ugolini@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;raffaele.degirolamo@airliquide.com;raffaele.degirolamo@airliquide.com' FROM AZIENDE WHERE CODICE='POR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'michele.poletti@airliquide.com;roberto.loi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alberto.deruvo@airliquide.com;alberto.deruvo@airliquide.com' FROM AZIENDE WHERE CODICE='RAV';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'renzo.zampini@airliquide.com;paride.fiuzzi@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alberto.deruvo@airliquide.com;alberto.deruvo@airliquide.com' FROM AZIENDE WHERE CODICE='FER';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'david.imbriaco@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;ferruccio.mazzoleni@airliquide.com;ferruccio.mazzoleni@airliquide.com' FROM AZIENDE WHERE CODICE='ASS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'fabio.dibiase@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='CSE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'fabio.dibiase@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='LAN';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'francesco.lanera@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='OST';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'salvatore.ucciardi@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alfonso.mattia@airliquide.com;alfonso.mattia@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'salvatore.ucciardi@airliquide.com;david.imbriaco@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;alfonso.mattia@airliquide.com;alfonso.mattia@airliquide.com' FROM AZIENDE WHERE CODICE='PRI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'alessandra.teruzzi@airliquide.com;ada.petringa@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;fabio.larosa@airliquide.com;fabio.larosa@airliquide.com' FROM AZIENDE WHERE CODICE='LIS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD67' AS MODULO, ID_AZIENDA,'lia.maiolino@airliquide.com;ada.petringa@airliquide.com;domenico.santoro@airliquide.com;domenico.santoro@airliquide.com;mario.locastro@airliquide.com;mario.locastro@airliquide.com' FROM AZIENDE WHERE CODICE='CAT';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;massimo.musin@airliquide.com;francesco.iannetti@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;massimo.musin@airliquide.com;francesco.iannetti@airliquide.com' FROM AZIENDE WHERE CODICE='OSO';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'fabio.durante@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;giuseppe.coviello@airliquide.com;giuseppe.casati@airliquide.com' FROM AZIENDE WHERE CODICE='PDASU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'gianluca.avanzi@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;nadia.mazzarella@airliquide.com;sara.voltolina@airliquide.com' FROM AZIENDE WHERE CODICE='CAS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'mariano.rega@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;miriam.capone@airliqide.com;giuseppe.colpani@airliquide.com' FROM AZIENDE WHERE CODICE='LIM';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'walter.camillo@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;miriam.capone@airliqide.com;giuseppe.colpani@airliquide.com' FROM AZIENDE WHERE CODICE='CET';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'michele.pari@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;maurizio.licitra@airliquide.com;maurizio.licitra@airliquide.com' FROM AZIENDE WHERE CODICE='FEB';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'maurizio.cadeddu@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;valentina.mulas@airliquide.com;gianfranco.dessi@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'davide.dimauro@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;giovanni.avolio@airliquide.com;giovanni.avolio@airliquide.com' FROM AZIENDE WHERE CODICE='PRISU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'ermanno.salamone@airliquide.com;carmelo.ruiz@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;giampaolo.pelliccia@airliquide.com;gianpiero.reale@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;paolo.boccalero@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;carmelo.manitta@airliquide.com;gianluca.bosinco@airliquide.com' FROM AZIENDE WHERE CODICE='PRISMR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'giuseppe.vairo@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='BRE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'giuseppe.vairo@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='ROD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'paolo.lazzari@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='LIS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'paolo.lazzari@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;rossella.miraglia@airliquide.com;rossella.miraglia@airliquide.com' FROM AZIENDE WHERE CODICE='RMO';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'cristian.bonino@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='PAD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'cristian.bonino@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='UDI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'attilio.oliosi@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;fabio.sanna@airliquide.com;fabio.sanna@airliquide.com' FROM AZIENDE WHERE CODICE='TRE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'walter.spoldi@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='BUS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'walter.spoldi@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='GRU';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'renato.cappa@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;davide.siliprandi@airliquide.com;davide.siliprandi@airliquide.com' FROM AZIENDE WHERE CODICE='SNZ';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'sergio.mariani@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;davide.mariani@airliquide.com;davide.mariani@airliquide.com' FROM AZIENDE WHERE CODICE='CHI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'marcello.cala@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;eleonora.nardi@airliquide.com;eleonora.nardi@airliquide.com' FROM AZIENDE WHERE CODICE='LUC';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'paolo.burgassi@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;eleonora.nardi@airliquide.com;eleonora.nardi@airliquide.com' FROM AZIENDE WHERE CODICE='SAL';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'roberto.loi@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;sara.pignagnoli@airliquide.com;sara.pignagnoli@airliquide.com' FROM AZIENDE WHERE CODICE='MOD';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'alberto.ugolini@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;raffaele.degirolamo@airliquide.com;raffaele.degirolamo@airliquide.com' FROM AZIENDE WHERE CODICE='POR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'michele.poletti@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;alberto.deruvo@airliquide.com;alberto.deruvo@airliquide.com' FROM AZIENDE WHERE CODICE='RAV';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'renzo.zampini@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;alberto.deruvo@airliquide.com;alberto.deruvo@airliquide.com' FROM AZIENDE WHERE CODICE='FER';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'david.imbriaco@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;ferruccio.mazzoleni@airliquide.com;ferruccio.mazzoleni@airliquide.com' FROM AZIENDE WHERE CODICE='ASS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'fabio.dibiase@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='CSE';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'fabio.dibiase@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='LAN';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'francesco.lanera@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;mariagrazia.silvestre@airliquide.com;mariagrazia.silvestre@airliquide.com' FROM AZIENDE WHERE CODICE='OST';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'salvatore.ucciardi@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;alfonso.mattia@airliquide.com;alfonso.mattia@airliquide.com' FROM AZIENDE WHERE CODICE='CAR';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'salvatore.ucciardi@airliquide.com;alfredo.daquino@airliquide.com;davide.italia@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;eleonora.nardi@airliquide.com;fabio.sanna@airliquide.com;giovanni.berta@airliquide.com;giuseppe.lanza@airliquide.com;leonardo.tassinari@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;rossano.nesi@airliquide.com;salvatore.ucciardi@airliquide.com;stefania.brangi@airliquide.com;thomas.perotti@airliquide.com;alfonso.mattia@airliquide.com;alfonso.mattia@airliquide.com' FROM AZIENDE WHERE CODICE='PRI';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'alessandra.teruzzi@airliquide.com;ada.petringa@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;fabio.larosa@airliquide.com;gabriel.marin@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;mario.locastro@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;fabio.larosa@airliquide.com;fabio.larosa@airliquide.com' FROM AZIENDE WHERE CODICE='LIS';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'lia.maiolino@airliquide.com;ada.petringa@airliquide.com;diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;fabio.larosa@airliquide.com;gabriel.marin@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;mario.locastro@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com;mario.locastro@airliquide.com;mario.locastro@airliquide.com' FROM AZIENDE WHERE CODICE='CAT';
INSERT INTO DISTRIBUTION_LIST (MODULO,ID_AZIENDA,LISTA_MAIL) SELECT 'ALIMOD80' AS MODULO, ID_AZIENDA,'diego.barbagallo@airliquide.com;domenico.santoro@airliquide.com;marco.salvoni@airliquide.com;mariacarmela.chiuri@airliquide.com;marta.belle@airliquide.com;martina.valli-sc@airliquide.com' FROM AZIENDE WHERE CODICE='SED';



-- Table: MAIL_CONFIG

DROP TABLE IF EXISTS MAIL_CONFIG CASCADE;

CREATE TABLE MAIL_CONFIG
(
	id_distribution_list serial not null,
	modulo character varying(30) not null,
	workflow_action character varying(30) not null,
	stato_iniziale char(3) not null,
	stato_finale char(3) not null,
	oggetto text not null,
	testo_mail text not null,
	mittente text
);


insert into MAIL_CONFIG (	
	modulo,
	workflow_action,
	stato_iniziale,
	stato_finale,
	oggetto,
	mittente,
	testo_mail
) values (
'ALIMOD80'
,'APPROVE'
,'DRA'
,'WAI'
,'ALIRep - Modulo Infortuni, incidenti nr. #NR_MODULO# del #DT_MODULO#'
,'noreply@alirep.net'
,'Gentile Utente,<br><br>è stato creato il modulo in oggetto in attesa della sua approvazione.<br>Per consultarlo e procedere con l''approvazione può accedere al portale cliccando sul seguente collegamento:<br><br><a href="https://www.alirep.net/alirep/astro?FUNCTIONID=InserimentoALIMOD80&OPERATION_TYPE=UPDATE&READONLY=Y&ID_MODULO=#ID_MODULO#">Cliccare qui</a><br><br>Cordiali saluti,<br><br>Team di sviluppo ALIRep<br>'
)
;

insert into MAIL_CONFIG (	
	modulo,
	workflow_action,
	stato_iniziale,
	stato_finale,
	oggetto,
	mittente,
	testo_mail
) values (
'ALIMOD80'
,'APPROVE'
,'WAI'
,'APP'
,'ALIRep - Modulo Infortuni, incidenti nr. #NR_MODULO# del #DT_MODULO# - APPROVATO'
,'noreply@alirep.net'
,'Gentile Utente,<br><br>il modulo in oggetto è stato approvato.<br>Per consultarlo può accedere al portale cliccando sul seguente collegamento:<br><br><a href="https://www.alirep.net/alirep/astro?FUNCTIONID=InserimentoALIMOD80&OPERATION_TYPE=UPDATE&READONLY=Y&ID_MODULO=#ID_MODULO#">Cliccare qui</a><br><br>Cordiali saluti,<br><br>Team di sviluppo ALIRep<br>'
)
;


insert into MAIL_CONFIG (	
	modulo,
	workflow_action,
	stato_iniziale,
	stato_finale,
	oggetto,
	mittente,
	testo_mail
) values (
'ALIMOD80'
,'ROLLBACK'
,'WAI'
,'DRA'
,'ALIRep - Modulo Infortuni, incidenti nr. #NR_MODULO# del #DT_MODULO# - ROLLBACK'
,'noreply@alirep.net'
,'Gentile Utente,<br><br>il modulo in oggetto è stato riportato allo stato di bozza.<br>Per consultarlo può accedere al portale cliccando sul seguente collegamento:<br><br><a href="https://www.alirep.net/alirep/astro?FUNCTIONID=InserimentoALIMOD80&OPERATION_TYPE=UPDATE&READONLY=Y&ID_MODULO=#ID_MODULO#">Cliccare qui</a><br><br>Cordiali saluti,<br><br>Team di sviluppo ALIRep<br>'
)
;

insert into MAIL_CONFIG (	
	modulo,
	workflow_action,
	stato_iniziale,
	stato_finale,
	oggetto,
	mittente,
	testo_mail
) values (
'ALIMOD80'
,'NOTIFY'
,'WAI'
,'APP'
,'ALIRep - Modulo Infortuni, incidenti nr. #NR_MODULO# del #DT_MODULO#'
,'noreply@alirep.net'
,'Gentile Utente,<br><br>la informiamo che il modulo in oggetto è stato registrato nel portale ALIrep.<br>Per consultarlo può accedere al portale cliccando sul seguente collegamento:<br><br><a href="https://www.alirep.net/alirep/astro?FUNCTIONID=InserimentoALIMOD80&OPERATION_TYPE=UPDATE&READONLY=Y&ID_MODULO=#ID_MODULO#">Cliccare qui</a><br><br>Cordiali saluti,<br><br>Team di sviluppo ALIRep<br>'
)
;



insert into MAIL_CONFIG 
(
	modulo
	,workflow_action
	,stato_iniziale
	,stato_finale
	,oggetto
	,mittente
	,testo_mail
) 
select 	
	'ALIMOD50' modulo
	,workflow_action
	,stato_iniziale
	,stato_finale
	,replace(oggetto,'Modulo Infortuni, incidenti','Verifica HSE')
	,mittente
	,replace(testo_mail,'ALIMOD80','ALIMOD50')
from MAIL_CONFIG
where modulo = 'ALIMOD80'
;	

insert into MAIL_CONFIG 
(
	modulo
	,workflow_action
	,stato_iniziale
	,stato_finale
	,oggetto
	,mittente
	,testo_mail
) 
select 	
	'ALIMOD67' modulo
	,workflow_action
	,stato_iniziale
	,stato_finale
	,replace(oggetto,'Modulo Infortuni, incidenti','Visita Comportamentale di Sicurezza')
	,mittente
	,replace(testo_mail,'ALIMOD80','ALIMOD67')
from MAIL_CONFIG
where modulo = 'ALIMOD80'
;	

insert into MAIL_CONFIG 
(
	modulo
	,workflow_action
	,stato_iniziale
	,stato_finale
	,oggetto
	,mittente
	,testo_mail
) 
select 	
	'ALIMOD20' modulo
	,workflow_action
	,stato_iniziale
	,stato_finale
	,replace(oggetto,'Modulo Infortuni, incidenti','Piano d''Azione')
	,mittente
	,replace(testo_mail,'ALIMOD20','ALIMOD20')
from MAIL_CONFIG
where modulo = 'ALIMOD80'
;



drop view if exists V_DISTRIBUTION_LIST;
create view V_DISTRIBUTION_LIST as 
select 
	dl.modulo
	,dl.workflow_action
	,dl.stato_iniziale
	,dl.stato_finale
	,dl.oggetto
	,dl.mittente
	,dl.testo_mail
	,t1.id_azienda
	,t1.codice_profilo
	,t1.destinatari_to
	,'' as destinatari_cc
	,'' as destinatari_bcc
from MAIL_CONFIG as dl, (select 
	ua.id_azienda
	,pr.codice as codice_profilo
	,string_agg(ut.email,';') as destinatari_to
from utenti_aziende as ua
inner join utenti as ut
on ut.id_utente = ua.id_utente 
inner join utenti_profili as up
on ua.id_utente = up.id_utente 
inner join profili as pr
on up.id_profilo = pr.id_profilo and pr.codice in ('APP')
inner join aziende as az 
on ua.id_azienda=az.id_azienda and tipo_azienda like '%HSE%'
group by 
	ua.id_azienda
	,pr.codice 
) as t1
where dl.workflow_action='APPROVE'
	and dl.stato_iniziale='DRA'
	AND dl.stato_finale='WAI'
	
UNION

select 
	dl.modulo
	,dl.workflow_action
	,dl.stato_iniziale
	,dl.stato_finale
	,dl.oggetto
	,dl.mittente
	,dl.testo_mail	
	,null as id_azienda
	,'' as codice_profilo
	,'' as destinatari_to
	,'' as destinatari_cc
	,'' as destinatari_bcc
from MAIL_CONFIG as dl
where dl.workflow_action='APPROVE'
	and dl.stato_iniziale='WAI'
	AND dl.stato_finale='APP'
	
UNION

select 
	dl.modulo
	,dl.workflow_action
	,dl.stato_iniziale
	,dl.stato_finale
	,dl.oggetto
	,dl.mittente
	,dl.testo_mail	
	,null as id_azienda
	,'' as codice_profilo
	,'' as destinatari_to
	,'' as destinatari_cc
	,'' as destinatari_bcc
from MAIL_CONFIG as dl
where dl.workflow_action='ROLLBACK'
	and dl.stato_iniziale='WAI'
	AND dl.stato_finale='DRA'
	
UNION

select 
	dl.modulo
	,mc.workflow_action
	,mc.stato_iniziale
	,mc.stato_finale
	,mc.oggetto
	,mc.mittente
	,mc.testo_mail	
	,dl.id_azienda
	,'' as codice_profilo
	,dl.lista_mail as destinatari_to
	,'' as destinatari_cc
	,'' as destinatari_bcc
from MAIL_CONFIG as mc
inner join DISTRIBUTION_LIST as dl
on mc.modulo=dl.modulo
where mc.workflow_action='NOTIFY'
	and mc.stato_iniziale='WAI'
	AND mc.stato_finale='APP'
;




