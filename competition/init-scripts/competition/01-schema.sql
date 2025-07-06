-- Создание типов
CREATE TYPE sport AS ENUM ('football', 'boxing', 'basketball', 'chess', 'tennis', 'jiu_jitsu');
CREATE TYPE type_tournament AS ENUM ('solo', 'team');
CREATE TYPE type_group AS ENUM ('olympic', 'swiss', 'round_robin');
CREATE TYPE participant_type AS ENUM ('solo', 'team');

-- Создание таблиц
CREATE TABLE teams
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    avatar     VARCHAR(500)
);

CREATE TABLE tournaments
(
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title                 VARCHAR(255)    NOT NULL,
    description           TEXT,
    sport                 sport           NOT NULL,
    type_tournament       type_tournament NOT NULL,
    type_group            type_group      NOT NULL,
    matches_number        INTEGER          DEFAULT 0,
    start_time            TIMESTAMP,
    created_at            TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    entry_cost            DECIMAL(10, 2)   DEFAULT 0,
    max_participants      INTEGER          DEFAULT 0,
    registration_deadline TIMESTAMP,
    place                 VARCHAR(255),
    organizer_id          UUID            NOT NULL -- Ссылка на user_service
);

CREATE TABLE tournament_registrations
(
    tournament_id    UUID             NOT NULL REFERENCES tournaments (id) ON DELETE CASCADE,
    sport            sport            NOT NULL,
    participant_id   UUID             NOT NULL, -- Ссылка на user_service или teams
    participant_type participant_type NOT NULL,
    registered_at    TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    status           VARCHAR(50) DEFAULT 'active',
    PRIMARY KEY (tournament_id, participant_id, participant_type)
);

CREATE TABLE team_player_relation
(
    team_id UUID NOT NULL REFERENCES teams (id) ON DELETE CASCADE,
    user_id UUID NOT NULL, -- Ссылка на user_service
    PRIMARY KEY (team_id, user_id)
);

CREATE TABLE team_mmr_relation
(
    team_id UUID PRIMARY KEY REFERENCES teams (id) ON DELETE CASCADE,
    mmr     DOUBLE PRECISION DEFAULT 1000.0
);

-- Создание индексов для производительности
CREATE INDEX idx_tournaments_sport ON tournaments (sport);
CREATE INDEX idx_tournaments_organizer ON tournaments (organizer_id);
CREATE INDEX idx_tournament_registrations_participant ON tournament_registrations (participant_id);
CREATE INDEX idx_team_player_relation_user ON team_player_relation (user_id);
