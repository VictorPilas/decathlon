package com.vps.decathlon.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TeammateNotFoundException.class)
    public ResponseEntity<?> handleNotFound(TeammateNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error("Teammate Not Found", ex.getMessage()));
    }

    @ExceptionHandler(TeammateAlreadyExistsException.class)
    public ResponseEntity<?> handleConflict(TeammateAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error("Conflict", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error("Unexpected Error", ex.getMessage()));
    }

    private Map<String, Object> error(String title, String message) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "error", title,
                "message", message
        );
    }
}
