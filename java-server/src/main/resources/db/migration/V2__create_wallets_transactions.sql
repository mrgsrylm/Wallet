--WALLET TABLE
CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    iban VARCHAR(34) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    balance NUMERIC(19, 2) NOT NULL,
    user_id BIGINT REFERENCES users(id) NOT NULL
);

--TRANSACTION TYPE TABLE
CREATE TABLE transaction_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(50)
);

--TRANSACTION TABLE
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    reference_number UUID NOT NULL UNIQUE,
    amount NUMERIC(19, 2) NOT NULL,
    description VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL,
    from_wallet_id BIGSERIAL REFERENCES wallets(id) NOT NULL,
    to_wallet_id BIGSERIAL REFERENCES wallets(id) NOT NULL,
    transaction_type_id BIGSERIAL REFERENCES transaction_types(id) NOT NULL
);