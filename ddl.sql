-- ENUM TYPES
CREATE TYPE sport AS ENUM ('football', 'boxing', 'basketball', 'chess', 'tennis', 'jiu jitsu');
CREATE TYPE type_tournament AS ENUM ('solo', 'team');
CREATE TYPE type_group AS ENUM ('olympic', 'swiss', 'round_robin');
CREATE TYPE participant_type AS ENUM ('solo', 'team');
CREATE TYPE registration_status AS ENUM ('pending', 'accepted', 'rejected');

-- USERS
CREATE TABLE users
(
    id              UUID PRIMARY KEY,
    name            VARCHAR,
    surname         VARCHAR,
    patronymic      VARCHAR,
    phone_number    VARCHAR,
    email           VARCHAR UNIQUE,
    hashed_password VARCHAR,
    is_admin        BOOLEAN,
    date_of_birth   DATE,
    age             INT,
    sex             CHAR(1),
    weight          FLOAT,
    height          FLOAT,
    created_at      TIMESTAMP,
    bio             TEXT,
    avatar_url      VARCHAR
);

-- TEAMS
CREATE TABLE teams
(
    id         UUID PRIMARY KEY,
    name       VARCHAR,
    created_at TIMESTAMP,
    avatar     VARCHAR
);

-- TEAM-PLAYER RELATION
CREATE TABLE team_player_relation
(
    team_id UUID NOT NULL REFERENCES teams (id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (team_id, user_id)
);

-- USER MMR BY SPORT
CREATE TABLE user_sport_mmr_relation
(
    user_id UUID  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    sport   sport NOT NULL,
    mmr     DOUBLE PRECISION,
    PRIMARY KEY (user_id, sport)
);

-- TEAM MMR
CREATE TABLE team_mmr_relation
(
    team_id UUID PRIMARY KEY REFERENCES teams (id) ON DELETE CASCADE,
    mmr     DOUBLE PRECISION
);

-- TOURNAMENTS
CREATE TABLE tournaments
(
    id                    UUID PRIMARY KEY,
    title                 VARCHAR,
    description           TEXT,
    sport                 sport,
    type_tournament       type_tournament,
    type_group            type_group,
    matches_number        INT,
    start_time            TIMESTAMP,
    created_at            TIMESTAMP,
    entry_cost            DECIMAL,
    max_participants      INT,
    registration_deadline TIMESTAMP,
    place                 VARCHAR,
    organizer_id          UUID REFERENCES users (id)
);

-- TOURNAMENT REGISTRATIONS
CREATE TABLE tournament_registrations
(
    tournament_id    UUID REFERENCES tournaments (id) ON DELETE CASCADE,
    sport            sport,
    participant_id   UUID,
    participant_type participant_type,
    registered_at    TIMESTAMP,
    status           registration_status,
    PRIMARY KEY (tournament_id, participant_id, participant_type)
);

-- MATCHES
CREATE TABLE matches
(
    match_id          UUID PRIMARY KEY,
    tournament_id     UUID REFERENCES tournaments (id) ON DELETE CASCADE,
    started_at        TIMESTAMP,
    created_at        TIMESTAMP,
    position          INT,
    partition1_id     UUID,
    partition2_id     UUID,
    partition1_points INT,
    partition2_points INT,
    winner_id         UUID
);
