package com.ala.book.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book,Integer>, JpaSpecificationExecutor<Book> {
    @Query("""

            SELECT book
FROM Book book
WHERE book.archived =false
AND book.shareable = true
AND book.user.id != :userId
AND book.id NOT IN (SELECT bth.book.id FROM BookTransactionHistory bth WHERE bth.book.id = book.id AND bth.returned=  false AND bth.returnApproved = false)


""")

    Page<Book> findAllDisplayableBook(Pageable page, Integer userId);
    @Query("""
SELECT book
FROM WaitingList list
join list.book book
WHERE list.user.id = :id
""")

    Page<Book> findMyWaitingListWithUserId(Integer id, Pageable pageable);


}