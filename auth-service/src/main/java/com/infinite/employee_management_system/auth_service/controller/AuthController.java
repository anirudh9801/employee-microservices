package com.infinite.employee_management_system.auth_service.controller;

import com.infinite.employee_management_system.auth_service.dto.LoginRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.LoginResponseDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterResponseDTO;
import com.infinite.employee_management_system.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //Login Api
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> getCurrentUser(Authentication authentication){
        return ResponseEntity.ok(authService.getLoggedUser(authentication));
    }

}
