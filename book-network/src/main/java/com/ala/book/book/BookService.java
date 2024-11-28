package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import com.ala.book.History.BookTransactionHistoryRepository;
import com.ala.book.exception.OperationNotPerimttedException;
import com.ala.book.notification.Notification;
import com.ala.book.notification.NotificationService;
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
import static com.ala.book.notification.NotificationStatus.*;

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
    private final NotificationService notificationService;


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


    public PageResponse<BookResponse> findBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<Book> books = repository.findAllDisplayableBook(pageable, user.getId());
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

    public PageResponse<BookResponse> getBookByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<Book> books = repository.findAll(withOwnerId(user.getId()), pageable);
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<BookTransactionHistory> books = transactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<BookTransactionHistory> books = transactionHistoryRepository.findAll(BookSpecification.withReturnedBook(user.getId()), pageable);
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

    public Integer shareBook(Integer bookId, Authentication connectedUser) {
        Book book = repository.findOne(BookSpecification.withBookBorrowedId(bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given criteria"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getUser().getId(), user.getId()) || book.isArchived()) {
            throw new OperationNotPerimttedException("you cannot update books shareable status");
        }
        book.setShareable(!book.isShareable());
        repository.save(book);
        return bookId;

    }

    public Integer archivedBook(Integer bookId, Authentication connectedUser) {
        Book book = repository.findOne(BookSpecification.withBookBorrowedId(bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given criteria"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getUser().getId(), user.getId())) {
            throw new OperationNotPerimttedException("you cannot update books archive status");
        }
        book.setArchived(!book.isArchived());
        repository.save(book);
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = repository.findOne(BookSpecification.withBookBorrowedId(bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given id"));
        if (!book.isShareable() || book.isArchived()) {
            throw new OperationNotPerimttedException("requested book cannot be borrowed since it is archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getUser().getId(), user.getId())) {
            throw new OperationNotPerimttedException("you cannot borrow your own book");
        }
        boolean isAlreadyBorrowed = transactionHistoryRepository.isAlreadyBorrowedByUser(bookId);
        if (isAlreadyBorrowed) {
            throw new OperationNotPerimttedException("the request book is already borrowed");
        }
        BookTransactionHistory bookTransactionHistory = BookTransactionHistory
                .builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        notificationService.sendNotification(
                book.getCreatedBy(),
                Notification.builder()
                        .status(BORROWED)
                        .message("your book has been borrowed")
                        .bookTitle(book.getTitle())
                        .build()
        );
        return transactionHistoryRepository.save(bookTransactionHistory).getId();

    }

    public Integer returnBook(Integer bookId, Authentication connectedUser) {
        Book book = repository.findOne(BookSpecification.withBookId(bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given criteria"));
        if (!book.isShareable() || book.isArchived()) {
            throw new OperationNotPerimttedException("requested book cannot be borrowed since it is archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getUser().getId(), user.getId())) {
            throw new OperationNotPerimttedException("you cannot borrow your own book");
        }
        BookTransactionHistory bookTransactionHistory = transactionHistoryRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPerimttedException("you did not borrow this book"));
        bookTransactionHistory.setReturned(true);
        var saved = transactionHistoryRepository.save(bookTransactionHistory);
        notificationService.sendNotification(
                book.getCreatedBy(),
                Notification.builder()
                        .status(RETURNED)
                        .message("your book has been returned")
                        .bookTitle(book.getTitle())
                        .build()
        );
        return saved.getId();

    }

    public Integer returnApprovedBook(Integer bookId, Authentication connectedUser) {

        Book book = repository.findOne(BookSpecification.withBookId(bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given id"));
        if (!book.isShareable() || book.isArchived()) {
            throw new OperationNotPerimttedException("requested book cannot be borrowed since it is archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getUser().getId(), user.getId())) {
            throw new OperationNotPerimttedException("you cannot return the book that you are not own");
        }
        BookTransactionHistory transactionHistory = transactionHistoryRepository.findByBookIdAndUserIdAndReturned(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPerimttedException("the book is not returned yet"));
        transactionHistory.setReturnApproved(true);
        var saved = transactionHistoryRepository.save(transactionHistory);
        notificationService.sendNotification(
                transactionHistory.getCreatedBy(),
                Notification.builder()
                        .status(RETURN_APPROVED)
                        .message("the book that you've returned has been Approved")
                        .bookTitle(book.getTitle())
                        .build()
        );
        return saved.getId();
    }

    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = repository.findOne(BookSpecification.withBookBorrowedId(bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given id"));
        User user = ((User) connectedUser.getPrincipal());
        var bookCover = fileStorageService.saveFile(file, user.getId());
        book.setBookCover(bookCover);
        repository.save(book);

    }

    public Integer addBookToWaitingList(Integer bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Boolean isAlreadyInTheList = waitingListRepository.isAlreadyInTheList(bookId, user.getId());
        Book book = repository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("book not found with the given id"));
        if (isAlreadyInTheList) {
            throw new OperationNotPerimttedException("the book is already in the waiting list");
        }
        var waitingList = WaitingList.builder().book(book).user(user).build();
        return waitingListRepository.save(waitingList).getId();
    }

    public PageResponse<BookResponse> getMyWaitingList(Integer page, Integer size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<Book> bookPageResponse = repository.findMyWaitingListWithUserId(user.getId(), pageable);

        List<BookResponse> books = bookPageResponse.stream().map(bookMapper::fromBook).toList();
        return new PageResponse<>(
                books,
                bookPageResponse.getNumber(),
                bookPageResponse.getSize(),
                bookPageResponse.getTotalElements(),
                bookPageResponse.getTotalPages(),
                bookPageResponse.isFirst(),
                bookPageResponse.isLast()
        );
    }

    public Integer removeBookFromWaitingList(Integer bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        return waitingListRepository.deleteByBookIdAndUserId(bookId, user.getId());
    }

    public PageResponse<BookResponse> findPublicBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());

        Page<Book> bookPageResponse = repository.findAll(pageable);

        List<BookResponse> books = bookPageResponse.stream().map(bookMapper::fromBook).toList();
        return new PageResponse<>(
                books,
                bookPageResponse.getNumber(),
                bookPageResponse.getSize(),
                bookPageResponse.getTotalElements(),
                bookPageResponse.getTotalPages(),
                bookPageResponse.isFirst(),
                bookPageResponse.isLast()
        );

    }
}
