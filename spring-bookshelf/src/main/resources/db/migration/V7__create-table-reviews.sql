CREATE TABLE reviews
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT           NOT NULL,
    book_id     BIGINT           NOT NULL,
    description VARCHAR(2000)    NOT NULL,
    rating      DOUBLE PRECISION NOT NULL,
    review_date DATE             NOT NULL,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES books (id)
);
