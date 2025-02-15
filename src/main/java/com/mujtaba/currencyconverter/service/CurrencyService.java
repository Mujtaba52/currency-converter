package com.mujtaba.currencyconverter.service;

import com.mujtaba.currencyconverter.dto.AvailableCurrencyDto;
import com.mujtaba.currencyconverter.dto.CurrencyDto;
import com.mujtaba.currencyconverter.integration.SwopService;
import com.mujtaba.currencyconverter.integration.model.SwopAvailableCurrencyResponse;
import com.mujtaba.currencyconverter.integration.model.SwopCurrencyConversionResponse;
import com.mujtaba.currencyconverter.util.SwopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CurrencyService {

    private static Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    private SwopService swopService;

    public CurrencyService(SwopService swopService) {
        this.swopService = swopService;
    }

    @Cacheable(value = "currency-cache")
    public List<AvailableCurrencyDto> getAvailableCurrencies(){
        logger.info("Get available currencies");
        List<SwopAvailableCurrencyResponse> availableCurrencies = swopService.getAvailableCurrencies();
        return availableCurrencies.stream().map(SwopMapper::fetchCurrencyMapper).toList();
    }

    @Cacheable(value = "currency-cache", key = "#baseCurrency + '-' + #quoteCurrency + '-' + #amount")
    public CurrencyDto currencyConverter(String baseCurrency, String quoteCurrency, BigDecimal amount){
        SwopCurrencyConversionResponse swopCurrencyConversionResponse = swopService.currencyConversion(baseCurrency, quoteCurrency, amount);
        return currencyConversion(swopCurrencyConversionResponse.getBaseCurrency(), swopCurrencyConversionResponse.getQuoteCurrency(), swopCurrencyConversionResponse.getQuote(), amount);
    }

    private CurrencyDto currencyConversion(String baseCurrency, String quoteCurrency, BigDecimal quote, BigDecimal amount){
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setBaseCurrency(baseCurrency);
        currencyDto.setQuoteCurrency(quoteCurrency);
        currencyDto.setConvertedAmount(quote.multiply(amount));
        return currencyDto;
    }

}
