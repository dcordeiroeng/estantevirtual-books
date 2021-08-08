package com.example.estantevirtual.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Messages {

    private static Map<String, String> errors = new HashMap<>();

    static {
        errors.put("NAO_ENCONTRADO", "Recurso não encontrado");
        errors.put("NENHUM_CADASTRADO", "Nenhum livro cadastrado.");
    }

    public static Map<String, String> getErrors() {
        return errors;
    }
}
