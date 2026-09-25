package com.ecol.authService.exception.keyclockAuthenticationException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class KeyclockAuthenticationException extends RuntimeException {

    private final HttpStatus status;

    public KeyclockAuthenticationException(String message) {
        this(message, HttpStatus.UNAUTHORIZED, null);
    }

    public KeyclockAuthenticationException(String message, Throwable cause) {
        this(message, HttpStatus.UNAUTHORIZED, cause);
    }

    public KeyclockAuthenticationException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public static KeyclockAuthenticationException unavailable(Throwable cause) {
        return new KeyclockAuthenticationException(
                "Authentication server is unavailable. Please try again shortly.",
                HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
}
