package com.ala.book.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class BookControllerIT {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    @WithMockUser
    @Disabled
        // Mocks an authenticated user
    void saveBook_ShouldReturnOkStatus() throws Exception {
        BookRequest bookRequest =
                BookRequest.builder()
                        .id(1)
                        .title("TEST TITLE")
                        .authorName("TEST AUTHOR")
                        .isbn("TEST ISBN")
                        .build();
        Mockito.when(bookService.save(any(BookRequest.class), any()))
                .thenReturn(1);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(1)));
    }

    @Test
    @WithMockUser
    @Disabled
    void getBookById_ShouldReturnBookResponse() throws Exception {
        BookResponse mockResponse = BookResponse.builder()
                .id(1)
                .title("TEST TITLE")
                .authorName("TEST AUTHOR")
                .isbn("TEST ISBN")
                .archived(true)

                .build();
        Mockito.when(bookService.getBookById(eq(1))).thenReturn(mockResponse);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.author", is("Test Author")));
    }

    @Test
    @WithMockUser
    @Disabled
    void getBooks_ShouldReturnPaginatedBooks() throws Exception {
        PageResponse<BookResponse> mockPageResponse = new PageResponse<>();
        BookResponse bookResponse1 = BookResponse.builder()
                .id(1)
                .title("TEST TITLE")
                .authorName("TEST AUTHOR")
                .isbn("TEST ISBN")
                .archived(true)
                .build();
        BookResponse bookResponse2 = BookResponse.builder()
                .id(1)
                .title("TEST TITLE")
                .authorName("TEST AUTHOR")
                .isbn("TEST ISBN")
                .archived(true).build();
        mockPageResponse.setTotalPages(1);
        mockPageResponse.setSize(2);
        mockPageResponse.setContent(List.of(
                bookResponse2,
                bookResponse1
        ));
        Mockito.when(bookService.findBooks(eq(0), eq(10), any()))
                .thenReturn(mockPageResponse);

        mockMvc.perform(get("/books?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].title", is("Book 1")))
                .andExpect(jsonPath("$.content[1].title", is("Book 2")));
    }

    @Test
    @WithMockUser
    void uploadBookCoverPicture_ShouldReturnAcceptedStatus() throws Exception {
        MvcResult result = mockMvc.perform(multipart("/books/cover/1")
                        .file("file", "dummy content".getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isAccepted())
                .andReturn();

        Mockito.verify(bookService).uploadBookCoverPicture(any(), any(), eq(1));
    }

    @Test
    @WithMockUser
    void returnBook_ShouldReturnOkStatus() throws Exception {
        Mockito.when(bookService.returnBook(eq(1), any()))
                .thenReturn(1);

        mockMvc.perform(patch("/books/borrow/return/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }
}
