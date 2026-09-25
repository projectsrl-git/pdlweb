-- MENU INTERFERENZE
--INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='interferenze'),30,'ModuloInterferenzeTurnisti','astro?FUNCTIONID=ModuloInterferenzeTurnisti','');
--INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='ModuloInterferenzeTurnisti'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Modulo Interferenze Turnisti');


INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='interferenze'),30,'StoricoInterferenze','astro?FUNCTIONID=StoricoInterferenze','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='StoricoInterferenze')
,(select id_lingue_iso from lingue_iso where codice_iso='it'),'Storico interferenze');

--INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='limod70'),1,'InserimentoLIMOD70','astro?FUNCTIONID=InserimentoLIMOD70','');
--INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='InserimentoLIMOD70'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Inserimento');

--INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='limod70'),2,'RicercaLIMOD70','astro?FUNCTIONID=RicercaLIMOD70','');
--INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='RicercaLIMOD70'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Ricerca');

