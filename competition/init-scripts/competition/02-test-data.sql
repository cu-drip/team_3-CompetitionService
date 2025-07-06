-- Предопределенные UUID для связи с user_service
-- Эти UUID должны совпадать с пользователями в user_service

-- Создание команд
INSERT INTO teams (id, name, avatar)
VALUES ('11111111-1111-1111-1111-111111111111', 'Thunder Hawks', 'https://example.com/avatar1.jpg'),
       ('22222222-2222-2222-2222-222222222222', 'Lightning Bears', 'https://example.com/avatar2.jpg'),
       ('33333333-3333-3333-3333-333333333333', 'Fire Dragons', 'https://example.com/avatar3.jpg'),
       ('44444444-4444-4444-4444-444444444444', 'Ice Wolves', 'https://example.com/avatar4.jpg');

-- Связь игроков с командами (используем UUID пользователей из user_service)
INSERT INTO team_player_relation (team_id, user_id)
VALUES
-- Thunder Hawks
('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), -- john_doe
('11111111-1111-1111-1111-111111111111', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), -- jane_smith
('11111111-1111-1111-1111-111111111111', 'cccccccc-cccc-cccc-cccc-cccccccccccc'), -- alice_johnson

-- Lightning Bears
('22222222-2222-2222-2222-222222222222', 'dddddddd-dddd-dddd-dddd-dddddddddddd'), -- bob_brown
('22222222-2222-2222-2222-222222222222', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), -- charlie_davis
('22222222-2222-2222-2222-222222222222', 'ffffffff-ffff-ffff-ffff-ffffffffffff'), -- diana_wilson

-- Fire Dragons
('33333333-3333-3333-3333-333333333333', 'gggggggg-gggg-gggg-gggg-gggggggggggg'), -- eve_miller
('33333333-3333-3333-3333-333333333333', 'hhhhhhhh-hhhh-hhhh-hhhh-hhhhhhhhhhhh');
-- frank_moore

-- MMR для команд
INSERT INTO team_mmr_relation (team_id, mmr)
VALUES ('11111111-1111-1111-1111-111111111111', 1250.5),
       ('22222222-2222-2222-2222-222222222222', 1180.0),
       ('33333333-3333-3333-3333-333333333333', 1320.7),
       ('44444444-4444-4444-4444-444444444444', 1050.3);

-- Создание турниров
INSERT INTO tournaments (id, title, description, sport, type_tournament, type_group, matches_number, start_time,
                         entry_cost, max_participants, registration_deadline, place, organizer_id)
VALUES ('55555555-5555-5555-5555-555555555555',
        'Spring Football Championship 2025',
        'Annual spring football tournament for teams and individuals',
        'football',
        'team',
        'round_robin',
        12,
        '2025-04-15 10:00:00',
        150.00,
        8,
        '2025-04-10 23:59:59',
        'Central Sports Arena',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), -- john_doe как организатор

       ('66666666-6666-6666-6666-666666666666',
        'Boxing Championship 2025',
        'Professional boxing tournament',
        'boxing',
        'solo',
        'olympic',
        16,
        '2025-05-20 14:00:00',
        75.00,
        16,
        '2025-05-15 23:59:59',
        'Boxing Club Downtown',
        'iiiiiiii-iiii-iiii-iiii-iiiiiiiiiiii'), -- admin как организатор

       ('77777777-7777-7777-7777-777777777777',
        'Chess Masters Tournament',
        'International chess competition',
        'chess',
        'solo',
        'swiss',
        7,
        '2025-06-01 09:00:00',
        25.00,
        32,
        '2025-05-25 23:59:59',
        'Chess Club Central',
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb');
-- jane_smith как организатор

-- Регистрации на турниры
INSERT INTO tournament_registrations (tournament_id, sport, participant_id, participant_type, status)
VALUES
-- Футбольный турнир (команды)
('55555555-5555-5555-5555-555555555555', 'football', '11111111-1111-1111-1111-111111111111', 'team', 'active'),
('55555555-5555-5555-5555-555555555555', 'football', '22222222-2222-2222-2222-222222222222', 'team', 'active'),
('55555555-5555-5555-5555-555555555555', 'football', '33333333-3333-3333-3333-333333333333', 'team', 'active'),

-- Боксерский турнир (индивидуальные)
('66666666-6666-6666-6666-666666666666', 'boxing', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'solo', 'active'),
('66666666-6666-6666-6666-666666666666', 'boxing', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'solo', 'active'),
('66666666-6666-6666-6666-666666666666', 'boxing', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'solo', 'active'),

-- Шахматный турнир (индивидуальные)
('77777777-7777-7777-7777-777777777777', 'chess', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'solo', 'active'),
('77777777-7777-7777-7777-777777777777', 'chess', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'solo', 'active'),
('77777777-7777-7777-7777-777777777777', 'chess', 'gggggggg-gggg-gggg-gggg-gggggggggggg', 'solo', 'active');
