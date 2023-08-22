package com.estantevirtual.books.model

import lombok.Data
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Data
data class Book(
    @Id
    @Column(name = "isbn")
    var isbn: String,
    @field:NotNull(message = "Title is required")
    var title: String,
    @field:NotNull(message = "Number of pages are required")
    @field:Min(value = 1, message = "Number of pages must be greater than 0")
    var pages: Int
) : Serializable
