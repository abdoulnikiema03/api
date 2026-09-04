package com.example.api.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Structure JSON uniforme renvoyée au client en cas d'erreur.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fieldErrors
) {
    public ErrorResponse(int status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, null);
    }

    public ErrorResponse(int status, String error, String message, Map<String, String> fieldErrors) {
        this(LocalDateTime.now(), status, error, message, fieldErrors);
    }
}