ALTER TABLE AREA_LAVORO ADD column RECT_X smallint;
ALTER TABLE AREA_LAVORO ADD column RECT_Y smallint;
ALTER TABLE AREA_LAVORO ADD column RECT_W smallint;
ALTER TABLE AREA_LAVORO ADD column RECT_H smallint;

ALTER TABLE AREA_LAVORO ADD column TESTO_X smallint;
ALTER TABLE AREA_LAVORO ADD column TESTO_Y smallint;
ALTER TABLE AREA_LAVORO ADD column A_CAPO smallint;


update area_lavoro set rect_x=0 where rect_x is null;
update area_lavoro set rect_y=0 where rect_y is null;
update area_lavoro set rect_w=0 where rect_w is null;
update area_lavoro set rect_h=0 where rect_h is null;

update area_lavoro set testo_x=0 where testo_x is null;
update area_lavoro set testo_y=0 where testo_y is null;
update area_lavoro set a_capo=0 where a_capo is null;




ALTER TABLE AREA_LAVORO ADD column RECT_X_STAMPA smallint;
ALTER TABLE AREA_LAVORO ADD column RECT_Y_STAMPA smallint;
ALTER TABLE AREA_LAVORO ADD column RECT_W_STAMPA smallint;
ALTER TABLE AREA_LAVORO ADD column RECT_H_STAMPA smallint;

ALTER TABLE AREA_LAVORO ADD column TESTO_X_STAMPA smallint;
ALTER TABLE AREA_LAVORO ADD column TESTO_Y_STAMPA smallint;
ALTER TABLE AREA_LAVORO ADD column A_CAPO_STAMPA smallint;


update area_lavoro set rect_x_STAMPA=0 where rect_x_STAMPA is null;
update area_lavoro set rect_y_STAMPA=0 where rect_y_STAMPA is null;
update area_lavoro set rect_w_STAMPA=0 where rect_w_STAMPA is null;
update area_lavoro set rect_h_STAMPA=0 where rect_h_STAMPA is null;

update area_lavoro set testo_x_STAMPA=0 where testo_x_STAMPA is null;
update area_lavoro set testo_y_STAMPA=0 where testo_y_STAMPA is null;
update area_lavoro set a_capo_STAMPA=0 where a_capo_STAMPA is null;