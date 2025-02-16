package com.mujtaba.currencyconverter.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class SwopAvailableCurrencyResponse {
    @JsonProperty("code")
    private String code;

    @JsonProperty("numeric_code")
    private String numericCode;

    @JsonProperty("decimal_digits")
    private int decimalDigits;

    @JsonProperty("name")
    private String name;

    @JsonProperty("active")
    private Boolean active;

}
