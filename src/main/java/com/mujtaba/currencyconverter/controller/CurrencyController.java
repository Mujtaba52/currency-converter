package com.mujtaba.currencyconverter.controller;

import com.mujtaba.currencyconverter.dto.AvailableCurrencyDto;
import com.mujtaba.currencyconverter.dto.CurrencyDto;
import com.mujtaba.currencyconverter.service.CurrencyService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping
@RestController
public class CurrencyController {
    private final CurrencyService currencyService;
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
            @PathVariable("base_currency") @Size(min = 3, max = 3, message = "Base currency must be 3 characters") String baseCurrency,
            @PathVariable("quote_currency") @Size(min = 3, max = 3, message = "Quote currency must be 3 characters") String quoteCurrency,
            @RequestParam(value = "amount", required = false, defaultValue = "1") @Min(value = 1, message = "Amount must be at least 1") BigDecimal amount) {
        return currencyService.currencyConverter(baseCurrency, quoteCurrency, amount);
    }

}
