package com.ala.book.History;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory,Integer>, JpaSpecificationExecutor<BookTransactionHistory> {
    @Query("""
SELECT history
FROM BookTransactionHistory history
WHERE history.user.id = :id

""")

    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer id);
@Query("""
SELECT (COUNT(*)>0)  AS isBorrowed
FROM BookTransactionHistory book
WHERE book.book.id = :bookId
AND book.returnApproved=false
AND book.returned = false
""")
    boolean isAlreadyBorrowedByUser(Integer bookId);
    @Query("""
SELECT book
FROM BookTransactionHistory book
WHERE book.book.id = :bookId
AND book.user.id = :id
AND book.returned = false
AND book.returnApproved = false
""")
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer id);

    @Query("""
SELECT book
FROM BookTransactionHistory book
WHERE book.book.id = :bookId
AND book.book.user.id = :id
AND book.returned = true
AND book.returnApproved = false
""")
    Optional<BookTransactionHistory> findByBookIdAndUserIdAndReturned(Integer bookId, Integer id);

}




