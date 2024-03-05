package com.rouclec.databasejpa.services;

import com.rouclec.databasejpa.domain.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author save(Author author);

    List<Author> getAll();

    Optional<Author> get(Long id);

    boolean isExisit(Long id);

    Author partialUpdate(Long id, Author author);

    void delete(Long id);
}
