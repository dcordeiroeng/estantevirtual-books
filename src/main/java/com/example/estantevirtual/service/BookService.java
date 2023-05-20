package com.example.estantevirtual.service;

import com.example.estantevirtual.exception.ResourceAlreadyExistsException;
import com.example.estantevirtual.model.Book;
import com.example.estantevirtual.model.Options;
import com.example.estantevirtual.repository.BookRepository;
import com.example.estantevirtual.utils.RedisCacheEvictor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RedisCacheEvictor redisCacheEvictor;

    @Cacheable(value = "books")
    public Optional<Book> findBookBy(UUID id) {
        return bookRepository.findById(id);
    }

    public void saveBook(Book book) {
        if (bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor()).isPresent()) {
            throw new ResourceAlreadyExistsException("Livro já cadastrado");
        }
        redisCacheEvictor.evictAllCaches();
        book.setId(UUID.randomUUID());
        bookRepository.save(book);
    }

    @Cacheable(value = "books")
    public Page<Book> findBooks(Options options) {
        validateParams(options);
        return bookRepository.findAll(PageRequest.of(
                options.getPage(),
                options.getPageSize(),
                Sort.Direction.valueOf(options.getSort()),
                options.getOrderBy()
        ));
    }

    public boolean deleteBook(UUID id) {
        if (bookRepository.findById(id).isPresent()) {
            redisCacheEvictor.evictAllCaches();
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean updateBook(UUID id, Book book) {
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.isPresent()) {
            bookToUpdate.get().setTitle(book.getTitle());
            bookToUpdate.get().setAuthor(book.getAuthor());
            redisCacheEvictor.evictAllCaches();
            bookRepository.save(bookToUpdate.get());
            return true;
        }
        return false;
    }

    public Map<String, Object> responseBuilder(Page<Book> book) {
        Map<String, Object> books = new LinkedHashMap<>();
        books.put("books", book.getContent());
        books.put("totalItems", book.getTotalElements());
        books.put("currentPage", book.getNumber());
        books.put("totalPages", book.getTotalPages());

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("data", books);
        return data;
    }

    private void validateParams(Options options) {
        if (options.getPage() < 0) {
            throw new IllegalArgumentException("O parâmetro page deve ser maior ou igual a zero");
        }
        if (options.getPageSize() < 0) {
            throw new IllegalArgumentException("O parâmetro pageSize deve ser maior ou igual a zero");
        }
        if (!options.getSort().equals(Sort.Direction.ASC.toString()) && !options.getSort().equals(Sort.Direction.DESC.toString())) {
            throw new IllegalArgumentException("O parâmetro sort deve ser ASC ou DESC");
        }
        if (!options.getOrderBy().equals("id") && !options.getOrderBy().equals("title") && !options.getOrderBy().equals("author")) {
            throw new IllegalArgumentException("O parâmetro orderBy deve ser id, title ou author");
        }
    }
}
