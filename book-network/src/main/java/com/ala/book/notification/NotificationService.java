package com.ala.book.notification;

import com.ala.book.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String userId, Notification notification){
        log.info("send notification to {} with {}", userId,notification);
        messagingTemplate.convertAndSendToUser(
                userId,
                "/notification",
                notification
        );
    }
}
