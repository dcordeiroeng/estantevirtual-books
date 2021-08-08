package com.example.estantevirtual.model;

import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {

    private HttpStatus status;
    private LocalDateTime date = LocalDateTime.now();
    private String description;

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
