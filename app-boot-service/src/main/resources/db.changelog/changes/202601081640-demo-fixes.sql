-- Liquibase change for demo usability:
-- - create hibernate_sequence (needed for POST /application)
-- - update seed person data to "Александр Алентьев" (for demo)
-- - ensure there is at least one job_application row (so GET /application isn't empty)

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 10000 INCREMENT BY 1;

UPDATE person
SET name = 'Александр',
    surname = 'Алентьев'
WHERE id = 1;

UPDATE job_application
SET name = 'Александр',
    surname = 'Алентьев',
    education = 'ИТМО',
    about_yourself = 'baseline seed',
    phone_number = '8999999999'
WHERE id = 1;

INSERT INTO job_application (id, name, surname, education, about_yourself, phone_number)
SELECT 1, 'Александр', 'Алентьев', 'ИТМО', 'baseline seed', '8999999999'
WHERE NOT EXISTS (SELECT 1 FROM job_application);



