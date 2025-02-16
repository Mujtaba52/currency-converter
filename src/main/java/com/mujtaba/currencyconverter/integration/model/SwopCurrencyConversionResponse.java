package com.mujtaba.currencyconverter.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SwopCurrencyConversionResponse {

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("quote")
    private BigDecimal quote;

    @JsonProperty("date")
    private Date date;
}
