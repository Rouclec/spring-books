package com.rouclec.databasejpa.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rouclec.databasejpa.TestDataUtil;
import com.rouclec.databasejpa.domain.dto.BookDto;
import com.rouclec.databasejpa.domain.entities.Book;
import com.rouclec.databasejpa.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public  void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public  void testThatCreateBookReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatGetBooksReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBooksReturnsListOfBooks() throws Exception {

        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value("The Shadow in the Attic")
        );
    }

    @Test
    public void testThatGetBooksReturnsHttp200WhenBookExists() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsHttp404WhenBookDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/19203ajajoiasodi")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookReturnsTheBookIfItExists() throws Exception {

        Book testBookA = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
        );
    }

    @Test
    public  void testThatUpdateBookReturnsHttpStatus200OK() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.createUpdateBook(testBookA.getIsbn(), testBookA);
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public  void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBook.getIsbn());
        bookDto.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttp200() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"The shadow in the basement\"}")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        Book testBookA = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.createUpdateBook(testBookA.getIsbn(), testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"The Shadow in the Basement\"}")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Basement")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(savedBook.getAuthor())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingBook() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/208")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthors() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus404WhenFetchingDeletedAuthor() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        );
        mockMvc.perform( MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
