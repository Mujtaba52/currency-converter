package com.mujtaba.currencyconverter.integration;

import com.mujtaba.currencyconverter.integration.model.SwopCurrencyConversionResponse;
import com.mujtaba.currencyconverter.integration.model.SwopAvailableCurrencyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SwopService {

    private final WebClient webClient;
    @Value("${swop.api.url}")
    private String swopUrl;
    @Value("${swop.api.key}")
    private String swopApiKey;
    public SwopService(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient.builder().baseUrl("https://swop.cx/rest").build();
    }

    public List<SwopAvailableCurrencyResponse> getAvailableCurrencies() {
        return this.webClient.get()
                .uri("/currencies")
                .header("Authorization", "ApiKey " + swopApiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SwopAvailableCurrencyResponse>>() {}).block();
    }

    public SwopCurrencyConversionResponse currencyConversion(String baseCurrency, String quoteCurrency, BigDecimal amount) {
        return this.webClient.get()
                .uri("/rates/" + baseCurrency + "/" + quoteCurrency)
                .header("Authorization", "ApiKey " + swopApiKey)
                .retrieve()
                .bodyToMono(SwopCurrencyConversionResponse.class).block();

    }
}
