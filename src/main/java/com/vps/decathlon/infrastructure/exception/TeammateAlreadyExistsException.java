package com.vps.decathlon.infrastructure.exception;

public class TeammateAlreadyExistsException extends RuntimeException {
    public TeammateAlreadyExistsException(String message) {
        super(message);
    }
}
