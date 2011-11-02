CREATE SEQUENCE e1_id_seq START WITH 1;
CREATE SEQUENCE e2_id_seq START WITH 1;
CREATE SEQUENCE e3_id_seq START WITH 1;
CREATE TABLE E1 (id INTEGER NOT NULL, name VARCHAR(255), year INTEGER, E2_ID INTEGER, PRIMARY KEY (id));
CREATE TABLE E2 (id INTEGER NOT NULL, PRIMARY KEY (id));
CREATE TABLE E3 (id INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY(id));
CREATE TABLE E1_E3 (id INTEGER NOT NULL AUTO_INCREMENT, E1_ID INTEGER, E3_ID INTEGER, PRIMARY KEY(id));
CREATE INDEX I_E1_E2 ON E1 (E2_ID);
