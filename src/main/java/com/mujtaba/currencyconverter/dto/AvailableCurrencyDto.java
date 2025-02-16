package com.mujtaba.currencyconverter.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class AvailableCurrencyDto {
    String currencyCode;
    String currencyName;
}
