package com.example.auth_system.controller;

import com.example.auth_system.entity.User;
import com.example.auth_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // Tất cả API trong class này đều bắt đầu bằng /users
public class UserController {

    @Autowired
    private UserService userService;

    // API 1: Tạo người dùng mới (Nhận dữ liệu JSON từ Postman)
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // API 2: Lấy danh sách tất cả người dùng
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //Login
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        // Nhận JSON khách gửi, đưa username và password cho UserService xử lý
        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());

        // Trả về cái vé (JWT) cho khách
        return token;
    }
}