package com.infinite.employee_management_system.auth_service.mapper;

import com.infinite.employee_management_system.auth_service.dto.LoginRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.LoginResponseDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterResponseDTO;
import com.infinite.employee_management_system.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
//Using MapStruct Library for Mapping between DTO <-> Entity
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "username", ignore = true)
    User toEntity(LoginRequestDTO dto);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "type", constant = "Bearer")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    LoginResponseDTO toResponse(String token, User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    User toUser(RegisterRequestDTO dto);


    default RegisterResponseDTO toRegisterResponse(String msg) {
        RegisterResponseDTO dto = new RegisterResponseDTO();
        dto.setMessage(msg);
        return dto;
    }

    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "type", ignore = true)
    LoginResponseDTO toCurrentUser(User user);

}

