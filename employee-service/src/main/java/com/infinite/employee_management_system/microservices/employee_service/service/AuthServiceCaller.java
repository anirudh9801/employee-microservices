package com.infinite.employee_management_system.microservices.employee_service.service;

import com.infinite.employee_management_system.microservices.employee_service.client.AuthClient;
import com.infinite.employee_management_system.microservices.employee_service.dto.LoginResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceCaller {
    private final AuthClient authClient;

    public AuthServiceCaller(AuthClient authClient) {
        this.authClient = authClient;
    }
    @CircuitBreaker(name = "authService", fallbackMethod = "fallbackGetCurrentUser")
    @Retry(name = "authService" , fallbackMethod = "fallbackGetCurrentUser")
    public LoginResponseDTO getCurrentUser(){
        log.info("calling Auth Service.");
        return authClient.getLoggedInUser();
    }
    public LoginResponseDTO fallbackGetCurrentUser(Throwable t){
        log.error("Auth Service fallback triggered", t);
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setEmail("UNKNOWN");
        dto.setRole("UNKNOWN");
        return dto;
    }
}
