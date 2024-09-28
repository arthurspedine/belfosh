package br.com.spedine.bookshelf.infra.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String msg) {
        super(msg);
    }
}
