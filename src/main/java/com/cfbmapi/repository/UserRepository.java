package com.cfbmapi.repository;

import com.cfbmapi.entity.User;
import com.cfbmapi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
    List<User> findAllByRole(UserRole role);
    boolean existsByEmail(String email);

}
