package com.g2t3.vms.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.Email;
import com.g2t3.vms.model.EmailTemplate;
import com.g2t3.vms.model.User;
import com.g2t3.vms.repository.EmailTemplateRepo;
import com.g2t3.vms.request.ReminderEmailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailTemplateRepo emailTemplateRepo;

    // templates
    public void createTemplate(EmailTemplate template) throws ResourceAlreadyExistException, Exception {

        // errors
        EmailTemplate oldTemplate = emailTemplateRepo.findByName(template.getName());
        if (oldTemplate != null) {
            throw new ResourceAlreadyExistException(String.format("An email template with this id (%s) already exist.", template.getId()));
        }

         // save email template
         emailTemplateRepo.save(template);

    }

    public ArrayList<EmailTemplate> getAllTemplates() throws ResourceNotFoundException, Exception {
        ArrayList<EmailTemplate> templates = new ArrayList<>();
        for (EmailTemplate template : emailTemplateRepo.findAll()) {
            templates.add(template);
        }

        if (templates.isEmpty()) {
            throw new ResourceNotFoundException("No email templates have been created.");
        }
        
        return templates;
    }

    public EmailTemplate getTemplateById(String id) throws ResourceNotFoundException, Exception {
        
        EmailTemplate template = emailTemplateRepo.findbyId(id);
        if (template == null) {
            throw new ResourceNotFoundException("Email Template with this id " + id + " does not exist.");
        }
        return template;

    }

    

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

    public void sendAccountConfirmationEmail(User user) throws ResourceNotFoundException, MailException, MessagingException, Exception {

        EmailTemplate template = getTemplateById("6425bf076162e20f5ce1dba3");
        if (template == null) {
            throw new ResourceNotFoundException("Template does not exist.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("is442g2t3@outlook.com");
        message.setTo(user.getEmail());
        message.setText(String.format(template.getData(), user.getName(), user.getUserId()));
        message.setSubject(template.getSubject());

        mailSender.send(message);
        System.out.println("Account Confirmation Email sent successfully");

    }

    public void sendReminderEmail(ReminderEmailRequest request) throws ResourceNotFoundException, MailException, MessagingException, Exception {

        EmailTemplate template = getTemplateById("6426facd0869fc2ea8b9aa97");
        if (template == null) {
            throw new ResourceNotFoundException("Template does not exist.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("is442g2t3@outlook.com");
        message.setTo(request.getEmail());
        message.setText(String.format(template.getData(), request.getName(), request.getDeadline()));
        message.setSubject(String.format(template.getSubject(), request.getFormName()));

        mailSender.send(message);
        System.out.println("Account Confirmation Email sent successfully");

    }
}
