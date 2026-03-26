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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Get All User
    @GetMapping
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
    public ResponseEntity<?> getUserById(@PathVariable int id){
        try{
            User user = userService.getUserById(id);
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
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        try{
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Get Users By Roles
    @GetMapping("/roles/{role}")
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
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserUpdateRequest request){
        try{
            userService.updateUser(id,request);
            return ResponseEntity.ok(request);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }

    //Delete User By ID
    @DeleteMapping("/{id}")
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
    public ResponseEntity<?> changeUserPassword(@PathVariable int id, @RequestBody UserChangePasswordRequest request){
        try{
            userService.changePassword(id,request.getOldPassword(),request.getNewPassword());
            return ResponseEntity.ok("Password changed");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error : "+e.getMessage());
        }
    }
}