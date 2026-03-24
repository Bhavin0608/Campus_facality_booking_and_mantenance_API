package com.cfbmapi.service;

import com.cfbmapi.entity.User;
import com.cfbmapi.entity.UserRole;
import com.cfbmapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // using lombok dependency is injected through constructor
    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(String name, String email, String password, UserRole role){

        try{
            if(userRepository.existsByEmail(email))
                throw new RuntimeException("Email already exits");

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);

            return userRepository.save(user);
        }catch(Exception e){
            throw new RuntimeException("Error in creating User" + e.getMessage());
        }
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found")); // if user is present then returns a user or else null is returned
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void updateUser(int id, User updatedUserDetails){
        try{
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id "+id+" doesn't exists"));

            if(updatedUserDetails.getName() != null)
                user.setName(updatedUserDetails.getName());

            if(updatedUserDetails.getEmail() != null)
                user.setEmail(updatedUserDetails.getEmail());

            userRepository.save(user);
            System.out.println("User is Updated successfully...");
        }catch (Exception e){
            throw new RuntimeException("Error in Updating User " +e.getMessage());
        }
    }

    public void deleteUser(int id){
        try{
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Such User with id "+id+" not exists."));

            userRepository.deleteById(id);
            System.out.println("User is Successfully deleted...");
        }catch (Exception e){
            throw new RuntimeException("Error in deleting the User " +e.getMessage());
        }
    }

    public List<User> getUsersByRole(UserRole role){
        return userRepository.findAllByRole(role);
    }

    public void changePassword(int id, String oldPassword, String newPassword){
        try{
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id "+id+" doesn't exists"));

            if(!passwordEncoder.matches(oldPassword, user.getPassword()))
                throw new RuntimeException("Old Password is not match with your password");

            if(passwordEncoder.matches(newPassword, user.getPassword()))
                throw new RuntimeException("Old password and new password are same try another one");

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            System.out.println("Password is updated successfully...");
        }catch (Exception e){
            throw new RuntimeException("Error in Changing Password "+e.getMessage());
        }
    }
}
