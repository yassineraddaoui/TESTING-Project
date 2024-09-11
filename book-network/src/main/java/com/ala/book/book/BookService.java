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





    public Integer shareBook(Integer bookId, Authentication connectedUser) {
        Book book =  repository.findOne(BookSpecification.withBookId( bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given criteria"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getUser().getId(), user.getId())){
            throw new OperationNotPerimttedException("you cannot update books shareable status");
        }
        book.setShareable(!book.isShareable());
        repository.save(book);
        return bookId;

    }

    public Integer archivedBook(Integer bookId, Authentication connectedUser) {
        Book book = repository.findOne(BookSpecification.withBookId(bookId))
                .orElseThrow(() ->new EntityNotFoundException("No book found for given criteria"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getUser().getId(),user.getId())){
            throw new OperationNotPerimttedException("you cannot update books archive status");
        }
        book.setArchived(!book.isArchived());
        repository.save(book);
        return bookId;
    }


    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = repository.findOne(BookSpecification.withBookId(bookId))
                .orElseThrow(() -> new EntityNotFoundException("No book found for given id"));
        User user = ((User) connectedUser.getPrincipal());
        var bookCover = fileStorageService.saveFile(file,user.getId());
        book.setBookCover(bookCover);
        repository.save(book);

    }

    public Integer addBookToWaitingList(Integer bookId, Authentication connectedUser) {
        User user = ((User)connectedUser.getPrincipal());
        Boolean isAlreadyInTheList = waitingListRepository.isAlreadyInTheList(bookId,user.getId());
        Book book = repository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("book not found with the given id"));
        if (isAlreadyInTheList){
            throw new OperationNotPerimttedException("the book is already in the waiting list");
        }
        var waitingList = WaitingList.builder().book(book).user(user).build();
        return waitingListRepository.save(waitingList).getId();
    }


}
