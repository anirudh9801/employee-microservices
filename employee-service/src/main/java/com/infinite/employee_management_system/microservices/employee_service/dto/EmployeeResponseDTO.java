package com.infinite.employee_management_system.microservices.employee_service.dto;

import com.infinite.employee_management_system.microservices.employee_service.entity.Department;
import com.infinite.employee_management_system.microservices.employee_service.entity.Designation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeePhoneNo;
    private Department department;
    private Designation designation;
}
