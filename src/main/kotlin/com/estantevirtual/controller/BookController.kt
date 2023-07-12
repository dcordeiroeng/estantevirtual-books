package com.estantevirtual.controller

import com.estantevirtual.exception.ResourceNotFoundException
import com.estantevirtual.model.Book
import com.estantevirtual.model.Options
import com.estantevirtual.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1")
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/books/{id}")
    fun findBook(@PathVariable id: UUID?): ResponseEntity<*> {
        val book = bookService.findBookBy(id)
        if (book?.isEmpty == true) {
            throw ResourceNotFoundException()
        }
        return ResponseEntity(book, HttpStatus.OK)
    }

    @GetMapping("/books")
    fun findBooks(
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) pageSize: Int,
        @RequestParam(value = "orderBy", defaultValue = "isbn", required = false) orderBy: String?,
        @RequestParam(value = "sort", defaultValue = "ASC", required = false) sort: String?
    ): ResponseEntity<*> {
        val options = Options(page, pageSize, orderBy, sort)
        val books = bookService.findBooks(options)
        return ResponseEntity(
            bookService.responseBuilder(books),
            HttpStatus.OK
        )
    }

    @PostMapping("/books")
    fun saveBook(@RequestBody book: @Valid Book?): ResponseEntity<*> {
        if (book != null) {
            bookService.saveBook(book)
        }
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @DeleteMapping("/books/{id}")
    fun deleteBook(@PathVariable id: UUID?): ResponseEntity<*> {
        return if (bookService.deleteBook(id)) {
            ResponseEntity<Any>(HttpStatus.OK)
        } else {
            throw ResourceNotFoundException()
        }
    }

    @PutMapping("/books/{id}")
    fun updateBook(@PathVariable id: UUID?, @RequestBody book: Book?): ResponseEntity<*> {
        return if (bookService.updateBook(id, book)) {
            ResponseEntity<Any>(HttpStatus.OK)
        } else {
            throw ResourceNotFoundException()
        }
    }
}
