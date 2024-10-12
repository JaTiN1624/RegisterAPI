package com.example.Invoice.System.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

//            Additional packages for resend email
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

//    public void sendEmail(String to, String subject, String body){
//
//        try {
//            // Create a MIME message
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            // Set recipient, subject, and content of the email
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(body, true);  // 'true' indicates that the body is HTML text
//
//            // Send the email
//            javaMailSender.send(message);
//        }
//
//
//        catch (MessagingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to send email", e);
//        }
//    }


//    updated code for send email


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

            // Schedule the email to be sent again after some time (e.g., 30 minutes)
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> future = scheduler.schedule(() -> sendEmailAgain(to, subject, body), 30, TimeUnit.MINUTES);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private void sendEmailAgain(String to, String subject, String body){
        try {
            // Create a new MIME message
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set recipient, subject, and content of the email
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);  // 'true' indicates that the body is HTML text

            // Send the email again
            javaMailSender.send(message);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email again", e);
        }
    }
}
