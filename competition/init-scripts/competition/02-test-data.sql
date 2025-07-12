-- ВНИМАНИЕ: UUID игроков генерируются на стороне user_service, поэтому они могут меняться

-- === Команды ===
INSERT INTO teams (id, name, avatar)
VALUES (gen_random_uuid(), 'Neon Wolves', 'https://example.com/avatar1.png'),
       (gen_random_uuid(), 'Cyber Phoenix', 'https://example.com/avatar2.png'),
       (gen_random_uuid(), 'Thunder Strike', 'https://example.com/avatar3.png'),
       (gen_random_uuid(), 'Quantum Masters', 'https://example.com/avatar4.png'),
       (gen_random_uuid(), 'Apex Legends', 'https://example.com/avatar5.png'),
       (gen_random_uuid(), 'Dark Matter', 'https://example.com/avatar6.png'),
       (gen_random_uuid(), 'Steel Hawks', 'https://example.com/avatar7.png'),
       (gen_random_uuid(), 'Shadow Blade', 'https://example.com/avatar8.png'),
       (gen_random_uuid(), 'Iron Fist', 'https://example.com/avatar9.png'),
       (gen_random_uuid(), 'Elite Squad', 'https://example.com/avatar10.png');

-- === Игроки в командах ===
-- Привязываем по два игрока на команду, выбирая ID из предоставленного списка
WITH team_list AS (SELECT id, row_number() OVER (ORDER BY id) as rn
                   FROM teams
                   LIMIT 10),
     player_list AS (SELECT unnest(ARRAY [
         '11a73a3b-c23f-4a4c-8684-e8dfa0dcebda',
         'e947eff5-e1f0-402f-be91-f6bfd323cc27',
         '397915e5-c451-4a65-abde-ec99059b1eb0',
         'e950f563-461b-4a1b-925f-9dd165628833',
         'fd448cde-534e-4731-a361-4ea7306dee6d',
         '189035c2-2ce8-4b44-8b43-c53beda9f5b7',
         '83e9a4a7-1978-417c-999c-4dcf92d1a029',
         '5375dc7e-8ef7-4384-b95a-9cddf4b713e2',
         '8b69289c-cb7f-47f1-9243-25d7152c3a1e',
         'c398d4f9-9092-4b4d-ad92-22c93d083dd3',
         '73dfa385-47c3-444c-85b8-6f91e2a42953',
         '39412174-1bf3-4bf6-951c-73d01055e5e3',
         '1e1a4df7-8e47-452a-862f-a479a7c3c104',
         '00ec23f1-798f-432b-bc70-d9143df96cc7',
         'fb91ae36-10e1-4372-8918-941a24b3089a'
         ])::UUID                                AS uid,
                            row_number() OVER () as player_rn)
INSERT
INTO team_player_relation (team_id, user_id)
SELECT t.id, p.uid
FROM team_list t
         JOIN player_list p ON p.player_rn BETWEEN (t.rn - 1) * 2 + 1 AND t.rn * 2
WHERE p.player_rn <= 20;

-- === Турниры ===
-- Будем использовать несколько видов спорта и типов (solo/team)
INSERT INTO tournaments (id, title, description, sport, type_tournament, type_group,
                         tournament_instant_state, matches_number, start_time,
                         entry_cost, max_participants, registration_deadline, place, organizer_id)
VALUES
-- Solo турниры
(gen_random_uuid(), 'Blitz Chess Championship', 'Турнир по быстрым шахматам среди профессионалов', 'chess', 'solo',
 'round_robin',
 'openedRegistrationTournaments', 10, NOW() + interval '7 months', 0, 16, NOW() + interval '6 months', 'Москва',
 '11a73a3b-c23f-4a4c-8684-e8dfa0dcebda'),

(gen_random_uuid(), 'Urban Boxing League', 'Открытый турнир по боксу среди любителей', 'boxing', 'solo', 'olympic',
 'openedRegistrationTournaments', 8, NOW() + interval '7 months', 100, 8, NOW() + interval '6 months',
 'Санкт-Петербург', 'e947eff5-e1f0-402f-be91-f6bfd323cc27'),

