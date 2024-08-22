package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> withOwnerId(Integer ownerId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"),ownerId));
    }
    public static Specification<BookTransactionHistory> withReturnedBook(Integer ownerId){
        return ((root, query, criteriaBuilder) -> {
            Predicate returnedPredicate = criteriaBuilder.equal(root.get("returned"),true);
            Predicate userIdPredicate = criteriaBuilder.equal(root.get("user").get("id"),ownerId);
            criteriaBuilder.and(returnedPredicate,userIdPredicate);
            return returnedPredicate;
        });
    }

    public static Specification<Book> withBookId(Integer bookId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),bookId));
    }

}
