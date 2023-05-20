package com.example.estantevirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EstanteVirtualApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstanteVirtualApplication.class, args);
    }

}
