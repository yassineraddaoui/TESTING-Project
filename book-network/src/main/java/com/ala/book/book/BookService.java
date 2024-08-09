package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import com.ala.book.History.BookTransactionHistoryRepository;
import com.ala.book.exception.OperationNotPerimttedException;
import com.ala.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.ala.book.book.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository repository;
    private final BookTransactionHistoryRepository transactionHistoryRepository;



    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setUser(user);
        return repository.save(book).getId();
    }

    public BookResponse getBookById(Integer bookId) {
        return repository.findById(bookId)
                .map(bookMapper::fromBook)
                .orElseThrow(() -> new EntityNotFoundException("no book found with the given id"));
    }



}
