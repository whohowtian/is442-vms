package com.g2t3.vms.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.g2t3.vms.request.UserLoginRequest;
import com.g2t3.vms.request.UserUpdateRequest;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.EmailService;
import com.g2t3.vms.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user", produces = "application/json")
@Validated
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Creates an account", description="password and enabled attributes are not required as input parameters. ", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserUpdateRequest.class))),
        @ApiResponse(responseCode = "400", description = "A user with the inputted entity UEN or email already exist.", content = @Content),
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserUpdateRequest request) {

        try {

            User user = null;
            if (request.getUserType().equals(UserType.VENDOR)) {
                user = userService.createVendor(request);
            } else if (request.getUserType().equals(UserType.ADMIN)) {
                user = userService.createAdmin(request);
            } else if (request.getUserType().equals(UserType.APPROVER)) {
                user = userService.createApprover(request);
            }
            // emailService.sendAccountConfirmationEmail(user);

            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, user);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error Occured: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @Operation(summary = "Get all user details", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "No users have been created.", content = @Content)
    })
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

    @Operation(summary = "Get users by user type", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "No user of inputted user type has been created.", content = @Content)
    })
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

    @Operation(summary = "Get user by id", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User does not exist.", content = @Content)
    })
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

    @Operation(summary = "Get user by email", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User does not exist.", content = @Content)
    })
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

    @Operation(summary = "Update user details", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User does not exist.", content = @Content)
    })
    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest user) {
        
        try {
            UserType prevType = (userService.getUserById(user.getUserId())).getUserType();
            UserType type = user.getUserType();

            if (type.equals(UserType.ADMIN)) {
                userService.updateAdmin(prevType, user);
            } else if (type.equals(UserType.APPROVER)) {
                userService.updateApprover(prevType, user);
            } else if (type.equals(UserType.VENDOR)) {
                userService.updateVendor(prevType, user);
            }
            
            return ResponseHandler.generateResponse("Updated User with email of " + user.getEmail() + " successfully.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @Operation(summary = "Update user password", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User does not exist.", content = @Content)
    })
    @PutMapping("/activate-account")
    public ResponseEntity<?> setPassword(@RequestBody UserLoginRequest user) {

        try {
            User newUser = userService.activateAccount(user);            
            return ResponseHandler.generateResponse("Updated password for user with email of " + user.getEmail() + " successfully.", HttpStatus.OK, newUser);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (DataIntegrityViolationException e){
            return ResponseHandler.generateResponse("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Internal Server Error: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @Operation(summary = "Delete user by email", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User does not exist.", content = @Content)
    })
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


    @Operation(summary = "Delete user by id", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User does not exist.", content = @Content)
    })
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
