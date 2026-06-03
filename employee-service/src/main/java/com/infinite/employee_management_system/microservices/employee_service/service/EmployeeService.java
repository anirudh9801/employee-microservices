package com.infinite.employee_management_system.microservices.employee_service.service;

import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeRequestDTO;
import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO create(EmployeeRequestDTO request);
    List<EmployeeResponseDTO> getAll(); //all records
    EmployeeResponseDTO getById(String id);
    EmployeeResponseDTO update(String id, EmployeeRequestDTO request);
    List<EmployeeResponseDTO> getAllActive();//active only
    void restore(String id);
    EmployeeResponseDTO getByEmail(String email);
    boolean existsByEmail(String email);
    void delete(String id);
}
