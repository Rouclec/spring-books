package com.rouclec.databasejpa.mappers.impl;

import com.rouclec.databasejpa.domain.dto.BookDto;
import com.rouclec.databasejpa.domain.entities.Book;
import com.rouclec.databasejpa.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<Book, BookDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookDto mapTo(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public Book mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
