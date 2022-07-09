package com.example.estantevirtual.model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {

    private final HttpStatus status;
    private final LocalDateTime date = LocalDateTime.now();
    private final String description;

    public ErrorMessage(HttpStatus status, String description) {
        this.status = status;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