(gen_random_uuid(), 'Metropolitan Tennis Open', 'Престижный турнир по теннису', 'tennis', 'solo', 'swiss',
 'openedRegistrationTournaments', 12, NOW() + interval '6.5 months', 50, 12, NOW() + interval '6 months', 'Казань',
 '397915e5-c451-4a65-abde-ec99059b1eb0'),

-- Team турниры
(gen_random_uuid(), 'Street Football Championship', 'Дворовый чемпионат по футболу 5x5', 'football', 'team', 'olympic',
 'openedRegistrationTournaments',
 16, NOW() + interval '7 months', 0, 10, NOW() + interval '6 months', 'Ростов-на-Дону',
 'e950f563-461b-4a1b-925f-9dd165628833'),

(gen_random_uuid(), 'Urban Basketball League 3x3', 'Динамичный баскетбол три на три', 'basketball', 'team',
 'round_robin',
 'openedRegistrationTournaments', 9, NOW() + interval '6.5 months', 25, 8, NOW() + interval '6 months', 'Новосибирск',
 'fd448cde-534e-4731-a361-4ea7306dee6d'),

(gen_random_uuid(), 'Youth Football Cup', 'Юношеский футбольный кубок до 14 лет', 'football', 'team', 'swiss',
 'openedRegistrationTournaments', 6, NOW() + interval '6.3 months', 0, 6, NOW() + interval '6 months', 'Калуга',
 '189035c2-2ce8-4b44-8b43-c53beda9f5b7'),

(gen_random_uuid(), 'Summer Jiu-Jitsu Masters', 'Летний турнир по джиу-джитсу', 'jiu_jitsu', 'team', 'olympic',
 'openedRegistrationTournaments', 10, NOW() + interval '6.5 months', 80, 10, NOW() + interval '6 months', 'Краснодар',
 '83e9a4a7-1978-417c-999c-4dcf92d1a029'),

-- Дополнительные турниры
(gen_random_uuid(), 'Elite Chess Tournament', 'Турнир для мастеров шахмат', 'chess', 'solo', 'olympic',
 'openedRegistrationTournaments', 8, NOW() + interval '8 months', 200, 8, NOW() + interval '7 months', 'Екатеринбург',
 '5375dc7e-8ef7-4384-b95a-9cddf4b713e2'),

(gen_random_uuid(), 'Phoenix Boxing Championship', 'Профессиональный турнир по боксу', 'boxing', 'solo', 'round_robin',
 'openedRegistrationTournaments', 12, NOW() + interval '8 months', 150, 12, NOW() + interval '7 months',
 'Нижний Новгород',
 '8b69289c-cb7f-47f1-9243-25d7152c3a1e'),

(gen_random_uuid(), 'Prime Tennis League', 'Лига профессионального тенниса', 'tennis', 'solo', 'swiss',
 'openedRegistrationTournaments', 15, NOW() + interval '7.5 months', 75, 15, NOW() + interval '7 months', 'Самара',
 'c398d4f9-9092-4b4d-ad92-22c93d083dd3'),

(gen_random_uuid(), 'Champions Football League', 'Лига чемпионов по футболу', 'football', 'team', 'round_robin',
 'openedRegistrationTournaments', 20, NOW() + interval '8 months', 300, 12, NOW() + interval '7 months', 'Волгоград',
 '73dfa385-47c3-444c-85b8-6f91e2a42953'),

(gen_random_uuid(), 'Pro Basketball Circuit', 'Профессиональная баскетбольная лига', 'basketball', 'team', 'olympic',
 'openedRegistrationTournaments', 14, NOW() + interval '7.5 months', 200, 10, NOW() + interval '7 months', 'Челябинск',
 '39412174-1bf3-4bf6-951c-73d01055e5e3'),

(gen_random_uuid(), 'Martial Arts Masters', 'Турнир мастеров джиу-джитсу', 'jiu_jitsu', 'solo', 'olympic',
 'openedRegistrationTournaments', 8, NOW() + interval '8 months', 120, 8, NOW() + interval '7 months', 'Уфа',
 '1e1a4df7-8e47-452a-862f-a479a7c3c104'),

(gen_random_uuid(), 'Grand Chess Open', 'Открытый гран-при по шахматам', 'chess', 'solo', 'swiss',
 'openedRegistrationTournaments', 18, NOW() + interval '9 months', 50, 20, NOW() + interval '8 months', 'Красноярск',
 '00ec23f1-798f-432b-bc70-d9143df96cc7'),

