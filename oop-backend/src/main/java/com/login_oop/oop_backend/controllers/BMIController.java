package com.login_oop.oop_backend.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.login_oop.oop_backend.dto.BMIRequest;
import com.login_oop.oop_backend.dto.BMIResponse;
import com.login_oop.oop_backend.services.BMIService;

// Controller สำหรับจัดการ API BMI
@RestController
@CrossOrigin(origins = "*")
public class BMIController {

    private final BMIService bmiService;

    // Constructor
    public BMIController(BMIService bmiService) {
        this.bmiService = bmiService;
    }

    /**
     * API สำหรับคำนวณ BMI
     * เรียกผ่าน POST /api/bmi/calculate
     */
    @PostMapping("/api/bmi/calculate")
    public Object calculateBMI(@RequestBody(required = false) BMIRequest request) {
        // เช็คว่ามี request body หรือเปล่า
        if (request == null) {
            return Map.of("status", "failed", "message", "Request body is missing");
        }
        
        // เช็คข้อมูล
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Username is required");
        }
        
        if (request.getWeight() <= 0) {
            return Map.of("status", "failed", "message", "Weight must be greater than 0");
        }
        
        if (request.getHeight() <= 0) {
            return Map.of("status", "failed", "message", "Height must be greater than 0");
        }
        
        // คำนวณ BMI
        BMIResponse response = bmiService.calculateBMI(
            request.getUsername(),
            request.getWeight(),
            request.getHeight()
        );
        
        return Map.of(
            "status", "success",
            "bmi", response.getBmi(),
            "calories", response.getCalories(),
            "category", response.getCategory(),
            "categoryRange", response.getCategoryRange()
        );
    }

    /**
     * API สำหรับดึงข้อมูล BMI ของ user
     * เรียกผ่าน GET /api/bmi/{username}
     */
    @GetMapping("/api/bmi/{username}")
    public Object getUserBMI(@PathVariable String username) {
        BMIResponse response = bmiService.getUserBMI(username);
        
        if (response == null) {
            return Map.of("status", "failed", "message", "ไม่พบข้อมูล BMI");
        }
        
        return Map.of(
            "status", "success",
            "bmi", response.getBmi(),
            "calories", response.getCalories(),
            "category", response.getCategory(),
            "categoryRange", response.getCategoryRange()
        );
    }

    /**
     * API สำหรับดึงข้อมูลน้ำหนักและส่วนสูงของ user
     * เรียกผ่าน GET /api/bmi/data/{username}
     */
    @GetMapping("/api/bmi/data/{username}")
    public Object getUserData(@PathVariable String username) {
        com.login_oop.oop_backend.models.UserData userData = bmiService.getUserData(username);
        
        if (userData == null) {
            return Map.of("status", "failed", "message", "ไม่พบข้อมูล");
        }
        
        return Map.of(
            "status", "success",
            "weight", userData.getWeight(),
            "height", userData.getHeight()
        );
    }
}

