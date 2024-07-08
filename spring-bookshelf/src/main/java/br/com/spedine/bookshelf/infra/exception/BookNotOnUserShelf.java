package br.com.spedine.bookshelf.infra.exception;

public class BookNotOnUserShelf extends RuntimeException {
    public BookNotOnUserShelf(String msg) {
        super(msg);
    }
}
