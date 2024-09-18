package com.ala.book.feedback;

import com.ala.book.book.Book;
import com.ala.book.book.BookRepository;
import com.ala.book.book.BookSpecification;
import com.ala.book.book.PageResponse;
import com.ala.book.exception.OperationNotPerimttedException;
import com.ala.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;
    private final BookRepository bookRepository;
    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findOne(BookSpecification.withBookId(request.bookId))
                .orElseThrow(()-> new EntityNotFoundException("book not found with the given id"));
        if (book.isArchived() || !book.isShareable()){
            throw new OperationNotPerimttedException("can not assign a feedback to this book because it is archived or not shareable");
        }
        User user =  ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getUser().getId(),user.getId())){
            throw new OperationNotPerimttedException("you cannot give a feedback to your own book");
        }
        Feedback feedback = mapper.toFeedback(request);
        return repository.save(feedback).getId();

    }


    public PageResponse<FeedbackResponse> getFeedbackByBookId(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createDate").descending());
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = repository.findAll(FeedbackSpecification.withBookId(bookId),pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream().map(f -> mapper.fromFeedback(f,user.getId())).toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()

        );
    }
}
