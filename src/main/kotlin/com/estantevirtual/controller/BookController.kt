package com.estantevirtual.controller

import com.estantevirtual.exception.ResourceNotFoundException
import com.estantevirtual.model.Book
import com.estantevirtual.model.Options
import com.estantevirtual.service.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/books")
class BookController(
    private val bookService: BookService,
    private val logger: Logger,
    private val objectMapper: ObjectMapper
) {
    @GetMapping("/{id}")
    fun findBook(@PathVariable id: UUID?): ResponseEntity<*> {
        val book = bookService.findBookBy(id)
        if (book?.isEmpty == true) {
            logger.error("Not found id: $id")
            throw ResourceNotFoundException()
        }
        logAsJson(book!!.get())
        return ResponseEntity(book, HttpStatus.OK)
    }

    @GetMapping
    fun findBooks(
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) pageSize: Int,
        @RequestParam(value = "orderBy", defaultValue = "isbn", required = false) orderBy: String?,
        @RequestParam(value = "sort", defaultValue = "ASC", required = false) sort: String?
    ): ResponseEntity<Map<String, Any>> {
        val books = bookService.findBooks(Options(page, pageSize, orderBy, sort))
        val data = responseBuilder(books)
        data.forEach {
            logAsJson(it)
        }
        return ResponseEntity(data, HttpStatus.OK)
    }

    @PostMapping
    fun saveBook(@RequestBody book: @Valid Book?): ResponseEntity<*> {
        if (book != null) {
            bookService.saveBook(book)
        }
        return ResponseEntity<Any>(book, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: UUID?, @RequestBody book: Book?): ResponseEntity<*> {
        return if (bookService.updateBook(id, book)) {
            ResponseEntity<Any>(book, HttpStatus.OK)
        } else {
            saveBook(book)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: UUID?): ResponseEntity<*> {
        return if (bookService.deleteBook(id)) {
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        } else {
            logger.error("Not found id: $id")
            throw ResourceNotFoundException()
        }
    }

    private fun logAsJson(obj: Any) {
        val json = objectMapper.writeValueAsString(obj)
        logger.info(json)
    }

    private fun responseBuilder(book: Page<Book?>): Map<String, Any> {
        val books = LinkedHashMap<String, Any>()
        books["books"] = book.content
        books["totalItems"] = book.totalElements
        books["currentPage"] = book.number
        books["totalPages"] = book.totalPages
        val data = LinkedHashMap<String, Any>()
        data["data"] = books
        return data
    }
}
