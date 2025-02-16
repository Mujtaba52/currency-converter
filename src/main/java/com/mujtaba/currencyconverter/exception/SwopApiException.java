package com.mujtaba.currencyconverter.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public class SwopApiException extends RuntimeException {
    private final int statusCode;
    public SwopApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
