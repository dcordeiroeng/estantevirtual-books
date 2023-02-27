package com.example.estantevirtual.repository;

import com.example.estantevirtual.model.Book;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Book, Long> {

    @Override
    @NonNull Page<Book> findAll(@NonNull Pageable pageable);
}
