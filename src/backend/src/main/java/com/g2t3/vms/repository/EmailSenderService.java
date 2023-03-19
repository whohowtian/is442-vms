package com.g2t3.vms.repository;

import java.io.File;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.g2t3.vms.model.Email;


@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    // public void sendEmail(String toEmail, String body, String subject, String attachment) throws MailException, MessagingException {
    public void sendEmail(Email email) throws MailException, MessagingException {
        if (email.getAttachment() == "") {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("is442g2t3@outlook.com");
            message.setTo(email.getToEmail());
            message.setText(email.getBody());
            message.setSubject(email.getSubject());

            mailSender.send(message);
            System.out.println("Email sent successfully");
        } else {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper;

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("is442g2t3@outlook.com");
            mimeMessageHelper.setTo(email.getToEmail());
            mimeMessageHelper.setText(email.getBody());
            mimeMessageHelper.setSubject(email.getSubject());

            FileSystemResource file = new FileSystemResource(new File(email.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);

            mailSender.send(mimeMessage);
            System.out.println("Email sent successfully");
        }

    }
}
