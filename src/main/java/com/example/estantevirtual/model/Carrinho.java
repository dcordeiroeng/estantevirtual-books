package com.example.estantevirtual.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "livros")
    private Livro livros;
    private StatusCompra status;
}
