package com.beour.beourminicrud.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SpaceNotFoundException.class)
    public ResponseEntity<HashMap<String, Object>> handleSpaceNotFoundException(SpaceNotFoundException ex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", HttpStatus.NOT_FOUND.value());
        map.put("error", "Not Found");
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<HashMap<String, Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", HttpStatus.CONFLICT.value());
        map.put("error", "Email Already Exists");
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }
}
