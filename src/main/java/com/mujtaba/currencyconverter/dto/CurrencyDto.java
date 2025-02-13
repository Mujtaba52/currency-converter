package com.mujtaba.currencyconverter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrencyDto {

    private String baseCurrency;

    private String quoteCurrency;

    private BigDecimal convertedAmount;

}
