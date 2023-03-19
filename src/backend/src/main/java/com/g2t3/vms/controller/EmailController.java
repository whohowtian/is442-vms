package com.g2t3.vms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g2t3.vms.model.Email;
import com.g2t3.vms.repository.EmailSenderService;
import com.g2t3.vms.response.ResponseHandler;

import jakarta.mail.MessagingException;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/email")
public class EmailController {

        @Autowired
        private EmailSenderService service;

        @PostMapping(value = "/sendEmail", consumes = "application/json", produces = "application/json")
        public ResponseEntity<?> sendEmail(@RequestBody Email email) {
            try{
                service.sendEmail(email);
                return ResponseHandler.generateResponse("Sent email successfully.", HttpStatus.OK, null);
            } catch (MailException e){
                return ResponseHandler.generateResponse("Error Occured: Mail Exception " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            } catch(MessagingException e){
                return ResponseHandler.generateResponse("Error Occured: Messaging Exception " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            } catch(Exception e) {
                return ResponseHandler.generateResponse("Error Occured: Exception " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            }

    }
}
