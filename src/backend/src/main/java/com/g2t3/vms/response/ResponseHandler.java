package com.g2t3.vms.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<?> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", status.value());
            map.put("data", responseObj);
            map.put("message", message);

            return new ResponseEntity<>(map,status);
    }
}
