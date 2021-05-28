package com.example.estantevirtual.controller;

import com.example.estantevirtual.model.Livro;
import com.example.estantevirtual.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/")
public class LivroController {

    @Autowired
    LivroService livroService;

    @GetMapping("/livro/{id}")
    public ResponseEntity<?> buscaLivro(@PathVariable Long id) {
        Optional<Livro> livro = livroService.buscaLivroPorId(id);
        if(livro.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livro.get(), HttpStatus.OK);
    }

    @GetMapping("/livros")
    public ResponseEntity<?> buscaLivros() {
        List<Livro> livros = livroService.buscarLivros();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @PostMapping("/livro")
    public ResponseEntity<?> cadastraLivro(@RequestBody Livro livro) {
        livroService.cadastrarLivro(livro);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/livro/{id}")
    public ResponseEntity<?> deletaLivro(@PathVariable Long id) {
        if(livroService.deletarLivro(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/livro/{id}")
    public ResponseEntity<?> atualizaLivro(@PathVariable Long id, @RequestBody Livro livro) {
        if(livroService.atualizarLivro(id, livro)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
