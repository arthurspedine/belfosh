package br.com.spedine.bookshelf.infra.exception;

public class BookAlreadyOnShelf extends RuntimeException {
    public BookAlreadyOnShelf(String msg) {
        super(msg);
    }
}
