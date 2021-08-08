package com.example.estantevirtual.exception;

import com.example.estantevirtual.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFound(ResourceNotFoundException ex, WebRequest req) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
    }
}
