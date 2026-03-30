package com.cfbmapi.service;

import com.cfbmapi.dto.UserRegisterRequest;
import com.cfbmapi.dto.UserUpdateRequest;
import com.cfbmapi.entity.User;
import com.cfbmapi.entity.UserRole;
import com.cfbmapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // using lombok dependency is injected through constructor
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserRegisterRequest request){ // only required field are entered using 'dto'

        try{
            if(userRepository.existsByEmail(request.getEmail()))
                throw new RuntimeException("Email already exits");

            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            return userRepository.save(user);
        }catch(Exception e){
            throw new RuntimeException("Error in creating User" + e.getMessage());
        }
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No Such email exists"));
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not Found")); // if user is present then returns a user or else null is returned
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(int id, UserUpdateRequest updatedUserDetails){ // only UserUpdateRequest fields are allowed here using 'dto'
        try{
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id "+id+" doesn't exists"));

            if(updatedUserDetails.getName() != null && !updatedUserDetails.getName().isBlank())
                user.setName(updatedUserDetails.getName());

            if(updatedUserDetails.getEmail() != null && !updatedUserDetails.getEmail().isBlank()){
                if(userRepository.existsByEmail(updatedUserDetails.getEmail()))
                    throw new RuntimeException("This Email is already exists");
                user.setEmail(updatedUserDetails.getEmail());
            }

            userRepository.save(user);
            System.out.println("User is Updated successfully...");
        }catch (Exception e){
            throw new RuntimeException("Error in Updating User " +e.getMessage());
        }
    }

    @Transactional
    public void deleteUser(int id){
        try{
            userRepository.deleteById(id);
            System.out.println("User is Successfully deleted...");
        }catch (Exception e){
            throw new RuntimeException("Error in deleting the User " +e.getMessage());
        }
    }

    public List<User> getUsersByRole(UserRole role){
        return userRepository.findAllByRole(role);
    }

    @Transactional
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
