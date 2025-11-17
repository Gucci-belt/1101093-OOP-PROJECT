package com.login_oop.oop_backend.dto;

// คลาสนี้เป็นแค่ "กล่อง" ธรรมดาๆ ไว้รับข้อมูลจาก JS
public class LoginRequest {
    private String username;
    private String password;

    // Getters (จำเป็นสำหรับ Spring)
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}