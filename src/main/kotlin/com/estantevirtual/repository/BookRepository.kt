package com.estantevirtual.repository

import com.estantevirtual.model.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface BookRepository : CrudRepository<Book?, Long?> {
    fun findAll(pageable: Pageable): Page<Book?>
    fun findById(id: UUID?): Optional<Book?>

    @Transactional
    fun deleteById(id: UUID?)
    fun findByIsbn(isbn: String?): Optional<Book?>
}
