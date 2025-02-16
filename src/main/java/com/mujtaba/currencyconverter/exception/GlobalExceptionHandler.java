package com.mujtaba.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SwopApiException.class)
    public ResponseEntity<ErrorResponse>handleSwopApiException(SwopApiException swopApiException){
        ErrorResponse errorResponse = new ErrorResponse(swopApiException.getStatusCode(), swopApiException.getMessage(), "Check your API plan or permissions");
        return new ResponseEntity<ErrorResponse>( errorResponse, HttpStatusCode.valueOf(swopApiException.getStatusCode()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        String errorMessage = ex.getAllErrors().get(0).getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                "Invalid request parameters"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
