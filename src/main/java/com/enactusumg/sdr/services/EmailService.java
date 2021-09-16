package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.EmailBodyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    public boolean sendEmail(EmailBodyDto emailBody) {
        log.info("EmailBody: {}", emailBody.toString());
        return sendEmailTool(emailBody.getContent(), emailBody.getEmail(), emailBody.getSubject());
    }

    private boolean sendEmailTool(String textMessage, String email, String subject) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            log.info("Email Sent");
            return true;
        } catch (MessagingException e) {
            log.error("An error has occurred: ", e);
        }
        return false;
    }
}