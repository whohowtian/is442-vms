package com.g2t3.vms.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ResourceAlreadyExistException extends DataIntegrityViolationException {
    
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
