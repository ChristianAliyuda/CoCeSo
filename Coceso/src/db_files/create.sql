CREATE SEQUENCE units_id_seq;
CREATE SEQUENCE persons_id_seq;
CREATE SEQUENCE log_id_seq;
CREATE SEQUENCE incidents_id_seq;
CREATE SEQUENCE cocesopois_id_seq;
CREATE SEQUENCE cases_id_seq;

CREATE TABLE cocesopois (
  id INTEGER NOT NULL DEFAULT nextval('cocesopois_id_seq'),
  address VARCHAR (64) NOT NULL,
  longitude DOUBLE PRECISION NOT NULL DEFAULT (0),
  latitude DOUBLE PRECISION NOT NULL DEFAULT (0),
  minimumUnits INTEGER NOT NULL DEFAULT (0),
  PRIMARY KEY (id)
);

CREATE TABLE cases (
  id INTEGER NOT NULL DEFAULT nextval('cases_id_seq'),
  place INTEGER,
  name VARCHAR(64) NOT NULL,
  organiser VARCHAR (64) NOT NULL,
  pax INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY (place) REFERENCES cocesopois ON DELETE SET NULL
);

CREATE TABLE units (
  id INTEGER DEFAULT nextval('units_id_seq'),
  aCase integer NOT NULL,
  state varchar (16) NOT NULL,
  call varchar (64) NOT NULL,
  ani varchar (16) NOT NULL,
  withDoc boolean NOT NULL,
  portable boolean NOT NULL,
  transportVehicle boolean NOT NULL,
  info varchar (128) NOT NULL,
  position integer,
  home integer,
  PRIMARY KEY (id),
  FOREIGN KEY (aCase) REFERENCES cases ON DELETE CASCADE,
  FOREIGN KEY (position) REFERENCES cocesopois ON DELETE SET NULL,
  FOREIGN KEY (home) REFERENCES cocesopois ON DELETE SET NULL
);

CREATE TABLE persons (
  id INTEGER NOT NULL DEFAULT nextval('persons_id_seq'),
  activeCase INTEGER,
  given_name VARCHAR (32) NOT NULL ,
  sur_name VARCHAR (32) NOT NULL,
  dnr INTEGER NOT NULL,
  contact VARCHAR (32) NOT NULL,
  allowLogin BOOLEAN NOT NULL,
  hashedPW VARCHAR (64),
  PRIMARY KEY (id),
  FOREIGN KEY (activeCase) REFERENCES cases ON DELETE SET NULL
);

CREATE TABLE crews (
  units_id integer NOT NULL,
  persons_id integer NOT NULL,
  PRIMARY KEY (units_id, persons_id),
  FOREIGN KEY (units_id) REFERENCES units ON DELETE CASCADE,
  FOREIGN KEY (persons_id) REFERENCES persons ON DELETE CASCADE
);

CREATE TABLE incidents (
  id INTEGER NOT NULL DEFAULT nextval('incidents_id_seq'),
  aCase INTEGER NOT NULL,
  state VARCHAR (16) NOT NULL,
  type VARCHAR (16) NOT NULL,
  priority INTEGER NOT NULL,
  blue BOOLEAN NOT NULL,
  bo INTEGER,
  ao INTEGER,
  info VARCHAR (64),
  caller VARCHAR (32),
  casusNr VARCHAR (20),
  PRIMARY KEY (id),
  FOREIGN KEY (aCase) REFERENCES cases ON DELETE CASCADE,
  FOREIGN KEY (bo) REFERENCES cocesopois ON DELETE SET NULL,
  FOREIGN KEY (ao) REFERENCES cocesopois ON DELETE SET NULL
);

CREATE TABLE log (
  id INTEGER NOT NULL DEFAULT nextval('log_id_seq'),
  aCase INTEGER NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  unit INTEGER,
  incident INTEGER,
  taskstate varchar (16),
  autoGenerated BOOLEAN NOT NULL,
  uzer INTEGER NOT NULL,
  text VARCHAR (128),
  json VARCHAR (256),
  PRIMARY KEY (id),
  FOREIGN KEY (aCase) REFERENCES cases ON DELETE NO ACTION,
  FOREIGN KEY (unit) REFERENCES units ON DELETE NO ACTION,
  FOREIGN KEY (incident) REFERENCES incidents ON DELETE NO ACTION,
  FOREIGN KEY (uzer) REFERENCES persons ON DELETE NO ACTION
);



CREATE TABLE tasks (
  incident_id INTEGER NOT NULL,
  unit_id INTEGER NOT NULL,
  state VARCHAR (16),
  PRIMARY KEY (incident_id, unit_id),
  FOREIGN KEY (incident_id) REFERENCES incidents ON DELETE CASCADE,
  FOREIGN KEY (unit_id) REFERENCES units ON DELETE CASCADE
);