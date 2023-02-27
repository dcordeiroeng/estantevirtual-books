package com.example.estantevirtual.service;

import com.example.estantevirtual.model.Book;
import com.example.estantevirtual.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    LivroRepository livroRepository;

    public Optional<Book> findBookBy(Long id) {
        if(livroRepository.findById(id).isPresent()) {
            return livroRepository.findById(id);
        }
        return Optional.empty();
    }

    public void saveBook(Book book) {
        livroRepository.save(book);
    }

    public Page<Book> findBooks(int page, int limit) {
        return livroRepository.findAll(PageRequest.of(page, limit, Sort.by("id")));
    }

    public boolean deleteBook(Long id) {
        if(livroRepository.findById(id).isPresent()) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean updateBook(Long id, Book book) {
        Optional<Book> bookToUpdate = livroRepository.findById(id);
        if(bookToUpdate.isPresent()) {
            bookToUpdate.get().setTitle(book.getTitle());
            bookToUpdate.get().setAuthor(book.getAuthor());
            livroRepository.save(bookToUpdate.get());
            return true;
        }
        return false;
    }
}
