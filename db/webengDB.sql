DROP DATABASE IF EXISTS `webeng`;
CREATE DATABASE IF NOT EXISTS `webeng` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `webeng`;


CREATE TABLE IF NOT EXISTS `Responsabile`(
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(60) NOT NULL,
    `nome` VARCHAR(255) NOT NULL,
    `version`  int unsigned DEFAULT '1',
    primary key(id));
    
CREATE TABLE IF NOT EXISTS `Aula` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(60) NOT NULL,
    `luogo` VARCHAR(60) NOT NULL,
    `edificio` VARCHAR(60) NOT NULL,
    `piano` VARCHAR(60) NOT NULL,
    `capienza` INT NOT NULL,
    `prese_elettriche` INT UNSIGNED NOT NULL,
    `prese_rete` INT UNSIGNED NOT NULL,
    `note` TINYTEXT NOT NULL,
    `id_responsabile` INT UNSIGNED NOT NULL,
    `version`  int unsigned DEFAULT '1',
    FOREIGN KEY (id_responsabile)
        REFERENCES Responsabile (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Attrezzatura` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `tipo` VARCHAR(60) NOT NULL,
    `version`  int unsigned DEFAULT '1',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Aula_Attrezzatura` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    id_aula INT UNSIGNED NOT NULL,
    id_attrezzatura INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_aula)
        REFERENCES Aula (id),
    FOREIGN KEY (id_attrezzatura)
        REFERENCES Attrezzatura (id)
        on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Gruppo` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(60) NOT NULL,
    `descrizione` TINYTEXT,
    `version`  int unsigned DEFAULT '1',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Aula_Gruppo` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    id_aula INT UNSIGNED NOT NULL,
    id_gruppo INT UNSIGNED NOT NULL,
    FOREIGN KEY (id_aula)
        REFERENCES Aula (id),
    FOREIGN KEY (id_gruppo)
        REFERENCES Gruppo (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Corso` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(60) NOT NULL,
    `version`  int unsigned DEFAULT '1',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Ricorrenza` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `tipo` ENUM('giornaliera', 'settimanale', 'mensile') NOT NULL,
    `data_termine` DATE NOT NULL,
    `version`  int unsigned DEFAULT '1',
    PRIMARY KEY (id)
);


-- fare il check sul corso: va messo solo quando la tipologia è lezione, esame e parziale
CREATE TABLE IF NOT EXISTS `Evento` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(60) NOT NULL,
    `giorno` DATE NOT NULL,
    `orario_inizio` TIME NOT NULL,
    `orario_fine` TIME NOT NULL,
    `descrizione` TINYTEXT NOT NULL,
    `tipologia` ENUM('lezione', 'esame', 'seminario', 'parziale', 'riunione', 'lauree', 'altro') NOT NULL,
    `id_responsabile` INT UNSIGNED NOT NULL,
    `id_master` INT UNSIGNED DEFAULT NULL,
    `id_aula` INT UNSIGNED NOT NULL,
    `id_corso` INT UNSIGNED DEFAULT NULL,
    `version`  int unsigned DEFAULT '1',
    PRIMARY KEY (id),
    CHECK ((`tipologia` IN ('lezione' , 'esame', 'parziale') AND `id_corso` IS NOT NULL)
        OR (`tipologia` IN ('seminario' , 'riunione', 'lauree', 'altro'))),
    FOREIGN KEY (id_aula)
        REFERENCES Aula (id),
    FOREIGN KEY (id_corso)
        REFERENCES Corso (id),
    FOREIGN KEY (id_master)
        REFERENCES Ricorrenza (id),
	FOREIGN KEY (id_responsabile)
        REFERENCES Responsabile (id)
);
    
CREATE TABLE IF NOT EXISTS `Admin`(
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(60) NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    `token` varchar(255),
    `version`  int unsigned DEFAULT '1',
    primary key(id)
);

-- DATI DI PROVA
drop user IF EXISTS 'webengUser'@'localhost';
CREATE USER 'webengUser'@'localhost' IDENTIFIED BY 'webengPassword';
GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'webengUser'@'localhost' WITH GRANT OPTION;


-- INSERIMENTO RESPONSABILI ****************
INSERT INTO Responsabile (nome, email) VALUES ('Mario Rossi','m.rossi@universita.it'), ('Anna Bianchi','a.bianchi@universita.it'), ('Luigi Verdi','l.verdi@universita.it'),
('Carlo Neri','c.neri@universita.it'), ('Elena Martini','e.martini@universita.it'), ('Nuovo Responsabile','Nuovo@Responsabile.it');

-- Inserimento AULE **************
INSERT INTO `webeng`.`aula` (`id`, `nome`, `luogo`, `edificio`, `piano`, `capienza`, `prese_elettriche`, `prese_rete`, `note`, `id_responsabile`) VALUES 
('1', 'C1.10', 'Coppito', 'Renato Ricamo', '0', '50', '9', '5', 'Nota1', 1), 
('2', 'A1.1', 'Coppito', 'Blocco 0', '1', '65', '20', '11', 'Nota2', 2),
('3', 'A1.6', 'Coppito', 'Blocco 0', '1', '55', '15', '14', 'Nota3', 2),
('4', 'C1.16', 'Coppito', 'Renato Ricamo', '0', '35', '10', '7', 'Nota4', 3);

