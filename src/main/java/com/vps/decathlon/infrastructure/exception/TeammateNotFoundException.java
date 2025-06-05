package com.vps.decathlon.infrastructure.exception;

public class TeammateNotFoundException extends RuntimeException {
    public TeammateNotFoundException(String message) {

        super(message);
    }
}
