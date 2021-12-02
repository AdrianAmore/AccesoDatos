CREATE DATABASE destinacions;
USE destinacions;


CREATE TABLE preus (
    id INT(3) NOT NULL AUTO_INCREMENT,
    nom VARCHAR(100),
    preu VARCHAR(10),
    PRIMARY KEY (ID)
);

INSERT INTO `preus` (`id`, `nom`, `preu`) VALUES 
(NULL, 'Londres', '150'), 
(NULL, 'Paris', '135'),
(NULL, 'Roma', '105'),
(NULL, 'Nova York', '725'),
(NULL, 'Tokio', '1200');