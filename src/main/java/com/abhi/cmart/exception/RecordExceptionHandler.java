package com.abhi.cmart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecordExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RecordNotFoundErrorResponse> handleException(Exception exc){

        RecordNotFoundErrorResponse error = new RecordNotFoundErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),System.currentTimeMillis());

        return new ResponseEntity<RecordNotFoundErrorResponse>(error,HttpStatus.BAD_REQUEST);
    }
}
