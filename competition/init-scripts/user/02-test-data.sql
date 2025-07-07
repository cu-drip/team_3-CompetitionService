-- Админ
INSERT INTO users (name, surname, patronymic, phone_number, email, hashed_password, is_admin, date_of_birth, age, sex, weight, height, bio, avatar_url, username) VALUES
('Admin', 'Administrator', 'Adminovich', '+1234567890', 'admin@sports.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', TRUE, '1985-01-01', 40, 'MALE', 75.0, 180.0, 'System administrator', 'https://example.com/admin-avatar.jpg');

-- Обычные пользователи
INSERT INTO users (name, surname, patronymic, phone_number, email, hashed_password, is_admin, date_of_birth, age, sex, weight, height, bio, avatar_url, username) VALUES
('John', 'Doe', 'Alexander', '+1234567891', 'john.doe@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1990-05-15', 35, 'MALE', 80.5, 185.0, 'Passionate football player and tournament organizer', 'https://example.com/john-avatar.jpg'),

('Jane', 'Smith', 'Michael', '+1234567892', 'jane.smith@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1992-08-22', 33, 'FALSE', 65.0, 170.0, 'Professional chess player and sports enthusiast', 'https://example.com/jane-avatar.jpg'),

('Alice', 'Johnson', 'David', '+1234567893', 'alice.johnson@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1995-03-10', 30, 'FALSE', 55.0, 165.0, 'Multi-sport athlete specializing in tennis and chess', 'https://example.com/alice-avatar.jpg'),

( 'Bob', 'Brown', 'Robert', '+1234567894', 'bob.brown@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1988-12-03', 37, 'MALE', 90.0, 190.0, 'Boxer and football player', 'https://example.com/bob-avatar.jpg', 'bob_brown'),

('Charlie', 'Davis', 'William', '+1234567895', 'charlie.davis@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1993-07-18', 32, 'MALE', 78.0, 175.0, 'Boxing enthusiast and team player', 'https://example.com/charlie-avatar.jpg')
