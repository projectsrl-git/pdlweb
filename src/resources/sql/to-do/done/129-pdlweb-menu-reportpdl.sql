update menu set link = '#' where alias='ReportPDL';


INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='ReportPDL'),10,'ReportPDLint','astro?FUNCTIONID=ReportPDL','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='ReportPDLint')
,(select id_lingue_iso from lingue_iso where codice_iso='it'),'Report PDL');


INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='ReportPDL'),20,'ReportStatusPDL','astro?FUNCTIONID=ReportStatusPDL','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='ReportStatusPDL')
,(select id_lingue_iso from lingue_iso where codice_iso='it'),'Status PDL');
