package com.victor.cursospring.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.victor.cursospring.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourcesExceptionHandler {
    
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound (ObjectNotFoundException e,
            HttpServletRequest request) {
        
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),
                e.getMessage(), System.currentTimeMillis());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}