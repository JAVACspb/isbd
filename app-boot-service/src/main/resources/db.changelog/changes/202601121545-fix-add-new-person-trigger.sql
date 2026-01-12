-- Fix trigger function add_new_person():
-- - nextval(person_ids) was interpreted as a column reference -> ERROR: column "person_ids" does not exist
-- - ensure person_ids sequence does not conflict with seeded person ids (1..5)

CREATE SEQUENCE IF NOT EXISTS person_ids START WITH 10000 INCREMENT BY 1;

-- Make sure sequence is ahead of existing ids
SELECT setval(
    'person_ids',
    (SELECT GREATEST(COALESCE(MAX(id), 0) + 1, 10000) FROM person),
    false
);

CREATE OR REPLACE FUNCTION add_new_person() RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    INSERT INTO person(id, name, surname, phone_number)
    VALUES (nextval('person_ids'), NEW.name, NEW.surname, NEW.phone_number);
    RETURN NEW;
END;
$$;


