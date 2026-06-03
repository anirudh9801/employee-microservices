package com.infinite.employee_management_system.microservices.employee_service.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String email;
    private String role;
}
