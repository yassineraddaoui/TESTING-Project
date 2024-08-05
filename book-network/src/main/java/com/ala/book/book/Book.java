package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import com.ala.book.common.BaseEntity;
import com.ala.book.feedback.Feedback;
import com.ala.book.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "book")
    List<Feedback> feedbacks;
    @OneToMany(mappedBy = "book")
    List<BookTransactionHistory> histories;

}
