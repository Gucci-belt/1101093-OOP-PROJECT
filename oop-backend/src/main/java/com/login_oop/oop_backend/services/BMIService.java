package com.login_oop.oop_backend.services;

import org.springframework.stereotype.Service;

import com.login_oop.oop_backend.dto.BMIResponse;
import com.login_oop.oop_backend.models.UserData;
import com.login_oop.oop_backend.repositories.UserDataRepository;

// Service สำหรับจัดการการคำนวณ BMI
@Service
public class BMIService {
    
    private final UserDataRepository userDataRepository;

    // Constructor
    public BMIService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    /**
     * คำนวณ BMI จากน้ำหนักและส่วนสูง
     * สูตร: BMI = weight / (height/100)^2
     */
    public BMIResponse calculateBMI(String username, double weight, double height) {
        // แปลงส่วนสูงจาก cm เป็น m
        double heightInMeters = height / 100.0;
        
        // คำนวณ BMI
        double bmi = weight / (heightInMeters * heightInMeters);
        bmi = Math.round(bmi * 100.0) / 100.0; // ปัดเป็น 2 ทศนิยม
        
        // คำนวณแคลอรี่ที่ควรกิน (สูตร: weight * 30)
        double calories = Math.round(weight * 30);
        
        // หา category ของ BMI
        String category = getBMICategory(bmi);
        String categoryRange = getBMICategoryRange(bmi);
        
        // บันทึกข้อมูล
        UserData userData = new UserData(username, weight, height, bmi, calories);
        userDataRepository.save(userData);
        
        return new BMIResponse(bmi, calories, category, categoryRange);
    }

    /**
     * ดึงข้อมูล BMI ของ user
     */
    public BMIResponse getUserBMI(String username) {
        UserData userData = userDataRepository.findByUsername(username);
        if (userData == null) {
            return null;
        }
        
        String category = getBMICategory(userData.getBmi());
        String categoryRange = getBMICategoryRange(userData.getBmi());
        
        return new BMIResponse(
            userData.getBmi(),
            userData.getCalories(),
            category,
            categoryRange
        );
    }

    /**
     * ดึงข้อมูล UserData (น้ำหนักและส่วนสูง) ของ user
     */
    public UserData getUserData(String username) {
        return userDataRepository.findByUsername(username);
    }

    /**
     * แปลงค่า BMI เป็น category
     */
    private String getBMICategory(double bmi) {
        if (bmi < 18.5) return "ผอมเกินไป";
        if (bmi <= 22.9) return "น้ำหนักปกติ";
        if (bmi <= 24.9) return "น้ำหนักเกิน";
        if (bmi <= 29.9) return "อ้วน";
        return "อ้วนมาก";
    }

    /**
     * แปลงค่า BMI เป็นช่วงค่า
     */
    private String getBMICategoryRange(double bmi) {
        if (bmi < 18.5) return "น้อยกว่า 18.5";
        if (bmi <= 22.9) return "18.5 - 22.9";
        if (bmi <= 24.9) return "23.0 - 24.9";
        if (bmi <= 29.9) return "25.0 - 29.9";
        return "30.0 ขึ้นไป";
    }
}

