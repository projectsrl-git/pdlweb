INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='master_table'),2,'MasterTableALIMOD50','astro?FUNCTIONID=MasterTableALIMOD50','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='MasterTableALIMOD50'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'ALIMOD50 - Verifica HSE');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='master_table'),3,'MasterTableALIMOD67','astro?FUNCTIONID=MasterTableALIMOD67','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='MasterTableALIMOD67'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'ALIMOD67 - Visita Comportamentale di Sicurezza');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='master_table'),4,'MasterTableALIMOD80','astro?FUNCTIONID=MasterTableALIMOD80','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='MasterTableALIMOD80'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'ALIMOD80 - Infortuni, incidenti');

