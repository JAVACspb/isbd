-- Add credentials to employee table (used by /login)
ALTER TABLE employee
    ADD COLUMN IF NOT EXISTS login VARCHAR(255);

ALTER TABLE employee
    ADD COLUMN IF NOT EXISTS password VARCHAR(255);

-- Seed demo credentials
UPDATE employee
SET login = 'hr',
    password = 'hr123'
WHERE id = 1;

UPDATE employee
SET login = 'seller',
    password = 'seller123'
WHERE id = 2;

-- Optional: avoid duplicates (safe if already exists)
CREATE UNIQUE INDEX IF NOT EXISTS employee_login_uq ON employee (login);


