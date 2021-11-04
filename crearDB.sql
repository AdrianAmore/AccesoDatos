CREATE DATABASE Biblioteca;
USE Biblioteca;


CREATE TABLE Llibres (
    ID int NOT NULL AUTO_INCREMENT,
    Titol VARCHAR(255),
    Autor VARCHAR(255),
    Any_naiximent INT(4),
    Any_publicacio INT(4),
    Editorial VARCHAR(255),
    NumPag INT(5), 
    PRIMARY KEY (ID)
);