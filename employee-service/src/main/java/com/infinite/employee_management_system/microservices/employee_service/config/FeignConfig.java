package com.infinite.employee_management_system.microservices.employee_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.web.context.request.*;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {

                HttpServletRequest request = attributes.getRequest();

                String authHeader = request.getHeader("Authorization");

                if (authHeader != null) {
                    requestTemplate.header("Authorization", authHeader);
                }
            }
        };
    }
}
