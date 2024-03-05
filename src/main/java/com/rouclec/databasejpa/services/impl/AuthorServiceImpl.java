package com.rouclec.databasejpa.services.impl;

import com.rouclec.databasejpa.domain.entities.Author;
import com.rouclec.databasejpa.repositories.AuthorRepository;
import com.rouclec.databasejpa.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Author> get(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExisit(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author partialUpdate(Long id, Author author) {
        author.setId(id);

        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(author.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(author.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
