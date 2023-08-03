package com.estantevirtual.service

import com.estantevirtual.exception.IsbnException
import com.estantevirtual.exception.ResourceAlreadyExistsException
import com.estantevirtual.model.Book
import com.estantevirtual.model.Books
import com.estantevirtual.model.Options
import com.estantevirtual.repository.BookRepository
import com.estantevirtual.utils.RedisCacheEvict
import com.estantevirtual.validator.IsbnValidator
import com.estantevirtual.validator.OptionsValidator
import org.slf4j.Logger
import org.springframework.beans.BeanUtils
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val redisCacheEvict: RedisCacheEvict,
    private val logger: Logger,
    private val isbnValidator: IsbnValidator,
    private val optionsValidator: OptionsValidator
) {
    @Cacheable(value = ["books"])
    fun findBookByIsbn(isbn: String?): Optional<Book?>? {
        return bookRepository.findByIsbn(isbn)
    }

    @Cacheable(value = ["books"])
    fun findBooks(options: Options): Books {
        optionsValidator.validateParams(options)
        val books = bookRepository.findAll(
            PageRequest.of(
                options.page,
                options.pageSize,
                Sort.Direction.valueOf(options.sort!!),
                options.orderBy
            )
        )

        val nextPage = if (books.hasNext()) {
            buildPageUrl(options.page + 1, UriComponentsBuilder.fromPath("/v1/books"), options)
        } else null

        val prevPage = if (books.hasPrevious() && books.hasContent()) {
            buildPageUrl(options.page - 1, UriComponentsBuilder.fromPath("/v1/books"), options)
        } else null

        return Books(
            books,
            books.totalElements,
            options.page,
            options.pageSize,
            nextPage,
            prevPage
        )
    }

    fun saveBook(book: Book?) {
        book ?: throw IllegalArgumentException("Book cannot be null")
        if (isbnValidator.isValid(book.isbn)) {
            val response = bookRepository.findByIsbn(book.isbn)
            if (response.isPresent) {
                logger.error(("Book with isbn: ${book.isbn} already exists"))
                throw ResourceAlreadyExistsException("Resource already exists")
            }
            bookRepository.save(book)
            logger.info("Created isbn: ${book.isbn} ")
            redisCacheEvict.evictAllCaches()
        } else {
            throw IsbnException()
        }
    }

    fun updateBook(isbn: String?, book: Book?): Boolean {
        book ?: throw IllegalArgumentException("Book cannot be null")
        val existingBook = bookRepository.findByIsbn(isbn)
        if (existingBook.isPresent) {
            val bookToUpdate = existingBook.get()
            BeanUtils.copyProperties(book, bookToUpdate, "isbn")
            bookRepository.save(bookToUpdate)
            logger.info("Updated isbn: $isbn")
            redisCacheEvict.evictAllCaches()
            return true
        }
        return false
    }

    fun deleteBook(isbn: String?): Boolean {
        if (bookRepository.findByIsbn(isbn).isPresent) {
            bookRepository.deleteById(isbn)
            logger.info("Deleted isbn: $isbn")
            redisCacheEvict.evictAllCaches()
            return true
        }
        return false
    }

    private fun buildPageUrl(page: Int, uriBuilder: UriComponentsBuilder, options: Options): String {
        return uriBuilder.replaceQueryParam("page", page)
            .queryParam("per_page", options.pageSize)
            .queryParam("order_by", options.orderBy)
            .queryParam("sort", options.sort)
            .toUriString()
    }
}