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

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedTransactionHistoryResponse>> getBorrowedBook(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "0",required = false) int size,
            Authentication connectedUser){
        return ResponseEntity.ok(service.getBorrowedBook(page,size,connectedUser));
    }
    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedTransactionHistoryResponse>> findReturnedBook(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "0",required = false)int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findReturnedBook(page,size,connectedUser));
    }
   
    @DeleteMapping("/waiting-list/{book-id}")

    public ResponseEntity<Integer> removeBookFromWaitingList(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser){

        return ResponseEntity.ok(service.removeBookFromWaitingList(bookId,connectedUser));
    }
}
