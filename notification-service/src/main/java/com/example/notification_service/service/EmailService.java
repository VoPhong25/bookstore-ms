package com.example.notification_service.service;

import com.example.notification_service.dto.request.EmailRequest;
import com.example.notification_service.dto.request.SendEmailRequest;
import com.example.notification_service.dto.request.Sender;
import com.example.notification_service.dto.response.EmailResponse;
import com.example.notification_service.exception.AppException;
import com.example.notification_service.exception.ErrorCode;
import com.example.notification_service.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;
    String apiKey = "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";

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("BookstoreVP")
                        .email("voquocphong6678@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
