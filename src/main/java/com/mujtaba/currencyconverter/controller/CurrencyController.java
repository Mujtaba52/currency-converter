package com.mujtaba.currencyconverter.controller;

import com.mujtaba.currencyconverter.dto.AvailableCurrencyDto;
import com.mujtaba.currencyconverter.dto.CurrencyDto;
import com.mujtaba.currencyconverter.service.CurrencyService;
import com.mujtaba.currencyconverter.integration.model.SwopCurrencyConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping
@RestController
public class CurrencyController {
    private CurrencyService currencyService;
    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/currencies")
    public List<AvailableCurrencyDto> getAvailableCurrencies(){

        return currencyService.getAvailableCurrencies();
    }
    @GetMapping(value="/convert/{base_currency}/{quote_currency}", produces = "application/json")
    public CurrencyDto currencyConverter(
            @PathVariable("base_currency") String baseCurrency,
            @PathVariable("quote_currency") String quoteCurrency,
            @RequestParam(value = "amount", required = false, defaultValue = "1") BigDecimal amount){
        return currencyService.currencyConverter(baseCurrency, quoteCurrency, amount);
    }

}
