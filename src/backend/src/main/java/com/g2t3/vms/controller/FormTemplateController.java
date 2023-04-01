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

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.FormTemplateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping(path="/api/formtemplate", produces="application/json")
public class FormTemplateController {

    @Autowired
    private FormTemplateService service;

    Logger logger = LogManager.getLogger(FormTemplateController.class);

    // Returns all Forms
    @Operation(summary = "Get all form templates", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormTemplate.class))),
        @ApiResponse(responseCode = "404", description = "No form templates have been created.", content = @Content)
    })
    @GetMapping("/all")
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
    @Operation(summary = "Get form by form name", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormTemplate.class))),
        @ApiResponse(responseCode = "404", description = "Form with inputted form name does not exist.", content = @Content)
    })
    @GetMapping("/{FTNo}")
    @ResponseBody
    public ResponseEntity<?> getFormTemplatesByFTNo(@PathVariable String FTNo) {
        FormTemplate getForm;
        try {
            getForm = service.getFormTemplateByFTNo(FTNo);
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
    @Operation(summary = "Edit form template", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormTemplate.class))),
        @ApiResponse(responseCode = "404", description = "Form with inputted id does not exist.", content = @Content)
    })
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
    @Operation(summary = "Create form template", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormTemplate.class))),
        @ApiResponse(responseCode = "404", description = "Form template already exist.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<?> createFormTemplate(@RequestBody FormTemplate formTemplate) {
        String formNo = formTemplate.getFormNo();
        try {
            service.createFormTemplate(formTemplate); 
            return ResponseHandler.generateResponse("Created " + formNo + " successfully.", HttpStatus.OK, null);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } 
    }

    @Operation(summary = "Delete form template by form template no", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormTemplate.class))),
        @ApiResponse(responseCode = "404", description = "User does not exist.", content = @Content)
    })
    @DeleteMapping("/{FTNo}")
    @ResponseBody
    public ResponseEntity<?> deleteFormTemplate(@PathVariable String FTNo) {
        try {
            service.deleteFormTemplate(FTNo); 
            return ResponseHandler.generateResponse("Deleted " + FTNo + " successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @Operation(summary = "Heathcheck")
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> healthCheck() {
        return ResponseHandler.generateResponse("FormTemplateController connected", HttpStatus.OK, null);
    }

}
