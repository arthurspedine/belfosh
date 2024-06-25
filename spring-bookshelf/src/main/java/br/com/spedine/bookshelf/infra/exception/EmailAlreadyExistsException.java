package br.com.spedine.bookshelf.infra.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String msg) {
        super(msg);
    }
}
