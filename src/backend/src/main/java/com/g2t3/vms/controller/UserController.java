package com.g2t3.vms.controller;

import java.util.ArrayList;

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

import com.g2t3.vms.enums.UserType;
import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.Admin;
import com.g2t3.vms.model.Approver;
import com.g2t3.vms.model.User;
import com.g2t3.vms.model.Vendor;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user", produces = "application/json")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/vendor")
    public ResponseEntity<?> createVendor(@RequestBody Vendor vendorRequest) {

        try {
            Vendor user = userService.createVendor(vendorRequest);
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@RequestBody Admin adminRequest) {

        try {
            Admin user = userService.createAdmin(adminRequest);
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @PostMapping("/approver")
    public ResponseEntity<?> createAdmin(@RequestBody Approver approverRequest) {

        try {
            Approver user = userService.createApprover(approverRequest);
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        ArrayList <User> users = new ArrayList<>();
        try {
            users = userService.getAllUsers();
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, users);
    }

    @GetMapping("/type/{userType}")
    @ResponseBody
    public ResponseEntity<?> getAllUsers(@PathVariable UserType userType) {
        ArrayList <User> users = new ArrayList<>();
        try {
            users = userService.getUsersByType(userType);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, users);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        User user = null;
        try {
            user = userService.getUserById(userId);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
    }

    @GetMapping("/email/{userEmail}")
    @ResponseBody
    public ResponseEntity<?> getUserByEmail(@PathVariable String userEmail) {
        User user = null;
        try {
            user = userService.getUserByEmail(userEmail);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
    }

    // @PutMapping("/{userId}")
    // public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User user) {
    //     try {
    //         userService.updateUser(userId, user); 
    //         return ResponseHandler.generateResponse("Updated User " + userId + "'s details successfully.", HttpStatus.OK, null);
    //     } catch (ResourceNotFoundException e) {
    //         return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
    //     } catch (DataIntegrityViolationException e){
    //         return ResponseHandler.generateResponse("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
    //     } catch (Exception e) {
    //         return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
    //     }
    // }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) { 
        try {
            userService.deleteUserByEmail(email);
            return ResponseHandler.generateResponse("Deleted User with " + email + " successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    } 

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) { 
        try {
            userService.deleteUser(userId);
            return ResponseHandler.generateResponse("Deleted User " + userId + " successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    } 
}
