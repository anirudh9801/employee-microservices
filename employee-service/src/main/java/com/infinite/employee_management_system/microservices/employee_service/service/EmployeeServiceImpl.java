package com.infinite.employee_management_system.microservices.employee_service.service;

import com.infinite.employee_management_system.microservices.employee_service.client.AuthClient;
import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeRequestDTO;
import com.infinite.employee_management_system.microservices.employee_service.dto.EmployeeResponseDTO;
import com.infinite.employee_management_system.microservices.employee_service.dto.LoginResponseDTO;
import com.infinite.employee_management_system.microservices.employee_service.entity.Employee;
import com.infinite.employee_management_system.microservices.employee_service.mapper.EmployeeMapper;
import com.infinite.employee_management_system.microservices.employee_service.repository.EmployeeRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AuthClient authClient;
    private final AuthServiceCaller authServiceCaller;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, AuthClient authClient, AuthServiceCaller authServiceCaller) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.authClient = authClient;
        this.authServiceCaller = authServiceCaller;
    }

    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO request) {
        if(employeeRepository.existsByEmployeeEmail(request.getEmployeeEmail())){
            throw new RuntimeException("Employee already exists with this email");
        }

        LoginResponseDTO currentUser = authServiceCaller.getCurrentUser();
        log.info("Employee created by: "+ currentUser.getEmail());
        Employee employee = employeeMapper.toEntity(request);

        System.out.println("Request: " + request);
        System.out.println("Mapped Entity: " + employee);

        employee.setEmployeeId(generateEmployeeId());
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toDTO(saved);
    }

    private String generateEmployeeId() {
        String datePart = LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomPart = UUID.randomUUID()
                .toString().substring(0,3)
                .toUpperCase();
        return "EMP-" + datePart + "-" +randomPart;
    }

    @Override
    public List<EmployeeResponseDTO> getAll() {
        return employeeRepository.findAll()
                .stream().map(employeeMapper::toDTO)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getById(String id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Employee not found"));
        return employeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeResponseDTO update(String id, EmployeeRequestDTO request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Employee not found"));

        if(!employee.getEmployeeEmail().equals(request.getEmployeeEmail())
            && employeeRepository.existsByEmployeeEmail(request.getEmployeeEmail())){
            throw new RuntimeException("Email already in use");
        }

        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeEmail(request.getEmployeeEmail());
        employee.setEmployeePhoneNo(request.getEmployeePhoneNo());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());

        Employee updated = employeeRepository.save(employee);
        return employeeMapper.toDTO(updated);
    }

    @Override
    public List<EmployeeResponseDTO> getAllActive() {
        return employeeRepository.findByActiveTrue()
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    @Override
    public void restore(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Employee not found"));
        employee.setActive(true);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponseDTO getByEmail(String email) {
        Employee employee = employeeRepository.findByEmployeeEmail(email)
                .orElseThrow(()-> new RuntimeException("Employee not found"));
        return employeeMapper.toDTO(employee);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmployeeEmail(email);
    }

    @Override
    public void delete(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Employee not found"));
        employee.setActive(false);
        employeeRepository.save(employee);
    }
}
