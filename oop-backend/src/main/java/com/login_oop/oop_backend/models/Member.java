package com.login_oop.oop_backend.models;

// Class สำหรับ Member
// สืบทอดจาก User และกำหนด role เป็น "Member" เสมอ
public class Member extends User {

    // Constructor สำหรับสร้าง Member
    public Member(String username, String password) {
        // เรียก constructor ของ User และกำหนด role เป็น "Member"
        super(username, password, "Member");
    }
}