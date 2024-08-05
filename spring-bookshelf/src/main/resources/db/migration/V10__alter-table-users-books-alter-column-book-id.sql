ALTER TABLE users_books ALTER COLUMN book_id TYPE VARCHAR(50);
ALTER TABLE users_books ADD CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (id);