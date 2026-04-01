package com.example.auth_system.repository;

import com.example.auth_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tự động tạo hàm tìm người dùng theo Username
    Optional<User> findByUsername(String username);
}