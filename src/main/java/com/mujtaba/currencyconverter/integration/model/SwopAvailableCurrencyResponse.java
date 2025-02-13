package com.mujtaba.currencyconverter.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
