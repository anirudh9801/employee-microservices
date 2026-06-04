package com.infinite.employee_management_system.api_gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;
    //  Public endpoints list
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/auth/login",
            "/api/auth/register"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //Skip endpoints
        String path = exchange.getRequest().getURI().getPath();
        if(PUBLIC_ENDPOINTS.contains(path)){
            log.info("Public endpoint, skipping JWT validation: {}", path);
            return chain.filter(exchange);
        }
        //Read Authorization Header
        String authHeader = exchange.getRequest()
                .getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        //Validate Header
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            log.error("Missing or invalid Authorization header");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String token = authHeader.substring(7);
        try {
            //Validate token
            if(!jwtUtil.validateToken(token)){
                throw new RuntimeException("Token expired or invalid");
            }
            //Extract data
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);
            log.info("Authenticated user: {}",email);

            //Adding headers for downstream services
             ServerWebExchange modifiedExchange = exchange.mutate()
                     .request(builder -> builder
                             .header("X-User-Email",email)
                             .header("X-User-Role",role)
                     )
                     .build();
             //continue request
             return chain.filter(modifiedExchange);
        } catch (Exception e){

            log.error("JWT validation failed: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();

        }
    }
}
