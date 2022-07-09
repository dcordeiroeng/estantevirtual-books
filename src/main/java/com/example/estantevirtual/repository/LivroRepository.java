package com.example.estantevirtual.repository;

import com.example.estantevirtual.model.Livro;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Override
    @NonNull Page<Livro> findAll(@NonNull Pageable pageable);
}
