package com.example.estantevirtual;

import org.springframework.http.HttpStatus;

public class DefaultException extends Exception {

    private String mensagem;
    private HttpStatus status;

}
