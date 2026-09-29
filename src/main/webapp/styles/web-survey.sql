CREATE TABLE users (
    user_id               BIGSERIAL    PRIMARY KEY,
    full_name             VARCHAR(100) NOT NULL,
    email                 VARCHAR(255) NOT NULL UNIQUE,
    birth_date            DATE,
    hear_about            VARCHAR(50),
    receive_announcements BOOLEAN      NOT NULL DEFAULT TRUE
);
 
-- Kiểm tra:
-- SELECT * FROM users;