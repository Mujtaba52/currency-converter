package com.mujtaba.currencyconverter.util;

import com.mujtaba.currencyconverter.dto.AvailableCurrencyDto;
import com.mujtaba.currencyconverter.dto.CurrencyDto;
import com.mujtaba.currencyconverter.integration.model.SwopAvailableCurrencyResponse;

public class SwopMapper {

    public static AvailableCurrencyDto fetchCurrencyMapper(SwopAvailableCurrencyResponse swopAvailableCurrencyResponse){
        AvailableCurrencyDto availableCurrencyDto = new AvailableCurrencyDto();
        availableCurrencyDto.setCurrencyCode(swopAvailableCurrencyResponse.getCode());
        availableCurrencyDto.setCurrencyName(swopAvailableCurrencyResponse.getName());
        return availableCurrencyDto;
    }

}
