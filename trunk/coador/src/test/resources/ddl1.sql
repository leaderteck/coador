CREATE SEQUENCE e1_id_seq START WITH 1;
CREATE SEQUENCE e2_id_seq START WITH 1;
CREATE TABLE E1Test (id INTEGER NOT NULL, name VARCHAR(255), year INTEGER, E2_ID INTEGER, PRIMARY KEY (id));
CREATE TABLE E2Test (id INTEGER NOT NULL, PRIMARY KEY (id));
CREATE INDEX I_E1TEST_E2 ON E1Test (E2_ID);
