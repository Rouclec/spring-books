package com.rouclec.databasejpa.controllers;

import com.rouclec.databasejpa.domain.dto.AuthorDto;
import com.rouclec.databasejpa.domain.entities.Author;
import com.rouclec.databasejpa.mappers.Mapper;
import com.rouclec.databasejpa.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Mapper<Author, AuthorDto> authorMapper;

    @PostMapping("")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
        Author author = authorMapper.mapFrom(authorDto);
        Author savedAuthor = authorService.save(author);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<AuthorDto> getAuthors(){
        List<Author> authors = authorService.getAll();
        return authors.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<Author> author = authorService.get(id);
        return author.map(authorEntity -> {
           AuthorDto authorDto =  authorMapper.mapTo(authorEntity);
           return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
       if(!authorService.isExisit(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

       authorDto.setId(id);
       Author author = authorMapper.mapFrom(authorDto);
       Author savedAuthor = authorService.save(author);
       return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExisit(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Author author = authorMapper.mapFrom(authorDto);
        Author updatedAuthor = authorService.partialUpdate(id, author);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