(gen_random_uuid(), 'Thunder Boxing Cup', 'Кубок молодых боксеров', 'boxing', 'solo', 'olympic',
 'openedRegistrationTournaments', 10, NOW() + interval '8.5 months', 75, 10, NOW() + interval '7.5 months', 'Омск',
 'fb91ae36-10e1-4372-8918-941a24b3089a'),

(gen_random_uuid(), 'Elite Tennis Masters', 'Мастерский турнир по теннису', 'tennis', 'solo', 'round_robin',
 'openedRegistrationTournaments', 16, NOW() + interval '9 months', 100, 16, NOW() + interval '8 months', 'Пермь',
 '11a73a3b-c23f-4a4c-8684-e8dfa0dcebda'),

(gen_random_uuid(), 'Victory Football Tournament', 'Турнир победителей по футболу', 'football', 'team', 'swiss',
 'openedRegistrationTournaments', 12, NOW() + interval '8.5 months', 150, 8, NOW() + interval '7.5 months', 'Тула',
 'e947eff5-e1f0-402f-be91-f6bfd323cc27'),

(gen_random_uuid(), 'Summit Basketball Championship', 'Чемпионат высшей лиги по баскетболу', 'basketball', 'team',
 'olympic',
 'openedRegistrationTournaments', 18, NOW() + interval '9 months', 250, 12, NOW() + interval '8 months', 'Воронеж',
 '397915e5-c451-4a65-abde-ec99059b1eb0'),

(gen_random_uuid(), 'Warrior Jiu-Jitsu Cup', 'Кубок воинов джиу-джитсу', 'jiu_jitsu', 'team', 'round_robin',
 'openedRegistrationTournaments', 14, NOW() + interval '8.5 months', 180, 10, NOW() + interval '7.5 months', 'Тверь',
 'e950f563-461b-4a1b-925f-9dd165628833'),

(gen_random_uuid(), 'International Chess Festival', 'Международный шахматный фестиваль', 'chess', 'solo', 'olympic',
 'openedRegistrationTournaments', 24, NOW() + interval '10 months', 300, 32, NOW() + interval '9 months', 'Сочи',
 'fd448cde-534e-4731-a361-4ea7306dee6d');

-- === Регистрация игроков/команд на турниры ===
-- Игрок в solo-турнир
INSERT INTO tournament_registrations (tournament_id, sport, participant_id, participant_type, status)
SELECT t.id, t.sport, '11a73a3b-c23f-4a4c-8684-e8dfa0dcebda'::UUID, 'solo', 'accepted'
FROM tournaments t
WHERE title = 'Blitz Chess Championship';

-- Команда в командный турнир
INSERT INTO tournament_registrations (tournament_id, sport, participant_id, participant_type, status)
SELECT t.id, t.sport, tm.id, 'team', 'accepted'
FROM tournaments t
         JOIN teams tm ON tm.name = 'Neon Wolves'
WHERE t.title = 'Street Football Championship';

-- Дополнительные регистрации
INSERT INTO tournament_registrations (tournament_id, sport, participant_id, participant_type, status)
SELECT t.id, t.sport, 'e947eff5-e1f0-402f-be91-f6bfd323cc27'::UUID, 'solo', 'accepted'
FROM tournaments t
WHERE title = 'Urban Boxing League';

INSERT INTO tournament_registrations (tournament_id, sport, participant_id, participant_type, status)
SELECT t.id, t.sport, tm.id, 'team', 'accepted'
FROM tournaments t
         JOIN teams tm ON tm.name = 'Cyber Phoenix'
WHERE t.title = 'Urban Basketball League 3x3';

INSERT INTO tournament_registrations (tournament_id, sport, participant_id, participant_type, status)
SELECT t.id, t.sport, '397915e5-c451-4a65-abde-ec99059b1eb0'::UUID, 'solo', 'accepted'
FROM tournaments t
WHERE title = 'Metropolitan Tennis Open';

-- === Рейтинги команд ===
INSERT INTO team_mmr_relation (team_id, mmr)
SELECT id, (1000 + random() * 500)::INTEGER
FROM teams;
