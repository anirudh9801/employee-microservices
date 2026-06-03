package com.infinite.employee_management_system.microservices.employee_service.dto;

import com.infinite.employee_management_system.microservices.employee_service.entity.Department;
import com.infinite.employee_management_system.microservices.employee_service.entity.Designation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
    @NotBlank(message = "Employee name is required.")
    private String employeeName;
    @NotBlank(message = "Employee email is required.")
    @Email(message = "Please enter valid email.")
    private String employeeEmail;
    @NotBlank(message = "Employee phone is required.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Enter valid 10-digit phone number.")
    private String employeePhoneNo;
    @NotNull
    private Department department;
    @NotNull
    private Designation designation;
}
