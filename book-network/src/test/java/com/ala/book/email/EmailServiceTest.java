package com.ala.book.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Test
    void sendEmail_ShouldSendEmailSuccessfully() throws MessagingException {
        // Arrange
        String to = "test@example.com";
        String username = "TestUser";
        EmailTemplateName templateName = EmailTemplateName.ActivateAccount;
        String confirmationUrl = "http://example.com/confirm";
        String activationCode = "123456";
        String subject = "Confirm Your Email";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Map<String, Object> variables = Map.of(
                "username", username,
                "confirmationUrl", confirmationUrl,
                "activation_code", activationCode
        );

        String processedTemplate = "<html>Email Template Content</html>";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn(processedTemplate);

        // Act
        emailService.sendEmail(to, username, templateName, confirmationUrl, activationCode, subject);

        // Assert
        verify(mailSender).send(mimeMessage);
        verify(templateEngine).process(eq(templateName.getName()), any(Context.class));
    }

    @Test
    void sendEmail_ShouldFallbackToDefaultTemplateWhenTemplateNameIsNull() throws MessagingException {
        // Arrange
        String to = "test@example.com";
        String username = "TestUser";
        String confirmationUrl = "http://example.com/confirm";
        String activationCode = "123456";
        String subject = "Confirm Your Email";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Map<String, Object> variables = Map.of(
                "username", username,
                "confirmationUrl", confirmationUrl,
                "activation_code", activationCode
        );

        String processedTemplate = "<html>Default Email Template Content</html>";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn(processedTemplate);

        // Act
        emailService.sendEmail(to, username, null, confirmationUrl, activationCode, subject);

        // Assert
        verify(mailSender).send(mimeMessage);
        verify(templateEngine).process(eq("confirm-email"), any(Context.class));
    }
}
