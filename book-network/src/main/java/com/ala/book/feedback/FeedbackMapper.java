package com.ala.book.feedback;

import com.ala.book.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.getNote())
                .comment(request.getComment())
                .book(Book.builder().id(request.bookId).build()).build();
    }

    public FeedbackResponse fromFeedback(Feedback feedback,Integer userId) {
        return FeedbackResponse.builder()
                .comment(feedback.getComment())
                .note(feedback.getNote())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(),userId))
                .build();
    }
}
