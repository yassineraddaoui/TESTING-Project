package com.ala.book.handler;

import com.ala.book.exception.OperationNotPerimttedException;
import jakarta.mail.MessagingException;
import jakarta.persistence.PersistenceException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exception){
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BusinessError.ACCOUNT_LOKCED.getCode())
                        .error(exception.getMessage())
                        .businessErrorDescription(BusinessError.ACCOUNT_LOKCED.getDescription())
                        .build());

    }
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exception){
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BusinessError.ACCOUNT_DISABLED.getCode())
                        .error(exception.getMessage())
                        .businessErrorDescription(BusinessError.ACCOUNT_DISABLED.getDescription())
                        .build());

    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exception){
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BusinessError.BAD_CREDENTIALS.getCode())
                        .error(exception.getMessage())
                        .businessErrorDescription(BusinessError.BAD_CREDENTIALS.getDescription())
                        .build());

    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .error(exception.getMessage())
                        .build());

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .error(exception.getMessage())
                        .businessErrorDescription("Internal error, contact the admin")
                        .build());

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception){
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error-> {
                    var message = error.getDefaultMessage();
                    errors.add(message);
                }
                );
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse
                        .builder()
                        .validationErrors(errors)
                        .build()
        );


    }
    @ExceptionHandler(OperationNotPerimttedException.class)
    public ResponseEntity<ExceptionResponse> handle(OperationNotPerimttedException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .error(exception.getMessage())
                        .build());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleException(DataIntegrityViolationException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BusinessError.PERSiSTENCE_EXCEPTION.getCode())
                        .businessErrorDescription(BusinessError.PERSiSTENCE_EXCEPTION.getDescription())
                        .error(exception.getMessage())
                        .build());
    }
}
