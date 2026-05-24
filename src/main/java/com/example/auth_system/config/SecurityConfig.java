package com.example.auth_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Cho phép đăng ký và đăng nhập công khai (Đã sửa dấu /)
                        .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()

                        // Chặng 5: Chỉ ADMIN mới được lấy danh sách người dùng
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")

                        // Bắt buộc xác thực với mọi API còn lại
                        .anyRequest().authenticated()
                )
                // Chặng 4: Đấu nối trạm kiểm tra vé JWT vào trước cổng bảo mật mặc định
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}