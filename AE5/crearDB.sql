CREATE DATABASE biblioteca;
USE biblioteca;


CREATE TABLE llibres (
    id int NOT NULL AUTO_INCREMENT,
    titol VARCHAR(255),
    autor VARCHAR(255),
    any_naiximent VARCHAR(255),
    any_publicacio VARCHAR(255),
    editorial VARCHAR(255),
    numpag VARCHAR(255), 
    PRIMARY KEY (ID)
);