package com.example.auth_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity // Đánh dấu đây là một bảng trong Database
@Table(name = "users") // Đặt tên bảng là "users"
@Data // Tự động tạo Getter, Setter nhờ thư viện Lombok
public class User {

    @Id // Đánh dấu đây là Khóa chính (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID (1, 2, 3...)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống!")
    @Size(min = 3, max = 20, message = "Tên đăng nhập phải từ 3 đến 20 ký tự")
    @Column(unique = true, nullable = false) // Không được trùng, không được để trống
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    @Column(nullable = false)
    private String password;

    private String role; // Ví dụ: ADMIN hoặc USER
}