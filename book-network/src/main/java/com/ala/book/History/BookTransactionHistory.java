package com.ala.book.History;

import com.ala.book.book.Book;
import com.ala.book.common.BaseEntity;
import com.ala.book.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class BookTransactionHistory extends BaseEntity {
    private boolean returned;
    private boolean returnApproved;
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
}
