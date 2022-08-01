
DROP SCHEMA IF EXISTS temperaturedb CASCADE;
CREATE SCHEMA temperaturedb;
SET search_path = temperaturedb;

CREATE TABLE TemperatureReading
(
	ID SERIAL,
	TIME_STAMP TIMESTAMP,
	INDOORTEMP INTEGER,
	OUTDOORTEMP INTEGER,
    PRIMARY KEY (ID)
);