package com.example.auth_system.exception;

import com.example.auth_system.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

@RestControllerAdvice // Đánh dấu đây là Tổng đài bắt lỗi toàn cục
public class GlobalExceptionHandler {

    // Bắt toàn bộ lỗi RuntimeException (Ví dụ: Lỗi "Sai mật khẩu", "User không tồn tại" mà bạn viết trong UserService)
    // Bắt lỗi Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        // Trích xuất lấy đúng cái câu chữ báo lỗi ("Tên đăng nhập không được để trống!", v.v.)
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Bạn có thể viết thêm các hàm @ExceptionHandler khác ở đây để bắt lỗi NullPointer, SQL Exception...
}