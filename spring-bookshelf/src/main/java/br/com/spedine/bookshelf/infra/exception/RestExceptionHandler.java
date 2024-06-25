package br.com.spedine.bookshelf.infra.exception;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Stream;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionData> emailAlreadyExistis(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionData(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionData> validationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionData(e.getMessage()));
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ExceptionData> jwtCreationException(JWTCreationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionData(e.getMessage()));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ExceptionData> jwtVerificationException(JWTVerificationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionData(e.getMessage()));
    }

//    JAVA EXCEPTIONS

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionData> entityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionData(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Stream<FieldExceptionData>> argumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(FieldExceptionData::new));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionData> badCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionData("Invalid credentials!"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionData> authenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionData( "Invalid authentication! Check fields and try again!"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionData> accessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionData("Access denied!"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionData> exception(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionData(ex.getLocalizedMessage()));
    }

    public record ExceptionData(
            String error
    ) {
    }

    public record FieldExceptionData(
            String field,
            String message
    ) {
        public FieldExceptionData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}

