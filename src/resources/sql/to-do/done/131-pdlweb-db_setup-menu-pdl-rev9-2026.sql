INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='pdl'),15,'InserimentoPDLv9','astro?FUNCTIONID=InserimentoPDLv9','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='InserimentoPDLv9'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Crea nuovo (2026)');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='pdl'),40,'RichiestePDL','astro?FUNCTIONID=RichiestePDL','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='RichiestePDL'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Richieste da Gestire');

