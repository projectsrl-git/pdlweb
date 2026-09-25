delete from menu_profili where id_menu in (select id_menu from menu where alias in ('capi_turno','InserimentoCapiTurno','RicercaCapiTurno','delegato_lavori','InserimentoDelegatoLavori','RicercaDelegatoLavori'
,'personale_interno','InserimentoPersonaleInterno','RicercaPersonaleInterno','responsabile_centrale','InserimentoResponsabileCentrale','RicercaResponsabileCentrale')
);

delete from menu where alias in ('capi_turno','InserimentoCapiTurno','RicercaCapiTurno','delegato_lavori','InserimentoDelegatoLavori','RicercaDelegatoLavori'
,'personale_interno','InserimentoPersonaleInterno','RicercaPersonaleInterno','responsabile_centrale','InserimentoResponsabileCentrale','RicercaResponsabileCentrale')
;


delete from menu_profili where id_menu in (select id_menu from menu where alias in ('area_lavoro','InserimentoAreaLavoro','RicercaAreaLavoro'));

delete from menu where alias in ('area_lavoro','InserimentoAreaLavoro','RicercaAreaLavoro');

-- area_lavoro
INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='anagrafiche'),50,'area_lavoro','#','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='area_lavoro'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Area di Lavoro');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='area_lavoro'),1,'InserimentoAreaLavoro','astro?FUNCTIONID=InserimentoAreaLavoro','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='InserimentoAreaLavoro'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Inserimento');

INSERT INTO MENU (id_menu_sup,ordine,alias,link,icon) VALUES ((select id_menu from menu where alias ='area_lavoro'),2,'RicercaAreaLavoro','astro?FUNCTIONID=RicercaAreaLavoro','');
INSERT INTO MENU_LINGUE (id_menu,id_lingue_iso,descrizione) VALUES ((select id_menu from menu where alias ='RicercaAreaLavoro'),(select id_lingue_iso from lingue_iso where codice_iso='it'),'Ricerca');

insert into menu_profili (id_menu,id_profilo) select id_menu,(select id_profilo from PROFILI where codice='ADM') from MENU 
where alias in ('area_lavoro','InserimentoAreaLavoro','RicercaAreaLavoro')
;

insert into menu_profili (id_menu,id_profilo) select id_menu,(select id_profilo from PROFILI where codice='PLA') from MENU 
where alias in ('area_lavoro','InserimentoAreaLavoro','RicercaAreaLavoro')
;

insert into menu_profili (id_menu,id_profilo) select id_menu,(select id_profilo from PROFILI where codice='RCD') from MENU 
where alias in ('area_lavoro','InserimentoAreaLavoro','RicercaAreaLavoro')
;


