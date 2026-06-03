package com.infinite.employee_management_system.microservices.employee_service.controller;

import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeRequestDTO;
import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeResponseDTO;
import com.infinite.employee_management_system.microservices.employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponseDTO create(@RequestBody @Valid EmployeeRequestDTO request){
        return employeeService.create(request);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<EmployeeResponseDTO> getAll(){
        return employeeService.getAllActive();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public EmployeeResponseDTO getById(@PathVariable String id){
        return employeeService.getById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponseDTO update(@PathVariable String id,
                                      @RequestBody @Valid EmployeeRequestDTO request){
        return employeeService.update(id,request);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id){
        employeeService.delete(id);
    }
    @PutMapping("/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public void restore(@PathVariable String id){
        employeeService.restore(id);
    }
    @GetMapping("/email")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public EmployeeResponseDTO getByEmail(@RequestParam String email){
        return employeeService.getByEmail(email);
    }
    @GetMapping("/exists")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public boolean existsByEmail(@RequestParam String email){
        return employeeService.existsByEmail(email);
    }


}
