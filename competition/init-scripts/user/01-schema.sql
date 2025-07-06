-- Создание таблицы пользователей
CREATE TABLE users
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(100) NOT NULL,
    surname         VARCHAR(100) NOT NULL,
    patronymic      VARCHAR(100),
    phone_number    VARCHAR(20),
    email           VARCHAR(255) UNIQUE NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    is_admin        BOOLEAN DEFAULT FALSE,
    date_of_birth   DATE,
    age             INTEGER,
    sex             CHAR(1) CHECK (sex IN ('M', 'F')),
    weight          DOUBLE PRECISION CHECK (weight > 0),
    height          DOUBLE PRECISION CHECK (height > 0),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    bio             TEXT,
    avatar_url      VARCHAR(500),
    username        VARCHAR(50) UNIQUE NOT NULL
);

-- Создание индексов для производительности
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_is_admin ON users(is_admin);