-- Inserimento CORSI *********** 
INSERT INTO `webeng`.`corso` (`id`, `nome`) VALUES ('1', 'Artificial Intellgence');
INSERT INTO `webeng`.`corso` (`id`, `nome`) VALUES ('2', 'Data Analytics');
INSERT INTO `webeng`.`corso` (`id`, `nome`) VALUES ('3', 'Ingegneria del software');
INSERT INTO `webeng`.`corso` (`id`, `nome`) VALUES ('4', 'Software quality engineering');

-- Inserimento EVENTI ***************************
INSERT INTO Evento (nome, giorno, orario_inizio, orario_fine, descrizione, tipologia, id_responsabile, id_master, id_aula, id_corso)
VALUES 
('Lezione di Matematica','2024-06-15', '09:00:00', '11:00:00', 'Lezione sul calcolo differenziale', 'lezione', 1, NULL, 1, 2),
('Esame di Fisica','2024-06-16','14:00:00', '17:00:00', 'Esame finale di fisica generale', 'esame', 2, NULL, 2, 2),
('Seminario di Informatica', '2024-06-17' , '10:00:00', '12:00:00', 'Seminario su Intelligenza Artificiale', 'seminario', 3, NULL, 3, NULL),
('Riunione del Dipartimento', '2024-06-18', '09:30:00', '11:00:00', 'Riunione mensile del dipartimento di ingegneria', 'riunione', 4, NULL, 4, NULL),
('Parziale di Chimica','2024-06-19', '08:00:00', '10:00:00', 'Parziale di chimica organica', 'parziale', 2, NULL, 1, 2),
('Lauree di Giugno', '2024-06-20', '09:00:00', '18:00:00', 'Cerimonia di laurea per gli studenti del dipartimento di economia', 'lauree', 3, NULL, 2, NULL),
('Workshop di Fotografia', '2024-06-21', '10:00:00', '13:00:00', 'Workshop pratico di fotografia', 'altro', 1, NULL, 3, NULL),
('Seminario di Economia', '2024-06-14' ,'15:00:00', '23:00:00', 'Conferenza sulle nuove tendenze economiche', 'seminario', 5, NULL, 1, NULL),
('Lezione Attuale', date(now()),time(now() - interval 1 hour),time(now() + interval 3 hour),'Lezione in corso adesso','lezione',5,NULL, 2, 2);



-- Inserimento EVENTI RICORRENTI
INSERT INTO `webeng`.`Ricorrenza` (`id`,`tipo`,`data_termine`) VALUES ('1', 'settimanale', '2024-06-30');
INSERT INTO `webeng`.`evento` (`id`, `nome`, `giorno`, `orario_inizio`, `orario_fine`, `descrizione`, `tipologia`, `id_responsabile`, `id_aula`, `id_corso`, `id_master`) VALUES 
('12', 'Lezione Ricorrente1', '2024/05/14', '22:00', '23:00', 'BBB', 'lezione', '1', '1', 1, '1'), 
('13', 'Lezione Ricorrente2', '2024/05/15', '22:00', '23:00', 'ZZZ', 'altro', '4', '1', null, '1');

-- Inserimento GRUPPI
INSERT INTO `webeng`.`gruppo` (`id`, `nome`, `descrizione`) VALUES ('1', 'Gruppo1', 'Gruppo fantastico');
INSERT INTO `webeng`.`gruppo` (`id`, `nome`, `descrizione`) VALUES ('2', 'Gruppo2', 'Gruppo numeroso');
INSERT INTO `webeng`.`gruppo` (`id`, `nome`, `descrizione`) VALUES ('3', 'Gruppo3', 'Descrizione casuale');
INSERT INTO `webeng`.`gruppo` (`id`, `nome`, `descrizione`) VALUES ('4', 'Gruppo4', 'Descrizione casuale');

-- Associazione AULE-GRUPPI
INSERT INTO `webeng`.`aula_gruppo` (`id`,`id_aula`,`id_gruppo`) VALUES ('1','1','1');
INSERT INTO `webeng`.`aula_gruppo` (`id`,`id_aula`,`id_gruppo`) VALUES ('2','2','2');
INSERT INTO `webeng`.`aula_gruppo` (`id`,`id_aula`,`id_gruppo`) VALUES ('3','3','3');
INSERT INTO `webeng`.`aula_gruppo` (`id`,`id_aula`,`id_gruppo`) VALUES ('4','4','3');

-- Inserimento ATTREZZATURE
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('1','Proiettore');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('2','Schermo Motorizzato');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('3','Schermo Manuale');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('4','Impianto Audio');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('5','PC Fisso');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('6','Microfono a filo');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('7','Microfono senza filo');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('8','Lavagna Luminosa');
INSERT INTO `webeng`.`attrezzatura` (`id`,`tipo`) VALUES ('9','Wi-fi');


-- Associazione AULE-ATTREZZATURE
INSERT INTO `webeng`.`aula_attrezzatura` (`id`,`id_aula`,`id_attrezzatura`) VALUES ('1','1','1');
INSERT INTO `webeng`.`aula_attrezzatura` (`id`,`id_aula`,`id_attrezzatura`) VALUES ('2','1','2');
INSERT INTO `webeng`.`aula_attrezzatura` (`id`,`id_aula`,`id_attrezzatura`) VALUES ('3','2','3');

INSERT INTO `webeng`.`admin` (`id`,`username`,`password`,`token`) VALUES ('1','username1','pass', null);
-- fine 