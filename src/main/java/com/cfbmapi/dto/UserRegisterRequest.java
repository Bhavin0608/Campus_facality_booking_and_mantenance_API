package com.cfbmapi.dto;

import com.cfbmapi.entity.UserRole;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
