package com.example.estantevirtual.repository;

import com.example.estantevirtual.model.Book;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @NonNull Page<Book> findAll(@NonNull Pageable pageable);

    Optional<Book> findById(UUID id);

    @Transactional
    void deleteById(UUID id);
}
