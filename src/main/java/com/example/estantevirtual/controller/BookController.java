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
import java.util.UUID;

@RestController("/v1")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books/{id}")
    public ResponseEntity<?> findBook(@PathVariable UUID id) {
        Optional<Book> book = bookService.findBookBy(id);
        if(book.isEmpty()) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        return new ResponseEntity<>(book.get(), HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<?> findBooks(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit
    ) {
        Page<Book> books = bookService.findBooks(page, limit);
        return new ResponseEntity<>(bookService.responseBuilder(books), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> saveBook(@Valid @RequestBody Book book) {
        book.setId(UUID.randomUUID());
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        if(bookService.deleteBook(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Recurso não encontrado");
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable UUID id, @RequestBody Book book) {
        if(bookService.updateBook(id, book)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
