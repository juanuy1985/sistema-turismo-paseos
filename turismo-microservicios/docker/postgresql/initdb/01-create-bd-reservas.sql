CREATE USER reservas_user WITH ENCRYPTED PASSWORD 'reservas_password';

CREATE DATABASE bd_reservas OWNER reservas_user;

GRANT ALL PRIVILEGES ON DATABASE bd_reservas TO reservas_user;

\connect bd_reservas

ALTER SCHEMA public OWNER TO reservas_user;
GRANT USAGE, CREATE ON SCHEMA public TO reservas_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO reservas_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO reservas_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON FUNCTIONS TO reservas_user;
