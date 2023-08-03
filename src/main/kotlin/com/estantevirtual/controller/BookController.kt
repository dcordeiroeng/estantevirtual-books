package com.estantevirtual.controller

import com.estantevirtual.exception.ResourceNotFoundException
import com.estantevirtual.model.Book
import com.estantevirtual.model.Books
import com.estantevirtual.model.Options
import com.estantevirtual.service.BookService
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/books")
class BookController(
    private val bookService: BookService,
    private val logger: Logger
) {
    @GetMapping("/{isbn}")
    fun findBook(@PathVariable isbn: String?): ResponseEntity<*> {
        val book = bookService.findBookByIsbn(isbn)?.orElse(null)
        book ?: run {
            logger.error("Not found isbn: $isbn")
            throw ResourceNotFoundException()
        }
        return ResponseEntity(book, HttpStatus.OK)
    }

    @GetMapping
    fun findBooks(
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "per_page", defaultValue = "10", required = false) perPage: Int,
        @RequestParam(value = "order_by", defaultValue = "id", required = false) orderBy: String?,
        @RequestParam(value = "sort", defaultValue = "ASC", required = false) sort: String?
    ): ResponseEntity<Books> {
        val books = bookService.findBooks(Options(page, perPage, orderBy, sort))
        return ResponseEntity(books, HttpStatus.OK)
    }

    @PostMapping
    fun saveBook(@RequestBody book: @Valid Book?): ResponseEntity<*> {
        bookService.saveBook(book)
        return ResponseEntity<Any>(book, HttpStatus.CREATED)
    }

    @PutMapping("/{isbn}")
    fun updateBook(@PathVariable isbn: String, @RequestBody book: Book?): ResponseEntity<*> {
        return if (bookService.updateBook(isbn, book)) {
            book?.isbn = isbn
            ResponseEntity(book, HttpStatus.OK)
        } else {
            book?.isbn = isbn
            bookService.saveBook(book)
            ResponseEntity(book, HttpStatus.CREATED)
        }
    }

    @DeleteMapping("/{isbn}")
    fun deleteBook(@PathVariable isbn: String?): ResponseEntity<*> {
        return if (bookService.deleteBook(isbn)) {
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        } else {
            logger.error("Not found isbn: $isbn")
            throw ResourceNotFoundException()
        }
    }
}
