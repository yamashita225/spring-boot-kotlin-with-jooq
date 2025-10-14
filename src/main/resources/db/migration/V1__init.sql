CREATE SCHEMA IF NOT EXISTS PUBLIC;

-- 書籍テーブル
CREATE TABLE PUBLIC.book (
    book_id         SERIAL PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    price           NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    publish_status  VARCHAR(20) NOT NULL CHECK (publish_status IN ('UNPUBLISHED', 'PUBLISHED')),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 著者テーブル
CREATE TABLE PUBLIC.author (
    author_id       SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    birth_date      DATE NOT NULL CHECK (birth_date < CURRENT_DATE),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 書籍と著者の中間テーブル（多対多関係）
CREATE TABLE PUBLIC.book_author (
    book_id         INT NOT NULL,
    author_id       INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book (book_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE
);
