package com.login_oop.oop_backend.models; // 1. อยู่ในแพ็คเกจ models เหมือนกัน

// 2. "extends User" คือการบอกว่า "Admin คือ User รูปแบบหนึ่ง"
// นี่คือ "Inheritance" (การสืบทอด)
public class Admin extends User {

    // 3. Constructor ของ Admin
    public Admin(String username, String password) {
        // 4. "super(...)" คือการวิ่งไปเรียก Constructor ของคลาสแม่ (User)
        // เราสร้าง Admin โดย "ล็อค" role ให้เป็น "Admin" เสมอ
        super(username, password, "Admin");
    }
    
    // 5. Admin อาจมีเมธอดพิเศษของตัวเองก็ได้ เช่น...
    public void performAdminTasks() {
        System.out.println("ผู้ใช้ " + getUsername() + " กำลังทำงานของแอดมิน");
    }
}