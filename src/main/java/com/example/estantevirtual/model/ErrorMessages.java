package com.example.estantevirtual.model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorMessages {

    private final HttpStatus status;
    private final LocalDateTime date = LocalDateTime.now();
    private final Map<String, String> errors;

    public ErrorMessages(HttpStatus status, Map<String, String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
