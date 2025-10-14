-- Ensure schema exists, then target it
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'wbg') THEN
    EXECUTE 'CREATE SCHEMA wbg';
  END IF;
END$$;

SET search_path TO wbg;

-- SPRING_SESSION
CREATE TABLE IF NOT EXISTS spring_session (
  primary_id CHAR(36) NOT NULL,
  session_id CHAR(36) NOT NULL,
  creation_time BIGINT NOT NULL,
  last_access_time BIGINT NOT NULL,
  max_inactive_interval INT NOT NULL,
  expiry_time BIGINT NOT NULL,
  principal_name VARCHAR(100),
  CONSTRAINT spring_session_pk PRIMARY KEY (primary_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS spring_session_ix1 ON spring_session (session_id);
CREATE INDEX IF NOT EXISTS spring_session_ix2 ON spring_session (expiry_time);
CREATE INDEX IF NOT EXISTS spring_session_ix3 ON spring_session (principal_name);

-- SPRING_SESSION_ATTRIBUTES
CREATE TABLE IF NOT EXISTS spring_session_attributes (
  session_primary_id CHAR(36) NOT NULL,
  attribute_name VARCHAR(200) NOT NULL,
  attribute_bytes BYTEA NOT NULL,
  CONSTRAINT spring_session_attributes_pk PRIMARY KEY (session_primary_id, attribute_name),
  CONSTRAINT spring_session_attributes_fk FOREIGN KEY (session_primary_id)
    REFERENCES spring_session (primary_id) ON DELETE CASCADE
);
