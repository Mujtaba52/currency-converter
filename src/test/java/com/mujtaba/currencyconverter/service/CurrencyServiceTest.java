package com.mujtaba.currencyconverter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mujtaba.currencyconverter.dto.AvailableCurrencyDto;
import com.mujtaba.currencyconverter.dto.CurrencyDto;
import com.mujtaba.currencyconverter.exception.SwopApiException;
import com.mujtaba.currencyconverter.integration.SwopService;
import com.mujtaba.currencyconverter.integration.model.SwopAvailableCurrencyResponse;
import com.mujtaba.currencyconverter.integration.model.SwopCurrencyConversionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

class CurrencyServiceTest {

    @Mock
    private SwopService swopService;

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(applicationContext.getBean(CurrencyService.class)).thenReturn(currencyService);
    }

    @Test
    void testSuccessfulCurrencyConversion() {
        String baseCurrency = "EUR";
        String quoteCurrency = "USD";
        BigDecimal amount = BigDecimal.ONE;
        BigDecimal expectedRate = BigDecimal.valueOf(1.1);

        // Creating a mock response with all required fields
        SwopCurrencyConversionResponse mockResponse = new SwopCurrencyConversionResponse();
        mockResponse.setBaseCurrency(baseCurrency);
        mockResponse.setQuoteCurrency(quoteCurrency);
        mockResponse.setQuote(expectedRate);
        mockResponse.setDate(new Date());

        // Mocking SwopService behavior
        when(swopService.currencyConversion(baseCurrency, quoteCurrency, amount)).thenReturn(mockResponse);

        // Mocking available currencies list to prevent the "Currency not available" error
        SwopAvailableCurrencyResponse availableCurrency = new SwopAvailableCurrencyResponse();
        availableCurrency.setCode("USD");
        availableCurrency.setActive(true);

        when(swopService.getAvailableCurrencies()).thenReturn(List.of(availableCurrency));

        CurrencyDto result = currencyService.currencyConverter(baseCurrency, quoteCurrency, amount);

        assertNotNull(result);
        assertEquals(quoteCurrency, result.getQuoteCurrency());
        assertEquals(expectedRate.multiply(amount), result.getConvertedAmount());
    }


    @Test
    void testInvalidBaseCurrency() {
        String baseCurrency = "USD";
        String quoteCurrency = "EUR";
        BigDecimal amount = BigDecimal.ONE;

        SwopApiException exception = assertThrows(
                SwopApiException.class,
                () -> currencyService.currencyConverter(baseCurrency, quoteCurrency, amount)
        );

        assertEquals(400, exception.getStatusCode());
        assertEquals("Please upgrade your account to perform this request.", exception.getMessage());
    }

    @Test
    void testNonExistentQuoteCurrency() {
        String baseCurrency = "EUR";
        String quoteCurrency = "US";
        BigDecimal amount = BigDecimal.ONE;

        SwopAvailableCurrencyResponse availableCurrency = new SwopAvailableCurrencyResponse();
        availableCurrency.setCode("USD");
        availableCurrency.setActive(true);

        SwopApiException exception = assertThrows(
                SwopApiException.class,
                () -> currencyService.currencyConverter(baseCurrency, quoteCurrency, amount)
        );

        assertEquals(400, exception.getStatusCode());
        assertEquals("Currency US is not available", exception.getMessage());
    }

    @Test
    void testNegativeAmountConversion() {
        String baseCurrency = "EUR";
        String quoteCurrency = "USD";
        BigDecimal amount = BigDecimal.valueOf(-1);

        SwopAvailableCurrencyResponse availableCurrency = new SwopAvailableCurrencyResponse();
        availableCurrency.setCode("USD");
        availableCurrency.setActive(true);

        SwopApiException exception = assertThrows(
                SwopApiException.class,
                () -> currencyService.currencyConverter(baseCurrency, quoteCurrency, amount)
        );

        assertEquals(400, exception.getStatusCode());
    }
}
