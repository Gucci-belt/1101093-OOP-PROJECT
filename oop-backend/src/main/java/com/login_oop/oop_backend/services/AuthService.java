package com.login_oop.oop_backend.services;

import org.springframework.stereotype.Service;

import com.login_oop.oop_backend.models.Member;
import com.login_oop.oop_backend.models.User;
import com.login_oop.oop_backend.repositories.UserRepository;

// Service สำหรับจัดการ login และ register
@Service
public class AuthService {

    // ต้องใช้ UserRepository เพื่อดึงข้อมูล user
    private final UserRepository userRepository;

    // Constructor รับ UserRepository เข้ามา (Spring จะ inject ให้อัตโนมัติ)
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method สำหรับ login
     * รับ username และ password แล้วเช็คว่าถูกต้องหรือไม่
     */
    public User login(String username, String password) {
        // หา user จาก username
        User foundUser = userRepository.findByUsername(username);

        // ถ้าเจอ user และ password ถูกต้อง
        if (foundUser != null && foundUser.checkPassword(password)) {
            System.out.println("[AuthService] " + username + " ล็อกอินสำเร็จ");
            return foundUser;
        }
        
        System.out.println("[AuthService] " + username + " ล็อกอินล้มเหลว");
        return null; // login ไม่สำเร็จ
    }

    /**
     * Method สำหรับสมัครสมาชิก
     * รับ username และ password แล้วสร้าง user ใหม่
     */
    public boolean register(String username, String password) {
        // เช็คว่า username ซ้ำหรือยัง
        if (userRepository.findByUsername(username) != null) {
            System.out.println("[AuthService] " + username + " สมัครสมาชิกไม่ได้ (ชื่อซ้ำ)");
            return false; // username ซ้ำ
        }

        // สร้าง Member ใหม่
        User newUser = new Member(username, password);
        
        // บันทึก user ใหม่ลง repository (และบันทึกลงไฟล์ด้วย)
        userRepository.save(newUser, password);
        
        System.out.println("[AuthService] " + username + " สมัครสมาชิกสำเร็จ");
        return true;
    }
}