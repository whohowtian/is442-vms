package com.g2t3.vms.exception;

public class ResourceNotFoundException extends NullPointerException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}