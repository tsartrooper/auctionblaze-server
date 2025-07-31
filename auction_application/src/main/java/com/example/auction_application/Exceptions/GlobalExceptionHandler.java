package com.example.auction_application.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<String> handleException(Exception ex){
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    // }

    // @ExceptionHandler(RuntimeException.class)
    // public ResponseEntity<String> handleRunTimeException(RuntimeException ex){
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request" );
    // }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex){
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
}
