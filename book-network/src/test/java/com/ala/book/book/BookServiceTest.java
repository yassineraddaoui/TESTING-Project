package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import com.ala.book.History.BookTransactionHistoryRepository;
import com.ala.book.notification.NotificationService;
import com.ala.book.notification.NotificationStatus;
import com.ala.book.user.User;
import com.ala.book.waitingList.WaitingList;
import com.ala.book.waitingList.WaitingListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookTransactionHistoryRepository transactionHistoryRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private WaitingListRepository waitingListRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Authentication authentication;


    @Test
    void borrowBook_ShouldBorrowBookSuccessfully() {
        // Arrange
        Integer bookId = 1;
        User user = new User();
        user.setId(2);

        Book book = new Book();
        book.setId(bookId);
        book.setUser(User.builder().id(2).build());
        book.setShareable(true);
        book.setArchived(false);
        Authentication authentication = new UsernamePasswordAuthenticationToken(User.builder().id(3).build(), "password", Collections.EMPTY_LIST);

        when(bookRepository.findOne(any(Specification.class))).thenReturn(Optional.of(book));

        when(transactionHistoryRepository.isAlreadyBorrowedByUser(bookId)).thenReturn(false);
        when(transactionHistoryRepository.save(any())).thenReturn(BookTransactionHistory.builder()
                .id(1)
                .build());
        // Act
        Integer transactionId = bookService.borrowBook(bookId, authentication);

        // Assert
        assertNotNull(transactionId);
        verify(notificationService).sendNotification(
                eq(book.getCreatedBy()),
                argThat(notification -> notification.getStatus() == NotificationStatus.BORROWED)
        );
        assertEquals(1, transactionId);
    }

    @Test
    void getBookById_ShouldReturnBookResponse() {
        // Arrange
        Integer bookId = 1;
        Book book = new Book();
        BookResponse response = new BookResponse();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.fromBook(book)).thenReturn(response);

        // Act
        BookResponse result = bookService.getBookById(bookId);

        // Assert
        assertEquals(response, result);
        verify(bookRepository).findById(bookId);
    }

    @Test
    void getBookById_ShouldThrowEntityNotFoundException() {
        // Arrange
        Integer bookId = 1;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    void addBookToWaitingList_ShouldAddBookToList() {
        // Arrange
        Integer bookId = 1;
        User user = new User();
        user.setId(1);
        Book book = new Book();

        when(authentication.getPrincipal()).thenReturn(user);
        when(waitingListRepository.isAlreadyInTheList(bookId, user.getId())).thenReturn(false);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(waitingListRepository.save(any()))
                .thenReturn(WaitingList.builder()
                        .id(1)
                        .build());
        // Act
        Integer result = bookService.addBookToWaitingList(bookId, authentication);

        // Assert
        assertEquals(1, result);
        verify(waitingListRepository).save(any());
    }

    @Test
    void uploadBookCoverPicture_ShouldUploadFile() {
        // Arrange
        Integer bookId = 1;
        MultipartFile file = mock(MultipartFile.class);
        User user = new User();
        user.setId(1);
        Book book = new Book();

        when(authentication.getPrincipal()).thenReturn(user);
        when(bookRepository.findOne(any(Specification.class))).thenReturn(Optional.of(book));
        when(fileStorageService.saveFile(file, user.getId())).thenReturn("path/to/file");

        // Act
        bookService.uploadBookCoverPicture(file, authentication, bookId);

        // Assert
        assertEquals("path/to/file", book.getBookCover());
        verify(bookRepository).save(book);
    }

}
