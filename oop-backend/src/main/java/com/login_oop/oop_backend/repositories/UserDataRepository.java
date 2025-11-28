package com.login_oop.oop_backend.repositories;

import com.login_oop.oop_backend.models.UserData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// Repository สำหรับจัดการข้อมูล BMI
// เก็บข้อมูลใน memory
@Repository
public class UserDataRepository {
    
    // เก็บข้อมูล BMI ของ user ทั้งหมด
    private final List<UserData> userDataDatabase;

    // Constructor
    public UserDataRepository() {
        this.userDataDatabase = new ArrayList<>();
    }

    /**
     * บันทึกข้อมูล BMI ของ user
     * ถ้ามีอยู่แล้วจะอัปเดต ถ้าไม่มีจะเพิ่มใหม่
     */
    public void save(UserData userData) {
        // หาว่ามีข้อมูลของ user นี้อยู่แล้วหรือไม่
        UserData existing = findByUsername(userData.getUsername());
        if (existing != null) {
            // อัปเดตข้อมูลเดิม
            existing.setWeight(userData.getWeight());
            existing.setHeight(userData.getHeight());
            existing.setBmi(userData.getBmi());
            existing.setCalories(userData.getCalories());
        } else {
            // เพิ่มใหม่
            userDataDatabase.add(userData);
        }
    }

    /**
     * ค้นหาข้อมูล BMI จาก username
     */
    public UserData findByUsername(String username) {
        for (UserData userData : userDataDatabase) {
            if (userData.getUsername().equals(username)) {
                return userData;
            }
        }
        return null; // ไม่เจอ
    }
}

