CREATE TABLE users_books (
    user_id BIGINT,
    book_id BIGINT,
    PRIMARY KEY (user_id, book_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (id)
);
