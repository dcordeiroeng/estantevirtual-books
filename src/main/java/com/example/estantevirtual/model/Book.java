package com.example.estantevirtual.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Book {

    @Id
    private UUID id;
    @Length(min = 3, max = 100, message = "O título do livro deve ter entre {min} e {max} caracteres")
    private String title;
    @Length(min = 3, max = 35, message = "O nome do autor deve ter entre {min} e {max} caracteres")
    private String author;
    @NotNull(message = "Necessário informar o número de páginas")
    @Min(value = 1, message = "Número de páginas deve ser maior que 0")
    private Integer pages;
}
