package com.login_oop.oop_backend.dto;

// นี่คือกล่องสำหรับรับข้อมูลตอน "สมัครสมาชิก"
public class RegisterRequest {
    private String username;
    private String password;
    // (ไม่ต้องมี confirmPassword ที่นี่ เพราะ JS ควรเช็คให้จบก่อนส่งมา)

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}