package com.login_oop.oop_backend.models; // 1. อยู่ในแพ็คเกจ models เหมือนกัน

// 2. "Member ก็คือ User รูปแบบหนึ่ง" (Inheritance)
public class Member extends User {

    // 3. Constructor ของ Member
    public Member(String username, String password) {
        // 4. "super(...)" วิ่งไปเรียกคลาสแม่ (User)
        // เรา "ล็อค" role ให้เป็น "Member" เสมอ
        super(username, password, "Member");
    }
    
    // 5. Member อาจมีเมธอดของตัวเองในอนาคต เช่น คำนวณ BMI 
    // public double calculateBMI(...) { ... }
}