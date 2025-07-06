-- Создание тестовых пользователей с предопределенными UUID
-- Эти UUID используются в competition_service для связи

-- Админ
INSERT INTO users (id, name, surname, patronymic, phone_number, email, hashed_password, is_admin, date_of_birth, age, sex, weight, height, bio, avatar_url, username) VALUES
('iiiiiiii-iiii-iiii-iiii-iiiiiiiiiiii', 'Admin', 'Administrator', 'Adminovich', '+1234567890', 'admin@sports.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', TRUE, '1985-01-01', 40, 'M', 75.0, 180.0, 'System administrator', 'https://example.com/admin-avatar.jpg', 'admin');

-- Обычные пользователи
INSERT INTO users (id, name, surname, patronymic, phone_number, email, hashed_password, is_admin, date_of_birth, age, sex, weight, height, bio, avatar_url, username) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'John', 'Doe', 'Alexander', '+1234567891', 'john.doe@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1990-05-15', 35, 'M', 80.5, 185.0, 'Passionate football player and tournament organizer', 'https://example.com/john-avatar.jpg', 'john_doe'),

('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Jane', 'Smith', 'Michael', '+1234567892', 'jane.smith@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1992-08-22', 33, 'F', 65.0, 170.0, 'Professional chess player and sports enthusiast', 'https://example.com/jane-avatar.jpg', 'jane_smith'),

('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Alice', 'Johnson', 'David', '+1234567893', 'alice.johnson@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1995-03-10', 30, 'F', 55.0, 165.0, 'Multi-sport athlete specializing in tennis and chess', 'https://example.com/alice-avatar.jpg', 'alice_johnson'),

('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Bob', 'Brown', 'Robert', '+1234567894', 'bob.brown@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1988-12-03', 37, 'M', 90.0, 190.0, 'Boxer and football player', 'https://example.com/bob-avatar.jpg', 'bob_brown'),

('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Charlie', 'Davis', 'William', '+1234567895', 'charlie.davis@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1993-07-18', 32, 'M', 78.0, 175.0, 'Boxing enthusiast and team player', 'https://example.com/charlie-avatar.jpg', 'charlie_davis'),

('ffffffff-ffff-ffff-ffff-ffffffffffff', 'Diana', 'Wilson', 'Elizabeth', '+1234567896', 'diana.wilson@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1991-11-25', 34, 'F', 60.0, 168.0, 'Competitive boxer and team sports player', 'https://example.com/diana-avatar.jpg', 'diana_wilson'),

('gggggggg-gggg-gggg-gggg-gggggggggggg', 'Eve', 'Miller', 'Anne', '+1234567897', 'eve.miller@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1994-04-12', 31, 'F', 58.0, 172.0, 'Chess master and strategic game player', 'https://example.com/eve-avatar.jpg', 'eve_miller'),

('hhhhhhhh-hhhh-hhhh-hhhh-hhhhhhhhhhhh', 'Frank', 'Moore', 'Thomas', '+1234567898', 'frank.moore@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', FALSE, '1987-09-08', 38, 'M', 85.0, 182.0, 'Veteran athlete with experience in multiple sports', 'https://example.com/frank-avatar.jpg', 'frank_moore');

-- Дополнительные пользователи для тестирования
INSERT INTO users (name, surname, email, hashed_password, username, age, sex) VALUES
('Test', 'User1', 'test1@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', 'test_user1', 25, 'M'),
('Test', 'User2', 'test2@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', 'test_user2', 28, 'F'),
('Test', 'User3', 'test3@email.com', '$2a$12$LQv3c1yqBwWJnwJvmxjzaOQZ7QzrXmKkHgZKLhzYi6tRPnHgLkKMC', 'test_user3', 22, 'M');