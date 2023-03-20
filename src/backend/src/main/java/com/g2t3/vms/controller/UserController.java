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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.g2t3.vms.exception.UserNotFoundException;
import com.g2t3.vms.model.User;
import com.g2t3.vms.model.UserType;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user", produces = "application/json")
public class UserController {
    
    @Autowired
    private UserService service;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        ArrayList <User> users = new ArrayList<>();
        try {
            users = service.getAllUsers();
        } catch (UserNotFoundException e) {
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
            user = service.getUserById(userId);
        } catch (UserNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
    }

    @GetMapping("/type/{userType}")
    @ResponseBody
    public ResponseEntity<?> getAllUsers(@PathVariable UserType userType) {
        ArrayList <User> users = new ArrayList<>();
        try {
            users = service.getUsersByType(userType);
        } catch (UserNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, users);
    }

    @GetMapping("/email/{userEmail}")
    @ResponseBody
    public ResponseEntity<?> getUserByEmail(@PathVariable String userEmail) {
        User user = null;
        try {
            user = service.getUserByEmail(userEmail);
        } catch (UserNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.NOT_ACCEPTABLE, null);
        }
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User user) {
        try {
            service.updateUser(userId, user); 
            return ResponseHandler.generateResponse("Updated User " + userId + "'s details successfully.", HttpStatus.OK, null);
        } catch (UserNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) { 
        try {
            service.deleteUser(userId);
            return ResponseHandler.generateResponse("Deleted User " + userId + " successfully.", HttpStatus.OK, null);
        } catch (UserNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    } 
}
