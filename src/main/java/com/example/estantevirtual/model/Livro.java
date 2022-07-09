package com.example.estantevirtual.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Título é obrigatório")
    @Length(min = 3, max = 100, message = "O título do livro deve ter entre {min} e {max} caracteres")
    private String titulo;
    @NotBlank(message = "Autor é obrigatório")
    @Length(min = 3, max = 35, message = "O nome do autor deve ter entre {min} e {max} caracteres")
    private String autor;
}
