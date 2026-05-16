package com.example.auth_system.service;

import com.example.auth_system.entity.User;
import com.example.auth_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Tiêm (Inject) hàm băm
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        // Lấy mật khẩu -> Cho vào hàm băm -> Set lại vào cục user
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        return userRepository.save(user); // Đưa cho Repo để lưu vào DB
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}