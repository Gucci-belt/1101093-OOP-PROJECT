package com.login_oop.oop_backend.dto;

// DTO สำหรับรับข้อมูล login จาก frontend
// Spring จะแปลง JSON ที่ส่งมาให้เป็น object นี้
public class LoginRequest {
    private String username;
    private String password;

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    
    // Setters (Spring ใช้ตอนแปลง JSON)
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}