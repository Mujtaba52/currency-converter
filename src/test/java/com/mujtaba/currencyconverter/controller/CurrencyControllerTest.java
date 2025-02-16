package com.mujtaba.currencyconverter.controller;

import com.mujtaba.currencyconverter.dto.AvailableCurrencyDto;
import com.mujtaba.currencyconverter.dto.CurrencyDto;
import com.mujtaba.currencyconverter.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CurrencyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    void testGetAvailableCurrencies() throws Exception {
        AvailableCurrencyDto mockCurrency = new AvailableCurrencyDto();
        mockCurrency.setCurrencyCode("USD");
        mockCurrency.setCurrencyName("US Dollar");

        when(currencyService.getAvailableCurrencies()).thenReturn(List.of(mockCurrency));

        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].currencyCode").value("USD"));
    }

    @Test
    void testCurrencyConverterSuccess() throws Exception {
        String baseCurrency = "EUR";
        String quoteCurrency = "USD";
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal convertedAmount = BigDecimal.valueOf(110);

        CurrencyDto mockResponse = new CurrencyDto();
        mockResponse.setBaseCurrency(baseCurrency);
        mockResponse.setQuoteCurrency(quoteCurrency);
        mockResponse.setConvertedAmount(convertedAmount);

        when(currencyService.currencyConverter(baseCurrency, quoteCurrency, amount)).thenReturn(mockResponse);

        mockMvc.perform(get("/convert/EUR/USD")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseCurrency").value("EUR"))
                .andExpect(jsonPath("$.quoteCurrency").value("USD"))
                .andExpect(jsonPath("$.convertedAmount").value(110));
    }

    @Test
    void testInvalidBaseCurrency() throws Exception {
        mockMvc.perform(get("/convert/US/USD")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidQuoteCurrency() throws Exception {
        mockMvc.perform(get("/convert/EUR/US")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNegativeAmountForConversion() throws Exception {
        mockMvc.perform(get("/convert/EUR/USD")
                        .param("amount", "-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDefaultAmountForConversion() throws Exception {
        String baseCurrency = "EUR";
        String quoteCurrency = "USD";
        BigDecimal defaultAmount = BigDecimal.ONE;
        BigDecimal convertedAmount = BigDecimal.valueOf(1.1);

        CurrencyDto mockResponse = new CurrencyDto();
        mockResponse.setBaseCurrency(baseCurrency);
        mockResponse.setQuoteCurrency(quoteCurrency);
        mockResponse.setConvertedAmount(convertedAmount);

        when(currencyService.currencyConverter(baseCurrency, quoteCurrency, defaultAmount)).thenReturn(mockResponse);

        mockMvc.perform(get("/convert/EUR/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.convertedAmount").value(1.1));
    }
}
