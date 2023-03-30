package com.g2t3.vms.controller;

import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.Form;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.FormService;


// TODO: capture last edit for edit/create 

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping(path="/api/form", produces="application/json")
public class FormController {
    @Autowired
    private FormService service;

    // Returns all Forms
    Logger logger = LogManager.getLogger(FormTemplateController.class);

    // Returns all Forms
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getAllForm() {
        ArrayList <Form> forms = new ArrayList<>();
        try {
            forms = service.getAllForms();
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, forms);
    }

    @GetMapping("/{FID}")
    @ResponseBody
    public ResponseEntity<?> getFormById(@PathVariable String FID) {
        Form getForm;
        try {
            getForm = service.getFormByFID(FID);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, getForm);
        
    }


    @PostMapping("/create")
    public ResponseEntity<?> createForm(@RequestBody Map<String, String> newFormInfo) {
        try {
            service.createForm(newFormInfo); 
            return ResponseHandler.generateResponse("Created form for " + newFormInfo.get("assigned_vendor_email") + " successfully.", HttpStatus.OK, null);
        } catch (FormNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } 
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editForm(@RequestBody Form editedForm) {
        try {
            service.editForm(editedForm); 
            return ResponseHandler.generateResponse("Form edited successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } 
    }

    @PostMapping("/changestatus/{action}")
    public ResponseEntity<?> changeStatus(@RequestBody Map<String, String> postQuery, @PathVariable String action) {
        try {
            service.changeStatus(postQuery, action);
            return ResponseHandler.generateResponse("Form Status changed successfully.", HttpStatus.OK, null);
        }
        catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
    }

    // @GetMapping("/user/{UID}")
    // public
}
