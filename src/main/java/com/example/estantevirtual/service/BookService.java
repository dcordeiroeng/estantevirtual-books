package com.example.estantevirtual.service;

import com.example.estantevirtual.model.Book;
import com.example.estantevirtual.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Optional<Book> findBookBy(UUID id) {
        return bookRepository.findById(id);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public Page<Book> findBooks(int page, int limit) {
        return bookRepository.findAll(PageRequest.of(page, limit, Sort.by("id")));
    }

    public boolean deleteBook(UUID id) {
        if(bookRepository.findById(id).isPresent()) {
            System.out.println("entrou");
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean updateBook(UUID id, Book book) {
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if(bookToUpdate.isPresent()) {
            bookToUpdate.get().setTitle(book.getTitle());
            bookToUpdate.get().setAuthor(book.getAuthor());
            bookRepository.save(bookToUpdate.get());
            return true;
        }
        return false;
    }
}
