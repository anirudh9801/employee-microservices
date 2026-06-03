package com.infinite.employee_management_system.microservices.employee_service.mapper;

import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeRequestDTO;
import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeResponseDTO;
import com.infinite.employee_management_system.microservices.employee_service.dto.LoginResponseDTO;
import com.infinite.employee_management_system.microservices.employee_service.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Employee toEntity(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO toDTO(Employee employee);
}
