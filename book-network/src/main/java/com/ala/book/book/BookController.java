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

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> shareBook(@PathVariable("book-id") Integer bookId,
                                             Authentication connectedUser ){
        return ResponseEntity.ok(service.shareBook(bookId,connectedUser));
    }

    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Integer> archivedBook(@PathVariable("book-id") Integer bookId,
                                                Authentication connectedUser){
        return ResponseEntity.ok(service.archivedBook(bookId,connectedUser));
    }


    @PostMapping("/waiting-list/{book-id}")
    public ResponseEntity<Integer> addBookToWaitingList(@PathVariable("book-id") Integer bookId,
                                                        Authentication connectedUser){
        return ResponseEntity.ok(service.addBookToWaitingList(bookId,connectedUser));
    }

   
}
