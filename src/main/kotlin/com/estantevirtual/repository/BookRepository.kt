package com.estantevirtual.repository

import com.estantevirtual.model.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface BookRepository : CrudRepository<Book, String> {
    @Query(
        value = "SELECT CONCAT(isbn) as id, * FROM Book",
        countQuery = "SELECT count(*) FROM Book",
        nativeQuery = true
    )
    fun findAll(pageable: Pageable): Page<Book?>

    fun findByIsbn(isbn: String?): Optional<Book?>

    @Transactional
    fun deleteById(id: String?)
}
