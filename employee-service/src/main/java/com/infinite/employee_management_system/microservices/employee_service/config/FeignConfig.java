package com.infinite.employee_management_system.microservices.employee_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            var authentication = SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            if (authentication != null && authentication.getCredentials() != null) {

                String token = authentication.getCredentials().toString();

                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
