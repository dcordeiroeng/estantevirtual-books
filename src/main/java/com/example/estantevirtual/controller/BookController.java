package com.example.estantevirtual.controller;

import com.example.estantevirtual.exception.ResourceNotFoundException;
import com.example.estantevirtual.model.Book;
import com.example.estantevirtual.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController("/v1")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books/{id}")
    public ResponseEntity<?> buscaLivro(@PathVariable Long id) {
        Optional<Book> livro = bookService.findBookBy(id);
        if(livro.isEmpty()) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        return new ResponseEntity<>(livro.get(), HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<?> buscaLivros(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit
    ) {
        Page<Book> livros = bookService.findBooks(page, limit);
        if(livros.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(livros.getContent(), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> cadastraLivro(@Valid @RequestBody Book book) {
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deletaLivro(@PathVariable Long id) {
        if(bookService.deleteBook(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Recurso não encontrado");
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> atualizaLivro(@PathVariable Long id, @RequestBody Book book) {
        if(bookService.updateBook(id, book)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
