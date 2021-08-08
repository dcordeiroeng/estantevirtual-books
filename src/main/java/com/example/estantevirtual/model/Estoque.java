package com.example.estantevirtual.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "livros")
    private Livro estoque;
}
