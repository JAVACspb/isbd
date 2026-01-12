-- Ensure token table exists (TokenService persists JWTs there)
CREATE TABLE IF NOT EXISTS token
(
    token        TEXT PRIMARY KEY,
    role         VARCHAR(64),
    time         TIMESTAMP
);


