package com.login_oop.oop_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.login_oop.oop_backend.dto.LoginRequest;
import com.login_oop.oop_backend.dto.RegisterRequest;
import com.login_oop.oop_backend.models.User;
import com.login_oop.oop_backend.services.AuthService;

import java.util.Map;

// Controller สำหรับจัดการ login และ register
// @CrossOrigin ต้องใส่ไม่งั้น frontend เรียก API ไม่ได้
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    // ใช้ AuthService เพื่อทำงานจริงๆ
    @Autowired
    private AuthService authService;

    /**
     * API สำหรับ login
     * เรียกผ่าน POST /login
     * รับ username และ password จาก frontend แล้วเช็คว่าถูกต้องหรือไม่
     */
    @PostMapping("/login")
    public Map<String, String> handleLogin(@RequestBody LoginRequest request) {
        // ส่ง username และ password ไปให้ service เช็ค
        User user = authService.login(request.getUsername(), request.getPassword());

        // ถ้า login สำเร็จ ส่ง role และ username กลับไป
        if (user != null) {
            return Map.of(
                "status", "success",
                "role", user.getRole(),
                "username", user.getUsername()
            );
        } else {
            // login ไม่สำเร็จ
            return Map.of("status", "failed");
        }
    }

    /**
     * API สำหรับสมัครสมาชิก
     * เรียกผ่าน POST /register
     * รับ username และ password แล้วสร้าง user ใหม่
     */
    @PostMapping("/register")
    public Map<String, String> handleRegister(@RequestBody RegisterRequest request) {
        // ส่งไปให้ service สร้าง user ใหม่
        boolean isSuccess = authService.register(request.getUsername(), request.getPassword());

        if (isSuccess) {
            return Map.of("status", "success");
        } else {
            // สมัครไม่สำเร็จ อาจเป็นเพราะ username ซ้ำ
            return Map.of("status", "failed", "message", "Username already taken");
        }
    }
}