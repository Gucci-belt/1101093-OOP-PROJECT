package com.login_oop.oop_backend.models;

// Class สำหรับเก็บข้อมูล user
// เป็น base class ที่ Member และ Admin จะสืบทอดไป
public class User {
    
    // ข้อมูลของ user
    private String username;
    private String password;
    private String role;

    // Constructor สำหรับสร้าง user
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter สำหรับ username
    public String getUsername() {
        return this.username;
    }

    // Getter สำหรับ role
    public String getRole() {
        return this.role;
    }

    // Method สำหรับเช็ครหัสผ่าน
    // ไม่ให้อ่าน password โดยตรง แต่ให้เช็คได้ว่าถูกต้องหรือไม่
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}