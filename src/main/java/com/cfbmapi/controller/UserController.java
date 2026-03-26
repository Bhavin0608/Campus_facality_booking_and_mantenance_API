package com.cfbmapi.controller;

import com.cfbmapi.dto.UserChangePasswordRequest;
import com.cfbmapi.dto.UserRegisterRequest;
import com.cfbmapi.dto.UserUpdateRequest;
import com.cfbmapi.entity.User;
import com.cfbmapi.entity.UserRole;
import com.cfbmapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Get All User
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(){
        try{
            List<User> users = userService.getAllUser();
            return ResponseEntity.ok(users); // 200 Status (OK Status)
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Get User By ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','STUDENT')")
    public ResponseEntity<?> getUserById(@PathVariable int id, Authentication auth){
        try{
            User user = userService.getUserById(id);
            if(!user.getEmail().equals(auth.getName()))
                return ResponseEntity.badRequest().body("Access Denied");
            return ResponseEntity.ok(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Register a New User
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest request){
        try{
            User user = userService.registerUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Get User By Email
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','STUDENT')")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email, Authentication auth){
        try{
            if(!email.equals(auth.getName()))
                return ResponseEntity.badRequest().body("Access Denied");
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Get Users By Roles
    @GetMapping("/roles/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsersByRole(@PathVariable UserRole role){
        try{
            List<User> users = userService.getUsersByRole(role);
            return ResponseEntity.ok(users);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Update User by ID
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','STUDENT')")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserUpdateRequest request, Authentication auth){
        try{
            if(!userService.getUserById(id).getEmail().equals(auth.getName()))
                return ResponseEntity.badRequest().body("Access Denied");

            userService.updateUser(id,request);
            return ResponseEntity.ok(request);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Delete User By ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok("Deleted");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Change-Password
    @PutMapping("/{id}/change-password")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','STUDENT')")
    public ResponseEntity<?> changeUserPassword(@PathVariable int id, @RequestBody UserChangePasswordRequest request ,Authentication auth){
        try{
            if(!userService.getUserById(id).getEmail().equals(auth.getName()))
                return ResponseEntity.badRequest().body("Access Denied");

            userService.changePassword(id,request.getOldPassword(),request.getNewPassword());
            return ResponseEntity.ok("Password changed");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }
}