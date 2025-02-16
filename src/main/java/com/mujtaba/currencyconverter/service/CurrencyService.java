package com.mujtaba.currencyconverter.service;

import com.mujtaba.currencyconverter.dto.AvailableCurrencyDto;
import com.mujtaba.currencyconverter.dto.CurrencyDto;
import com.mujtaba.currencyconverter.exception.SwopApiException;
import com.mujtaba.currencyconverter.integration.SwopService;
import com.mujtaba.currencyconverter.integration.model.SwopAvailableCurrencyResponse;
import com.mujtaba.currencyconverter.integration.model.SwopCurrencyConversionResponse;
import com.mujtaba.currencyconverter.util.SwopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CurrencyService {

    private static Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private final ApplicationContext applicationContext;
    private SwopService swopService;

    public CurrencyService(ApplicationContext applicationContext,SwopService swopService) {
        this.applicationContext = applicationContext;
        this.swopService = swopService;
    }

    @Cacheable(value = "currency-cache")
    public List<AvailableCurrencyDto> getAvailableCurrencies(){
        logger.info("Get available currencies");
        List<SwopAvailableCurrencyResponse> availableCurrencies = swopService.getAvailableCurrencies();
        return availableCurrencies.stream().filter(SwopAvailableCurrencyResponse::getActive).map(SwopMapper::fetchCurrencyMapper).toList();
    }

    @Cacheable(value = "currency-cache", key = "#baseCurrency + '-' + #quoteCurrency + '-' + #amount")
    public CurrencyDto currencyConverter(String baseCurrency, String quoteCurrency, BigDecimal amount){
        logger.info("Convert currencies");
        CurrencyService currencyServiceProxy = applicationContext.getBean(CurrencyService.class);
        if(!baseCurrency.equals("EUR")) throw new SwopApiException("Please upgrade your account to perform this request.", 400);

        if (!currencyExists(quoteCurrency, currencyServiceProxy)) {
            throw new SwopApiException("Currency " + quoteCurrency + " is not available", 400);
        }

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
    private boolean currencyExists(String quoteCurrency, CurrencyService currencyServiceProxy) {
        return currencyServiceProxy.getAvailableCurrencies()
                .stream()
                .anyMatch(currency -> currency.getCurrencyCode().equals(quoteCurrency));
    }

}
