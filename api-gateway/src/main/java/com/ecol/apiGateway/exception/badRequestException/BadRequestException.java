package com.ecol.apiGateway.exception.badRequestException;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable t) {
        super(message, t);
    }
    public BadRequestException(Throwable t) {
        super(t);
    }
}
