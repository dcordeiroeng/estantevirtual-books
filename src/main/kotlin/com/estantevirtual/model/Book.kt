package com.estantevirtual.model

import lombok.Getter
import lombok.Setter
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Getter
@Setter
class Book(
    isbn: String,
    title: String,
    author: String,
    pages: Int
) : Serializable {

    @Id
    @Column(name = "isbn")
    var isbn: String? = isbn

    @NotNull(message = "Title is required")
    @Length(min = 3, max = 100, message = "Book title must be between {min} and {max} characters")
    var title: String? = title

    @NotNull(message = "Author is required")
    @Length(min = 3, max = 35, message = "Author name must be between {min} and {max} characters")
    var author: String? = author

    @NotNull(message = "Number of pages are required")
    @Min(value = 1, message = "Number of pages must be greater than 0")
    var pages: Int? = pages
}
