package com.login_oop.oop_backend.dto;

// คลาสนี้เป็นแค่ "กล่อง" ธรรมดาๆ ไว้รับข้อมูลจาก JS
public class LoginRequest {
    private String username;
    private String password;

    // Getters (จำเป็นสำหรับ Spring)
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    
    // Setters (จำเป็นสำหรับ Spring เพื่อ deserialize JSON)
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}