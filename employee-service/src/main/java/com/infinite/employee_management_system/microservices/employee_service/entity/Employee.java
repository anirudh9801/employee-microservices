package com.infinite.employee_management_system.microservices.employee_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private String employeeId;
    @Column(nullable = false)
    @NotBlank
    private String employeeName;
    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String employeeEmail;
    @Column(nullable = false)
    @NotBlank
    private String employeePhoneNo;
    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = false)
    @NotNull
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @NotNull
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Department department;
    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Designation designation;


    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
