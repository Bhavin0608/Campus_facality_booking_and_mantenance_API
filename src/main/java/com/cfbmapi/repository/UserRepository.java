package com.cfbmapi.repository;

import com.cfbmapi.entity.User;
import com.cfbmapi.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findById(int id);
    User findByEmail(String email);
    List<User> findAllByRole(UserRole role);
    boolean existsByEmail(String email);

}
