package com.example.estantevirtual.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Book implements Serializable {

    @Id
    private UUID id;

    @NotNull(message = "Title is required")
    @Length(min = 3, max = 100, message = "Book title must be between {min} and {max} characters")
    private String title;

    @NotNull(message = "Author is required")
    @Length(min = 3, max = 35, message = "Author name must be between {min} and {max} characters")
    private String author;

    @NotNull(message = "Number of pages is required")
    @Min(value = 1, message = "Number of pages must be greater than 0")
    private Integer pages;
}
