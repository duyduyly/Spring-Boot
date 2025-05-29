package com.alan.security.service;

import com.alan.security.model.entity.EmailRecord;
import com.alan.security.model.payload.mail.MailRequest;
import com.alan.security.repositories.EmailRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final EmailRecordRepository emailRepo;

    public void sendEmail(MailRequest request) {
        // 1. Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getBody());
        mailSender.send(message);

        // 2. Save to DB
        EmailRecord record = new EmailRecord();
        record.setToEmail(request.getTo());
        record.setSubject(request.getSubject());
        record.setBody(request.getBody());
        record.setSentAt(LocalDateTime.now());
//        emailRepo.save(record);
    }
}
