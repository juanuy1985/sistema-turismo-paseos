\set ON_ERROR_STOP on
\getenv reservas_db_name RESERVAS_DB_NAME
\getenv reservas_db_user RESERVAS_DB_USER
\getenv reservas_db_password RESERVAS_DB_PASSWORD

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles WHERE rolname = :'reservas_db_user') THEN
    EXECUTE format('CREATE ROLE %I LOGIN PASSWORD %L', :'reservas_db_user', :'reservas_db_password');
  END IF;
END
$$;

SELECT format('CREATE DATABASE %I OWNER %I', :'reservas_db_name', :'reservas_db_user')
WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = :'reservas_db_name')\gexec

DO $$
BEGIN
  EXECUTE format('GRANT ALL PRIVILEGES ON DATABASE %I TO %I', :'reservas_db_name', :'reservas_db_user');
END
$$;

\connect :reservas_db_name

DO $$
BEGIN
  EXECUTE format('ALTER SCHEMA public OWNER TO %I', :'reservas_db_user');
  EXECUTE format('GRANT USAGE, CREATE ON SCHEMA public TO %I', :'reservas_db_user');
  EXECUTE format('ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO %I', :'reservas_db_user');
  EXECUTE format('ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO %I', :'reservas_db_user');
  EXECUTE format('ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON FUNCTIONS TO %I', :'reservas_db_user');
END
$$;
