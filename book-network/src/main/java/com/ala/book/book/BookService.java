package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import com.ala.book.History.BookTransactionHistoryRepository;
import com.ala.book.exception.OperationNotPerimttedException;
import com.ala.book.user.User;
import com.ala.book.waitingList.WaitingList;
import com.ala.book.waitingList.WaitingListRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.ala.book.book.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository repository;
    private final BookTransactionHistoryRepository transactionHistoryRepository;
    private final FileStorageService fileStorageService;
    private final WaitingListRepository waitingListRepository;





    public PageResponse<BookResponse> getBookByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size,Sort.by("createDate").descending());
        Page<Book> books = repository.findAll(withOwnerId(user.getId()),pageable);
        List<BookResponse> bookResponses = books.stream().map(bookMapper::fromBook).toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BorrowedTransactionHistoryResponse> getBorrowedBook(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size,Sort.by("createDate").descending());
        Page<BookTransactionHistory> books = transactionHistoryRepository.findAllBorrowedBooks(pageable,user.getId());
        List<BorrowedTransactionHistoryResponse> transactionHistories = books.stream().map(bookMapper::fromBookTransactionHistory).toList();
        return new PageResponse<>(
                transactionHistories,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()

        );

    }

    public PageResponse<BorrowedTransactionHistoryResponse> findReturnedBook(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size,Sort.by("createDate").descending());
        Page<BookTransactionHistory> books = transactionHistoryRepository.findAll(BookSpecification.withReturnedBook(user.getId()),pageable);
        List<BorrowedTransactionHistoryResponse> transactionHistory = books.stream().map(bookMapper::fromBookTransactionHistory).toList();
        return new PageResponse<>(
                transactionHistory,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()

        );
    }



    public Integer removeBookFromWaitingList(Integer bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
         return waitingListRepository.deleteByBookIdAndUserId(bookId,user.getId());
    }
}
