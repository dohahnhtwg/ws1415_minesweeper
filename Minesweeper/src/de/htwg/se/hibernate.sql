CREATE TABLE IF NOT EXISTS hibernateCell (
  cellid INT(6) AUTO_INCREMENT PRIMARY KEY,
  value SMALLINT DEFAULT 0,
  isRevealed BOOL DEFAULT FALSE,
  fieldid INT(6),
  FOREIGN KEY (`fieldid`) REFERENCES hibernateField(`fieldid`)
);

CREATE TABLE IF NOT EXISTS hibernateField (
  `fieldid` INT(6) AUTO_INCREMENT PRIMARY KEY,
  `nMines` TINYINT NOT NULL,
  `lines` TINYINT NOT NULL,
  `columns` TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS hibernateStatistic (
  `statid` INT(6) AUTO_INCREMENT PRIMARY KEY,
  `gamesWon` INT(6),
  `timeSpent` INT,
  `minTime` INT,
  `gamesPlayed` INT(6)
);

CREATE TABLE IF NOT EXISTS hibernateUser (
  `userid` INT(6) AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `encryptedPassword` BINARY NOT NULL,
  `salt` BINARY NOT NULL,
  `algorithm` VARCHAR(20) NOT NULL,
  `fieldid` INT(6) NOT NULL,
  `statid` INT(6) NOT NULL,
  FOREIGN KEY (`fieldid`) REFERENCES hibernateField(`fieldid`),
  FOREIGN KEY (`statid`) REFERENCES hibernateStatistic(`statid`)
);

CREATE TABLE minesweeper.field_cells
(
  fieldid INT(6) NOT NULL,
  cellid INT(6) NOT NULL,
  CONSTRAINT field_cells_fieldid_cellid_pk PRIMARY KEY (fieldid, cellid),
  CONSTRAINT field_cells_hibernatecell_cellid_fk FOREIGN KEY (cellid) REFERENCES hibernatecell (cellid),
  CONSTRAINT field_cells_hibernatefield_fieldid_fk FOREIGN KEY (fieldid) REFERENCES hibernatefield (fieldid)
);

SHOW FULL TABLES;
