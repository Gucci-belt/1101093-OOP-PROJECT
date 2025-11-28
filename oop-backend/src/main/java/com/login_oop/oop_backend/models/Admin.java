package com.login_oop.oop_backend.models;

// Class สำหรับ Admin
// สืบทอดจาก User และกำหนด role เป็น "Admin" เสมอ
public class Admin extends User {

    // Constructor สำหรับสร้าง Admin
    public Admin(String username, String password) {
        // เรียก constructor ของ User และกำหนด role เป็น "Admin"
        super(username, password, "Admin");
    }
    
    // Method สำหรับทำงานของ admin (ถ้าต้องการเพิ่มในอนาคต)
    public void performAdminTasks() {
        System.out.println("ผู้ใช้ " + getUsername() + " กำลังทำงานของแอดมิน");
    }
}