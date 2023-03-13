package com.g2t3.vms.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import com.g2t3.vms.model.Email;
import com.g2t3.vms.repository.EmailSenderService;

@RestController
@RequestMapping("/email")
public class EmailController {

        @Autowired
        private EmailSenderService service;

        @PostMapping(value = "/sendEmail", consumes = "application/json", produces = "application/json")
        public String sendEmail(@RequestBody Email email) {
            try{
                service.sendEmail(email.getToEmail(),email.getBody(),email.getSubject(),email.getAttachment());
                return "Email sent successfully";
            }catch (MailException e){
                return "error for MailException";
            }catch(MessagingException e){
                return "error for MessagingException";
            }

    }
}
