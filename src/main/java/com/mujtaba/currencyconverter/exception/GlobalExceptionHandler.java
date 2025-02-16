package com.mujtaba.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SwopApiException.class)
    public ResponseEntity<ErrorResponse>handleSwopApiException(SwopApiException swopApiException){
        ErrorResponse errorResponse = new ErrorResponse(swopApiException.getStatusCode(), swopApiException.getMessage(), "Check your API plan or permissions");
        return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatusCode.valueOf(swopApiException.getStatusCode()));
    }
}
