package com.example.auth_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;       // Mã lỗi (VD: 400, 401, 404)
    private String message;       // Thông báo (VD: "Sai mật khẩu!")
    private LocalDateTime timestamp; // Thời gian
}