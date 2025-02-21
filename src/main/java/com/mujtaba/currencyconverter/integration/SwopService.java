package com.mujtaba.currencyconverter.integration;

import com.mujtaba.currencyconverter.exception.SwopApiException;
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
    public SwopService(WebClient.Builder webClientBuilder,  @Value("${swop.api.url}") String swopUrl) {
        this.webClient = WebClient.builder().baseUrl(swopUrl).build();
    }

    public List<SwopAvailableCurrencyResponse> getAvailableCurrencies() {
        return this.webClient.get()
                .uri("/currencies")
                .header("Authorization", "ApiKey " + swopApiKey)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    int statusCode = response.statusCode().value();
                                    return Mono.error(new SwopApiException(errorBody, statusCode));
                                })
                )
                .bodyToMono(new ParameterizedTypeReference<List<SwopAvailableCurrencyResponse>>() {}).block();
    }

    public SwopCurrencyConversionResponse currencyConversion(String baseCurrency, String quoteCurrency, BigDecimal amount) {
        return this.webClient.get()
                .uri("/rates/" + baseCurrency + "/" + quoteCurrency)
                .header("Authorization", "ApiKey " + swopApiKey)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    int statusCode = response.statusCode().value();
                                    return Mono.error(new SwopApiException(errorBody, statusCode));
                                })
                )
                .bodyToMono(SwopCurrencyConversionResponse.class).block();

    }
}
