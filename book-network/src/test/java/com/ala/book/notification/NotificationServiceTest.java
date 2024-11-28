package com.ala.book.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void sendNotification() {
        // Arrange
        String userId = "user123";
        Notification notification = Notification.builder()
                .message("Hello World")
                .build();

        notificationService.sendNotification(userId, notification);

        verify(messagingTemplate, times(1))
                .convertAndSendToUser(eq(userId), eq("/notification"), eq(notification));
    }
}
