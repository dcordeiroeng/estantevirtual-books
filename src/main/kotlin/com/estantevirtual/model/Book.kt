package com.estantevirtual.model

import lombok.Getter
import lombok.Setter
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Getter
@Setter
class Book : Serializable {

    @Id
    var id: UUID? = null

    @NotNull(message = "ISBN is required")
    var isbn: String? = null

    @NotNull(message = "Title is required")
    @Length(min = 3, max = 100, message = "Book title must be between {min} and {max} characters")
    var title: String? = null

    @NotNull(message = "Author is required")
    @Length(min = 3, max = 35, message = "Author name must be between {min} and {max} characters")
    var author: String? = null

    @NotNull(message = "Number of pages is required")
    @Min(value = 1, message = "Number of pages must be greater than 0")
    var pages: Int? = null
}
