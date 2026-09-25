-- MENU INTERFERENZE
--INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='interferenze'),30,'ModuloInterferenzeTurnisti','astro?FUNCTIONID=ModuloInterferenzeTurnisti','');
--INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='ModuloInterferenzeTurnisti'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Modulo Interferenze Turnisti');


INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='interferenze'),40,'limod70','#','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='limod70')
,(select id_lingue_iso from lingue_iso where codice_iso='it'),'Elenco LIMOD70');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='limod70'),1,'InserimentoLIMOD70','astro?FUNCTIONID=InserimentoLIMOD70','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='InserimentoLIMOD70'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Inserimento');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='limod70'),2,'RicercaLIMOD70','astro?FUNCTIONID=RicercaLIMOD70','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='RicercaLIMOD70'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Ricerca');





INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='interferenze'),50,'limod64','#','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='limod64')
,(select id_lingue_iso from lingue_iso where codice_iso='it'),'Elenco LIMOD64');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='limod64'),1,'InserimentoLIMOD64','astro?FUNCTIONID=InserimentoLIMOD64','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='InserimentoLIMOD64'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Inserimento');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='limod64'),2,'RicercaLIMOD64','astro?FUNCTIONID=RicercaLIMOD64','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='RicercaLIMOD64'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Ricerca');

