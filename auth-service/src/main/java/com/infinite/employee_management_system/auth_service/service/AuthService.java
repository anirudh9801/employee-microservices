package com.infinite.employee_management_system.auth_service.service;


import com.infinite.employee_management_system.auth_service.dto.LoginRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.LoginResponseDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterResponseDTO;
import com.infinite.employee_management_system.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    RegisterResponseDTO register(RegisterRequestDTO request);
    LoginResponseDTO getLoggedUser(Authentication authentication);
}
