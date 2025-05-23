package com.ayush.auth_service.repository;

import com.ayush.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find user by email (used during login)
    Optional<User> findByEmail(String email);
    
    // Check if email exists (used during registration)
    boolean existsByEmail(String email);
    
}
