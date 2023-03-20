package com.g2t3.vms.exception;

public class UserNotFoundException extends NullPointerException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
}
