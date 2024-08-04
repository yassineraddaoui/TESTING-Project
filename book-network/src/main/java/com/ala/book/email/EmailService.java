package com.ala.book.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Async
    public void sendEmail(String to,
                          String username,
                          EmailTemplateName emailTemplateName,
                          String confirmationUrl,
                          String activationCode,
                          String subject) throws MessagingException {
        String temlateName;
        if (emailTemplateName == null){
            temlateName = "confirm-email";
        }else {
            temlateName = emailTemplateName.getName();
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MULTIPART_MODE_MIXED, UTF_8.name());
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("confirmationUrl",confirmationUrl);
        map.put("activation_code",activationCode);
        Context context = new Context();
        context.setVariables(map);
        messageHelper.setFrom("bjaouiala3@gmail.com");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        String template = templateEngine.process(temlateName,context);
        messageHelper.setText(template,true);
        mailSender.send(message);

    }
}
