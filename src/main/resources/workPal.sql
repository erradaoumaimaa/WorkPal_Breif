CREATE TYPE user_role AS ENUM ('admin', 'manager', 'member');

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role user_role NOT NULL
);
CREATE TABLE members (
    address VARCHAR(50) NOT NULL,
    number VARCHAR(10) NOT NULL
) INHERITS (users);

CREATE TABLE managers (

) INHERITS (users);


CREATE TYPE type_espace AS ENUM ('Salle', 'Salle de conférence', 'Salle de réunion', 'Bureau');

CREATE TABLE espace (
    id UUID PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    type type_espace NOT NULL,
    capacite INT NOT NULL,
    equipements TEXT[] NOT NULL, 
    description TEXT,
    prix DECIMAL(10, 2) NOT NULL 
);
