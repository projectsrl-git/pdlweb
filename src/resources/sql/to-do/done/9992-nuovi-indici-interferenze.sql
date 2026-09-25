DROP INDEX IF EXISTS idx_pdl_1
;

CREATE INDEX idx_pdl_1
  ON pdl
  USING btree
  (dt_pdl,nr_pdl)
; 

DROP INDEX IF EXISTS idx_pdl_2
;

CREATE INDEX idx_pdl_2
  ON pdl
  USING btree
  (stato)
; 

DROP INDEX IF EXISTS idx_pdl_3
;

CREATE INDEX idx_pdl_3
  ON pdl
  USING btree
  (id_area)
; 



DROP INDEX IF EXISTS idx_pdl_4
;

CREATE INDEX idx_pdl_4
  ON pdl
  USING btree
  (id_azienda)
; 


DROP INDEX IF EXISTS idx_pdl_5
;

CREATE INDEX idx_pdl_5
  ON pdl
  USING btree
  (id_utente_attivazione)
; 



DROP INDEX IF EXISTS idx_pdl_6
;

CREATE INDEX idx_pdl_6
  ON pdl
  USING btree
  (id_equipment)
; 


DROP INDEX IF EXISTS idx_pdl_7
;

CREATE INDEX idx_pdl_7
  ON pdl
  USING btree
  (id_impianto)
; 



DROP INDEX IF EXISTS idx_pdl_8
;

CREATE INDEX idx_pdl_8
  ON pdl
  USING btree
  (id_pdl, stato)
; 




DROP INDEX IF EXISTS idx_pdl_9
;

CREATE INDEX idx_pdl_9
  ON pdl
  USING btree
  (id_area, stato)
; 

DROP INDEX IF EXISTS idx_parametri_1
;

CREATE INDEX idx_parametri_1
  ON parametri
  USING btree
  (dominio,codice)
;


DROP INDEX IF EXISTS idx_parametri_2
;

CREATE INDEX idx_parametri_2
  ON parametri
  USING btree
  (dominio)
;


DROP INDEX IF EXISTS idx_area_lavoro_1
;

CREATE INDEX idx_area_lavoro_1
  ON area_lavoro
  USING btree
  (id_impianto,id_area)
;


DROP INDEX IF EXISTS idx_equipment_1
;

CREATE INDEX idx_equipment_1
  ON equipment
  USING btree
  (id_equipment)
;

DROP INDEX IF EXISTS idx_aziende_1
;

CREATE INDEX idx_aziende_1
  ON aziende
  USING btree
  (id_azienda)
;


DROP INDEX IF EXISTS idx_attributi_azienda_1
;

CREATE INDEX idx_attributi_azienda_1
  ON attributi_azienda
  USING btree
  (id_azienda,codice_attributo)
;


DROP INDEX IF EXISTS idx_utenti_1
;

CREATE INDEX idx_utenti_1
  ON utenti
  USING btree
  (id_utente)
;


DROP INDEX IF EXISTS idx_preposti_impresa_1
;

CREATE INDEX idx_preposti_impresa_1
  ON preposti_impresa
  USING btree
  (nome_impresa)
;
