CREATE TABLE artist (
    id VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

CREATE TABLE friend (
    id BIGINT NOT NULL UNIQUE PRIMARY KEY,
    user1id VARCHAR(255) NOT NULL,
    user2id VARCHAR(255) NOT NULL
);

CREATE TABLE friend_seq (
    next_val BIGINT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT
);

INSERT INTO friend_seq (next_val) VALUES (1);

CREATE TABLE history (
    id BIGINT NOT NULL UNIQUE PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    track1id VARCHAR(255),
    track2id VARCHAR(255),
    track3id VARCHAR(255),
    artist1id VARCHAR(255),
    artist2id VARCHAR(255),
    artist3id VARCHAR(255),
    idx VARCHAR(255)
);

CREATE TABLE history_seq (
    next_val BIGINT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT
);

INSERT INTO history_seq (next_val) VALUES (1);

CREATE TABLE track (
    id VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

CREATE TABLE user (
    id VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255)
);
