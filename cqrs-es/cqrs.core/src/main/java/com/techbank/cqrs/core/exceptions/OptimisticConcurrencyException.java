package com.techbank.cqrs.core.exceptions;

public class OptimisticConcurrencyException extends RuntimeException {

    public OptimisticConcurrencyException(String message) {
        super(message);
    }
}
