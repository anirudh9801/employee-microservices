package com.infinite.employee_management_system.microservices.employee_service.client;

import com.infinite.employee_management_system.microservices.employee_service.config.FeignConfig;
import com.infinite.employee_management_system.microservices.employee_service.dto.LoginResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:8081",configuration = FeignConfig.class)
public interface AuthClient {
    @GetMapping("/auth/me")
    LoginResponseDTO getLoggedInUser();
}
