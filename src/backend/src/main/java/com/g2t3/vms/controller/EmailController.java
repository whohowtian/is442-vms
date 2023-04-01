package com.g2t3.vms.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.Email;
import com.g2t3.vms.model.EmailTemplate;
import com.g2t3.vms.request.ReminderEmailRequest;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.EmailService;

import jakarta.mail.MessagingException;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/email")
public class EmailController {

        @Autowired
        private EmailService service;

        @PostMapping(value = "/sendEmail", consumes = "application/json", produces = "application/json")
        public ResponseEntity<?> sendEmail(@RequestBody Email email) {
            try{
                service.sendEmail(email);
                return ResponseHandler.generateResponse("Sent email successfully.", HttpStatus.OK, null);
            } catch (MailException e){
                return ResponseHandler.generateResponse("Error Occured: Mail Exception. " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            } catch(MessagingException e){
                return ResponseHandler.generateResponse("Error Occured: Messaging Exception. " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            } catch(Exception e) {
                return ResponseHandler.generateResponse("Error Occured: Exception. " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            }
        }

        @PostMapping(value = "/sendReminderEmail", consumes = "application/json", produces = "application/json")
        public ResponseEntity<?> sendReminderEmail(@RequestBody ReminderEmailRequest email) {
            try{
                service.sendReminderEmail(email);
                return ResponseHandler.generateResponse("Sent email successfully.", HttpStatus.OK, null);
            } catch (MailException e){
                return ResponseHandler.generateResponse("Error Occured: Mail Exception. " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            } catch(MessagingException e){
                return ResponseHandler.generateResponse("Error Occured: Messaging Exception. " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            } catch(Exception e) {
                return ResponseHandler.generateResponse("Error Occured: Exception. " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            }
        }

        // templates
        @GetMapping("/template/all")
        @ResponseBody
        public ResponseEntity<?> getAllTemplates() {
            ArrayList <EmailTemplate> users = new ArrayList<>();
            try {
                users = service.getAllTemplates();
            } catch (ResourceNotFoundException e) {
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
            } catch (Exception e) {
                return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            }
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, users);
        }

        @GetMapping("/template/{id}")
        @ResponseBody
        public ResponseEntity<?> getUserById(@PathVariable String id) {
            EmailTemplate template = null;
            try {
                template = service.getTemplateById(id);
            } catch (ResourceNotFoundException e) {
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
            } catch (Exception e) {
                return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
            }
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, template);
        }

        @PostMapping("/template/create")
        public ResponseEntity<?> createTemplate(@RequestBody EmailTemplate template) {

            try {
                service.createTemplate(template);
                return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
            } catch (ResourceAlreadyExistException e) {
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
            } catch (Exception e) {
                return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
            }

        }


        // Healthcheck
        @GetMapping("/")
        @ResponseBody
        public ResponseEntity<?> healthCheck() {
            return ResponseHandler.generateResponse("EmailController connected", HttpStatus.OK, null);
        }
}
