package com.example.auth_system.service;

import com.example.auth_system.entity.User;
import com.example.auth_system.repository.UserRepository;
import com.example.auth_system.util.JwtUtil; // Import anh thợ in vé
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    // Inject UserRepo
    private final UserRepository userRepository;
    // Tiêm (Inject) hàm băm
    private final PasswordEncoder passwordEncoder;
    // Inject Jwt
    private final JwtUtil jwtUtil;

    public User createUser(User user) {
        // Lấy mật khẩu -> Cho vào hàm băm -> Set lại vào cục user
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        return userRepository.save(user); // Đưa cho Repo để lưu vào DB
    }

    // HÀM XỬ LÝ ĐĂNG NHẬP
    public String login(String username, String rawPassword) {
        // 1. Tìm xem có người này trong Database không?
        // Nếu hộp rỗng (không tìm thấy), nó sẽ tự động ném ra lỗi (Throw) luôn!
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Tên đăng nhập không tồn tại!"));

        // 2. Có người rồi thì so sánh mật khẩu (Thô vs Đã băm)
        boolean isMatch = passwordEncoder.matches(rawPassword, user.getPassword());
        if (!isMatch) {
            throw new RuntimeException("Sai mật khẩu!");
        }

        // 3. Đúng hết rồi thì nhờ anh thợ in vé xuất ra cái JWT
        return jwtUtil.generateToken(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}