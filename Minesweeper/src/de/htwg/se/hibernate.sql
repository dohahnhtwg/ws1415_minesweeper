CREATE TABLE celltest (
  cellid VARCHAR(255) PRIMARY KEY NOT NULL,
  value SMALLINT(6) DEFAULT '0',
  isRevealed TINYINT(1) DEFAULT '0',
  idx INT(6) NOT NULL,
  fieldid VARCHAR(50) NOT NULL,
  CONSTRAINT celltest_ibfk_1 FOREIGN KEY (fieldid) REFERENCES fieldtest (fieldid)
);

CREATE TABLE fieldtest (
  fieldid VARCHAR(50) PRIMARY KEY NOT NULL,
  nmines INT(6),
  nlines INT(6),
  ncolumns INT(6),
  isgameover TINYINT(1) DEFAULT '0',
  isvictory TINYINT(1) DEFAULT '0'
);

CREATE TABLE statistictest (
  statid VARCHAR(255) PRIMARY KEY NOT NULL,
  gamesWon INT(6) DEFAULT '0',
  timeSpent BIGINT(20) DEFAULT '0',
  minTime BIGINT(20) DEFAULT '999999999999999',
  gamesPlayed INT(6) DEFAULT '0'
);

CREATE TABLE usertest (
  userid VARCHAR(50) PRIMARY KEY NOT NULL,
  username VARCHAR(255) NOT NULL,
  encryptedPassword BLOB NOT NULL,
  salt BLOB NOT NULL,
  pwdalgorithm VARCHAR(50) NOT NULL,
  statid VARCHAR(50),
  fieldid VARCHAR(50),
  CONSTRAINT usertest_fieldtest_fieldid_fk FOREIGN KEY (fieldid) REFERENCES fieldtest (fieldid),
  CONSTRAINT usertest_statistictest_statid_fk FOREIGN KEY (statid) REFERENCES statistictest (statid)
);

SHOW FULL TABLES;

DELETE FROM "statistictest" WHERE 1;
DELETE FROM "fieldtest" WHERE 1;
DELETE FROM "usertest" WHERE 1;
DELETE FROM "celltest" WHERE 1;
