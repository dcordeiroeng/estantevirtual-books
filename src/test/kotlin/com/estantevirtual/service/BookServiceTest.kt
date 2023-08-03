package com.estantevirtual.controller

import com.estantevirtual.model.Book
import com.estantevirtual.service.BookService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk

class BookServiceTest : StringSpec() {

    private val logger: org.slf4j.Logger = mockk()
    private val bookService: BookService = mockk()
    private val bookController = BookController(bookService, logger)

    init {
        "should throw IllegalArgumentException when book is null" {
            val book = Book("978-1408855662", "The Silo", "", 12)

            every { bookService.saveBook(book) } throws IllegalArgumentException()

            shouldThrow<IllegalArgumentException> {
                bookController.saveBook(book)
            }
        }

        "should throw IllegalArgumentException when book is null" {
            val book = Book("978-1408855662", "", "...", 12)

            every { bookService.saveBook(book) } throws IllegalArgumentException()

            shouldThrow<IllegalArgumentException> {
                bookController.saveBook(book)
            }
        }

        "should throw IllegalArgumentException when book is null" {
            val book = Book("978-1408855662", "The Silo", "The Silo", 0)

            every { bookService.saveBook(book) } throws IllegalArgumentException()

            shouldThrow<IllegalArgumentException> {
                bookController.saveBook(book)
            }
        }
    }
}
