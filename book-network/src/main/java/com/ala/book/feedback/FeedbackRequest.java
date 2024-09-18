package com.ala.book.feedback;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
    @Positive(message = "200")
    @Min(value = 0,message = "201")
    @Max(value = 5, message = "202")
    private Double note;
    private String comment;
    @NotNull(message = "204")
    Integer bookId;
}
