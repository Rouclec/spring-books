package com.rouclec.databasejpa.services;

import com.rouclec.databasejpa.domain.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    public Book createUpdateBook(String isbn, Book book);

    Page<Book> getAll(Pageable pageable);
    List<Book> getAll();

    Optional<Book> get(String isbn);

    boolean isExists(String isbn);

    Book partialUpdate(String isbn, Book book);

    void delete(String isbn);
}
