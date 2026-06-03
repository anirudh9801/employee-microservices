package com.infinite.employee_management_system.auth_service.dto;

import com.infinite.employee_management_system.auth_service.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "Please enter username.")
    private String username;
    @NotBlank(message = "Email is required.")
    @Email(message = "Enter Valid Email")
    private String email;
    @NotBlank(message = "Please enter correct password")
    private String password;
    @NotNull(message = "Role is required.")
    private Role role;
}
