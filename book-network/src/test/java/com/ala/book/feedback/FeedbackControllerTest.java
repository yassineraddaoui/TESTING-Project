package com.ala.book.feedback;

import com.ala.book.book.PageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class FeedbackControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private Authentication connectedUser;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
    }

    @Disabled
    @Test
    void saveFeedback_ShouldReturnFeedbackId() throws Exception {
        // Arrange
        FeedbackRequest feedbackRequest = FeedbackRequest.builder()
                .comment("Great book!")
                .build();
        int expectedFeedbackId = 1;

        when(feedbackService.save(any(FeedbackRequest.class), any()))
                .thenReturn(expectedFeedbackId);

        // Act & Assert
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void getFeedbackByBookId_ShouldReturnPageResponse() throws Exception {
        // Arrange
        Integer bookId = 1;
        PageResponse<FeedbackResponse> pageResponse = new PageResponse<>(List.of(FeedbackResponse.builder().comment("Great book!").build()), 0, 10, 1, 1, true, true);

        when(feedbackService.getFeedbackByBookId(eq(bookId), anyInt(), anyInt(), eq(connectedUser))).thenReturn(pageResponse);

        // Act & Assert
        mockMvc.perform(get("/feedbacks/book/{book-id}", bookId)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
        System.out.println(jsonPath("$.content"));
    }
}
