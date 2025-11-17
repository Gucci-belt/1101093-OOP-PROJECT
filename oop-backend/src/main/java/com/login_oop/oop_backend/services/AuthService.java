package com.login_oop.oop_backend.services; // 1. บอกว่าอยู่ในแพ็คเกจ services

// 2. Import เครื่องมือและคลาสที่ต้องใช้
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login_oop.oop_backend.models.Member;
import com.login_oop.oop_backend.models.User;
import com.login_oop.oop_backend.repositories.UserRepository;

// 3. @Service บอก Spring ว่าคลาสนี้คือ "สมอง" หรือ "Service"
@Service
public class AuthService {

    // 4. AuthService ต้องคุยกับ "ห้องเก็บของ" (Repository)
    private UserRepository userRepository;

    /**
     * 5. นี่คือ Constructor ที่ใช้เทคนิค "Dependency Injection"
     * @Autowired บอก Spring ว่า "ช่วยเอา UserRepository ที่คุณสร้างไว้ มาฉีดใส่ในนี้ที"
     * ทำให้ AuthService ของเรามี userRepository พร้อมใช้งานทันที
     */
    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 🎯 นี่คือ Logic (เมท็อด) สำหรับการ Login
     */
    public User login(String username, String password) {
        // 1. ไปที่ "ห้องเก็บของ" แล้ว "ค้นหา" (Search) ผู้ใช้ด้วยชื่อ
        User foundUser = userRepository.findByUsername(username);

        // 2. ถ้าเจอผู้ใช้ และ รหัสผ่านที่ป้อนมา "ถูกต้อง" (เช็คด้วยเมธอดของ User)
        if (foundUser != null && foundUser.checkPassword(password)) {
            System.out.println("[AuthService] " + username + " ล็อกอินสำเร็จ");
            return foundUser; // คืนค่า User ที่ login สำเร็จ
        }
        
        System.out.println("[AuthService] " + username + " ล็อกอินล้มเหลว");
        return null; // Login ล้มเหลว (ไม่เจอ user หรือ รหัสผิด)
    }

    /**
     * 🎯 นี่คือ Logic (เมท็อด) สำหรับการสมัครสมาชิก (Register)
     */
    public boolean register(String username, String password) {
        // 1. ไปที่ "ห้องเก็บของ" แล้ว "ค้นหา" ว่ามีชื่อนี้ในระบบหรือยัง
        if (userRepository.findByUsername(username) != null) {
            System.out.println("[AuthService] " + username + " สมัครสมาชิกไม่ได้ (ชื่อซ้ำ)");
            return false; // สมัครล้มเหลว (ชื่อผู้ใช้นี้ถูกใช้ไปแล้ว)
        }

        // 2. ถ้าชื่อไม่ซ้ำ ให้สร้าง "Member" ใหม่
        User newUser = new Member(username, password);
        
        // 3. สั่งให้ "ห้องเก็บของ" บันทึกผู้ใช้ใหม่นี้
        userRepository.save(newUser);
        
        System.out.println("[AuthService] " + username + " สมัครสมาชิกสำเร็จ");
        return true; // สมัครสำเร็จ
    }
}