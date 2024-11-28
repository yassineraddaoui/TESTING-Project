package com.ala.book.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private Authentication authentication;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private BookController bookController;


    @Test
    void saveBook_ShouldReturnBookId() {
        // Arrange
        BookRequest request = BookRequest.builder().build();
        when(bookService.save(any(BookRequest.class), eq(authentication))).thenReturn(1);

        // Act
        ResponseEntity<Integer> response = bookController.saveBook(request, authentication);

        // Assert
        verify(bookService, times(1)).save(any(BookRequest.class), eq(authentication));
        assertEquals(1, response.getBody());
    }

    @Test
    void getBookById_ShouldReturnBookResponse() {
        // Arrange
        int bookId = 1;
        BookResponse bookResponse = new BookResponse();
        when(bookService.getBookById(bookId)).thenReturn(bookResponse);

        // Act
        ResponseEntity<BookResponse> response = bookController.getBookById(bookId);

        // Assert
        verify(bookService, times(1)).getBookById(bookId);
        assertEquals(bookResponse, response.getBody());
    }

    @Test
    void getBooks_ShouldReturnPageOfBooks() {
        // Arrange
        int page = 0;
        int size = 10;
        PageResponse<BookResponse> pageResponse = new PageResponse<>();
        when(bookService.findBooks(page, size, authentication)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<BookResponse>> response = bookController.getBooks(page, size, authentication);

        // Assert
        verify(bookService, times(1)).findBooks(page, size, authentication);
        assertEquals(pageResponse, response.getBody());
    }

    @Test
    void shareBook_ShouldReturnBookId() {
        // Arrange
        int bookId = 1;
        when(bookService.shareBook(bookId, authentication)).thenReturn(1);

        // Act
        ResponseEntity<Integer> response = bookController.shareBook(bookId, authentication);

        // Assert
        verify(bookService, times(1)).shareBook(bookId, authentication);
        assertEquals(1, response.getBody());
    }

    @Test
    void uploadBookCoverPicture_ShouldCallService() {
        // Arrange
        int bookId = 1;
        doNothing().when(bookService).uploadBookCoverPicture(multipartFile, authentication, bookId);

        // Act
        ResponseEntity<?> response = bookController.uploadBookCoverPicture(bookId, multipartFile, authentication);

        // Assert
        verify(bookService, times(1)).uploadBookCoverPicture(multipartFile, authentication, bookId);
        assertEquals(202, response.getStatusCodeValue());
    }

    @Test
    void addBookToWaitingList_ShouldReturnBookId() {
        // Arrange
        int bookId = 1;
        when(bookService.addBookToWaitingList(bookId, authentication)).thenReturn(1);

        // Act
        ResponseEntity<Integer> response = bookController.addBookToWaitingList(bookId, authentication);

        // Assert
        verify(bookService, times(1)).addBookToWaitingList(bookId, authentication);
        assertEquals(1, response.getBody());
    }

    @Test
    void removeBookFromWaitingList_ShouldReturnBookId() {
        // Arrange
        int bookId = 1;
        when(bookService.removeBookFromWaitingList(bookId, authentication)).thenReturn(1);

        // Act
        ResponseEntity<Integer> response = bookController.removeBookFromWaitingList(bookId, authentication);

        // Assert
        verify(bookService, times(1)).removeBookFromWaitingList(bookId, authentication);
        assertEquals(1, response.getBody());
    }
}
