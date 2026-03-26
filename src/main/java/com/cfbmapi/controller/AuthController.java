package com.cfbmapi.controller;

import com.cfbmapi.dto.LoginRequest;
import com.cfbmapi.dto.LoginResponse;
import com.cfbmapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        try {
            // Call AuthService to verify email & password
            LoginResponse response = authService.login(request);

            // If login successful (user found)
            if (response.getRole() != null && !response.getMessage().contains("failed")) {
                // Store user info in session
                // Spring Security will create JSESSIONID cookie automatically
                session.setAttribute("userId", response.getUserId());
                session.setAttribute("email", response.getEmail());
                session.setAttribute("role", response.getRole());

                return ResponseEntity.ok(response);
            } else {
                // Login failed
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // Invalidate session (Spring Security removes from memory)
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}