package com.infinite.employee_management_system.microservices.employee_service.repository;

import com.infinite.employee_management_system.microservices.employee_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Optional<Employee> findByEmployeeEmail(String s);
    boolean existsByEmployeeEmail(String email);
    List<Employee> findByActiveTrue();
}
