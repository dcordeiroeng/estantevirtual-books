package com.estantevirtual.service

import com.estantevirtual.exception.ResourceAlreadyExistsException
import com.estantevirtual.model.Book
import com.estantevirtual.model.Options
import com.estantevirtual.repository.BookRepository
import com.estantevirtual.utils.RedisCacheEvict
import org.slf4j.Logger
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val redisCacheEvict: RedisCacheEvict,
    private val logger: Logger
) {
    @Cacheable(value = ["books"])
    fun findBookBy(id: UUID?): Optional<Book?>? {
        return bookRepository.findById(id)
    }

    @Cacheable(value = ["books"])
    fun findBooks(options: Options): Page<Book?> {
        validateParams(options)
        val page = PageRequest.of(
            options.page,
            options.pageSize,
            Sort.Direction.valueOf(options.sort!!),
            options.orderBy
        )
        return bookRepository.findAll(page)
    }

    fun saveBook(book: Book) {
        val response = bookRepository.findByIsbn(book.isbn)
        if (response.isPresent) {
            logger.error(("Book with ISBN: ${book.isbn} already exists"))
            throw ResourceAlreadyExistsException("Resource already exists")
        }
        book.id = UUID.randomUUID()
        bookRepository.save(book)
        logger.info("Created id: ${book.id} ")
        redisCacheEvict.evictAllCaches()
    }

    fun deleteBook(id: UUID?): Boolean {
        if (bookRepository.findById(id).isPresent) {
            bookRepository.deleteById(id)
            logger.info("Deleted id: $id")
            redisCacheEvict.evictAllCaches()
            return true
        }
        return false
    }

    fun updateBook(id: UUID?, book: Book?): Boolean {
        val newBook = bookRepository.findById(id)
        if (newBook.isPresent) {
            if (book != null) {
                newBook.get().author = book.author
                newBook.get().title = book.title
                newBook.get().pages = book.pages
                bookRepository.save(newBook.get())
                logger.info("Updated id: $id")
                redisCacheEvict.evictAllCaches()
                return true
            }
        }
        return false
    }

    private fun validateParams(options: Options) {
        require(options.page >= 0) { "Page must be equal or greater than 0" }
        require(options.pageSize > 0) { "Page size must be greater than 0" }
        require(!(options.sort != Sort.Direction.ASC.toString() && options.sort != Sort.Direction.DESC.toString())) { "Sort must be ASC or DESC" }
    }
}