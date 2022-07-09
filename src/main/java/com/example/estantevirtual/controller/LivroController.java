package com.example.estantevirtual.controller;

import com.example.estantevirtual.exception.ResourceNotFoundException;
import com.example.estantevirtual.model.Livro;
import com.example.estantevirtual.service.LivroService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("/v1")
public class LivroController {

    @Autowired
    LivroService livroService;

    @GetMapping("/livros/{id}")
    public ResponseEntity<?> buscaLivro(@PathVariable Long id) {
        Optional<Livro> livro = livroService.buscaLivroPorId(id);
        if(livro.isEmpty()) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        return new ResponseEntity<>(livro.get(), HttpStatus.OK);
    }

    @GetMapping("/livros")
    public ResponseEntity<?> buscaLivros(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit
    ) {
        Page<Livro> livros = livroService.buscarLivros(page, limit);
        if(livros.isEmpty()) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        return new ResponseEntity<>(livros.getContent(), HttpStatus.OK);
    }

    @PostMapping("/livros")
    public ResponseEntity<?> cadastraLivro(@RequestBody Livro livro) {
        livroService.cadastrarLivro(livro);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<?> deletaLivro(@PathVariable Long id) {
        if(livroService.deletarLivro(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Recurso não encontrado");
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<?> atualizaLivro(@PathVariable Long id, @RequestBody Livro livro) {
        if(livroService.atualizarLivro(id, livro)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
