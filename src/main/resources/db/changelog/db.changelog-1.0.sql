--liquibase formatted sql

--changeset Maxim Kabanov:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(128) NOT NULL,
    birth_date date,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    role VARCHAR(8),
    UNIQUE(email,password)
);
--changeset Maxim Kabanov:2
CREATE TABLE IF NOT EXISTS user_friend
(
    id SERIAL PRIMARY KEY ,
    first_name VARCHAR(255) NOT NULL ,
    last_name VARCHAR(255) NOT NULL ,
    user_id BIGINT REFERENCES users
)