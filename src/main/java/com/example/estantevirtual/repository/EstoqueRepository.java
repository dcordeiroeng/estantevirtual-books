package com.example.estantevirtual.repository;

import com.example.estantevirtual.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Livro, Long> {

}
