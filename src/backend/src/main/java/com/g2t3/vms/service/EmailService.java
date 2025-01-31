package com.g2t3.vms.service;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.exception.ResourceNotValidException;
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

    @Value("${spring.mail.username}")
    private String senderEmail;

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
    
    // Send Email Template
    private boolean isEmailValid(String emailTo) {
        return EmailValidator.getInstance().isValid(emailTo);
    }

    public void sendSimpleEmail(Email email) throws MailException, MessagingException, ResourceNotValidException, Exception {

        // error
        if (!isEmailValid(email.getToEmail())) {
            throw new ResourceNotValidException(String.format("The sender email (%s) is invalid.", email.getToEmail()));
        }

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(email.getToEmail());
        message.setText(email.getBody());
        message.setSubject(email.getSubject());

        mailSender.send(message);
        
    }

    public void sendEmailWithAttachment(Email email) throws MailException, MessagingException, ResourceNotValidException, Exception {

        // error
        if (!isEmailValid(email.getToEmail())) {
            throw new ResourceNotValidException(String.format("The sender email (%s) is invalid.", email.getToEmail()));
        }
     
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(senderEmail);
        mimeMessageHelper.setTo(email.getToEmail());
        mimeMessageHelper.setText(email.getBody());
        mimeMessageHelper.setSubject(email.getSubject());

        FileSystemResource file = new FileSystemResource(new File(email.getAttachment()));
        mimeMessageHelper.addAttachment(file.getFilename(), file);

        mailSender.send(mimeMessage);
        
    }

    public void sendAccountConfirmationEmail(User user) throws ResourceNotFoundException, MailException, MessagingException, ResourceNotValidException, Exception {

        // error
        if (!isEmailValid(user.getEmail())) {
            throw new ResourceNotValidException(String.format("The sender email (%s) is invalid.", user.getEmail()));
        }

        EmailTemplate template = getTemplateById("642d07c023c81a4aea7043b4");
        if (template == null) {
            throw new ResourceNotFoundException("Template does not exist.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(user.getEmail());
        message.setText(String.format(template.getData(), user.getName(), user.getUserId()));
        message.setSubject(template.getSubject());

        mailSender.send(message);

    }

    public void sendReminderEmail(ReminderEmailRequest request) throws ResourceNotFoundException, MailException, MessagingException, ResourceNotValidException, Exception {

        // error
        if (!isEmailValid(request.getEmail())) {
            throw new ResourceNotValidException(String.format("The sender email (%s) is invalid.", request.getEmail()));
        }

        EmailTemplate template = getTemplateById("642d07d323c81a4aea7043b9");
        if (template == null) {
            throw new ResourceNotFoundException("Template does not exist.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(request.getEmail());
        message.setText(String.format(template.getData(), request.getName(), request.getDeadline()));
        message.setSubject(String.format(template.getSubject(), request.getFormName()));

        mailSender.send(message);

    }
}