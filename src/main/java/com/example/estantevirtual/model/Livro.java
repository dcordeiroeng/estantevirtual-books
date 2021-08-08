package com.example.estantevirtual.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 2, max = 100)
    private String titulo;
    @Length(min = 2, max = 50)
    private String autor;
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
