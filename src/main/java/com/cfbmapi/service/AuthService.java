package com.cfbmapi.service;

import com.cfbmapi.dto.LoginRequest;
import com.cfbmapi.dto.LoginResponse;
import com.cfbmapi.entity.User;
import com.cfbmapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Login user
    public LoginResponse login(LoginRequest request) {
        try {
            // Find user by email
            User user = userRepository.findByEmail(request.getEmail());

            // Check if user is active
            if (!user.isActive()) {
                throw new RuntimeException("User account is inactive");
            }

            // Verify password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid email or password");
            }

            // Return success response
            return new LoginResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getRole()
            );
        } catch (Exception e) {
            return new LoginResponse("Login failed: " + e.getMessage());
        }
    }
}