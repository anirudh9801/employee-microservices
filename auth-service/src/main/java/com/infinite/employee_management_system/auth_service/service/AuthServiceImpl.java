package com.infinite.employee_management_system.auth_service.service;

import com.infinite.employee_management_system.auth_service.dto.LoginRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.LoginResponseDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterRequestDTO;
import com.infinite.employee_management_system.auth_service.dto.RegisterResponseDTO;
import com.infinite.employee_management_system.auth_service.entity.User;
import com.infinite.employee_management_system.auth_service.mapper.UserMapper;
import com.infinite.employee_management_system.auth_service.repository.UserRepository;
import com.infinite.employee_management_system.auth_service.security.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid User or Password");
        }
        String token = jwtUtil.generateToken(user.getEmail(),user.getRole().name());
        return userMapper.toResponse(token,user);
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        Optional<User> exsistingUser = userRepository.findByEmail(request.getEmail());
        if(exsistingUser.isPresent()){
            throw new RuntimeException("User already exists with this email");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toRegisterResponse("User registered successfully.");
    }


    @Override
    public LoginResponseDTO getLoggedUser(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        return userMapper.toCurrentUser(user);
    }


}
