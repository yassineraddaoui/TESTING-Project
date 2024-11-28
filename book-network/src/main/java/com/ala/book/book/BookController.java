package com.ala.book.book;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Book")
public class BookController {
    private final BookService service;

    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("book-id") Integer bookId) {
        return ResponseEntity.ok(service.getBookById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> getBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size,
            Authentication connectedUser
    ) {
        ;
        return ResponseEntity.ok(service.findBooks(page, size, connectedUser));
    }

    @GetMapping("/public")
    public ResponseEntity<PageResponse<BookResponse>> getBooksByPublic() {
        return ResponseEntity.ok(service.findPublicBooks(0, 100));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> getBookByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.getBookByOwner(page, size, connectedUser));

    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedTransactionHistoryResponse>> getBorrowedBook(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.getBorrowedBook(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedTransactionHistoryResponse>> findReturnedBook(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findReturnedBook(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> shareBook(@PathVariable("book-id") Integer bookId,
                                             Authentication connectedUser) {
        return ResponseEntity.ok(service.shareBook(bookId, connectedUser));
    }

    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Integer> archivedBook(@PathVariable("book-id") Integer bookId,
                                                Authentication connectedUser) {
        return ResponseEntity.ok(service.archivedBook(bookId, connectedUser));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBook(@PathVariable("book-id") Integer bookId,
                                              Authentication connectedUser) {
        return ResponseEntity.ok(service.returnBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/approved/{book-id}")
    public ResponseEntity<Integer> returnApprovedBook(@PathVariable("book-id") Integer bookId,
                                                      Authentication connectedUser) {
        return ResponseEntity.ok(service.returnApprovedBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") Integer bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        service.uploadBookCoverPicture(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/waiting-list/{book-id}")
    public ResponseEntity<Integer> addBookToWaitingList(@PathVariable("book-id") Integer bookId,
                                                        Authentication connectedUser) {
        return ResponseEntity.ok(service.addBookToWaitingList(bookId, connectedUser));
    }

    @GetMapping("/waiting-list")
    public ResponseEntity<PageResponse<BookResponse>> getMyWaitingList(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "0", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.getMyWaitingList(page, size, connectedUser));

    }

    @DeleteMapping("/waiting-list/{book-id}")

    public ResponseEntity<Integer> removeBookFromWaitingList(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser) {

        return ResponseEntity.ok(service.removeBookFromWaitingList(bookId, connectedUser));
    }
}
