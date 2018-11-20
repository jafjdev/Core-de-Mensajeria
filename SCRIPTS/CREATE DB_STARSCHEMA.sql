CREATE DATABASE "CoreMensajeria_StarSchema"
    WITH 
    OWNER = "CoreMensajeria"
    ENCODING = 'UTF8'
    -- LC_COLLATE = 'English_United States.1252'
    -- LC_CTYPE = 'English_United States.1252'
    LC_COLLATE = 'C'
    LC_CTYPE = 'UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c CoreMensajeria_StarSchema CoreMensajeria

CREATE EXTENSION postgres_fdw;

CREATE SERVER statistics_server FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host 'localhost', port '5432', dbname 'CoreMensajeria');

CREATE USER MAPPING FOR "CoreMensajeria" SERVER statistics_server OPTIONS (user 'CoreMensajeria', password 'coremensajeria');

CREATE SCHEMA stats;

IMPORT FOREIGN SCHEMA public LIMIT TO (Message, Company, Campaign) FROM SERVER statistics_server INTO stats;
