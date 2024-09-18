package com.ala.book.book;

import com.ala.book.History.BookTransactionHistory;
import com.ala.book.common.BaseEntity;
import com.ala.book.feedback.Feedback;
import com.ala.book.user.User;
import com.ala.book.waitingList.WaitingList;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@ToString
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
    @OneToMany(mappedBy = "book")
    List<WaitingList> waitingLists;
    @Transient
    public double getRate(){
        if (feedbacks == null || feedbacks.isEmpty()){
            return  0.0;
        }
        var rate = this.feedbacks.stream().mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        return Math.round(rate * 10.0) / 10.0;
    }

}
