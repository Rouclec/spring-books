package com.rouclec.databasejpa.mappers.impl;

import com.rouclec.databasejpa.domain.dto.AuthorDto;
import com.rouclec.databasejpa.domain.entities.Author;
import com.rouclec.databasejpa.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<Author, AuthorDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AuthorDto mapTo(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public Author mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}
