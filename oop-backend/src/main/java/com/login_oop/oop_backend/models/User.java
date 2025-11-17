package com.login_oop.oop_backend.models; // 1. บอกว่าไฟล์นี้อยู่ในแพ็คเกจ models

// 2. นี่คือ "แบบพิมพ์เขียว" (Class) ของผู้ใช้ทุกคน
public class User {
    
    // 3. คุณสมบัติ (Fields) - เป็น private เพื่อ Encapsulation
    private String username;
    private String password;
    private String role;

    // 4. นี่คือ "Constructor" (โรงงาน) ที่ใช้สร้าง User
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // 5. นี่คือ "Methods" (พฤติกรรม)
    
    // เมธอดให้คนอื่นมา "อ่าน" username (Getter)
    public String getUsername() {
        return this.username;
    }

    // เมธอดให้คนอื่นมา "อ่าน" role (Getter)
    public String getRole() {
        return this.role;
    }

    // เมธอดสำหรับ "ตรวจสอบ" รหัสผ่าน
    // นี่คือ Encapsulation ที่ดี: เราไม่ให้คนอื่น "อ่าน" รหัสผ่าน
    // แต่เราให้ "ถาม" ได้ว่ารหัสที่ป้อนมาถูกไหม
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}