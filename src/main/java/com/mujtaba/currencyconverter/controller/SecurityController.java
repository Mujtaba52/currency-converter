package com.mujtaba.currencyconverter.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(@RequestAttribute("_csrf") CsrfToken csrfToken) {
        return csrfToken;
    }

    @PostMapping("/test-csrf")
    public Map<String, String> testCsrf() {
        return Map.of("message", "csrf authenticated");
    }
}
