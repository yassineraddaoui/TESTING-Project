package com.ala.book.waitingList;

import com.ala.book.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface    WaitingListRepository extends JpaRepository<WaitingList,Integer> {
    @Query("""
SELECT (COUNT(*) > 0) as listNumber
FROM WaitingList list
WHERE list.book.id = :bookId
AND list.user.id = :userId
""")

    Boolean isAlreadyInTheList(Integer bookId,Integer userId);

    Integer deleteByBookIdAndUserId(Integer bookId, Integer userId);
}
