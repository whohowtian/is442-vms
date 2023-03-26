package com.g2t3.vms.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.FormTemplateService;

@CrossOrigin
@RestController
@RequestMapping(path="/api/formtemplate", produces="application/json")
public class FormTemplateController {

    @Autowired
    private FormTemplateService service;

    Logger logger = LogManager.getLogger(FormTemplateController.class);

    // Returns all Forms
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getAllFormTemplates() {
        ArrayList <FormTemplate> formTemplates = new ArrayList<>();
        try {
            formTemplates = service.getAllFormTemplates();
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, formTemplates);
    } 

    // Returns form by FormNo
    // Returns 404 not found if invalid formNo
    @GetMapping("/{FITD}")
    @ResponseBody
    public ResponseEntity<?> getFormTemplatesById(@PathVariable String FITD) {
        FormTemplate getForm;
        try {
            getForm = service.getFormTemplateByFTID(FITD);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, getForm);
        
    }

    // Edit existing form by FormNo
    // Returns form json obj if success, otherwise 400 or 500
    @PostMapping("/edit")
    public ResponseEntity<?> updateFormTemplate(@RequestBody FormTemplate formTemplate) {
        String formNo = formTemplate.getFormNo();
        try {
            service.updateFormTemplate(formTemplate); 
            return ResponseHandler.generateResponse("Updated " + formNo + " successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    
    // Creates new Form
    // Returns 400 bad request if validation fails
    @PostMapping("/create")
    public ResponseEntity<?> createFormTemplate(@RequestBody FormTemplate formTemplate) {
        String formNo = formTemplate.getFormNo();
        try {
            service.createFormTemplate(formTemplate); 
            return ResponseHandler.generateResponse("Created " + formNo + " successfully.", HttpStatus.OK, null);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } 
    }

    @DeleteMapping("/{FTID}")
    @ResponseBody
    public ResponseEntity<?> deleteFormTemplate(@PathVariable String FTID) {
        try {
            service.deleteFormTemplate(FTID); 
            return ResponseHandler.generateResponse("Deleted " + FTID + " successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
