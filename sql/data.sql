CREATE TABLE emails (
    id SERIAL PRIMARY KEY,
    create_date DATE,
    email VARCHAR(100) UNIQUE
);

INSERT INTO emails (create_date, email)
VALUES
    ('2023-01-01', 'email1@example.com'),
    ('2023-01-02', 'email2@example.com'),
    ('2023-01-03', 'email3@example.com'),
    ('2023-01-04', 'email4@example.com'),
    ('2023-01-05', 'email5@example.com'),
    ('2023-01-06', 'email6@example.com'),
    ('2023-01-07', 'email7@example.com'),
    ('2023-01-08', 'email8@example.com'),
    ('2023-01-09', 'email9@example.com'),
    ('2023-01-10', 'email10@example.com'),
    ('2023-01-11', 'email11@example.com'),
    ('2023-01-12', 'email12@example.com'),
    ('2023-01-13', 'email13@example.com'),
    ('2023-01-14', 'email14@example.com'),
    ('2023-01-15', 'email15@example.com'),
    ('2023-01-16', 'email16@example.com'),
    ('2023-01-17', 'email17@example.com'),
    ('2023-01-18', 'email18@example.com'),
    ('2023-01-19', 'email19@example.com'),
    ('2023-01-20', 'email20@example.com');
