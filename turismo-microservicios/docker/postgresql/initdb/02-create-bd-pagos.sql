\set ON_ERROR_STOP 1
\getenv pagos_db_name PAGOS_DB_NAME
\getenv pagos_db_user PAGOS_DB_USER
\getenv pagos_db_password PAGOS_DB_PASSWORD

DO $$
BEGIN
  IF trim(coalesce(:'pagos_db_password', '')) = '' THEN
    RAISE EXCEPTION 'PAGOS_DB_PASSWORD no puede estar vacío';
  END IF;
  IF length(:'pagos_db_password') < 8 THEN
    RAISE EXCEPTION 'PAGOS_DB_PASSWORD debe tener al menos 8 caracteres';
  END IF;
END
$$;

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles WHERE rolname = :'pagos_db_user') THEN
    EXECUTE format('CREATE ROLE %I LOGIN PASSWORD %L', :'pagos_db_user', :'pagos_db_password');
  END IF;
END
$$;

SELECT format('CREATE DATABASE %I OWNER %I', :'pagos_db_name', :'pagos_db_user')
WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = :'pagos_db_name')\gexec

\connect :pagos_db_name

DO $$
BEGIN
  EXECUTE format('ALTER SCHEMA public OWNER TO %I', :'pagos_db_user');
END
$$;
