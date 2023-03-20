package com.g2t3.vms.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping(path="/api/users", produces="application/json")
public class UserController {
    
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        ArrayList <User> users = new ArrayList<>();
        // TO BE EDITED
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, users);
    }
    
    @GetMapping("/{UID}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable String FTID) {
        // TO BE EDITED
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
    }

    // NOTE:  PUT is defined to assume idempotency
    @PutMapping("/{UID}")
    @ResponseBody
    public ResponseEntity<?> updateUserById(@RequestBody User user, @PathVariable String UID) {
        // TO BE EDITED
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // TO BE EDITED
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
    }

    @DeleteMapping("/{UID}")
    @ResponseBody
    public ResponseEntity<?> createUser(@PathVariable String FTID) {
        // TO BE EDITED
        return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
    }
}
