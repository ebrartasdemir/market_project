package com.market_p.market_p.error;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFound exc){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
