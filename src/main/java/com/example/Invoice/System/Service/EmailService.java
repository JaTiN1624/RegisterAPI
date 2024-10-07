package com.example.Invoice.System.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String body){

        try {
            // Create a MIME message
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set recipient, subject, and content of the email
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);  // 'true' indicates that the body is HTML text

            // Send the email
            javaMailSender.send(message);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
