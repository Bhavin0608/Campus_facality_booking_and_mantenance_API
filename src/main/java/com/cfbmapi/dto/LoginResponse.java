package com.cfbmapi.dto;

import com.cfbmapi.entity.UserRole;
import lombok.Data;

@Data
public class LoginResponse {
    private int userId;
    private String email;
    private String name;
    private UserRole role;
    private String message;

    public LoginResponse(int id, String email, String name, UserRole role){
        this.userId = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.message = "Login Successful";
    }

    public LoginResponse(String message){
        this.message = message;
    }
}
