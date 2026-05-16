package com.example.auth_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Bật tính năng bảo mật cho Web
public class SecurityConfig {

    // 1. Lấy hàm Bcrypt và đánh dấu @Bean để Spring Boot quản lý
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Viết luật cho Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt tính năng bảo vệ chống giả mạo web cũ liên quan đến session/cookie (Vì API không cần)
                .authorizeHttpRequests(auth -> auth
                        // Cho phép tất cả mọi người được gọi API Đăng ký (Tạo user mới)
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        // Cho phép tất cả mọi người được gọi API Đăng nhập
                        .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                        // RẤT QUAN TRỌNG: Mở cửa cho Spring Boot hiển thị thông báo lỗi thật
                        .requestMatchers("/error").permitAll()

                        // Bắt buộc đăng nhập với mọi API còn lại (VD: Xem danh sách user)
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}