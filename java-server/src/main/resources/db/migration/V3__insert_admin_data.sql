-- Insert roles
INSERT INTO roles (type) VALUES ('admin'), ('user');

-- Insert user with the "admin" role
INSERT INTO users (first_name, last_name, username, email, password)
VALUES ('Admin', 'Wallet', 'adminuser', 'admin@wallet.com', '$2a$12$ymYXWxzPivp64Wi4JfepNOH9v1kL1tK1SI6P69Xw9S7H9A87wLQ4O');

-- Associate user with the "admin" role
INSERT INTO users_roles (role_id, user_id)
VALUES (
    (SELECT id FROM roles WHERE type = 'admin'),
    (SELECT id FROM users WHERE username = 'adminuser')
);

INSERT INTO users_roles (role_id, user_id)
VALUES (
    (SELECT id FROM roles WHERE type = 'user'),
    (SELECT id FROM users WHERE username = 'adminuser')
);

INSERT INTO transaction_types (name, description) VALUES ('TRANSFER', 'Transer on Transaction');
INSERT INTO transaction_types (name, description) VALUES ('TOPUP', 'Top Up on Transaction');
