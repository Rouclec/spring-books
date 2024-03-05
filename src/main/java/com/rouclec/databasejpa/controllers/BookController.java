package com.rouclec.databasejpa.controllers;

import com.rouclec.databasejpa.domain.dto.BookDto;
import com.rouclec.databasejpa.domain.entities.Book;
import com.rouclec.databasejpa.mappers.Mapper;
import com.rouclec.databasejpa.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private Mapper<Book, BookDto> bookMapper;

    @Autowired
    private BookService bookService;

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        Boolean bookExists = bookService.isExists(isbn);
        Book book = bookMapper.mapFrom(bookDto);
        Book savedBook = bookService.createUpdateBook(isbn, book);
        BookDto savedUpdatedBookDto = bookMapper.mapTo(savedBook);

        if(bookExists){
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.CREATED);
        }
    }

    @PatchMapping("{isbn}")
    public ResponseEntity<BookDto> bookPartialUpdate(@PathVariable("isbn") String isbn, @RequestBody BookDto bookdto){
        Book book = bookMapper.mapFrom(bookdto);
        if(!bookService.isExists(isbn)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Book updatedBook = bookService.partialUpdate(isbn, book);

        return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
    }

    @GetMapping("")
    public Page<BookDto> getBooks(Pageable pageable){
        Page<Book> books = bookService.getAll(pageable);
        return books.map(bookMapper::mapTo);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<Book> book = bookService.get(isbn);
        return book.map(bookEntity -> {
            BookDto bookDto =  bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
