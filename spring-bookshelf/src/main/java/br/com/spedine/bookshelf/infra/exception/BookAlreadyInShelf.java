package br.com.spedine.bookshelf.infra.exception;

public class BookAlreadyInShelf extends RuntimeException {
    public BookAlreadyInShelf(String msg) {
        super(msg);
    }
}
