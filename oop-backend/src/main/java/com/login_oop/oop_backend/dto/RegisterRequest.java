package com.login_oop.oop_backend.dto;

// นี่คือกล่องสำหรับรับข้อมูลตอน "สมัครสมาชิก"
public class RegisterRequest {
    private String username;
    private String password;
    // (ไม่ต้องมี confirmPassword ที่นี่ เพราะ JS ควรเช็คให้จบก่อนส่งมา)

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    
    // Setters (จำเป็นสำหรับ Spring เพื่อ deserialize JSON)
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}