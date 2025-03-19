DROP DATABASE IF EXISTS scheduler;
CREATE DATABASE scheduler;

USE scheduler;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    created_at timestamp,
    updated_at timestamp
);

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    password VARCHAR(50) NOT NULL,
    task TEXT NOT NULL,
    created_at timestamp,
    updated_at timestamp
);
