CREATE TABLE users (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(200) NOT NULL,
    email         VARCHAR(200) NOT NULL UNIQUE,
    password_hash VARCHAR(200) NOT NULL,
    role          VARCHAR(20)  NOT NULL DEFAULT 'BUYER',
    cpf           VARCHAR(11),
    phone         VARCHAR(11),
    birth_date    DATE,
    zip_code      VARCHAR(8),
    state         VARCHAR(2),
    city          VARCHAR(200),
    neighborhood  VARCHAR(200),
    street        VARCHAR(200),
    street_number VARCHAR(20),
    complement    VARCHAR(200),
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_role  ON users (role);
