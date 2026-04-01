package com.example.auth_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Đánh dấu đây là một bảng trong Database
@Table(name = "users") // Đặt tên bảng là "users"
@Data // Tự động tạo Getter, Setter nhờ thư viện Lombok
public class User {

    @Id // Đánh dấu đây là Khóa chính (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID (1, 2, 3...)
    private Long id;

    @Column(unique = true, nullable = false) // Không được trùng, không được để trống
    private String username;

    @Column(nullable = false)
    private String password;

    private String role; // Ví dụ: ADMIN hoặc USER
}