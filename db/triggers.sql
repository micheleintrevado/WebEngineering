SHOW VARIABLES LIKE 'event_scheduler';
-- Se il risultato è OFF, abilitalo con:
SET GLOBAL event_scheduler = ON;
CREATE TABLE eventi_da_processare (
    id_master INT
);

DELIMITER //

CREATE PROCEDURE inserisci_eventi_ricorrenti_trig (
    IN p_id_master INT
)
BEGIN
    DECLARE v_nome VARCHAR(255);
    DECLARE v_giorno DATE;
    DECLARE v_orario_inizio TIME;
    DECLARE v_orario_fine TIME;
    DECLARE v_descrizione VARCHAR(255);
    DECLARE v_tipoevento ENUM('lezione', 'esame', 'seminario', 'parziale', 'riunione', 'lauree', 'altro');
    DECLARE v_responsabile INT;
    DECLARE v_aula INT;
    DECLARE v_corso INT;
    DECLARE v_tipologia ENUM('giornaliera', 'settimanale', 'mensile');
    DECLARE v_data_termine DATE;
    DECLARE v_data_corrente DATE;
    DECLARE v_diff INT;

    -- Ottieni i dettagli dell'evento e della ricorrenza
    SELECT nome, giorno, orario_inizio, orario_fine, descrizione, tipologia, id_responsabile, id_aula, id_corso
    INTO v_nome, v_giorno, v_orario_inizio, v_orario_fine, v_descrizione, v_tipoevento, v_responsabile, v_aula, v_corso
    FROM evento
    WHERE id_master = p_id_master
    LIMIT 1;

    SELECT tipo, data_termine
    INTO v_tipologia, v_data_termine
    FROM ricorrenza
    WHERE id = p_id_master;

    SET v_data_corrente = v_giorno;
    
    WHILE v_data_corrente <= v_data_termine DO
           
        -- Calcola la prossima data dell'evento ricorrente
        IF v_tipologia = 'giornaliera' THEN
            SET v_data_corrente = DATE_ADD(v_data_corrente, INTERVAL 1 DAY);
        ELSEIF v_tipologia = 'settimanale' THEN
            SET v_data_corrente = DATE_ADD(v_data_corrente, INTERVAL 7 DAY);
        ELSEIF v_tipologia = 'mensile' THEN
            SET v_data_corrente = DATE_ADD(v_data_corrente, INTERVAL 30 DAY);
        END IF;
        
        IF v_data_corrente <= v_data_termine THEN
			-- Inserisci l'evento nella tabella eventi
			INSERT INTO evento (nome, giorno, orario_inizio, orario_fine, descrizione, tipologia, id_responsabile, id_master, id_aula, id_corso)
			VALUES (v_nome, v_data_corrente, v_orario_inizio, v_orario_fine, v_descrizione, v_tipoevento, v_responsabile, p_id_master, v_aula, v_corso);
		END IF;
    END WHILE;
END //

DELIMITER ;
DELIMITER //
CREATE TRIGGER after_insert_evento
AFTER INSERT ON evento
FOR EACH ROW
BEGIN
    DECLARE v_exists INT;

    -- Controlla se esiste una ricorrenza corrispondente
    SELECT COUNT(*) INTO v_exists
    FROM ricorrenza
    WHERE id = NEW.id_master;

    -- Se esiste la ricorrenza corrispondente, inserisce nella tabella temporanea
    IF v_exists > 0 THEN
        INSERT INTO eventi_da_processare (id_master) VALUES (NEW.id_master)
        ON DUPLICATE KEY UPDATE id_master = VALUES(id_master);
        
        -- Chiama immediatamente la stored procedure per processare
        CALL inserisci_eventi_ricorrenti_trig(NEW.id_master);
    END IF;
END;

CREATE TRIGGER after_update_evento
AFTER UPDATE ON evento
FOR EACH ROW
BEGIN
    DECLARE v_exists_old INT;
    DECLARE v_exists_new INT;

    -- Controlla se esisteva una ricorrenza prima dell'update
    SELECT COUNT(*) INTO v_exists_old
    FROM ricorrenza
    WHERE id = OLD.id_master;

    -- Controlla se esiste una ricorrenza dopo l'update
    SELECT COUNT(*) INTO v_exists_new
    FROM ricorrenza
    WHERE id = NEW.id_master;

    -- Se esisteva una ricorrenza prima ma non dopo, cancella i ricorrenti
    IF v_exists_old > 0 AND v_exists_new = 0 THEN
        INSERT INTO eventi_da_processare (id_master) VALUES (OLD.id_master)
        ON DUPLICATE KEY UPDATE id_master = VALUES(id_master);
    END IF;

    -- Se non esisteva una ricorrenza prima ma ora sì, inserisci i ricorrenti
    IF v_exists_old = 0 AND v_exists_new > 0 THEN
        INSERT INTO eventi_da_processare (id_master) VALUES (NEW.id_master)
        ON DUPLICATE KEY UPDATE id_master = VALUES(id_master);
    END IF;

    -- Se la ricorrenza è cambiata, rimuove i vecchi e aggiunge i nuovi
    IF OLD.id_master <> NEW.id_master THEN
        IF v_exists_old > 0 THEN
            INSERT INTO eventi_da_processare (id_master) VALUES (OLD.id_master)
            ON DUPLICATE KEY UPDATE id_master = VALUES(id_master);
        END IF;
        IF v_exists_new > 0 THEN
            INSERT INTO eventi_da_processare (id_master) VALUES (NEW.id_master)
            ON DUPLICATE KEY UPDATE id_master = VALUES(id_master);
        END IF;
    END IF;
END //


DELIMITER ;

DELIMITER //

CREATE EVENT processa_eventi_ricorrenti
ON SCHEDULE EVERY 20 second
DO
BEGIN
    DECLARE v_id_master INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE curs CURSOR FOR
        SELECT id_master FROM eventi_da_processare;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN curs;

    read_loop: LOOP
        FETCH curs INTO v_id_master;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Chiama la stored procedure per inserire gli eventi ricorrenti
        CALL inserisci_eventi_ricorrenti_trig(v_id_master);

        -- Rimuove il record dalla tabella temporanea
        DELETE FROM eventi_da_processare WHERE id_master = v_id_master;
    END LOOP;

    CLOSE curs;
END //

DELIMITER ;
INSERT INTO ricorrenza (tipo, data_termine) values ('settimanale', '2024-09-15');
SET @id_master = LAST_INSERT_ID();
INSERT INTO Evento (nome, giorno, orario_inizio, orario_fine, descrizione, tipologia, id_responsabile, id_master, id_aula, id_corso)
values ('A','2024-08-15','09:00','12:00','Ricorrenza','lezione',2,@id_master,2,2);
-- UPDATE evento SET nome='BB' WHERE id_master = @id_master
