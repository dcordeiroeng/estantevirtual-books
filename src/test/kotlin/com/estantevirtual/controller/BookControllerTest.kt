package com.estantevirtual.controller

import com.estantevirtual.model.Book
import com.estantevirtual.service.BookService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BookControllerTest : StringSpec() {

    private val logger: org.slf4j.Logger = mockk()
    private val bookService: BookService = mockk()
    private val bookController = BookController(bookService, logger)

    init {
        "should save book and return created response" {
            val book = Book("978-1408855662", "The Silo", "Hugh Howey", 12)
            val expectedResponse = ResponseEntity<Any>(book, HttpStatus.CREATED)

            every { bookService.saveBook(any()) } returns Unit

            val actualResponse = bookController.saveBook(book)
            actualResponse shouldBe expectedResponse
        }
    }
}
