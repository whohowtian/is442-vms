package com.g2t3.vms.controller;

import java.util.ArrayList;
import java.util.Map;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping(path="/api/form", produces="application/json")
public class FormController {

    @Autowired
    private FormService service;

    // Returns all Forms
    @Operation(summary = "Get all forms", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Form.class))),
        @ApiResponse(responseCode = "404", description = "No forms have been created.", content = @Content)
    })
    @GetMapping("/all")
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

    @Operation(summary = "Get form by id", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Form.class))),
        @ApiResponse(responseCode = "404", description = "Form with inputted id does not exist.", content = @Content)
    })
    @GetMapping("{FID}")
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


    @Operation(summary = "Create form", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Form.class))),
        @ApiResponse(responseCode = "404", description = "The inputted form template does not exist.", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<?> createForm(@RequestBody Map<String, String> newFormInfo) {
        try {
            service.createForm(newFormInfo); 
            return ResponseHandler.generateResponse("Created form for " + newFormInfo.get("assigned_vendor_email") + " successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } 
    }

    @Operation(summary = "Edit form", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Form.class))),
        @ApiResponse(responseCode = "404", description = "Form with inputted id does not exist.", content = @Content)
    })
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

    @PostMapping("/action/{action}")
    public ResponseEntity<?> submitForm(@RequestBody Form submittedForm, @PathVariable String action) {
        try {
            service.editForm(submittedForm);
            service.changeStatus(submittedForm, action); 
            return ResponseHandler.generateResponse("Form edited successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } 
    }

    @Operation(summary = "Archive form", description="Form Id and Admin Id are required as input parameters. Form will be archived and the admin who archived it will be saved.",
    responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "User or Form does not exist.", content = @Content)
    })
    @PostMapping("/archive")
    public ResponseEntity<?> archiveForm(@RequestBody Map<String, String> postQuery) {
        try {
            service.archiveForm(postQuery);
            return ResponseHandler.generateResponse("Form archived successfully.", HttpStatus.OK, null);
        }
        catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
        catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
    }

    @GetMapping("/formstatus/{statusID}")
    @ResponseBody
    public ResponseEntity<?> getFormByStatus(@PathVariable String statusID) {
        ArrayList<Form> formsArr;
        try {
            formsArr = service.getFormByStatus(statusID);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, formsArr);
        
    }

    @GetMapping("/vendor/{vendorID}")
    @ResponseBody
    public ResponseEntity<?> getFormByVendor(@PathVariable String vendorID) {
        ArrayList<Form> formsArr;
        try {
            formsArr = service.getFormByVendor(vendorID);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, formsArr);
        
    }

    @Operation(summary = "Heathcheck")
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> healthCheck() {
        return ResponseHandler.generateResponse("FormController connected", HttpStatus.OK, null);
    }
}
