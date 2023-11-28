--ROLE TABLE
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL UNIQUE
);

--USER TABLE
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles (
    role_id BIGSERIAL REFERENCES roles(id),
    user_id BIGSERIAL REFERENCES users(id)
);

--REFRESH TABLE
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR NOT NULL UNIQUE,
    expiry_date DATE NOT NULL,
    user_id BIGINT REFERENCES users(id)
);