package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import com.ala.book.file.FileUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookMapper {
    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .authorName(request.authorName())
                .isbn(request.isbn())
                .shareable(request.shareable())
                .synopsis(request.synopsis())
                .build();
    }


    public BookResponse fromBook(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .authorName(book.getAuthorName())
                .rate(book.getRate())
                .synopsis(book.getSynopsis())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .owner(book.getUser().fullName())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedTransactionHistoryResponse fromBookTransactionHistory(BookTransactionHistory history) {
        return BorrowedTransactionHistoryResponse.builder()
                .id(history.getBook().getId())
                .authorName(history.getBook().getAuthorName())
                .rate(history.getBook().getRate())
                .isbn(history.getBook().getIsbn())
                .title(history.getBook().getTitle())
                .returnApproved(history.isReturnApproved())
                .returned(history.isReturned())
                .build();


    }
}
