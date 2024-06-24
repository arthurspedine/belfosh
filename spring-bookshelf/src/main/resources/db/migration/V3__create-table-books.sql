CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    published_date DATE NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    summary VARCHAR(2000) NOT NULL,
    total_pages SMALLINT NOT NULL,
    author_id BIGINT NOT NULL,
    poster_url VARCHAR(255) NOT NULL,
    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES authors (id)
);